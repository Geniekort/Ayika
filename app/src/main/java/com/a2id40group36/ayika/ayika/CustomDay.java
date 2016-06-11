package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mwape on 11.06.2016.
 */
public class CustomDay extends View {
    private Handler mHandler;
    Canvas canvas;
    String day;
    TextView textView;
    public String currentDateTimeString;
    public Location location;



    //   public int counter =23;
    TextView showText;
    private Paint cd_paint = new Paint();



    public CustomDay(Context context) {
        super(context);
        init(null,0);



    }

    public CustomDay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public CustomDay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);

    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        cd_paint.setColor(Color.WHITE);
        cd_paint.setTextSize(50);


    }


    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        this.canvas = canvas;
        run();
        canvas.drawText(currentDateTimeString+"", 20, 550, cd_paint);
        invalidate();
    }

    public void run(){currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());}

}






