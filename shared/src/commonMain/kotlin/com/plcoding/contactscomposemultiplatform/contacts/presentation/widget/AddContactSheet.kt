package com.plcoding.contactscomposemultiplatform.contacts.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListEvent
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListState
import com.plcoding.contactscomposemultiplatform.core.presentation.BottomSheetFromWish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactSheet(
    state: ContactListState,
    newContact: Contact?,
    isOpen: Boolean,
    onEvent: (ContactListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetFromWish(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                if (newContact?.photoBytes == null) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(40))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable { onEvent(ContactListEvent.OnAddPhotoClicked) }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = RoundedCornerShape(40)
                            ),

                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "add photo",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    ContactPhoto(
                        contact = newContact,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable { onEvent(ContactListEvent.OnAddPhotoClicked) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ContactTextField(
                    value = newContact?.firstName ?: "",
                    placeholder = "First Name",
                    error = state.firstNameError,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnFirstNameChanged(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ContactTextField(
                    value = newContact?.lastName ?: "",
                    placeholder = "Last Name",
                    error = state.lastNameError,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnLastNameChanged(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ContactTextField(
                    value = newContact?.email ?: "",
                    placeholder = "First Name",
                    error = state.emailError,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnEmailChanged(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ContactTextField(
                    value = newContact?.phoneNumber ?: "",
                    placeholder = "First Name",
                    error = state.phoneNumberError,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnPhoneNumberChanged(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onEvent(ContactListEvent.SaveContact)
                    }
                ) {
                    Text(text = "Save")
                }
            }

            IconButton(onClick = { onEvent(ContactListEvent.DismissContact) }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close"
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ContactTextField(
    value: String,
    placeholder: String,
    error: String?,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        if (error != null) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}
