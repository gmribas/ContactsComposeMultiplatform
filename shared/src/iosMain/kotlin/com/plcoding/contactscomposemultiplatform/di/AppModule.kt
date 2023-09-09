package com.plcoding.contactscomposemultiplatform.di

import com.plcoding.conactscomposemultiplatform.com.ContactDatabase
import com.plcoding.contactscomposemultiplatform.contacts.data.SqlDelightContactDataSourceImpl
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.data.DatabaseDriverFactory
import com.plcoding.contactscomposemultiplatform.core.data.ImageStorage

actual class AppModule {

    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSourceImpl(
            db = ContactDatabase(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}