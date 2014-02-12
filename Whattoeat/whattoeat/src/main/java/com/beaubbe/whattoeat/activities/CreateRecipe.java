package com.beaubbe.whattoeat.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Ingredient;
import com.beaubbe.whattoeat.models.InputError;
import com.beaubbe.whattoeat.models.ModelFinder;
import com.beaubbe.whattoeat.models.Recipe;
import com.beaubbe.whattoeat.models.RecipeIngredient;
import com.beaubbe.whattoeat.models.Step;
import com.beaubbe.whattoeat.widgets.IngredientInput;
import com.beaubbe.whattoeat.widgets.StepInput;

import java.util.ArrayList;

public class CreateRecipe extends ActionBarActivity {

    ArrayList<IngredientInput> ingredientWidgets;
    ArrayList<StepInput> stepWidgets;
    Recipe storedRecipe;

    public Recipe getStoredRecipe()
    {
        return storedRecipe;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        final long id = getIntent().getLongExtra("id", 0);
        if(id>0)
        {
            storedRecipe = new ModelFinder(this).getRecipe(id);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        ingredientWidgets = new ArrayList<IngredientInput>();
        stepWidgets = new ArrayList<StepInput>();
    }

    public void addIngredient(RecipeIngredient recipeIngredient)
    {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction ft = fragmentManager.beginTransaction();

        IngredientInput ii;
        if(recipeIngredient==null)
            ii = new IngredientInput(this);
        else
            ii = new IngredientInput(this, recipeIngredient);

        ft.add(R.id.ingredient_list, ii);
        ft.commit();
        this.ingredientWidgets.add(ii);
    }

    public void actionAddIngredient(View v)
    {
        addIngredient(null);
    }

    public void actionAddStep(View v)
    {
        addStep(null);
    }

    public void addStep(Step step)
    {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction ft = fragmentManager.beginTransaction();

        StepInput si;
        if(step == null)
            si = new StepInput(this, stepWidgets.size()+1);
        else
            si = new StepInput(step);

        ft.add(R.id.step_list, si);
        ft.commit();
        stepWidgets.add(si);

    }

    public void saveRecipe(View v)
    {
        Recipe recipe;
        if(storedRecipe == null)
            recipe = new Recipe(this);
        else
            recipe = storedRecipe;

        recipe.setName(((EditText)findViewById(R.id.recipe_name)).getText().toString());

        recipe.validate();

        TextView errors = (TextView) findViewById(R.id.errors);
        errors.setText("Error!");
        if(recipe.hasErrors())
        {
            for(InputError error:recipe.getErrors())
            {
                errors.append("\n"+error.error);
            }
            errors.setVisibility(View.VISIBLE);
            return;
        }
        else
        {
            errors.setVisibility(View.GONE);
        }

        //set ingredients:
        for(IngredientInput ii:ingredientWidgets)
        {
            RecipeIngredient ri = ii.getRecipeIngredient();

            if(ri==null)
                continue;

            ri.setRecipe(recipe);
            Ingredient i = ri.getIngredient();
            i.validate();

            if(i.hasErrors())
            {
                for(InputError error:i.getErrors())
                {
                    errors.append("\n"+error.error);
                }
                errors.setVisibility(View.VISIBLE);
                return;
            }

            ri.validate();

            if(ri.hasErrors())
            {
                for(InputError error:ri.getErrors())
                {
                    errors.append("\n"+error.error);
                }
                errors.setVisibility(View.VISIBLE);
                return;
            }

            recipe.addIngredient(ri);
        }

        //add step
        for(StepInput si:stepWidgets)
        {
            Step s = si.getStep();

            if(s==null)
                continue;

            s.setRecipe(recipe);

            s.validate();

            if(s.hasErrors())
            {
                for(InputError error:s.getErrors())
                {
                    errors.append("\n"+error.error);
                }
                errors.setVisibility(View.VISIBLE);
                return;
            }
            recipe.addStep(s);
        }

        recipe.save();

        //return to the recipe list.
        Intent intent = new Intent(this, ListRecipes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_create_recipe, container, false);

            CreateRecipe thisActivity = ((CreateRecipe)getActivity());
            Recipe storedRecipe = thisActivity.getStoredRecipe();
            if(storedRecipe != null)
            {
                ((EditText) rootView.findViewById(R.id.recipe_name)).setText(storedRecipe.getName());

                for(RecipeIngredient ri:storedRecipe.getIngredients())
                {
                    thisActivity.addIngredient(ri);
                }

                for(Step s:storedRecipe.getSteps())
                {
                    thisActivity.addStep(s);
                }
            }

            return rootView;
        }
    }

}
