package com.beaubbe.whattoeat.etc;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gab on 22/02/14.
 */
public class ViewsHelper
{
    public static List<View> getDirectChildren(ViewGroup root)
    {
        ArrayList<View> childs = new ArrayList<View>();

        for(int i=0;i<root.getChildCount();i++)
            childs.add(root.getChildAt(i));

        return childs;
    }
}
