package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Ingredient;
import com.beaubbe.whattoeat.models.Quantity;
import com.beaubbe.whattoeat.models.RecipeIngredient;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Gab on 06/02/14.
 */
public class IngredientView extends Fragment
{
    RecipeIngredient recipeIngredient;
    Context context;

    public IngredientView()
    {

    }

    public IngredientView(RecipeIngredient recipeIngredient)
    {
        context = recipeIngredient.getContext();
        this.recipeIngredient=recipeIngredient;
    }

    public void setRecipeIngredient(RecipeIngredient recipeIngredient)
    {
        this.recipeIngredient = recipeIngredient;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ingredient_view_widget, container, false);

        TextView name = (TextView)root.findViewById(R.id.ingredient_name);
        name.setText(recipeIngredient.getIngredient().getName());

        TextView quantity = (TextView) root.findViewById(R.id.qty);
        quantity.setText(String.valueOf(recipeIngredient.getQuantity().getAmount()));

        TextView units = (TextView)root.findViewById(R.id.unit);
        units.setText(recipeIngredient.getQuantity().getUnit().getSymbol());

        return root;
    }
}