package com.example.chartify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        Intent intent  = getIntent();
        ChartData myChartData  = (ChartData) intent.getParcelableExtra("chart_data");

        BarChart myBarChart = findViewById(R.id.myBarChart);
        myBarChart.setData(myChartData);
    }
}