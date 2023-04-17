package com.example.contacts.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contacts.Contact;

import java.util.List;

@Dao
public interface DataDao {

    @Insert
    long addContact(Contact contact);

    @Delete
    int deleteContact(Contact contact);

    @Update
    int updateContact(Contact contact);

    @Query("SELECT * FROM contact_db")
    List<Contact>getContact();

    @Query("SELECT * FROM contact_db WHERE fullname like '%'|| :query ||'%'")
    List<Contact>searchContact(String query);
}
