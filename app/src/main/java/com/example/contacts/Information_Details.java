package com.example.contacts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Information_Details extends Fragment {

    TextView name_t, phonenumber_t, email_t;
    ImageButton btn_call;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information__details, container, false);

        name_t = view.findViewById(R.id.name_contact);
        phonenumber_t = view.findViewById(R.id.phone_number);
        email_t = view.findViewById(R.id.email_address);
        btn_call = view.findViewById(R.id.btn_call);

        Bundle bundle = getArguments();
        if (bundle != null) {
            name_t.setText(bundle.getString("name"));
            phonenumber_t.setText(bundle.getString("phonenumber"));
            email_t.setText(bundle.getString("email"));
        }

        return view;
    }
}
