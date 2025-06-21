package com.l3.filmesapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddFilmeActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescricao;
    Button btnSalvar;
    FilmesDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);
        btnSalvar = findViewById(R.id.btnSalvar);

        db = new FilmesDatabaseHelper(this);

        btnSalvar.setOnClickListener(v -> {
            String titulo = edtTitulo.getText().toString();
            String descricao = edtDescricao.getText().toString();

            if (!titulo.isEmpty() && !descricao.isEmpty()) {
                db.adicionarFilme(titulo, descricao);
                finish();
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
