package com.beaubbe.whattoeat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 20/02/14.
 */
public class RecipeViewFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        return root;
    }
}
