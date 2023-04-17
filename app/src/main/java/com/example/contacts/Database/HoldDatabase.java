package com.example.contacts.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.contacts.Contact;

@Database(version = 1,exportSchema = true,entities = {Contact.class})
public abstract class HoldDatabase extends RoomDatabase {

    private static HoldDatabase holdDatabase;

   public static HoldDatabase getdata(Context context){
        if(holdDatabase==null) {
            holdDatabase= Room.databaseBuilder(context.getApplicationContext(),HoldDatabase.class,"contact_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return holdDatabase;
    }

    public abstract DataDao getDao();
    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}
