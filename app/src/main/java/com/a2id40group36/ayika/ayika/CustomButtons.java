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
import android.widget.Toast;

/**
 * Created by Mwape on 18.06.2016.
 */
public class CustomButtons extends ImageButton implements View.OnClickListener {
    private Handler mHandler;
    View v;
    String help1 = "To change the temperature of the vacation mode, tap the box and enter a number between 5 and 30.";
    Canvas canvas;
    Bitmap BL;
    HomeActivity home;
    String day;
    ImageButton helpSettings = (ImageButton) findViewById(R.id.settingshelp);
    // ImageButton helpSchedule = (ImageButton) findViewById(R.id.helpme);
    // ImageButton helpHome = (ImageButton) findViewById(R.id.helpme);

    public String DJBASLUTTIK = "VaccationMode On!";
    public String DJBASLUTTIK2 = "VaccationMode OFF!";
    private MainActivity m;




    //   public int counter =23;
    TextView showText;
    private Paint paint_Luttik = new Paint();





    public CustomButtons(Context context) {
        super(context);
        init(null,0);

    }

    public CustomButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomButtons(Context context, AttributeSet attrs, int defStyleAttr) {
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

        // canvas.drawText(CustomVS.counter + "°", 230, 270, paint_Luttik);

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
                builder.setMessage(help1)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Help")
                        .create();
                //set the view of the AlertDialog

                //    onClick(view);
                //  AlertDialog dialog = builder.create();
                builder.show();
            }


        });
    }

}
