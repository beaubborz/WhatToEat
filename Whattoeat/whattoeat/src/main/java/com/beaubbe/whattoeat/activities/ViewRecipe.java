package com.beaubbe.whattoeat.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.ModelFinder;
import com.beaubbe.whattoeat.models.Recipe;
import com.beaubbe.whattoeat.models.RecipeIngredient;
import com.beaubbe.whattoeat.models.Step;
import com.beaubbe.whattoeat.widgets.IngredientView;
import com.beaubbe.whattoeat.widgets.StepView;

public class ViewRecipe extends FragmentActivity {
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        final long id = getIntent().getLongExtra("id", 0);
        recipe = new ModelFinder(this).getRecipe(id);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(recipe))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.start_cooking:

            return true;

            case R.id.edit:
                Intent intent = new Intent(this, CreateRecipe.class);
                intent.putExtra("id", recipe.getId());
                startActivity(intent);
            return true;

            case R.id.remove:
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Alert!");
                dialog.setMessage("Are you sure that you want to remove this recipe?");
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete
                        dialog.hide();
                        recipe.removeFromDatabase();
                        Intent intent = new Intent(getApplicationContext(), ListRecipes.class);
                        startActivity(intent);
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.hide();
                    }
                });
                dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Recipe recipe;

        public PlaceholderFragment(Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_recipe, container, false);

            ((TextView) rootView.findViewById(R.id.recipeTitle)).setText(recipe.getName());

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            for(RecipeIngredient ri:recipe.getIngredients())
                ft.add(R.id.ingredient_list, new IngredientView(ri));

            int actualStepNumber=1;
            for(Step s:recipe.getSteps())
                ft.add(R.id.step_list, new StepView(s, actualStepNumber++));

            ft.commit();

            return rootView;
        }
    }

}
