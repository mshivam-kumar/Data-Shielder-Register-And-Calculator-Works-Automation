package com.datahiveshield.mydata.mydairy.khata.files;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.IndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

//Process linked after proceed button in UpdateIndividualPersonDbData
public class UpdateIndividualPersonDbData extends AppCompatActivity {
    Button updateData;
    TextView setSelectedDateIntoBox;

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private String amountPaid="0";
    private String afterStatusPaid="Not Paid";
    private String beforePaidStatus="Paid";//as msg will only be sent when unpaid
    private int FlagSendPaidMsg=1;
    private String oldAmountPaid="0";
    // change to paid , therefore initially taking Paid for false condition


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_individual_person_db_data);

        //Set back button to go to previous activity
        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.editTextNumberDecimal1).requestFocus();

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


        TextView textViewDate=(TextView) findViewById(R.id.textViewDate);
        Contact contact=new Contact();
        textViewDate.setText("Today's Date : "+contact.getDefaultDate());

        setSelectedDateIntoBox=(TextView) findViewById(R.id.selectedDateToUpdate);
        setSelectedDateIntoBox.setText(contact.getSelectedDate());

        MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(UpdateIndividualPersonDbData.this);
        //Initialize defualtValues of assoiciated Customer person into textboxes
//        Contact init_contact=db.getDefaultDataForTextBoxes();
        IndividualPersonData init_contact=db.getSavedRecordForIndividualPersonSelectedDate(contact.selectedDate);
//        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();


        //Set default saved CustomerNames(Created by user) int textViews
        try {
            setDefaultValuesIntoTextBoxes();
//            CustomerNames defaultCustNames=new CustomerNames();
//            setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show
            //Log.d("dbsvm","Successfully fetched default customer names in InsertDbData");
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to set default fetched customer names in InsertDbData.java " +
                    "\nError : "+e.getMessage().toString());
        }

        //Set default values in text Boxes to update


        GetAlerts ga=new GetAlerts();



        Button updateData=(Button) findViewById(R.id.updateDataInDb);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                try {
                    IndividualPersonData updatedRecord=updateContactDataFromTextBoxes();
//                    textViewDate.setText(Contact.selectedDate);//checking object independent selectedDate value
                    try {
                        oldAmountPaid = db.getIndividualPersonAmountPaidRecords();//
                        Log.d("dbsvm","old amount not paid "+oldAmountPaid);
                    }
                    catch (Exception e)
                    {
                        Log.d("dbsvm","Unable to fetch not paid records");
                    }

                    MyDbHandlerIndividualPersonConfigs db=new MyDbHandlerIndividualPersonConfigs(UpdateIndividualPersonDbData.this);
                    db.updateIndividualPersonData(updatedRecord);
//                    try {
//                        if(Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE) && FlagSendPaidMsg==1) {
//
//                            notifyPersonWhoPaidMoney();
//                            FlagSendPaidMsg=0;
//                        }
//                        else//YOU_WILL_GIVE_MONEY interface To notify yourself
//                        {
//                            SharedPreferences yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
//
//                            String yourName=yourDetailsForMsgSharedPref.getString("yourName","");
//                            String yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");
//
//                            String newAmountPaid="";
//                            Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;
//
//
//                            String yourPhoneNumInfoForSms="",yourInfoForSms="";
//                            if(yourPhoneNum.length()!=0)
//                            {
//                                yourPhoneNumInfoForSms=" to ( "+yourPhoneNum+" )";
//                            }
//                            yourInfoForSms= yourPhoneNumInfoForSms+"\n" + yourName;
//
//                            String earlierPaidAmountMsg="";
//                            try {
//                                if (!(oldAmountPaid.equals(null))) {
//                                    earlierPaidAmountMsg = "\nYour earlier paid amount is RS " + oldAmountPaid + ".";
//                                }
//                            }
//                            catch (Exception e)
//                            {
//                                Log.d("dbsvm","Inside update click listener" +
//                                        "\nError :"+e.getMessage());
//                            }
//
//                            String Smsmessage="Dear Sir/Madam,\n" +
//                                    "You paid "+"RS "+newAmountPaid+" for  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//                                    yourInfoForSms+earlierPaidAmountMsg;
////        Smsmessage="Hello, how are your?";
//                            Log.d("dbsvm","Message : "+Smsmessage);
//                            try {
//                                newAmountPaid=updatedRecord.getPERSON_DATA()+"";
//                                SmsManager smsManager = SmsManager.getDefault();
//                                //Send sms to payer
//                                if(Float.parseFloat(newAmountPaid)>0 && FlagSendPaidMsg==1 &&updatedRecord.getStatus().equals("Paid")) {
//                                    smsManager.sendTextMessage(yourPhoneNum, null, Smsmessage, null, null);
//                                    FlagSendPaidMsg=0;
//                                    Toast.makeText(UpdateIndividualPersonDbData.this, "SMS sent to you successfully.",
//                                            Toast.LENGTH_SHORT).show();
//
//                                    newAmountPaid="0";
//                                }
//
//                            }
//                            catch (Exception e)
//                            {
//                                if(updatedRecord.getStatus().equals("Paid")) {
//                                    Toast.makeText(UpdateIndividualPersonDbData.this, "Unable to send you SMS.\n" +
//                                                    "Please add your phone number in settings.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//
//                                Log.d("dbsvm","Unable to send sms to you, YOU_WILL_GIVE_MONEY");
//                            }
//                        }
//
//                    }
//                    catch (Exception e)
//                    {
//                        Log.d("dbsvm","Unable to send SMS \nError :"+e.getMessage());
//                    }

                    ga.alertDialogBox(UpdateIndividualPersonDbData.this, "Date : " + contact.getSelectedDate() + "  record updated", "Record Updated");
                }
                catch (Exception e)
                {
                    ga.alertDialogBox(UpdateIndividualPersonDbData.this, "Unable to update data for "+"Date : " + contact.getSelectedDate() , "Unable to update");

                }

            }
        });

        Button viewData=(Button) findViewById(R.id.viewDataFromDb);
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                startActivity(new Intent(UpdateIndividualPersonDbData.this,TableTotalViewIndividualPersonData.class));

            }
        });

    }


//    Automatic sms services in YOU_WILL_GET_MONEY interface
//    not permitted by google

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void notifyPersonWhoPaidMoney()
//    {
//        if(Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE))
//        {
//            if(beforePaidStatus.equals("Not Paid") && afterStatusPaid.equals("Paid"))
//            {
//                String phoneNumber="";
//                Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;//Change database name to access all person names
//                MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(UpdateIndividualPersonDbData.this);
//                try {
//
//                    phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);
//                    Log.d("dbsvm","Person Phone phoneNumber "+ phoneNumber);
//                }
//                catch (Exception e)
//                {
//                    Log.d("dbsvm","Error in fetching phoneNumber from database \nError: "+e.getMessage().toString());
//                }
//
//                Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;//Change  database name back to
//
//                SharedPreferences yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
//
//                String yourName=yourDetailsForMsgSharedPref.getString("yourName","");
//                String yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");
//
//                Log.d("dbsvm","Person Phone : "+phoneNumber);
//                Log.d("dbsvm","Your Phone : "+yourPhoneNum);
//                String yourPhoneNumInfoForSms="",yourInfoForSms="";
//                if(yourPhoneNum.length()!=0)
//                {
//                    yourPhoneNumInfoForSms=" to ( "+yourPhoneNum+" )";
//                }
//                yourInfoForSms= yourPhoneNumInfoForSms+"\n" + yourName;
//
//
//                String Smsmessage="Dear Sir/Madam,\n" +
//                        "You paid "+"RS "+amountPaid+" for  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//                        yourInfoForSms;
////                            Log.d("dbsvm","Message : "+Smsmessage);
//                try {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    //Send sms to payer
//                    smsManager.sendTextMessage(phoneNumber, null, Smsmessage, null, null);
//                    //Send sms to receiver
//                    try {
//                        String senderPhoneNumInfo="";
//                        if(phoneNumber.length()!=0)
//                        {
//                            senderPhoneNumInfo="( "+phoneNumber+" )";
//                        }
//                        String receiverMsg="Dear Sir/Madam,\n" +
//                                "You received "+"RS "+amountPaid+" for  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//                                " from " +senderPhoneNumInfo+"\n "+Params.CURRENT_GROUP_DB_NAME;
//                        smsManager.sendTextMessage(yourPhoneNum, null, receiverMsg, null, null);
//
//                    }
//                    catch (Exception e)
//                    {
//                        Log.d("dbsvm","Unable to send message to receiver");
//                    }
//                    Toast.makeText(UpdateIndividualPersonDbData.this, "SMS sent to "+Params.CURRENT_GROUP_DB_NAME+" Successfully.",
//                            Toast.LENGTH_SHORT).show();
//
//                }
//                catch (Exception e)
//                {
//                    Log.d("dbsvm","Unable to send SMS \nError :"+e.getMessage());
//                }
//
//            }
//        }
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDefaultValuesIntoTextBoxes()
    {
        MyDbHandlerIndividualPersonConfigs db=new MyDbHandlerIndividualPersonConfigs(UpdateIndividualPersonDbData.this);
        EditText etC1 = findViewById(R.id.editTextNumberDecimal1);
//            EditText etC2 = findViewById(R.id.editTextNumberDecimal2);
        Spinner etC2=findViewById(R.id.spinner);
        EditText etC3 = findViewById(R.id.editTextNumberDecimal3);
//    Contact defaultData=db.getDefaultDataForTextBoxes();
        IndividualPersonData defaultData=db.getSavedRecordForIndividualPersonSelectedDate(Contact.selectedDate);
        //Log.d("dbsvm","default value data fetched successfully");

        int pos=etC2.getSelectedItemPosition();

//        Contact.milk_rate = Contact.getMilk_rate();//updating static value shown in TableTotalView
        etC1.setText(defaultData.getPERSON_DATA() + "");
        if(defaultData.getStatus().equals("Paid"))
        {
            etC2.setSelection(1);
            beforePaidStatus="Paid";
            FlagSendPaidMsg=0;
        }
        else
        {
            etC2.setSelection(0);
            beforePaidStatus="Not Paid";
            FlagSendPaidMsg=1;
        }
//        etC2.setSelection(pos);
//        etC2.setPrompt(defaultData.getStatus());
        etC3.setText(defaultData.getADD_NOTE() + "");

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public IndividualPersonData updateContactDataFromTextBoxes() {

        GetAlerts ga=new GetAlerts();
        IndividualPersonData newContact = new IndividualPersonData();

        //Get typed data from texboxes into string and insert into db table
        try {
            TextView etDATE=findViewById(R.id.selectedDateToUpdate);
            EditText etC1 = findViewById(R.id.editTextNumberDecimal1);
            Spinner etC2 = findViewById(R.id.spinner);
            EditText etC3 = findViewById(R.id.editTextNumberDecimal3);

            String sdate = etDATE.getText().toString();
            String sc1 = etC1.getText().toString();
            String sc2 = etC2.getSelectedItem().toString();
            String sc3 = etC3.getText().toString();

            //Create Contact object with user input to insert into db
//        Contact newContact1=new Contact(sdate,Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc14),Double.parseDouble(sc15));

//        EditText edate=findViewById(R.id.editTextDate);

//        newContact.setDATE("24-08-2021");//All db entries updated to same date
            newContact.setDATE(sdate);
            newContact.setPERSON_DATA(Double.parseDouble(sc1));
            newContact.setSTATUS(sc2);
            newContact.setADD_NOTE(sc3);

            afterStatusPaid=sc2;//To notify , change to paid
            amountPaid=sc1;

        } catch (Exception e) {
            ga.alertDialogBox(UpdateIndividualPersonDbData.this,"Unable to create " +
                    "contact for inserting\n" +
                    "Error : "+e.getMessage().toString(),"Unable to create contact");

        }
        return newContact;
    }

}
