package com.beaubbe.whattoeat.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gab on 08/02/14.
 */
public class ModelFinder extends DatabaseModel
{
    Context context;

    public ModelFinder(Context c)
    {
        super(c);
        this.context=c;
    }

    public Recipe getRecipe(long id)
    {
        SQLiteDatabase db = getDatabase();

        final String recipe_query = "SELECT * FROM " +
                Recipe.TABLE_NAME+
                " WHERE "+
                Recipe.FIELD_ID+"="+id;

        Cursor recipeCursor = db.rawQuery(recipe_query, null);

        //empty set, stops here.
        if(recipeCursor.getCount()==0)
            return null;

        recipeCursor.moveToFirst();
        //get recipe
        final Recipe recipe = new Recipe(context);
        recipe.setId(recipeCursor.getLong(recipeCursor.getColumnIndex(Recipe.FIELD_ID)));
        recipe.setName(recipeCursor.getString(recipeCursor.getColumnIndex(Recipe.FIELD_NAME)));
        recipeCursor.close();

        final String ingredientSQLQuery = "SELECT "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_ID+", "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_QUANTITY+", "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_UNIT_TYPE+", "
                +Ingredient.TABLE_NAME+"."+Ingredient.FIELD_ID+", "
                +Ingredient.TABLE_NAME+"."+Ingredient.FIELD_NAME
                +" FROM " +
                RecipeIngredient.TABLE_NAME+", "+ Ingredient.TABLE_NAME +
                " WHERE " +
                RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_RECIPE_ID+"="+id+
                " AND "+
                RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_INGREDIENT_ID+" = "+Ingredient.TABLE_NAME+"."+Ingredient.FIELD_ID;

        Cursor ingredientCursor = db.rawQuery(ingredientSQLQuery, null);
        if(ingredientCursor.getCount() > 0)
        {
            ingredientCursor.moveToFirst();
            do
            {
                final RecipeIngredient ri = new RecipeIngredient(context);
                ri.setId(ingredientCursor.getLong(0));
                final Ingredient i = new Ingredient(context);

                ri.setRecipe(recipe);

                Quantity qty = new Quantity();
                qty.setAmount(ingredientCursor.getDouble(1));
                qty.setUnit(new Quantity.Unit(context, ingredientCursor.getInt(2)));
                ri.setQuantity(qty);

                i.setId(ingredientCursor.getLong(3));
                i.setName(ingredientCursor.getString(4));

                ri.setIngredient(i);

                recipe.addIngredient(ri);
            }while(ingredientCursor.moveToNext());
        }

        ingredientCursor.close();

        final String stepSQLQuery = "SELECT * FROM "+Step.TABLE_NAME+
                " WHERE "+
                Step.FIELD_RECIPE_ID+"="+id;

        Cursor stepCursor = db.rawQuery(stepSQLQuery, null);

        if(stepCursor.getCount() > 0)
        {
            stepCursor.moveToFirst();
            do
            {
                Step s = new Step(context);
                s.setId(stepCursor.getLong(stepCursor.getColumnIndex(Step.FIELD_ID)));

                s.setRecipe(recipe);

                s.setStep(stepCursor.getInt(stepCursor.getColumnIndex(Step.FIELD_STEP)));

                s.setDescription(stepCursor.getString(stepCursor.getColumnIndex(Step.FIELD_DESCRIPTION)));
                s.setDuration(stepCursor.getInt(stepCursor.getColumnIndex(Step.FIELD_DURATION)));

                recipe.addStep(s);
            }while(stepCursor.moveToNext());
        }
            stepCursor.close();
            closeDbConnexion();
            return recipe;

    }

    public List<Recipe> getAllRecipes()
    {
        HashMap<Long, Recipe> recipes = new HashMap<Long, Recipe>();
        SQLiteDatabase db = getDatabase();

        final String recipe_query = "SELECT * FROM " +
                Recipe.TABLE_NAME;

        Cursor recipeCursor = db.rawQuery(recipe_query, null);

        //empty set, stops here.
        if(recipeCursor.getCount()==0)
            return new ArrayList<Recipe>();

        recipeCursor.moveToFirst();

        //get recipes
        do
        {
            final Recipe recipe = new Recipe(context);
            recipe.setId(recipeCursor.getLong(recipeCursor.getColumnIndex(Recipe.FIELD_ID)));
            recipe.setName(recipeCursor.getString(recipeCursor.getColumnIndex(Recipe.FIELD_NAME)));
            recipes.put(recipe.getId(), recipe);

        }while(recipeCursor.moveToNext());

        recipeCursor.close();

        final String ingredientSQLQuery = "SELECT "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_ID+", "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_RECIPE_ID+", "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_QUANTITY+", "
                +RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_UNIT_TYPE+", "
                +Ingredient.TABLE_NAME+"."+Ingredient.FIELD_ID+", "
                +Ingredient.TABLE_NAME+"."+Ingredient.FIELD_NAME
                +" FROM " +
                RecipeIngredient.TABLE_NAME+", "+ Ingredient.TABLE_NAME +
                " WHERE " +
                RecipeIngredient.TABLE_NAME+"."+RecipeIngredient.FIELD_INGREDIENT_ID+" = "+Ingredient.TABLE_NAME+"."+Ingredient.FIELD_ID;

        Cursor ingredientCursor = db.rawQuery(ingredientSQLQuery, null);
        if(ingredientCursor.getCount() > 0)
        {
            ingredientCursor.moveToFirst();
            do
            {
                final RecipeIngredient ri = new RecipeIngredient(context);
                ri.setId(ingredientCursor.getLong(0));
                final Ingredient i = new Ingredient(context);

                final long recipe_id = ingredientCursor.getLong(1);

                final Recipe r = recipes.get(recipe_id);
                ri.setRecipe(r);

                Quantity qty = new Quantity();
                qty.setAmount(ingredientCursor.getDouble(2));
                qty.setUnit(new Quantity.Unit(context, ingredientCursor.getInt(3)));
                ri.setQuantity(qty);

                i.setId(ingredientCursor.getLong(4));
                i.setName(ingredientCursor.getString(5));

                ri.setIngredient(i);

                r.addIngredient(ri);
            }while(ingredientCursor.moveToNext());
        }

        ingredientCursor.close();

        final String stepSQLQuery = "SELECT * FROM "+Step.TABLE_NAME;

        Cursor stepCursor = db.rawQuery(stepSQLQuery, null);

        if(stepCursor.getCount() > 0)
        {
            stepCursor.moveToFirst();
            do
            {
                Step s = new Step(context);
                s.setId(stepCursor.getLong(stepCursor.getColumnIndex(Step.FIELD_ID)));

                final long recipe_id = stepCursor.getLong(stepCursor.getColumnIndex(Step.FIELD_RECIPE_ID));
                final Recipe r = recipes.get(recipe_id);
                s.setRecipe(r);

                s.setStep(stepCursor.getInt(stepCursor.getColumnIndex(Step.FIELD_STEP)));

                s.setDescription(stepCursor.getString(stepCursor.getColumnIndex(Step.FIELD_DESCRIPTION)));
                s.setDuration(stepCursor.getInt(stepCursor.getColumnIndex(Step.FIELD_DURATION)));

                r.addStep(s);
            }while(stepCursor.moveToNext());

            stepCursor.close();
            closeDbConnexion();
        }

        return new ArrayList<Recipe>(recipes.values());
    }


    @Override
    public boolean save(SQLiteDatabase db) {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean validate() {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db) {
        throw new NoSuchMethodError();
    }
}
