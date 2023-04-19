package com.example.contacts;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.contacts.Adapter.ContactsAdapter;
import com.example.contacts.Database.Contact;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class AddDialog extends DialogFragment {

     AddContactInterface addContactInterface;
     ContactsAdapter.ContactClickListener contactClickListener;
     ImageView btn_close,btn_add;
     TextInputLayout layout_name_et,layout_number_et;
     TextInputEditText editText_name,editText_number,editText_email;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addContactInterface=(AddContactInterface) context;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view =LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_add,null,false);
        builder.setView(view);

        btn_close= view.findViewById(R.id.btn_close_add);
        btn_add=view.findViewById(R.id.btn_add);
        layout_name_et=view.findViewById(R.id.layout_name_et_add);
        layout_number_et=view.findViewById(R.id.layout_number_et_add);
        editText_name=view.findViewById(R.id.edittext_name_add);
        editText_number=view.findViewById(R.id.edittext_number_add);
        editText_email=view.findViewById(R.id.edittext_email_add);

        btn_close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                builder.setMessage("Do you want to close?").setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setNegativeButton("No",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                androidx.appcompat.app.AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(editText_name.length()>0 && editText_number.length()>0){
                        Contact contact=new Contact();
                        contact.setFullname(Objects.requireNonNull(editText_name.getText()).toString());
                        contact.setPhonenumber(Objects.requireNonNull(editText_number.getText()).toString());
                        contact.setEmail(Objects.requireNonNull(editText_email.getText()).toString());
                        addContactInterface.Onnewcontact(contact);
                        dismiss();
                    }else {
                        if(editText_name.length()==0)
                            layout_name_et.setError("name is empty!");
                        if (editText_number.length()==0)
                            layout_number_et.setError("number is empty");
                    }
            }
        });

        return builder.create();
    }
    public interface AddContactInterface{
        void Onnewcontact(Contact contact);
    }
}