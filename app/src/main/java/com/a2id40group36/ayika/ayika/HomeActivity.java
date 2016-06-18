package com.a2id40group36.ayika.ayika;

/**
 * Created by D Kortleven on 30/05/2016.
 */

import com.a2id40group36.ayika.ayika.R;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.thermostatapp.util.HeatingSystem;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends Fragment implements View.OnClickListener {
    public static TextView textView;
    public boolean m_iAmVisible;
    public static String currentDateTimeString = "";
    View rootView;

    Toast t;
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
    public void onClick(final View v) {
        if(v.getId() == R.id.holidaybutton){
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Vacation mode " + (!holiday ? "on" : "off") + "?")
                    .setMessage("Do you really want to put the vacation mode " + (holiday ?
                            "off? This will cause the thermostat to follow the schedule again, and also listen to the slider again. "
                            : "on? This will cause the thermostat to stop following the schedule, and " +
                            "the thermostat will not respond to the slider anymore."))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            setVacationMode(!holiday);

                            putVacationMode(holiday);

                            Bitmap bb;
                            if(holiday){
                                bb = BitmapFactory.decodeResource(getResources(), R.drawable.airplanetakeoff);
                            }else{
                                bb = BitmapFactory.decodeResource(getResources(), R.drawable.airplanedown);
                            }

                            ((ImageButton) v).setImageBitmap(bb);

                            if(t != null && t.getView().getWindowVisibility() == View.VISIBLE){
                                t.cancel();
                            }
                            t = Toast.makeText(v.getRootView().getContext(), "Vacation mode is put " + (holiday ? "on" : "off") + "!", Toast.LENGTH_SHORT);
                            t.show();

                        }})
                    .setNegativeButton(android.R.string.no, null).show();




        }
    }

    public void putVacationMode(final boolean b){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (b) {
                        HeatingSystem.put("weekProgramState", "off");
                    } else {
                        HeatingSystem.put("weekProgramState", "on");
                    }
                }catch(Exception e){
                    Log.d("Error", e.getMessage());
                }
            }
        }).start();

    }

    public void setVacationMode(boolean b){
        holiday = b;

        ((ThrottleSlider)getActivity().findViewById(R.id.throttle)).activated = !b;
        ((ThrottleSlider)getActivity().findViewById(R.id.throttle)).invalidate();
    }
}
