package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDBHandlerIndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DeleteIndividualPersonGroup extends Activity {
    public static String selectedGroupName = "";//Used to delete person person in MyDBHandlerIndividualPersonGroup
                                                //Inside deleteIndividualPersonGroup() method
    public static String[] str;
    public int i;
    public int sizeOfReceivedArrayList = 0;
    public String noGroupFound;
    ArrayList<String> customerDatabasesArrayList = new ArrayList<>();

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_individual_person_group);
//        final String str[] = {"unassessed", "Skipped", "Incorrect", "Correct", "1 mark"};
//        String numbers[] = { "100 ", "200 ","300"};

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
        try
        {
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/GloriaHallelujah-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Meddon-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/RuslanDisplay-Regular.ttf");

            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");

//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Monofett-Regular.ttf");//3d text
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/TradeWinds-Regular.ttf");
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }


        Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;

        MyDBHandlerIndividualPersonData db = new MyDBHandlerIndividualPersonData(DeleteIndividualPersonGroup.this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToDelete);

        try {
            showAllCustomerGroups();
        }catch (Exception e)
        {
            //Log.d("dbsvm","\nDelete Customer person Error :"+e.getMessage().toString());
        }


        //Remove cursor form selected number picker element
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //Increase font size of first element of number picker, without touching
        //as others will be affected after touch
        View child = numberPicker.getChildAt(0);
        if (child instanceof EditText) {
            try {
                @SuppressLint("SoonBlockedPrivateApi") Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                selectorWheelPaintField.setAccessible(true);
//                    ((EditText) child).setTextColor(DeleteConfigureCustomerGroupDataWithMonthAndYear.this.getResources().getColor(colorRes));
                ((EditText) child).setTextSize(30);
                numberPicker.performClick();
//                    TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);
//                    TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);

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
                            setSelectedTextColor((NumberPicker)v);
                        }
                    },0);
                }
                return false;
            }
        });


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                selectedGroupName = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);
                showSelectedGroupName.setText("Selected Person : " + selectedGroupName);
            }
        });
//Show first person as default selected, with scrolling
        try {
            selectedGroupName = numberPicker.getDisplayedValues()[0];
            TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);
            showSelectedGroupName.setText("Selected Person : " + selectedGroupName);
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Empty number picker\n" +
                    "Error : "+e.getMessage().toString());
        }





        Button deleteGroup = findViewById(R.id.deleteGroup);
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                String msg = "Are your sure you want to delete " + selectedGroupName + " person data.\n" +
                        "Click yes to continue";
//                final String[] action = {""};
                Context context = DeleteIndividualPersonGroup.this;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("Please Select any option");
                dialog.setMessage(msg);
//        dialog.setTitle("Dialog Box");
                dialog.setTitle("Delete the selected person");
                dialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            public void onClick(DialogInterface dialog,
                                                int which) {
//                                Toast.makeText(context, "Yes is clicked", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(DeleteIndividualPersonGroupName.this, ClearTableData.class));
                                GetAlerts ga = new GetAlerts();
                                try {
                                    String groupDatabaseName = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                                    db.deleteCustomeGroupDatabaseFromStoringTable(groupDatabaseName);
//                                    Params.DB_NAME=groupDatabaseName;
//                                    MyDBHandlerIndividualPersonData db1=new MyDBHandlerIndividualPersonData(DeleteIndividualPersonGroup.this);
//                                         db1.dropGroupTablesAssociated(groupDatabaseName);
//                                        db1.deleteAllTables();
//                                    db1.deleteCustomerGroup();
                                    db.deleteIndividualPersonGroup();

                                    ga.alertDialogBox(DeleteIndividualPersonGroup.this, groupDatabaseName + " person deleted successfully", "person Deleted Successfully");
                                }
                                catch (Exception e)
                                {
                                    ga.alertDialogBox(DeleteIndividualPersonGroup.this, "Unable to Delete person  ", "No person Found To Delete");
//                                        ga.alertDialogBox(DeleteIndividualPersonGroup.this,);
                                }
                                Params.DB_NAME=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;
                                showAllCustomerGroups();
//                                    HOMEcustomerGroupListView home=new HOMEcustomerGroupListView();
//                                    show new selected date after deletion
                                try {
                                    selectedGroupName = numberPicker.getDisplayedValues()[0];
                                    TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);
                                    showSelectedGroupName.setText("Selected Person : " + selectedGroupName);
                                }
                                catch (Exception e)
                                {
                                    Log.d("dbsvm","Empty number picker\n" +
                                            "Error : "+e.getMessage().toString());
                                }




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
        MyDBHandlerIndividualPersonData db = new MyDBHandlerIndividualPersonData(DeleteIndividualPersonGroup.this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToDelete);



        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                customerDatabasesArrayList = db.getAllSavedCustomerGroupDatabaseNames();
            }
            i = 0;
            sizeOfReceivedArrayList = customerDatabasesArrayList.size();
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
//    numberPicker1.setDisplayedValues(genders);
            if(sizeOfReceivedArrayList!=0) {
                numberPicker.setMaxValue(str.length - 1);
                numberPicker.setMinValue(0);
                numberPicker.setWrapSelectorWheel(true);
                numberPicker.setDisplayedValues(str);
//            setNumberPicker(yourNumberPicker,mValues);
            }
            else
            {
                numberPicker.setDisplayedValues(null);

//                numberPicker.setDisplayedValues("");
                TextView atShowSelectedGroup=findViewById(R.id.showSelectedGroupInDelete);
                atShowSelectedGroup.setText( "No person found");
            }
        } catch (Exception e) {
            Log.d("dbsvm", "String picker error \n" +
                    "Error : " + e.getMessage().toString());
        }

    }

    public void setSelectedTextColor(NumberPicker np) {
        final int count = np.getChildCount();
        for(int i = 0; i < count; i++){
            View child = np.getChildAt(i);
            if(child instanceof EditText){
                try{
                    @SuppressLint("SoonBlockedPrivateApi") Field selectorWheelPaintField = np.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
//                    ((EditText) child).setTextColor(DeleteConfigureCustomerGroupDataWithMonthAndYear.this.getResources().getColor(colorRes));
                    ((EditText) child).setTextSize(30);
                    np.performClick();
//                    TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInDelete);

                }
                catch(NoSuchFieldException e){
                }
                catch(IllegalArgumentException e){
                }
            }
        }
    }

}

