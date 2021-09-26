package com.example.coronatracker.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class CountryInfo implements Parcelable {
        public int _id;
        public String iso2;
        public String iso3;
        public double lat;
        public double longs;
        public String flag;


        protected CountryInfo(Parcel in) {
                _id = in.readInt();
                iso2 = in.readString();
                iso3 = in.readString();
                lat = in.readDouble();
                longs = in.readDouble();
                flag = in.readString();
        }

        public static final Creator<CountryInfo> CREATOR = new Creator<CountryInfo>() {
                @Override
                public CountryInfo createFromParcel(Parcel in) {
                        return new CountryInfo(in);
                }

                @Override
                public CountryInfo[] newArray(int size) {
                        return new CountryInfo[size];
                }
        };

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(_id);
                dest.writeString(iso2);
                dest.writeString(iso3);
                dest.writeDouble(lat);
                dest.writeDouble(longs);
                dest.writeString(flag);
        }
}
