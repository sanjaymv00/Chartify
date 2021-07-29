package com.example.chartify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), SetDataActivity.class);
                newIntent.putExtra("chart_data", getChartData());
                startActivity(newIntent);
            }
        });

        findViewById(R.id.buttonBarChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), BarChartActivity.class);
                newIntent.putExtra("chart_data", getChartData());
                startActivity(newIntent);
            }
        });

        findViewById(R.id.buttonLineChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), LineChartActivity.class);
                newIntent.putExtra("chart_data", getChartData());
                startActivity(newIntent);
            }
        });

        findViewById(R.id.buttonPieChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), PieChartActivity.class);
                newIntent.putExtra("chart_data", getChartData());
                startActivity(newIntent);
            }
        });
    }

    protected ChartData getChartData(){
        Intent intent  = getIntent();
        if(intent.hasExtra("chart_data")){
            ChartData myChartData  = (ChartData) intent.getParcelableExtra("chart_data");
            return myChartData;
        }
        else {
            ArrayList<DataEntry> myData = new ArrayList<>();
            myData.add(new DataEntry("Madison",13));
            myData.add(new DataEntry("Victoria",23));
            myData.add(new DataEntry("Jackson",27));
            myData.add(new DataEntry("Emerson",19));
            myData.add(new DataEntry("Washington",14));
            myData.add(new DataEntry("Mark",21));
            myData.add(new DataEntry("Franklin",17));

            ChartData myChartData = new ChartData();
            myChartData.setData(myData);
            myChartData.setLabels("Students", "Marks");

            return myChartData;
        }
    }
}