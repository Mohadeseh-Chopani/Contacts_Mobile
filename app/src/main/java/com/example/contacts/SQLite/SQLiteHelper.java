package com.example.contacts.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contacts.Contact;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    String name_tb="contact_db";
    public static final String TAG="SQLiteHelper";
    public SQLiteHelper(@Nullable Context context) {
        super(context,"contact_db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + name_tb +"(id INTEGER PRIMARY KEY AUTOINCREMENT , fullname TEXT NOT NULL,phonenumber TEXT NOT NULL,email TEXT);");
        }catch (SQLiteException e){
            Log.e(TAG, "onCreate: "+e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addContact(Contact contact){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("fullname",contact.getFullname());
        contentValues.put("phonenumber",contact.getPhonenumber());
        contentValues.put("email",contact.getEmail());

        long result=sqLiteDatabase.insert(name_tb,null,contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<Contact> getContact(){

        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT* FROM "+name_tb,null);
        List<Contact>contacts=new ArrayList<Contact>();

        if(cursor.moveToFirst()){
            do {
                Contact contact=new Contact();
                contact.setId(cursor.getLong(0));
                contact.setFullname(cursor.getString(1));
                contact.setPhonenumber(cursor.getString(2));
                contact.setEmail(cursor.getString(3));

                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        return contacts;
    }

    public int deleteContact(Contact  contact){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        int result=sqLiteDatabase.delete(name_tb,"id=?",new String[] {String.valueOf(contact.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("fullname",contact.getFullname());
        contentValues.put("phonenumber",contact.getPhonenumber());
        contentValues.put("email",contact.getEmail());

        int result=sqLiteDatabase.update(name_tb,contentValues,"id=?",new String[] {String.valueOf(contact.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public List<Contact> searchContact(String query){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT* FROM "+name_tb+" WHERE fullname LIKE '%"+query+"%'",null);

        List<Contact>contacts=new ArrayList<Contact>();

        if(cursor.moveToFirst()){
            do {
                Contact contact=new Contact();
                contact.setId(cursor.getLong(0));
                contact.setFullname(cursor.getString(1));
                contact.setPhonenumber(cursor.getString(2));
                contact.setEmail(cursor.getString(3));

                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        return contacts;
    }

}

