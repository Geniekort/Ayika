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
public class TempFieldHandeler implements TextWatcher {

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, int start, int before, int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case 0:
                        updateNightTemp(s);
                        break;
                    case 1:
                        updateDayTemp(s);
                        break;
                }
            }
        }).start();

    }

    private void updateDayTemp(CharSequence s) {
        Log.d("DEBUG", "Day Changed: " + s);
        try {
            HeatingSystem.put("dayTemperature", s.toString());
        }catch(InvalidInputValueException e){
            Log.d("Error", "Day temperature could not be put to the server: " + e.getMessage());
        }
    }

    private void updateNightTemp(CharSequence s){
        Log.d("DEBUG", "Night Changed: " + s);

        try {
            HeatingSystem.put("nightTemperature", s.toString());
        }catch(InvalidInputValueException e){
            Log.d("Error", "Night temperature could not be put to the server: " + e.getMessage());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {


    }
}
