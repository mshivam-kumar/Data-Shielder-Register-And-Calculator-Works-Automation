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
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InsertDbData extends AppCompatActivity {
    TextView textViewDate;
//    EditText etbDate;
    Button tableViewData;
    Contact newContact;
    int flagCreatedContact=0;
    int flagCheckMonthYearConfiguration=0;
    int validMonthNum=0;
    int validYearNum=0;


    EditText etDATE;
    String dateChoosenToInsert="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar tempDateChoosenCalendar;
    SimpleDateFormat sdf;

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();


    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_db_data);


        TextView dataAccessed=findViewById(R.id.infoHeading);
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );

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



        MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(InsertDbData.this);
        //Initialize defualtValues of assoiciated Customer group with names into textboxes
        Contact init_record=db.getDefaultDataForTextBoxes();
        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();


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
                        InsertDbData.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
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
                    ga.alertDialogBox(InsertDbData.this,"Please choose date only for " +
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
                setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show
                //Log.d("dbsvm","Successfully fetched default customer names in InsertDbData");

                setDefaultValuesIntoTextBoxes();//set default values in text boxes in Insert activity
                //Log.d("dbsvm","Successfully fetched default values in InsertDbData");

            }
            catch (Exception e)
            {
                Log.d("dbsvm","Unable to set default fetched customer names in InsertDbData.java " +
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

                MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(InsertDbData.this);
                if (flagCreatedContact == 1 &&flagCheckMonthYearConfiguration==1) {
                    //call MyDbHandlerGroupConfigs method to insert record
//                    db.updateDefaultData(newContact,1);//update default data other than date
//                    db.insertData(createContactBeforeInsert());
                    if(db.isDateExist(newContact.getDATE())==0)//If date not already exists then insert new record
                    {
                        SQLiteDatabase db1=db.getWritableDatabase();//pass this to create table
                            db.insertData(newContact);//Insert new record in Milk table
//                            //Log.d("dbsvm","Inside InsertDbData , Unable to" +
//                                    "insert record\nError : "+e.getMessage().toString());
//                            db.createMilkTable(db1);
//                            db.insertData(newContact);
                        //update defualt data table
                        ga.alertDialogBox(InsertDbData.this, "Date : " + newContact.getDATE() + "  record inserted", "Record Inserted successfully");
                            db.updateDefaultData(newContact, 0);//do not update milk_rate, it will be updated for textBox in TableTotalView

                    }
                    else
                    {
                        //Log.d("dbsvm","Record with entered date exists");

                        ga.alertDialogBox(InsertDbData.this, "Can not insert record for "+"Date : " + newContact.getDATE() + "\nRecord already exists", "Record Already Exists");

                    }
                } else {
                    if(flagCheckMonthYearConfiguration==0&&flagCreatedContact==1)//Contact created =1,
//                        but not with with valid configuration date
                    {
                        ga.alertDialogBox(InsertDbData.this,"Please create record with date only for " +
                                "' "+validMonthNum+"/"+validYearNum+" '  ( "+Params.CURRENT_GROUP_CONFIG_DB_NAME+" ) configuration. You can not insert " +
                                "for different configuration.","Unable To Insert Data");

                    }
                    else {
                        ga.alertDialogBox(InsertDbData.this, "Please create record with " + "CREATE RECORD->" + " Button before inserting", "Create record before saving");
                    }

//                    ga.alertDialogBox(InsertDbData.this, "Unable to update data for " + "Date : ", "Unable to update");
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
                startActivity(new Intent(InsertDbData.this, TableTotalView.class));
            }
        });


    }

    @SuppressLint("SetTextI18n")
    public void setDefaultValuesIntoTextBoxes()
    {
        EditText etC1 = findViewById(R.id.editTextNumberDecimal1);
        EditText etC2 = findViewById(R.id.editTextNumberDecimal2);
        EditText etC3 = findViewById(R.id.editTextNumberDecimal3);
        EditText etC4 = findViewById(R.id.editTextNumberDecimal4);
        EditText etC5 = findViewById(R.id.editTextNumberDecimal5);
        EditText etC6 = findViewById(R.id.editTextNumberDecimal6);
        EditText etC7 = findViewById(R.id.editTextNumberDecimal7);
        EditText etC8 = findViewById(R.id.editTextNumberDecimal8);
        EditText etC9 = findViewById(R.id.editTextNumberDecimal9);
        EditText etC10 = findViewById(R.id.editTextNumberDecimal10);
        EditText etC11 = findViewById(R.id.editTextNumberDecimal11);
        EditText etC12 = findViewById(R.id.editTextNumberDecimal12);
        EditText etC13 = findViewById(R.id.editTextNumberDecimal13);
        EditText etC14 = findViewById(R.id.editTextNumberDecimal14);
        EditText etC15 = findViewById(R.id.editTextNumberDecimal15);

        MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(InsertDbData.this);
        SQLiteDatabase db1=db.getWritableDatabase();//pass this to create table
//        Contact defaultData=new Contact();
//         Contact defaultData = db.getDefaultDataForTextBoxes();
        Contact defaultData=db.getDefaultDataForTextBoxes();
        //Log.d("dbsvm","default value data fetched successfully");
        //Log.d("dbsvm","default value data fetched successfully");

//        Contact.milk_rate = Contact.getMilk_rate();//updating static value shown in TableTotalView
        etC1.setText(defaultData.getC1() + "");
        etC2.setText(defaultData.getC2() + "");
        etC3.setText(defaultData.getC3() + "");
        etC4.setText(defaultData.getC4() + "");
        etC5.setText(defaultData.getC5() + "");
        etC6.setText(defaultData.getC6() + "");
        etC7.setText(defaultData.getC7() + "");
        etC8.setText(defaultData.getC8() + "");
        etC9.setText(defaultData.getC9() + "");
        etC10.setText(defaultData.getC10() + "");
        etC11.setText(defaultData.getC11() + "");
        etC12.setText(defaultData.getC12() + "");
        etC13.setText(defaultData.getC13() + "");
        etC14.setText(defaultData.getC14() + "");
        etC15.setText(defaultData.getC15() + "");



    }
    @SuppressLint("SetTextI18n")
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
        etC1.setText(CustomerNamesSet.getNAME1()+" : ");
        etC2.setText(CustomerNamesSet.getNAME2()+" : ");
        etC3.setText(CustomerNamesSet.getNAME3()+" : ");
        etC4.setText(CustomerNamesSet.getNAME4()+" : ");
        etC5.setText(CustomerNamesSet.getNAME5()+" : ");
        etC6.setText(CustomerNamesSet.getNAME6()+" : ");
        etC7.setText(CustomerNamesSet.getNAME7()+" : ");
        etC8.setText(CustomerNamesSet.getNAME8()+" : ");
        etC9.setText(CustomerNamesSet.getNAME9()+" : ");
        etC10.setText(CustomerNamesSet.getNAME10()+" : ");
        etC11.setText(CustomerNamesSet.getNAME11()+" : ");
        etC12.setText(CustomerNamesSet.getNAME12()+" : ");
        etC13.setText(CustomerNamesSet.getNAME13()+" : ");
        etC14.setText(CustomerNamesSet.getNAME14()+" : ");
        etC15.setText(CustomerNamesSet.getNAME15()+" : ");


    }



    public Contact createContactBeforeInsert() {

        GetAlerts ga=new GetAlerts();
        Contact newContact = new Contact();

        //Get typed data from textboxes into string and insert into db table
        try {
//           all below initialized as class variable,to use throughout whole class, without initializing again

            @SuppressLint("WrongViewCast") EditText etDATE=findViewById(R.id.editTextDateInInsert);
            EditText etC1 = findViewById(R.id.editTextNumberDecimal1);
            EditText etC2 = findViewById(R.id.editTextNumberDecimal2);
            EditText etC3 = findViewById(R.id.editTextNumberDecimal3);
            EditText etC4 = findViewById(R.id.editTextNumberDecimal4);
            EditText etC5 = findViewById(R.id.editTextNumberDecimal5);
            EditText etC6 = findViewById(R.id.editTextNumberDecimal6);
            EditText etC7 = findViewById(R.id.editTextNumberDecimal7);
            EditText etC8 = findViewById(R.id.editTextNumberDecimal8);
            EditText etC9 = findViewById(R.id.editTextNumberDecimal9);
            EditText etC10 = findViewById(R.id.editTextNumberDecimal10);
            EditText etC11 = findViewById(R.id.editTextNumberDecimal11);
            EditText etC12 = findViewById(R.id.editTextNumberDecimal12);
            EditText etC13 = findViewById(R.id.editTextNumberDecimal13);
            EditText etC14 = findViewById(R.id.editTextNumberDecimal14);
            EditText etC15 = findViewById(R.id.editTextNumberDecimal15);


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
            String sc2 = etC2.getText().toString();
            String sc3 = etC3.getText().toString();
            String sc4 = etC4.getText().toString();
            String sc5 = etC5.getText().toString();
            String sc6 = etC6.getText().toString();
            String sc7 = etC7.getText().toString();
            String sc8 = etC8.getText().toString();
            String sc9 = etC9.getText().toString();
            String sc10 = etC10.getText().toString();
            String sc11 = etC11.getText().toString();
            String sc12 = etC12.getText().toString();
            String sc13 = etC13.getText().toString();
            String sc14 = etC14.getText().toString();
            String sc15 = etC15.getText().toString();

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
                newContact.setC1(Double.parseDouble(sc1));
                newContact.setC2(Double.parseDouble(sc2));
                newContact.setC3(Double.parseDouble(sc3));
                newContact.setC4(Double.parseDouble(sc4));
                newContact.setC5(Double.parseDouble(sc5));
                newContact.setC6(Double.parseDouble(sc6));
                newContact.setC7(Double.parseDouble(sc7));
                newContact.setC8(Double.parseDouble(sc8));
                newContact.setC9(Double.parseDouble(sc9));
                newContact.setC10(Double.parseDouble(sc10));
                newContact.setC11(Double.parseDouble(sc11));
                newContact.setC12(Double.parseDouble(sc12));
                newContact.setC13(Double.parseDouble(sc13));
                newContact.setC14(Double.parseDouble(sc14));
                newContact.setC15(Double.parseDouble(sc15));

                //New record created successfully
                flagCreatedContact = 1;
                ga.alertDialogBox(InsertDbData.this, "New record created successfully for Date: " + newContact.getDATE() + "\n" +
                        "Don't forget to Press INSERT button to save record into database", "Record created successfully");

            }
            else if(flagCheckMonthYearConfiguration==0)
            {
                ga.alertDialogBox(InsertDbData.this,"Please create record by selecting date only for \n" +
                        "' "+validMonthNum+"/"+validYearNum+" '  ( "+Params.CURRENT_GROUP_CONFIG_DB_NAME+" ) configuration. You can not create " +
                        "for different configuration.","Unable To Create Contact");
//                ga.alertDialogBox(InsertDbData.this," Unable to create new record Please enter valid Date of form 'dd/mm/yyyy'","Invalid date entered");
            }


        } catch (Exception e) {
            ga.alertDialogBox(InsertDbData.this,"Unable to create " +
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
