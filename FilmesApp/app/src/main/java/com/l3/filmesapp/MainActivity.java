package com.l3.filmesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FilmesAdapter adapter;
    List<Filmes> listaFilmes;
    FilmesDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewFilmes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new FilmesDatabaseHelper(this);

        findViewById(R.id.btnAddFilme).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddFilmeActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaFilmes = db.getTodosFilmes();
        adapter = new FilmesAdapter(listaFilmes, this);
        recyclerView.setAdapter(adapter);
    }
}
