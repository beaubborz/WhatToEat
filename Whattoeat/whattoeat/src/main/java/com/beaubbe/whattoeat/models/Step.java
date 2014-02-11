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
public class Step extends DatabaseModel {

    public static final String TABLE_NAME = "recipe_steps";
    public static final String FIELD_ID = "id";
    public static final String FIELD_RECIPE_ID = "recipe_id";
    public static final String FIELD_STEP = "step";
    public static final String FIELD_DURATION = "duration";
    public static final String FIELD_DESCRIPTION = "description";

    long id;
    Recipe recipe;
    int step;
    int duration;
    String description;

    public Step(Context c)
    {
        super(c);
    }

    public long getId() {
        return id;
    }

    public void setId(long id){ this.id=id; }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean save(SQLiteDatabase db) {
        //data is not valid, we dont save.
        if(!validate())
            return false;

        //prepare the data:
        ContentValues values = new ContentValues();
        values.put(FIELD_RECIPE_ID, recipe.getId());
        values.put(FIELD_STEP, getStep());
        values.put(FIELD_DURATION, getDuration());
        values.put(FIELD_DESCRIPTION, getDescription());

        if(getId() == 0)
        {
            try{
            this.id = db.insertOrThrow(TABLE_NAME, null, values);
            }catch(SQLException ex)
            {
                Log.e("whattoeat", ex.toString());
                throw ex;
            }
        }
        else
        {
            db.update(TABLE_NAME, values,
                    FIELD_ID+"="+getId(),
                    null);
        }

        return true;
    }


    @Override
    public boolean validate() {
        boolean valid = true;

        if(recipe==null)
        {
            valid = false;
            errors.add(new InputError(FIELD_ID, context.getString(R.string.error_recipe_id_null)));
        }

        //make sure every step has an unique number.
        if(recipe!=null)
        {
            for(Step s:recipe.getSteps())
            {
                if(s!=this)
                {
                    if(s.getStep() == this.getStep())
                    {
                        valid=false;
                        errors.add(new InputError(FIELD_STEP, context.getString(R.string.error_duplicate_steps)));
                        break;
                    }
                }
            }
        }

        if(description==null || description.length()==0)
        {
            valid=false;
            errors.add(new InputError(FIELD_DESCRIPTION, context.getString(R.string.error_description_cannot_be_blank)));
        }


        return valid;
    }

    @Override
    public boolean removeFromDatabase(SQLiteDatabase db) {
        return db.delete(TABLE_NAME, FIELD_ID+"="+getId(), null)>0;
    }

    public String getDurationAsString() {
        final String[] units = context.getResources().getStringArray(R.array.time_units);
        if(duration>=3600)
        {
            return (duration/3600)+" "+units[0];
        }

        if(duration>=60)
        {
            return ((duration%3600)/60)+" "+units[1];
        }

        return duration+" "+units[2];
    }
}
