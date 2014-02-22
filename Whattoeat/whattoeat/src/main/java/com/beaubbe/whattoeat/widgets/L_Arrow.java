package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.beaubbe.whattoeat.etc.GraphicsHelper;

/**
 * Created by Gab on 22/02/14.
 */
public class L_Arrow extends View
{
    public L_Arrow(Context context)
    {
        super(context);
    }

    public L_Arrow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public L_Arrow(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final Paint paint = new Paint();
        paint.setColor(Color.rgb(0x33,0x33,0x33));
        paint.setAntiAlias(true);
        final float thickness = 3.0f;
        final int arrow_size = 10;
        paint.setStrokeWidth(thickness);
        final int w = getWidth();
        final int h = getHeight();

        canvas.drawLine(w/2.0f,0.0f, w/2.0f, h/2.0f+thickness/2.0f, paint);
        canvas.drawLine(arrow_size,h/2.0f, w/2.0f, h/2.0f, paint);

        paint.setStyle(Paint.Style.FILL);
        GraphicsHelper.drawRegularPolygon(canvas, new Point(arrow_size, h/2), 3, arrow_size, (Math.PI/3.0), paint);

    }
}
