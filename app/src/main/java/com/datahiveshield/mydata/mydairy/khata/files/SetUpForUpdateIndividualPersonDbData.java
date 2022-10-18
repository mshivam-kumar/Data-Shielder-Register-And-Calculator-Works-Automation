package com.datahiveshield.mydata.mydairy.khata.files;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDBHandlerIndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.IndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetUpForUpdateIndividualPersonDbData extends AppCompatActivity {

    TextView showSelecteDate;
    private Button mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TableLayout table;
    Button proceedToUpdate;
    public String datePickerTitle="";
    public static int FlagSendPaidMsg=1;

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private String oldAmountPaid="";


    protected void onRestart() {//With back button reload this activity to update changes in person list
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_for_individual_person_update_db_data);

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


        //Below statements implemented in SendReminderIndividualPersonGetMoney
//        try {
//            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(SetUpForUpdateIndividualPersonDbData.this);
//            String amountNotPaid;
//            amountNotPaid = db.getAllIndividualPersonAmountNotPaidRecords();
//            Log.d("dbsvm", "Amouont not paid by : " + Params.CURRENT_GROUP_DB_NAME + " is " + amountNotPaid);
//        }
//        catch (Exception e)
//        {
//            Log.d("dbsvm","Unable to get sum \nError : "+e.getMessage());
//        }


        table = (TableLayout) findViewById(R.id.viewForsetupUpdateInTable);
        MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(SetUpForUpdateIndividualPersonDbData.this);

        try {
            oldAmountPaid = db.getIndividualPersonAmountPaidRecords();//
            Log.d("dbsvm","old amount not paid "+oldAmountPaid);
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to fetch not paid records");
        }

//        TableView showinUpdateDbData=new TableView();
//        EditText etMilkRate=findViewById(R.id.)
        tableView(db);


        showSelecteDate=(TextView) findViewById(R.id.showSelectedDate);
        mDisplayDate = (Button) findViewById(R.id.selectDate);

        datePickerTitle="Select The Date";
        selectDate();//select date button listener is initialised inside this method
        Contact contact=new Contact();
        proceedToUpdate=findViewById(R.id.proceedToUpdate);
        proceedToUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
//                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
//                    return;
//                lastTimeClicked=now;
                GetAlerts ga = new GetAlerts();
//                if(showSelecteDate.getText().toString().length()>21)//21 is length of textView content "Selected date to update" this text
//                    // will be replaced with "Selected Date : dd/mm/yyyy" which will be more than 21 if
////                    date is selected
//                {
                if(Contact.tempSelectedDate.length()!=0) {
                    if (db.isIndividualPersonDataDateExist(Contact.tempSelectedDate) == 1)//If date already exists so that record can be updated
                    {
                        startActivity(new Intent(SetUpForUpdateIndividualPersonDbData.this, UpdateIndividualPersonDbData.class));
                        //Log.d("dbsvm", "Data with selected date already  exists");
                    } else {
                        ga.alertDialogBox(SetUpForUpdateIndividualPersonDbData.this, "Record does not exist with selected  " + "Date : " + Contact.tempSelectedDate + "\nPlease select valid date", "Selected Date Does Not Exist");
                        //Log.d("dbsvm", "Record with selected date does not exists,\ndata can be updated");
                    }
                }
                else {
                    datePickerTitle="Please Select Date Before Proceeding";
                    mDisplayDate.callOnClick();//call click listener of other button programmatically
//                        selectDate();
//                        ga.alertDialogBox(SetUpForUpdateIndividualPersonDbData.this,"\nPlease select the date", "Date not selected");
//                        //Log.d("dbsvm", "Record with selected date does not exists");


                }
                Contact.tempSelectedDate = "";
                datePickerTitle="Select The Date";
            }


        });

        Button paidAll=findViewById(R.id.paidAll);
        paidAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                String msg = "Are your sure you want to set whole month money to 'Paid', click Yes to set or click No to cancel.";
                Context context = SetUpForUpdateIndividualPersonDbData.this;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("Please Select any option");
                dialog.setMessage(msg);
//        dialog.setTitle("Dialog Box");
                dialog.setTitle("Warning!! Please be sure, this action is irreversible");
                dialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                db.paidAllIndividualPersonData();
                                tableView(db);
//                                try {
//                                    if(Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE) && FlagSendPaidMsg==1) {
//                                        notifyPersonWhoPaidBalancedMoney();
//                                        FlagSendPaidMsg=0;
//                                    }
//                                    else//YOU_WILL_GIVE_MONEY interface To notify yourself
//                                    {
//                                        SharedPreferences yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
//
//                                        String yourName=yourDetailsForMsgSharedPref.getString("yourName","");
//                                        String yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");
//
//                                        String newAmountPaid="";
//                                        Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;
//
//                                        try {
//                                            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(SetUpForUpdateIndividualPersonDbData.this);
//                                            newAmountPaid = db.getIndividualPersonAmountPaidRecords();
//                                            newAmountPaid=(Float.parseFloat(newAmountPaid)-Float.parseFloat(oldAmountPaid))+"";
//                                            Log.d("dbsvm", "Amount  paid by : " + Params.CURRENT_GROUP_DB_NAME + " is " + newAmountPaid);
//                                        }
//                                        catch (Exception e)
//                                        {
//                                            Log.d("dbsvm","Unable to get sum \nError : "+e.getMessage());
//                                        }
//
//                                        String yourPhoneNumInfoForSms="",yourInfoForSms="";
//                                        if(yourPhoneNum.length()!=0)
//                                        {
//                                           yourPhoneNumInfoForSms=" to ( "+yourPhoneNum+" )";
//                                        }
//                                      yourInfoForSms= yourPhoneNumInfoForSms+"\n" + yourName;
//
//                                        String earlierPaidAmountMsg="";
//                                      if(!(oldAmountPaid.equals(null)) && !(oldAmountPaid.equals("0"))) {
//                                           earlierPaidAmountMsg = "\nYour earlier paid amount is RS " + oldAmountPaid+".";
//                                           }
//       String Smsmessage="Dear Sir/Madam,\n" +
//                "You paid "+"RS "+newAmountPaid+" for  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//                yourInfoForSms+earlierPaidAmountMsg;
////        Smsmessage="Hello, how are your?";
//        Log.d("dbsvm","Message : "+Smsmessage);
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            //Send sms to payer
//            if(Float.parseFloat(newAmountPaid)>0 && FlagSendPaidMsg==1) {
//                smsManager.sendTextMessage(yourPhoneNum, null, Smsmessage, null, null);
//                FlagSendPaidMsg=0;
//                Toast.makeText(SetUpForUpdateIndividualPersonDbData.this, "SMS sent to you successfully.",
//                        Toast.LENGTH_SHORT).show();
//
//                newAmountPaid="0";
//            }
//
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(SetUpForUpdateIndividualPersonDbData.this, "Unable to send you SMS.",
//                    Toast.LENGTH_SHORT).show();
//
//            Log.d("dbsvm","Unable to send sms to you, YOU_WILL_GIVE_MONEY");
//        }
//                                    }
//                                }
//                                catch (Exception e)
//                                {
//                                        Log.d("dbsvm","Unable to send sms from paidAll method \nError: "+e.getMessage());
//                                    }


                            }
                        });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "No is clicked", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        Button unpaidAll=findViewById(R.id.unpaidAll);
        unpaidAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                oldAmountPaid="0";//As whole data is set to unpaid , so paid became 0
                FlagSendPaidMsg=1;

                String msg = "Are your sure you want to set whole month money to 'Not Paid', click Yes to set or click No to cancel.";
//                final String[] action = {""};
                Context context = SetUpForUpdateIndividualPersonDbData.this;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("Please Select any option");
                dialog.setMessage(msg);
//        dialog.setTitle("Dialog Box");
                dialog.setTitle("Warning!! Please be sure, this action is irreversible");
                dialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                db.unpaidAllIndividualPersonData();
                                tableView(db);

                            }
                        });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "No is clicked", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });



    }

//    Automatic sms services not permitted by user

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void notifyPersonWhoPaidBalancedMoney()
//    {
//        ActivityCompat.requestPermissions(SetUpForUpdateIndividualPersonDbData.this,new String[] {Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
//
//        SharedPreferences yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
//
//        String yourName=yourDetailsForMsgSharedPref.getString("yourName","");
//        String yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");
//
//        String phoneNumber="",Smsmessage="";
//        Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;//Change database name to access all person names
//        MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(SetUpForUpdateIndividualPersonDbData.this);
//        try {
//
//            phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);
//            Log.d("dbsvm","Phone Number "+ phoneNumber);
//        }
//        catch (Exception e)
//        {
//            Log.d("dbsvm","Error in fetching phoneNumber from database \nError: "+e.getMessage().toString());
//        }
//        String newAmountPaid="";
//        Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;
//
//        try {
//            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(SetUpForUpdateIndividualPersonDbData.this);
//            newAmountPaid = db.getIndividualPersonAmountPaidRecords();
//            newAmountPaid=(Float.parseFloat(newAmountPaid)-Float.parseFloat(oldAmountPaid))+"";
//            Log.d("dbsvm", "Amount  paid by : " + Params.CURRENT_GROUP_DB_NAME + " is " + newAmountPaid);
//        }
//        catch (Exception e)
//        {
//            Log.d("dbsvm","Unable to get sum \nError : "+e.getMessage());
//        }
//        String yourPhoneNumInfoForSms="",yourInfoForSms="";
//        if(yourPhoneNum.length()!=0)
//        {
//            yourPhoneNumInfoForSms=" to ( "+yourPhoneNum+" )";
//        }
//        yourInfoForSms= yourPhoneNumInfoForSms+"\n" + yourName;
//
//        String earlierPaidAmountMsg="";
//        try {
//            if (!(oldAmountPaid.equals(null)) && !(oldAmountPaid.equals("0"))) {
//                earlierPaidAmountMsg = "\nYour earlier paid amount is RS " + oldAmountPaid + ".";
//            }
//        }
//        catch (Exception e)
//        {
//            Log.d("dbsvm","Inside SetUpForUpdatIndividual ," +
//                    "Unable to get earlier amount paid msg \nError : "+e.getMessage());
//        }
//        Smsmessage="Dear Sir/Madam,\n" +
//                "You paid "+"RS "+newAmountPaid+" for  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//                yourInfoForSms+earlierPaidAmountMsg;
////        Smsmessage="Hello, how are your?";
//        Log.d("dbsvm","Message : "+Smsmessage);
//        if(Float.parseFloat(newAmountPaid)>0 && FlagSendPaidMsg==1) {
//            try {
//                SmsManager smsManager = SmsManager.getDefault();
//                //Send sms to payer
//                smsManager.sendTextMessage(phoneNumber, null, Smsmessage, null, null);
//                FlagSendPaidMsg=0;
//                //Send sms to receiver
//                try {
//                    String senderPhoneNumInfo = "";
//                    if (phoneNumber.length() != 0) {
//                        senderPhoneNumInfo = "( " + phoneNumber + " )";
//                    }
//                    String receiverMsg = "Dear Sir/Madam,\n" +
//                            "You received " + "RS " + newAmountPaid + " for  " + Params.CURRENT_GROUP_CONFIG_DB_NAME +
//                            " from " + senderPhoneNumInfo + "\n " + Params.CURRENT_GROUP_DB_NAME;
//                    smsManager.sendTextMessage(yourPhoneNum, null, receiverMsg, null, null);
////                    FlagSendPaidMsg=0;
//
//                } catch (Exception e) {
//                    Log.d("dbsvm", "Unable to send message to receiver");
//                }
//                Toast.makeText(SetUpForUpdateIndividualPersonDbData.this, "SMS sent to " + Params.CURRENT_GROUP_DB_NAME + " Successfully.",
//                        Toast.LENGTH_SHORT).show();
//
//                newAmountPaid="0";
//
//            } catch (Exception e) {
//
////            String msg = "Add "+Params.CURRENT_GROUP_DB_NAME+"'s Phone No. to send SMS." +
////                    "Click yes to continue";
//////                final String[] action = {""};
////            Context context = SetUpForUpdateIndividualPersonDbData.this;
////            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//////        dialog.setMessage("Please Select any option");
////            dialog.setMessage(msg);
//////        dialog.setTitle("Dialog Box");
////            dialog.setTitle("Add Phone Number");
////            dialog.setPositiveButton("YES",
////                    new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog,
////                                            int which) {
//////                                Toast.makeText(context, "Yes is clicked", Toast.LENGTH_LONG).show();
////                            startActivity(new Intent(SetUpForUpdateIndividualPersonDbData.this, SendReminderIndividualPersonGetMoney.class));
////
////                        }
////                    });
////            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
//////                        Toast.makeText(context, "No is clicked", Toast.LENGTH_LONG).show();
////                }
////            });
////            AlertDialog alertDialog = dialog.create();
////            alertDialog.show();
//
//                Toast.makeText(SetUpForUpdateIndividualPersonDbData.this, "Person Phone No. Not Found.",
//                        Toast.LENGTH_SHORT).show();
//
//                Log.d("dbsvm", "Unable to send sms automatically\nError: " + e.getMessage());
//            }
//        }
//
//    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tableView(MyDbHandlerIndividualPersonConfigs db)

    {
        try {
//            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(com.easyway2in.sqlitedb.TableView.this);
            table.removeAllViews();//To update new costs at same postion
            ArrayList<String> tempdate = new ArrayList<>();
            ArrayList<String> tempc1 = new ArrayList<>();
            ArrayList<String> tempc2 = new ArrayList<>();
            ArrayList<String> tempc3 = new ArrayList<>();
//            ArrayList<String> paid = new ArrayList<>();
//            ArrayList<String> notPaid = new ArrayList<>();
            String tDATE;
            double tC1=0,sC1 = 0, paidC = 0, notPaidC = 0, sC4 = 0, sC5 = 0, sC6 = 0, sC7 = 0, sC8 = 0, sC9 = 0, sC10 = 0, sC11 = 0, sC12 = 0, sC13 = 0, sC14 = 0, sC15 = 0;
            String tC2 = "", tC3 = "";
            double todayTotal = 0,todayTotalRs=0, todayTotalMonth = 0;
            //Get all contacts
            List<IndividualPersonData> allContacts = db.getAllIndividualPersonDataSavedRecords();//fetch all saved records
            for (IndividualPersonData contact : allContacts) {
//                    temp.add("Id: "+contact.getId()+"\nName: "+contact.getName()+
//                    "\nPhone Number: "+contact.getPhoneNumber());
//                    sum.add(contact.getC1());
                tDATE=contact.getDATE();
                tC1 = contact.getPERSON_DATA();
                tC2 = contact.getStatus();
                tC3 = contact.getADD_NOTE();
                //handling todays total on table view
//                todayTotalMonth += todayTotal;//updating monthly total milk
//                    Update individual user milk sum
                if(tC2.equals("Paid"))
                {
                    paidC+=tC1;
                }
                else
                {
                    notPaidC+=tC1;
                }
//                sC1 += tC1;

                tempdate.add(contact.getDATE());
//                tempdate.add(Contact.rate+"");
//                tempdate.add(tDATE);
                tempc1.add("           " + tC1 + "");
                tempc2.add("         " + tC2 + "");
                tempc3.add("           " + tC3 + "");

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

            String dayNum="S. No.   ";
            String hdate= "Date";
            String hc1 ="       "+"Rupees";
            String hc2 = "       "+"Status";
            String hc3 = "         "+"Additional Note                                                                                               ";


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



            hrow.addView(htvdayNum);
            hrow.addView(htvdate);
            hrow.addView(htv1);
            hrow.addView(htv2);
            hrow.addView(htv3);
            table.addView(hrow);

            //Log.d("dbsvm",tempdate.size()+"\n");            //Log.d("dbsvm",tempdate.size()+"\n");

            for (int i = 0; i < tempdate.size(); i++) {
                //Log.d("dbsvm",tempdate.get(i)+"\n");
                TableRow row = new TableRow(this);
                String date = tempdate.get(i);
                String c1 = tempc1.get(i);
                String c2 = tempc2.get(i);
                String c3 = tempc3.get(i);

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

                row.addView(tvDayNum);
                row.addView(tvdate);
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);

                table.addView(row);
            }
            //Milk per ltr rate to calculate total cost per user
            //Take input from user in text box
            double rate;
//            rate = 43.00;//temprary
            rate=Contact.Rate;

            //Add total footer row
            String addToCreateSpaceBefore="   ";
            String ftotal_head = "Total(Not Paid)";

            String ftotCostC1 = "         " + notPaidC ;



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




            frow.addView(faddToCreateSpaceBefore);
            frow.addView(ftvhead);
            frow.addView(ftv1);

            table.addView(frow);//first frow added

            //Adding frow2 for total cost(rs)















            String ftotal_head2 = "Total(Paid)";
            String ftotCostC12a="         " + paidC;

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



//            frow2.addView(faddToCreateSpaceBefore);//Add space before last two rows, this initialised
            // for frow above, and used here as purpose are same
//            frow2.addView(faddToCreateSpaceBefore);
            frow2.addView(faddToCreateSpaceBefore2);
            frow2.addView(ftvhead2);
            frow2.addView(ftv12a);

            table.addView(frow2);//second frow added



        } catch (Exception e) {
            //Log.d("svm", e.getMessage().toString());
//                textView.setText(e.getMessage().toString());
        }
        //Log.d("dbsvm","Total Table rows: "+table.getChildCount()+"");
//        return table;//To convert into pdf
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void selectDate()
    {
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SetUpForUpdateIndividualPersonDbData.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle(datePickerTitle);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d("sk", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

//                String mwith0=month+"";
//                if(month<10)//leading 0 with months less than 10th
//                {
//                    mwith0=String.format("%02d", month);
//                }
//                String date =  day + "/"+mwith0 + "/" +  year;
                String date=day+"/"+month+"/"+year;//Not leading zeros with date and month
////                mDisplayDate.setText(date);
//                Contact contact =new Contact();
                Contact.selectedDate=date;
                Contact.tempSelectedDate=date;//selected date is not object dependent, so value updated forever

//                contact.setSelectedDate(date);//update variable to date selected by user in Contact.java
                showSelecteDate.setText("Selected Date : "+date);


            }
        };
        Contact.tempSelectedDate="";
    }
}
