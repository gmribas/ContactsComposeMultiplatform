package com.plcoding.contactscomposemultiplatform.di

import android.content.Context
import com.plcoding.conactscomposemultiplatform.com.ContactDatabase
import com.plcoding.contactscomposemultiplatform.contacts.data.SqlDelightContactDataSourceImpl
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.data.DatabaseDriverFactory

actual class AppModule(private val context: Context) {

    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSourceImpl(
            db = ContactDatabase(
                driver = DatabaseDriverFactory(context).create()
            )
        )
    }
}