package com.beaubbe.whattoeat.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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

    public List<MenuEntry> getMenuEntriesForDate(long date)
    {
        ArrayList<MenuEntry> entries = new ArrayList<MenuEntry>();

        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(date);

        final GregorianCalendar startOfDay = new GregorianCalendar();
        startOfDay.set(GregorianCalendar.YEAR, cal.get(GregorianCalendar.YEAR));
        startOfDay.set(GregorianCalendar.MONTH, cal.get(GregorianCalendar.MONTH));
        startOfDay.set(GregorianCalendar.DATE, cal.get(GregorianCalendar.DATE));
        startOfDay.set(GregorianCalendar.HOUR,0);
        startOfDay.set(GregorianCalendar.MINUTE,0);
        startOfDay.set(GregorianCalendar.SECOND,0);

        final GregorianCalendar endOfDay = new GregorianCalendar();
        endOfDay.set(GregorianCalendar.YEAR, cal.get(GregorianCalendar.YEAR));
        endOfDay.set(GregorianCalendar.MONTH, cal.get(GregorianCalendar.MONTH));
        endOfDay.set(GregorianCalendar.DATE, cal.get(GregorianCalendar.DATE));
        endOfDay.set(GregorianCalendar.HOUR,23);
        endOfDay.set(GregorianCalendar.MINUTE,59);
        endOfDay.set(GregorianCalendar.SECOND,59);

        SQLiteDatabase db = getDatabase();
        final String sqlQuery = "SELECT * FROM "+MenuEntry.TABLE_NAME+" WHERE "+MenuEntry.FIELD_DATETIME+
                " BETWEEN "+
                 startOfDay.getTimeInMillis()+
                " AND "+
                endOfDay.getTimeInMillis();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        for(int i=0;i<cursor.getCount();i++)
        {
            final MenuEntry me = new MenuEntry(context);
            cursor.moveToPosition(i);
            me.setId(cursor.getLong(cursor.getColumnIndex(MenuEntry.FIELD_ID)));
            Recipe r = new Recipe(context);
            r.setId(cursor.getLong(cursor.getColumnIndex(MenuEntry.FIELD_RECIPE_ID)));
            me.setRecipe(r);
            me.setDatetime(cursor.getLong(cursor.getColumnIndex(MenuEntry.FIELD_DATETIME)));
            me.setQuantityMultiplier(cursor.getInt(cursor.getColumnIndex(MenuEntry.FIELD_QUANTITY_MULTIPLIER)));
            entries.add(me);
        }
        cursor.close();
        db.close();
        closeDbConnexion();

        for(MenuEntry me:entries)
        {
            final Recipe r = this.getRecipe(me.getRecipe().getId());
            me.setRecipe(r);
        }

        return entries;
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
        {
            closeDbConnexion();
            return new ArrayList<Recipe>();
        }

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


    public List<Ingredient> getIngredients()
    {
        SQLiteDatabase db = getDatabase();
        final String query = "SELECT * FROM "+Ingredient.TABLE_NAME;
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() == 0)
        {
            closeDbConnexion();
            return ingredients;
        }

        cursor.moveToFirst();

        do
        {
            final Ingredient i = new Ingredient(context);

            i.setId(cursor.getLong(0));
            i.setName(cursor.getString(1));

            ingredients.add(i);

        }while(cursor.moveToNext());


        closeDbConnexion();

        return ingredients;
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

    public Ingredient getIngredientByName(String ingredientName) {
        Ingredient ingredient=null;
        final String query = "SELECT * FROM "+Ingredient.TABLE_NAME+" WHERE " +
                "UPPER("+Ingredient.FIELD_NAME+") LIKE ?";

        SQLiteDatabase db = getDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{ingredientName.toUpperCase()});

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            ingredient = new Ingredient(context);
            ingredient.setId(cursor.getLong(0));
            ingredient.setName(cursor.getString(1));
        }

        db.close();
        closeDbConnexion();
        return ingredient;
    }
}
