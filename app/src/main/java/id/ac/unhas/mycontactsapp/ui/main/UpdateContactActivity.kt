package id.ac.unhas.mycontactsapp.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.mycontactsapp.R
import id.ac.unhas.mycontactsapp.contact.Contact

class UpdateContactActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnCancel: Button

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contact: Contact

    companion object{
        const val EXTRA_NAME_UPDATE = "NAME"
        const val EXTRA_CONTACT_UPDATE = "CONTACT"
        const val EXTRA_ADDRESS_UPDATE = "ADDRESS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)
        if (supportActionBar != null) {
            supportActionBar?.title = "Update Contact"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        editTextName = findViewById(R.id.name_content)
        editTextContact = findViewById(R.id.contact_content)
        editTextAddress = findViewById(R.id.address_content)
        btnUpdate = findViewById(R.id.btn_update)
        btnCancel = findViewById(R.id.btn_cancel)
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        getExtra()

        btnUpdate.setOnClickListener {
            updateContact(contact)
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun getExtra(){
        contact = intent.getParcelableExtra("EXTRA_LIST")!!
        editTextName.setText(intent.getStringExtra(EXTRA_NAME_UPDATE))
        editTextContact.setText(intent.getStringExtra(EXTRA_CONTACT_UPDATE))
        editTextAddress.setText(intent.getStringExtra(EXTRA_ADDRESS_UPDATE))
    }

    private fun updateContact(contact: Contact){

        contact.name = editTextName.text.toString().trim()
        contact.contact = editTextContact.text.toString().trim()
        contact.address = editTextAddress.text.toString().trim()
        contactViewModel.updateContact(contact)

        finish()
    }
}