package com.app.whisperly.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.whisperly.R
import com.app.whisperly.model.Contact
import com.app.whisperly.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewContactsDisplayActivity: ComponentActivity() {

    private val viewModel: ContactsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ContactsScreen(viewModel, onSelectContact = {
                val intent = Intent()
                intent.putExtra("resultKey", it) // Use the same key as in the launcher
                setResult(Activity.RESULT_OK, intent)
                finish()
            })
        }

    }
}
@Composable
fun ContactsScreen(viewModel: ContactsViewModel, onSelectContact: (Contact) -> Unit) {
    val contacts  = viewModel.contacts.value

    ContactsList(contacts = contacts, onSelectContact)
}

@Composable
fun ContactsList(contacts: List<Contact>, onSelectContact: (Contact) -> Unit) {
    // State to track the selected contact
    var selectedContact by remember { mutableStateOf<Contact?>(null) }

    LazyColumn {
        items(contacts) { contact ->
            ContactCard(
                contact = contact,
                isSelected = selectedContact == contact,
                onSelectContact = {
                    selectedContact = it
                    onSelectContact(it)

                }
            )
        }
    }
}

@Composable
fun ContactCard(contact: Contact, isSelected: Boolean, onSelectContact: (Contact) -> Unit) {

    Card(
        backgroundColor = if(isSelected) Color.DarkGray else Color.LightGray,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onSelectContact(contact) },
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_other_user),
                contentDescription = "Contact Image",
                modifier = Modifier
                    // Set the size of the image
                    .width(50.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = contact.name, style = MaterialTheme.typography.h6)
                Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

}