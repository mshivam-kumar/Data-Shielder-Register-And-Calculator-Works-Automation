package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.datahiveshield.mydata.mydairy.khata.files.adapter.RecyclerViewAdapter;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandler;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;




public class HomeConfigureCustomerGroupDataWithMonthAndYear extends AppCompatActivity {
    NumberPicker numberPicker;
    public ArrayList<String> customerDatabasesArrayList ;
    public int sizeOfReceivedArrayList = 0;
    //    public ListView mylistView;
    public androidx.recyclerview.widget.RecyclerView recyclerView;
    public RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<String>a;

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private Context mContext;


    @Override
    protected void onRestart() {//With back button reload this activity to update changes in group list
        super.onRestart();
        finish();
//        overridePendingTransition(0, 0);
        startActivity(getIntent());
//        overridePendingTransition(0, 0);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_configure_customer_group_data_with_month_and_year);

//        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


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

            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }
//        TextView tv=findViewById(R.id.textView2);
//      mylistView=findViewById(R.id.listView);
//      showAllGroupsList();
        recyclerView=findViewById(R.id.homeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Params.DB_NAME=Params.CURRENT_GROUP_DB_NAME;
        TextView groupAccessed=findViewById(R.id.groupAccessedInConfig);
        if(Params.INDIVIDUAL_PERSON_DATA_TABLE_NAME_ACCESS_TELLER.equals(Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME))
            groupAccessed.setText("Person Accessed : "+Params.groupNameToAddBeforeDefault_group_TableNamesReceived);
        else
            groupAccessed.setText("Group Accessed : "+Params.groupNameToAddBeforeDefault_group_TableNamesReceived);

        //Stop control go to recycler view after some change made in recycler view
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);

        a=new ArrayList<>();
//        a.add("c");
//        a.add("c");
//        a.add("c");
//        a.add("c");
//        a.add("c");



        //the constructor so ,DB_NAME change will only take place if constructor is called after changing name
        //Storing all new database names int the same db, to fetch all later
//        String storage_folder = "/.DataShielderApp/"+Params.DB_NAME;
        String storage_folder = "/."+Params.EmailId+"/"+Params.DB_NAME;
//        Log.d("dbsvm","Inside home configure "+storage_folder);
        mContext=HomeConfigureCustomerGroupDataWithMonthAndYear.this;
        String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
//    Log.d("dbsvm",destPath);
    File f = new File(destPath, storage_folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        MyDbHandler db=new MyDbHandler(HomeConfigureCustomerGroupDataWithMonthAndYear.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table
//        db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);//Database table to store new group name ,create if not exits
try {
//    Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
//    Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;

//    Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
    db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);
    showAllGroupsList();
}catch (Exception e)
{
    Log.d("dbsvm","Unable to fetch configuration data\n" +
            "Error : "+e.getMessage().toString());
}

//Set back button image to go to previous activity
//    findViewById(R.id.imageBackButton).setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            onBackPressed();
//        }
//    });


        //Adding clickListeners to buttons
        Button createNewGroup=(Button) findViewById(R.id.createGroup);
        createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
//                startActivity(new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this,CreateNewCustomerGroup.class));
//                Intent intent = new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this,CreateNewConfigureCustomerGroupDataWithMonthAndYear.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                startActivity(new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this,CreateNewConfigureCustomerGroupDataWithMonthAndYear.class));
                //Log.d("dbsvm","CreateNewGroup activity opened");
//                showAllGroupsList();

            }
        });

        //No need to change month year configuration name, as data is inserted
        //according to month and year, if name is changed then in different month year
        //different data may be found, which is not goog

//        try {
//            Button updateGroup = (Button) findViewById(R.id.updateGroup);
//            updateGroup.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                   startActivity(new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this, UpdateCustomerGroup.class));
//                    Intent intent = new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this,UpdateConfigureCustomerGroupDataWithMonthAndYear.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//
//
//
//                }
//            });
//        }
//        catch(Exception e)
//        {
//            //Log.d("dbsvm", e.getMessage().toString());
//        }

        Button deleteGroup=(Button) findViewById(R.id.deleteGroup);
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                startActivity(new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this,DeleteConfigureCustomerGroupDataWithMonthAndYear.class));
//                Intent intent = new Intent(HomeConfigureCustomerGroupDataWithMonthAndYear.this,DeleteCustomerGroup.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showAllGroupsList()
    {
        ArrayList<String >customerDatabasesArrayList = new ArrayList<>();
//        Params.DB_NAME=Params.DEFAULT_DB_NAME;
//        Params.Curent_Running_Db=Params.DB_NAME;
//        ListView mylistV`iew=findViewById(R.id.listView);
        //the constructor so ,DB_NAME change will only take place if constructor is called after changing name
        //Storing all new database names int the same db, to fetch all later
        MyDbHandler db=new MyDbHandler(HomeConfigureCustomerGroupDataWithMonthAndYear.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table
//        db.createConfigureCustomerGroupDataTableWithMonthAndYear(db1);//Database table to store new group name ,create if not exits
        //As below we are checking whether the entered name exists in it or not
        //Else if not present , it will give table not found error
//        MyDbHandler db=new MyDbHandler(HomeConfigureCustomerGroupDataWithMonthAndYear.this);
//        List<CustomerGroupDatabases> customerGroupDatabasesName = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            customerDatabasesArrayList = db.getAllSavedConfigureCustomerGroupDataTableWithMonthAndYear();

        }
        if(customerDatabasesArrayList.size()==0)
        {
//            customerDatabasesArrayList.add("No group found.\nCreate a new group to insert data");
            TextView noGroupFound=findViewById(R.id.ifNoGroupFound);
            noGroupFound.setText("No Data found");
//            customerDatabasesArrayList.add("");
        }

//        else {
////            CustomerGroupDatabases c=
//            for (CustomerGroupDatabases cDatabaseName : customerGroupDatabasesName) {
//                customerDatabasesArrayList.add(cDatabaseName.getCustomerGroupNameDatabase());
//            }
//        }


//        ArrayList<String> grocery=new ArrayList<>();
//        grocery.add("Bhindi");
//        grocery.add("Pen");
//        grocery.add("Apples");
//        grocery.add("Tea Leaves");
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,grocery);
        if(customerDatabasesArrayList.size()!=0) {

            try {
                a=customerDatabasesArrayList;
                ArrayList<String> temp_a=new ArrayList<>();
//                Collections.replaceAll(a,Params.groupNameToAddBeforeDefaultTableNamesReceived,"");
//                Collections.replaceAll(a,"_","  ");
                    String t="";
                for(int i=0;i<a.size();i++)
                {
                    t=a.get(i);
//                    t=t.replace(Params.groupNameToAddBeforeDefault_group_TableNamesReceived,"");
                    t=t.replace("_"," ");
                    a.set(i,t);

                }
//                recyclerViewAdapter = new RecyclerViewAdapter(HomeConfigureCustomerGroupDataWithMonthAndYear.this,a );
//                Collections.sort(a);

//                sorting string arraylist containing dates as string 'june 2021'
                Collections.sort(a, new Comparator<String>() {
                    //Sorting date with month and year configuration
                    //of format 'june 2021'
                    @SuppressLint("SimpleDateFormat")
                    DateFormat f = new SimpleDateFormat("MMMM yyyy");
                    @Override
                    public int compare(String o1, String o2) {
                        try {
                            return f.parse(o1).compareTo(f.parse(o2));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
//                Collections.sort(a);//Show month year configuration in ascending order
//                Collections.sort(a);
                recyclerViewAdapter = new RecyclerViewAdapter(HomeConfigureCustomerGroupDataWithMonthAndYear.this,a);

                recyclerView.setAdapter(recyclerViewAdapter);
            }
            catch (Exception e)
            {
                Log.d("dbsvm","Inside HOME" +
                        "\nError : "+e.getMessage().toString());
            }

        }

    }

}
