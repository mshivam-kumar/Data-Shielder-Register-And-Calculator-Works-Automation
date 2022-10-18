package com.datahiveshield.mydata.mydairy.khata.files;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.model.IndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;
import java.util.List;

public class TableTotalViewIndividualPersonData extends AppCompatActivity {
    //    public TableLayout table;//To store database table data and used to show table data on screen
    public static TableLayout table;//Made static to use in GeneratePdf.java to generate pdf of data
    //On call method tableView and returning table , null pointer exception is coming
    EditText etUnit;
    EditText etRate;

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_total_view_individual_person_data);

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


        table = (TableLayout) findViewById(R.id.viewForsetupUpdateInTable);

//            Params.TABLE_NAME= CustomerGroupTableName.customerGroupTable;
//            Params.DEFAULT_DATA_TABLE_NAME="default_value_"+CustomerGroupTableName.customerGroupTable;
//            Params.DEFAULT_CUSTOMER_NAMES_TABLE="default_customers_"+CustomerGroupTableName.customerGroupTable;


        MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(TableTotalViewIndividualPersonData.this);
        //Initialize defualtValues of assoiciated Customer person into textboxes
        Contact init_contact=db.getDefaultDataForTextBoxes();
        CustomerNames defaultCustNames=db.getDefaultCustomerDataForTextBoxes();



//            Contact defaultData=db.getDefaultDataForTextBoxes();

//            etMilkRate.setText(defaultData.getMilk_rate()+"");
        //No need to fetch data here as data is static,
        // already fetched in MainActivity



//            contact.setMilk_rate(Double.parseDouble(etMilkRate.getText().toString()));
        tableView(db);//show cost with default rate value


    }
    //    public void tableView(MyDbHandlerIndividualPersonConfigs db)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tableView(MyDbHandlerIndividualPersonConfigs db)

    {
        try {
//            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(com.easyway2in.sqlitedb.TableView.this);
            table.removeAllViews();//To update new costs at same postion
            ArrayList<String> tempdate = new ArrayList<>();
            ArrayList<String> tempc1 = new ArrayList<>();
            ArrayList<String> tempc2 = new ArrayList<>();
            ArrayList<String> tempc3 = new ArrayList<>();
//            ArrayList<String> paid = new ArrayList<>();
//            ArrayList<String> notPaid = new ArrayList<>();
                    String tDATE;
            double tC1=0,sC1 = 0, paidC = 0, notPaidC = 0, sC4 = 0, sC5 = 0, sC6 = 0, sC7 = 0, sC8 = 0, sC9 = 0, sC10 = 0, sC11 = 0, sC12 = 0, sC13 = 0, sC14 = 0, sC15 = 0;
            String tC2 = "", tC3 = "";
            double todayTotal = 0,todayTotalRs=0, todayTotalMonth = 0;
            //Get all contacts
            List<IndividualPersonData> allContacts = db.getAllIndividualPersonDataSavedRecords();//fetch all saved records
            for (IndividualPersonData contact : allContacts) {
//                    temp.add("Id: "+contact.getId()+"\nName: "+contact.getName()+
//                    "\nPhone Number: "+contact.getPhoneNumber());
//                    sum.add(contact.getC1());
                tDATE=contact.getDATE();
                tC1 = contact.getPERSON_DATA();
                tC2 = contact.getStatus();
                tC3 = contact.getADD_NOTE();
                //handling todays total on table view
//                todayTotalMonth += todayTotal;//updating monthly total milk
//                    Update individual user milk sum
                if(tC2.equals("Paid"))
                {
                    paidC+=tC1;
                }
                else
                {
                    notPaidC+=tC1;
                }
//                sC1 += tC1;

                tempdate.add(contact.getDATE());
//                tempdate.add(Contact.rate+"");
//                tempdate.add(tDATE);
                tempc1.add("           " + tC1 + "");
                tempc2.add("         " + tC2 + "");
                tempc3.add("           " + tC3 + "");

//            temp.add("Name: "+contact.getName());
//            temp.add("Phone Number: "+contact.getPhoneNumber());
//                //Log.d("dbsvm", "Id : " + contact.getC1() + "\n" +
//                        "Name : " + contact.getDATE() + "\n");

            }
//            table = (TableLayout) findViewById(R.id.myTable);//declaring outside function
            //inorder to increase code reusability in other classes


//            Collections.sort(tempdate);//sort data in ascending order with date
            //Add column headings
            TableRow hrow = new TableRow(this);

            String dayNum="S. No.   ";
            String hdate= "Date";
            String hc1 ="       "+"Rupees";
            String hc2 = "       "+"Status";
            //Below line hc3 is to wide to give space to show long add note string
            String hc3 = "         "+"Additional Note                                                                                                                                                                                                                                               ";


            TextView htvdayNum = new TextView(this);
            htvdayNum.setTextSize(15);
            htvdayNum.setTextColor(this.getResources().getColor(R.color.black));
            htvdayNum.setText(dayNum);
//
            TextView htvdate = new TextView(this);
            htvdate.setTextSize(18);
//                htv1.setLinkTextColor();
            htvdate.setTextColor(this.getResources().getColor(R.color.black));
            htvdate.setText(hdate);

            TextView htv1 = new TextView(this);
            htv1.setTextSize(18);
            htv1.setTextColor(this.getResources().getColor(R.color.black));
            htv1.setText(hc1);

            TextView htv2 = new TextView(this);
            htv2.setTextSize(18);
            htv2.setTextColor(this.getResources().getColor(R.color.black));
            htv2.setText(hc2);

            TextView htv3 = new TextView(this);
            htv3.setTextSize(18);
            htv3.setTextColor(this.getResources().getColor(R.color.black));
            htv3.setText(hc3);



            hrow.addView(htvdayNum);
            hrow.addView(htvdate);
            hrow.addView(htv1);
            hrow.addView(htv2);
            hrow.addView(htv3);
            table.addView(hrow);

            //Log.d("dbsvm",tempdate.size()+"\n");            //Log.d("dbsvm",tempdate.size()+"\n");

            for (int i = 0; i < tempdate.size(); i++) {
                //Log.d("dbsvm",tempdate.get(i)+"\n");
                TableRow row = new TableRow(this);
                String date = tempdate.get(i);
                String c1 = tempc1.get(i);
                String c2 = tempc2.get(i);
                String c3 = tempc3.get(i);

                TextView tvDayNum = new TextView(this);
                tvDayNum.setText(i+1+"");

                TextView tvdate = new TextView(this);
                tvdate.setText(date);
                TextView tv1 = new TextView(this);
                tv1.setText(c1);
                TextView tv2 = new TextView(this);
                tv2.setText(c2);
                TextView tv3 = new TextView(this);
                tv3.setText(c3);

                row.addView(tvDayNum);
                row.addView(tvdate);
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);

                table.addView(row);
            }
            //Milk per ltr rate to calculate total cost per user
            //Take input from user in text box
            double rate;
//            rate = 43.00;//temprary
            rate=Contact.Rate;

            //Add total footer row
            String addToCreateSpaceBefore="   ";
            String ftotal_head = "Total(Not Paid)";

            String ftotCostC1 = "         " + notPaidC ;



            TableRow frow = new TableRow(this);


            TextView faddToCreateSpaceBefore=new TextView(this);
            faddToCreateSpaceBefore.setText(addToCreateSpaceBefore);


            TextView ftvhead = new TextView(this);
            ftvhead.setTextSize(18);
            ftvhead.setTextColor(this.getResources().getColor(R.color.black));
            ftvhead.setText(ftotal_head);


            TextView ftv1 = new TextView(this);
            ftv1.setTextSize(18);
            ftv1.setTextColor(this.getResources().getColor(R.color.black));
            ftv1.setText(ftotCostC1);




            frow.addView(faddToCreateSpaceBefore);
            frow.addView(ftvhead);
            frow.addView(ftv1);

            table.addView(frow);//first frow added

            //Adding frow2 for total cost(rs)















            String ftotal_head2 = "Total(Paid)";
            String ftotCostC12a="         " + paidC;

            TableRow frow2 = new TableRow(this);

            TextView faddToCreateSpaceBefore2=new TextView(this);
            faddToCreateSpaceBefore.setText(addToCreateSpaceBefore);

            TextView ftvhead2 = new TextView(this);
            ftvhead2.setTextSize(18);
            ftvhead2.setTextColor(this.getResources().getColor(R.color.black));
            ftvhead2.setText(ftotal_head2);


            TextView ftv12a = new TextView(this);
            ftv12a.setTextSize(18);
            ftv12a.setTextColor(this.getResources().getColor(R.color.black));
            ftv12a.setText(ftotCostC12a);



//            frow2.addView(faddToCreateSpaceBefore);//Add space before last two rows, this initialised
            // for frow above, and used here as purpose are same
//            frow2.addView(faddToCreateSpaceBefore);
            frow2.addView(faddToCreateSpaceBefore2);
            frow2.addView(ftvhead2);
            frow2.addView(ftv12a);

            table.addView(frow2);//second frow added



        } catch (Exception e) {
            //Log.d("svm", e.getMessage().toString());
//                textView.setText(e.getMessage().toString());
        }
        //Log.d("dbsvm","Total Table rows: "+table.getChildCount()+"");
//        return table;//To convert into pdf
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




