package com.a2id40group36.ayika.ayika;

/**
 * Created by D Kortleven on 30/05/2016.
 */

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.thermostatapp.util.HeatingSystem;
import org.thermostatapp.util.InvalidInputValueException;

import java.net.ConnectException;

public class WeekProgramActivity extends Fragment implements View.OnClickListener {


    dayButtonHandeler d;
    int counter = 0;
    ScheduleView s;
    boolean activated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weekprogram, container, false);



        d = new dayButtonHandeler((MainActivity)this.getActivity());

        rootView.findViewById(R.id.buttonmo).setOnClickListener(d);
        rootView.findViewById(R.id.buttontu).setOnClickListener(d);
        rootView.findViewById(R.id.buttonwe).setOnClickListener(d);
        rootView.findViewById(R.id.buttonth).setOnClickListener(d);
        rootView.findViewById(R.id.buttonfr).setOnClickListener(d);
        rootView.findViewById(R.id.buttonsa).setOnClickListener(d);
        rootView.findViewById(R.id.buttonsu).setOnClickListener(d);

        EditText f = (EditText)rootView.findViewById(R.id.nightTempField);
        f.setOnFocusChangeListener(new TempFieldHandeler(f));
        f = (EditText)rootView.findViewById(R.id.dayTempField);
        f.setOnFocusChangeListener(new TempFieldHandeler(f));

        ToggleButton tb = (ToggleButton) rootView.findViewById(R.id.scheduleOnOffButton);
        tb.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceType) {
        super.onActivityCreated(savedInstanceType);
        d.getButtons();
        d.highlightButton(1);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getActivity() != null) {
            Log.d("DEBUG", "WEEKPROGRAMVIS: ");
            ((Indicator) getActivity().findViewById(R.id.indicator)).state = 2;
            ((Indicator)getActivity().findViewById(R.id.indicator)).invalidate();
        }
    }


    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.scheduleOnOffButton){
            final boolean b = ((ThrottleSlider)getActivity().findViewById(R.id.throttle)).activated;
            if(b){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HeatingSystem.put("weekProgramState", ((ToggleButton) v).isChecked() ? "on" : "off");
                        } catch (InvalidInputValueException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        }
    }
}
