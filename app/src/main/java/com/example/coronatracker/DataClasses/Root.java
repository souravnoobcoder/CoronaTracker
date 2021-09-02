package com.example.coronatracker.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Root implements Parcelable {
        public long updated;
        public String country;
        public CountryInfo countryInfo;
        public int cases;
        public int todayCases;
        public int deaths;
        public int todayDeaths;
        public int recovered;
        public int todayRecovered;
        public int active;
        public int critical;
        public int casesPerOneMillion;
        public double deathsPerOneMillion;
        public int tests;
        public int testsPerOneMillion;
        public int population;
        public String continent;
        public int oneCasePerPeople;
        public int oneDeathPerPeople;
        public int oneTestPerPeople;
        public double activePerOneMillion;
        public double recoveredPerOneMillion;
        public double criticalPerOneMillion;

        protected Root(Parcel in) {
                updated = in.readLong();
                country = in.readString();
                cases = in.readInt();
                todayCases = in.readInt();
                deaths = in.readInt();
                todayDeaths = in.readInt();
                recovered = in.readInt();
                todayRecovered = in.readInt();
                active = in.readInt();
                critical = in.readInt();
                casesPerOneMillion = in.readInt();
                deathsPerOneMillion = in.readDouble();
                tests = in.readInt();
                testsPerOneMillion = in.readInt();
                population = in.readInt();
                continent = in.readString();
                oneCasePerPeople = in.readInt();
                oneDeathPerPeople = in.readInt();
                oneTestPerPeople = in.readInt();
                activePerOneMillion = in.readDouble();
                recoveredPerOneMillion = in.readDouble();
                criticalPerOneMillion = in.readDouble();
        }

        public static final Creator<Root> CREATOR = new Creator<Root>() {
                @Override
                public Root createFromParcel(Parcel in) {
                        return new Root(in);
                }

                @Override
                public Root[] newArray(int size) {
                        return new Root[size];
                }
        };

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(updated);
                dest.writeString(country);
                dest.writeInt(cases);
                dest.writeInt(todayCases);
                dest.writeInt(deaths);
                dest.writeInt(todayDeaths);
                dest.writeInt(recovered);
                dest.writeInt(todayRecovered);
                dest.writeInt(active);
                dest.writeInt(critical);
                dest.writeInt(casesPerOneMillion);
                dest.writeDouble(deathsPerOneMillion);
                dest.writeInt(tests);
                dest.writeInt(testsPerOneMillion);
                dest.writeInt(population);
                dest.writeString(continent);
                dest.writeInt(oneCasePerPeople);
                dest.writeInt(oneDeathPerPeople);
                dest.writeInt(oneTestPerPeople);
                dest.writeDouble(activePerOneMillion);
                dest.writeDouble(recoveredPerOneMillion);
                dest.writeDouble(criticalPerOneMillion);
        }
}
