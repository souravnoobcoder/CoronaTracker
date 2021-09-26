package com.example.coronatracker.room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "offlineContacts", primaryKeys = {})
public class indiaStateModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int key;
    private final String loc;
    private final int confirmedCasesForeign;
    private final int discharged;
    private final int deaths;
    private final int totalConfirmed;
    private final String number;
    private final int activeCases;

    public indiaStateModel(String loc, int confirmedCasesForeign, int discharged, int deaths,
                           int totalConfirmed, String number, int activeCases) {
        this.loc = loc;
        this.confirmedCasesForeign = confirmedCasesForeign;
        this.discharged = discharged;
        this.deaths = deaths;
        this.totalConfirmed = totalConfirmed;
        this.number = number;
        this.activeCases = activeCases;
    }
    @Ignore
    protected indiaStateModel(Parcel in) {
        key = in.readInt();
        loc = in.readString();
        confirmedCasesForeign = in.readInt();
        discharged = in.readInt();
        deaths = in.readInt();
        totalConfirmed = in.readInt();
        number = in.readString();
        activeCases = in.readInt();
    }
    @Ignore
    public static final Creator<indiaStateModel> CREATOR = new Creator<indiaStateModel>() {
        @Override
        public indiaStateModel createFromParcel(Parcel in) {
            return new indiaStateModel(in);
        }

        @Override
        public indiaStateModel[] newArray(int size) {
            return new indiaStateModel[size];
        }
    };

    public String getLoc() {
        return loc;
    }

    public int getConfirmedCasesForeign() {
        return confirmedCasesForeign;
    }

    public int getDischarged() {
        return discharged;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public String getNumber() {
        return number;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeString(loc);
        dest.writeInt(confirmedCasesForeign);
        dest.writeInt(discharged);
        dest.writeInt(deaths);
        dest.writeInt(totalConfirmed);
        dest.writeString(number);
        dest.writeInt(activeCases);
    }
}
