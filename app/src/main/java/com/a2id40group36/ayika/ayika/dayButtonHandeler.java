package com.a2id40group36.ayika.ayika;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.thermostatapp.util.HeatingSystem;
import org.thermostatapp.util.InvalidInputValueException;
import org.thermostatapp.util.WeekProgram;

/**
 * Created by D Kortleven on 15/06/2016.
 * Class to deal with the buttons in the weekProgram Fragment
 */
public class dayButtonHandeler implements View.OnClickListener {

    MainActivity m;
    Button[] bs;
    boolean buttonsSet = false;

    public dayButtonHandeler(MainActivity mo){
        m = mo;

        bs = new Button[7];
    }

    public void getButtons(){
        bs[0] = (Button) m.findViewById(R.id.buttonsu);
        bs[1] = (Button) m.findViewById(R.id.buttonmo);
        bs[2] = (Button) m.findViewById(R.id.buttontu);
        bs[3] = (Button) m.findViewById(R.id.buttonwe);
        bs[4] = (Button) m.findViewById(R.id.buttonth);
        bs[5] = (Button) m.findViewById(R.id.buttonfr);
        bs[6] = (Button) m.findViewById(R.id.buttonsa);

        for(int i = 0; i < bs.length; i++){
            if(i == 0){
                bs[i].setBackgroundColor(MainActivity.dayButtonBackgroundColorS);
            }else{
                bs[i].setBackgroundColor(MainActivity.dayButtonBackgroundColor);
            }
        }
    }




    @Override
    public void onClick(View v){



        if(!buttonsSet){
            buttonsSet = true;
            getButtons();
        }


        int day = getDay(v.getId());

        ((ScheduleView) m.findViewById(R.id.scheduler)).changeDay(m.getNodesForDay(day), day);
        ((ScheduleView) m.findViewById(R.id.scheduler)).getNodesFromServer();

        highlightButton(day);


    }

    public void highlightButton(int day){
        for(int i = 0; i < bs.length; i++){
            if(i == day){
                bs[i].setBackgroundColor(MainActivity.dayButtonBackgroundColorS);
            }else{
                bs[i].setBackgroundColor(MainActivity.dayButtonBackgroundColor);
            }
        }

    }

    private int getDay(int id){
        switch(id){
            case R.id.buttonsu:
                return 0;
            case R.id.buttonmo:
                return 1;
            
            case R.id.buttontu:
                return 2;
            
            case R.id.buttonwe:
                return 3;
            
            case R.id.buttonth:
                return 4;
            
            case R.id.buttonfr:
                return 5;
            
            case R.id.buttonsa:
                return 6;
            
        }
        return -1;
    }
}
