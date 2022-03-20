package com.example.pokemonapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PokeDB";
    private static final String TABLE_FAVOURITES = "favourites";
    private static final String KEY_ID = "id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_FAVOURITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY"
                + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);

        // Create tables again
        onCreate(db);
    }

    public void deletefav(PokemonFavourites pokefav) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVOURITES, KEY_ID + " = ?",
                new String[] { String.valueOf(pokefav.getId()) });
        db.close();
    }

    public void addFav(PokemonFavourites pokefav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, pokefav.getId());

        // Inserting Row
        db.insert(TABLE_FAVOURITES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection

    }

    public Integer checkFav(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id FROM " + TABLE_FAVOURITES + " WHERE id = '" + id + "'", null);

       return c.getCount();

    }




}
