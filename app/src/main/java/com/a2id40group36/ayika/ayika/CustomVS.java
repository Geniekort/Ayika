package com.a2id40group36.ayika.ayika;

import android.graphics.Color;
import android.graphics.Paint;
import android.widget.SeekBar;

/**
 * Created by Mwape on 08.06.2016.
 */

    import android.widget.SeekBar;
    import android.util.Log;
    import android.content.Context;
    import android.graphics.Canvas;
    import android.util.AttributeSet;
    import android.view.MotionEvent;
import android.widget.TextView;

public class CustomVS extends SeekBar {
    SeekBar bar;

  //  public int counter =23;
    TextView showText;
    private Paint paint_VS = new Paint();
    static Integer counter = 0;



        public CustomVS(Context context) {
            super(context);
        }

        public CustomVS(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);


        }

        public CustomVS(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(h, w, oldh, oldw);

        }

        @Override
        protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());

        }

        protected void onDraw(Canvas c) {
            c.rotate(-90);
            c.translate(-getHeight(),100);

            super.onDraw(c);



        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            if (!isEnabled()) {
                bar.setProgress(200);
                return false;
            }


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    int i=0;
                    i=getMax() - (int) (getMax() * event.getY() / getHeight());
                    setProgress(i);
                    Log.i("Progress",getProgress()+"");
                    onSizeChanged(getWidth(), getHeight(), 0, 0);

                    if(i >= 399){
                        counter++;

                    }

                    if(i <= 1){
                        counter--;

                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    i = 400;
                    setProgress(i);

                    break;

            }
            return true;
        }









    }

