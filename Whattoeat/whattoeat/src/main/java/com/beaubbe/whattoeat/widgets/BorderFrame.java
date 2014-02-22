package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.beaubbe.whattoeat.R;

/**
 * Created by Gab on 22/02/14.
 */
public class BorderFrame extends FrameLayout
{
    private boolean borderTop,
            borderBottom,
            borderLeft,
            borderRight;

    public BorderFrame(Context c)
    {
        super(c);
    }
    public BorderFrame(Context c, AttributeSet attrs)
    {
        super(c, attrs);
        setupBorders(attrs);
    }
    public BorderFrame(Context c, AttributeSet attrs, int styleDef)
    {
        super(c, attrs, styleDef);
        setupBorders(attrs);
    }

    private void setupBorders(AttributeSet attrs)
    {
        borderTop = attrs.getAttributeBooleanValue(R.attr.borderTop, true);
        borderBottom = attrs.getAttributeBooleanValue(R.attr.borderBottom, true);
        borderLeft = attrs.getAttributeBooleanValue(R.attr.borderLeft, true);
        borderRight = attrs.getAttributeBooleanValue(R.attr.borderRight, true);
    }


    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLACK);

        //top
        if(borderTop)
            canvas.drawLine(0,0,getWidth(),0,p);
        //bottom
        if(borderBottom)
            canvas.drawLine(0,getHeight()-1,getWidth(),getHeight()-1,p);
        //left
        if(borderLeft)
            canvas.drawLine(0,0,0,getHeight(),p);
        //right
        if(borderRight)
            canvas.drawLine(getWidth()-1,0,getWidth()-1,getHeight(),p);
    }
}
