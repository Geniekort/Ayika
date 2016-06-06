package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.Random;

/**
 * Created by D Kortleven on 05/06/2016.
 */
public class ScheduleView extends View {

    private final Paint lineDrawer, textDrawer;
    private int counter;

    private boolean longTouch, editState;
    private float touchX;
    private float touchY;
    private final float moveThreshold = 50;

    private final float hourBarPerc = (float) 0.20; //indicates the width of the first column with hours in percentage
    private final float headerBarPerc = 1-hourBarPerc; // the rest of the columns :)
    private final float hourRowHeight = 100; //height of every hour row in pixels
    private final float headerHeight = 120; //height of header bar in pixels

    private final float nightDayLinesDist = (float)0.47; // The relative distance of between the day and night lines.
                                                        // (Relative to the graph part, so not including the hour labels)

    private final int lineColor = Color.rgb(230,230,230);
    private final int nightLineColor = Color.argb(155,102,150,186);
    private final int dayLineColor = Color.argb(155,231,165,83);
    private final int nightNodeColor = Color.argb(255,102,150,186);
    private final int dayNodeColor = Color.argb(255,231,165,83);

    private final int textColor = Color.WHITE;

    private final float line1Width = 5;
    private final float line2Width = 3;
    private final float graphLineWidth = 10;
    private final float tempLineWidth = 20;
    private final float hourLabelTextSize = 50;
    private final float tempLabelTextSize = 60;
    private final float nodeSize = 40;

    private float[] dayNodes = {7,12,15,17,23}, nightNodes = {0,10,14,16,22} ;

    private Random r = new Random();

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            longTouch = true;
            editState = true;
        }
    };


    public ScheduleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        lineDrawer = new Paint();
        textDrawer = new Paint();

    }

    public ScheduleView(Context context, AttributeSet attrs){
        super(context, attrs);
        lineDrawer = new Paint();
        textDrawer = new Paint();
        longTouch = false;
        editState = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float height = this.getMeasuredHeight();
        float width = this.getMeasuredWidth();
        float hoursHeight = 24 * hourRowHeight;

        float axisX = width * hourBarPerc;

        lineDrawer.setColor(lineColor);
        lineDrawer.setStyle(Style.STROKE);
        lineDrawer.setStrokeWidth(line1Width);

        canvas.drawLine(axisX, 0, axisX, hoursHeight + headerHeight, lineDrawer); //Draws the hour column separator line
        canvas.drawLine(axisX, headerHeight, width, headerHeight, lineDrawer); //Draws header bottom line


        float[] linesX = {axisX + (1-nightDayLinesDist)/2 * (width * headerBarPerc) , axisX + (1 - (1-nightDayLinesDist)/2) * (width * headerBarPerc)}; // X-coordinates of night and day lines


        // Draw the day and night lines
        lineDrawer.setStrokeWidth(line1Width);
        lineDrawer.setColor(nightLineColor);
        canvas.drawLine(linesX[0], headerHeight, linesX[0], headerHeight+hoursHeight, lineDrawer);

        textDrawer.setTextAlign(Paint.Align.CENTER);
        textDrawer.setTextSize(tempLabelTextSize);
        textDrawer.setColor(textColor);
        textDrawer.setStyle(Style.FILL);

        canvas.drawText("Night", linesX[0], headerHeight - textDrawer.getTextSize() + textDrawer.descent(), textDrawer );

        lineDrawer.setStrokeWidth(graphLineWidth);
        lineDrawer.setColor(dayLineColor);
        canvas.drawLine(linesX[1], headerHeight, linesX[1], headerHeight+hoursHeight, lineDrawer);
        canvas.drawText("Day", linesX[1], headerHeight - textDrawer.getTextSize() + textDrawer.descent(), textDrawer );


        //Draw all the hour lines
        lineDrawer.setColor(lineColor);
        lineDrawer.setStrokeWidth(line2Width);

        textDrawer.setTextSize(hourLabelTextSize);
        textDrawer.setTextAlign(Paint.Align.RIGHT);

        float tempY;
        for(int i = 0; i < 24; i++){
            tempY = headerHeight + i * hourRowHeight;
            canvas.drawLine(axisX, tempY, width, tempY, lineDrawer);

            canvas.drawText(i + ":00", axisX - 5, tempY + hourLabelTextSize/2, textDrawer);
        }


        int dayNC = 0, nightNC = 0; // NodeCounters, to keep track of which nodes already are drawn

        float prevX = -1, prevY = -1;
        
        while(!(dayNC >= dayNodes.length && nightNC >= nightNodes.length)){
            while(dayNC < dayNodes.length && dayNodes[dayNC] == -1) dayNC++;
            while(nightNC < nightNodes.length && nightNodes[nightNC] == -1) nightNC++;
            
            float nodeY = 0;
            float nodeX = 0;



            boolean dayN; //0 = night, 1 = day node has to be drawn
            if(dayNC < dayNodes.length){
                if(nightNC < nightNodes.length){
                    if(nightNodes[nightNC] < dayNodes[dayNC]){
                        dayN = false;
                    }else{
                        dayN = true;
                    }
                } else{
                    dayN = true;
                }
            }else{
                dayN = false;
            }

            lineDrawer.setStyle(Style.FILL);
            if(dayN){
                lineDrawer.setColor(dayNodeColor);
                nodeX = linesX[1];
                nodeY = headerHeight + dayNodes[dayNC++] * hourRowHeight;
            }else{
                lineDrawer.setColor(nightNodeColor);
                nodeX = linesX[0];
                nodeY = headerHeight + nightNodes[nightNC++] * hourRowHeight;
            }

            float addX = 0, addY = 0;
            if(editState) {
                addX = (r.nextFloat() - r.nextFloat()) * r.nextInt(15);
                addY = (r.nextFloat() - r.nextFloat()) * r.nextInt(15);
                invalidate();
            }
            canvas.drawCircle(nodeX + addX, nodeY + addY, nodeSize, lineDrawer);

            //Draw a line to the previous node
            if(!(prevX == -1 && prevY == -1)){
                if(prevX == linesX[0])
                    lineDrawer.setColor(nightNodeColor);
                else lineDrawer.setColor(dayNodeColor);

                lineDrawer.setStyle(Style.FILL_AND_STROKE);
                lineDrawer.setStrokeWidth(tempLineWidth);

                canvas.drawLine(prevX, prevY, prevX, nodeY + tempLineWidth/2, lineDrawer);
                canvas.drawLine(prevX, nodeY, nodeX, nodeY , lineDrawer);
            }


            prevX = nodeX;
            prevY = nodeY;
        }
        // If long touched
        if(editState){
            lineDrawer.setColor(Color.argb(200,255,10,10));
            lineDrawer.setStyle(Style.FILL);
            canvas.drawRect(width - 50, headerHeight, width, headerHeight+hoursHeight, lineDrawer);
        }
    }



    private void getNodes(){
        // Method to set the day and night nodes


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();

            handler.postDelayed(mLongPressed, 2000);
        }
        if((event.getAction() == MotionEvent.ACTION_UP)||(event.getAction() == MotionEvent.ACTION_MOVE) ){
            if(Math.abs(touchX - event.getX()) > moveThreshold &&  Math.abs(touchY - event.getY()) > moveThreshold){
                if (!longTouch) {
                    handler.removeCallbacks(mLongPressed);
                }
                longTouch = false;
                // CODE FOR SHORT TOUCH

            }
        }
        invalidate();
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();


        setMeasuredDimension(width,(int) (24 * hourRowHeight + headerHeight));
    }


}
