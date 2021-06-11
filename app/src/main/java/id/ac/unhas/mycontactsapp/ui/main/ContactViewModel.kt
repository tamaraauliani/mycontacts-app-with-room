package id.ac.unhas.mycontactsapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.mycontactsapp.contact.Contact
import id.ac.unhas.mycontactsapp.contact.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var contactRepository = ContactRepository(application)
    private var contacts: LiveData<List<Contact>>? = contactRepository.getContacts()

    fun getContacts(): LiveData<List<Contact>>? {
        return contacts
    }

    fun insertContact(contact: Contact) {
        contactRepository.insertContact(contact)
    }

    fun deleteContact(contact: Contact) {
        contactRepository.deleteContact(contact)
    }

    fun updateContact(contact: Contact) {
        contactRepository.updateContact(contact)
    }

    fun searchContact(name: String): LiveData<List<Contact>>?{
        return contactRepository.searchContact(name)
    }

}