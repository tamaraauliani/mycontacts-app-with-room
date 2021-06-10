package id.ac.unhas.mycontactsapp.contact

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContactRepository(application: Application) {

    private val contactDao: ContactDao?
    private var contacts: LiveData<List<Contact>>? = null

    init {
        val db = AppDatabase.getInstance(application.applicationContext)
        contactDao = db?.contactDao()
        contacts = contactDao?.getContacts()
    }

    fun getContacts(): LiveData<List<Contact>>? {
        return contacts
    }

    fun insertContact(contact: Contact) = runBlocking {
        this.launch(Dispatchers.IO) {
            contactDao?.insertContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                contactDao?.deleteContact(contact)
            }
        }
    }

    fun updateContact(contact: Contact) = runBlocking {
        this.launch(Dispatchers.IO) {
            contactDao?.updateContact(contact)
        }
    }

    fun searchContact(name: String): LiveData<List<Contact>>?{
        return contactDao?.searchContact(name)
    }
}