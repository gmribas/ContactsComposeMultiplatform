package com.plcoding.contactscomposemultiplatform.contacts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.presentation.widget.AddContactSheet
import com.plcoding.contactscomposemultiplatform.contacts.presentation.widget.ContactListItemWidget
import com.plcoding.contactscomposemultiplatform.core.presentation.ImagePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    state: ContactListState,
    newContact: Contact?,
    onEvent: (ContactListEvent) -> Unit,
    imagePicker: ImagePicker
    ) {

    imagePicker.registerPicker { bytes ->
        onEvent(ContactListEvent.OnPhotoPicked(bytes))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ContactListEvent.OnAddNewContact) },
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(imageVector = Icons.Rounded.PersonAdd, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "My contacts (${state.contacts.size})",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            items(state.contacts.size) { index ->
                val contact = state.contacts[index]
                ContactListItemWidget(
                    contact = contact,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEvent(ContactListEvent.SelectContact(contact)) }
                        .padding(horizontal = 16.dp)
                    )
            }
        }
    }

    AddContactSheet(
        state = state,
        newContact = newContact,
        isOpen = state.isAddContactSheetOpen,
        onEvent = { event ->
            if (event is ContactListEvent.OnAddPhotoClicked) {
                imagePicker.pickImage()
            } else {
                onEvent(event)
            }
        }
    )
}