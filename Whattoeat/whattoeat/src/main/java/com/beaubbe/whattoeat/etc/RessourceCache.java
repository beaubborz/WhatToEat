package com.beaubbe.whattoeat.etc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * Created by Gab on 20/02/14.
 */
public class RessourceCache
{
    public static SparseArray<Bitmap> cache = new SparseArray<Bitmap>();

    public static Bitmap getRessourceBitmap(Context c, int ressource_id)
    {
        Bitmap bmp = cache.get(ressource_id);
        if(bmp == null)
        {
            bmp = BitmapFactory.decodeResource(c.getResources(), ressource_id);
            cache.put(ressource_id, bmp);
        }
        return bmp;
    }
}
