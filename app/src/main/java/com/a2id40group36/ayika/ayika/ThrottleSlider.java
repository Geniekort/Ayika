package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by D Kortleven on 17/06/2016.
 */
public class ThrottleSlider extends View {
    public ThrottleSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();


        setMeasuredDimension(width, getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = canvas.getWidth();
        float height = canvas.getHeight();

       /* Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.backsll);

        canvas.drawBitmap(b,0,0, new Paint());*/
    }
}
