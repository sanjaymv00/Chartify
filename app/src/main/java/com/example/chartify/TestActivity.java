package com.example.chartify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent  = getIntent();
        ChartData mychart  = (ChartData) intent.getParcelableExtra("chart_data");

        String string = mychart.label_x;
        string += mychart.label_y;

        for(int i = 0;i<mychart.size();i++){
            string += mychart.getData(i).x;
            string += mychart.getData(i).y;
        }
        Log.d("myTag", "This is my message");
        Toast.makeText(getApplicationContext(), string,
                Toast.LENGTH_LONG).show();
    }
}