package com.beaubbe.whattoeat.activities;

import android.app.Activity;
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
import android.widget.CalendarView;
import android.widget.FrameLayout;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.MenuEntry;
import com.beaubbe.whattoeat.models.ModelFinder;
import com.beaubbe.whattoeat.widgets.DayPreview;

import java.util.Date;
import java.util.GregorianCalendar;

public class ShowCalendar extends ActionBarActivity {

    public static final int REQUEST_CHOOSE_RECIPE = 1;


    private long selectedDate = System.currentTimeMillis();


    public long getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(long selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_calendar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void addRecipeToDate(View view)
    {
        Intent intent = new Intent(this, ListRecipes.class);
        startActivityForResult(intent, REQUEST_CHOOSE_RECIPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CHOOSE_RECIPE && resultCode == Activity.RESULT_OK)
        {
            final long recipe_id = data.getLongExtra("recipe_id", 0);
            if(recipe_id>0)
            {
                final MenuEntry me = new MenuEntry(this);
                me.setQuantityMultiplier(1);
                me.setDatetime(getSelectedDate());
                me.setRecipe(new ModelFinder(this).getRecipe(recipe_id));
                me.save();
                findViewById(R.id.calendar_container).invalidate();
            }
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
            final View rootView = inflater.inflate(R.layout.fragment_show_calendar, container, false);

            FrameLayout calendarContainer = (FrameLayout)rootView.findViewById(R.id.calendar_container);

            //create the calendar widget here:
            final CalendarView calendar = new CalendarView(getActivity());
            calendar.setId(0);
            calendar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            calendar.setDate(System.currentTimeMillis());
            calendarContainer.addView(calendar);
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    GregorianCalendar gc = new GregorianCalendar(year, month, dayOfMonth, 12, 00);
                    DayPreview prev = (DayPreview) getFragmentManager().findFragmentById(R.id.day_preview);
                    prev.setDate(gc.getTimeInMillis());
                    ((ShowCalendar)getActivity()).setSelectedDate(gc.getTimeInMillis());
                }
            });

            return rootView;
        }
    }

}
