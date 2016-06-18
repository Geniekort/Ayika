package com.a2id40group36.ayika.ayika;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import org.thermostatapp.util.HeatingSystem;
import org.thermostatapp.util.InvalidInputValueException;
import org.thermostatapp.util.Switch;
import org.thermostatapp.util.WeekProgram;

public class MainActivity extends AppCompatActivity{
//


    private ViewPager viewPager;
    ViewAdapter mAdapter;
    int counter = 0;

    public static final int dayButtonBackgroundColor = Color.argb(200,200,200,200);
    public static final int dayButtonBackgroundColorS = Color.argb(250, 200,200,200);

    private float[][][] switchPoints = new float[7][2][5]; //7 days, 2 possible states, 5 switches

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ViewAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(1);

        for(int i = 0; i < switchPoints.length; i++){
            for(int j = 0; j < switchPoints[i].length; j++){
                for(int k = 0; k < switchPoints[i][j].length; k++){
                    switchPoints[i][j][k] = -1;
                }
            }
        }



    }


    public void pauseSwipe(boolean b){
        Log.d("DEBUG", "Pausing");
        ((StopViewPager) findViewById(R.id.pager)).setPagingEnabled(b);
    }

    public void pauseScroll(boolean b){
        Log.d("DEBUG", "Pausing");
        ((LockableScrollView) findViewById(R.id.scrollViewSchedule)).setScrollingEnabled(b);
    }



    public float[][] getNodesForDay(int day){
        return switchPoints[day];
    }

    // Get all the switches from the server and return the switches for the day on input
    public float[][] getSwitchesFromServer(int day){
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    WeekProgram w = HeatingSystem.getWeekProgram();

                    for(int i = 0; i < 7; i++) {
                        String day = getDay(i);
                        for (int j = 0; j < 2; j++) {
                            for (int k = 0; k < 5; k++) {
                                Switch s = w.data.get(day).get((5 * j) + k);
                                int type = s.getType().equals("day") ? 1 : 0;
                                float time = s.getTimeFloat();
                                switchPoints[i][type][k] = time;
                            }
                        }
                    }

                }catch(Exception e){
                    Log.d("ERROR", "Oops something went wrong: " + e.getMessage());
                }
            }
        });
        t.start();
        try {
            t.join();
        }catch(Exception e){
            Log.d("ERROR", "Ik was te ongeduldig");
        }

        return getNodesForDay(day);
    }

    public void updateSwitches(float[][] newSwitches, int day){
        switchPoints[day] = newSwitches;
        updateServerWeekProgram();
        return;
    }

    private void updateServerWeekProgram(){
        new Thread(new Runnable() {
            public void run() {

                WeekProgram w = new WeekProgram();

                for(int i = 0; i < 7; i++){
                    String day = getDay(i);

                    for(int j = 0; j < 2; j++){
                        for(int k = 0; k < 5; k++){
                            String time;
                            if(switchPoints[i][j][k] != -1){
                                time = ((int)Math.floor(switchPoints[i][j][k]) < 10 ?
                                        "0" + (int)Math.floor(switchPoints[i][j][k]) : (int)Math.floor(switchPoints[i][j][k])) +
                                        ":" +
                                        ((int)((switchPoints[i][j][k] % 1)*60) == 0 ?
                                                "00" : (int)((switchPoints[i][j][k] % 1)*60));
                            }else time = "00:00";

                            Log.d("Debug", "time: " + time);
                            w.data.get(day).set((5 * j) + k, new Switch((j==0 ? "night" : "day"), !(switchPoints[i][j][k] == -1), time));
                        }
                    }


                }
                HeatingSystem.setWeekProgram(w);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ScheduleView)findViewById(R.id.scheduler)).invalidateMe();
                    }
                });
            }

        }).start();
    }


    private String getDay(int day){
        switch(day){
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
        }
        return null;
    }




}
