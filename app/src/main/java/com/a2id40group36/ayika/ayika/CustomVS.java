package com.a2id40group36.ayika.ayika;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    import java.math.RoundingMode;
    import java.text.DecimalFormat;



public class CustomVS extends SeekBar {
    public static int i;

    private static Integer old_i = 0;
    private static Integer new_i = 0;
    ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton4);
    DecimalFormat decimalFormat;
    public static double fCounter;
    public static float mCount;
    public static String mTrick = "20,0";


    //  public int counter =23;
    TextView showText;
    private Paint paint_VS = new Paint();
    static double counter = 20.0;


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
        c.translate(-getHeight(), 100);


        super.onDraw(c);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if((i<=300)&&(i>=50))
        {
        mCount = (i * 0.1f);
        mTrick = String.format("%.1f", mCount);
        }

        decimalFormat = new DecimalFormat("##.#");
        LuttikLemma();


        if (!isEnabled()) {
            return false;

        }

        if(event.getAction() ==MotionEvent.ACTION_BUTTON_RELEASE){
            i =150;
            setProgress(i);
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                //   int i=0;
                i = getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                Log.i("Progress", getProgress() + "");
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                decimalFormat.setRoundingMode(RoundingMode.CEILING);

                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }


    public void LuttikLemma() {


    }


}

