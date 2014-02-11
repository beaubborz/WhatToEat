package com.beaubbe.whattoeat.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created by Gab on 08/02/14.
 */
public class Menu extends DatabaseModel
{
    public static final String TABLE_NAME = "menu";
    public static final String FIELD_ID = "id";
    public static final String FIELD_RECIPE_ID = "recipe_id";
    //stored as YYYY-MM-DD-HH-MM-SS
    public static final String FIELD_DATETIME = "datetime";
    public static final String FIELD_QUANTITY_MULTIPLIER = "quantity_multiplier";

    private long id;
    private Recipe recipe;
    private Date datetime;

    public Menu(Context c)
    {
        super(c);
    }

    public long getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean save(SQLiteDatabase db) {
        return false;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db) {
        return false;
    }
}
