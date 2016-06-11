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
 * Created by Mwape on 08.06.2016.
 */
public class CustomBG extends View {
    Bitmap BG;
    private Paint paint_BG = new Paint();
    public CustomBG(Context context) {
        super(context);
        init(null,0);
    }

    public CustomBG(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    public CustomBG(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        paint_BG.setColor(Color.BLUE);
        paint_BG.setTextSize(230);
    }



    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
       // canvas.drawLine(0,0,getHeight(),getWidth(), paint_BG);
        BG = BitmapFactory.decodeResource(getResources(), R.mipmap.fslider);
        canvas.drawBitmap(BG, 400, 250, paint_BG);


    }



}
