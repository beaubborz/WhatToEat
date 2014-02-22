package com.beaubbe.whattoeat.models;

import android.content.Context;

import com.beaubbe.whattoeat.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gab on 20/02/14.
 */
public class QuantityManager
{

    public static String[] getUnitTypes(Context c)
    {
        return c.getResources().getStringArray(R.array.unit_types);
    }

    public static String[] getUnitNames(Context c)
    {
        return c.getResources().getStringArray(R.array.unit_names);
    }
}
