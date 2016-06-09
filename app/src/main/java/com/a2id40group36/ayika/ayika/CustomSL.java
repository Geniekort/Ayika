package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Mwape on 08.06.2016.
 */
public class CustomSL extends View{

    private Paint paint_SL = new Paint();
    public CustomSL(Context context) {
        super(context);
        init(null,0);
    }

    public CustomSL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    public CustomSL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public void init(AttributeSet attrs, int defStyleAttr)
    {
        paint_SL.setColor(Color.BLUE);

    }




}
