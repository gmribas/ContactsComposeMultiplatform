package com.plcoding.contactscomposemultiplatform.contacts.data

import com.plcoding.conactscomposemultiplatform.com.ContactDatabase
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.data.ImageStorage
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock

class SqlDelightContactDataSourceImpl(
    db: ContactDatabase,
    private val imageStorage: ImageStorage
) : ContactDataSource {

    private val queries = db.contactQueries

    override fun getContacts(): Flow<List<Contact>> {
        return queries
            .getContacts()
            .asFlow()
            .mapToList()
            .map { list ->
                supervisorScope {
                    list
                        .map {
                            async { it.toContact(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override fun getRecentContacts(amount: Long): Flow<List<Contact>> {
        return queries
            .getRecentContacts(amount)
            .asFlow()
            .mapToList()
            .map { list ->
                supervisorScope {
                    list
                        .map {
                            async { it.toContact(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override suspend fun insertContact(contact: Contact) {
        val imagePath = contact.photoBytes?.let {
            imageStorage.saveImage(it)
        }

        with(contact) {
            queries.insertContact(
                id = id,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phoneNumber = phoneNumber,
                createdAt = Clock.System.now().toEpochMilliseconds(),
                imagePath = imagePath
            )
        }
    }

    override suspend fun deleteContact(id: Long) {
        val entity = queries.getContactById(id).executeAsOne()
        entity.imagePath?.let {
            imageStorage.deleteImage(it)
        }

        queries.deleteContact(id)
    }
}