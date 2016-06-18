package com.a2id40group36.ayika.ayika;

/**
 * Created by D Kortleven on 30/05/2016.
 */

import com.a2id40group36.ayika.ayika.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends Fragment implements View.OnClickListener {
    public static TextView textView;
    public boolean m_iAmVisible;
    public static String currentDateTimeString = "";
    View rootView;

    public boolean holiday = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        m_iAmVisible = false;

        return rootView;





    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ImageButton) view.findViewById(R.id.holidaybutton)).setOnClickListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        m_iAmVisible = isVisibleToUser;

        Log.d("DEBUG", "setUserVisibleHint: " + m_iAmVisible);
        if(getActivity() != null) {
            Log.d("DEBUG", "HOMEACTIVITY: ");
            ThrottleSlider x = ((ThrottleSlider) getActivity().findViewById(R.id.throttle));
            ((Indicator)getActivity().findViewById(R.id.indicator)).state = 1;
            ((Indicator)getActivity().findViewById(R.id.indicator)).invalidate();
            if (!m_iAmVisible && x != null) {
                Log.d("DEBUG", "setUserVisibleHint: ");
                x.stoppedThread();
            }
        }


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.holidaybutton){
            holiday = !holiday;
            Toast t = Toast.makeText(v.getRootView().getContext(), "Holiday is " + holiday, Toast.LENGTH_SHORT);
            t.show();
            Bitmap bb = BitmapFactory.decodeResource(getResources(), R.drawable.helpblack);
            ((ImageButton) v).setImageBitmap(bb);
        }
    }
}
