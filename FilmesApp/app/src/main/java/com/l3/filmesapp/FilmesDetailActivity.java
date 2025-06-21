package com.l3.filmesapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FilmesDetailActivity extends AppCompatActivity {

    TextView txtTitulo, txtDescricao;
    FilmesDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes_detail);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);

        db = new FilmesDatabaseHelper(this);

        int filmeId = getIntent().getIntExtra("filmeId", -1);
        if (filmeId != -1) {
            Filmes filme = db.getFilmePorId(filmeId);
            if (filme != null) {
                txtTitulo.setText(filme.getTitulo());
                txtDescricao.setText(filme.getDescricao());
            }
        }
    }
}
