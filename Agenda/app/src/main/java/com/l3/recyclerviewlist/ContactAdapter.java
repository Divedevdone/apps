package com.l3.recyclerviewlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, phoneTextView;

        public ContactViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.textName);
            phoneTextView = view.findViewById(R.id.textPhone);
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}