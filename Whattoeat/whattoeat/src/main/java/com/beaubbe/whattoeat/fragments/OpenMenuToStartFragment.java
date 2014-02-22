package com.beaubbe.whattoeat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 22/02/14.
 */
public class OpenMenuToStartFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_open_menu_to_start, container, false);
        return root;
    }
}
