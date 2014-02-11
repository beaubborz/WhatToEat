package com.beaubbe.whattoeat.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beaubbe.whattoeat.models.Ingredient;
import com.beaubbe.whattoeat.models.Recipe;
import com.beaubbe.whattoeat.models.Step;

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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create recipe table
        final String recipeSQL = "CREATE TABLE "+ Recipe.TABLE_NAME+"(" +
                Recipe.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Recipe.FIELD_NAME+" TEXT NOT NULL" +
                ")";

        final String ingredientSQL = "CREATE TABLE "+ Ingredient.TABLE_NAME+"(" +
                Ingredient.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Ingredient.FIELD_NAME+" TEXT UNIQUE NOT NULL" +
                ")";


        //fais le lien entre les ingredients de chaque recette avec sa quantite.
        final String recipe_ingredientSQL = "CREATE TABLE "+RecipeIngredient.TABLE_NAME+"(" +
                RecipeIngredient.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeIngredient.FIELD_RECIPE_ID+" INTEGER NOT NULL," +
                RecipeIngredient.FIELD_INGREDIENT_ID+" INTEGER NOT NULL," +
                RecipeIngredient.FIELD_QUANTITY+" REAL,"+
                RecipeIngredient.FIELD_UNIT_TYPE+" INTEGER,"+
                "FOREIGN KEY ("+RecipeIngredient.FIELD_RECIPE_ID+") REFERENCES "+Recipe.TABLE_NAME+"("+Recipe.FIELD_ID+"),"+
                "FOREIGN KEY ("+RecipeIngredient.FIELD_INGREDIENT_ID+") REFERENCES "+Ingredient.TABLE_NAME+"("+Ingredient.FIELD_ID+")"+
                ")";

        final String menu_SQL = "CREATE TABLE "+Menu.TABLE_NAME+"(" +
                Menu.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Menu.FIELD_RECIPE_ID+" INTEGER NOT NULL," +
                Menu.FIELD_DATETIME+" TEXT NOT NULL," +
                Menu.FIELD_QUANTITY_MULTIPLIER+" REAL," +
                "FOREIGN KEY ("+Menu.FIELD_RECIPE_ID+") REFERENCES "+Recipe.TABLE_NAME+"("+Recipe.FIELD_ID+")" +
                ")";

        //fais le lien entre les ingredients de chaque recette avec sa quantite.
        final String recipe_stepsSQL = "CREATE TABLE "+ Step.TABLE_NAME+"(" +
                Step.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Step.FIELD_RECIPE_ID+" INTEGER NOT NULL," +
                Step.FIELD_STEP+" INTEGER NOT NULL," +
                Step.FIELD_DURATION+" INTEGER," +
                Step.FIELD_DESCRIPTION+" TEXT,"+
                "FOREIGN KEY ("+Step.FIELD_RECIPE_ID+") REFERENCES "+Recipe.TABLE_NAME+"("+Recipe.FIELD_ID+")"+
                ")";

        //on cree les tables!!
        sqLiteDatabase.execSQL(recipeSQL);
        sqLiteDatabase.execSQL(ingredientSQL);
        sqLiteDatabase.execSQL(recipe_ingredientSQL);
        sqLiteDatabase.execSQL(recipe_stepsSQL);
        sqLiteDatabase.execSQL(menu_SQL);

        //on active les FK
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
