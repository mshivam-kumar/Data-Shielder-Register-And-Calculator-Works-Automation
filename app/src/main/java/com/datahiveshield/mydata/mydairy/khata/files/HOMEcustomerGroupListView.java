package com.datahiveshield.mydata.mydairy.khata.files;
//In the app coding group is considered as customer , so where ever customer used
//means group or group configuration

//Whenever want to debug this app, just replace //Log.d  with Log.d, all logs will come back
//they are commented for performance purpose, press ctrl+shift+r and type //Log.d replce
//with Log.d


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.datahiveshield.mydata.mydairy.khata.files.adapter.RecyclerViewAdapter;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandler;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class HOMEcustomerGroupListView extends AppCompatActivity {
    NumberPicker numberPicker;
    public ArrayList<String> customerDatabasesArrayList ;
    public int sizeOfReceivedArrayList = 0;
//    public ListView mylistView;
    public androidx.recyclerview.widget.RecyclerView recyclerView;
    public RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<String>a;

    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActionBarDrawerToggle mDrawerToggle;

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private Context mContext;


    @Override
    protected void onRestart() {//With back button reload this activity to update changes in group list
        Params.DB_NAME=Params.DEFAULT_DB_NAME;
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_customer_group_list_view);

//        if(GoogleSignIn1.personEmail.equals(""))//Guest sign in, if email is null
//            //means user has gone for guest sign in , so access guest sign in folder
//            //by assigning value to Params.EmailId
//        {
//            Params.EmailId = "DataShielderAppGuestSignIn";
//        }


        //Delete the file created to share, here it will always be deleted as
        //if user comes back as this activity reloads on back
//        try {
////            Thread.sleep(15000);
//            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/share.pdf";
//            //Log.d("dbsvm", "/nshare pdf file Path to delete : " + path);
//
////        File dir = new File(storage_folder);
//            File file = new File(path);
//            if(file.exists())
//            {
//                file.delete();
//                //Log.d("dbsvm", "/nshare pdf file deleted : " + path);
//
//            }
//        }
//        catch (Exception e)
//        {
//            //Log.d("dbsvm","Unable to delete share pdf file\n" +
////                    "Error : "+e.getMessage().toString());
//        }

        //Change font of app name
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


        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.nav_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//Setting custome toolbar back button to go back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                }

        });


        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);

//        TextView personName=findViewById(R.id.personName);
//        TextView personEmail=findViewById(R.id.personEmail);
//        personName.setText(GoogleSignIn1.personName);
//        personEmail.setText(GoogleSignIn1.personEmail);


       @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("UserSignInInfoSharedPref",MODE_APPEND);
       String userName=sharedPreferences.getString("name","");
       String userEmail=sharedPreferences.getString("email","");

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.personName);
        navUsername.setText(userName);
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.personEmail);
        navUserEmail.setText(userEmail);

//        TextView personName = (TextView) navigationView.getHeaderView(1);
//       personName.setText(userName);
//       personEmail.setText(userEmail);

        //Log.d("dbsvm","Name : "+GoogleSignIn1.personName);
        //Log.d("dbsvm","Email : "+GoogleSignIn1.personEmail);

        //Log.d("dbsvm","Name : "+userName);
        //Log.d("dbsvm","Email : "+userEmail);

        dl = (DrawerLayout) findViewById(R.id.dl);


        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
//        abdt = new ActionBarDrawerToggle(this, dl, R.drawable.ic_baseline_person_24, R.string.Open, R.string.Close);
//        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        //Set background color of individual person data menu item to higlight due
        //to importance
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.manage_individual_person_data);
        Spannable spannable = new SpannableString(menuItem.getTitle());
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spannable.length(), 0);
        spannable.setSpan(new BackgroundColorSpan(0xFF00CCCC), 0, menuItem.getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        menuItem.setTitle(spannable);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                onOptionsItemSelected(item);
                int id=item.getItemId();


                if(id==R.id.manage_individual_person_data)
                {

//                    startActivity(new Intent(HOMEcustomerGroupListView.this, HOMEIndividualPersonDataListView.class));
                    startActivity(new Intent(HOMEcustomerGroupListView.this, ChooseMoneyWillGiveOrGetIndividualPersonGroup.class));

                }
                else if(id==R.id.nav_about)
                {
                    startActivity(new Intent(HOMEcustomerGroupListView.this,About.class));
//                    finish();
                }
                else if(id==R.id.nav_userInstructions)
                {
                   startActivity(new Intent(HOMEcustomerGroupListView.this,UserInstructions.class));
                }
                else if(id==R.id.settings)
                {
                    startActivity(new Intent(HOMEcustomerGroupListView.this,SettingsApp.class));
                }
                else if(id==R.id.share_app)
                {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Make yourself calculator and register free, automate your data today.\nDownload Data Shielder app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    startActivity(Intent.createChooser(sendIntent, "Share via"));
//                    startActivity(sendIntent);
//                    Toast.makeText(HOMEcustomerGroupListView.this, "Share App", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(HOMEcustomerGroupListView.this,UserInstructions.class));
                }

                else if(id==R.id.rate_us)
                {
                    try {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(goToMarket);
                        //Log.d("dbsvm","Inside try block to rate");
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        //Log.d("dbsvm","Inside catch block to rate");
                    }
//                    Toast.makeText(HOMEcustomerGroupListView.this, "Rate Us", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(HOMEcustomerGroupListView.this,UserInstructions.class));
                }
                else if(id==R.id.contact_us)
                {
//                    Toast.makeText(HOMEcustomerGroupListView.this, "Contact Us", Toast.LENGTH_SHORT).show();
                    String[] TO = {"datahiveshield.help@gmail.com"};
                    Uri uri = Uri.parse("mailto:datahiveshield.help@gmail.com")
                            .buildUpon()
                            .appendQueryParameter("subject", "")
                            .appendQueryParameter("body", "")
                            .build();
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }

                //Tree directory of all folders in Android-> created in google drive
                //Unable to upload a file to google drive automatically without
                //showing choosing intent to user
//                else if(id==R.id.google_drive_support)
//                {
//
////                    Toast.makeText(HOMEcustomerGroupListView.this, "Save To Google Drive", Toast.LENGTH_SHORT).show();
//
//                    startActivity(new Intent(HOMEcustomerGroupListView.this, GDriveMain.class));
////                    startActivity(new Intent(HOMEcustomerGroupListView.this,ServiceNotSupported.class));
//                }


                else if(id==R.id.nav_addOrSwitchAccount)
                {
                    GoogleSignIn.getClient(getApplicationContext(), GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
                    startActivity(new Intent(HOMEcustomerGroupListView.this,GoogleSignIn1.class));
                    finish();

                }
                else if(id==R.id.nav_signOut)
                {
//                    Toast.makeText(HOMEcustomerGroupListView.this, "Sign Out", Toast.LENGTH_SHORT).show();
                    GoogleSignIn.getClient(getApplicationContext(), GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
                    startActivity(new Intent(HOMEcustomerGroupListView.this,GoogleSignIn1.class));
                    finish();
                }
                return true;
            }
        });


        findViewById(R.id.nav_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dl.isDrawerOpen(GravityCompat.END)) {
                    dl.closeDrawer(GravityCompat.END);
                } else {
                    dl.openDrawer(GravityCompat.END);
                }
            }
        });

        //        TouchImageView touchImageView=new TouchImageView(HOMEcustomerGroupListView.this);



//        TextView tv=findViewById(R.id.textView2);
//      mylistView=findViewById(R.id.listView);
//      showAllGroupsList();
      recyclerView=findViewById(R.id.homeRecyclerView);
        recyclerView.setMotionEventSplittingEnabled(false);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //Stop control go to recycler view after some change made in recycler view
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);

        Params.DB_NAME=Params.DEFAULT_DB_NAME;

//        File sourceFile = new File(getReadableDatabase().getPath());
//        String DB_PATH="";
        Context context=HOMEcustomerGroupListView.this;

//        //Log.d("dbsvm","\ndb path : "+DB_PATH);
//        Params.DB_NAME=Params.DEFAULT_DB_NAME;
//        ArrayList<String> customerDatabasesArrayList=new ArrayList<>();
//        NumberPicker numberPicker= (NumberPicker) findViewById(R.id.numberPicker);
//        final String str[] = { "unassessed", "Skipped", "Incorrect", "Correct", "1 mark" };
        a=new ArrayList<>();


try {

//    String storage_folder = "/.DataShielderApp/db";
//    String storage_folder = "/."+Params.EmailId+"/"+Params.DB_NAME;
        String storage_folder = "/." + Params.EmailId + "/" + Params.DB_NAME;

        mContext=HOMEcustomerGroupListView.this;

//    String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
//    Log.d("dbsvm",destPath);
//    File f = new File(destPath, storage_folder);
    String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
    Params.App_Path_Location=destPath;
    Log.d("dbsvm",destPath);
    File f = new File(destPath, storage_folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        MyDbHandler db = new MyDbHandler(HOMEcustomerGroupListView.this);
//    int count=db.getCountValueForMainMenu();

//    //Log.d("dbsvm",""+count);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table
        db.createCustomerGroupDatabaseStoringTable(db1);//Database table to store new group name ,create if not exits

    }catch (Exception e)
    {
        //If unable to create folder in user's device then show not supported on device
        //message written in ServiceNotSupported.java class
        startActivity(new Intent(HOMEcustomerGroupListView.this,ServiceNotSupported.class));


        //Log.d("dbsvm","\nError in HOME :"+e.getMessage().toString());
    }

        showAllGroupsList();

        //Adding clickListeners to buttons
        Button createNewGroup=(Button) findViewById(R.id.createGroup);
        createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                Intent intent = new Intent(HOMEcustomerGroupListView.this,CreateNewCustomerGroup.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //Log.d("dbsvm","CreateNewGroup activity opened");
                showAllGroupsList();

            }
        });

        try {
            Button updateGroup = (Button) findViewById(R.id.updateGroup);
            updateGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long now=System.currentTimeMillis();
                    if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                        return;
                    lastTimeClicked=now;

//                   startActivity(new Intent(HOMEcustomerGroupListView.this, UpdateCustomerGroup.class));
                    Intent intent = new Intent(HOMEcustomerGroupListView.this, RenameCustomerGroup.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);



                }
            });
        }
        catch(Exception e)
        {
            //Log.d("dbsvm", e.getMessage().toString());
        }

        Button deleteGroup=(Button) findViewById(R.id.deleteGroup);
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                startActivity(new Intent(HOMEcustomerGroupListView.this,DeleteCustomerGroup.class));
//                Intent intent = new Intent(HOMEcustomerGroupListView.this,DeleteCustomerGroup.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

            }
        });

        //set back button to go back
//        findViewById(R.id.imageBackButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });




    }


    public void showAllGroupsList()
    {
//        Params.DB_NAME=Params.DEFAULT_DB_NAME;
//        Params.Curent_Running_Db=Params.DB_NAME;
        ArrayList<String >customerDatabasesArrayList = new ArrayList<>();
//        ListView mylistView=findViewById(R.id.listView);

        //the constructor so ,DB_NAME change will only take place if constructor is called after changing name
        //Storing all new database names int the same db, to fetch all later
        MyDbHandler db=new MyDbHandler(HOMEcustomerGroupListView.this);
        SQLiteDatabase db1 = db.getWritableDatabase();//pass this to create table
        db.createCustomerGroupDatabaseStoringTable(db1);//Database table to store new group name ,create if not exits
        //As below we are checking whether the entered name exists in it or not
        //Else if not present , it will give table not found error
//        MyDbHandler db=new MyDbHandler(HOMEcustomerGroupListView.this);
//        List<CustomerGroupDatabases> customerGroupDatabasesName = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            customerDatabasesArrayList = db.getAllSavedCustomerGroupDatabaseNames();

        }
        if(customerDatabasesArrayList.size()==0)
        {
//            customerDatabasesArrayList.add("No group found.\nCreate a new group to insert data");
            TextView noGroupFound=findViewById(R.id.ifNoGroupFound);
            noGroupFound.setText("No group found.\nCreate a new group to insert data");
//            customerDatabasesArrayList.add("");
        }

        if(customerDatabasesArrayList.size()!=0) {

            try {
                a=customerDatabasesArrayList;
                Collections.sort(a);//Show month year configuration in ascending order
                recyclerViewAdapter = new RecyclerViewAdapter(HOMEcustomerGroupListView.this,a );
                recyclerView.setAdapter(recyclerViewAdapter);
            }
            catch (Exception e)
            {
                //Log.d("dbsvm","Inside HOME" +
//                        "\nError : "+e.getMessage().toString());
            }


        }

    }

    void askRatings() {
        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task2 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
            }
        });
    }


}
