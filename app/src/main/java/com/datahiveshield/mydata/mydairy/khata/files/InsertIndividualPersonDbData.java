package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.IndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InsertIndividualPersonDbData extends AppCompatActivity {
    TextView textViewDate;
    //    EditText etbDate;
    Button tableViewData;
    IndividualPersonData newContact;
    int flagCreatedContact=0;
    int flagCheckMonthYearConfiguration=0;
    int validMonthNum=0;
    int validYearNum=0;


    EditText etDATE;
    String dateChoosenToInsert="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar tempDateChoosenCalendar;
    SimpleDateFormat sdf;
    private String  givenAmountToNotifyWithSms;

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    EditText etPaidNotPaid;


    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_individual_person_db_data);


        TextView dataAccessed=findViewById(R.id.infoHeading);
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );

        findViewById(R.id.editTextNumberDecimal1).requestFocus();
//        //Ask below user permission to send sms
//        if(Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE)) {
//            ActivityCompat.requestPermissions(InsertIndividualPersonDbData.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
//        }

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
        Calendar calendar = Calendar.getInstance();

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


        textViewDate = (TextView) findViewById(R.id.textViewDate);
        etDATE=findViewById(R.id.editTextDateInInsert);
        etDATE.setText(Contact.DefaultDate);//show current date in Date text box
        textViewDate.setText("Today's Date : " + Contact.DefaultDate);

        checkDefaultDateConfigurationWithRequiredsMonthYearConfig();//method call

        String[] temp=Params.CURRENT_GROUP_CONFIG_DB_NAME.split(" ");

        //Cast string month name to month number from configuration name
        //to show to user for inserting in invalid configuration date
        validMonthNum= Month.valueOf(temp[0].toUpperCase()).getValue();
        validYearNum=Integer.parseInt(temp[1]);//temp[1] int type
        //Log.d("dbsvm",temp[0]+" "+temp[1]+" "+validYearNum);
        int mwith0_=0;



        MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(InsertIndividualPersonDbData.this);
        //Initialize defualtValues of assoiciated Customer person with names into textboxes
//        Contact init_record=db.getDefaultDataForTextBoxes();
//        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();

        db.getAllIndividualPersonDataSavedRecords();
//Choose date from popup by clicking Choose Date button , along with typing text in box
//        Button mDisplayDate = (Button) findViewById(R.id.chooseDateToInsert);

        etDATE.setFocusable(false);//Make Date textBox clickable
        etDATE.setClickable(true);//To select date from popup rather than to write

        GetAlerts ga=new GetAlerts();
        etDATE.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        InsertIndividualPersonDbData.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        NumberPicker numberPicker = new NumberPicker(this);
        String[] arrayString= new String[]{"Not Paid","Paid"};
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(arrayString.length-1);

        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                // TODO Auto-generated method stub
                return arrayString[value];
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d("sk", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date=day+"/"+month+"/"+year;//Not leading zeros with date and month
                etDATE.setText(date);

                sdf = new SimpleDateFormat("MMMM/yyyy");
                tempDateChoosenCalendar=Calendar.getInstance();
////                 int mwith0=0;
//                if(month<10)//leading 0 with months less than 10th
//                {
//                    mwith0=Integer.parseInt(String.format("%02d", month));
//                }
                tempDateChoosenCalendar.set(year, month-1, day);
                String tempdateString = sdf.format(tempDateChoosenCalendar.getTime());


                dateChoosenToInsert=tempdateString.replace("/"," ");
                if(dateChoosenToInsert.equals(Params.CURRENT_GROUP_CONFIG_DB_NAME))
                {
                    flagCheckMonthYearConfiguration=1;//Inserting in same month year configuration
                }
                else
                {
                    flagCheckMonthYearConfiguration=0;
                    ga.alertDialogBox(InsertIndividualPersonDbData.this,"Please choose date only for " +
                            "\n' "+validMonthNum+"/"+validYearNum+" '  ( "+Params.CURRENT_GROUP_CONFIG_DB_NAME+" ) configuration. You can not insert " +
                            "for different configuration.","Invalid Date Choosen");
                }
                //Log.d("dbsvm","dateChoosenToInsert "+dateChoosenToInsert);
                //Log.d("dbsvm","CURRENT_GROUP_CONFIG_DB_NAME "+Params.CURRENT_GROUP_CONFIG_DB_NAME);
                dateChoosenToInsert="";
                flagCreatedContact=0;

            }

        };


//        CustomerNames newNames=new CustomerNames();
        //Temprary changing user names when change button clicked from main activity
        //Using ChangeDefaulderCustomerNames funcion as both classes textview ids are same
//        try {
        //Set default saved CustomerNames(Created by user) int textViews
        try {
//                CustomerNames defaultCustNames=new CustomerNames();
//            setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show
            //Log.d("dbsvm","Successfully fetched default customer names in InsertIndividualPersonDbData");

//            setDefaultValuesIntoTextBoxes();//set default values in text boxes in Insert activity
            //Log.d("dbsvm","Successfully fetched default values in InsertIndividualPersonDbData");

        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to set default fetched customer names in InsertIndividualPersonDbData.java " +
                    "\nError : "+e.getMessage().toString());
        }


//        GetAlerts ga = new GetAlerts();


        @SuppressLint("CutPasteId") Button createRecordBeforeInsertion=findViewById(R.id.createRecordBeforeInsert);
        createRecordBeforeInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                //flagCreatedContact tells whether the valid record is created or not, o for not , 1 for yes
                flagCreatedContact=0;//reset to false before creating new record
                newContact = createContactBeforeInsert();
//                    flagCreatedContact will be overridden in createContactBeforeInsert
                //if record is created

                //Unable to create condition dialogBox handled in createContactBeforeInsert method

            }
        });
        Button insertData = (Button) findViewById(R.id.insertDataDb);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(InsertIndividualPersonDbData.this);
                if (flagCreatedContact == 1 &&flagCheckMonthYearConfiguration==1) {
                    //call MyDbHandlerIndividualPersonConfigs method to insert record
//                    db.updateDefaultData(newContact,1);//update default data other than date
//                    db.insertData(createContactBeforeInsert());
                    if(db.isIndividualPersonDataDateExist(newContact.getDATE())==0)//If date not already exists then insert new record
                    {
                        SQLiteDatabase db1=db.getWritableDatabase();//pass this to create table
                        db.insertIndividualPersonData(newContact);
//                        db.insertData(newContact);//Insert new record in Milk table
//                            //Log.d("dbsvm","Inside InsertIndividualPersonDbData , Unable to" +
//                                    "insert record\nError : "+e.getMessage().toString());
//                            db.createMilkTable(db1);
//                            db.insertData(newContact);
                        //update defualt data table
                        ga.alertDialogBox(InsertIndividualPersonDbData.this, "Date : " + newContact.getDATE() + "  record inserted", "Record Inserted successfully");

                        //Handle money , only which you will get. not that
                        // You will give, all sms facility is given to YOU WILL GET
                        //MONEY interface
//
//                        if(Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE)) {
//                            {
//                                notifyPersonWhoTookMoney();//Send automatically sms to notify person who took money
//                            }
//                        }

//                        db.updateDefaultData(newContact, 0);//do not update milk_rate, it will be updated for textBox in TableTotalView

                    }
                    else
                    {
                        //Log.d("dbsvm","Record with entered date exists");

                        ga.alertDialogBox(InsertIndividualPersonDbData.this, "Can not insert record for "+"Date : " + newContact.getDATE() + "\nPlease update the existing record in UPDATE section", "Record Already Exists\n");

                    }
                } else {
                    if(flagCheckMonthYearConfiguration==0&&flagCreatedContact==1)//Contact created =1,
//                        but not with with valid configuration date
                    {
                        ga.alertDialogBox(InsertIndividualPersonDbData.this,"Please create record with date only for " +
                                "' "+validMonthNum+"/"+validYearNum+" '  ( "+Params.CURRENT_GROUP_CONFIG_DB_NAME+" ) configuration. You can not insert " +
                                "for different configuration.","Unable To Insert Data");

                    }
                    else {
                        ga.alertDialogBox(InsertIndividualPersonDbData.this, "Please create record with " + "CREATE RECORD->" + " Button before inserting", "Create record before saving");
                    }

//                    ga.alertDialogBox(InsertIndividualPersonDbData.this, "Unable to update data for " + "Date : ", "Unable to update");
                }
//                if(flagCreatedContact==1) {
//                    flagCheckMonthYearConfiguration = 0;//handled while selecting date
                //from DatePicker
//                }

            }
        });


        tableViewData = (Button) findViewById(R.id.viewDataDb);
        tableViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
//                TextView dateTextView=findViewById(R.id.dateTextView);
//                dateTextView.setText(etbDate.getText().toString());
                startActivity(new Intent(InsertIndividualPersonDbData.this, TableTotalViewIndividualPersonData.class));
            }
        });


    }

    //Automatic sms services whenever the data is inserted in
    //YOU_WILL_GET_MONEY interface

//    public void notifyPersonWhoTookMoney()
//    {
//        ActivityCompat.requestPermissions(InsertIndividualPersonDbData.this,new String[] {Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
//
//        SharedPreferences yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
//
//        String yourName=yourDetailsForMsgSharedPref.getString("yourName","");
//        String yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");
//
//        String phoneNumber="",Smsmessage="";
//        Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;//Change database name to access all person names
//        MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(InsertIndividualPersonDbData.this);
//        try {
//
//            phoneNumber = db1.getRequiredCustomerGroupDatabasePhoneNumber(Params.CURRENT_GROUP_DB_NAME);
//            Log.d("dbsvm","person Phone Number "+ phoneNumber);
//        }
//        catch (Exception e)
//        {
//            Log.d("dbsvm","Error in fetching phoneNumber from database \nError: "+e.getMessage().toString());
//        }
//
//        Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;
//
//        String yourNameInfoForSms="",yourPhoneNumInfoForSms="",yourInfoForSms="";
//        if(yourPhoneNum.length()!=0)
//        {
//            yourPhoneNumInfoForSms=" from ( "+yourPhoneNum+" )";
//        }
//        yourInfoForSms= yourPhoneNumInfoForSms+"\n" + yourName;
//        Smsmessage="Dear Sir/Madam,\n" +
//                "Your took "+"RS "+givenAmountToNotifyWithSms+" in  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//        yourInfoForSms;
////        Smsmessage="Hello, how are your?";
////        Log.d("dbsvm","Message : "+Smsmessage);
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, Smsmessage, null, null);
//
//            Log.d("dbsvm","giver phone "+yourPhoneNum+"\ntaker phone "+phoneNumber);
//            try {
//                String takerPhoneNumInfo="";
//                if(phoneNumber.length()!=0)
//                {
//                    takerPhoneNumInfo="( "+phoneNumber+" )";
//                }
//                String giverMsg="Dear Sir/Madam,\n" +
//                        "You gave "+"RS "+givenAmountToNotifyWithSms+" in  "+ Params.CURRENT_GROUP_CONFIG_DB_NAME+
//                        " to " +takerPhoneNumInfo+"\n "+Params.CURRENT_GROUP_DB_NAME;
//                smsManager.sendTextMessage(yourPhoneNum, null, giverMsg, null, null);
//
//            }
//            catch (Exception e)
//            {
//                Log.d("dbsvm","Unable to send message to giver");
//            }
//
//            Toast.makeText(InsertIndividualPersonDbData.this, "SMS sent to "+Params.CURRENT_GROUP_DB_NAME+" Successfully.",
//                    Toast.LENGTH_SHORT).show();
//
//
//        }
//        catch (Exception e)
//        {
////            String msg = "Add "+Params.CURRENT_GROUP_DB_NAME+"'s Phone No. to send SMS." +
////                    "Click yes to continue";
//////                final String[] action = {""};
////            Context context = InsertIndividualPersonDbData.this;
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
////                            startActivity(new Intent(InsertIndividualPersonDbData.this, SendReminderIndividualPersonGetMoney.class));
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
//            Toast.makeText(InsertIndividualPersonDbData.this, "Person Phone No. Not Found.",
//                    Toast.LENGTH_SHORT).show();
//            Log.d("dbsvm","Unable to send sms automatically\nError: "+e.getMessage());
//        }
//
//    }




//    @SuppressLint("SetTextI18n")
//    public void setDefaultValuesIntoTextBoxes()
//    {
//        EditText etC1 = findViewById(R.id.editTextNumberDecimal1);
//        EditText etC2 = findViewById(R.id.editTextNumberDecimal2);//data
//        EditText etC3 = findViewById(R.id.editTextNumberDecimal3);//status paid
//        EditText etC4 = findViewById(R.id.editTextNumberDecimal4);//additional note
//
//        MyDbHandlerIndividualPersonConfigs db=new MyDbHandlerIndividualPersonConfigs(InsertIndividualPersonDbData.this);
//        SQLiteDatabase db1=db.getWritableDatabase();//pass this to create table
////        Contact defaultData=new Contact();
////         Contact defaultData = db.getDefaultDataForTextBoxes();
//        Contact defaultData=db.getDefaultDataForTextBoxes();
//        //Log.d("dbsvm","default value data fetched successfully");
//        //Log.d("dbsvm","default value data fetched successfully");
//
////        Contact.milk_rate = Contact.getMilk_rate();//updating static value shown in TableTotalView
//        etC1.setText(defaultData.getC1() + "");
//        etC2.setText(defaultData.getC2() + "");
//        etC3.setText(defaultData.getC3() + "");
//        etC4.setText(defaultData.getC4() + "");
//
//
//
//    }
//    @SuppressLint("SetTextI18n")
//    public void setNewCustomerNamesIntoTextViews(CustomerNames CustomerNamesSet) {
//
//        GetAlerts ga = new GetAlerts();
//
//        //Get typed data from textboxes into string and insert into db table
//
////           all below initialized as class variable,to use throughout whole class, without initializing again
//
//        TextView etC1 = findViewById(R.id.textViewName1);
//        TextView etC2 = findViewById(R.id.textViewName2);
//        TextView etC3 = findViewById(R.id.textViewName3);
//        TextView etC4 = findViewById(R.id.textViewName4);
//
//        //Set new names into text boxes
//        etC1.setText("Data : ");
//        etC2.setText("Status : ");
//        etC3.setText("Add Note : ");
//        etC4.setText(CustomerNamesSet.getNAME4()+" : ");
//
//
//    }
//
//

    public IndividualPersonData createContactBeforeInsert() {

        GetAlerts ga=new GetAlerts();
        IndividualPersonData newContact = new IndividualPersonData();

        //Get typed data from textboxes into string and insert into db table
        try {
//           all below initialized as class variable,to use throughout whole class, without initializing again

            @SuppressLint("WrongViewCast") EditText etDATE=findViewById(R.id.editTextDateInInsert);
            EditText etC1 = findViewById(R.id.editTextNumberDecimal1);
//            EditText etC2 = findViewById(R.id.editTextNumberDecimal2);
            Spinner etC2=findViewById(R.id.spinner);
            EditText etC3 = findViewById(R.id.editTextNumberDecimal3);


            String sdate = etDATE.getText().toString();// Still to Remove leading zeros from input date
            String sdateEdited=sdate;
            char[] sdateCharArray=sdate.toCharArray();
            int flagWrongDateInput=0;//initializing date is in proper format
//            05/07/2021
            if((sdateCharArray[0]=='/' || (sdateCharArray[0]=='0' && sdateCharArray[1]=='/') || (sdateCharArray[3]=='0' && sdateCharArray[4]=='/') || sdateCharArray[3]=='/'))
            {
                flagWrongDateInput=1;

            }

            String sc1 = etC1.getText().toString();
//            String sc2 = etC2.getText().toString();
            String sc2=etC2.getSelectedItem().toString();
            String sc3 = etC3.getText().toString();

            //Create Contact object with user input to insert into db

            //Considering 01/05/2021 and 1/05/2021 and 01/5/2021
//            int flagMonthYearConfiguration=0;
//            if(Params.CURRENT_GROUP_CONFIG_DB_NAME.equals(dateChoosenToInsert))
//            {
//                flagMonthYearConfiguration=1;
//            }


//            flagCreatedContact==0 to check whether already a record is not created
//           so that user creates a new record everytime
//          And  flagCheckMonthYearConfiguration==1 to check  month year configuration
            //matches to data accessed, for ex aug/2021 user can not create record for sep/2021
            //or aug/2022

            if(flagCreatedContact==0 &&flagCheckMonthYearConfiguration==1){
                StringBuilder sb1 = new StringBuilder(sdateEdited);

//                As invalid date is not entered so
//                Now edit date to format without leading zeros 1/1/2021
//                textViewDate.setText(sb.length()+"");

                //If date and month both have leading zeros
                int flagBothHaveLeadingzeros=0;//Initially taking false, If dd and mm both have leading zeros
                StringBuilder sb2=new StringBuilder();//second sb used because first sequence is changed and below unwanted conditions are implemented
                if((sdateCharArray[0]=='0' && sdateCharArray[1]!='/')&&(sdateCharArray[3]=='0' && sdateCharArray[4]!='/'))
                {
                    flagBothHaveLeadingzeros=1;
                    sb1.deleteCharAt(3);//to maintain same order of index where found
                    sdateEdited=sb1.toString();
                    sb2=new StringBuilder(sdateEdited);
                    sb2.deleteCharAt(0);//deleting 0 index first will decrease next elements index
//                    textViewDate.setText(sb2.charAt(2)+" "+sb.charAt(3)+"2");
                }

                //01/02/2021  -> format and save as 1/2/2021 to create new record
                //If date have leading zero
                if((sdateCharArray[0]=='0' && sdateCharArray[1]!='/'))
                {
//                    sdateEdited=sdateCharArray[1]+sdateCharArray[2]+"";
                    sb1.deleteCharAt(0);
//                    textViewDate.setText(sb.length()+"");
                    //comparing third position with 2 index as one element is removed ,so index after
                    //that element is decreased by one
//                    textViewDate.setText(sb.charAt(2)+" "+sb.charAt(3)+" "+"0");

                }

                //1/02/2021
                //If date is single digit and month have leading zero
                if((sdateCharArray[2]=='0' && sdateCharArray[3]!='/'))
                {

//                    textViewDate.setText(sb.charAt(2)+" "+"2");
                    sb1.deleteCharAt(2);

                }
                //12/02/2021
                //If date is double digit and month has leading zero
                if((sdateCharArray[3]=='0' && sdateCharArray[4]!='/') )
                {

//                    textViewDate.setText(sb.charAt(3)+"3 "+sb.length());

                    sb1.deleteCharAt(3);
                }


                if(flagBothHaveLeadingzeros==1)//dd and mm both have leading zeros
                    sdate=sb2.toString();
                else
                    sdate=sb1.toString();//Only one leading zero
//                newContact.setDATE(sdateEdited);//Using formatted date in form of 1/1/2021,without leading zeros
                newContact.setDATE(sdate);
                newContact.setPERSON_DATA(Double.parseDouble(sc1));
                newContact.setSTATUS(sc2);
                newContact.setADD_NOTE(sc3);

                //below used in above notifyPersonWhoTookMoney method
                givenAmountToNotifyWithSms=sc1;//Given amount send in msg to person (taker)

                //New record created successfully
                flagCreatedContact = 1;
                ga.alertDialogBox(InsertIndividualPersonDbData.this, "New record created successfully for Date: " + newContact.getDATE() + "\n" +
                        "Don't forget to Press INSERT button to save record into database", "Record created successfully");

            }
            else if(flagCheckMonthYearConfiguration==0)
            {
                ga.alertDialogBox(InsertIndividualPersonDbData.this,"Please create record by selecting date only for \n" +
                        "' "+validMonthNum+"/"+validYearNum+" '  ( "+Params.CURRENT_GROUP_CONFIG_DB_NAME+" ) configuration. You can not create " +
                        "for different configuration.","Unable To Create Contact");
//                ga.alertDialogBox(InsertIndividualPersonDbData.this," Unable to create new record Please enter valid Date of form 'dd/mm/yyyy'","Invalid date entered");
            }


        } catch (Exception e) {
            Log.d("dbsvm",e.getMessage().toString());
            ga.alertDialogBox(InsertIndividualPersonDbData.this,"Unable to create " +
                    "record\n" +"Please fill all the fields\n"+
                    "Error : "+e.getMessage().toString(),"Warning empty field present");

        }
        dateChoosenToInsert="";
        return newContact;
    }

    public void checkDefaultDateConfigurationWithRequiredsMonthYearConfig()
    {
        sdf = new SimpleDateFormat("MMMM/yyyy");
        tempDateChoosenCalendar=Calendar.getInstance();
//        tempDateChoosenCalendar.set();
        String tempdateString = sdf.format(tempDateChoosenCalendar.getTime());

        dateChoosenToInsert=tempdateString.replace("/"," ");

        //Log.d("dbsvm",tempdateString);

//
        if(dateChoosenToInsert.equals(Params.CURRENT_GROUP_CONFIG_DB_NAME))
        {
            flagCheckMonthYearConfiguration=1;
        }

    }
}
