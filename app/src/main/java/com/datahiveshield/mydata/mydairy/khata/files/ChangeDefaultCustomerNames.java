package com.datahiveshield.mydata.mydairy.khata.files;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.datahiveshield.mydata.mydairy.khata.files.R;
import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;

public class ChangeDefaultCustomerNames extends Activity {
    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();
    private ArrayList<String> newCustomerNamesArrayList=new ArrayList<>();
    private CustomerNames oldCustomerNamesSet;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_default_customer_names);



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



        TextView tvDATE=findViewById(R.id.textViewDate2);
        tvDATE.setText("Today's Date : "+Contact.DefaultDate);

        GetAlerts ga = new GetAlerts();
        MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(ChangeDefaultCustomerNames.this);

//       Get the current names had by user from database
        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();

        //Set the current names had by user to text views to show to user
        setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show

        //the default saved customer names from database
        //Get user entered names to change

        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button changeDefaultCustomerNames=findViewById(R.id.changeDefaultNames);
        changeDefaultCustomerNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                try {
                    oldCustomerNamesSet=defaultCustNames;//it will be used to update names in Default_customer_name_and_phone_number_table
                    CustomerNames newCustomerNamesSet = getNewCustomerNamesFromTextBoxes();
//                    CustomerNames newCustomerNamesSet=new CustomerNames();
                    setNewCustomerNamesIntoTextViews(newCustomerNamesSet);
                    db.updateDefaultCustomerData(newCustomerNamesSet);//Update the names entered by user
                    //Log.d("dbsvm","customer names changed in changeDefaultCustomerNames.java");

                    db.updateDefaultCustomerGroupMemberName(newCustomerNamesArrayList);//update the names of new customers in DEFAULT_CUSTOMER_NAMES_AND_PHONE_NUMBER_TABLE
                    //that contains groupo members name and phone number

                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Unable to change customer names\n" +
                            "Error : "+e.getMessage().toString());
                    ga.alertDialogBox(ChangeDefaultCustomerNames.this,"Unable to change entered customer names","Unable to change");

                }
            }
        });
    }


    public CustomerNames getNewCustomerNamesFromTextBoxes() {

        GetAlerts ga = new GetAlerts();
        CustomerNames newCustomerNamesSet = new CustomerNames();

        //Get typed data from textboxes into string and insert into db table
        try {
//           all below initialized as class variable,to use throughout whole class, without initializing again

            EditText etC1 = findViewById(R.id.editTextNewName1);
            EditText etC2 = findViewById(R.id.editTextNewName2);
            EditText etC3 = findViewById(R.id.editTextNewName3);
            EditText etC4 = findViewById(R.id.editTextNewName4);
            EditText etC5 = findViewById(R.id.editTextNewName5);
            EditText etC6 = findViewById(R.id.editTextNewName6);
            EditText etC7 = findViewById(R.id.editTextNewName7);
            EditText etC8 = findViewById(R.id.editTextNewName8);
            EditText etC9 = findViewById(R.id.editTextNewName9);
            EditText etC10 = findViewById(R.id.editTextNewName10);
            EditText etC11 = findViewById(R.id.editTextNewName11);
            EditText etC12 = findViewById(R.id.editTextNewName12);
            EditText etC13 = findViewById(R.id.editTextNewName13);
            EditText etC14 = findViewById(R.id.editTextNewName14);
            EditText etC15 = findViewById(R.id.editTextNewName15);



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

            //newCustomerNamesArrayList to update the names in DEFAULT_CUSTOMER_NAMES_AND_PHONE_NUMBER_TABLE .. this is separate table
            //that contains name and phone numbers of group members
//            newCustomerNamesArrayList.add(sc5);
//            newCustomerNamesArrayList.add(sc6);
//            newCustomerNamesArrayList.add(sc7);
//            newCustomerNamesArrayList.add(sc8);
//            newCustomerNamesArrayList.add(sc9);
//            newCustomerNamesArrayList.add(sc10);
//            newCustomerNamesArrayList.add(sc11);
//            newCustomerNamesArrayList.add(sc12);
//            newCustomerNamesArrayList.add(sc13);
//            newCustomerNamesArrayList.add(sc14);
//            newCustomerNamesArrayList.add(sc15);

            int i=0;
            //Create CustomerName object with user input names length>0
            if(sc1.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME1(sc1);
                newCustomerNamesArrayList.add(sc1);
            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME1());

            }
            if(sc2.length()>0)
            {                i++;
                newCustomerNamesSet.setNAME2(sc2);
                newCustomerNamesArrayList.add(sc2);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME2());

            }
            if(sc3.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME3(sc3);
                newCustomerNamesArrayList.add(sc3);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME3());

            }
            if(sc4.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME4(sc4);

                newCustomerNamesArrayList.add(sc4);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME4());

            }
            if(sc5.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME5(sc5);
                newCustomerNamesArrayList.add(sc5);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME5());

            }
            if(sc6.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME6(sc6);
                newCustomerNamesArrayList.add(sc6);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME6());

            }
            if(sc7.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME7(sc7);
                newCustomerNamesArrayList.add(sc7);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME7());

            }
            if(sc8.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME8(sc8);
                newCustomerNamesArrayList.add(sc8);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME8());

            }
            if(sc9.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME9(sc9);
                newCustomerNamesArrayList.add(sc9);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME9());

            }
            if(sc10.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME10(sc10);
                newCustomerNamesArrayList.add(sc10);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME10());

            }
            if(sc11.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME11(sc11);
                newCustomerNamesArrayList.add(sc11);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME11());

            }
            if(sc12.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME12(sc12);
                newCustomerNamesArrayList.add(sc12);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME12());

            }
            if(sc13.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME13(sc13);
                newCustomerNamesArrayList.add(sc13);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME13());

            }
            if(sc14.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME14(sc14);
                newCustomerNamesArrayList.add(sc14);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME14());

            }
            if(sc15.length()>0)
            {
                i++;
                newCustomerNamesSet.setNAME15(sc15);
                newCustomerNamesArrayList.add(sc15);

            }
            else
            {
                newCustomerNamesArrayList.add(oldCustomerNamesSet.getNAME15());

            }

            if(i>0)
            {
                ga.alertDialogBox(ChangeDefaultCustomerNames.this,"Entered customer names changed successfully","Changed Successfully");

            }
            else
            {
                ga.alertDialogBox(ChangeDefaultCustomerNames.this,"Unable to change names \nPlease enter the names to change","Empty text");

            }

        } catch (Exception e) {
            //Log.d("dbsvm", "Unable to create new set of User names");
        }
        return newCustomerNamesSet;
    }


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
        etC1.setText(CustomerNamesSet.getNAME1());
        etC2.setText(CustomerNamesSet.getNAME2());
        etC3.setText(CustomerNamesSet.getNAME3());
        etC4.setText(CustomerNamesSet.getNAME4());
        etC5.setText(CustomerNamesSet.getNAME5());
        etC6.setText(CustomerNamesSet.getNAME6());
        etC7.setText(CustomerNamesSet.getNAME7());
        etC8.setText(CustomerNamesSet.getNAME8());
        etC9.setText(CustomerNamesSet.getNAME9());
        etC10.setText(CustomerNamesSet.getNAME10());
        etC11.setText(CustomerNamesSet.getNAME11());
        etC12.setText(CustomerNamesSet.getNAME12());
        etC13.setText(CustomerNamesSet.getNAME13());
        etC14.setText(CustomerNamesSet.getNAME14());
        etC15.setText(CustomerNamesSet.getNAME15());


    }

}
