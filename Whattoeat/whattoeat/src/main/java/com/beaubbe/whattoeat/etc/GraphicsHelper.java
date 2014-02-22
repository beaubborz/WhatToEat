package com.beaubbe.whattoeat.etc;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by Gab on 22/02/14.
 */
public class GraphicsHelper {

    /**
     * Draw a regular polygon (triangle, square, pentagon, hexagon...)
     * @param c canvas to draw on
     * @param center center of the element.
     * @param sides number of sides
     * @param radius length from center to each points of the polygon.
     * @param rotation initial rotation angle (radians) of the polygon.
     * @param paint paint to use.
     */
    public static void drawRegularPolygon(Canvas c, Point center, int sides, int radius, double rotation, Paint paint)
    {
        final Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        final double angleStep = 2*Math.PI/sides;
        for(int i=0;i<sides;i++)
        {
            final double angle = angleStep*i;
            final float x = (float) (center.x + radius*Math.cos(angle+rotation));
            final float y = (float) (center.y + radius*Math.sin(angle+rotation));
            if(i==0)
            {
                path.moveTo(x,y);
            }
            else
            {
                path.lineTo(x,y);
            }
        }
        path.close();
        c.drawPath(path, paint);
    }
}
