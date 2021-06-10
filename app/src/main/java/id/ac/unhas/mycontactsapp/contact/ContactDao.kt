package id.ac.unhas.mycontactsapp.contact

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("Select * from contact")
    fun getContacts(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Query("SELECT * FROM contact WHERE name LIKE :name")
    fun searchContact(name: String): LiveData<List<Contact>>

}