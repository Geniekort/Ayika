package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by D Kortleven on 18/06/2016.
 */
public class TemperatureChangeThread implements Runnable {

    final ThrottleSlider t;
    final Activity a;
    public final float RATEOFTEMPCHANGE = (float)0.023; // In degrees per 10 miliseconds

    public TemperatureChangeThread(ThrottleSlider to, Activity ac){

        Log.d("DEBUG", "Thread is made");
        this.t = to;
        a = ac;

    }


    @Override
    public void run() {

        while(t.currentChange != 0 && !t.stopThreadPlease) {

            t.addTemperature(t.currentChange * RATEOFTEMPCHANGE);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.d("Error", "Could not sleep: " + e.getMessage());
            }

            t.changeTempTextView();

        }

        t.stoppedThread();
        t.stopThreadPlease = false;
    }
}
