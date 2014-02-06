package com.beaubbe.whattoeat.com.beaubbe.whattoeat.widgets;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 06/02/14.
 */
public class IngredientInput extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ingredient_input_widget, container);
    }
}
