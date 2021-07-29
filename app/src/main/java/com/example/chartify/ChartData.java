package com.example.chartify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ChartData implements Parcelable {

    public ArrayList<DataEntry> dataArr;
    public String label_x;
    public String label_y;

    public ChartData(){

    }

    protected ChartData(Parcel in) {
        dataArr = in.createTypedArrayList(DataEntry.CREATOR);
        label_x = in.readString();
        label_y = in.readString();
    }

    public static final Creator<ChartData> CREATOR = new Creator<ChartData>() {
        @Override
        public ChartData createFromParcel(Parcel in) {
            return new ChartData(in);
        }

        @Override
        public ChartData[] newArray(int size) {
            return new ChartData[size];
        }
    };

    public void setData(ArrayList<DataEntry> data){
        this.dataArr = data;
    }

    public DataEntry getData(int index){
        return dataArr.get(index);
    }

    public int size(){
        return dataArr.size();
    }

    public void setLabels(String label_x, String label_y){
        this.label_x = new String(label_x);
        this.label_y = new String(label_y);
    }

    public float getMax(){
        float max = dataArr.get(0).y;

        for(int i = 1; i < size(); i++){
            if(dataArr.get(i).y>max){
                max = dataArr.get(i).y;
            }
        }
        return max;
    }

    public float getSum(){
        float sum = 0;

        for(int i = 0; i < size(); i++){
            sum += dataArr.get(i).y;
        }
        return sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(dataArr);
        dest.writeString(label_x);
        dest.writeString(label_y);
    }
}
