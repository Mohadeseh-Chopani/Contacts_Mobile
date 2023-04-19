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

import com.example.contacts.Database.Contact;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class EditDialog extends DialogFragment {


     EditContactInterface editContactInterface;
     ImageView btn_close,btn_edit;
     TextInputLayout layout_name_et,layout_number_et;
     TextInputEditText editText_name,editText_number,editText_email;

     Contact contact;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        editContactInterface=(EditContactInterface) context;
        if (getArguments() != null) {
            contact=getArguments().getParcelable("contact");
        }
        if(contact==null)
            dismiss();
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        View view =LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_edit,null,false);

        builder.setView(view);

        btn_close= view.findViewById(R.id.btn_close_edit);
        btn_edit=view.findViewById(R.id.btn_edit);
        layout_name_et=view.findViewById(R.id.layout_name_et_edit);
        layout_number_et=view.findViewById(R.id.layout_number_et_edit);
        editText_name=view.findViewById(R.id.edittext_name_edit);
        editText_number=view.findViewById(R.id.edittext_number_edit);
        editText_email=view.findViewById(R.id.edittext_email_edit);

        editText_name.setText(contact.getFullname());
        editText_number.setText(contact.getPhonenumber());
        editText_email.setText(contact.getEmail());

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

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(editText_name.length()>0 && editText_number.length()>0){
                        contact.setFullname(Objects.requireNonNull(editText_name.getText()).toString());
                        contact.setPhonenumber(Objects.requireNonNull(editText_number.getText()).toString());
                        contact.setEmail(Objects.requireNonNull(editText_email.getText()).toString());
                        editContactInterface.OnEditcontact(contact);
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

    public interface EditContactInterface{
        void OnEditcontact(Contact contact);
    }
}