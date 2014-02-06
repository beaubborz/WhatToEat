package com.beaubbe.whattoeat.widgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Quantity;

/**
 * Created by Gab on 06/02/14.
 */
public class IngredientInput extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ingredient_input_widget, container, false);
        Spinner units = (Spinner)root.findViewById(R.id.unit);
        units.setAdapter(new ArrayAdapter<Quantity.Unit>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, Quantity.Unit.getAsList(root.getContext())));
        return root;
    }


}
