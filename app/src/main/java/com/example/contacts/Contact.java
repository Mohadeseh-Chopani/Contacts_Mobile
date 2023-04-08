package com.example.contacts;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String fullname,phonenumber,email;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.fullname);
        dest.writeString(this.phonenumber);
        dest.writeString(this.email);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.fullname = source.readString();
        this.phonenumber = source.readString();
        this.email = source.readString();
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.id = in.readLong();
        this.fullname = in.readString();
        this.phonenumber = in.readString();
        this.email = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
