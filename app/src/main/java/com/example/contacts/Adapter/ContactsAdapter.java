package com.example.contacts.Adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.contacts.Database.Contact;
import com.example.contacts.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

   public List<Contact> contactList=new ArrayList<>();
   public ContactClickListener eventListener;
   public static boolean status=false;

    public ContactsAdapter(ContactClickListener eventListener){

        this.eventListener = eventListener;
    }

    public void addContact(Contact contact){
        contactList.add(0,contact);
        notifyItemInserted(0);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add_contacts(List<Contact> contact){
        this.contactList.addAll(contact);
        notifyDataSetChanged();
    }

    public void deleteContact(Contact contact){
        for(int i=0;i<contactList.size();i++){
            if(contactList.get(i).getId()==contact.getId()){
                contactList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void updateContact(Contact contact){
        for (int i=0;i<contactList.size();i++){
            if(contactList.get(i).getId()==contact.getId()){
                contactList.set(i,contact);
                notifyItemChanged(i);
            }
        }
    }
    public void searchContact(List<Contact> contacts){
        contactList=contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent,false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.bind_item(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


     public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView profile_name_rv;
        TextView full_name_rv;
        ImageView btn_delete,btn_edit;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_name_rv = itemView.findViewById(R.id.profile_name_rv);
            full_name_rv = itemView.findViewById(R.id.full_name_rv);
            btn_delete=itemView.findViewById(R.id.btn_delete);
            btn_edit=itemView.findViewById(R.id.btn_edit);

        }

        public void bind_item(Contact contact){
             full_name_rv.setText(contact.getFullname());
             String fullname=full_name_rv.getText().toString();
            profile_name_rv.setText(fullname.substring(0, 1));

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.OnClickDelete(contact);
                }
            });

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.OnClickEdit(contact);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.OnClickItem(contact);
                }
            });
        }

    }
    public interface ContactClickListener{
        void OnClickDelete(Contact contact);
        void OnClickEdit(Contact contact);
        void OnClickItem(Contact contact);
    }
}
