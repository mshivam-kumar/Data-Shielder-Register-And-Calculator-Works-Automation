package com.datahiveshield.mydata.mydairy.khata.files;





import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

public class GenerateIndividualPersonDataPdf<table> extends AppCompatActivity{
    //    private String m_Text = "";
    public static TableLayout table;

    EditText result;
    String myFilePath;
    File myFile;
    int i=2;

    Bitmap bmp;
    public String  styledAppNameToPutInPdf="";
    public Typeface myFont;

    private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();



    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_pdf);

        //Set back button to go to previous activity
        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.fileNameUserInput).requestFocus();

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
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/GloriaHallelujah-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Meddon-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/RuslanDisplay-Regular.ttf");

            myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");

//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Monofett-Regular.ttf");//3d text
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/TradeWinds-Regular.ttf");
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
            styledAppNameToPutInPdf=appName.getText().toString();
        }catch (Exception e)
        {
            //Log.d("dbsvm","unable to change font of app name in HOME");
        }

//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        result = findViewById(R.id.result);
        ;//Call below user defined method to give permission if not given

        TextView groupAccessed=findViewById(R.id.groupAccessed);
        TextView dataAccessed=findViewById(R.id.dataAccessed);
        groupAccessed.setText("Person Accessed : "+Params.groupNameToAddBeforeDefault_group_TableNamesReceived );
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );
        table=new TableLayout(GenerateIndividualPersonDataPdf.this);//Initialised to overcome null pointer exception while using table
        Button generate = findViewById(R.id.generatePdfButton);
        EditText input=findViewById(R.id.fileNameUserInput);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                createMyPDF();
//                    //Log.d("dbsvm","Inside generate method");
//                GetAlerts ga=new GetAlerts();
//                ga.alertDialogBox(GenerateIndividualPersonDataPdf.GenerateIndividualPersonDataPdf.this,"generate button pressed","prssed");
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createMyPDF() {

        //Run TableTotal View with Total View Button for temporary reason as
        //Table is static , so we will acess it after running there
        //File name should be entered to save pdf
        GetAlerts ga = new GetAlerts();
        EditText userFileNameInput = findViewById(R.id.fileNameUserInput);
        String userInputFileName = userFileNameInput.getText().toString();
        boolean falgValidDate = true;
        if (userInputFileName.length() == 0)//If file name not entered then alert the user to enter
        {
            falgValidDate = false;
            ga.alertDialogBox(GenerateIndividualPersonDataPdf.this, "Please enter your file name in box. " +
                    "For ease save file name as 'month year' format \nEx: 'Jan 2021'", "Empty file name");

        }


        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(5000, 1600, 2).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

//        EditText myEditText=findViewById(R.id.fileNameIUserInput);

        Paint myPaint = new Paint();
//        String myString = myEditText.getText().toString();
        int x = 10, y = 25;


            MyDbHandlerIndividualPersonConfigs db = new MyDbHandlerIndividualPersonConfigs(GenerateIndividualPersonDataPdf.this);
//        TableLayout tableReceiverFromTableTotalView=tTView.tableView(db);
            TableLayout layout = null;
//            layout=tTView.tableView(db);
//            int i=1;
            String dateHead = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
//                tTView.tableView(db);//No it should not be called from here ,from here static variable is null
                    //Try to make table variable static inside method
//                layout = tTView.tableView(db);
//                    layout=TableTotalView.table;
                    tableView(db);
                    layout = GenerateIndividualPersonDataPdf.table;//Initilized all data in static GenerateIndividualPersonDataPdf.table variable
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

            String appAdvertise = "Data stored and generated by app : ";
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            myPaint.setTextSize(25f);
            myPage.getCanvas().drawText(appAdvertise, 300, 25, myPaint);
            myPaint.setTextSize(32f);
            myPaint.setTypeface(Typeface.create(myFont, Typeface.BOLD));
//            myPaint.setTypeface(Typeface.DEFAULT_BOLD)

            myPage.getCanvas().drawText(styledAppNameToPutInPdf, 370, 71, myPaint);
            myPage.getCanvas().drawBitmap(bmp, 607, 12, myPaint);//draw image
            myPaint.setTextSize(22f);
            myPaint.setTypeface(Typeface.DEFAULT);
//            y+=100;

//            SpannableString s=new SpannableString("Download Data Shielder App");
//            s.setSpan(new URLSpan("http://www.developer.android.com"), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            myPage.getCanvas().drawText(s.toString(),x+70,y,myPaint);
            y += 160;
            x = 10;
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
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                myPaint.setTextSize(50f);
                x += 57;
                String groupName = "Person Name : " + Params.groupNameToAddBeforeDefault_group_TableNamesReceived;
                myPage.getCanvas().drawText(groupName, x, y, myPaint);
//                x += myPaint.descent() - myPaint.ascent() + 50;

//                x = 10;//Updating x=10, not to disturb below postions
//                //Writing pdf generating date in first line of pdf
//                x += 340;

                x = 840;
                int y1 = 65;

//                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
//                myPaint.setTextSize(50f);
                myPage.getCanvas().drawText(dateHead, x + 150, y1, myPaint);
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

                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                //Name table headings
                for (int x1 = 0; x1 < row.getChildCount(); x1++) {

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

            y += 15;
            //Data value
            for (int i = 1; i < layout.getChildCount() - 2; i++) {
                View child = layout.getChildAt(i);

                if (child instanceof TableRow) {
                    TableRow row = (TableRow) child;
                    x = 16;
                    y += 31;
                    for (int x1 = 0; x1 < row.getChildCount(); x1++) {


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


            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            y += 8;
            View childl1 = layout.getChildAt(layout.getChildCount() - 2);

            if (childl1 instanceof TableRow) {
                TableRow row = (TableRow) childl1;
                x = 16;
                y += 31;
                for (int x1 = 0; x1 < row.getChildCount(); x1++) {

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
                for (int x1 = 0; x1 < row.getChildCount(); x1++) {

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


//        for (String line:myString.split("\n")){
//            myPage.getCanvas().drawText(line, x, y, myPaint);
//            y+=myPaint.descent()-myPaint.ascent();
//        }
            try {
                myPdfDocument.finishPage(myPage);

            } catch (Exception e) {
                Log.d("dbsvm", "Inside GenerateIndividualPersonDataPdf.java Unable ot add myPage canvas to myPdfDocument" +
                        e.getMessage().toString());
            }
//        String userInputFileName=ga.PromptUserForInput(GenerateIndividualPersonDataPdf.GenerateIndividualPersonDataPdf.this);

            //App  Files saved by user will be stored in Data Shielder folder inside internal storage
//        File mediaStorageDir = new File(GenerateIndividualPersonDataPdf.this.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath(), "Data Shielder/" + Params.DB_NAME);
//        File mediaStorageDir = new File(GenerateIndividualPersonDataPdf.this.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath(), "Data Shielder/"+Params.CURRENT_GROUP_DB_NAME+"/" + Params.DB_NAME);
//        String str=GenerateIndividualPersonDataPdf.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//        String str=GenerateIndividualPersonDataPdf.this.getExternalMediaDirs();

//        Log.d("dbsvm","Downloads location : "+str);

//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("App", "failed to create directory");
//            }
//        }

//        String userInputFileName=ga.promtUserForInput();/*Unable to use GenerateIndividualPersonDataPdf.this method
            //of GetAlerts class, as GenerateIndividualPersonDataPdf.this is working very slow, when its box prompts
            //for user input, till then controller goes ahead and execute the program further
            //which is creating problem in decision making , that whether file name is given by user or not
            //So further code runs for empty file name condition as alerts box prompting
            //very late


            //Unable to save pdf in app created folder , it is saving image type, which is not opening
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,userInputFileName + ".pdf");
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"application/pdf");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Data Shielder");
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            contentValues.put(MediaStore.Images.Media.IS_PENDING, true);
//        }
//
//        ContentResolver contentResolver = GenerateIndividualPersonDataPdf.this.getContentResolver();
//
//        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues);
//
//
//        if (uri != null) {
//
//            //convert the pic as byte array to bitmap
//
//            try {
//                FileOutputStream fos=(FileOutputStream) contentResolver.openOutputStream(Objects.requireNonNull(uri));
//            } catch (Exception e) {
//
//
//            }
//        }


            //Saving pdf file to app specific folder in Android/data/packageName/Data Shielder ..
            String storage_folder = "/" + Params.AppName + "/" + Params.DB_NAME;

            String destPath = GenerateIndividualPersonDataPdf.this.getExternalFilesDir(null).getAbsolutePath();
            Log.d("dbsvm", destPath);
            File f = new File(destPath, storage_folder);
            if (!f.exists()) {
                f.mkdirs();
            }

//        String a=GenerateIndividualPersonDataPdf.this.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + Params.AppName + "/" + Params.DB_NAME;
//        File af=new File(a);
//        try {
//            if(!af.exists())
//            {
//                af.mkdirs();
//            }
//        }catch (Exception e)
//        {
//            Log.d("dbsvm","unable to create directory "+e.getMessage());
//        }
//        String myFilePath = context.getExternalFilesDir(null).getAbsolutePath().getPath() +"/Data Shielder/"+Params.TABLE_NAME+"/"+userInputFileName+".pdf";
//        myFilePath = GenerateIndividualPersonDataPdf.this.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + Params.AppName + "/" + Params.DB_NAME + "/" + userInputFileName + ".pdf";
            myFilePath = GenerateIndividualPersonDataPdf.this.getExternalFilesDir(null).getAbsolutePath() + "/" + Params.AppName + "/" + Params.DB_NAME + "/" + userInputFileName + ".pdf";
//        String myFilePath = context.getExternalFilesDir(null).getAbsolutePath().getPath() +"/"+Params.AppName+"/"+Params.TABLE_NAME+"/"+Params.SubTableNameWithMonthAndYear+"/"+userInputFileName+".pdf";


            myFile = new File(myFilePath);

            if (myFile.exists()) {
//
                String msg = "Please enter some other name.\n\n" +
                        "There is already a file with the same  name in this location.";
                ga.alertDialogBox(GenerateIndividualPersonDataPdf.this, msg, "Enter new name");
            } else {
                try {

                    if (falgValidDate) {
                        myPdfDocument.writeTo(new FileOutputStream(myFile));
                        ga.alertDialogBox(GenerateIndividualPersonDataPdf.this, "Pdf is saved with file name [" + userInputFileName + ".pdf]" + " in [Android]/[data]/[com.datahive...iry.khata.files]/[Data Shielder]/[" + Params.DB_NAME + "] folder of your internal storage ", "Pdf Saved Successfully");

                        //Log.d("dbsvm", "pdf generated");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ga.alertDialogBox(GenerateIndividualPersonDataPdf.this, "Unable to save pdf\n" +
                            "Error : " + e.getMessage().toString(), "Unable to save pdf");
//            myEditText.setText("ERROR");
                    Log.d("dbsvm", "Unable to generate pdf" +
                            "Error : " + e.getMessage().toString());
                }
            }
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
