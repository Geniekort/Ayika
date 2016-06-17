package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mwape on 16.06.2016.
 */
public class LuttikLemma extends ImageButton implements View.OnClickListener{
    private Handler mHandler;
    View v;
    Canvas canvas;
    Bitmap BL;
    HomeActivity home;
    String day;
    ImageButton button = (ImageButton) findViewById(R.id.imageButton4);
    public String DJBASLUTTIK = "VaccationMode On!";
    public String DJBASLUTTIK2 = "VaccationMode OFF!";
    private MainActivity m;




    //   public int counter =23;
    TextView showText;
    private Paint paint_Luttik = new Paint();





    public LuttikLemma(Context context) {
        super(context);
        init(null,0);

    }

    public LuttikLemma(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LuttikLemma(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);


    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        paint_Luttik.setColor(Color.WHITE);
        paint_Luttik.setTextSize(150);
        onClick(v);



        //   showText.setTextColor(Color.BLUE);
        //  showText.setTextSize(40);


    }


    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        this.canvas = canvas;

        BL = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
        canvas.drawBitmap(BL, 400, 250, paint_Luttik);

       // canvas.drawText(CustomVS.counter + "°", 230, 270, paint_Luttik);

        invalidate();

    }

    @Override
    public void onClick(View v) {
        this.v = v;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = (MainActivity) getContext();
                Toast toast = Toast.makeText(m.getApplicationContext(), DJBASLUTTIK + " current set temperature for this mode is:"+CustomVS.mTrick+"°", Toast.LENGTH_SHORT);
                toast.show();

            }


        });
    }
}
