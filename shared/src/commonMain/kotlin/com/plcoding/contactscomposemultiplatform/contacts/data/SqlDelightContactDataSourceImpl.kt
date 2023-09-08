package com.plcoding.contactscomposemultiplatform.contacts.data

import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.database.ContactDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightContactDataSourceImpl(
    db: ContactDatabase
): ContactDataSource {

    private val queries = db.contactQueries

    override fun getContacts(): Flow<List<Contact>> {
        return queries
            .getContacts()
            .asFlow()
            .mapToList()
            .map { list ->
                list.map { it.toContact() }
            }
    }

    override fun getRecentContacts(amount: Long): Flow<List<Contact>> {
        return queries
            .getRecentContacts(amount)
            .asFlow()
            .mapToList()
            .map { list ->
                list.map { it.toContact() }
            }
    }

    override suspend fun insertContact(contact: Contact) {
        with(contact) {
            queries.insertContactEntity(
                id = id,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phoneNumber = phoneNumber,
                createdAt = Clock.System.now().toEpochMilliseconds(),
                imagePath = null
            )
        }
    }

    override suspend fun deleteContact(id: Long) {
        queries.deleteContact(id)
    }
}