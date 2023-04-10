package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.contacts.Adapter.ContactsAdapter;
import com.example.contacts.SQLite.SQLiteHelper;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddDialog.AddContactInterface, ContactsAdapter.ContactClickListener, EditDialog.EditContactInterface {

    RecyclerView recyclerView;
    ImageView btn_close_page_add,btn_add_page_add,img_profile_page_add;
    EditText name_et,phonenumber_et,email_et,editText_search;
    AppBarLayout appBarLayout;
    ContactsAdapter adapter;
    SQLiteHelper sqLiteHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_search=findViewById(R.id.edit_search);
        btn_close_page_add=findViewById(R.id.btn_close);
        btn_add_page_add=findViewById(R.id.btn_add);
        img_profile_page_add=findViewById(R.id.img_profile);
        recyclerView=findViewById(R.id.recyclerview);
        name_et=findViewById(R.id.edittext_name);
        phonenumber_et=findViewById(R.id.edittext_number);
        email_et=findViewById(R.id.edittext_email);
        appBarLayout=findViewById(R.id.appbar_layout);



        editText_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    List<Contact>contacts=sqLiteHelper.searchContact(s.toString());
                    adapter.searchContact(contacts);
                }else {
                    List<Contact>contacts=sqLiteHelper.getContact();
                    adapter.searchContact(contacts);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new ContactsAdapter(this);
        recyclerView.setAdapter(adapter);

        sqLiteHelper=new SQLiteHelper(this);
        List<Contact> contactList=sqLiteHelper.getContact();
        adapter.add_contacts(contactList);

        // when we click on add button then show dialog add contact
        AddDialog dialog_add=new AddDialog();
        View addActionButton=findViewById(R.id.add_action_button);
        addActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog_add.show(getSupportFragmentManager(),null);
            }
        });
    }


    @Override
    public void Onnewcontact(Contact contact) {
        long contactId= sqLiteHelper.addContact(contact);
        if(contactId!=0){
            contact.setId(contactId);
            adapter.addContact(contact);
        }
    }

    @Override
    public void OnClickDelete(Contact contact) {
        int result = sqLiteHelper.deleteContact(contact);
        if (result > 0) {
            adapter.deleteContact(contact);
        }
    }

    @Override
    public void OnClickEdit(Contact contact) {
        EditDialog editDialog=new EditDialog();
        Bundle bundle=new Bundle();
        bundle.putParcelable("contact",contact);
        editDialog.setArguments(bundle);
        editDialog.show(getSupportFragmentManager(),null);
    }

    @Override
    public void OnClickItem(Contact contact) {
        Bundle bundle=new Bundle();
        bundle.putString("name",contact.getFullname());
        bundle.putString("phonenumber",contact.getPhonenumber());
        bundle.putString("email",contact.getEmail());

        Information_Details  information_details=new Information_Details();
        information_details.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_page, information_details);
        transaction.commit();

        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void Oneditcontact(Contact contact) {
        int result = sqLiteHelper.updateContact(contact);
        if(result > 0) {
            adapter.updateContact(contact);
        }
    }
}