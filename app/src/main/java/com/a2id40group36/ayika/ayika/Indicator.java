package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by D Kortleven on 18/06/2016.
 */
public class Indicator extends View {

    public int state = 1;

    public Indicator(Context context) {
        super(context);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.GREEN);

        float bottomline = canvas.getHeight() - 200;
        Bitmap b;
        switch(state){
            case 0:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.lset);
                break;
            case 1:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.lhome);
                break;
            case 2:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.lsched);
                break;
            default:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.lhome);
        }

        canvas.drawBitmap(b, (canvas.getWidth()/2) - (b.getWidth()/2),bottomline,p);
    }
}
