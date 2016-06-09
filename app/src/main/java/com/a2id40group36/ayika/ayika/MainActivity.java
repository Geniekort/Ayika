package com.a2id40group36.ayika.ayika;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
//
    private ViewPager viewPager;
    ViewAdapter mAdapter;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ViewAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);



    }


    public void pauseSwipe(boolean b){
        Log.d("DEBUG", "Pausing");
        ((StopViewPager) findViewById(R.id.pager)).setPagingEnabled(b);
    }

    public void pauseScroll(boolean b){
        Log.d("DEBUG", "Pausing");
        ((LockableScrollView) findViewById(R.id.scrollViewSchedule)).setScrollingEnabled(b);
    }



}
