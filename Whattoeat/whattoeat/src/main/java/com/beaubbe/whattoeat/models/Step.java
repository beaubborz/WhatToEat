package com.beaubbe.whattoeat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 06/02/14.
 */
public class Step
{

    public static final String TABLE_NAME = "recipe_steps";
    public static final String FIELD_ID = "id";
    public static final String FIELD_RECIPE_ID = "recipe_id";
    public static final String FIELD_ORDER = "order";
    public static final String FIELD_DESCRIPTION = "description";

    long id;
    long recipe_id;
    int order;
    String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
