package com.a2id40group36.ayika.ayika;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.os.Vibrator;
import android.widget.Toast;

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

    private final String tooMuchNodesMsg = "Sorry, you can only have five nodes for the night temperature line, and five nodes for the day temperature line. Please first delete a node";
    private final String nodeDeletedMsg = "A node was deleted!";

    private boolean editState, attached;
    private float touchX;
    private float touchY;
    private int[] attachedNode;
    private float attachedY;
    private float attachedTime;
    private float attachDist = 100;

    private boolean thrDel; // If node is threathend to be deleted
    private float thrTime;

    private final float moveThreshold = 50;

    private final float hourBarPerc = (float) 0.20; //indicates the width of the first column with hours in percentage
    private final float headerBarPerc = 1-hourBarPerc; // the rest of the columns :)
    private float axisX; //To be set to the real X value of the axis separateing labels and the graph
    private final float hourRowHeight = 200; //height of every hour row in pixels
    private final float headerHeight = 120; //height of header bar in pixels
    float[] linesX;
    float deleteX, deleteWidth = 50;


    private final float nightDayLinesDist = (float)0.47; // The relative distance of between the day and night lines.
                                                        // (Relative to the graph part, so not including the hour labelss)

    private final int lineColor = Color.rgb(230,230,230);
    private final int lightlineColor = Color.argb(160, 230,230,230);
    private final int nightLineColor = Color.argb(155,102,150,186);
    private final int dayLineColor = Color.argb(155,231,165,83);
    private final int nightNodeColor = Color.argb(255,102,150,186);
    private final int dayNodeColor = Color.argb(255,231,165,83);
    private final int dragTimeLabelColor = Color.argb(200,255,10,10);

    private final int textColor = Color.WHITE;

    private final float line1Width = 5;
    private final float line2Width = 3;
    private final float graphLineWidth = 10;
    private final float tempLineWidth = 20;
    private final float hourLabelTextSize = 50;
    private final float hourLabelTextSizeS = 40;
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

        thrDel = false;
        thrTime = -1;
        attachedNode = new int[2]; //0 is for night/day node, 1 is for index in that row of nodes
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float height = this.getMeasuredHeight();
        float width = this.getMeasuredWidth();
        float hoursHeight = 24 * hourRowHeight;
        deleteX = this.getMeasuredWidth() - deleteWidth;

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

            if(!(tempY == attachedY && attached)){//To remove the label that is overlapped by the draglabel
                textDrawer.setTextSize(hourLabelTextSize);
                canvas.drawText(i + ":00", axisX - 5, tempY + hourLabelTextSize/2, textDrawer);
            }


            lineDrawer.setColor(lightlineColor);
            tempY += .5 * hourRowHeight;

            canvas.drawLine(axisX, tempY, width, tempY, lineDrawer);
            if(!(tempY == attachedY && attached)) {//To remove the label that is overlapped by the draglabel
                textDrawer.setTextSize(hourLabelTextSizeS);
                canvas.drawText(i + ":30", axisX - 5, tempY + hourLabelTextSizeS / 2, textDrawer);
            }
        }


        int dayNC = 0, nightNC = 0; // NodeCounters, to keep track of which nodes already are drawn

        float prevX = -1, prevY = -1;
        boolean firstNode = true;
        float nodeY = 0;
        float nodeX = 0;


        //Draw all the nodes and the lines in between
        while(!(dayNC >= dayNodes.size() && nightNC >= nightNodes.size())){

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


            //Set the right color and coordinates
            if(dayN){
                lineDrawer.setColor(dayNodeColor);
                nodeX = linesX[1];
                nodeY = headerHeight + dayNodes.get(dayNC++) * hourRowHeight;
            }else{
                lineDrawer.setColor(nightNodeColor);
                nodeX = linesX[0];
                nodeY = headerHeight + nightNodes.get(nightNC++) * hourRowHeight;
            }

            if(firstNode){
                int presColor = lineDrawer.getColor(); //To preserve the color;
                firstNode = false;
                lineDrawer.setStyle(Style.FILL_AND_STROKE);
                lineDrawer.setStrokeWidth(tempLineWidth);
                lineDrawer.setColor(nightNodeColor);
                canvas.drawLine(linesX[0], headerHeight, linesX[0], nodeY + tempLineWidth/2, lineDrawer);
                canvas.drawLine(linesX[0], nodeY, nodeX, nodeY , lineDrawer);

                lineDrawer.setColor(presColor);
            }


            float addX = 0, addY = 0;
            if(editState) {
                addX = (r.nextFloat() - r.nextFloat()) * r.nextInt(15);
                addY = (r.nextFloat() - r.nextFloat()) * r.nextInt(15);
                invalidate();
            }
            lineDrawer.setStyle(Style.FILL);
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

            while(dayNC < dayNodes.size() && dayNodes.get(dayNC) == -1) dayNC++; // To skip over all undefined nodes
            while(nightNC < nightNodes.size() && nightNodes.get(nightNC) == -1) nightNC++; // To skip over all undefined nodes

        }

        if(prevX == linesX[0])
            lineDrawer.setColor(nightNodeColor);
        else lineDrawer.setColor(dayNodeColor);
        // Draw the line to midnight
        canvas.drawLine(prevX, prevY, prevX, headerHeight+hoursHeight, lineDrawer);



        // If long touched, and therefore in editstate
        if(editState){
            lineDrawer.setColor(Color.argb(200,255,10,10));
            lineDrawer.setStyle(Style.FILL);
            if(thrDel){
                canvas.drawRect((float)(deleteX-deleteWidth), headerHeight, width, headerHeight+hoursHeight, lineDrawer);
            }else{
                canvas.drawRect((float)(deleteX-.5*deleteWidth), headerHeight, width, headerHeight+hoursHeight, lineDrawer);
            }

            if(attached){
                textDrawer.setColor(dragTimeLabelColor);
                textDrawer.setTextSize(hourLabelTextSize + 20);
                textDrawer.setFakeBoldText(true);
                textDrawer.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText((int)Math.floor(attachedTime) + ":" +
                        ((int)((attachedTime % 1)*60) == 0 ? "00" : (int)((attachedTime % 1)*60))
                        , axisX, attachedY + textDrawer.getTextSize()/2, textDrawer );
            }
        }
    }


    private void sortNodes(){
        float attachedValue = -1;
        if(attachedNode != null){
            if(attachedNode[0] == 0){
                attachedValue = nightNodes.get(attachedNode[1]);
            }else if(attachedNode[0] == 1){
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
            }else if (attachedNode[0] == 1){
                for(int i = 0; i < dayNodes.size(); i++){
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
        }else{
            editState = false;
            m.pauseSwipe(true);
        }
    }

    public void setAttachState(boolean b){
        if(b){
            attached = true;
            m.pauseScroll(false);
        }else{
            attached = false;
            m.pauseScroll(true);
        }
    }
    public void updateAttachedNode(MotionEvent me){
        float newx, newtime;
        float mx = me.getX(), my = me.getY();
        float nlinedist = Math.abs(mx - linesX[0]); //Absolute distance from touch to nightline
        float dlinedist = Math.abs(mx - linesX[1]); //Absolute distance from touch to nightline
        float delboxdist = Math.abs(mx - deleteX);

        int prevX = attachedNode[0]; //Check if the attached node is on a nightline

        float[] xes = new float[]{nlinedist,dlinedist,delboxdist};
        int closestXCase = getSmallest(xes);

        //Correct the mouseY for the bounds of the graph
        if(my < headerHeight){
            my = headerHeight;
        }else if(my > headerHeight + 24 * hourRowHeight){
            my = headerHeight + 24 * hourRowHeight;
        }

        newtime = (my - headerHeight)/hourRowHeight;

        switch(closestXCase){
            case 0: // In case the mouseX is into the nightline
                if(prevX != 0){
                    if(prevX == 1){
                        dayNodes.remove(attachedNode[1]); // emove the node from the day and put it to the night
                    }else if(prevX == 2){
                        thrDel = false;
                    }
                    nightNodes.add(newtime);
                    attachedNode[0] = 0;
                    attachedNode[1] = nightNodes.size()-1;
                }
                break;
            case 1: // In case the mouseX is into the dayline
                if(prevX != 1){
                    if(prevX == 0){
                        nightNodes.remove(attachedNode[1]); // emove the node from the day and put it to the night
                    }else if(prevX == 2){
                        thrDel = false;
                    }
                    dayNodes.add(newtime);
                    attachedNode[0] = 1;
                    attachedNode[1] = dayNodes.size()-1;
                }
                break;
            case 2: // In case the mouseX is into the delbox
                if(prevX != 2){
                    if(prevX == 0){
                        nightNodes.remove(attachedNode[1]); // emove the node from the day and put it to the night
                    }else if(prevX == 1){
                        dayNodes.remove(attachedNode[1]); // emove the node from the day and put it to the night
                    }
                    thrDel = true;
                    thrTime = newtime;
                    attachedNode[0] = 2;
                    attachedNode[1] = 0;
                }
                break;
        }




        if(attachedNode[0] == 0){
            nightNodes.set(attachedNode[1], newtime);
        }else if(attachedNode[0] == 1) {
            dayNodes.set(attachedNode[1], newtime);
        }


        attachedTime = roundForQuarters((my - headerHeight)/hourRowHeight);
        attachedY = attachedTime * hourRowHeight + headerHeight;
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

    private float roundForQuarters(float inp){
        // Function returns the value rounded to the closest quarter value .25, .75, .50 or .00
        float t = (float) (inp % 0.25);
        if(t >= 0.25/2){
            return (float)(inp + .25 - t);
        }else{
            return (float)(inp - t);
        }

    }

    private int getSmallest(float[] input){
        // Function returns index of smallest value in inputarray
        float smallest = Float.MAX_VALUE;
        int smalind= -1;

        for(int i = 0; i < input.length; i++){
           if(input[i] < smallest){
               smallest = input[i];
               smalind = i;
           }
        }

        return smalind;
    }
    private void addNode(MotionEvent me){
        if(!editState){
            float mx = me.getX(), my = me.getY();
            float nlinedist = Math.abs(mx - linesX[0]); //Absolute distance from touch to nightline
            float dlinedist = Math.abs(mx - linesX[1]); //Absolute distance from touch to nightline

            float newtime = (my - headerHeight)/hourRowHeight;
            boolean noNodeAdd = false;

            if(nlinedist < dlinedist && nlinedist < attachDist){
                if(nightNodes.size() < 5){
                    nightNodes.add(newtime);
                }else{
                    noNodeAdd = true;
                }
            }else if( dlinedist < attachDist) {
                if(nightNodes.size() < 5){
                    dayNodes.add(newtime);
                }else{
                    noNodeAdd = true;
                }
            }

            if(noNodeAdd){
                Toast t = Toast.makeText(m.getApplicationContext(), tooMuchNodesMsg, Toast.LENGTH_LONG);
                t.show();
            }

            sortNodes();
            invalidate();
        }

        return;
    }

    public void attachToNode(MotionEvent e){
        attachedNode = getClosestNode(e.getX(), e.getY());
        if(attachedNode != null){
            setAttachState(true);
            Log.d("DEBUG", attachedNode[0] + "," + attachedNode[1]);
            attachedY = (float) e.getY();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        if(attached){
            if(me.getAction() == MotionEvent.ACTION_UP){
                setAttachState(false);
                if(thrDel){
                    thrDel = false;
                    Toast t = Toast.makeText(m.getApplicationContext(), nodeDeletedMsg, Toast.LENGTH_LONG);
                    t.show();
                }
                //
                if(attachedNode[0] == 0){
                    nightNodes.set(attachedNode[1], attachedTime);
                }else if (attachedNode[0] == 1){
                    dayNodes.set(attachedNode[1], attachedTime);
                }
            }

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
        if(editState && !isNodeCoordinate(e.getX(),e.getY())){
            setEditState(false);

            setAttachState(false);
        }
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
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
        }
        invalidate();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }


}
