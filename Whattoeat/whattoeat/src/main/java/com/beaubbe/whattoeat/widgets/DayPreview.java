package com.beaubbe.whattoeat.widgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.activities.ListRecipes;
import com.beaubbe.whattoeat.models.MenuEntry;
import com.beaubbe.whattoeat.models.ModelFinder;
import com.beaubbe.whattoeat.models.Recipe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Gab on 11/02/14.
 */
public class DayPreview extends Fragment {
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.day_preview_widget, container, false);
        setDate(new Date().getTime());
        return root;
    }

    public void setDate(long date)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        ((TextView)root.findViewById(R.id.date)).setText(dateFormat.format(new Date(date)));

        //show recipes.
        showRecipesForDate(date);
    }

    private void showRecipesForDate(long date)
    {
        ((ViewGroup)root.findViewById(R.id.recipes)).removeAllViews();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        for(MenuEntry r:new ModelFinder(getActivity()).getMenuEntriesForDate(date))
        {
            ft.add(R.id.recipes, new RecipeListItem(r.getRecipe()));
        }
        ft.commit();
    }
}
