package com.a2id40group36.ayika.ayika;

/**
 * Created by Mwape on 31.05.2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SliderActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView text = (TextView)findViewById(R.id.tempslidertext);
        text.setTextSize(20);
        Slider slider = (Slider) findViewById(R.id.tempslider);
        slider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int changeDegrees,
                                          boolean fromUser) {
                text.setText(""+changeDegrees);
            }
        });
    }
}


