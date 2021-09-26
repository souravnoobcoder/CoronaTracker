package com.example.coronatracker.dataClasses.indiaModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Regional implements Parcelable {
    public String loc;
    public int confirmedCasesIndian;
    public int confirmedCasesForeign;
    public int discharged;
    public int deaths;
    public int totalConfirmed;

    protected Regional(Parcel in) {
        loc = in.readString();
        confirmedCasesIndian = in.readInt();
        confirmedCasesForeign = in.readInt();
        discharged = in.readInt();
        deaths = in.readInt();
        totalConfirmed = in.readInt();
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
        dest.writeInt(confirmedCasesIndian);
        dest.writeInt(confirmedCasesForeign);
        dest.writeInt(discharged);
        dest.writeInt(deaths);
        dest.writeInt(totalConfirmed);
    }
}
