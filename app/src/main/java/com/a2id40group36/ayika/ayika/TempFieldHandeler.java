package com.a2id40group36.ayika.ayika;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.thermostatapp.util.HeatingSystem;
import org.thermostatapp.util.InvalidInputValueException;

/**
 * Created by D Kortleven on 16/06/2016.
 */
public class TempFieldHandeler implements View.OnFocusChangeListener{

    int type; //0 = nightTemp, 1 = dayTemp
    final EditText v;

    public TempFieldHandeler(EditText vo){
        if(vo.getId() == R.id.nightTempField){
            type = 0;
        }else if(vo.getId() == R.id.dayTempField){
            type = 1;
        }

        this.v = vo;

        updateFromServer();


    }

    public void updateFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    float t = 0;
                    switch (type) {
                        case 0:
                            t = Float.parseFloat(HeatingSystem.get("nightTemperature"));
                            break;
                        case 1:
                            t = Float.parseFloat(HeatingSystem.get("dayTemperature"));
                            break;
                    }

                    v.setText(Float.toString(t));


                }catch(Exception e){
                    Log.d("DEBUG", "Error while getting temperatures: " + e.getMessage());
                }
            }
        }).start();
    }
    private void updateDayTemp(Float s) {

        Log.d("DEBUG", "Day Changed: " + s);
        try {
            HeatingSystem.put("dayTemperature", s.toString());
        }catch(InvalidInputValueException e){
            Log.d("Error", "Day temperature could not be put to the server: " + e.getMessage());
        }
    }

    private void updateNightTemp(Float s){
        Log.d("DEBUG", "Night Changed: " + s);

        try {
            HeatingSystem.put("nightTemperature", s.toString());
        }catch(InvalidInputValueException e){
            Log.d("Error", "Night temperature could not be put to the server: " + e.getMessage());
        }
    }

    // Returns the value if it is in the range 5 - 30, or change to closest bound if possible
    public float formatAndInRange(float in){
        if(in < 5){
            return 5;
        }
        if(in > 30){
            return 30;
        }
        return 18;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            final float t = formatAndInRange(Float.parseFloat(((EditText)v).getText().toString()));
            ((EditText) v).setText(Float.toString(t));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    switch (type) {
                        case 0:
                            updateNightTemp(t);
                            break;
                        case 1:
                            updateDayTemp(t);
                            break;
                    }
                }
            }).start();

        }

    }
}
