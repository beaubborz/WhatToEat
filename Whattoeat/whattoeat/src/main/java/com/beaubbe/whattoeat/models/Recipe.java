package com.beaubbe.whattoeat.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

import com.beaubbe.whattoeat.R;

import java.util.ArrayList;
import java.util.List;

public class Recipe extends DatabaseModel
{
    public static final String TABLE_NAME = "recipe";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    long id;
    String name;
    List<RecipeIngredient> ingredients;
    List<Step> steps;

    public Recipe(Context c)
    {
        super(c);
        ingredients = new ArrayList<RecipeIngredient>();
        steps = new ArrayList<Step>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id){ this.id=id; };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    @Override
    public boolean save(SQLiteDatabase db) {
        //data is not valid, we dont save.
        if(!validate())
            return false;

        //prepare the data:
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, getName());

        db.beginTransaction();
        try{
            if(getId() <= 0)
            {
                //CREATE!!
                this.id = db.insert(TABLE_NAME, null, values);
            }
            else
            {
                //UPDATE!!
                db.update(
                        TABLE_NAME,
                        values,
                        FIELD_ID+"="+getId(),
                        null
                );
            }

            for(RecipeIngredient ingredient:ingredients)
            {
                ingredient.setRecipe(this);
                ingredient.save(db);
            }

            for(Step step:steps)
            {
                step.setRecipe(this);
                step.save(db);
            }

            //all good!!
            db.setTransactionSuccessful();
        }catch(SQLException ex)
        {
            //error while saving data, DO NOT COMMIT!
        }finally {
            db.endTransaction();
        }

        return true;
    }

    @Override
    public boolean validate() {
        boolean valid = true;

        if(name==null || name.length()==0)
        {
            errors.add(new InputError(FIELD_NAME, context.getString(R.string.error_name_cannot_be_blank)));
            valid=false;
        }

        for(RecipeIngredient i:ingredients)
        {
            valid&=i.validate();
        }

        for(Step s:steps)
        {
            valid&=s.validate();
        }

        return valid;
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db) {
        db.beginTransaction();
        boolean result = false;
        try
        {
            //remove recipeIngredients:
            for(RecipeIngredient ri:getIngredients())
            {
                ri.removeFromDatabase(db);
            }

            //remove steps
            for(Step s:getSteps())
            {
                s.removeFromDatabase(db);
            }

            result = db.delete(TABLE_NAME, FIELD_ID+"="+getId(), null)>0;

            db.setTransactionSuccessful();
        }catch (SQLException ex)
        {
            //error, we will revert.
        }
        finally {
            db.endTransaction();
        }
        return result;
    }

    public int getDuration() {
        int duration = 0;
        for(Step s:steps)
        {
            duration+=s.getDuration();
        }
        return duration;
    }

    public String getDurationAsText()
    {
        int duration = getDuration();
        if(duration>=60)
        {
            final int h = duration/3600;
            final int m = (duration%3600)/60;
            final int s = duration%60;

            return h+":"+((m<10?"0":"")+m)+":"+((s<10?"0":"")+s);
        }
        else
        {
            return duration+" seconds";
        }
    }


}
