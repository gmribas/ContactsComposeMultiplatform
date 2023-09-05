package com.plcoding.contactscomposemultiplatform.contacts.domain.presentation

import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact

sealed interface ContactListEvent {

    object OnAddNewContact : ContactListEvent
    object DismissContact : ContactListEvent

    data class OnFirstNameChanged(val value: String) : ContactListEvent
    data class OnLastNameChanged(val value: String) : ContactListEvent
    data class OnEmailChanged(val value: String) : ContactListEvent
    data class OnPhoneNumberChanged(val value: String) : ContactListEvent

    class OnPhotoPicked(val bytes: ByteArray) : ContactListEvent

    object OnAddPhotoClicked : ContactListEvent

    object SaveContact : ContactListEvent

    data class SelectContact(val contact: Contact): ContactListEvent

    data class EditContact(val contact: Contact): ContactListEvent

    object DeleteContent: ContactListEvent
}