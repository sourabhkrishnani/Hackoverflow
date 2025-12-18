package com.example.callingapp.data

import android.content.ContentResolver
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Contact(val id: String, val name: String)

class ContactRepository(private val contentResolver: ContentResolver) {

    suspend fun getContacts(): List<Contact> = withContext(Dispatchers.IO) {
        val contacts = mutableListOf<Contact>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
            val nameColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

            while (it.moveToNext()) {
                val id = it.getString(idColumn)
                val name = it.getString(nameColumn)
                contacts.add(Contact(id, name))
            }
        }
        return@withContext contacts.distinctBy { it.name }.sortedBy { it.name }
    }
}
