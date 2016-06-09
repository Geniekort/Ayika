package com.a2id40group36.ayika.ayika;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;



public class StopViewPager extends ViewPager {

    private boolean isPagingEnabled = true;

    public StopViewPager(Context context) {
        super(context);
    }

    public StopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
        Log.d("DEBUG", "Enable/Disable" + isPagingEnabled);
    }
}