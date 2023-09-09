package com.plcoding.contactscomposemultiplatform.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val source: ContactDataSource
): ViewModel() {

    private val _state = MutableStateFlow(
        ContactListState()
    )

    val state = combine(
        _state,
        source.getContacts(),
        source.getRecentContacts(20.toLong())
    ) { state, contacts, recentContacts ->
        state.copy(
            contacts = contacts,
            recentlyAddedContacts = recentContacts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {
        when(event) {
            ContactListEvent.DeleteContact -> {
                deleteContact()
            }
            ContactListEvent.DismissContact -> {
                dismissContact()
            }
            is ContactListEvent.EditContact -> {
                editContact(event.contact)
            }
            ContactListEvent.OnAddNewContact -> {
                addNewContact()
            }
//            ContactListEvent.OnAddPhotoClicked -> TODO()
            is ContactListEvent.OnEmailChanged -> {
                newContact = newContact?.copy(
                    email = event.value
                )
            }
            is ContactListEvent.OnFirstNameChanged -> {
                newContact = newContact?.copy(
                    firstName = event.value
                )
            }
            is ContactListEvent.OnLastNameChanged -> {
                newContact = newContact?.copy(
                    lastName = event.value
                )
            }
            is ContactListEvent.OnPhoneNumberChanged -> {
                newContact = newContact?.copy(
                    phoneNumber = event.value
                )
            }
            is ContactListEvent.OnPhotoPicked -> {
                newContact = newContact?.copy(
                    photoBytes = event.bytes
                )
            }
            ContactListEvent.SaveContact -> {
                newContact?.let { contact ->
                    saveContact(contact)
                }
            }
            is ContactListEvent.SelectContact -> {
                _state.update {
                    it.copy(
                        selectedContact = event.contact,
                        isSelectedSheetOpen = true
                    )
                }
            }
            else -> Unit
        }
    }

    private fun deleteContact() {
        viewModelScope.launch {
            _state.value.selectedContact?.id?.let { id ->
                _state.update {
                    it.copy(isSelectedSheetOpen = false)
                }
                source.deleteContact(id)
                delay(300L) //animation delay
                _state.update {
                    it.copy(selectedContact = null)
                }
            }
        }
    }

    private fun dismissContact() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSelectedSheetOpen = false,
                    isAddContactSheetOpen = false,
                    firstNameError = null,
                    lastNameError = null,
                    emailError = null,
                    phoneNumberError = null
                )
            }
            delay(300L)
            _state.update {
                it.copy(selectedContact = null)
            }
        }
    }

    private fun editContact(contact: Contact) {
        _state.update {
            it.copy(
                selectedContact = null,
                isAddContactSheetOpen = true,
                isSelectedSheetOpen = false
            )
        }

        newContact = contact
    }

    private fun addNewContact() {
        _state.update {
            it.copy(
                isAddContactSheetOpen = true
            )
        }

        newContact = Contact(
            id = null,
            firstName = "",
            lastName = "",
            email = "",
            phoneNumber = "",
            photoBytes = null

        )
    }

    private fun saveContact(contact: Contact) {
        val result = ContactValidator.validateContact(contact)
        val errors = listOfNotNull(
            result.firstNameError,
            result.lastNameError,
            result.emailError,
            result.phoneNumberError
        )

        if (errors.isEmpty()) {
            _state.update {
                it.copy(
                    isAddContactSheetOpen = false,
                    firstNameError = null,
                    lastNameError = null,
                    emailError = null,
                    phoneNumberError = null
                )
            }

            viewModelScope.launch {
                source.insertContact(contact)
                delay(300L)
                newContact = null
            }
        } else {
            _state.update {
                it.copy(
                    firstNameError = result.firstNameError,
                    lastNameError = result.lastNameError,
                    emailError = result.emailError,
                    phoneNumberError = result.phoneNumberError
                )
            }
        }
    }
}