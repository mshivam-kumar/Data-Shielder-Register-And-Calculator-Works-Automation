package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandler;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class RenameCustomerGroup extends Activity {
    String selectedGroupName = "";
    public static String[] str;
    public int i;
    public int sizeOfReceivedArrayList = 0;
    public String noGroupFound;
    ArrayList<String> customerDatabasesArrayList = new ArrayList<>();

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private RenameCustomerGroup mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer_group);

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
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }


        Params.DB_NAME=Params.DEFAULT_DB_NAME;
//        String storage_folder = "/.DataShielderApp/db";
//        String storage_folder = "/myapp/db";
        String storage_folder = "/."+Params.EmailId+"/"+Params.DB_NAME;


        mContext= RenameCustomerGroup.this;
        String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
//    Log.d("dbsvm",destPath);
    File f = new File(destPath, storage_folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        MyDbHandler db = new MyDbHandler(RenameCustomerGroup.this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToUpdate);


//        try {
        showAllCustomerGroups();

        findViewById(R.id.editTextUpdateGroupName).requestFocus();
//        }catch (Exception e)
//        {
//            //Log.d("dbsvm","Unable to fetch Customer database names in UpdateCustomerGroup.java(inside onCreate)");
//        }
//        try {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                customerDatabasesArrayList = db.getAllSavedCustomerGroupDatabaseNames();
//            }
//            i = 0;
//            sizeOfReceivedArrayList = customerDatabasesArrayList.size();
//             str = new String[sizeOfReceivedArrayList];
//            for (String cDatabaseName : customerDatabasesArrayList) {
//                str[i] = cDatabaseName;
//                i++;
//            }
//        }
//        catch (Exception e)
//        {
//            //Log.d("dbsvm","unable to fetch database name data\n" +
//                    "Error : "+e.getMessage().toString());
//        }
//
//        try {
//            if(sizeOfReceivedArrayList!=0) {
//                numberPicker.setMaxValue(str.length - 1);
//                numberPicker.setMinValue(0);
//                numberPicker.setWrapSelectorWheel(true);
//                numberPicker.setDisplayedValues(str);
//            }
//            else
//            {
////                numberPicker.setDisplayedValues("");
//                TextView atShowSelectedGroup=findViewById(R.id.showSelectedGroupInUpdateCustomerGroup);
//                atShowSelectedGroup.setText( "No group found.\nCreate a new group to update group name");
//            }
////            setNumberPicker(yourNumberPicker,mValues);
//        } catch (Exception e) {
//            //Log.d("dbsvm", "Inside UpdateCustomerGroup.java String picker error \n" +
//                    "Error : " + e.getMessage().toString());
//        }




        Button updateGroup = (Button) findViewById(R.id.updateGroupName);
        updateGroup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                GetAlerts ga = new GetAlerts();
                String oldGroupDatabaseName="",newgroupDatabaseName="";
                boolean flag_able_to_update=false;
                boolean flag_empty_new_name=false;
                try {
                    oldGroupDatabaseName = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                    EditText etnewGrpName = findViewById(R.id.editTextUpdateGroupName);
                    newgroupDatabaseName = etnewGrpName.getText().toString();
                    flag_able_to_update=true;
                } catch (Exception e)
                {
                    TextView atShowSelectedGroup=findViewById(R.id.showSelectedGroupInUpdateCustomerGroup);
                    atShowSelectedGroup.setText( "No group found");
                    if(oldGroupDatabaseName.length()==0) {
                        flag_empty_new_name=true;
                        ga.alertDialogBox(RenameCustomerGroup.this, "Unable to update group name " + oldGroupDatabaseName, "No Group Found To Update");
                    }

                }
//                try {
                if(newgroupDatabaseName.length()>0) {
//                    Params.DB_NAME=Params.Customer_Group_Storing_DB_NAME;
//                    MyDbHandler db2 = new MyDbHandler(UpdateCustomerGroup.this);
                    //group name updated
                    if (db.isCustomerGroupDatabaseExist(newgroupDatabaseName) == 0) {//Database with entered name does not exists exists to update
                        Params.DB_TO_CHANGE = oldGroupDatabaseName;
                        db.updateCustomerGroupDatabaseStoringTable(oldGroupDatabaseName, newgroupDatabaseName);
//                    Params.toAddBeforeNewTableName=newgroupDatabaseName;
//                        Params.groupNameToAddBeforeDefault_group_TableNamesReceived = newgroupDatabaseName;
//                        String changeConfigToNew = newgroupDatabaseName + "_" + Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;

//                    Params.DB_NAME=oldGroupDatabaseName;
//                    MyDbHandler db1=new MyDbHandler()

                        db.changeCustomerGroupName(oldGroupDatabaseName, newgroupDatabaseName);
                    if (flag_able_to_update) {

                        ga.alertDialogBox(RenameCustomerGroup.this, oldGroupDatabaseName + " group changed to " + newgroupDatabaseName + " group successfully", "Group Name Updated Successfully");
                    }
                    }
                    else
                    {
                        ga.alertDialogBox(RenameCustomerGroup.this, "Unable to update  group name.\nGroup with name '" + oldGroupDatabaseName+
                                "' already exists.", "Database already exists");

                    }
                }
                if(newgroupDatabaseName.length()==0&&!flag_empty_new_name)
                {
                    ga.alertDialogBox(RenameCustomerGroup.this, "Please enter a valid group name to update", "Empty Group Name");

                }
                showAllCustomerGroups();
                //show new selected date after updating group name

                try {
                    selectedGroupName = numberPicker.getDisplayedValues()[0];
                    @SuppressLint("CutPasteId") TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInUpdateCustomerGroup);
                    showSelectedGroupName.setText("Selected Group : " + selectedGroupName);
                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Empty number picker\n" +
                            "Error : "+e.getMessage().toString());
                }
//                }


//                ga.alertDialogBox(UpdateCustomerGroup.this, "This will be joined with database later", "Service not available");

            }
        });

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
//                    showSelectedGroupName.setText("Selected Group : " + ((EditText) child).getText().toString());

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
                TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInUpdateCustomerGroup);
                showSelectedGroupName.setText("Selected Group : " + selectedGroupName);
            }
        });

        //Show first group as default selected, with scrolling
        try {
            selectedGroupName = numberPicker.getDisplayedValues()[0];
            TextView showSelectedGroupName = findViewById(R.id.showSelectedGroupInUpdateCustomerGroup);
            showSelectedGroupName.setText("Selected Group : " + selectedGroupName);
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Empty number picker\n" +
                    "Error : "+e.getMessage().toString());
        }

    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    //    @Override
//    public void onRestart() {
//        super.onRestart();
//        //When BACK BUTTON is pressed, the activity on the stack is restarted
//        //Do what you want on the refresh procedure here
//
//    }
    public  void showAllCustomerGroups()
    {
        Params.DB_NAME=Params.DEFAULT_DB_NAME;
        MyDbHandler db = new MyDbHandler(RenameCustomerGroup.this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToUpdate);



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
//                numberPicker.setDisplayedValues("");
                TextView atShowSelectedGroup=findViewById(R.id.showSelectedGroupInUpdateCustomerGroup);
                atShowSelectedGroup.setText( "No group found");
            }
        } catch (Exception e) {
            Log.d("dbsvm", "String picker error \n" +
                    "Error : " + e.getMessage().toString());
        }

    }


    //Update home screen with changes, on press of back button
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if(keyCode == KeyEvent.KEYCODE_BACK)
//        {
//
//            Intent intent = new Intent(UpdateCustomerGroup.this, HOMEcustomerGroupListView.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Open new activity without loading white screen
//            startActivity(intent);
//            return true;
//        }
//        return false;
//    }

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
//                    showSelectedGroupName.setText("Selected Group : " + ((EditText) child).getText().toString());

                }
                catch(NoSuchFieldException e){
                }
                catch(IllegalArgumentException e){
                }
            }
        }
    }

}



