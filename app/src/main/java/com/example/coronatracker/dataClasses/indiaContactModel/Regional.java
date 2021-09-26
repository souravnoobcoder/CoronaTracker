package com.example.coronatracker.dataClasses.indiaContactModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Regional implements Parcelable,Comparable<Regional> {
    public String loc;
    public String number;

    protected Regional(Parcel in) {
        loc = in.readString();
        number = in.readString();
    }

    public static final Creator<Regional> CREATOR = new Creator<Regional>() {
        @Override
        public Regional createFromParcel(Parcel in) {
            return new Regional(in);
        }

        @Override
        public Regional[] newArray(int size) {
            return new Regional[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loc);
        dest.writeString(number);
    }

    @Override
    public int compareTo(Regional o) {
        return 0;
    }
}
