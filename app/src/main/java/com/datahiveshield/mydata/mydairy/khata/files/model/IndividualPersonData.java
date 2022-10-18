package com.datahiveshield.mydata.mydairy.khata.files.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class IndividualPersonData {

    //Make all fields static , to store fetched data from mainActivity only and use everywher, we do not need to
    //fetch in different classes activities
    public static Date DATE1=Date.from(Instant.now());
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//current date

    public static String DefaultDate = formatter.format(DATE1);
    //    public static double milk_rate=43.00;//to store user input milk rate



    //    String DATE=DefaultDate;
    public static String tempSelectedDate;
    public static String selectedDate;//For update  data, to show selected date in box//not part of database
    //    public static String[] selectedDateArr=new String[4];
    //    public static final String KEY_NAME="name";
//    public static final String KEY_PHONE="phone_number";
//    private  double C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15;
    public  String DATE="",STATUS="",ADD_NOTE="";//Do not make this static, otherwise same date will be shown for every record
    public   double PERSON_DATA=0;


    public static String getSelectedDate() {
        return selectedDate;
    }

    public static void setSelectedDate(String selectedDate) {
        IndividualPersonData.selectedDate = selectedDate;
    }

    public static String getDefaultDate() {
        return DefaultDate;
    }//today's date is default date

    public static void setDefaultDate(String defaultDate) {
        DefaultDate = defaultDate;
    }

    public void setDATE(String d)
    {
        DATE=d;
    }

    public  String getDATE() {
        return DATE;
    }

    public String getStatus(){return STATUS;};
    public void setSTATUS(String s){STATUS=s;};

    public String getADD_NOTE(){return ADD_NOTE;};
    public void setADD_NOTE(String s){ADD_NOTE=s;};


    public double getPERSON_DATA() {
        return PERSON_DATA;
    }

    public void setPERSON_DATA(double c1) {
        PERSON_DATA = c1;
    }


}


