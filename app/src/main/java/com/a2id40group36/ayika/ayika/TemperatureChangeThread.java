package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by D Kortleven on 18/06/2016.
 */
public class TemperatureChangeThread implements Runnable {

    final ThrottleSlider t;
    final Activity a;
    final TextView temptext;
    public final float RATEOFTEMPCHANGE = (float)0.1; // In degrees per miliseconds

    public TemperatureChangeThread(ThrottleSlider to, Activity ac){

        Log.d("DEBUG", "Thread is made");
        this.t = to;
        a = ac;

        temptext = (TextView) a.findViewById(R.id.temperatureText);
    }


    @Override
    public void run() {
        int loop = 0;
        Log.d("DEBUG", "In thread run" + t.currentChange);
        while(t.currentChange != 0) {

            t.addTemperature(t.currentChange * RATEOFTEMPCHANGE);
            loop++;

            Log.d("DEBUG", "In thread: " + t.currentChange);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.d("Error", "Could not sleep: " + e.getMessage());
            }


            if(temptext != null) {
                Log.d("DEBUG", "temptext is nonnull");
                a.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String nview = String.format("%.2f",t.sliderTemperature) + "°";
                        temptext.setText(nview);
                    }
                });
            }else{

                Log.d("DEBUG", "temptext is null");
            }





        }

        t.stoppedThread();
    }
}
