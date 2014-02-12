package com.beaubbe.whattoeat.widgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 11/02/14.
 */
public class DayPreview extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.day_preview_widget, container, false);

        return root;
    }
}
