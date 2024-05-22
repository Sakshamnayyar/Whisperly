package com.app.whisperly.repository

import android.content.Context
import android.provider.ContactsContract
import com.app.whisperly.model.Contact
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    @ApplicationContext val context:Context) {
    fun getContacts(): List<Contact> {
        var contactsList = mutableListOf<Contact>()

        val contactsCursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        while (contactsCursor?.moveToNext() == true) {
            val nameIndex = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            if (nameIndex != -1 && phoneNumberIndex != -1) {
                val name = contactsCursor.getString(nameIndex)
                var phoneNumber = contactsCursor.getString(phoneNumberIndex)

                phoneNumber = phoneNumber.replace("\\s".toRegex(), "")
                    .replace("-", "")
                    .replace("(", "")
                    .replace(")", "")


                if(phoneNumber.length >= 10) {
                    contactsList.add(Contact(name, phoneNumber))
                }
            }
            contactsList = contactsList.distinctBy { it.name to it.phoneNumber }.toMutableList()
        }

        contactsCursor?.close()

        return contactsList
    }
}
