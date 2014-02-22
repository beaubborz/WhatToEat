package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 22/02/14.
 */
public class RecipeListItem extends TextView
{
    private long recipe_id;

    public RecipeListItem(Context context)
    {
        super(context);
    }
    public RecipeListItem(Context context, AttributeSet attr)
    {
        super(context, attr);
        recipe_id = attr.getAttributeIntValue(R.attr.recipeId, 0);
    }
    public RecipeListItem(Context context, AttributeSet attr, int styleDef)
    {
        super(context, attr, styleDef);
        recipe_id = attr.getAttributeIntValue(R.attr.recipeId, 0);
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }
}
