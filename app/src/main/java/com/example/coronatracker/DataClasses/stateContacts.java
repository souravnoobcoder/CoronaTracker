package com.example.coronatracker.DataClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class stateContacts implements Serializable {
    public boolean success;
    public Data data;
    public Date lastRefreshed;
    public Date lastOriginUpdate;
    public static class Primary{
        public String number;
        public String numberTollfree;
        public String email;
        public String twitter;
        public String facebook;
        public List<String> media;
    }

    public static class Regional{
        public String loc;
        public String number;
    }

    public static class Contacts{
        public Primary primary;
        public List<Regional> regional;
    }

    public static class Data{
        public Contacts contacts;
    }
}
