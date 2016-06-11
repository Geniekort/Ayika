package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mwape on 08.06.2016.
 */
public class CustomTEMP extends View {
    private Handler mHandler;
    Canvas canvas;




 //   public int counter =23;
    TextView showText;
    private Paint paint_TEMP = new Paint();



    public CustomTEMP(Context context) {
        super(context);
        init(null,0);



    }

    public CustomTEMP(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public CustomTEMP(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);

    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        paint_TEMP.setColor(Color.CYAN);
        paint_TEMP.setTextSize(150);

     //   showText.setTextColor(Color.BLUE);
      //  showText.setTextSize(40);


    }


    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        this.canvas = canvas;
     //   Handler mHandler = new Handler();
      //  mHandler.post(mUpdate);
        canvas.drawText(CustomVS.counter + "°", 230, 270, paint_TEMP);

        invalidate();







    }

    public void run1(){

        String mwape;
        showText.setText(""+ CustomVS.counter + "°");
        showText.setTextColor(Color.CYAN);
        showText = (TextView) findViewById(R.id.no);
        mwape = String.valueOf(CustomVS.counter);
        showText.setText(mwape);
        showText.setTextSize(50);
    }

    public void run(){
        run1();
    }

  /*  Runnable mUpdate = new Runnable() {


        public void run() {

            canvas.drawText("Secret Text"+counter + "°", 300, 300, paint_TEMP);

            counter++; // incrementing the value

            mHandler.postDelayed(this, 60);
            invalidate();
        }
    }*/;




}
