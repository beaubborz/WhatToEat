package com.beaubbe.whattoeat.widgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Recipe;

/**
 * Created by Gab on 08/02/14.
 */
public class RecipeListItem extends Fragment
{
    private Recipe recipe;
    private View.OnTouchListener touchListener;
    private View root;

    public RecipeListItem(Recipe recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.recipe_listitem_widget, container, false);

        ((TextView)root.findViewById(R.id.recipe_name)).setText(recipe.getName());
        ((TextView)root.findViewById(R.id.duration)).setText(recipe.getDurationAsText());

        root.setOnTouchListener(new TouchListener());
        return root;
    }

    public void setTouchListener(View.OnTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public void setBackground(int background) {
        root.setBackgroundColor(background);
    }

    private class TouchListener implements View.OnTouchListener
    {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(touchListener!=null)
                touchListener.onTouch(view, motionEvent);
            return true;
        }
    }
}
