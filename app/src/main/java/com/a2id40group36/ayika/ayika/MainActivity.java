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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import org.thermostatapp.util.HeatingSystem;

public class MainActivity extends AppCompatActivity {
//
    HeatingSystem h;

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

        for(int i = 0; i < switchPoints.length; i++){
            for(int j = 0; j < switchPoints[i].length; j++){
                for(int k = 0; k < switchPoints[i][j].length; k++){
                    switchPoints[i][j][k] = -1;
                }
            }
        }

        h = new HeatingSystem();

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

    public void updateSwitches(float[][] newSwitches, int day){
        switchPoints[day] = newSwitches;
        return;
    }

}
