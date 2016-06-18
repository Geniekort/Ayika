package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Mwape on 17.06.2016.
 */
public class CustomsSettings extends View implements View.OnClickListener{
    Canvas canvas;
    EditText text = (EditText) findViewById(R.id.server);
    InputFilter[] filters = new InputFilter[1];


    ImageButton helpButton = (ImageButton) findViewById(R.id.helpme);
    Button save = (Button) findViewById(R.id.savebutton);
    View v;

    HomeActivity home;
    String day;
    public int userInput;
    float w;
    float textSize;
    private MainActivity m;





    private Paint paint_luttik = new Paint();
    private Paint paint_bluttik = new Paint();
    private Paint mpaint = new Paint();
    private Paint paint2 = new Paint();

    public CustomsSettings(Context context) {
        super(context);
        init(null,0);

    }

    public CustomsSettings(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public CustomsSettings(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);

    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        paint_luttik.setColor(Color.WHITE);
        paint_luttik.setTextSize(40);
        paint_bluttik.setColor(Color.WHITE);
        paint_bluttik.setTextSize(120);
        // onClick(v);




    }

    public void onDraw(Canvas canvas){
        this.canvas=canvas;
        //  filters[0] = new InputFilter.LengthFilter(10); //Filter to 10 characters
        //  text.setFilters(filters);

        canvas.drawText("Server address:",50, 180, paint_luttik);
        // canvas.drawRect(50,190,600, 260,paint_luttik);
        canvas.drawText("Settings",140, 120, paint_bluttik);
        canvas.drawLine(30,300,750, 300, paint_bluttik);
        // canvas.drawRect(50,430,420, 480,paint_luttik);
        canvas.drawText("Vacation Mode Temperature:",50, 400, paint_luttik);

        super.onDraw(canvas);
        invalidate();

    }


    @Override
    public void onClick(View v) {
        this.v =v;
        save.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Log.v("EditText", text.getText().toString());
                        m = (MainActivity) getContext();
                        Toast toast = Toast.makeText(m.getApplicationContext(), " Current settings have been saved!", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                });
    }
}

