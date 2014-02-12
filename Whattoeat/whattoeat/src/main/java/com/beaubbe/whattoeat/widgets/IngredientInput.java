package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Ingredient;
import com.beaubbe.whattoeat.models.ModelFinder;
import com.beaubbe.whattoeat.models.Quantity;
import com.beaubbe.whattoeat.models.RecipeIngredient;

import java.util.List;

/**
 * Created by Gab on 06/02/14.
 */
public class IngredientInput extends Fragment
{
    RecipeIngredient recipeIngredient;
    Context context;

    View root;

    public IngredientInput()
    {

    }

    public IngredientInput(Context c)
    {
        context = c;
        recipeIngredient = new RecipeIngredient(c);
    }

    public IngredientInput(Context c, RecipeIngredient recipeIngredient)
    {
        context = c;
        this.recipeIngredient=recipeIngredient;
    }

    public void setRecipeIngredient(RecipeIngredient recipeIngredient)
    {
        this.recipeIngredient = recipeIngredient;
    }

    public RecipeIngredient getRecipeIngredient()
    {
        final String ingredientName = ((EditText)root.findViewById(R.id.ingredient_name)).getText().toString();
        final String quantityString = ((EditText) root.findViewById(R.id.qty)).getText().toString();

        //empty field
        if(ingredientName.length()==0 && quantityString.length()==0)
            return null;

        Ingredient i = new ModelFinder(context).getIngredientByName(ingredientName);

        if(i==null)
        {
            i = new Ingredient(context);
            i.setName(ingredientName);
        }



        recipeIngredient.setIngredient(i);

        Quantity qty = recipeIngredient.getQuantity();

        if(qty == null)
        {
            qty = new Quantity();
        }

        qty.setAmount(Double.parseDouble(quantityString.length()>0?quantityString:"0"));
        qty.setUnit((Quantity.Unit)((Spinner)root.findViewById(R.id.unit)).getSelectedItem());

        recipeIngredient.setQuantity(qty);

        return recipeIngredient;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ingredient_input_widget, container, false);

        AutoCompleteTextView name = (AutoCompleteTextView)root.findViewById(R.id.ingredient_name);
        name.setAdapter(new ArrayAdapter<Ingredient>(context, android.R.layout.simple_dropdown_item_1line, new ModelFinder(context).getIngredients()));

        //set name, (for use as update element)
        if(recipeIngredient.getIngredient()!=null)
            name.setText(recipeIngredient.getIngredient().getName());

        EditText quantity = (EditText) root.findViewById(R.id.qty);

        Spinner units = (Spinner)root.findViewById(R.id.unit);
        List<Quantity.Unit> unit_types = Quantity.Unit.getAsList(root.getContext());
        units.setAdapter(new ArrayAdapter<Quantity.Unit>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, unit_types));

        if(recipeIngredient.getQuantity()!=null)
        {
            quantity.setText(String.valueOf(recipeIngredient.getQuantity().getAmount()));
            units.setSelection(unit_types.indexOf(new Quantity().getUnit()));
        }

        return root;
    }
}