package com.example.chartify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import static java.lang.Math.pow;

public class LineChart extends View {

    public ChartData data;
    private int size;

    Paint barPaint;
    Paint gridPaint;
    Paint labelPaint;

    public void setData(ChartData data){
        this.data = data;
        this.size = data.size();
    }

    public LineChart(Context context) {
        super(context);

        init(null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set){

        int MY_DP_VALUE, pixel;
        MY_DP_VALUE = 15; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        labelPaint = new Paint();
        labelPaint.setColor(Color.DKGRAY);
        labelPaint.setTextSize(40);                                         //40
        labelPaint.setTextAlign(Paint.Align.CENTER);

        barPaint = new Paint();
        barPaint.setColor(Color.parseColor("#fca103"));
        barPaint.setTextSize(40);                                           //40
        MY_DP_VALUE = 3; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        barPaint.setStrokeWidth(9);

        MY_DP_VALUE = 11; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        gridPaint = new Paint();
        gridPaint.setColor(Color.DKGRAY);
        gridPaint.setTextSize(30);                                          //30
        gridPaint.setTextAlign(Paint.Align.RIGHT);


    }

    private float calcGridY(float hMax){
        float unitGridY;
        int digits = 0;
        float hmaxnew = (float) (hMax/5.454);

        while(hmaxnew>=1){
            digits++;
            hmaxnew/=10;
        }
        hmaxnew = (float) (hMax/5.454);
        while(hmaxnew<1){
            digits--;
            hmaxnew*=10;
        }

        float near10multiple = (float) pow(10,digits);
        if(near10multiple >= hMax/6 && near10multiple <= hMax/5){
            unitGridY = near10multiple;
        }
        else if(near10multiple/2 >= hMax/6 && near10multiple/2 <= hMax/5){
            unitGridY = near10multiple/2;
        }
        else if(near10multiple/5 >= hMax/6 && near10multiple/5 <= hMax/5){
            unitGridY = near10multiple/5;
        }
        else if(near10multiple/10 >= hMax/6 && near10multiple/10 <= hMax/5){
            unitGridY = near10multiple/10;
        }
        else if(0.3*near10multiple >= hMax/6 && 0.3*near10multiple <= hMax/5){
            unitGridY = (float) (0.3*near10multiple);
        }
        else if(0.4*near10multiple >= hMax/6 && 0.4*near10multiple <= hMax/5){
            unitGridY = (float) (0.4*near10multiple);
        }
        else if(0.6*near10multiple >= hMax/6 && 0.6*near10multiple <= hMax/5){
            unitGridY = (float) (0.6*near10multiple);
        }
        else if(0.7*near10multiple >= hMax/6 && 0.7*near10multiple <= hMax/5){
            unitGridY = (float) (0.7*near10multiple);
        }
        else if(0.8*near10multiple >= hMax/6 && 0.8*near10multiple <= hMax/5){
            unitGridY = (float) (0.8*near10multiple);
        }
        else if(0.9*near10multiple >= hMax/6 && 0.9*near10multiple <= hMax/5){
            unitGridY = (float) (0.9*near10multiple);
        }
        else{
            if(near10multiple >= hMax/5 && near10multiple <= hMax/4){
                unitGridY = near10multiple;
            }
            else if(near10multiple/2 >= hMax/5 && near10multiple/2 <= hMax/4){
                unitGridY = near10multiple/2;
            }
            else if(near10multiple/5 >= hMax/5 && near10multiple/5 <= hMax/4){
                unitGridY = near10multiple/5;
            }
            else if(near10multiple/10 >= hMax/5 && near10multiple/10 <= hMax/4){
                unitGridY = near10multiple/10;
            }
            else if(0.3*near10multiple >= hMax/5 && 0.3*near10multiple <= hMax/4){
                unitGridY = (float) (0.3*near10multiple);
            }
            else if(0.4*near10multiple >= hMax/5 && 0.4*near10multiple <= hMax/4){
                unitGridY = (float) (0.4*near10multiple);
            }
            else if(0.6*near10multiple >= hMax/5 && 0.6*near10multiple <= hMax/4){
                unitGridY = (float) (0.6*near10multiple);
            }
            else if(0.7*near10multiple >= hMax/5 && 0.7*near10multiple <= hMax/4){
                unitGridY = (float) (0.7*near10multiple);
            }
            else if(0.8*near10multiple >= hMax/5 && 0.8*near10multiple <= hMax/4){
                unitGridY = (float) (0.8*near10multiple);
            }
            else if(0.9*near10multiple >= hMax/5 && 0.9*near10multiple <= hMax/4){
                unitGridY = (float) (0.9*near10multiple);
            }
            else{
                unitGridY = (float) (hMax/5.454);
            }
        }

        return unitGridY;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //CONFIGURE DIMENSIONS
        float chartWidth = (float) (0.8*getWidth());
        float chartHeight = (float) (0.6*getHeight());
        float baseY = (float) (0.8*getHeight());
        float leftX = (float) (0.1*getWidth());
        float unitGridY = calcGridY(data.getMax());
        float scaleFactorY = chartHeight/6/unitGridY;
        int unitGrid_Y = 0;
        if(unitGridY%1 == 0.0){
            unitGrid_Y = (int) unitGridY;
        }
        else{
            gridPaint.setTextSize(15);
        }
        float unitGridX = chartWidth/size;



        //DRAW GRID LINES AND GRID LABELS
        for(int i = 0; i<7; i++){
            canvas.drawLine(leftX-20,baseY-(i*chartHeight/6),leftX+chartWidth+20,baseY-(i*chartHeight/6),gridPaint);

            if(unitGrid_Y != 0){
                canvas.drawText(String.valueOf((int) i*unitGrid_Y),leftX-30, baseY+10-(i*chartHeight/6), gridPaint);
            }
            else{
                canvas.drawText(String.valueOf(i*unitGridY),leftX-30, baseY+10-(i*chartHeight/6), gridPaint);
            }
        }

        canvas.drawLine(leftX-20,baseY,leftX-20,baseY-chartHeight,gridPaint);

        canvas.drawLine(leftX+chartWidth+20,baseY,leftX+chartWidth+20,baseY-chartHeight,gridPaint);


        //CONSTRUCT CHART LINES
        for(int i=0;i<size-1;i++){
            canvas.drawLine((float)(leftX + (i+0.5)*unitGridX), baseY - (data.getData(i).y)*scaleFactorY, (float)(leftX + (i+1.5)*unitGridX), baseY - (data.getData(i+1).y)*scaleFactorY, barPaint);
        }


        //DRAW BAR LABELS & CIRCLE AT POINTS
        gridPaint.setTextSize(30);
        for(int i=0;i<size;i++){
            canvas.drawCircle((float)(leftX + (i+0.5)*unitGridX),baseY - (data.getData(i).y)*scaleFactorY, 12, barPaint);
            canvas.save();
            canvas.rotate(-90, (float) (leftX + (i+0.5) * unitGridX + 7), baseY+20);
            canvas.drawText(data.getData(i).x,(float) (leftX + (i+0.5) * unitGridX + 7), baseY+20, gridPaint);
            canvas.restore();
        }


        //DRAW AXIS LABELS
        canvas.drawText(data.label_x.toUpperCase(), (float) (0.5*getWidth()), (float) (0.98*getHeight()), labelPaint);
        canvas.save();
        canvas.rotate(-90, (float) (0.97*getWidth()), (float) (0.5*getHeight()));
        canvas.drawText(data.label_y.toUpperCase(),(float) (0.97*getWidth()), (float) (0.5*getHeight()), labelPaint);
        canvas.restore();

    }
}
