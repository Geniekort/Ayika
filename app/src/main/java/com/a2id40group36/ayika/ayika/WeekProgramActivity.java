package com.a2id40group36.ayika.ayika;

/**
 * Created by D Kortleven on 30/05/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class WeekProgramActivity extends Fragment {


    dayButtonHandeler d;
    int counter = 0;
    ScheduleView s;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weekprogram, container, false);



        d = new dayButtonHandeler(this);
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

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceType) {
        super.onActivityCreated(savedInstanceType);
        d.getButtons();
        d.highlightButton(1);
    }

}
