package com.app.whisperly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.whisperly.repository.ContactsRepository
import com.app.whisperly.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
): ViewModel() {
    var contacts = mutableStateOf(listOf<Contact>())


    init {
        loadContacts()
    }
    private fun loadContacts() {
        viewModelScope.launch(Dispatchers.IO) {

            // This should ideally be launched in a coroutine scope
            val newContacts = contactsRepository.getContacts()
            launch (Dispatchers.Main){
                contacts.value = newContacts
            }

        }
    }
}