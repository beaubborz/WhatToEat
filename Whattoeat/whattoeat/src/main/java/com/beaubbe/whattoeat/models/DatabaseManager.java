package com.beaubbe.whattoeat.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gab on 06/02/14.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WhatToEatDB";


    public DatabaseManager(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        createTableRecipe(sqLiteDatabase);
        createTableIngredient(sqLiteDatabase);
        createTableStep(sqLiteDatabase);
    }

    private void createTableRecipe(SQLiteDatabase db)
    {
        final String sql = "CREATE TABLE "+Recipe.TABLE_NAME+"(" +
                Recipe.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Recipe.FIELD_NAME+" TEXT NOT NULL," +
                Recipe.FIELD_DURATION+" INTEGER DEFAULT 0," +
                Recipe.FIELD_PORTIONS+" INTEGER DEFAULT 1," +
                Recipe.FIELD_SCORE+" INTEGER DEFAULT 0," +
                Recipe.FIELD_VOTES+" INTEGER DEFAULT 0," +
                Recipe.FIELD_IMAGE_URL+" TEXT" +
                ")";

        db.execSQL(sql, null);
    }

    private void createTableIngredient(SQLiteDatabase db)
    {
        final String sql = "CREATE TABLE "+Ingredient.TABLE_NAME+"(" +
                Ingredient.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Ingredient.FIELD_RECIPE_ID+" INTEGER NOT NULL," +
                Ingredient.FIELD_NAME+" TEXT NOT NULL," +
                Ingredient.FIELD_QUANTITY+" REAL NOT NULL," +
                Ingredient.FIELD_UNIT_TYPE+" TEXT NOT NULL," +
                "FOREIGN KEY("+Ingredient.FIELD_RECIPE_ID+") REFERENCES "+Recipe.TABLE_NAME+"("+Recipe.FIELD_ID+")"+
                ")";

        db.execSQL(sql, null);
    }

    private void createTableStep(SQLiteDatabase db)
    {
        final String sql = "CREATE TABLE "+Step.TABLE_NAME+"(" +
                Step.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Step.FIELD_RECIPE_ID+" INTEGER NOT NULL," +
                Step.FIELD_ORDER+" INTEGER NOT NULL," +
                Step.FIELD_DESCRIPTION+" TEXT NOT NULL," +
                "FOREIGN KEY("+Step.FIELD_RECIPE_ID+") REFERENCES "+Recipe.TABLE_NAME+"("+Recipe.FIELD_ID+")"+
                ")";

        db.execSQL(sql, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int from, int to)
    {
        //TODO
    }
}
