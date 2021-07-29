package com.example.chartify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LineChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        Intent intent  = getIntent();
        ChartData myChartData  = (ChartData) intent.getParcelableExtra("chart_data");

        LineChart myLineChart = findViewById(R.id.myLineChart);
        myLineChart.setData(myChartData);
    }
}