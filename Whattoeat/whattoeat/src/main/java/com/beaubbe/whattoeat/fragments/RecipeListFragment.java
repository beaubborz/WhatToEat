package com.beaubbe.whattoeat.fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.etc.ViewsHelper;
import com.beaubbe.whattoeat.observers.RecipeListListener;
import com.beaubbe.whattoeat.widgets.RecipeListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gab on 20/02/14.
 */
public class RecipeListFragment extends Fragment {
    private RecipeListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        //adds listener to "create recipe" button.
        root.findViewById(R.id.item_new_recipe).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(view instanceof RecipeListItem)
                    onItemSelected((RecipeListItem)view, motionEvent);
                return true;
            }
        });
        return root;
    }

    private void onItemSelected(RecipeListItem item, MotionEvent motionEvent)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            setSelectedItem(item.getRecipe_id());
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP)
        {
            listener.onRecipeSelected(item.getRecipe_id());
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
        {
            //reset les selections.
            setSelectedItem(-1);
        }
    }

    public void setRecipeListListener(RecipeListListener listener)
    {
        this.listener = listener;
    }

    public void setSelectedItem(long recipe_id)
    {

        final TypedArray defaultStyle = getView().getContext().obtainStyledAttributes(R.style.recipe_list_item,
                new int[]{android.R.attr.textColor, android.R.attr.background});

        final TypedArray selectedStyle = getView().getContext().obtainStyledAttributes(R.style.recipe_list_item_selected,
                new int[]{android.R.attr.textColor, android.R.attr.background});

        //get all views to restyle:
        //change styles from the `my recipes` list.
        final LinearLayout recipe_list = ((LinearLayout)getView().findViewById(R.id.list_my_recipes));

        ArrayList<View> views = new ArrayList<View>();
        views.addAll(ViewsHelper.getDirectChildren(recipe_list));
        views.add(getView().findViewById(R.id.item_new_recipe));


        for(View child:views)
        {
            if(child instanceof RecipeListItem)
            {
                if(((RecipeListItem)child).getRecipe_id() == recipe_id)
                {
                    child.setBackgroundColor(selectedStyle.getColor(1, Color.RED));
                    ((RecipeListItem) child).setTextColor(selectedStyle.getColor(0, Color.RED));
                }
                else
                {
                    child.setBackgroundColor(defaultStyle.getColor(1, Color.RED));
                    ((RecipeListItem) child).setTextColor(defaultStyle.getColor(0, Color.RED));
                }
            }
        }
    }
}
