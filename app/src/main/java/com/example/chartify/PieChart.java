package com.example.chartify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PieChart extends View {
    public ChartData data;
    private int size;

    RectF oval;
    ArrayList<Paint> piePaint;

    ArrayList<Float> startAngle;
    ArrayList<Float> sweepAngle;

    Paint percentPaint;
    Paint gridPaint;
    Paint labelPaint;

    DecimalFormat df;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setData(ChartData data) {
        this.data = data;
        this.size = data.size();

        float sum = data.getSum();
        for(int i = 0;i<data.size();i++){
            sweepAngle.add((float) ((data.getData(i).y) * 360.0 / sum));
        }
        float angleStart = 0;
        for(int i = 0;i<sweepAngle.size();i++){
            startAngle.add(angleStart);
            angleStart += sweepAngle.get(i);
        }

        int rgb;

        for(int i=0;i<sweepAngle.size();i++){
            rgb = 60;
            rgb +=(int)(i*195.0/data.size());
            piePaint.add(new Paint());
            piePaint.get(i).setColor(Color.rgb(255, rgb,0));
        }

        Log.d("mySizeeeeeeeeeeeeeeeee", String.valueOf(data.size()));
        Log.d("mySizeeeeeeeeeeeeeeeee1", String.valueOf(sweepAngle.size()));
        Log.d("mySizeeeeeeeeeeeeeeeee2", String.valueOf(sweepAngle.size()));
        Log.d("mySizeeeeeeeeeeeeeeeee3", String.valueOf(piePaint.size()));
    }

    private void init(@Nullable AttributeSet set){

        int MY_DP_VALUE, pixel;

        oval = new RectF();
        sweepAngle = new ArrayList<>();
        startAngle = new ArrayList<>();
        piePaint = new ArrayList<Paint>();

        MY_DP_VALUE = 10; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        percentPaint = new Paint();
        percentPaint.setColor(Color.DKGRAY);
        percentPaint.setTextSize(pixel);                //27
        percentPaint.setTextAlign(Paint.Align.CENTER);

        MY_DP_VALUE = 11; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        gridPaint = new Paint();
        gridPaint.setColor(Color.WHITE);
        gridPaint.setTextSize(pixel);                      //30
        MY_DP_VALUE = 4; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());
        gridPaint.setStrokeWidth(pixel);                   //10
        gridPaint.setTextAlign(Paint.Align.RIGHT);

        MY_DP_VALUE = 15; //dp to pixels code

        pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MY_DP_VALUE, getResources().getDisplayMetrics());

        labelPaint = new Paint();
        labelPaint.setColor(Color.DKGRAY);
        labelPaint.setTextSize(pixel);                     //40
        labelPaint.setTextAlign(Paint.Align.CENTER);

        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
    }

    public PieChart(Context context) {
        super(context);
        init(null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        float centerX = (float) (0.5*getWidth());
        float centerY = (float) (0.5*getHeight());
        float radius = (float) (0.35*getWidth());
        oval.right = (float) (centerX + radius);
        oval.left = (float) (centerX - radius);
        oval.top = (float) (centerY - radius);
        oval.bottom = (float) (centerY + radius);

        canvas.drawText(data.label_x.toUpperCase()+" - "+data.label_y.toUpperCase()+" DISTRIBUTION", centerX, (float) (0.85*getHeight()), labelPaint);

        canvas.save();
        canvas.rotate(-90,centerX, centerY);
        for(int i=0;i<startAngle.size();i++){
            canvas.drawArc(oval, startAngle.get(i), sweepAngle.get(i), true, piePaint.get(i));
        }
        canvas.restore();

        for(int i=0;i<startAngle.size();i++){
            canvas.drawLine(centerX,centerY, (float) (centerX + radius*sin(Math.toRadians(startAngle.get(i)))),(float) (centerY - radius*cos(Math.toRadians(startAngle.get(i)))),gridPaint);
            canvas.drawText(data.getData(i).x, (float) (centerX + 1.15*radius*sin(Math.toRadians(startAngle.get(i)+sweepAngle.get(i)/2))),(float) (centerY - 1.15*radius*cos(Math.toRadians(startAngle.get(i)+sweepAngle.get(i)/2))),labelPaint);
        }
        for(int i=0;i<startAngle.size();i++){
            canvas.drawText(String.valueOf(df.format(sweepAngle.get(i)*100/360))+"%", (float) (centerX + 0.7*radius*sin(Math.toRadians(startAngle.get(i)+sweepAngle.get(i)/2))),(float) (centerY - 0.7*radius*cos(Math.toRadians(startAngle.get(i)+sweepAngle.get(i)/2))),percentPaint);
        }
    }
}
