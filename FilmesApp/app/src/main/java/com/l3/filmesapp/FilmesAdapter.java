package com.l3.filmesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.FilmesViewHolder> {

    private List<Filmes> listaFilmes;
    private Context context;

    public FilmesAdapter(List<Filmes> listaFilmes, Context context) {
        this.listaFilmes = listaFilmes;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.activity_list_item, parent, false);
        return new FilmesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmesViewHolder holder, int position) {
        Filmes filme = listaFilmes.get(position);
        holder.textView.setText(filme.getTitulo());

        // Aumentar o tamanho da fonte
        holder.textView.setTextSize(40);  // tamanho em SP

        // Deixar o texto em negrito
        holder.textView.setTypeface(null, Typeface.BOLD);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FilmesDetailActivity.class);
            intent.putExtra("filmeId", filme.getId());
            context.startActivity(intent);
        });
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Excluir Filme")
                    .setMessage("Tem certeza que deseja excluir \"" + filme.getTitulo() + "\"?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        FilmesDatabaseHelper dbHelper = new FilmesDatabaseHelper(context);
                        dbHelper.deletarFilme(filme.getId());

                        // Atualizar lista:
                        listaFilmes.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaFilmes.size());
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });


    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public static class FilmesViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public FilmesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
