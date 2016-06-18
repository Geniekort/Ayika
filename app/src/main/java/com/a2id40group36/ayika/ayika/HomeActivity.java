package com.a2id40group36.ayika.ayika;

/**
 * Created by D Kortleven on 30/05/2016.
 */

import com.a2id40group36.ayika.ayika.R;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends Fragment {
    public static TextView textView;
    public boolean m_iAmVisible;
    public static String currentDateTimeString = "";
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        m_iAmVisible = false;

        return rootView;





    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        m_iAmVisible = isVisibleToUser;

        Log.d("DEBUG", "setUserVisibleHint: " + m_iAmVisible);
        if(getActivity() != null) {
            ThrottleSlider x = ((ThrottleSlider) getActivity().findViewById(R.id.throttle));

            if (!m_iAmVisible && x != null) {
                Log.d("DEBUG", "setUserVisibleHint: ");
                x.stoppedThread();
            }
        }
    }


}
