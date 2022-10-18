package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.model.DefaultCustomerData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SendReminderGroupMembersGetMoney extends AppCompatActivity {
    private String phoneNumber = "";
    private String amountNotPaid = "0";
    SharedPreferences yourDetailsForMsgSharedPref;
    SharedPreferences.Editor yourDetailsSPrefEditor;
    private String yourName;
    private String yourPhoneNum;

    String selectedGroupName = "";
    public static String[] str;
    public int i;
    public int sizeOfReceivedArrayList = 0;
    public String noGroupFound;
    ArrayList<String> customerDatabasesArrayList = new ArrayList<>();

    private static final long TIME_INTERVAL_GAP = 500;
    private long lastTimeClicked = System.currentTimeMillis();
    private SendReminderGroupMembersGetMoney mContext;
    private ArrayList<DefaultCustomerData> defaultCustomerDataArrayList;
    private EditText editTextPhoneNum;
    private static ArrayList<Double> totalUnitArrayList;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_reminder_individual_group_members_get_money);


        editTextPhoneNum = findViewById(R.id.editTextNewPhoneNum);

        editTextPhoneNum.requestFocus();
        //Setting back button in toolbar explicitely
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        yourDetailsForMsgSharedPref = getSharedPreferences("yourDetailsForMsgSharedPref", MODE_PRIVATE);
        yourDetailsSPrefEditor = yourDetailsForMsgSharedPref.edit();

        yourName = yourDetailsForMsgSharedPref.getString("yourName", "");
        yourPhoneNum = yourDetailsForMsgSharedPref.getString("yourPhoneNum", "");

        EditText editTextYourName = findViewById(R.id.editTextYourName);
        EditText editTextYourPhoneNum = findViewById(R.id.editTextYourPhoneNum);
        editTextYourName.setText(yourName);
        editTextYourPhoneNum.setText(yourPhoneNum);


        MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(SendReminderGroupMembersGetMoney.this);

//       Get the current names had by user from database
//        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();

        defaultCustomerDataArrayList = db.getDefaultCustomerNameAndPhoneNumber();
        try {

//            for (DefaultCustomerData c : defaultCustomerDataArrayList) {

                Log.d("dbsvm", "Inside SendReminderGroupMembersGetMoney Name : " + defaultCustomerDataArrayList.get(0).getName() + " Phone Number : " +
                        defaultCustomerDataArrayList.get(0).getPhoneNumber());
//            }

        } catch (Exception e) {
            Log.d("dbsvm", "Inside SendReminderGroupMembersGetMoney Error :" + e.getMessage());
        }

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToShowExistingGroups);

        try {
            tableView(db);//tableView must be called before showAllCustomerGroups as tableView method updates a static arrayList that is used in showAllCustomerGroups
            showAllCustomerGroups();
        } catch (Exception e) {
            Log.d("dbsvm", "Unable to fetch Customer database names in UpdateCustomerGroup.java(inside onCreate)");
        }

        //Remove cursor form selected number picker element
//        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //Increase font size of first element of number picker, without touching
        //as others will be affected after touch
        View child = numberPicker.getChildAt(0);
        if (child instanceof EditText) {
            try {
                @SuppressLint("SoonBlockedPrivateApi") Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                selectorWheelPaintField.setAccessible(true);
//                    ((EditText) child).setTextColor(DeleteConfigureCustomerGroupDataWithMonthAndYear.this.getResources().getColor(colorRes));
                ((EditText) child).setTextSize(20);
                numberPicker.performClick();
//                    TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);
//                    showSelectedGroupName.setText("Selected person : " + ((EditText) child).getText().toString());

            } catch (NoSuchFieldException e) {
            } catch (IllegalArgumentException e) {
            }
        }

        //Increase the font size of current selected element
        numberPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                int currentTouchAction = event.getAction();
                if (currentTouchAction == MotionEvent.ACTION_UP) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setSelectedTextColor((NumberPicker) v);
                        }
                    }, 0);
                }
                return false;
            }
        });


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int numberPickerIndex = numberPicker.getValue();
                selectedGroupName = numberPicker.getDisplayedValues()[numberPickerIndex];
                editTextPhoneNum=findViewById(R.id.editTextNewPhoneNum);

                try {
                    amountNotPaid=  (totalUnitArrayList.get(numberPickerIndex)*Contact.getRate())+"";
                    String selectedMemberphoneNumber = defaultCustomerDataArrayList.get(numberPickerIndex).getPhoneNumber();
                    phoneNumber=selectedMemberphoneNumber;
//                    editTextPhoneNum.setText(selectedMemberphoneNumber);
//                    Log.d("dbsvm","selected member Phone "+selectedMemberphoneNumber);
                } catch (Exception e) {
                    editTextPhoneNum.setText("");
                    Log.d("dbsm", "Inside number picker setValueChangedListener Unable to set phone numbere Error  : " + e.getMessage());
                }

                TextView showSelectedGroupName = findViewById(R.id.textViewSelectedGroupToRemind);
                showSelectedGroupName.setText("Selected Person : " + selectedGroupName);
                editTextPhoneNum.setText(phoneNumber);
                TextView personName = findViewById(R.id.textViewPersonNameToSend);
                String textToset = "To     [  " + selectedGroupName + " ]     for     [  " + "\u20B9  "
                        + amountNotPaid + " ]     at ";
                personName.setText(textToset);




            }
        });


        editTextYourName.setText(yourName);
        editTextYourPhoneNum.setText(yourPhoneNum);



        Button updatePhoneNumber = findViewById(R.id.updateCustPhoneNumberBtn);
        EditText editTextnewPhoneNumber = findViewById(R.id.editTextYourPhoneNum);
        updatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String personNewPhoneNum = editTextPhoneNum.getText().toString().trim();

                    if (personNewPhoneNum.length() == 10 || personNewPhoneNum.length() == 12) {
//                        db1.UpdateIndividualPersonPhoneNumber(personNewPhoneNum);
                        phoneNumber = personNewPhoneNum;
                        db.updateDefaultCustomerGroupMemberPhoneNumbber(selectedGroupName, personNewPhoneNum);

                        defaultCustomerDataArrayList = db.getDefaultCustomerNameAndPhoneNumber();//Load the data again after updation in phone number
//                    phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);

                        Toast.makeText(SendReminderGroupMembersGetMoney.this, selectedGroupName + " number New Number Updated Successfully.",
                                Toast.LENGTH_SHORT).show();
                    } else if (personNewPhoneNum.length() != 0) {
                        Toast.makeText(SendReminderGroupMembersGetMoney.this, "Invalid Number Entered.\n" +
                                "Unable To Update.", Toast.LENGTH_SHORT).show();

                    }
//                    Toast.makeText(SendReminderIndividualPersonGetMoney.this, "New Number Updated Successfully.",
//                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d("dbsvm", "Inside SendReminderIndividual, unable to update person phone number.\nError : " + e.getMessage());
                }


            }
        });


        editTextPhoneNum.setText(phoneNumber);
        TextView personName = findViewById(R.id.textViewPersonNameToSend);
        String textToset = "To     [  " + Params.CURRENT_GROUP_DB_NAME + " ]     for     [  " + "\u20B9  "
                + amountNotPaid + " ]     at ";
        personName.setText(textToset);


        Button sms = findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yourDetailsSPrefEditor.putString("yourName", editTextYourName.getText().toString().trim());
                yourDetailsSPrefEditor.commit();
                yourDetailsSPrefEditor.putString("yourPhoneNum", (editTextYourPhoneNum).getText().toString().trim());
                yourDetailsSPrefEditor.commit();

                String newPhoneNum = editTextPhoneNum.getText().toString();
                if (!phoneNumber.equals(newPhoneNum)) {
                    updatePhoneNumber.callOnClick();//Calling button listener
                    //to update phone with clicking on button
                }
//                phoneNumber=editTextPhoneNum.getText().toString().trim();


                yourName = yourDetailsForMsgSharedPref.getString("yourName", "");
                yourPhoneNum = yourDetailsForMsgSharedPref.getString("yourPhoneNum", "");


                String yourPhoneNumInfoForMsg = "", yourInfoForMsg = "";
                if (yourPhoneNum.length() != 0) {
                    yourPhoneNumInfoForMsg = " to ( " + yourPhoneNum + " )";
                }
                yourInfoForMsg = yourPhoneNumInfoForMsg + "\n" + yourName;


                String message = "Dear Sir/Madam,\n" +
                        "Your payment of " + "\u20B9 " + amountNotPaid + " is pending for " + Params.CURRENT_GROUP_CONFIG_DB_NAME +
                        yourInfoForMsg;


//                Log.d("dbsvm","Phone phoneNumber"+phoneNumber);


                //Send SMS automatically without user involvement , only press button
//                SmsManager smsManager=SmsManager.getDefault();
//                smsManager.sendTextMessage(phoneNumber,null,message,null,null);

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", phoneNumber);
                smsIntent.putExtra("sms_body", message);

                try {
                    startActivity(smsIntent);

                    //If invalid number enterd ,then send msg with old number only
                    //After coming back to display show old number in editText box
                    editTextPhoneNum.setText(phoneNumber);
//                    Toast.makeText(SendReminderIndividualPersonGetMoney.this,"SMS sent successfully.",
//                            Toast.LENGTH_SHORT).show();
                    Log.d("Finished sending SMS...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendReminderGroupMembersGetMoney.this,
                            "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button sendWhatsAppMsg = findViewById(R.id.whatsApp);
        sendWhatsAppMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;//Change database name to access all person names
//                MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(SendReminderIndividualPersonGetMoney.this);
                try {

//                    phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);

                    String newPhoneNum = editTextPhoneNum.getText().toString();
                    if (!phoneNumber.equals(newPhoneNum)) {
                        updatePhoneNumber.callOnClick();//Calling button listener
                        //to update phone with clicking on button
                    }

//                    Log.d("dbsvm","Phone phoneNumber "+ phoneNumber);
                } catch (Exception e) {
                    Log.d("dbsvm", "Error in fetching phoneNumber from database \nError: " + e.getMessage().toString());
                }
                int FlagSendWhatsAppMsg = 1;
//                phoneNumber=phoneNumber.trim();//remove extra spaces in last/start

//                phoneNumber=editTextPhoneNum.getText().toString().trim();

                String whatsAppPhoneNumber = phoneNumber;
                if (phoneNumber.length() == 10) {
                    whatsAppPhoneNumber = "91" + phoneNumber;
                } else if (phoneNumber.length() > 12 || phoneNumber.length() < 10 || phoneNumber.length() == 11) {
                    FlagSendWhatsAppMsg = 0;
                    GetAlerts ga = new GetAlerts();
                    ga.alertDialogBox(SendReminderGroupMembersGetMoney.this,
                            phoneNumber.length() + " digit number entered.\n" +
                                    "Please enter the correct 10 digit phone number.",
                            "Incorrect phone number");

                }
                try {
                    yourDetailsSPrefEditor.putString("yourName", editTextYourName.getText().toString());
                    yourDetailsSPrefEditor.commit();
                    yourDetailsSPrefEditor.putString("yourPhoneNum", (editTextYourPhoneNum).getText().toString());
                    yourDetailsSPrefEditor.commit();


                    yourName = yourDetailsForMsgSharedPref.getString("yourName", "");
                    yourPhoneNum = yourDetailsForMsgSharedPref.getString("yourPhoneNum", "");


                    String yourPhoneNumInfoForMsg = "", yourInfoForMsg = "";
                    if (yourPhoneNum.length() != 0) {
                        yourPhoneNumInfoForMsg = " to ( " + yourPhoneNum + " )";
                    }
                    yourInfoForMsg = yourPhoneNumInfoForMsg + "\n" + yourName;

                    String message = "Dear Sir/Madam,\n" +
                            "Your payment of " + "\u20B9 " + amountNotPaid + " is pending for " + Params.CURRENT_GROUP_CONFIG_DB_NAME +
                            yourInfoForMsg;

                    if (FlagSendWhatsAppMsg == 1) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + whatsAppPhoneNumber +
                                "&text=" + message));
                        startActivity(i);
                        //If invalid number enterd ,then send msg with old number only
                        //After coming back to display show old number in editText box
                        editTextPhoneNum.setText(phoneNumber);

                    }


                } catch (Exception e) {
                    Log.d("dbsvm", "Unable to send whatsapp message\nError : " + e.getMessage());
                }

            }
        });
    }

    public void showAllCustomerGroups() {
//        MyDBHandlerIndividualPersonData db = new MyDBHandlerIndividualPersonData(SendReminderGroupMembersGetMoney.this);
        MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(SendReminderGroupMembersGetMoney.this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToShowExistingGroups);


        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                customerDatabasesArrayList = new ArrayList<>();
                CustomerNames defaultCustNames = db.getDefaultCustomerDataForTextBoxes();
                customerDatabasesArrayList.add(defaultCustNames.getNAME1());
                customerDatabasesArrayList.add(defaultCustNames.getNAME2());
                customerDatabasesArrayList.add(defaultCustNames.getNAME3());
                customerDatabasesArrayList.add(defaultCustNames.getNAME4());
                customerDatabasesArrayList.add(defaultCustNames.getNAME5());
                customerDatabasesArrayList.add(defaultCustNames.getNAME6());
                customerDatabasesArrayList.add(defaultCustNames.getNAME7());
                customerDatabasesArrayList.add(defaultCustNames.getNAME8());
                customerDatabasesArrayList.add(defaultCustNames.getNAME9());
                customerDatabasesArrayList.add(defaultCustNames.getNAME10());
                customerDatabasesArrayList.add(defaultCustNames.getNAME11());
                customerDatabasesArrayList.add(defaultCustNames.getNAME12());
                customerDatabasesArrayList.add(defaultCustNames.getNAME13());
                customerDatabasesArrayList.add(defaultCustNames.getNAME14());
                customerDatabasesArrayList.add(defaultCustNames.getNAME15());

            }
            i = 0;
            sizeOfReceivedArrayList = customerDatabasesArrayList.size();
            str = new String[sizeOfReceivedArrayList];
            for (String cDatabaseName : customerDatabasesArrayList) {
                str[i] = cDatabaseName;
                i++;
            }
        } catch (Exception e) {
            Log.d("dbsvm", "unable to fetch database name data\n" +
                    "Error : " + e.getMessage().toString());
        }


        try {
//    numberPicker1.setDisplayedValues(genders);
            if (sizeOfReceivedArrayList != 0) {
                numberPicker.setMaxValue(str.length - 1);
                numberPicker.setMinValue(0);
                numberPicker.setWrapSelectorWheel(true);
                numberPicker.setDisplayedValues(str);
                //show number picker 0 index and then change as user touches by default it does not show 1st element
                try {
                    int numberPickerIndex = 0;
                    selectedGroupName = numberPicker.getDisplayedValues()[numberPickerIndex];
                    try {
                        amountNotPaid=  (totalUnitArrayList.get(numberPickerIndex)*Contact.getRate())+"";
                        String selectedMemberphoneNumber = defaultCustomerDataArrayList.get(numberPickerIndex).getPhoneNumber();
                        phoneNumber=selectedMemberphoneNumber;
//                        editTextPhoneNum.setText(selectedMemberphoneNumber);
                    } catch (Exception e) {
                        editTextPhoneNum.setText("");
                        Log.d("dbsm", "Inside number picker setValueChangedListener Unable to set phone numbere Error  : " + e.getMessage());
                    }
                    TextView showSelectedGroupName = findViewById(R.id.textViewSelectedGroupToRemind);
                    showSelectedGroupName.setText("Selected Person : " + selectedGroupName);
                    editTextPhoneNum.setText(phoneNumber);
                    TextView personName = findViewById(R.id.textViewPersonNameToSend);
                    String textToset = "To     [  " + selectedGroupName + " ]     for     [  " + "\u20B9  "
                            + amountNotPaid + " ]     at ";
                    personName.setText(textToset);




                } catch (Exception e) {
                    Log.d("dbsvm", "Inside showAllCustomerGroups Error : " + e.getMessage());
                }
//            setNumberPicker(yourNumberPicker,mValues);
            } else {
//                numberPicker.setDisplayedValues("");
                TextView atShowSelectedGroup = findViewById(R.id.textViewSelectedGroupToRemind);
                atShowSelectedGroup.setText("No person found");
            }
        } catch (Exception e) {
            Log.d("dbsvm", "String picker error \n" +
                    "Error : " + e.getMessage().toString());
        }

    }


    public void setSelectedTextColor(NumberPicker np) {
        final int count = np.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = np.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    @SuppressLint("SoonBlockedPrivateApi") Field selectorWheelPaintField = np.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
//                    ((EditText) child).setTextColor(DeleteConfigureCustomerGroupDataWithMonthAndYear.this.getResources().getColor(colorRes));
                    ((EditText) child).setTextSize(20);
                    np.performClick();
//                    TextView showSelectedGroupName = findViewById(R.id.textViewSelectedGroupToRemind);
//                    showSelectedGroupName.setText("Selected person : " + ((EditText) child).getText().toString());

                } catch (NoSuchFieldException e) {
                } catch (IllegalArgumentException e) {
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tableView(MyDbHandlerGroupConfigs db) {
        try {

             totalUnitArrayList = new ArrayList<>();
            String tDATE;
            double sC1 = 0, sC2 = 0, sC3 = 0, sC4 = 0, sC5 = 0, sC6 = 0, sC7 = 0, sC8 = 0, sC9 = 0, sC10 = 0, sC11 = 0, sC12 = 0, sC13 = 0, sC14 = 0, sC15 = 0;
            double tC1 = 0, tC2 = 0, tC3 = 0, tC4 = 0, tC5 = 0, tC6 = 0, tC7 = 0, tC8 = 0, tC9 = 0, tC10 = 0, tC11 = 0, tC12 = 0, tC13 = 0, tC14 = 0, tC15 = 0;
            double todayTotal = 0, todayTotalRs = 0, todayTotalMonth = 0;
            //Get all contacts
            List<Contact> allContacts = db.getAllSavedRecords();//fetch all saved records
            for (Contact contact : allContacts) {
//                    temp.add("Id: "+contact.getId()+"\nName: "+contact.getName()+
//                    "\nPhone Number: "+contact.getPhoneNumber());
//                    sum.add(contact.getC1());
                tDATE = contact.getDATE();
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
//                todayTotal = tC1 + tC2 + tC3 + tC4 + tC5 + tC6 + tC7 + tC8 + tC9 + tC10 + tC11 + tC12 + tC13 + tC14 + tC15;
//                todayTotalRs = todayTotal * Contact.getRate();
//                todayTotalMonth += todayTotal;//updating monthly total milk
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

//                todayTotalArrayList.add(todayTotal+"");//adding todays total in arrayList
//                todayTotalRSArrayList.add(todayTotalRs+"");


//            Log.d("dbsvm","Inside SendReminderGroupMembersGetMoney total : "+todayTotal);
//            Log.d("dbsvm","Inside SendReminderGroupMembersGetMoney total rs : "+todayTotalRs);
            }
            totalUnitArrayList.add(sC1);
            totalUnitArrayList.add(sC2);
            totalUnitArrayList.add(sC3);
            totalUnitArrayList.add(sC4);
            totalUnitArrayList.add(sC5);
            totalUnitArrayList.add(sC6);
            totalUnitArrayList.add(sC7);
            totalUnitArrayList.add(sC8);
            totalUnitArrayList.add(sC9);
            totalUnitArrayList.add(sC10);
            totalUnitArrayList.add(sC11);
            totalUnitArrayList.add(sC12);
            totalUnitArrayList.add(sC13);
            totalUnitArrayList.add(sC14);
            totalUnitArrayList.add(sC15);
            Log.d("dbsvm","Inside SendReminderGroupMembersGetMoney person1 : "+sC1);
            Log.d("dbsvm","Inside SendReminderGroupMembersGetMoney person 2 : "+sC2);
            Log.d("dbsvm","Inside SendReminderGroupMembersGetMoney person 3 : "+sC3);

        }
        catch (Exception e)
        {
            Log.d("dbsvm","Inside SendReminderGroupMembersGetMoney.tableView Unable to calculate" +
                    " total, Error : "+e.getMessage());
        }
    }
}