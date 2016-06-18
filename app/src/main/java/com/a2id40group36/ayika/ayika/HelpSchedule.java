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
public class HelpSchedule extends ImageButton implements View.OnClickListener {
    private Handler mHandler;
    View v;
    String help2 = "Adding a node:" +
            "To add a node tap on the vertical Day or Night axis." +
            "Can not be done in the edit state." +
            "Entering the edit state:" +
            "The edit state can be entered by holding and releasing on the schedule." +
            "Moving a node:" +
            "While in the edit state, drag a node to move it and release to finalise its position." +
            "Removing a node:" +
            "While in edit mode, drag and drop a node into the delete zone (on the right side in red with a trashcan) to delete said node." +
            "Exiting the edit state:" +
            "To exit the edit state tap the screen." +
            "Scrolling:" +
            "While a node is being dragged, drag it to the upper or lower edge of the schedule do scroll through it." +
            "Adjusting the Day and Night temperature:" +
            "The temperatures can be changed by tapping the boxes respectively to the right of Day and Night and entering a number between 5 and 25." +
            "Turning the schedule on or off" +
            "The slider can be turned on or off thanks to the switch in the top left of the screen." +
            "This can be useful when the temperature has been changed on the homepage but the user wants to go back to the schedule’s temperature.";
    Canvas canvas;
    Bitmap BL;
    HomeActivity home;
    String day;
    ImageButton helpSettings = (ImageButton) findViewById(R.id.helpme2);
    // ImageButton helpSchedule = (ImageButton) findViewById(R.id.helpme);
    // ImageButton helpHome = (ImageButton) findViewById(R.id.helpme);
    private MainActivity m;




    //   public int counter =23;
    TextView showText;
    private Paint paint_Luttik = new Paint();





    public HelpSchedule(Context context) {
        super(context);
        init(null,0);

    }

    public HelpSchedule(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HelpSchedule(Context context, AttributeSet attrs, int defStyleAttr) {
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
                builder.setMessage(help2)
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
