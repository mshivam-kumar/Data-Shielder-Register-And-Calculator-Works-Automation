package com.datahiveshield.mydata.mydairy.khata.files.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Contact {
//    private  int id;
//    private String name;
//    private String phoneNumber;

//Make all fields static , to store fetched data from mainActivity only and use everywher, we do not need to
    //fetch in different classes activities
    public static Date DATE1=Date.from(Instant.now());
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//current date

    public static String DefaultDate = formatter.format(DATE1);
//    public static double milk_rate=43.00;//to store user input milk rate
public static double Rate=0;//to store user input milk rate
    public static String QUANTITYUNITNAME="UNIT";

    public static double getRate() {
        return Rate;
    }

    public static void setRate(double milk_rate) {
        Contact.Rate = milk_rate;
    }

    //    String DATE=DefaultDate;
    public static String tempSelectedDate;
    public static String selectedDate;//For update  data, to show selected date in box//not part of database
//    public static String[] selectedDateArr=new String[4];
    //    public static final String KEY_NAME="name";
//    public static final String KEY_PHONE="phone_number";
//    private  double C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15;
    private  String DATE="";//Do not make this static, otherwise same date will be shown for every record
    public   double C1=0,C2=0,C3=0,C4=0,C5=0,C6=0,C7=0,C8=0,C9=0,C10=0,C11=0,C12=0,C13=0,C14=0,C15;//Do not

    public static String getQUANTITYUNITNAME() {
        return QUANTITYUNITNAME;
    }

    public static void setQUANTITYUNITNAME(String QUANTITYUNITNAME) {
        Contact.QUANTITYUNITNAME = QUANTITYUNITNAME;
    }

    //make them static, otherwise last used values  will be shown for all records
    //while being different in database
//    public static List<Contact> allContacts;

    public Contact(){ }//Default constructor

    //    public Contact(){ }//Default constructor
    public Contact(String date,double c1,double c2,double c3,double c4,double c5,double c6,
                   double c7,double c8,double c9,double c10,double c11,
                   double c12,double c13,double c14,double c15)
    {
        this.DATE=date;
        this.C1=c1;
        this.C2=c2;
        this.C3=c3;
        this.C4=c4;
        this.C5=c5;
        this.C6=c6;
        this.C7=c7;
        this.C8=c8;
        this.C9=c9;
        this.C10=c10;
        this.C11=c11;
        this.C12=c12;
        this.C13=c13;
        this.C14=c14;
        this.C15=c15;


    }
//    public Contact(int id,String name,String phoneNumber)
//    {
//        this.id=id;
//        this.name=name;
//        this.phoneNumber=phoneNumber;
//    }


    public static String getSelectedDate() {
        return selectedDate;
    }

    public static void setSelectedDate(String selectedDate) {
        Contact.selectedDate = selectedDate;
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



    public double getC1() {
        return C1;
    }

    public void setC1(double c1) {
        C1 = c1;
    }

    public double getC2() {
        return C2;
    }

    public void setC2(double c2) {
        C2 = c2;
    }

    public double getC3() {
        return C3;
    }

    public void setC3(double c3) {
        C3 = c3;
    }

    public double getC4() {
        return C4;
    }

    public void setC4(double c4) {
        C4 = c4;
    }

    public double getC5() {
        return C5;
    }

    public void setC5(double c5) {
        C5 = c5;
    }

    public double getC6() {
        return C6;
    }

    public void setC6(double c6) {
        C6 = c6;
    }

    public double getC7() {
        return C7;
    }

    public void setC7(double c7) {
        C7 = c7;
    }

    public double getC8() {
        return C8;
    }

    public void setC8(double c8) {
        C8 = c8;
    }

    public double getC9() {
        return C9;
    }

    public void setC9(double c9) {
        C9 = c9;
    }

    public double getC10() {
        return C10;
    }

    public void setC10(double c10) {
        C10 = c10;
    }

    public double getC11() {
        return C11;
    }

    public void setC11(double c11) {
        C11 = c11;
    }

    public double getC12() {
        return C12;
    }

    public void setC12(double c12) {
        C12 = c12;
    }

    public double getC13() {
        return C13;
    }

    public void setC13(double c13) {
        C13 = c13;
    }

    public double getC14() {
        return C14;
    }

    public void setC14(double c14) {
        C14 = c14;
    }

    public double getC15() {
        return C15;
    }

    public void setC15(double c15) {
        C15 = c15;
    }
}
