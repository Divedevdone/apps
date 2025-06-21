package com.l3.filmesapp;

public class Filmes {
    private int id;
    private String titulo;
    private String descricao;

    public Filmes(int id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
