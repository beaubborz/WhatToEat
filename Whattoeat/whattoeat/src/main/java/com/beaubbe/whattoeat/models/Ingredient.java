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
public class Ingredient extends DatabaseModel
{
    public static final String TABLE_NAME = "ingredient";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient(Context c)
    {
        super(c);
    }

    @Override
    public boolean save(SQLiteDatabase db) {
        //data is not valid, we dont save.
        if(!validate())
            return false;

        //prepare the data:
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, name);

        if(getId() == 0)
        {
            this.id = db.insert(TABLE_NAME, null, values);
        }
        else
        {
            values.put(FIELD_ID, getId());
            db.update(TABLE_NAME, values, FIELD_ID+"="+getId(), null);
        }

        return true;
    }

    @Override
    public boolean validate() {
        boolean valid = name!=null && name.length()>0;
        errors.clear();

        if(!valid)
        {
            errors.add(new InputError(FIELD_NAME,context.getString(R.string.error_name_cannot_be_blank)));
        }

        return valid;
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db)
    {
        final String recipeIngredients = "SELECT * FROM "+RecipeIngredient.TABLE_NAME+" WHERE"+
                RecipeIngredient.FIELD_INGREDIENT_ID+"="+getId();

        Cursor c = db.rawQuery(recipeIngredients, null);

        if(c.getCount()>0)
            throw new SQLException("This item is still used by some RecipeIngredients!");

        return db.delete(TABLE_NAME, Ingredient.FIELD_ID+"="+getId(), null)>0;
    }

    @Override
    public String toString() {
        return getName();
    }
}
