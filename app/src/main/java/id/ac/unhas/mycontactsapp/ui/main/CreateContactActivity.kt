package id.ac.unhas.mycontactsapp.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.mycontactsapp.R
import id.ac.unhas.mycontactsapp.contact.Contact

class CreateContactActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var btnSave: Button
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)
        if(supportActionBar != null){
            supportActionBar?.title = "Add New Contact"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextName = findViewById(R.id.name_content)
        editTextContact = findViewById(R.id.contact_content)
        editTextAddress = findViewById(R.id.address_content)
        btnSave = findViewById(R.id.btn_save)
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        btnSave.setOnClickListener{
            saveList()
        }
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun saveList(){

        val name = editTextName.text.toString().trim()
        val contact = editTextContact.text.toString().trim()
        val address = editTextAddress.text.toString().trim()



        contactViewModel.insertContact(
            Contact(
                name = name,
                contact = contact,
                address = address
            )
        )

        finish()
    }
}