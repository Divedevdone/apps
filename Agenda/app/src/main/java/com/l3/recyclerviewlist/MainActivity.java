package com.l3.recyclerviewlist;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editPhone;
    private ContactAdapter adapter;
    private ArrayList<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        Button btnAdd = findViewById(R.id.btnAdd);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        contactList = new ArrayList<>();
        adapter = new ContactAdapter(contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    contactList.add(new Contact(name, phone));
                    adapter.notifyItemInserted(contactList.size() - 1);
                    editName.setText("");
                    editPhone.setText("");
                }
            }
        });
    }
}