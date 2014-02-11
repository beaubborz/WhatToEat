package com.beaubbe.whattoeat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 07/02/14.
 */
public class RecipeIngredient extends DatabaseModel
{

    public static final String TABLE_NAME = "recipe_ingredients";

    public static final String FIELD_ID = "id";
    public static final String FIELD_RECIPE_ID = "recipe_id";
    public static final String FIELD_INGREDIENT_ID = "ingredient_id";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_UNIT_TYPE = "unit_type";

    long id;
    Ingredient ingredient;
    Quantity quantity;
    Recipe recipe;

    public RecipeIngredient(Context c)
    {
        super(c);
    }

    public long getId() {
        return id;
    }

    public void setId(long id){ this.id = id; }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean save(SQLiteDatabase db) {
        //data is not valid, we dont save.
        if(!validate() || !ingredient.validate())
            return false;

        ingredient.save(db);
        ContentValues values = new ContentValues();
        values.put(FIELD_INGREDIENT_ID, ingredient.getId());
        values.put(FIELD_RECIPE_ID, recipe.getId());
        values.put(FIELD_QUANTITY, quantity.getAmount());
        values.put(FIELD_UNIT_TYPE, quantity.getUnit().getType().ordinal());

        db.beginTransaction();
        try{
            if(getId()==0)
                this.id = db.insert(TABLE_NAME, null, values);
            else
                db.update(TABLE_NAME, values, FIELD_ID+"="+getId(),null);
            db.setTransactionSuccessful();
        }catch(SQLException ex)
        {
            //error while saving data.
        }finally{
            db.endTransaction();
        }

        return true;
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        if(ingredient == null)
        {
            errors.add(new InputError(FIELD_INGREDIENT_ID, context.getString(R.string.error_ingredient_id_null)));
            valid = false;
        }
        if(recipe == null)
        {
            errors.add(new InputError(FIELD_RECIPE_ID, context.getString(R.string.error_recipe_id_null)));
            valid = false;
        }

        return valid;
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db) {

        Ingredient i = getIngredient();

        boolean result = db.delete(TABLE_NAME, FIELD_ID+"="+getId(), null)>0;

        try
        {
            i.removeFromDatabase(db);
        }catch(SQLException ex)
        {
            //still used elsewhere, keep it then.
        }

        return result;
    }

    public Context getContext() {
        return context;
    }
}
