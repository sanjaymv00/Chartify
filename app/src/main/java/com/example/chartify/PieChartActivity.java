package com.example.chartify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        Intent intent  = getIntent();
        ChartData myChartData  = (ChartData) intent.getParcelableExtra("chart_data");

        PieChart myPieChart = findViewById(R.id.myPieChart);
        myPieChart.setData(myChartData);
    }
}