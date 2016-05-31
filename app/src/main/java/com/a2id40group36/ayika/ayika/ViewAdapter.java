package com.a2id40group36.ayika.ayika;

import com.a2id40group36.ayika.ayika.HomeActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by D Kortleven on 30/05/2016.
 */
public class ViewAdapter extends FragmentPagerAdapter  {

    public ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new HomeActivity();
            case 1:
                return new WeekProgramActivity();
            case 2:
                return new SettingsActivity();
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }


}
