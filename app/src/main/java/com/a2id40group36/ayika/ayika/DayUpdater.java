package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.widget.TextView;

import org.thermostatapp.util.HeatingSystem;

import java.net.ConnectException;

/**
 * Created by D Kortleven on 18/06/2016.
 */
public class DayUpdater {

    public DayUpdater(final Activity a){

        final TextView t = (TextView)a.findViewById(R.id.dateAndTimeField);
        new Thread(new Runnable() {
            @Override
            public void run() {




                while(true){
                    String day = "", time="";
                    try {
                        time = HeatingSystem.get("time");
                        day = HeatingSystem.get("day");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }

                    final String fday = day, ftime = time;
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            t.setText(fday + " " + ftime);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
