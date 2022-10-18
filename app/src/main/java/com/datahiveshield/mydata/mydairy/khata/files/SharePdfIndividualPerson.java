package com.datahiveshield.mydata.mydairy.khata.files;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.IndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SharePdfIndividualPerson extends AppCompatActivity {
    public PdfDocument myPdfDocument;
    public static TableLayout table;

    String selectedName = "";
    public static String[] str;
    public int i;
    public int sizeOfReceivedArrayList = 0;
    public String noGroupFound;
    ArrayList<String> customerDatabasesArrayList = new ArrayList<>();
    private String selectedNameToSharePdf="";

    Bitmap bmp;
    public String  styledAppNameToPutInPdf="";
    public Typeface myFont;
    public static String  fileNameToSendPdf="";
    SharedPreferences spStoreSavedPdfFileName;
    private SharedPreferences.Editor controlSpStoreSavedPdfFileName;
    public SharedPreferences receiveSpSavedPdfFileName;

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();

    public Context context;

    //    protected void onRestart() {
//        super.onRestart();
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);
//    }
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_pdf_individual_person_data);
        table=new TableLayout(SharePdfIndividualPerson.this);

        context=SharePdfIndividualPerson.this;

        TextView dataAccessed=findViewById(R.id.infoHeading);
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );

        spStoreSavedPdfFileName= getSharedPreferences("spStoreSavedPdfFileName",MODE_PRIVATE);
        controlSpStoreSavedPdfFileName= spStoreSavedPdfFileName.edit();
//        controlSpStoreSavedPdfFileName.putString("fileName","demo");

        receiveSpSavedPdfFileName=getSharedPreferences("spStoreSavedPdfFileName",MODE_APPEND);
        String singleNameToDeletePdf=receiveSpSavedPdfFileName.getString("fileSingleName","");
        String allNameToDeletePdf=receiveSpSavedPdfFileName.getString("fileAllName","");
//        Log.d("dbsvm",singleNameToDeletePdf);


        try {
//            String pathSingleName = context.getExternalFilesDir(null).getAbsolutePath() +"/"+ singleNameToDeletePdf;
            String pathSingleName=context.getExternalFilesDir(null).getAbsolutePath()+ "/" + Params.AppName +"/"+singleNameToDeletePdf;

//            String pathAllName = context.getExternalFilesDir(null).getAbsolutePath() +"/"+ AllNameToDeletePdf;
            String pathAllName=context.getExternalFilesDir(null).getAbsolutePath()+ "/" + Params.AppName +"/"+allNameToDeletePdf;

//            Log.d("dbsvm","pathSingleName: "+pathSingleName);
//            Log.d("dbsvm","pathAllName : "+pathAllName);
            //Log.d("dbsvm", "/nshare pdf file Path to delete : " + path);

//        File dir = new File(storage_folder);
            File fileSingleName = new File(pathSingleName);
            File fileAllName = new File(pathAllName);
            if(fileSingleName.exists())
            {
                fileSingleName.delete();
                Log.d("dbsvm", "/n"+fileSingleName+" pdf file deleted");

            }
            if(fileAllName.exists())
            {
                fileAllName.delete();
                Log.d("dbsvm", "/n"+fileAllName+" pdf file deleted " );
            }
        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to delete share pdf file\n" +
                    "Error : "+e.getMessage().toString());
        }


        try {

            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_ptemp);
            bmp = Bitmap.createScaledBitmap(bmp, 110, 100, false);
        }catch (Exception e)
        {
            //Log.d("dbsvm","Bitmap Error : "+e.getMessage().toString());
        }


        TextView appName=findViewById(R.id.appName);
        try
        {
            myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
            //App name initialised to put into pdf
            styledAppNameToPutInPdf=appName.getText().toString();
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }


        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//Setting custome toolbar back button to go back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });


//        MyDbHandlerIndividualPersonConfigs db=new MyDbHandlerIndividualPersonConfigs(SharePdfIndividualPerson.this);
        MyDbHandlerIndividualPersonConfigs db=new MyDbHandlerIndividualPersonConfigs(SharePdfIndividualPerson.this);


        //Get the current names had by user from database
//        CustomerNames defaultCustNames= null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            defaultCustNames = db.getDefaultCustomerDataForTextBoxes();
//        }

        //Set the current names had by user to text views to show to user
//        setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show






        //Increase font size of first element of number picker, without touching
        //as others will be affected after touch





        GetAlerts ga=new GetAlerts();
        TextView showSelectedName=findViewById(R.id.showSelectedNameToSharePdf);

        Button shareAllNamesPdf=findViewById(R.id.share_all_names_pdf);
        shareAllNamesPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;


                createAllNamesPDF();

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

//                File pdfFile = new File(Environment.getExternalStoragePublicDirectory
//                        (Environment.DIRECTORY_DOWNLOADS), "Your file");
                try {

//                        File pdfFile = new File(context.getExternalFilesDir(null).getAbsolutePath(),fileNameToSendPdf);
                    File pdfFile= new File(context.getExternalFilesDir(null).getAbsolutePath()+ "/" + Params.AppName +"/"+fileNameToSendPdf);


                    Uri uri = Uri.fromFile(pdfFile);

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("application/pdf");

                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                    //Can not implement here as it deletes the file just after sharing invocation
                    //so file will become null to share, we can not share that (no file)
//                    try {
//
//                        String path = context.getExternalFilesDir(null).getAbsolutePath() + "/share.pdf";
//                        //Log.d("dbsvm", "/nshare pdf file Path to delete : " + path);
//
////        File dir = new File(storage_folder);
//                        File file = new File(path);
//                        file.delete();
//                    }
//                    catch (Exception e)
//                    {
//                        //Log.d("dbsvm","Unable to delete share pdf file\n" +
//                                "Error : "+e.getMessage().toString());
//                    }

                } catch (Exception e) {
                    Log.d("dbsvm", "Error in sharing \n" +
                            "Error : " + e.getMessage().toString());
                }


            }

        });
           }

    public void createAllNamesPDF() {

        //Run TableTotal View with Total View Button for temporary reason as
        //Table is static , so we will acess it after running there
        //File name should be entered to save pdf
        GetAlerts ga = new GetAlerts();
        boolean falgValidDate = true;



        myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(3500, 1600, 2).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

//        EditText myEditText=findViewById(R.id.fileNameIUserInput);

        Paint myPaint = new Paint();
//        String myString = myEditText.getText().toString();
        int x = 10, y = 25;
        try {

            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(SharePdfIndividualPerson.this);
//        TableLayout tableReceiverFromTableTotalView=tTView.tableView(db);
            TableLayout layout = null;
//            layout=tTView.tableView(db);
//            int i=1;
            String  dateHead = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
//                tTView.tableView(db);//No it should not be called from here ,from here static variable is null
                    //Try to make table variable static inside method
//                layout = tTView.tableView(db);
//                    layout=TableTotalView.table;
                    tableView(db);
                    layout = SharePdfIndividualPerson.table;//Initilized all data in static SharePdfIndividualPerson.table variable
//                    using tableView method defined below

                    //Get Unit and Rate from Contact to write in database

                    //Log.d("dbsvm", "TableView method called from TableTotalView.java");
                } catch (Exception e) {
                    //Log.d("dbsvm", "Unable to call Table View method\nError : " + e.getMessage().toString());
                }
            }

//        TableLayout layout = (TableLayout) findViewById(R.id.IdTable);

            //Writing Unit and Rate to pdf in above heading
//            x += myPaint.descent() - myPaint.ascent() + 50;

            String appAdvertise="Data stored and generated by app : ";
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            myPaint.setTextSize(25f);
            myPage.getCanvas().drawText(appAdvertise,300,25,myPaint);
            myPaint.setTextSize(32f);
            myPaint.setTypeface(Typeface.create(myFont,Typeface.BOLD));
//            myPaint.setTypeface(Typeface.DEFAULT_BOLD)

            myPage.getCanvas().drawText(styledAppNameToPutInPdf, 370, 71, myPaint);
            myPage.getCanvas().drawBitmap(bmp,607,12,myPaint);//draw image
            myPaint.setTextSize(22f);
            myPaint.setTypeface(Typeface.DEFAULT);
//            y+=100;

//            SpannableString s=new SpannableString("Download Data Shielder App");
//            s.setSpan(new URLSpan("http://www.developer.android.com"), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            myPage.getCanvas().drawText(s.toString(),x+70,y,myPaint);
            y+=160;
            x=10;
            //As on 0  index S.No. is present and on 1 date is there , so increase index of name by 2



//        TableLayout layout = (TableLayout) findViewById(R.id.IdTable);

            //Writing Unit and Rate to pdf in above heading
//            x += myPaint.descent() - myPaint.ascent() + 50;

//            Adding date as pdf table heading
            View childOnlyDate0 = layout.getChildAt(1);
            if (childOnlyDate0 instanceof TableRow) {
                TableRow rowOnlyDate = (TableRow) childOnlyDate0;
                TextView view = (TextView) rowOnlyDate.getChildAt(1);
                String myString = view.getText().toString();

                dateHead = Params.CURRENT_GROUP_CONFIG_DB_NAME;


                //Writing person Name line of pdf
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                myPaint.setTextSize(50f);
                x += 57;
                String groupName = "Person Name : " + Params.groupNameToAddBeforeDefault_group_TableNamesReceived;
                myPage.getCanvas().drawText(groupName, x, y, myPaint);
//                x += myPaint.descent() - myPaint.ascent() + 50;

//                x = 10;//Updating x=10, not to disturb below postions
//                //Writing pdf generating date in first line of pdf
//                x += 340;

                x = 840;
                int y1=65;

//                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
//                myPaint.setTextSize(50f);
                myPage.getCanvas().drawText(dateHead, x+150, y1, myPaint);
                x += myPaint.descent() - myPaint.ascent() + 50;
            }

            myPaint.setTypeface(Typeface.DEFAULT);
            myPaint.setTextSize(22f);

//            Writing unit and Rate in second line of pdf
//            x = 60;
//            y += 40;
//            myPage.getCanvas().drawText(unit, x + 10, y, myPaint);
//            y += myPaint.descent() - myPaint.ascent();
//            x += myPaint.descent() - myPaint.ascent() + 50;
//            myPage.getCanvas().drawText(rate, x + 56, y, myPaint);
//            x += myPaint.descent() - myPaint.ascent() + 50;


            //Writing first row header of customer names, written separately
            //due to the difference in length of Customers names and unit quantity
            View child0 = layout.getChildAt(0);

            if (child0 instanceof TableRow) {
                TableRow row = (TableRow) child0;
                x = 16;
//                y += 25;
                y += 68;

                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));

                //Name table headings
                for (int x1 = 0; x1 <row.getChildCount(); x1++)
                {

                    TextView view = (TextView) row.getChildAt(x1);
                    String myString = view.getText().toString();
//                    //Log.d("dbsvm",myString);
                    for (String line : myString.split("\n")) {
                        x += 16;//best , check and see how effects
//                        x+=5
                        myPage.getCanvas().drawText(line, x, y, myPaint);
//                            y += myPaint.descent() - myPaint.ascent();
                        x += myPaint.descent() - myPaint.ascent() + 210;
                    }
                }
            }

            myPaint.setTypeface(Typeface.DEFAULT);

            y+=15;
            //Data value
            for (int i = 1; i < layout.getChildCount() - 2; i++) {
                View child = layout.getChildAt(i);

                if (child instanceof TableRow) {
                    TableRow row = (TableRow) child;
                    x = 16;
                    y += 31;
                    for (int x1 = 0; x1 <row.getChildCount(); x1++) {


                        TextView view = (TextView) row.getChildAt(x1);
                        String myString = view.getText().toString();
                        for (String line : myString.split("\n")) {
                            x += 16;
                            myPage.getCanvas().drawText(line, x, y, myPaint);
//                            y += myPaint.descent() - myPaint.ascent();
                            x += myPaint.descent() - myPaint.ascent() + 210;
                        }


//                    view.setEnabled(false);
//                        String line=myString.split("\n")[row.getChildCount()-3];
//                        myPage.getCanvas().drawText(line, x, y, myPaint);
                    }
//                            y += myPaint.descent() - myPaint.ascent();
//                        y += myPaint.descent()+5 - myPaint.ascent();
//                    y+=15;
                }
//                         myPage.getCanvas().drawText("\n", x, y, myPaint);
//                        myPdfDocument.finishPage(myPage);

            }

            //Inserting last two rows to create pdf, as they have total data


            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            y+=8;
            View childl1 = layout.getChildAt(layout.getChildCount() - 2);

            if (childl1 instanceof TableRow) {
                TableRow row = (TableRow) childl1;
                x = 16;
                y += 31;
                for (int x1 = 0; x1 <row.getChildCount(); x1++) {

                    TextView view = (TextView) row.getChildAt(x1);
                    String myString = view.getText().toString();
                    for (String line : myString.split("\n")) {
                        x += 16;//best , check and see how effects
//                        x+=5
                        myPage.getCanvas().drawText(line, x, y, myPaint);
//                            y += myPaint.descent() - myPaint.ascent();
                        x += myPaint.descent() - myPaint.ascent() + 210;
                    }
                }
            }

            View childl2 = layout.getChildAt(layout.getChildCount() - 1);

            if (childl2 instanceof TableRow) {
                TableRow row = (TableRow) childl2;
                x = 16;
                y += 31;
                for (int x1 = 0; x1 <row.getChildCount(); x1++) {

                    TextView view = (TextView) row.getChildAt(x1);
                    String myString = view.getText().toString();
                    for (String line : myString.split("\n")) {
                        x += 16;//best , check and see how effects
//                        x+=5
                        myPage.getCanvas().drawText(line, x, y, myPaint);
//                            y += myPaint.descent() - myPaint.ascent();
                        x += myPaint.descent() - myPaint.ascent() + 210;
                    }
                }
            }

            try {
                myPdfDocument.finishPage(myPage);


            } catch (Exception e) {
                Log.d("dbsvm", "Inside SharePdfIndividualPerson.java Unable ot add myPage canvas to myPdfDocument" +
                        e.getMessage().toString());
            }

//           File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath(),"share.pdf");

            fileNameToSendPdf=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"-"+Params.CURRENT_GROUP_CONFIG_DB_NAME+".pdf";
            Log.d("dbsvm","Current person "+Params.CURRENT_GROUP_DB_NAME);
            controlSpStoreSavedPdfFileName.putString("fileAllName",fileNameToSendPdf);
            controlSpStoreSavedPdfFileName.commit();
//           File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath(),"share.pdf");
            String storage_folder = "/" + Params.AppName;

            String destPath =SharePdfIndividualPerson.this.getExternalFilesDir(null).getAbsolutePath();
            Log.d("dbsvm",destPath);
            File f = new File(destPath, storage_folder);
            if (!f.exists()) {
                f.mkdirs();
            }

            File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath()+ "/" + Params.AppName +"/"+fileNameToSendPdf);

//            File myFile= new File(context.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath(),fileNameToSendPdf);
            myPdfDocument.writeTo(new FileOutputStream(myFile));


        } catch (Exception e) {
            Log.d("dbsvm", "Inside SharePdfIndividualPerson.java Unable to convert TableLayout to pdf" +
                    "\nError : " + e.getMessage().toString());
        }
//        for (String line:myString.split("\n")){
//            myPage.getCanvas().drawText(line, x, y, myPaint);
//            y+=myPaint.descent()-myPaint.ascent();
//        }



        myPdfDocument.close();
    }


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
                tempc1.add("" + tC1 + "");
                tempc2.add("" + tC2 + "");
                tempc3.add("" + tC3 + "");

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
            String hc1 =""+"Rupees";
            String hc2 = ""+"Status";
            String hc3 = ""+"Additional Note                                                                                               ";


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
            String addToCreateSpaceBefore="";
            String ftotal_head = "Total(Not Paid)";

            String ftotCostC1 = "" + notPaidC ;



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
            String ftotCostC12a="" + paidC;

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


}
