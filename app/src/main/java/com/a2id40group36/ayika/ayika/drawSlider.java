package com.a2id40group36.ayika.ayika;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mwape on 05.06.2016.
 */
public class drawSlider extends HomeActivity{
    Paint paint;
    Matrix matrix;
    SurfaceHolder holder;
    Bitmap bitmap; //the slider bitmap
    float x,y;
    ImageView imageView;
    TextView textView;
    String number;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bsliderw);
        x = 0;
        y = 0;


        return rootView;


    }

    public void onDraw(Canvas canvas)
    {
        Resources resources = getResources();

        Canvas canvas1 = new Canvas(bitmap.copy(Bitmap.Config.ARGB_8888, true));
        canvas1 = holder.lockCanvas();
        canvas1.drawARGB(250,250,250,250);
        canvas1.drawBitmap(bitmap, x, y, null);
        holder.unlockCanvasAndPost(canvas1);
    }

    public void pCounter(){
        int count = 0;


    }

    public void updateCOunter(){

    }




}
