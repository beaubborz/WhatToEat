package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 20/02/14.
 */
public class RatingWidget extends View
{

    public RatingWidget(Context c)
    {
        super(c);
    }

    @Override
    public void draw(Canvas canvas) {
       // canvas.drawBitmap(RessourceCache.getRessourceBitmap(getContext(), R.drawable.star));
    }
}
