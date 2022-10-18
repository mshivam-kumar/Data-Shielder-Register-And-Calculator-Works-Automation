package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDBHandlerIndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SendReminderIndividualPersonGetMoney extends AppCompatActivity {
    private String phoneNumber="";
    private String amountNotPaid="0";
    SharedPreferences yourDetailsForMsgSharedPref;
    SharedPreferences.Editor yourDetailsSPrefEditor;
    private String yourName;
    private String yourPhoneNum;



    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_reminder_individual_person_get_money);

        EditText editTextPhoneNum=findViewById(R.id.editTextNewPhoneNum);
        editTextPhoneNum.requestFocus();

        //Setting back button in toolbar explicitely
        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
        yourDetailsSPrefEditor=yourDetailsForMsgSharedPref.edit();

        yourName=yourDetailsForMsgSharedPref.getString("yourName","");
        yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");

        EditText editTextYourName=findViewById(R.id.editTextYourName);
        EditText editTextYourPhoneNum=findViewById(R.id.editTextYourPhoneNum);
        editTextYourName.setText(yourName);
        editTextYourPhoneNum.setText(yourPhoneNum);







        //Params.Current_Running_Db is the person name whose phoneNumber is to be fetched
        //Get phone phoneNumber for the person accessed
        Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;//Change database name to access all person names
        MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(SendReminderIndividualPersonGetMoney.this);
        try {

            phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);
            Log.d("dbsvm","Phone phoneNumber "+ phoneNumber);
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Error in fetching phoneNumber from database \nError: "+e.getMessage().toString());
        }

        Button updatePhoneNumber=findViewById(R.id.updateCustPhoneNumberBtn);
        EditText editTextnewPhoneNumber=findViewById(R.id.editTextYourPhoneNum);
        updatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String personNewPhoneNum=editTextPhoneNum.getText().toString().trim();

                    if(personNewPhoneNum.length()==10 || personNewPhoneNum.length()==12)
                    {
                    db1.UpdateIndividualPersonPhoneNumber(personNewPhoneNum);
                    phoneNumber=personNewPhoneNum;
//                    phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);

                        Toast.makeText(SendReminderIndividualPersonGetMoney.this, "New Number Updated Successfully.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(personNewPhoneNum.length()!=0)
                    {
                        Toast.makeText(SendReminderIndividualPersonGetMoney.this, "Invalid Number Entered.\n" +
                                "Unable To Update.", Toast.LENGTH_SHORT).show();

                    }
//                    Toast.makeText(SendReminderIndividualPersonGetMoney.this, "New Number Updated Successfully.",
//                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Inside SendReminderIndividual, unable to update person phone number.\nError : "+e.getMessage());
                }


            }
        });

        Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;//Change  database name back to
        //accessed person name to run opertions on its data
        try {
            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(SendReminderIndividualPersonGetMoney.this);
            amountNotPaid = db.getIndividualPersonAmountNotPaidRecords();
            Log.d("dbsvm", "Amount not paid by : " + Params.CURRENT_GROUP_DB_NAME + " is " + amountNotPaid);
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to get sum \nError : "+e.getMessage());
        }

        editTextPhoneNum.setText(phoneNumber);
        TextView personName=findViewById(R.id.textViewPersonNameToSend);
        String textToset="To     [  "+Params.CURRENT_GROUP_DB_NAME+" ]     for     [  "+"\u20B9  "
                 +amountNotPaid+" ]     at ";
        personName.setText(textToset);

        Button sms=findViewById(R.id.sms);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yourDetailsSPrefEditor.putString("yourName",editTextYourName.getText().toString().trim());
                yourDetailsSPrefEditor.commit();
                yourDetailsSPrefEditor.putString("yourPhoneNum",(editTextYourPhoneNum).getText().toString().trim());
                yourDetailsSPrefEditor.commit();

                String newPhoneNum=editTextPhoneNum.getText().toString();
                if(!phoneNumber.equals(newPhoneNum)) {
                    updatePhoneNumber.callOnClick();//Calling button listener
                    //to update phone with clicking on button
                }
//                phoneNumber=editTextPhoneNum.getText().toString().trim();


                yourName=yourDetailsForMsgSharedPref.getString("yourName","");
                yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");


                String yourPhoneNumInfoForMsg="",yourInfoForMsg="";
                if(yourPhoneNum.length()!=0)
                {
                    yourPhoneNumInfoForMsg=" to ( "+yourPhoneNum+" )";
                }
                yourInfoForMsg= yourPhoneNumInfoForMsg+"\n" + yourName;


                String message="Dear Sir/Madam,\n" +
                               "Your payment of "+"\u20B9 "+amountNotPaid+" is pending for "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
                        yourInfoForMsg;


//                Log.d("dbsvm","Phone phoneNumber"+phoneNumber);


                //Send SMS automatically without user involvement , only press button
//                SmsManager smsManager=SmsManager.getDefault();
//                smsManager.sendTextMessage(phoneNumber,null,message,null,null);

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address"  , phoneNumber);
                smsIntent.putExtra("sms_body"  , message);

                try {
                    startActivity(smsIntent);

                    //If invalid number enterd ,then send msg with old number only
                    //After coming back to display show old number in editText box
                    editTextPhoneNum.setText(phoneNumber);
//                    Toast.makeText(SendReminderIndividualPersonGetMoney.this,"SMS sent successfully.",
//                            Toast.LENGTH_SHORT).show();
                    Log.d("Finished sending SMS...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendReminderIndividualPersonGetMoney.this,
                            "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button sendWhatsAppMsg=findViewById(R.id.whatsApp);
        sendWhatsAppMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;//Change database name to access all person names
//                MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(SendReminderIndividualPersonGetMoney.this);
                try {

//                    phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);

                    String newPhoneNum=editTextPhoneNum.getText().toString();
                    if(!phoneNumber.equals(newPhoneNum)) {
                        updatePhoneNumber.callOnClick();//Calling button listener
                        //to update phone with clicking on button
                    }

//                    Log.d("dbsvm","Phone phoneNumber "+ phoneNumber);
                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Error in fetching phoneNumber from database \nError: "+e.getMessage().toString());
                }
                int FlagSendWhatsAppMsg=1;
//                phoneNumber=phoneNumber.trim();//remove extra spaces in last/start

//                phoneNumber=editTextPhoneNum.getText().toString().trim();

                String whatsAppPhoneNumber=phoneNumber;
                if(phoneNumber.length()==10)
                {
                    whatsAppPhoneNumber="91"+phoneNumber;
                }
                else if(phoneNumber.length()>12 || phoneNumber.length()<10 || phoneNumber.length()==11)
                {
                    FlagSendWhatsAppMsg=0;
                    GetAlerts ga=new GetAlerts();
                    ga.alertDialogBox(SendReminderIndividualPersonGetMoney.this,
                            phoneNumber.length()+" digit number entered.\n" +
                                    "Please enter the correct 10 digit phone number.",
                            "Incorrect phone number");

                }
                try {
                    yourDetailsSPrefEditor.putString("yourName",editTextYourName.getText().toString());
                    yourDetailsSPrefEditor.commit();
                    yourDetailsSPrefEditor.putString("yourPhoneNum",(editTextYourPhoneNum).getText().toString());
                    yourDetailsSPrefEditor.commit();




                    yourName=yourDetailsForMsgSharedPref.getString("yourName","");
                    yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");


                    String yourPhoneNumInfoForMsg="",yourInfoForMsg="";
                    if(yourPhoneNum.length()!=0)
                    {
                        yourPhoneNumInfoForMsg=" to ( "+yourPhoneNum+" )";
                    }
                    yourInfoForMsg= yourPhoneNumInfoForMsg+"\n" + yourName;

                    String message="Dear Sir/Madam,\n" +
                            "Your payment of "+"\u20B9 "+amountNotPaid+" is pending for "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
                            yourInfoForMsg;

                    if(FlagSendWhatsAppMsg==1) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + whatsAppPhoneNumber +
                                "&text=" + message));
                        startActivity(i);
                        //If invalid number enterd ,then send msg with old number only
                        //After coming back to display show old number in editText box
                        editTextPhoneNum.setText(phoneNumber);

                    }


                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Unable to send whatsapp message\nError : "+e.getMessage());
                }

            }
        });
    }


}
