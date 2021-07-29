package com.example.chartify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class BarChart extends View {

    public ChartData data;
    private int size;

    ArrayList<RectF> RectArr;

    Paint barPaint;
    Paint gridPaint;
    Paint labelPaint;

    public void setData(ChartData data){
        this.data = data;
        this.size = data.size();

        for(int i=0;i<size;i++){
            RectArr.add(new RectF());
        }
    }

    public BarChart(Context context) {
        super(context);

        init(null);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        int MY_DP_VALUE, pixel;

        RectArr = new ArrayList<>();

        MY_DP_VALUE = 15; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        barPaint = new Paint();
        barPaint.setColor(Color.parseColor("#fca103"));
        barPaint.setTextSize(pixel);                                       //40

        labelPaint = new Paint();
        labelPaint.setColor(Color.DKGRAY);
        labelPaint.setTextSize(pixel);                                     //40
        labelPaint.setTextAlign(Paint.Align.CENTER);

        MY_DP_VALUE = 11; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        gridPaint = new Paint();
        gridPaint.setColor(Color.DKGRAY);
        gridPaint.setTextSize(pixel);                                      //30
        gridPaint.setTextAlign(Paint.Align.RIGHT);
    }

    private float calcGridLabel(float hMax){
        float unitGrid;
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
            unitGrid = near10multiple;
        }
        else if(near10multiple/2 >= hMax/6 && near10multiple/2 <= hMax/5){
            unitGrid = near10multiple/2;
        }
        else if(near10multiple/5 >= hMax/6 && near10multiple/5 <= hMax/5){
            unitGrid = near10multiple/5;
        }
        else if(near10multiple/10 >= hMax/6 && near10multiple/10 <= hMax/5){
            unitGrid = near10multiple/10;
        }
        else if(0.3*near10multiple >= hMax/6 && 0.3*near10multiple <= hMax/5){
            unitGrid = (float) (0.3*near10multiple);
        }
        else if(0.4*near10multiple >= hMax/6 && 0.4*near10multiple <= hMax/5){
            unitGrid = (float) (0.4*near10multiple);
        }
        else if(0.6*near10multiple >= hMax/6 && 0.6*near10multiple <= hMax/5){
            unitGrid = (float) (0.6*near10multiple);
        }
        else if(0.7*near10multiple >= hMax/6 && 0.7*near10multiple <= hMax/5){
            unitGrid = (float) (0.7*near10multiple);
        }
        else if(0.8*near10multiple >= hMax/6 && 0.8*near10multiple <= hMax/5){
            unitGrid = (float) (0.8*near10multiple);
        }
        else if(0.9*near10multiple >= hMax/6 && 0.9*near10multiple <= hMax/5){
            unitGrid = (float) (0.9*near10multiple);
        }
        else{
            if(near10multiple >= hMax/5 && near10multiple <= hMax/4){
                unitGrid = near10multiple;
            }
            else if(near10multiple/2 >= hMax/5 && near10multiple/2 <= hMax/4){
                unitGrid = near10multiple/2;
            }
            else if(near10multiple/5 >= hMax/5 && near10multiple/5 <= hMax/4){
                unitGrid = near10multiple/5;
            }
            else if(near10multiple/10 >= hMax/5 && near10multiple/10 <= hMax/4){
                unitGrid = near10multiple/10;
            }
            else if(0.3*near10multiple >= hMax/5 && 0.3*near10multiple <= hMax/4){
                unitGrid = (float) (0.3*near10multiple);
            }
            else if(0.4*near10multiple >= hMax/5 && 0.4*near10multiple <= hMax/4){
                unitGrid = (float) (0.4*near10multiple);
            }
            else if(0.6*near10multiple >= hMax/5 && 0.6*near10multiple <= hMax/4){
                unitGrid = (float) (0.6*near10multiple);
            }
            else if(0.7*near10multiple >= hMax/5 && 0.7*near10multiple <= hMax/4){
                unitGrid = (float) (0.7*near10multiple);
            }
            else if(0.8*near10multiple >= hMax/5 && 0.8*near10multiple <= hMax/4){
                unitGrid = (float) (0.8*near10multiple);
            }
            else if(0.9*near10multiple >= hMax/5 && 0.9*near10multiple <= hMax/4){
                unitGrid = (float) (0.9*near10multiple);
            }
            else{
                unitGrid = (float) (hMax/5.454);
            }
        }

        return unitGrid;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //CONFIGURE DIMENSIONS
        float chartWidth = (float) (0.8*getWidth());
        float chartHeight = (float) (0.7*getHeight());
        float barWidth = (float) (chartWidth/(1.2*size - 0.2));
        float barSpacing = (float) (0.2*barWidth);
        float baseY = (float) (0.85*getHeight());
        float leftX = (float) (0.1*getWidth());
        float unitGrid = calcGridLabel(data.getMax());
        float scaleFactor = chartHeight/6/unitGrid;
        int unitGrid_ = 0;
        if(unitGrid%1 == 0.0){
            unitGrid_ = (int) unitGrid;
        }
        else{
            gridPaint.setTextSize(15);
        }


        //DRAW GRID LINES AND GRID LABELS
        for(int i = 0; i<7; i++){
            canvas.drawLine(leftX-20,baseY-(i*chartHeight/6),leftX+chartWidth+20,baseY-(i*chartHeight/6),gridPaint);

            if(unitGrid_ != 0){
                canvas.drawText(String.valueOf((int) i*unitGrid_),leftX-30, baseY+10-(i*chartHeight/6), gridPaint);
            }
            else{
                canvas.drawText(String.valueOf(i*unitGrid),leftX-30, baseY+10-(i*chartHeight/6), gridPaint);
            }
        }

        canvas.drawLine(leftX-20,baseY,leftX-20,baseY-chartHeight,gridPaint);

        canvas.drawLine(leftX+chartWidth+20,baseY,leftX+chartWidth+20,baseY-chartHeight,gridPaint);


        //CONSTRUCT BARS
        for(int i=0;i<size;i++){
            RectArr.get(i).bottom = baseY;
            RectArr.get(i).left = (float) (leftX + i * (barWidth + barSpacing));
            RectArr.get(i).right = RectArr.get(i).left + barWidth;
            RectArr.get(i).top = baseY - (float) ((data.getData(i).y)*scaleFactor);
        }

        for(RectF myrect: RectArr){
            canvas.drawRect(myrect, barPaint);
        }


        //DRAW BAR LABELS
        gridPaint.setTextSize(30);
        for(int i=0;i<size;i++){
            canvas.save();
            canvas.rotate(-90,RectArr.get(i).left + barWidth/2 + 5, baseY+20);
            canvas.drawText(data.getData(i).x,RectArr.get(i).left + barWidth/2 + 5, baseY+20, gridPaint);
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
