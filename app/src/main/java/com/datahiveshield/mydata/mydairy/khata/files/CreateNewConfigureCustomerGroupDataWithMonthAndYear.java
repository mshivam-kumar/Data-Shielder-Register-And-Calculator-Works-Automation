package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandler;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateNewConfigureCustomerGroupDataWithMonthAndYear extends Activity {
    private DatePickerDialog.OnDateSetListener listener;
    private static final int MAX_YEAR = 2099;

    String selectedGroupName = "";
    public static String[] str;
    public int i;
    public int sizeOfReceivedArrayList = 0;
    public String noGroupFound;
    EditText mDisplayDate;
    ArrayList<String> customerDatabasesArrayList = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public String monthString,localYear;

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_configure_customer_data_with_date_and_year);

        TextView appName=findViewById(R.id.appName);
        try
        {
            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }


//        String storage_folder = "/.DataShielderApp/db";
        String storage_folder = "/."+Params.EmailId+"/"+Params.DB_NAME;


        mContext=CreateNewConfigureCustomerGroupDataWithMonthAndYear.this;
//        String destPath = CreateNewConfigureCustomerGroupDataWithMonthAndYear.this.getExternalFilesDir(null).getAbsolutePath();
        String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
    File f = new File(destPath, storage_folder);
        if (!f.exists()) {
            f.mkdirs();
        }

        MyDbHandler db = new MyDbHandler(CreateNewConfigureCustomerGroupDataWithMonthAndYear.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table

//        Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
//        Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;//Stores configuration uniquely for all groups
        db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
        showAllCustomerGroups();

        mDisplayDate = findViewById(R.id.editTextNewNameGroup);
        mDisplayDate.setFocusable(false);//Make Date textBox clickable
        mDisplayDate.setClickable(true);
//        mDisplayDate = (Button) findViewById(R.id.selectDate);

        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

                Calendar calendar = Calendar.getInstance();
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerDialog datePickerDialog=new DatePickerDialog();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateNewConfigureCustomerGroupDataWithMonthAndYear.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ((ViewGroup) dialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                dialog.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month;
                //Log.d("sk", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

//                String mwith0=month+"";
//                if(month<10)//leading 0 with months less than 10th
//                {
//                    mwith0=String.format("%02d", month);

//                }
//                String date =  day + "/"+mwith0 + "/" +  year;
//                String date=day+"/"+month+"/"+year;//Not leading zeros with date and month
//                Calendar calendar = Calendar.getInstance();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
                calendar.set(year, month, day);
                String dateString = sdf.format(calendar.getTime());
                mDisplayDate.setText(dateString);
//                String date=monthString+"/"+localYear;
//                mDisplayDate.setText(date);
//                Contact contact =new Contact();
//                Contact.selectedDate=date;//selected date is not object dependent, so value updated forever

//                contact.setSelectedDate(date);//update variable to date selected by user in Contact.java
//                showSelecteDate.setText("Selected Date : "+date);


            }
        };




        Button CreateNewGroup = (Button) findViewById(R.id.createNewMonthYearGroup);
        CreateNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

//                Params.DB_NAME=Params.Curent_Running_Db;
                GetAlerts ga = new GetAlerts();
                EditText enteredGroupName = findViewById(R.id.editTextNewNameGroup);
                String configTableDataName = enteredGroupName.getText().toString();
                if (configTableDataName.length() > 0) {
                    try {
//                        db.createCustomerGroupDatabaseStoringTable(db1);//Database table to store new group name ,create if not exits
                        //As below we are checking whether the entered name exists in it or not
                        //Else if not present , it will give table not found error

                        configTableDataName=configTableDataName.replace('/','_');
                        if(db.isCustomerGroupConfigDatabaseExist(configTableDataName)==0) {

//                        Params.toAddBeforeNewCustomerDataconfigurationWithMonthAndYear=configTableName;
//                        configTableName=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.toAddBeforeNewCustomerDataconfigurationWithMonthAndYear+"_"+Params.TABLE_NAME;
//                        Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
//                        Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;

//                        db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
//                Params.TABLE_NAME=table_group_name_added_before+"_"+Params.DEFAULT_TABLE_NAME;
//                        Params.DATA_TABLE_NAME=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_DATA_TABLE_NAME;

                            //Get default customer names data storing table
//                        Params.CUSTOMER_NAMES_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_CUSTOMER_NAMES_TABLE;
//                        Params.DATA_VALUE_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_DATA_VALUE_TABLE;
//


//                        configTableName+=Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
//                        Params.DEFAULT_DATA_TABLE_NAME=Params.toAddBeforeNewTableName+"_"+Params.toAddBeforeNewCustomerDataconfigurationWithMonthAndYear+"_"+Params.DEFAULT_DATA_TABLE_NAME;
//                        Params.DEFAULT_CUSTOMER_NAMES_TABLE=Params.toAddBeforeNewTableName+"_"+Params.toAddBeforeNewCustomerDataconfigurationWithMonthAndYear+"_"+Params.DEFAULT_CUSTOMER_NAMES_TABLE;
//                        if (db.isCustomerGroupDatabaseExist(configTableName) == 0) {//No database exists with entered database name
//                            db.createValueStoringTable(db1);
//                            db.createDefaultDataTableForTextBoxes(db1);
//                            db.createDefaultCustomerNames(db1);
//                            db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
//                                CustomerGroupDatabases newCustomerGroup = new CustomerGroupDatabases();
//                                newCustomerGroup.setCustomerGroupNameDatabase(configTableName);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                String storage_folder = "/.DataShielderApp/db";
                                String storage_folder = "/."+Params.EmailId+"/" + Params.DB_NAME;

                                String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
    File f = new File(destPath, storage_folder);
                                if (!f.exists()) {
                                    f.mkdirs();
                                }
                                //Log.d("dbsvm", "\nnew config table inserted\n" +
//                                        configTableDataName);
                                db.insertConfigureCustomerGroupDataWithMonthAndYear(configTableDataName);


                                //Log.d("dbsvm", "New customer group database name inserted successfully into database table");
                                //let user do operations on the created databases groups

                        configTableDataName = configTableDataName.replace("_","");
                            //Log.d("dbsvm", configTableDataName + " database created successfully");
                            ga.alertDialogBox(CreateNewConfigureCustomerGroupDataWithMonthAndYear.this, configTableDataName + " group created successfully", "New Group Created");
                            showAllCustomerGroups();
                            }
                        }else {
                            ga.alertDialogBox(CreateNewConfigureCustomerGroupDataWithMonthAndYear.this, "Unable to create  group configuration .\nConfiguration with name '" + configTableDataName.replace("_"," ") +
                                    "' already exists.", "Configuration already exists");
                        }
                    } catch (Exception e) {
                        Log.d("dbsvm", "Unable to create new database\n" +
                                "Error : " + e.getMessage().toString());
                    }

                } else {

                    ga.alertDialogBox(CreateNewConfigureCustomerGroupDataWithMonthAndYear.this, "Please enter a valid group name", "Empty Group Name");
                }

//                startActivity(new Intent(CreateNewConfigureCustomerGroupDataWithMonthAndYear.this, DeleteDbData.class));
            }
        });
    }
    //Update home screen with changes, on press of back button
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if(keyCode == KeyEvent.KEYCODE_BACK)
//        {
//
//            Intent intent = new Intent(this, HOMEcustomerGroupListView.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Open new activity without loading white screen
//            startActivity(intent);
//            return true;
//        }
//        return false;
//    }
    public  void showAllCustomerGroups()
    {
//        Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;

        MyDbHandler db = new MyDbHandler(CreateNewConfigureCustomerGroupDataWithMonthAndYear.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToShowExistingGroups);


        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                customerDatabasesArrayList = db.getAllSavedConfigureCustomerGroupDataTableWithMonthAndYear();
            }
            sizeOfReceivedArrayList = customerDatabasesArrayList.size();
            i = 0;
            str = new String[sizeOfReceivedArrayList];
            for (String cDatabaseName : customerDatabasesArrayList) {
//                cDatabaseName=cDatabaseName.substring()
//                cDatabaseName=cDatabaseName.replace(Params.groupNameToAddBeforeDefault_group_TableNamesReceived,"");
//                str[i] = cDatabaseName;
                str[i] = cDatabaseName.replace("_","");
                i++;
            }
        }
        catch (Exception e)
        {
            Log.d("dbsvm","unable to fetch database name data\n" +
                    "Error : "+e.getMessage().toString());
        }

        try {
            if(sizeOfReceivedArrayList!=0) {
                numberPicker.setDisplayedValues(null);
                numberPicker.setMaxValue(str.length - 1);
                numberPicker.setMinValue(0);
                numberPicker.setWrapSelectorWheel(true);
                numberPicker.setDisplayedValues(str);
                TextView atShowSelectedGroup=findViewById(R.id.textViewExitingGroups);
                atShowSelectedGroup.setText( "Your Existing Data ");

            }
            else
            {
//                numberPicker.setDisplayedValues("");
                TextView atShowSelectedGroup=findViewById(R.id.textViewExitingGroups);
                atShowSelectedGroup.setText( "No data found");
            }
//            setNumberPicker(yourNumberPicker,mValues);
        } catch (Exception e) {
            Log.d("dbsvm", "Inside UpdateCustomerGroup.java String picker error \n" +
                    "Error : " + e.getMessage().toString());
        }

    }


}

