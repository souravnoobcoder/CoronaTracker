package com.example.coronatracker.DataClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class indianStates implements Serializable {

        public boolean success;
        public Data data;
        public Date lastRefreshed;
        public Date lastOriginUpdate;

    public static class Summary{
        public int total;
        public int confirmedCasesIndian;
        public int confirmedCasesForeign;
        public int discharged;
        public int deaths;
        public int confirmedButLocationUnidentified;
    }
    public static class Regional{
        public String loc;
        public int confirmedCasesIndian;
        public int confirmedCasesForeign;
        public int discharged;
        public int deaths;
        public int totalConfirmed;
    }
    public static class UnofficialSummary{
        public String source;
        public int total;
        public int recovered;
        public int deaths;
        public int active;
    }

    public static class Data{
        public indianStates.Summary summary;
        public List<indianStates.UnofficialSummary> unofficialSummary;
        public List<indianStates.Regional> regional;
    }
}



