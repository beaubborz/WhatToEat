package com.beaubbe.whattoeat.activities;

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
import com.beaubbe.whattoeat.widgets.DayPreview;

import java.util.Date;
import java.util.GregorianCalendar;

public class ShowCalendar extends ActionBarActivity {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            calendar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
           // calendar.setMinDate(System.currentTimeMillis()); /* workaround that prevents an error since default date = now  */
            calendar.setDate(System.currentTimeMillis());
            calendarContainer.addView(calendar);
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    GregorianCalendar gc = new GregorianCalendar(year, month, dayOfMonth, 12, 00);
                    DayPreview prev = (DayPreview) getFragmentManager().findFragmentById(R.id.day_preview);
                    prev.setDate(gc.getTimeInMillis());
                }
            });

            return rootView;
        }
    }

}
