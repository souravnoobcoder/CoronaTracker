package com.example.coronatracker.DataClasses;

public class indiaStateModel {
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
}
