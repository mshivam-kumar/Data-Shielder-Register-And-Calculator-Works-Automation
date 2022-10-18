package com.datahiveshield.mydata.mydairy.khata.files;

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
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.R;
import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

//Process linked after proceed button in SetUpForUpdateDbData
public class UpdateDbData extends AppCompatActivity {
    Button updateData;
    TextView setSelectedDateIntoBox;

   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_db_data);

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


        TextView textViewDate=(TextView) findViewById(R.id.textViewDate);
        Contact contact=new Contact();
        textViewDate.setText("Today's Date : "+contact.getDefaultDate());

        setSelectedDateIntoBox=(TextView) findViewById(R.id.selectedDateToUpdate);
        setSelectedDateIntoBox.setText(contact.getSelectedDate());

        MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(UpdateDbData.this);
        //Initialize defualtValues of assoiciated Customer group into textboxes
//        Contact init_contact=db.getDefaultDataForTextBoxes();
        Contact init_contact=db.getSavedRecordForSelectedDate(Contact.selectedDate);
        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();


        //Set default saved CustomerNames(Created by user) int textViews
        try {
//            CustomerNames defaultCustNames=new CustomerNames();
            setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show
            //Log.d("dbsvm","Successfully fetched default customer names in InsertDbData");
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to set default fetched customer names in InsertDbData.java " +
                    "\nError : "+e.getMessage().toString());
        }

        //Set default values in text Boxes to update
        setDefaultValuesIntoTextBoxes();


        GetAlerts ga=new GetAlerts();



        Button updateData=(Button) findViewById(R.id.updateDataInDb);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                try {
                    Contact updatedRecord=updateContactDataFromTextBoxes();
//                    textViewDate.setText(Contact.selectedDate);//checking object independent selectedDate value
                    MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(UpdateDbData.this);
                    db.updateData(updatedRecord);

                ga.alertDialogBox(UpdateDbData.this, "Date : " + contact.getSelectedDate() + "  record updated", "Record Updated");
                }
                catch (Exception e)
                {
                    ga.alertDialogBox(UpdateDbData.this, "Unable to update data for "+"Date : " + contact.getSelectedDate() , "Unable to update");

                }

            }
        });

        Button viewData=(Button) findViewById(R.id.viewDataFromDb);
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                startActivity(new Intent(UpdateDbData.this,TableTotalView.class));

            }
        });

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

@RequiresApi(api = Build.VERSION_CODES.O)
public void setDefaultValuesIntoTextBoxes()
{
    MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(UpdateDbData.this);
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

//    Contact defaultData=db.getDefaultDataForTextBoxes();
    Contact defaultData=db.getSavedRecordForSelectedDate(Contact.selectedDate);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact updateContactDataFromTextBoxes() {

        GetAlerts ga=new GetAlerts();
        Contact newContact = new Contact();

        //Get typed data from texboxes into string and insert into db table
        try {
            TextView etDATE=findViewById(R.id.selectedDateToUpdate);
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


            String sdate = etDATE.getText().toString();
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
//        Contact newContact1=new Contact(sdate,Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc2),Double.parseDouble(sc3),Double.parseDouble(sc1),Double.parseDouble(sc14),Double.parseDouble(sc15));

//        EditText edate=findViewById(R.id.editTextDate);
//        newContact.setDATE("24-08-2021");//All db entries updated to same date
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


        } catch (Exception e) {
            ga.alertDialogBox(UpdateDbData.this,"Unable to create " +
                    "contact for inserting\n" +
                    "Error : "+e.getMessage().toString(),"Unable to create contact");

        }
        return newContact;
    }
}
