package com.beaubbe.whattoeat.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.ModelFinder;
import com.beaubbe.whattoeat.models.Recipe;
import com.beaubbe.whattoeat.widgets.RecipeListItem;

import java.util.List;

public class ListRecipes extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void createRecipe(View view)
    {
        Intent i = new Intent(this, CreateRecipe.class);
        startActivity(i);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_recipes, container, false);

            List<Recipe> recipes = (new ModelFinder(rootView.getContext())).getAllRecipes();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            for(final Recipe r:recipes)
            {
                final RecipeListItem item = new RecipeListItem(r);
                item.setTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        {
                            item.setBackground(Color.CYAN);
                        }
                        else if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                        {
                            item.setBackground(Color.WHITE);
                            if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                            {
                                Intent intent = new Intent(getActivity(), ViewRecipe.class);
                                intent.putExtra("id", r.getId());
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                });
                transaction.add(R.id.recipe_list, item);
            }
            transaction.commit();

            return rootView;
        }
    }

}
