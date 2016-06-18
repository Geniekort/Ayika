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

public class SettingsActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("DEBUG", "SETTINGSACTIVITY: ");
        super.setUserVisibleHint(isVisibleToUser);
        if(getActivity() != null) {
            ((Indicator)getActivity().findViewById(R.id.indicator)).invalidate();
            ((Indicator) getActivity().findViewById(R.id.indicator)).state = 0;
        }
    }

}
