package id.ac.unhas.mycontactsapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.mycontactsapp.R
import id.ac.unhas.mycontactsapp.contact.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactAdapter(private val context: Context?, private val listener: (Contact, Int) -> Unit) :
    RecyclerView.Adapter<ContactViewHolder>() {

    private var contacts = listOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_contact,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, contacts[position], listener)
        }
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(context: Context, contact: Contact, listener: (Contact, Int) -> Unit) {
        itemView.item_name.text = contact.name

        itemView.setOnClickListener {
            listener(contact, layoutPosition)
        }
    }

}