package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Mwape on 18.06.2016.
 */
public class HelpHome extends ImageButton implements View.OnClickListener {
    private Handler mHandler;
    View v;
    String help3 = "Dragging the slider up increases the temperature, dragging the slider down decreases the temperature of the thermostat. \n" +
            "The further away the slider is from the middle the faster the temperature will increase or decrease depending on the direction the slider is dragged in.\n" +
            "The maximal temperature that can be reached by the thermostat is 30 degrees Celsius and the lowest it can reach is 5 degrees celsius.\n";
    Canvas canvas;
    Bitmap BL;
    HomeActivity home;
    String day;
    ImageButton helpSettings = (ImageButton) findViewById(R.id.helpme3);
    // ImageButton helpSchedule = (ImageButton) findViewById(R.id.helpme);
    // ImageButton helpHome = (ImageButton) findViewById(R.id.helpme);
    private MainActivity m;




    //   public int counter =23;
    TextView showText;
    private Paint paint_Luttik = new Paint();





    public HelpHome(Context context) {
        super(context);
        init(null,0);

    }

    public HelpHome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HelpHome(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);


    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        paint_Luttik.setColor(Color.WHITE);
        paint_Luttik.setTextSize(150);
        onClick(v);
    }


    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        this.canvas = canvas;

        BL = BitmapFactory.decodeResource(getResources(), R.drawable.helpblack);
        canvas.drawBitmap(BL, 400, 250, paint_Luttik);
        invalidate();

    }

    @Override
    public void onClick(View v) {
        this.v = v;
        helpSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                m = (MainActivity) getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(m);
                builder.setMessage(help3)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Help")
                        .create();

                builder.show();
            }


        });
    }
}
