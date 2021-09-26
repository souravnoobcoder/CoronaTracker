package com.example.coronatracker.dataClasses.indiaModel;


import java.io.Serializable;
import java.util.Date;

public class indiaStates implements Serializable {
    public boolean success;
    public Data data;
    public Date lastRefreshed;
    public Date lastOriginUpdate;
}
