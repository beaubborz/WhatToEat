package com.beaubbe.whattoeat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 06/02/14.
 */
public class Ingredient
{
    public static final String TABLE_NAME = "ingredient";
    public static final String FIELD_ID = "id";
    public static final String FIELD_RECIPE_ID = "recipe_id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_UNIT_TYPE = "unit_type";

    //DATABASE VALUES
    private long id;
    private long recipe_id;
    private String name;
    private double quantity;
    private String unit_type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }

    @Override
    public String toString() {
        return getName();
    }
}
