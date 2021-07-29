package com.example.chartify;

import android.os.Parcel;
import android.os.Parcelable;

public class DataEntry implements Parcelable {
    public String x;
    public float y;
    public DataEntry(String x, float y){
        this.x = x;
        this.y = y;
    }

    protected DataEntry(Parcel in) {
        x = in.readString();
        y = in.readFloat();
    }

    public static final Creator<DataEntry> CREATOR = new Creator<DataEntry>() {
        @Override
        public DataEntry createFromParcel(Parcel in) {
            return new DataEntry(in);
        }

        @Override
        public DataEntry[] newArray(int size) {
            return new DataEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(x);
        dest.writeFloat(y);
    }
}