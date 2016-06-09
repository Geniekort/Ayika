package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by D Kortleven on 05/06/2016.
 */
public class ScheduleView extends View implements GestureDetector.OnGestureListener{

    private MainActivity m;
    private GestureDetector gDetector;
    private final Paint lineDrawer, textDrawer;
    private int counter;

    private boolean editState, attached;
    private float touchX;
    private float touchY;
    private int[] attachedNode;
    private float attachDist = 100;

    private final float moveThreshold = 50;

    private final float hourBarPerc = (float) 0.20; //indicates the width of the first column with hours in percentage
    private final float headerBarPerc = 1-hourBarPerc; // the rest of the columns :)
    private final float hourRowHeight = 200; //height of every hour row in pixels
    private final float headerHeight = 120; //height of header bar in pixels
    float[] linesX;

    private final float nightDayLinesDist = (float)0.47; // The relative distance of between the day and night lines.
                                                        // (Relative to the graph part, so not including the hour labelss)

    private final int lineColor = Color.rgb(230,230,230);
    private final int lightlineColor = Color.argb(160, 230,230,230);
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

    private ArrayList<Float> dayNodes = new ArrayList<Float>(), nightNodes = new ArrayList<Float>();

    private Random r = new Random();



    public ScheduleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        lineDrawer = new Paint();
        textDrawer = new Paint();
        gDetector = new GestureDetector(getContext(), this);
        m = (MainActivity) getContext();
        getNodes();
    }

    public ScheduleView(Context context, AttributeSet attrs){
        super(context, attrs);
        lineDrawer = new Paint();
        textDrawer = new Paint();
        editState = false;
        gDetector = new GestureDetector(getContext(), this);
        m = (MainActivity) getContext();
        getNodes();
        linesX = new float[2]; //Resp x of night line and of day line
        attachedNode = new int[2]; //0 is for night/day node, 1 is for index in that row of nodes
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

        linesX[0] = axisX + (1-nightDayLinesDist)/2 * (width * headerBarPerc);
        linesX[1] = axisX + (1 - (1-nightDayLinesDist)/2) * (width * headerBarPerc); // X-coordinates of night and day lines


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

        lineDrawer.setStrokeWidth(line2Width);

        textDrawer.setTextSize(hourLabelTextSize);
        textDrawer.setTextAlign(Paint.Align.RIGHT);

        float tempY;
        for(int i = 0; i < 24; i++){
            lineDrawer.setColor(lineColor);
            tempY = headerHeight + i * hourRowHeight;
            canvas.drawLine(axisX, tempY, width, tempY, lineDrawer);

            canvas.drawText(i + ":00", axisX - 5, tempY + hourLabelTextSize/2, textDrawer);

            lineDrawer.setColor(lightlineColor);
            tempY += .5 * hourRowHeight;
            canvas.drawLine(axisX, tempY, width, tempY, lineDrawer);

        }


        int dayNC = 0, nightNC = 0; // NodeCounters, to keep track of which nodes already are drawn

        float prevX = -1, prevY = -1;
        
        while(!(dayNC >= dayNodes.size() && nightNC >= nightNodes.size())){
            while(dayNC < dayNodes.size() && dayNodes.get(dayNC) == -1) dayNC++;
            while(nightNC < nightNodes.size() && nightNodes.get(nightNC) == -1) nightNC++;
            
            float nodeY = 0;
            float nodeX = 0;



            boolean dayN; //0 = night, 1 = day node has to be drawn
            if(dayNC < dayNodes.size()){
                if(nightNC < nightNodes.size()){
                    if(nightNodes.get(nightNC) < dayNodes.get(dayNC)){
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
                nodeY = headerHeight + dayNodes.get(dayNC++) * hourRowHeight;
            }else{
                lineDrawer.setColor(nightNodeColor);
                nodeX = linesX[0];
                nodeY = headerHeight + nightNodes.get(nightNC++) * hourRowHeight;
            }
////HIii
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


    private void sortNodes(){
        float attachedValue = -1;
        if(attachedNode != null){
            if(attachedNode[0] == 0){
                attachedValue = nightNodes.get(attachedNode[1]);
            }else{
                attachedValue = dayNodes.get(attachedNode[1]);
            }
        }

        Collections.sort(nightNodes);
        Collections.sort(dayNodes);


        if(attachedNode != null){
            if(attachedNode[0] == 0){
                for(int i = 0; i < nightNodes.size(); i++){
                    if(nightNodes.get(i) == attachedValue)
                        attachedNode[1] = i;
                }
            }else{
                for(int i = 0; i < nightNodes.size(); i++){
                    if(dayNodes.get(i) == attachedValue)
                        attachedNode[1] = i;
                }
            }
        }



    }

    private void getNodes(){
        // Method to set the day and night nodes
        dayNodes.add((float)7);
        dayNodes.add((float)12);
        dayNodes.add((float)15);
        dayNodes.add((float)17);
        dayNodes.add((float)23);

        nightNodes.add((float)0);
        nightNodes.add((float)10);
        nightNodes.add((float)13);
        nightNodes.add((float)16);
        nightNodes.add((float)20);


    }

    private int[] getClosestNode(float x, float y){
        int[] sol = {-1,-1};
        float mindist = Float.MAX_VALUE;
        float tempdist, nodeY;


        for(int i = 0; i < nightNodes.size(); i++){
            nodeY = headerHeight + nightNodes.get(i) * hourRowHeight;
            tempdist = (float) Math.sqrt(Math.pow(x-linesX[0],2) + Math.pow(y-nodeY, 2));
            if(tempdist < mindist){
                mindist = tempdist;
                sol[0] = 0;
                sol[1] = i;
            }
        }

        for(int i = 0; i < dayNodes.size(); i++){
            nodeY = headerHeight + dayNodes.get(i) * hourRowHeight;
            tempdist = (float) Math.sqrt(Math.pow(x-linesX[1],2) + Math.pow(y-nodeY, 2));
            if(tempdist < mindist){
                mindist = tempdist;
                sol[0] = 1;
                sol[1] = i;
            }
        }


        if(mindist < attachDist)
            return sol;
        else return null;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();


        setMeasuredDimension(width,(int) (24 * hourRowHeight + headerHeight));
    }

    public void setEditState(boolean b){
        if(b){
            editState = true;
            m.pauseSwipe(false);
            m.pauseScroll(false);
        }else{
            editState = false;
            m.pauseSwipe(true);
            m.pauseScroll(true);
        }
    }

    public void updateAttachedNode(MotionEvent me){
        float newx, newtime;
        float mx = me.getX(), my = me.getY();
        float nlinedist = Math.abs(mx - linesX[0]); //Absolute distance from touch to nightline
        float dlinedist = Math.abs(mx - linesX[1]); //Absolute distance from touch to nightline

        boolean night = attachedNode[0] == 0;


        newtime = (my - headerHeight)/hourRowHeight;

        if(nlinedist < dlinedist){ //if closer to nightline
            if(!night){
                dayNodes.remove(attachedNode[1]); // emove the node from the day and put it to the night
                nightNodes.add(newtime);
                attachedNode[0] = 0;
                attachedNode[1] = nightNodes.size()-1;
            }
        }else{// if closer to dayline
            if(night){
                nightNodes.remove(attachedNode[1]); // emove the node from the day and put it to the night
                dayNodes.add(newtime);
                attachedNode[0] = 1;
                attachedNode[1] = dayNodes.size()-1;
            }
        }

        night = attachedNode[0] == 0;

        if(night){
            nightNodes.set(attachedNode[1], newtime);
        }else {
            dayNodes.set(attachedNode[1], newtime);
        }
    }
    private boolean isNodeCoordinate(float x, float y){
        float nodeY;


        for(int i = 0; i < nightNodes.size(); i++){
            nodeY = headerHeight + nightNodes.get(i) * hourRowHeight;
            if((float) Math.sqrt(Math.pow(x-linesX[0],2) + Math.pow(y-nodeY, 2)) < attachDist){
                return true;
            }
        }

        for(int i = 0; i < dayNodes.size(); i++){
            nodeY = headerHeight + dayNodes.get(i) * hourRowHeight;
            if((float) Math.sqrt(Math.pow(x-linesX[1],2) + Math.pow(y-nodeY, 2)) < attachDist){
                return true;
            }
        }

        return false;


    }


    private void addNode(MotionEvent me){
        if(!editState){
            float mx = me.getX(), my = me.getY();
            float nlinedist = Math.abs(mx - linesX[0]); //Absolute distance from touch to nightline
            float dlinedist = Math.abs(mx - linesX[1]); //Absolute distance from touch to nightline

            float newtime = (my - headerHeight)/hourRowHeight;

            if(nlinedist < dlinedist){
                nightNodes.add(newtime);
            }else {
                dayNodes.add(newtime);
            }

            sortNodes();
            invalidate();
        }

        return;
    }

    public void attachToNode(MotionEvent e){
        attachedNode = getClosestNode(e.getX(), e.getY());
        if(attachedNode != null){
            attached = true;
            Log.d("DEBUG", attachedNode[0] + "," + attachedNode[1]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        if(attached){
            if(me.getAction() == MotionEvent.ACTION_UP)
                attached = false;
            else{
                updateAttachedNode(me);
                sortNodes();
            }
        }
        return gDetector.onTouchEvent(me);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(editState){
            attachToNode(e);
        }

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("DEBUG", "Press");
        return;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("DEBUG", "Tap");
        addNode(e);
        if(editState && !isNodeCoordinate(e.getX(),e.getY())) setEditState(false);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if(!attached) {
            Log.d("DEBUG", "Long press");

            setEditState(true);
            attachToNode(e);
        }
        invalidate();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }


}
