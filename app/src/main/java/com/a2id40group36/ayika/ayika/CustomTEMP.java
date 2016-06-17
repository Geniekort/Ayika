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
    HomeActivity home;
    String day;



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
        paint_TEMP.setColor(Color.WHITE);
        paint_TEMP.setTextSize(120);


     //   showText.setTextColor(Color.BLUE);
      //  showText.setTextSize(40);


    }


    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        this.canvas = canvas;

     //   Handler mHandler = new Handler();
      //  mHandler.post(mUpdate);
      //  canvas.drawText(CustomVS.counter + "°", 230, 270, paint_TEMP);
        canvas.drawText(CustomVS.mTrick+ "°", 230, 270, paint_TEMP);

        invalidate();


    }

}
