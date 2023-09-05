package com.plcoding.contactscomposemultiplatform.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.presentation.ContactListEvent
import com.plcoding.contactscomposemultiplatform.contacts.domain.presentation.ContactListState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactListViewModel: ViewModel() {

    private val _state = MutableStateFlow(ContactListState(
        contacts = contacts
    ))

    val state = _state.asStateFlow()

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {
        when(event) {
            ContactListEvent.DeleteContent -> TODO()
            ContactListEvent.DismissContact -> TODO()
            is ContactListEvent.EditContact -> TODO()
            ContactListEvent.OnAddNewContact -> TODO()
            ContactListEvent.OnAddPhotoClicked -> TODO()
            is ContactListEvent.OnEmailChanged -> TODO()
            is ContactListEvent.OnFirstNameChanged -> TODO()
            is ContactListEvent.OnLastNameChanged -> TODO()
            is ContactListEvent.OnPhoneNumberChanged -> TODO()
            is ContactListEvent.OnPhotoPicked -> TODO()
            ContactListEvent.SaveContact -> TODO()
            is ContactListEvent.SelectContact -> TODO()
        }
    }
}

private val contacts = (0..50).map {
    Contact(
        id = it.toLong(),
        firstName = "Name $it",
        lastName = "Last $it",
        email = "email@email$it.com",
        phoneNumber = "293848932743",
        photoBytes = null
    )
}