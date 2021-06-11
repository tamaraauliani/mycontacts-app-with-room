package id.ac.unhas.mycontactsapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.unhas.mycontactsapp.R
import id.ac.unhas.mycontactsapp.contact.Contact
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.fab)


        contactRV.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(this) { contact, i ->
            showAlertMenu(contact)
        }
        contactRV.adapter = contactAdapter

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getContacts()?.observe(this, Observer {
            contactAdapter.setContacts(it)
        })

        floatingActionButton.setOnClickListener{
            createContact()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun createContact(){
        val addIntent = Intent(this, CreateContactActivity::class.java)
        startActivity(addIntent)
    }

    private fun searchName(menu: Menu?){
        val item = menu?.findItem(R.id.search_name)

        val searchView = item?.actionView as androidx.appcompat.widget.SearchView?
        searchView?.isSubmitButtonEnabled = true

        searchView?.setOnQueryTextListener(
                object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(query != null){
                            getItemsFromDb(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if(newText != null){
                            getItemsFromDb(newText)
                        }
                        return true
                    }
                }
        )
    }

    private fun getItemsFromDb(searchText: String){
        var searchText = searchText
        searchText = "%$searchText%"

        contactViewModel.searchContact(searchText)?.observe(this, Observer {
            contactAdapter.setContacts(it)
        })
    }

    private fun showAlertMenu(contact: Contact) {
        val items = arrayOf("Show", "Update", "Delete")

        val builder = AlertDialog.Builder(this)
        val alert = AlertDialog.Builder(this)
        builder.setItems(items){ dialog, which ->

            when (which) {
                0 -> {
                    listDetails(alert, contact)
                }
                1 -> {
                    updateContact(contact)

                }
                2 -> {
                    alert.setTitle("Delete")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Delete"){dialog, _ ->
                                contactViewModel.deleteContact(contact)
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                }
            }
        }
        builder.show()
    }

    private fun listDetails(alert: AlertDialog.Builder, contact: Contact){
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.item_details, null)

        val name: TextView = dialogView.findViewById(R.id.name)
        val additionalContact: TextView = dialogView.findViewById(R.id.contact_content)
        val additionalAddress: TextView = dialogView.findViewById(R.id.address_content)

        name.text = contact.name
        additionalContact.text = contact.contact
        additionalAddress.text = contact.address

        alert.setView(dialogView)
                .setNeutralButton("OK"){dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    private fun updateContact(contact: Contact){
        val addIntent = Intent(this, UpdateContactActivity::class.java)
                .putExtra("EXTRA_LIST", contact)
                .putExtra(UpdateContactActivity.EXTRA_NAME_UPDATE, contact.name)
                .putExtra(UpdateContactActivity.EXTRA_CONTACT_UPDATE, contact.contact)
                .putExtra(UpdateContactActivity.EXTRA_ADDRESS_UPDATE, contact.address)

        startActivity(addIntent)
    }
}