package com.l3.filmesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FilmesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "filmes.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "filmes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRICAO = "descricao";

    public FilmesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT, " +
                COLUMN_DESCRICAO + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void adicionarFilme(String titulo, String descricao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_DESCRICAO, descricao);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Filmes> getTodosFilmes() {
        List<Filmes> listaFilmes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                listaFilmes.add(new Filmes(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaFilmes;
    }

    public Filmes getFilmePorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Filmes filme = new Filmes(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO))
            );
            cursor.close();
            return filme;
        }
        return null;
    }
    public void deletarFilme(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }


}
