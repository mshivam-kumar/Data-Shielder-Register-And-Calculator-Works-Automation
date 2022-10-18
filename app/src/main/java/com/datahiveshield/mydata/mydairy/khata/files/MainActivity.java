
//App  Files saved by user will be stored in MyDairy folder inside internal storage
//Refer GeneradePdf.java for all information
package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.model.DefaultCustomerData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;

//import android.graphics.pdf.PdfDocument;

//import org.w3c.dom.Document;

//import com.itextpdf.kernel.pdf.PdfDocument;


public class MainActivity extends AppCompatActivity  {
    EditText editTextName;
//    Button recyclerViewButton;
    Button tableView;
//    Button testLayout;
   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    public CustomerNames defaultCustomerNamesFromPreviousMonth;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        try {
////            Thread.sleep(15000);
//            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/share.pdf";
//            //Log.d("dbsvm", "/nshare pdf file Path to delete : " + path);
//
////        File dir = new File(storage_folder);
//            File file = new File(path);
//            if(file.exists())
//            {
//            file.delete();
//            //Log.d("dbsvm", "/nshare pdf file deleted : " + path);
//
//            }
//        }
//        catch (Exception e)
//        {
//            //Log.d("dbsvm","Unable to delete share pdf file\n" +
//                    "Error : "+e.getMessage().toString());
//        }

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

            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            Log.d("dbsvm","unable to change font of app name in HOME");
        }



        //Log.d("dbsvm","inside  mainActivity.java");

//        TextView groupAccess=findViewById(R.id.groupAccessName);
////        groupAccess.setText("Group Accessed : "+ Params.DB_NAME);
//        groupAccess.setText("Group Accessed : "+ Params.CURRENT_GROUP_DB_NAME+"/"
//        +Params.CURRENT_GROUP_CONFIG_DB_NAME);

        TextView groupAccessed=findViewById(R.id.groupAccessed);
        groupAccessed.setText("Group Accessed : "+Params.CURRENT_GROUP_DB_NAME );
        TextView dataAccessed=findViewById(R.id.infoHeading);
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );


        //Static customer names reseted to provide uniqueness to all groups customer names
        //No two grops can access each others customer names
         CustomerNames.NAME1="Name1";CustomerNames.NAME2="Name2";
         CustomerNames.NAME3="Name3";
         CustomerNames.NAME4="Name4";CustomerNames.NAME5="Name5";
         CustomerNames.NAME6="Name6";
         CustomerNames.NAME7="Name7";CustomerNames.NAME8="Name8";
         CustomerNames.NAME9="Name9";
         CustomerNames.NAME10="Name10";CustomerNames.NAME11="Name11";
         CustomerNames.NAME12="Name12";CustomerNames.NAME13="Name13";
         CustomerNames.NAME14="Name14";CustomerNames.NAME15="Name15";
         Contact.QUANTITYUNITNAME="UNIT";
         Contact.Rate=0;
         Contact.selectedDate="";



//        Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;

//        Contact.rate=Double.parseDouble(findViewById(R.id.editTextMilkRate));
        TableTotalView ttV=new TableTotalView();//to update Contact.Rate from box, as this class is
        //linked with Rate textBox
        MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(MainActivity.this);
        SQLiteDatabase db1=db.getWritableDatabase();//pass this to create table

        try {//If Milk table not exists then create it as our
            //most operations are based on it so it needs to be created before using
            db.createDataValueStoringTable(db1);
//            db.clearDefaultValueDataTable(db1);
            db.createDefaultDataTableForTextBoxes(db1);
            db.createDefaultCustomerNames(db1);

            db.createDefaultCustomerNamesAndPhoneNumbbersTable(db1);//to send reminders ..newly added

//            db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
                //Log.d("dbsvm", "If tables does not exists then Milk, defaultValue and defaultCustomerNames table created from MainActivity");
//            try {
//                Contact.allContacts=db.getAllSavedRecords();
//                //Log.d("dbsvm","All Customer saved Records fetched successfully");
//            }
//            catch (Exception e)
//            {
//                //Log.d("dbsvm","Unable to fetch saved record of customers\n" +
//                        "Error : "+e.getMessage().toString());
//            }
            if(db.isDefaultValueDataRowExist()==0)//DefaultData Table does not contain a single row,so insert
                //one row due to which data can be updated further base on user newly data insertion
            {
                Contact contact=new Contact();
                db.insertDefaultValueData(contact);
                contact=db.getDefaultDataForTextBoxes();


                ArrayList<DefaultCustomerData> customerNameAndPhoneArrayList=new ArrayList<>();
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME1,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME2,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME3,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME4,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME5,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME6,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME7,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME8,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME9,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME10,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME11,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME12,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME13,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME14,""));
                customerNameAndPhoneArrayList.add(new DefaultCustomerData(CustomerNames.NAME15,""));

                db.insertDefaultCustomerNamesAndPhoneNumbbersTable(customerNameAndPhoneArrayList);

                //Log.d("dbsvm","Very first row inserted in DefaultData Table");
                //After
                //Log.d("dbsvm","DefaultValue data fetched successfully in MainActivity");

            }
            else //If data row is present in defaultValue table, then fetch data here only one time to use in all activities
            {

                Contact contact=db.getDefaultDataForTextBoxes();
                //Log.d("dbsvm","DefaultValue data fetched successfully in MainActivity");

            }
            if(db.isDefaultCustomerDataRowExist()==0)//DefaultCustomerNames data table does not contain a
            //single row to update
            {
                //Newly added
                //Import customer names from previous month, if exists
//                try {
//                    Params.DB_NAME=Params.CURRENT_GROUP_DB_NAME;
//                    MyDbHandler db2=new MyDbHandler(MainActivity.this);
//                    SQLiteDatabase db3=db.getWritableDatabase();
//                    db2.createDefaultCustomerNamesForNextMonth(db3);
//                    defaultCustomerNamesFromPreviousMonth=db2.getDefaultCustomerDataForNextMonthForTextBoxes();
//                    if(db2.isDefaultCustomerForNextMonthDataRowExist()==0)//table is empty
//                    {
//                        CustomerNames.NAME1="Name1";CustomerNames.NAME2="Name2";
//                        CustomerNames.NAME3="Name3";
//                        CustomerNames.NAME4="Name4";CustomerNames.NAME5="Name5";
//                        CustomerNames.NAME6="Name6";
//                        CustomerNames.NAME7="Name7";CustomerNames.NAME8="Name8";
//                        CustomerNames.NAME9="Name9";
//                        CustomerNames.NAME10="Name10";CustomerNames.NAME11="Name11";
//                        CustomerNames.NAME12="Name12";CustomerNames.NAME13="Name13";
//                        CustomerNames.NAME14="Name14";CustomerNames.NAME15="Name15";
//
//                        Log.d("dbsvm","empty names for next month");
//                        CustomerNames c=new CustomerNames();
//                        db2.insertDefaultCustomerDataForNextMonth(c);//Insert the row of initial
//                        //defalt customer names
//
//                    }
//                    else//Previous month table already exists
//                    {
//                        CustomerNames c=new CustomerNames();
//
//                        db2.insertDefaultCustomerDataForNextMonth(c);//Insert the row of initial
//                        Log.d("dbsvm","having row");
//                        defaultCustomerNamesFromPreviousMonth=db2.getDefaultCustomerDataForNextMonthForTextBoxes();
//                    }
//                }
//                catch (Exception e)
//                {
//                    Log.d("dbsvm","Unable to import customer names from previous month\nError :"+e.getMessage());
//                }


////                CustomerNames defaultCustNames=new CustomerNames();
                defaultCustomerNamesFromPreviousMonth=new CustomerNames();
//                db.insertDefaultCustomerData(defaultCustNames);
                Params.DB_NAME=Params.CURRENT_GROUP_CONFIG_DB_NAME;
                db.insertDefaultCustomerData(defaultCustomerNamesFromPreviousMonth);
                //Log.d("dbsvm","DefaultCustomer data data first row successfully in MainActivity");

                //After
//                 defaultCustomerNamesFromPreviousMonth=db.getDefaultCustomerDataForTextBoxes();
//                Log.d("dbsvm","DefaultCustomer data fetched successfully in MainActivity");

            }
            else//If data row is present in customerNames table, then fetch data here only one time to use in all activities
            {
                CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();
                //Log.d("dbsvm","DefaultCustomer data fetched successfully in MainActivity");

            }
//            db.updateDefaultData(contact,0);
//            db.clearDefaultDataTable(db1);
//            //Log.d("dbsvm","Inside main default data inserted");
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Inside MainActivity Unable to create milk table\n" +
                    "Error : "+e.getMessage().toString());
        }
        //Log.d("dbsvm","inside  mainActivity.java");
//        db.clearTable();
        //Creating a contact to db
//        Contact svm=new Contact();//Temporary stopped to add new contact
        //Will do this in TestLayout.java
//        svm.setC1(2.5);
//        svm.setC2(3.5);

//        svm.setNam("SK");
        //Adding a contact
//        db.addContact(svm);//linked this method with button, contact will be inserted when button is pressed
//        db.addContact();

//        ListViewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ViewActivity.class));
//            }
//        });

        //Click on view button to go on another xml layout file
//        ListViewButton.setOnClickListener(this);
//        recyclerViewButton=(Button) findViewById(R.id.recyclerViewButtton);
//        recyclerViewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, RecyclerView.class));
//            }
//        });

        //Separate activity to test not working
//        Button fsdForCheck = (Button) findViewById(R.id.viewButton);
//        fsdForCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
//                startActivity(new Intent(MainActivity.this, FsdForCheck.class));
//
//            }
//        });

        Button sharePdf=findViewById(R.id.share_pdf);
        sharePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                startActivity(new Intent(MainActivity.this,SharePdf.class));
            }
        });

        tableView=(Button) findViewById(R.id.tableView);
        tableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                startActivity(new Intent(MainActivity.this,TableTotalView.class));
            }
        });
        Button insertData=(Button) findViewById(R.id.insertData);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                startActivity(new Intent(MainActivity.this, InsertDbData.class));
            }
        });
        Button updateData=(Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                startActivity(new Intent(MainActivity.this, SetUpForUpdateDbData.class));
            }
        });

        Button deleteData=(Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                startActivity(new Intent(MainActivity.this, DeleteDbData.class));
            }
        });


        //Clear table data click listener
        Button clearTableData=(Button) findViewById(R.id.clearTableData);
        clearTableData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                String msg = "Dangerous Action!!\nAre your sure you want to delete whole table table data\n" +
                        "Click yes to continue";
//                final String[] action = {""};
                Context context = MainActivity.this;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("Please Select any option");
                dialog.setMessage(msg);
//        dialog.setTitle("Dialog Box");
                dialog.setTitle("Delete whole table data");
                dialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
//                                Toast.makeText(context, "Yes is clicked", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, ClearTableData.class));

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

//                if (action.equals("Yes")) //As length on No is 2
//                if(action.length()>2)
//                {
//                    TextView tviewhead=findViewById(R.id.textView);
//                    tviewhead.setText(action.length()+"");
//                    startActivity(new Intent(MainActivity.this, DeleteDbData.class));
//                }
            }
        });

        //Change customer names
        Button changeDefaultCustomerNames=(Button) findViewById(R.id.changeDefaultCustomerNames);

        changeDefaultCustomerNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                startActivity(new Intent(MainActivity.this, ChangeDefaultCustomerNames.class));
            }
        });

        GetAlerts ga=new GetAlerts();
        Button generatePdf=findViewById(R.id.generatePdf);
        generatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                //Add code to generate pdf from table layout
                startActivity(new Intent(MainActivity.this, GeneratePdf.class));

//                ga.alertDialogBox(MainActivity.this,"This service is currently unavailable \n","Service unavailable");

            }
        });

        Button sendReminder=findViewById(R.id.sendReminderGroup);
        sendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                //Add code to generate pdf from table layout
                startActivity(new Intent(MainActivity.this, SendReminderGroupMembersGetMoney.class));

//                ga.alertDialogBox(MainActivity.this,"This service is currently unavailable \n","Service unavailable");

            }
        });


    }


}