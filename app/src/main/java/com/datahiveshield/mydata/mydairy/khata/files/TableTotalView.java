package com.datahiveshield.mydata.mydairy.khata.files;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.R;
import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableTotalView extends AppCompatActivity {
//    public TableLayout table;//To store database table data and used to show table data on screen
    public static TableLayout table;//Made static to use in GeneratePdf.java to generate pdf of data
    //On call method tableView and returning table , null pointer exception is coming
    EditText etUnit;
    EditText etRate;

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();



        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.table_total_view);

            //Set back button to go to previous activity
            androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            TextView appName=findViewById(R.id.appName);
            try
            {
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/GloriaHallelujah-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Meddon-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/RuslanDisplay-Regular.ttf");

                Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");

//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Monofett-Regular.ttf");//3d text
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/TradeWinds-Regular.ttf");
                appName.setText(Params.AppName);
                appName.setTypeface(myFont);
            }catch (Exception e)
            {
                //Log.d("dbsvm","unable to change font of app name in HOME");
            }


            table = (TableLayout) findViewById(R.id.viewForsetupUpdateInTable);

//            Params.TABLE_NAME= CustomerGroupTableName.customerGroupTable;
//            Params.DEFAULT_DATA_TABLE_NAME="default_value_"+CustomerGroupTableName.customerGroupTable;
//            Params.DEFAULT_CUSTOMER_NAMES_TABLE="default_customers_"+CustomerGroupTableName.customerGroupTable;


            MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(TableTotalView.this);
            //Initialize defualtValues of assoiciated Customer group into textboxes
            Contact init_contact=db.getDefaultDataForTextBoxes();
            CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();


            //Update rate in Contact.java
            Contact contact=new Contact();
             etUnit=findViewById(R.id.editTextUnit);
             etRate=findViewById(R.id.editTextRate);


//            Contact defaultData=db.getDefaultDataForTextBoxes();

//            etMilkRate.setText(defaultData.getMilk_rate()+"");
            //No need to fetch data here as data is static,
            // already fetched in MainActivity
            etRate.setText(Contact.getRate()+"");
            etUnit.setText(Contact.getQUANTITYUNITNAME());



//            contact.setMilk_rate(Double.parseDouble(etMilkRate.getText().toString()));
            tableView(db);//show cost with default rate value
            Button changeUnitAndRate=findViewById(R.id.applyChanges);
            changeUnitAndRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long now=System.currentTimeMillis();
                    if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                        return;
                    lastTimeClicked=now;
//                    EditText etMilkRate=findViewById(R.id.editTextMilkRate);
                    Double t_rate=Double.parseDouble(etRate.getText().toString());
                    Contact.setRate(t_rate);
                    String t_unit=etUnit.getText().toString();
                    Contact.setQUANTITYUNITNAME(t_unit);
                    tableView(db);
                    db.updateDefaultData(contact,1);//Update rate and unit
                    Contact contact=new Contact();
//                    db.updateDefaultData(contact,0);//0 is key to update default milk rate
                etUnit.clearFocus();//Remove cursor after pressing button
                etRate.clearFocus();//Remove cursor after pressing button
                }
            });


        }
//    public void tableView(MyDbHandlerGroupConfigs db)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tableView(MyDbHandlerGroupConfigs db)

    {
        try {
//            MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(com.easyway2in.sqlitedb.TableView.this);
            table.removeAllViews();//To update new costs at same postion
            ArrayList<String> tempdate = new ArrayList<>();
            ArrayList<String> tempc1 = new ArrayList<>();
            ArrayList<String> tempc2 = new ArrayList<>();
            ArrayList<String> tempc3 = new ArrayList<>();
            ArrayList<String> tempc4 = new ArrayList<>();
            ArrayList<String> tempc5 = new ArrayList<>();
            ArrayList<String> tempc6 = new ArrayList<>();
            ArrayList<String> tempc7 = new ArrayList<>();
            ArrayList<String> tempc8 = new ArrayList<>();
            ArrayList<String> tempc9 = new ArrayList<>();
            ArrayList<String> tempc10 = new ArrayList<>();
            ArrayList<String> tempc11 = new ArrayList<>();
            ArrayList<String> tempc12 = new ArrayList<>();
            ArrayList<String> tempc13 = new ArrayList<>();
            ArrayList<String> tempc14 = new ArrayList<>();
            ArrayList<String> tempc15 = new ArrayList<>();
            ArrayList<String> todayTotalArrayList = new ArrayList<>();
            ArrayList<String> todayTotalRSArrayList = new ArrayList<>();
            String tDATE;
            double sC1 = 0, sC2 = 0, sC3 = 0, sC4 = 0, sC5 = 0, sC6 = 0, sC7 = 0, sC8 = 0, sC9 = 0, sC10 = 0, sC11 = 0, sC12 = 0, sC13 = 0, sC14 = 0, sC15 = 0;
            double tC1 = 0, tC2 = 0, tC3 = 0, tC4 = 0, tC5 = 0, tC6 = 0, tC7 = 0, tC8 = 0, tC9 = 0, tC10 = 0, tC11 = 0, tC12 = 0, tC13 = 0, tC14 = 0, tC15 = 0;
            double todayTotal = 0,todayTotalRs=0, todayTotalMonth = 0;
            //Get all contacts
            List<Contact> allContacts = db.getAllSavedRecords();//fetch all saved records
            for (Contact contact : allContacts) {
//                    temp.add("Id: "+contact.getId()+"\nName: "+contact.getName()+
//                    "\nPhone Number: "+contact.getPhoneNumber());
//                    sum.add(contact.getC1());
                tDATE=contact.getDATE();
                tC1 = contact.getC1();
                tC2 = contact.getC2();
                tC3 = contact.getC3();
                tC4 = contact.getC4();
                tC5 = contact.getC5();
                tC6 = contact.getC6();
                tC7 = contact.getC7();
                tC8 = contact.getC8();
                tC9 = contact.getC9();
                tC10 = contact.getC10();
                tC11 = contact.getC11();
                tC12 = contact.getC12();
                tC13 = contact.getC13();
                tC14 = contact.getC14();
                tC15 = contact.getC15();
                //handling todays total on table view
                todayTotal = tC1 + tC2 + tC3 + tC4 + tC5 + tC6 + tC7 + tC8 + tC9 + tC10 + tC11 + tC12 + tC13 + tC14 + tC15;
                todayTotalRs=todayTotal*Contact.getRate();
                todayTotalMonth += todayTotal;//updating monthly total milk
//                    Update individual user milk sum
                sC1 += tC1;
                sC2 += tC2;
                sC3 += tC3;
                sC4 += tC4;
                sC5 += tC5;
                sC6 += tC6;
                sC7 += tC7;
                sC8 += tC8;
                sC9 += tC9;
                sC10 += tC10;
                sC11 += tC11;
                sC12 += tC12;
                sC13 += tC13;
                sC14 += tC14;
                sC15 += tC15;

                tempdate.add(contact.getDATE());
//                tempdate.add(Contact.rate+"");
//                tempdate.add(tDATE);
                tempc1.add("           " + contact.getC1() + "");
                tempc2.add("            " + contact.getC2() + "");
                tempc3.add("         " + contact.getC3() + "");
                tempc4.add("             " + contact.getC4() + "");
                tempc5.add("         " + contact.getC5() + "");
                tempc6.add("         " + contact.getC6() + "");
                tempc7.add("         " + contact.getC7() + "");
                tempc8.add("           " + contact.getC8() + "");
                tempc9.add("             " + contact.getC9() + "");
                tempc10.add("         " + contact.getC10() + "");
                tempc11.add("         " + contact.getC11() + "");
                tempc12.add("         " + contact.getC12() + "");
                tempc13.add("         " + contact.getC13() + "");
                tempc14.add("         " + contact.getC14() + "");
                tempc15.add("         " + contact.getC15() + "       ");
                todayTotalArrayList.add("       " + todayTotal);//adding todays total in arrayList
                todayTotalRSArrayList.add("       "+todayTotalRs);

//            temp.add("Name: "+contact.getName());
//            temp.add("Phone Number: "+contact.getPhoneNumber());
//                //Log.d("dbsvm", "Id : " + contact.getC1() + "\n" +
//                        "Name : " + contact.getDATE() + "\n");

            }
//            table = (TableLayout) findViewById(R.id.myTable);//declaring outside function
            //inorder to increase code reusability in other classes


//            Collections.sort(tempdate);//sort data in ascending order with date
            //Add column headings
            TableRow hrow = new TableRow(this);
//            String hdate = "Date";
//            String hc1 = "       Arun";
//            String hc2 = "       Arvind ";
//            String hc3 = "    Chetan";
//            String hc4 = "   Jayprakash";
//            String hc5 = "   Mahesh";
//            String hc6 = "   Naresh";
//            String hc7 = "   Prempal";
//            String hc8 = "       Ravi";
//            String hc9 = "      Ravindra";
//            String hc10 = "     Rinku";
//            String hc11 = "     New1";
//            String hc12 = "     new2";
//            String hc13 = "     New3";
//            String hc14 = "     New4";
//            String hc15 = "     New5";

            CustomerNames defaultCustomerNames=new CustomerNames();
            String dayNum="S. No.   ";
            String hdate= "Date";
            String hc1 ="       "+defaultCustomerNames.getNAME1();
            String hc2 = "       "+defaultCustomerNames.getNAME2();
            String hc3 = "       "+defaultCustomerNames.getNAME3();
            String hc4 = "       "+defaultCustomerNames.getNAME4();
            String hc5 = "       "+defaultCustomerNames.getNAME5();
            String hc6 = "       "+defaultCustomerNames.getNAME6();
            String hc7 = "       "+defaultCustomerNames.getNAME7();
            String hc8 = "       "+defaultCustomerNames.getNAME8();
            String hc9 = "       "+defaultCustomerNames.getNAME9();
            String hc10="       "+ defaultCustomerNames.getNAME10();
            String hc11= "       "+defaultCustomerNames.getNAME11();
            String hc12= "       "+defaultCustomerNames.getNAME12();
            String hc13= "       "+defaultCustomerNames.getNAME13();
            String hc14= "       "+defaultCustomerNames.getNAME14();
            String hc15= "       "+defaultCustomerNames.getNAME15();

            String htodayTotal = "   Total";
            String htodayTotalRS = "     Today Total (RS)      ";

            TextView htvdayNum = new TextView(this);
            htvdayNum.setTextSize(15);
            htvdayNum.setTextColor(this.getResources().getColor(R.color.black));
            htvdayNum.setText(dayNum);
//
            TextView htvdate = new TextView(this);
            htvdate.setTextSize(18);
//                htv1.setLinkTextColor();
            htvdate.setTextColor(this.getResources().getColor(R.color.black));
            htvdate.setText(hdate);

            TextView htv1 = new TextView(this);
            htv1.setTextSize(18);
            htv1.setTextColor(this.getResources().getColor(R.color.black));
            htv1.setText(hc1);

            TextView htv2 = new TextView(this);
            htv2.setTextSize(18);
            htv2.setTextColor(this.getResources().getColor(R.color.black));
            htv2.setText(hc2);

            TextView htv3 = new TextView(this);
            htv3.setTextSize(18);
            htv3.setTextColor(this.getResources().getColor(R.color.black));
            htv3.setText(hc3);

            TextView htv4 = new TextView(this);
            htv4.setTextSize(18);
            htv4.setTextColor(this.getResources().getColor(R.color.black));
            htv4.setText(hc4);

            TextView htv5 = new TextView(this);
            htv5.setTextSize(18);
            htv5.setTextColor(this.getResources().getColor(R.color.black));
            htv5.setText(hc5);

            TextView htv6 = new TextView(this);
            htv6.setTextSize(18);
            htv6.setTextColor(this.getResources().getColor(R.color.black));
            htv6.setText(hc6);

            TextView htv7 = new TextView(this);
            htv7.setTextSize(18);
            htv7.setTextColor(this.getResources().getColor(R.color.black));
            htv7.setText(hc7);

            TextView htv8 = new TextView(this);
            htv8.setTextSize(18);
            htv8.setTextColor(this.getResources().getColor(R.color.black));
            htv8.setText(hc8);

            TextView htv9 = new TextView(this);
            htv9.setTextSize(18);
            htv9.setTextColor(this.getResources().getColor(R.color.black));
            htv9.setText(hc9);

            TextView htv10 = new TextView(this);
            htv10.setTextSize(18);
            htv10.setTextColor(this.getResources().getColor(R.color.black));
            htv10.setText(hc10);

            TextView htv11 = new TextView(this);
            htv11.setTextSize(18);
            htv11.setTextColor(this.getResources().getColor(R.color.black));
            htv11.setText(hc11);

            TextView htv12 = new TextView(this);
            htv12.setTextSize(18);
            htv12.setTextColor(this.getResources().getColor(R.color.black));
            htv12.setText(hc12);

            TextView htv13 = new TextView(this);
            htv13.setTextSize(18);
            htv13.setTextColor(this.getResources().getColor(R.color.black));
            htv13.setText(hc13);

            TextView htv14 = new TextView(this);
            htv14.setTextSize(18);
            htv14.setTextColor(this.getResources().getColor(R.color.black));
            htv14.setText(hc14);

            TextView htv15 = new TextView(this);
            htv15.setTextSize(18);
            htv15.setTextColor(this.getResources().getColor(R.color.black));
            htv15.setText(hc15);

            TextView htvtodayTotal = new TextView(this);
            htvtodayTotal.setTextSize(18);
            htvtodayTotal.setTextColor(this.getResources().getColor(R.color.black));
            htvtodayTotal.setText(htodayTotal);

            TextView htvtodayTotalRS = new TextView(this);
            htvtodayTotalRS.setTextSize(18);
            htvtodayTotalRS.setTextColor(this.getResources().getColor(R.color.black));
            htvtodayTotalRS.setText(htodayTotalRS);

            hrow.addView(htvdayNum);
            hrow.addView(htvdate);
            hrow.addView(htv1);
            hrow.addView(htv2);
            hrow.addView(htv3);
            hrow.addView(htv4);
            hrow.addView(htv5);
            hrow.addView(htv6);
            hrow.addView(htv7);
            hrow.addView(htv8);
            hrow.addView(htv9);
            hrow.addView(htv10);
            hrow.addView(htv11);
            hrow.addView(htv12);
            hrow.addView(htv13);
            hrow.addView(htv14);
            hrow.addView(htv15);
            hrow.addView(htvtodayTotal);
            hrow.addView(htvtodayTotalRS);
            table.addView(hrow);

            //Log.d("dbsvm",tempdate.size()+"\n");            //Log.d("dbsvm",tempdate.size()+"\n");

            for (int i = 0; i < tempdate.size(); i++) {
                //Log.d("dbsvm",tempdate.get(i)+"\n");
                TableRow row = new TableRow(this);
                String date = tempdate.get(i);
                String c1 = tempc1.get(i);
                String c2 = tempc2.get(i);
                String c3 = tempc3.get(i);
                String c4 = tempc4.get(i);
                String c5 = tempc5.get(i);
                String c6 = tempc6.get(i);
                String c7 = tempc7.get(i);
                String c8 = tempc8.get(i);
                String c9 = tempc9.get(i);
                String c10 = tempc10.get(i);
                String c11 = tempc11.get(i);
                String c12 = tempc12.get(i);
                String c13 = tempc13.get(i);
                String c14 = tempc14.get(i);
                String c15 = tempc15.get(i);
                String vartodayTotal = todayTotalArrayList.get(i) + "       ";
                String vartodayTotalRS = todayTotalRSArrayList.get(i) + "       ";

                TextView tvDayNum = new TextView(this);
                tvDayNum.setText(i+1+"");

                TextView tvdate = new TextView(this);
                tvdate.setText(date);
                TextView tv1 = new TextView(this);
                tv1.setText(c1);
                TextView tv2 = new TextView(this);
                tv2.setText(c2);
                TextView tv3 = new TextView(this);
                tv3.setText(c3);
                TextView tv4 = new TextView(this);
                tv4.setText(c4);
                TextView tv5 = new TextView(this);
                tv5.setText(c5);
                TextView tv6 = new TextView(this);
                tv6.setText(c6);
                TextView tv7 = new TextView(this);
                tv7.setText(c7);
                TextView tv8 = new TextView(this);
                tv8.setText(c8);
                TextView tv9 = new TextView(this);
                tv9.setText(c9);
                TextView tv10 = new TextView(this);
                tv10.setText(c10);
                TextView tv11 = new TextView(this);
                tv11.setText(c11);
                TextView tv12 = new TextView(this);
                tv12.setText(c12);
                TextView tv13 = new TextView(this);
                tv13.setText(c13);
                TextView tv14 = new TextView(this);
                tv14.setText(c14);
                TextView tv15 = new TextView(this);
                tv15.setText(c15);
                TextView tvtodayTotal = new TextView(this);
                tvtodayTotal.setText(vartodayTotal);
                TextView tvtodayTotalRS = new TextView(this);
                tvtodayTotalRS.setText(vartodayTotalRS);

                row.addView(tvDayNum);
                row.addView(tvdate);
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);
                row.addView(tv4);
                row.addView(tv5);
                row.addView(tv6);
                row.addView(tv7);
                row.addView(tv8);
                row.addView(tv9);
                row.addView(tv10);
                row.addView(tv11);
                row.addView(tv12);
                row.addView(tv13);
                row.addView(tv14);
                row.addView(tv15);
                row.addView(tvtodayTotal);
                row.addView(tvtodayTotalRS);


                table.addView(row);
            }
            //Milk per ltr rate to calculate total cost per user
            //Take input from user in text box
            double rate;
//            rate = 43.00;//temprary
            rate=Contact.Rate;

            //Add total footer row
            String addToCreateSpaceBefore="   ";
            String ftotal_head = "Total("+Contact.QUANTITYUNITNAME+")";

            String ftotCostC1 = "         " + sC1 ;
            String ftotCostC2 = "        " + sC2 ;
            String ftotCostC3 = "        " + sC3 ;
            String ftotCostC4 = "          " + sC4 ;
            String ftotCostC5 = "       " + sC5 ;
            String ftotCostC6 = "       " + sC6 ;
            String ftotCostC7 = "       " + sC7 ;
            String ftotCostC8 = "         " + sC8 ;
            String ftotCostC9 = "          " + sC9 ;
            String ftotCostC10 = "       " + sC10 ;
            String ftotCostC11 = "       " + sC11 ;
            String ftotCostC12 = "       " + sC12 ;
            String ftotCostC13 = "       " + sC13 ;
            String ftotCostC14 = "       " + sC14 ;
            String ftotCostC15 = "       " + sC15 ;
            String ftotCosttodayTotalMonth = "     " + todayTotalMonth ;


            TableRow frow = new TableRow(this);


            TextView faddToCreateSpaceBefore=new TextView(this);
            faddToCreateSpaceBefore.setText(addToCreateSpaceBefore);


            TextView ftvhead = new TextView(this);
            ftvhead.setTextSize(18);
            ftvhead.setTextColor(this.getResources().getColor(R.color.black));
            ftvhead.setText(ftotal_head);


            TextView ftv1 = new TextView(this);
            ftv1.setTextSize(18);
            ftv1.setTextColor(this.getResources().getColor(R.color.black));
            ftv1.setText(ftotCostC1);

            TextView ftv2 = new TextView(this);
            ftv2.setTextSize(18);
            ftv2.setTextColor(this.getResources().getColor(R.color.black));
            ftv2.setText(ftotCostC2);

            TextView ftv3 = new TextView(this);
            ftv3.setTextSize(18);
            ftv3.setTextColor(this.getResources().getColor(R.color.black));
            ftv3.setText(ftotCostC3);

            TextView ftv4 = new TextView(this);
            ftv4.setTextSize(18);
            ftv4.setTextColor(this.getResources().getColor(R.color.black));
            ftv4.setText(ftotCostC4);

            TextView ftv5 = new TextView(this);
            ftv5.setTextSize(18);
            ftv5.setTextColor(this.getResources().getColor(R.color.black));
            ftv5.setText(ftotCostC5);

            TextView ftv6 = new TextView(this);
            ftv6.setTextSize(18);
            ftv6.setTextColor(this.getResources().getColor(R.color.black));
            ftv6.setText(ftotCostC6);

            TextView ftv7 = new TextView(this);
            ftv7.setTextSize(18);
            ftv7.setTextColor(this.getResources().getColor(R.color.black));
            ftv7.setText(ftotCostC7);

            TextView ftv8 = new TextView(this);
            ftv8.setTextSize(18);
            ftv8.setTextColor(this.getResources().getColor(R.color.black));
            ftv8.setText(ftotCostC8);

            TextView ftv9 = new TextView(this);
            ftv9.setTextSize(18);
            ftv9.setTextColor(this.getResources().getColor(R.color.black));
            ftv9.setText(ftotCostC9);

            TextView ftv10 = new TextView(this);
            ftv10.setTextSize(18);
            ftv10.setTextColor(this.getResources().getColor(R.color.black));
            ftv10.setText(ftotCostC10);

            TextView ftv11 = new TextView(this);
            ftv11.setTextSize(18);
            ftv11.setTextColor(this.getResources().getColor(R.color.black));
            ftv11.setText(ftotCostC11);

            TextView ftv12 = new TextView(this);
            ftv12.setTextSize(18);
            ftv12.setTextColor(this.getResources().getColor(R.color.black));
            ftv12.setText(ftotCostC12);

            TextView ftv13 = new TextView(this);
            ftv13.setTextSize(18);
            ftv13.setTextColor(this.getResources().getColor(R.color.black));
            ftv13.setText(ftotCostC13);

            TextView ftv14 = new TextView(this);
            ftv14.setTextSize(18);
            ftv14.setTextColor(this.getResources().getColor(R.color.black));
            ftv14.setText(ftotCostC14);

            TextView ftv15 = new TextView(this);
            ftv15.setTextSize(18);
            ftv15.setTextColor(this.getResources().getColor(R.color.black));
            ftv15.setText(ftotCostC15);

            TextView ftvtotCosttodayTotalMonth = new TextView(this);
            ftvtotCosttodayTotalMonth.setTextSize(18);
            ftvtotCosttodayTotalMonth.setTextColor(this.getResources().getColor(R.color.black));
            ftvtotCosttodayTotalMonth.setText(ftotCosttodayTotalMonth);

            frow.addView(faddToCreateSpaceBefore);
            frow.addView(ftvhead);
            frow.addView(ftv1);
            frow.addView(ftv2);
            frow.addView(ftv3);
            frow.addView(ftv4);
            frow.addView(ftv5);
            frow.addView(ftv6);
            frow.addView(ftv7);
            frow.addView(ftv8);
            frow.addView(ftv9);
            frow.addView(ftv10);
            frow.addView(ftv11);
            frow.addView(ftv12);
            frow.addView(ftv13);
            frow.addView(ftv14);
            frow.addView(ftv15);
            frow.addView(ftvtotCosttodayTotalMonth);

            table.addView(frow);//first frow added

            //Adding frow2 for total cost(rs)















            String ftotal_head2 = "Total(Rs)";
            String ftotCostC12a="         " + sC1 * rate;
            String ftotCostC22="        " + sC2 * rate;
            String ftotCostC32="        " + sC3 * rate;
            String ftotCostC42="          " + sC4 * rate;
            String ftotCostC52="       " + sC5 * rate;
            String ftotCostC62="       " + sC6 * rate;
            String ftotCostC72="       " + sC7 * rate;
            String ftotCostC82="         " + sC8 * rate;
            String ftotCostC92="          " + sC9 * rate;
            String ftotCostC102="       " + sC10 * rate;
            String ftotCostC112="       " + sC11 * rate;
            String ftotCostC122="       " + sC12 * rate;
            String ftotCostC132="       " + sC13 * rate;
            String ftotCostC142="       " + sC14 * rate;
            String ftotCostC152="       " + sC15 * rate;
            String ftotCosttodayTotalMonth2="     " + todayTotalMonth * rate + "      ";

            TableRow frow2 = new TableRow(this);

            TextView faddToCreateSpaceBefore2=new TextView(this);
            faddToCreateSpaceBefore.setText(addToCreateSpaceBefore);

            TextView ftvhead2 = new TextView(this);
            ftvhead2.setTextSize(18);
            ftvhead2.setTextColor(this.getResources().getColor(R.color.black));
            ftvhead2.setText(ftotal_head2);


            TextView ftv12a = new TextView(this);
            ftv12a.setTextSize(18);
            ftv12a.setTextColor(this.getResources().getColor(R.color.black));
            ftv12a.setText(ftotCostC12a);

            TextView ftv22 = new TextView(this);
            ftv22.setTextSize(18);
            ftv22.setTextColor(this.getResources().getColor(R.color.black));
            ftv22.setText(ftotCostC22);

            TextView ftv32 = new TextView(this);
            ftv32.setTextSize(18);
            ftv32.setTextColor(this.getResources().getColor(R.color.black));
            ftv32.setText(ftotCostC32);

            TextView ftv42 = new TextView(this);
            ftv42.setTextSize(18);
            ftv42.setTextColor(this.getResources().getColor(R.color.black));
            ftv42.setText(ftotCostC42);

            TextView ftv52 = new TextView(this);
            ftv52.setTextSize(18);
            ftv52.setTextColor(this.getResources().getColor(R.color.black));
            ftv52.setText(ftotCostC52);

            TextView ftv62 = new TextView(this);
            ftv62.setTextSize(18);
            ftv62.setTextColor(this.getResources().getColor(R.color.black));
            ftv62.setText(ftotCostC62);

            TextView ftv72 = new TextView(this);
            ftv72.setTextSize(18);
            ftv72.setTextColor(this.getResources().getColor(R.color.black));
            ftv72.setText(ftotCostC72);

            TextView ftv82 = new TextView(this);
            ftv82.setTextSize(18);
            ftv82.setTextColor(this.getResources().getColor(R.color.black));
            ftv82.setText(ftotCostC82);

            TextView ftv92 = new TextView(this);
            ftv92.setTextSize(18);
            ftv92.setTextColor(this.getResources().getColor(R.color.black));
            ftv92.setText(ftotCostC92);

            TextView ftv102 = new TextView(this);
            ftv102.setTextSize(18);
            ftv102.setTextColor(this.getResources().getColor(R.color.black));
            ftv102.setText(ftotCostC102);

            TextView ftv112 = new TextView(this);
            ftv112.setTextSize(18);
            ftv112.setTextColor(this.getResources().getColor(R.color.black));
            ftv112.setText(ftotCostC112);

            TextView ftv122 = new TextView(this);
            ftv122.setTextSize(18);
            ftv122.setTextColor(this.getResources().getColor(R.color.black));
            ftv122.setText(ftotCostC122);

            TextView ftv132 = new TextView(this);
            ftv132.setTextSize(18);
            ftv132.setTextColor(this.getResources().getColor(R.color.black));
            ftv132.setText(ftotCostC132);

            TextView ftv142 = new TextView(this);
            ftv142.setTextSize(18);
            ftv142.setTextColor(this.getResources().getColor(R.color.black));
            ftv142.setText(ftotCostC142);

            TextView ftv152 = new TextView(this);
            ftv152.setTextSize(18);
            ftv152.setTextColor(this.getResources().getColor(R.color.black));
            ftv152.setText(ftotCostC152);

            TextView ftvtotCosttodayTotalMonth2 = new TextView(this);
            ftvtotCosttodayTotalMonth2.setTextSize(18);
            ftvtotCosttodayTotalMonth2.setTextColor(this.getResources().getColor(R.color.black));
            ftvtotCosttodayTotalMonth2.setText(ftotCosttodayTotalMonth2);

//            frow2.addView(faddToCreateSpaceBefore);//Add space before last two rows, this initialised
            // for frow above, and used here as purpose are same
//            frow2.addView(faddToCreateSpaceBefore);
            frow2.addView(faddToCreateSpaceBefore2);
            frow2.addView(ftvhead2);
            frow2.addView(ftv12a);
            frow2.addView(ftv22);
            frow2.addView(ftv32);
            frow2.addView(ftv42);
            frow2.addView(ftv52);
            frow2.addView(ftv62);
            frow2.addView(ftv72);
            frow2.addView(ftv82);
            frow2.addView(ftv92);
            frow2.addView(ftv102);
            frow2.addView(ftv112);
            frow2.addView(ftv122);
            frow2.addView(ftv132);
            frow2.addView(ftv142);
            frow2.addView(ftv152);
            frow2.addView(ftvtotCosttodayTotalMonth2);

            table.addView(frow2);//second frow added



        } catch (Exception e) {
            //Log.d("svm", e.getMessage().toString());
//                textView.setText(e.getMessage().toString());
        }
        //Log.d("dbsvm","Total Table rows: "+table.getChildCount()+"");
//        return table;//To convert into pdf
    }
    public void setNewCustomerNamesIntoTextViews(CustomerNames CustomerNamesSet) {

        GetAlerts ga = new GetAlerts();

        //Get typed data from textboxes into string and insert into db table

//           all below initialized as class variable,to use throughout whole class, without initializing again

        TextView etC1 = findViewById(R.id.textViewName1);
        TextView etC2 = findViewById(R.id.textViewName2);
        TextView etC3 = findViewById(R.id.textViewName3);
        TextView etC4 = findViewById(R.id.textViewName4);
        TextView etC5 = findViewById(R.id.textViewName5);
        TextView etC6 = findViewById(R.id.textViewName6);
        TextView etC7 = findViewById(R.id.textViewName7);
        TextView etC8 = findViewById(R.id.textViewName8);
        TextView etC9 = findViewById(R.id.textViewName9);
        TextView etC10 = findViewById(R.id.textViewName10);
        TextView etC11 = findViewById(R.id.textViewName11);
        TextView etC12 = findViewById(R.id.textViewName12);
        TextView etC13 = findViewById(R.id.textViewName13);
        TextView etC14 = findViewById(R.id.textViewName14);
        TextView etC15 = findViewById(R.id.textViewName15);

        //Set new names into text boxes
        etC1.setText(CustomerNamesSet.getNAME1());
        etC2.setText(CustomerNamesSet.getNAME2());
        etC3.setText(CustomerNamesSet.getNAME3());
        etC4.setText(CustomerNamesSet.getNAME4());
        etC5.setText(CustomerNamesSet.getNAME5());
        etC6.setText(CustomerNamesSet.getNAME6());
        etC7.setText(CustomerNamesSet.getNAME7());
        etC8.setText(CustomerNamesSet.getNAME8());
        etC9.setText(CustomerNamesSet.getNAME9());
        etC10.setText(CustomerNamesSet.getNAME10());
        etC11.setText(CustomerNamesSet.getNAME11());
        etC12.setText(CustomerNamesSet.getNAME12());
        etC13.setText(CustomerNamesSet.getNAME13());
        etC14.setText(CustomerNamesSet.getNAME14());
        etC15.setText(CustomerNamesSet.getNAME15());


    }

}




