


package com.datahiveshield.mydata.mydairy.khata.files;

        import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDBHandlerIndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;

public class CreateNewIndividualPersonDataGroupName extends Activity {
    String selectedGroupName = "";
    public static String[] str;
    public int i;
    public int sizeOfReceivedArrayList = 0;
    public String noGroupFound;
    ArrayList<String> customerDatabasesArrayList = new ArrayList<>();

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private CreateNewIndividualPersonDataGroupName mContext;
    private static boolean flagCreateIndividualPersonGroup=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_individual_person_group);

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

        Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;
//        String storage_folder = "/."+Params.EmailId+"/"+Params.DB_NAME;
//        String storage_folder = "/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.DB_NAME;
//
//        mContext=CreateNewIndividualPersonDataGroupName.this;
//        String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
////        Log.d("dbsvm","Inside create person "+storage_folder);
//        File f = new File(destPath, storage_folder);
//        if (!f.exists()) {
//            f.mkdirs();
//        }

        findViewById(R.id.editTextNewGroupName).requestFocus();

        MyDBHandlerIndividualPersonData db = new MyDBHandlerIndividualPersonData(CreateNewIndividualPersonDataGroupName.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table
        showAllCustomerGroups();

        //Set back button to go to previous activity
        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        EditText enteredGroupPhoneNum = findViewById(R.id.editTextCreateGroupPhoneNum);
        TextView textViewPhoneNum=findViewById(R.id.textViewPhoneNum);
        if(!Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE))
        {
            enteredGroupPhoneNum.setVisibility(View.GONE);
            textViewPhoneNum.setVisibility(View.GONE);
            flagCreateIndividualPersonGroup=true;
        }
        Button CreateNewGroup = (Button) findViewById(R.id.createNewGroup);
        CreateNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                Params.DB_NAME=Params.Curent_Running_Db;
                GetAlerts ga = new GetAlerts();
                EditText enteredGroupName = findViewById(R.id.editTextNewGroupName);
//                EditText enteredGroupPhoneNum = findViewById(R.id.editTextCreateGroupPhoneNum);
                String groupDatabaseName = enteredGroupName.getText().toString().trim();
                String groupDatabasePhoneNum = enteredGroupPhoneNum.getText().toString().trim();
                if(!flagCreateIndividualPersonGroup && (groupDatabasePhoneNum.length()>12 || groupDatabasePhoneNum.length()<10 || groupDatabasePhoneNum.length()==11))
                {
                    ga.alertDialogBox(CreateNewIndividualPersonDataGroupName.this,
                            groupDatabasePhoneNum.length()+" digit number entered.\n" +
                                    "Please enter the correct 10 digit phone number.",
                            "Incorrect phone number");

                }
                else {
                    if (groupDatabaseName.length() > 0) {
                        try {
                            db.createCustomerGroupDatabaseStoringTable(db1);//Database table to store new person name ,create if not exits
                            //As below we are checking whether the entered name exists in it or not
                            //Else if not present , it will give table not found error

                            if (db.isCustomerGroupDatabaseExist(groupDatabaseName) == 0) {//No database exists with entered database name
                                //Create all required table here only for ease and use later
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                db.createCustomerGroupDatabaseStoringTable(db1);//Create table if not exists
                                    db.insertCustomerGroupDatabaseNameIntoTable(groupDatabaseName, groupDatabasePhoneNum);
                                    //Log.d("dbsvm", "New customer person database name inserted successfully into database table");
                                    //let user do operations on the created databases groups

                                }
                                Params.toAddBeforeNewTableName = groupDatabaseName;
                                Params.DB_NAME = Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;
                                Params.Curent_Running_Db = groupDatabaseName;
//                                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.toAddBeforeNewTableName+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;//Stores configuration uniquely for all groups
                                //based on month and year
//                                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;//Stores configuration uniquely for all groups

//                                All other tables depend on month year configuration, so will be created with configuration table only
                                //available in CreateConfigureGropDataWithMonthYear
//                                Params.DATA_TABLE_NAME=Params.toAddBeforeNewTableName++Params.DEFAULT_DATA_TABLE_NAME;
//                                Params.CUSTOMER_NAMES_TABLE=Params.toAddBeforeNewTableName+"_"+Params.DEFAULT_CUSTOMER_NAMES_TABLE;
//                                db.createValueStoringTable(db1);
//                                db.createDefaultDataTableForTextBoxes(db1);
//                                db.createDefaultCustomerNames(db1);
                                db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
//                                db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
//                                CustomerGroupDatabases newCustomerGroup = new CustomerGroupDatabases();
//                                newCustomerGroup.setCustomerGroupNameDatabase(groupDatabaseName);
                                Params.DB_NAME = Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;

                                showAllCustomerGroups();

                                //Log.d("dbsvm", groupDatabaseName + " database created successfully");
                                ga.alertDialogBox(CreateNewIndividualPersonDataGroupName.this, groupDatabaseName + " person created successfully", "New person Created");
                            } else {
                                ga.alertDialogBox(CreateNewIndividualPersonDataGroupName.this, "Unable to create person." +
                                        "\nperson with name " + groupDatabaseName + " already exists", "person Already Exists");
                            }
                        } catch (Exception e) {
                            Log.d("dbsvm", "Unable to create new database\n" +
                                    "Error : " + e.getMessage().toString());
                        }

                    } else {

                        ga.alertDialogBox(CreateNewIndividualPersonDataGroupName.this, "Please enter a valid person name", "Empty person Name");
                    }
                }

//                startActivity(new Intent(CreateNewIndividualPersonDataGroupName.this, DeleteDbData.class));
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
        Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;
        MyDBHandlerIndividualPersonData db = new MyDBHandlerIndividualPersonData(CreateNewIndividualPersonDataGroupName.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToShowExistingGroups);


        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                customerDatabasesArrayList = db.getAllSavedCustomerGroupDatabaseNames();
            }
            sizeOfReceivedArrayList = customerDatabasesArrayList.size();
            i = 0;
            str = new String[sizeOfReceivedArrayList];
            for (String cDatabaseName : customerDatabasesArrayList) {
                str[i] = cDatabaseName;
                i++;
            }
        }
        catch (Exception e)
        {
            Log.d("dbsvm","unable to fetch database name data\n" +
                    "Error : "+e.getMessage().toString());
        }

        try {
            if(str.length>0) {
                numberPicker.setDisplayedValues(null);
                numberPicker.setMaxValue(str.length - 1);
                numberPicker.setMinValue(0);
                numberPicker.setWrapSelectorWheel(true);
                numberPicker.setDisplayedValues(str);
                TextView atShowSelectedGroup = findViewById(R.id.textViewExitingGroups);
                atShowSelectedGroup.setText("Existing Persons ");

            }
            else
            {
//                numberPicker.setDisplayedValues("");
                TextView atShowSelectedGroup=findViewById(R.id.textViewExitingGroups);
                atShowSelectedGroup.setText( "No person found");
            }
//            setNumberPicker(yourNumberPicker,mValues);
        } catch (Exception e) {
            Log.d("dbsvm", "Inside CreateNewIndividualPersonDataGroupName.java String picker error \n" +
                    "Error : " + e.getMessage().toString());
        }

    }
}


