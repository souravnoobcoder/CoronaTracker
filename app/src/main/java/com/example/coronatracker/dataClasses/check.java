package com.example.coronatracker.dataClasses;

import java.util.Date;
import java.util.List;

public class check {
    public class Delta{
        public int confirmed;
        public int recovered;
    }

    public class Delta2114{
        public int confirmed;
    }

    public class Delta7{
        public int confirmed;
        public int recovered;
        public int tested;
        public int vaccinated1;
        public int vaccinated2;
    }

    public class Meta{
        public int population;
        public String date;
        public Date last_updated;
        public Tested tested;
    }

    public class Total{
        public int vaccinated1;
        public int vaccinated2;
        public int confirmed;
        public int deceased;
        public int recovered;
        public int tested;
    }


    public class Districts{
        public Delta delta;
        public Delta2114 delta21_14;
        public Delta7 delta7;
        public Total total;
    }

    public class Tested{
        public String date;
        public String source;
    }

    public class States{
        public Delta delta;
        public Delta2114 delta21_14;
        public Delta7 delta7;
        public List<Districts> districts;
        public Meta meta;
        public Total total;
    }

    public class Root{
        public States states;
    }
}
