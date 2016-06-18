package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by D Kortleven on 17/06/2016.
 */
public class ThrottleSlider extends View {

    public float sliderTemperature; // Between 5 - 30, is desired temperature

    private float handleY, handleWidth, handleHeight, bheight;
    private TemperatureChangeThread runnab;
    private Thread thr;

    public boolean tempChanging, tempChCommand;
    public float currentChange; // Should be a number between -1.0 and 1.0. Indicating the rate of change

    public Bitmap b;

    public ThrottleSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }
    public ThrottleSlider(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }
    public ThrottleSlider(Context context) {
        super(context);
        initialize();

    }

    public void initialize(){
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.backsll);
        bheight = b.getHeight();
        handleY = 0;
        handleWidth = b.getWidth() + 25;
        handleHeight = 50;
        currentChange = 0;
        sliderTemperature = 18;

        tempChanging = false;
        runnab = null; //IT IS INITIALIZED FIRST TIME NEEDED!
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

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.backsll);

        Paint p = new Paint();

        canvas.drawBitmap(b,0,(height/2 - b.getHeight()/2), p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.RED);
        canvas.drawRect(0,height/2 + handleY - handleHeight/2, handleWidth, height/2 + handleY + handleHeight/2,p);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                handleY = e.getY() - getHeight()/2;
                currentChange = handleY / bheight * 2;
                break;
            case MotionEvent.ACTION_DOWN:
                handleY = e.getY() - getHeight()/2;
                currentChange = handleY / bheight * 2;
                if (!tempChanging) {
                    tempChanging = true;
                    startThread();
                }


                break;
            case MotionEvent.ACTION_UP:
                handleY = 0;
                currentChange = handleY / bheight * 2;
                break;

        }



            if(handleY > bheight / 2)
                handleY = bheight/2;
            if(handleY <  -bheight / 2)
                handleY = -bheight/2;


        invalidate();
        return true;
    }

    public void startThread(){
        if(runnab == null){
            runnab = new TemperatureChangeThread(this, (Activity) getContext());
        }

        (new Thread(runnab)).start();

    }

    public void stoppedThread(){
        tempChanging = false;

        Log.d("DEBUG", "Thread isss " + tempChanging);
    }

    public void setTemperature(float t){
        if(t < 5.0){
            sliderTemperature = 5;
        }else if( t > 30.0){
            sliderTemperature = 30;
        }else sliderTemperature = t;

        return;
    }

    public void addTemperature(float t){
        setTemperature(sliderTemperature + t);
        return;
    }
}
