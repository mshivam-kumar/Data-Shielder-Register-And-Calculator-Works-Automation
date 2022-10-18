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
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;

//import android.graphics.pdf.PdfDocument;

//import org.w3c.dom.Document;

//import com.itextpdf.kernel.pdf.PdfDocument;


public class IndividualPersonMainActivity extends AppCompatActivity  {
    EditText editTextName;
    //    Button recyclerViewButton;
    Button tableView;
    //    Button testLayout;
    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_person_activity_main);

        String storage_folder = "/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+Params.CURRENT_GROUP_DB_NAME+"/"+Params.CURRENT_GROUP_CONFIG_DB_NAME;
//        Log.d("dbsvm"," individual home configure -storage folder "+storage_folder);

        Log.d("dbsvm","Inside IndividualPersonMainActivity "+Params.Curent_Running_Db);
        Context mContext=IndividualPersonMainActivity.this;
        String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
        Log.d("dbsvm",destPath);
        File f = new File(destPath, storage_folder);
        if (!f.exists()) {
            f.mkdirs();
        }

        Log.d("dbsvm",Params.DB_NAME);
        Log.d("dbsvm",Params.Curent_Running_Db);
        Log.d("dbsvm",Params.CURRENT_GROUP_DB_NAME);
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

//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Monofett-Regular.ttf");//3d text
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/TradeWinds-Regular.ttf");
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }



        //Log.d("dbsvm","inside  mainActivity.java");

//        TextView groupAccess=findViewById(R.id.groupAccessName);
////        groupAccess.setText("person Accessed : "+ Params.DB_NAME);
//        groupAccess.setText("person Accessed : "+ Params.CURRENT_GROUP_DB_NAME+"/"
//        +Params.CURRENT_GROUP_CONFIG_DB_NAME);

        TextView groupAccessed=findViewById(R.id.groupAccessed);
        groupAccessed.setText("Person Accessed : "+Params.groupNameToAddBeforeDefault_group_TableNamesReceived );
        TextView dataAccessed=findViewById(R.id.dataAccessed);
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );


        //Static customer names reseted to provide uniqueness to all groups customer names
        //No two grops can access each others customer names
//        CustomerNames.NAME1="Name1";CustomerNames.NAME2="Name2";
//        CustomerNames.NAME3="Name3";
//        CustomerNames.NAME4="Name4";CustomerNames.NAME5="Name5";
//        CustomerNames.NAME6="Name6";
//        CustomerNames.NAME7="Name7";CustomerNames.NAME8="Name8";
//        CustomerNames.NAME9="Name9";
//        CustomerNames.NAME10="Name10";CustomerNames.NAME11="Name11";
//        CustomerNames.NAME12="Name12";CustomerNames.NAME13="Name13";
//        CustomerNames.NAME14="Name14";CustomerNames.NAME15="Name15";
        Contact.QUANTITYUNITNAME="UNIT";
        Contact.Rate=0;
        Contact.selectedDate="";


//        Contact.rate=Double.parseDouble(findViewById(R.id.editTextMilkRate));
        TableTotalView ttV=new TableTotalView();//to update Contact.milkRate from box, as this class is
        //linked with MilkRate textBox
        MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(IndividualPersonMainActivity.this);
        SQLiteDatabase db1=db.getWritableDatabase();//pass this to create table

            //most operations are based on it so it needs to be created before using
//            db.clearDefaultValueDataTable(db1);
//            db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
            //Log.d("dbsvm", "If tables does not exists then Milk, defaultValue and defaultCustomerNames table created from IndividualPersonMainActivity");
//            try {
//                Contact.allContacts=db.getAllSavedRecords();
//                //Log.d("dbsvm","All Customer saved Records fetched successfully");
//            }
//            catch (Exception e)
//            {
//                //Log.d("dbsvm","Unable to fetch saved record of customers\n" +
//                        "Error : "+e.getMessage().toString());
//            }
            //single row to update
                db.createIndividualPersonDataValueStoringTable(db1);
                Log.d("dbsvm","individual person data table created successfully");
                //Log.d("dbsvm","DefaultCustomer data data first row successfully in IndividualPersonMainActivity");

                //After
                //Log.d("dbsvm","DefaultCustomer data fetched successfully in IndividualPersonMainActivity");


//            db.updateDefaultData(contact,0);
//            db.clearDefaultDataTable(db1);
//            //Log.d("dbsvm","Inside main default data inserted");
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
//                startActivity(new Intent(IndividualPersonMainActivity.this, ViewActivity.class));
//            }
//        });

        //Click on view button to go on another xml layout file
//        ListViewButton.setOnClickListener(this);
//        recyclerViewButton=(Button) findViewById(R.id.recyclerViewButtton);
//        recyclerViewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(IndividualPersonMainActivity.this, RecyclerView.class));
//            }
//        });

        //Separate activity to test not working
//        Button fsdForCheck = (Button) findViewById(R.id.viewButton);
//        fsdForCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(IndividualPersonMainActivity.this, ListViewActivity.class));
//                startActivity(new Intent(IndividualPersonMainActivity.this, FsdForCheck.class));
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

                startActivity(new Intent(IndividualPersonMainActivity.this,SharePdfIndividualPerson.class));
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

                startActivity(new Intent(IndividualPersonMainActivity.this,TableTotalViewIndividualPersonData.class));
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

                startActivity(new Intent(IndividualPersonMainActivity.this, InsertIndividualPersonDbData.class));
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

                startActivity(new Intent(IndividualPersonMainActivity.this, SetUpForUpdateIndividualPersonDbData.class));
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

                startActivity(new Intent(IndividualPersonMainActivity.this, DeleteIndividualPersonDbData.class));
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
                Context context = IndividualPersonMainActivity.this;
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
                                startActivity(new Intent(IndividualPersonMainActivity.this, ClearIndividualPersonDataTable.class));

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
//                    startActivity(new Intent(IndividualPersonMainActivity.this, DeleteDbData.class));
//                }
            }
        });

        //Change customer names
//        Button changeDefaultCustomerNames=(Button) findViewById(R.id.changeDefaultCustomerNames);
//
//        changeDefaultCustomerNames.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                long now=System.currentTimeMillis();
//                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
//                    return;
//                lastTimeClicked=now;
//                startActivity(new Intent(IndividualPersonMainActivity.this, ChangeDefaultCustomerNames.class));
//            }
//        });

        GetAlerts ga=new GetAlerts();
//        Button generatePdf=findViewById(R.id.generatePdf);
        Button SendReminder=findViewById(R.id.generatePdf);
        boolean choosingSendOrGeneratePdfBtn=Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE);
//        if(!Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE))
        if(!choosingSendOrGeneratePdfBtn)
        {
            SendReminder.setText("GENERATE PDF");
        }
        SendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                //Add code to generate pdf from table layout
//                startActivity(new Intent(IndividualPersonMainActivity.this, GenerateIndividualPersonDataPdf.class));
                if(choosingSendOrGeneratePdfBtn) {//If YOU WILL GET MONEY btn choosen, then open send
                    startActivity(new Intent(IndividualPersonMainActivity.this, SendReminderIndividualPersonGetMoney.class));

                }
                else//If YOU WILL GIVE MONEY btn choosen, the open generate pdf
                {
                    //Add code to generate pdf from table layout
                startActivity(new Intent(IndividualPersonMainActivity.this, GenerateIndividualPersonDataPdf.class));
                }

//                ga.alertDialogBox(IndividualPersonMainActivity.this,"This service is currently unavailable \n","Service unavailable");

            }
        });


    }


}