package com.beaubbe.whattoeat.activities;

import android.content.Intent;
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

import com.beaubbe.whattoeat.R;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void actionGoTo(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.button_recipes:
                intent = new Intent(this, ListRecipes.class);
                startActivity(intent);
            break;

            case R.id.button_calendar:
                intent = new Intent(this, ShowCalendar.class);
                startActivity(intent);
            break;
        }
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
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
            return rootView;
        }
    }

}
