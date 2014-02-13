package com.beaubbe.whattoeat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.beaubbe.whattoeat.R;

import java.util.Date;

/**
 * Created by Gab on 08/02/14.
 */
public class MenuEntry extends DatabaseModel
{
    public static final String TABLE_NAME = "menu_entry";
    public static final String FIELD_ID = "id";
    public static final String FIELD_RECIPE_ID = "recipe_id";
    //stored as YYYY-MM-DD-HH-MM-SS
    public static final String FIELD_DATETIME = "datetime";
    public static final String FIELD_QUANTITY_MULTIPLIER = "quantity_multiplier";

    private long id;
    private Recipe recipe;
    private long datetime;
    private int quantityMultiplier;

    public MenuEntry(Context c)
    {
        super(c);
    }

    public int getQuantityMultiplier() {
        return quantityMultiplier;
    }

    public void setQuantityMultiplier(int quantityMultiplier) {
        this.quantityMultiplier = quantityMultiplier;
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

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean save(SQLiteDatabase db) {
        if(!validate())
            return false;

        ContentValues values = new ContentValues();
        values.put(FIELD_DATETIME, getDatetime());
        values.put(FIELD_QUANTITY_MULTIPLIER, getQuantityMultiplier());
        values.put(FIELD_RECIPE_ID, getRecipe().getId());

        if(getId()<=0)
        {
            setId(db.insert(TABLE_NAME, null, values));
            return getId() > 0;
        }
        else
        {
            values.put(FIELD_ID, getId());
            return db.update(TABLE_NAME, values, FIELD_ID+"="+getId(), null) > 0;
        }
    }

    @Override
    public boolean validate() {
        errors.clear();
        if(getRecipe().getId()<=0)
        {
            errors.add(new InputError(FIELD_RECIPE_ID, context.getString(R.string.error_recipe_id_null)));
        }
        if(getQuantityMultiplier()<=0)
        {
            errors.add(new InputError(FIELD_QUANTITY_MULTIPLIER, context.getString(R.string.error_quantity_must_be_positive)));
        }

        if(getDatetime()<=0)
        {
            errors.add(new InputError(FIELD_DATETIME, context.getString(R.string.error_date_should_not_be_null)));
        }

        return !hasErrors();
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db) {
        if(getId()>0)
            return db.delete(TABLE_NAME, FIELD_ID+"="+getId(), null)>0;
        return false;
    }

    public void setId(long id) {
        this.id = id;
    }
}
