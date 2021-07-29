package com.example.chartify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class SetDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);

        LinearLayout datalayout = findViewById(R.id.layoutDataEntry);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //from intent
        Intent intent  = getIntent();
        ChartData myChartData  = (ChartData) intent.getParcelableExtra("chart_data");
        EditText xlabeltext = findViewById(R.id.xLabel);
        xlabeltext.setText(myChartData.label_x);
        EditText ylabeltext = findViewById(R.id.yLabel);
        ylabeltext.setText(myChartData.label_y);
        for(int i=0;i<myChartData.size();i++){
            View entrylayout = inflater.inflate(R.layout.data_entry_layout, null);
            EditText xValue = entrylayout.findViewById(R.id.xValue);
            xValue.setText(myChartData.getData(i).x);
            EditText yValue = entrylayout.findViewById(R.id.yValue);
            yValue.setText(String.valueOf(myChartData.getData(i).y));
            Button remove = entrylayout.findViewById(R.id.buttonRemove);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout mylayout = (LinearLayout) remove.getParent();
                    datalayout.removeView(mylayout);
                }
            });
            datalayout.addView(entrylayout);
        }

        findViewById(R.id.buttonAddData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout datalayout = findViewById(R.id.layoutDataEntry);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View entrylayout = inflater.inflate(R.layout.data_entry_layout, null);
                Button remove = entrylayout.findViewById(R.id.buttonRemove);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout mylayout = (LinearLayout) remove.getParent();
                        datalayout.removeView(mylayout);
                    }
                });
                datalayout.addView(entrylayout);
                entrylayout.requestFocus();
            }
        });

        findViewById(R.id.buttonSaveData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText xlabeltext = findViewById(R.id.xLabel);
                EditText ylabeltext = findViewById(R.id.yLabel);
                // validation
                if(xlabeltext.length()==0){
                    xlabeltext.setError("Required");
                    return;
                }
                if(ylabeltext.length()==0){
                    ylabeltext.setError("Required");
                    return;
                }

                ChartData mychart = new ChartData();
                mychart.label_x = xlabeltext.getText().toString();
                mychart.label_y = ylabeltext.getText().toString();

                final int children = datalayout.getChildCount();
                ArrayList<DataEntry> myData = new ArrayList<>();

                for(int i = 0;i<children;i++){
                    View entrylayout = datalayout.getChildAt(i);
                    EditText xvalue = entrylayout.findViewById(R.id.xValue);
                    if(xvalue.length()==0){
                        xvalue.setError("Required");
                        return;
                    }
                    String xvaluetext = xvalue.getText().toString();
                    EditText yvalue = entrylayout.findViewById(R.id.yValue);
                    if(yvalue.length()==0){
                        yvalue.setError("Required");
                        return;
                    }
                    String yvaluetext = yvalue.getText().toString();
                    myData.add(new DataEntry(xvaluetext ,Float.parseFloat(yvaluetext) ));
                }

                mychart.setData(myData);

                String string = mychart.label_x;
                string += mychart.label_y;

                for(int i = 0;i<mychart.size();i++){
                    string += mychart.getData(i).x;
                    string += mychart.getData(i).y;
                }
                Log.d("myTag", string);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("chart_data", mychart);
                startActivity(intent);
            }
        });

//        Toast.makeText(getApplicationContext(), "This is my Toast message!"+children,
//                Toast.LENGTH_LONG).show();
    }
}