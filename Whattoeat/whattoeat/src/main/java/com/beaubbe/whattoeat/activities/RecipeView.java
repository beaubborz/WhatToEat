package com.beaubbe.whattoeat.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.fragments.OpenMenuToStartFragment;
import com.beaubbe.whattoeat.fragments.RecipeCreateFragment;
import com.beaubbe.whattoeat.fragments.RecipeListFragment;
import com.beaubbe.whattoeat.observers.RecipeListListener;

public class RecipeView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        getActionBar().hide();

        setupFragments();
    }

    private void setupFragments()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecipeListFragment list = new RecipeListFragment();
        list.setRecipeListListener(new RecipeListListener() {
            @Override
            public void onRecipeSelected(long recipe_id) {
                onRecipeAction(recipe_id);
            }
        });
        transaction.add(R.id.left_drawer, list);
        transaction.add(R.id.content_frame, new OpenMenuToStartFragment());
        transaction.commit();

    }

    //called when an item is selected from the menu.
    private void onRecipeAction(long recipe_id)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(recipe_id==0)
        {
            //new recipe!
            ft.replace(R.id.content_frame, new RecipeCreateFragment());
        }
        else
        {
            ft.replace(R.id.content_frame, new OpenMenuToStartFragment());
        }
        ft.commit();

        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawers();
    }
}