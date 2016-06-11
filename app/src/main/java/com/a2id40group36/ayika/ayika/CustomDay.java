package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mwape on 11.06.2016.
 */
public class CustomDay extends Date {
    View view;
    Paint cd_paint = new Paint();
    int day;
    int month;
    int year;
    Date date;
    TextView mTimeText;
    
    CustomDay(int year, int month, int day){
        init(year,month,day);

    }

    public void init(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
        cd_paint.setColor(Color.CYAN);

        Calendar location;
       location = new GregorianCalendar(){

       };

        date = new Date(String.valueOf(location.getTime()));

    }




}
