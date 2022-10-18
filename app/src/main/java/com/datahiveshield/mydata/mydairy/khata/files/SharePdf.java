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
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.model.Contact;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SharePdf extends AppCompatActivity {
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
        setContentView(R.layout.share_pdf);
        table=new TableLayout(SharePdf.this);

        context=SharePdf.this;

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
//                Log.d("dbsvm", "/n"+fileSingleName+" pdf file deleted");

            }
            if(fileAllName.exists())
            {
                fileAllName.delete();
//                Log.d("dbsvm", "/n"+fileAllName+" pdf file deleted " );
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


//        MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(SharePdf.this);
        MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(SharePdf.this);


        //Get the current names had by user from database
//        CustomerNames defaultCustNames= null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            defaultCustNames = db.getDefaultCustomerDataForTextBoxes();
//        }

        //Set the current names had by user to text views to show to user
//        setNewCustomerNamesIntoTextViews(defaultCustNames);//While opening activity show


        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToSelectName);

        try {
            showAllCustomerNames();
        }catch (Exception e)
        {
            //Log.d("dbsvm","\nDelete Customer Group Error :"+e.getMessage().toString());
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
                ((EditText) child).setTextSize(26);
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
                selectedName = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                selectedNameToSharePdf=selectedName;
                TextView atShowSelectedName=findViewById(R.id.showSelectedNameToSharePdf);
                atShowSelectedName.setText( "Selected Name : "+selectedName);
            }
        });
//Show first group as default selected, with scrolling
        try {
            selectedName = numberPicker.getDisplayedValues()[0];
            selectedNameToSharePdf=selectedName;
            TextView atShowSelectedName=findViewById(R.id.showSelectedNameToSharePdf);
            atShowSelectedName.setText( "Selected Name : "+selectedName);        }
        catch (Exception e)
        {
            Log.d("dbsvm","Empty number picker\n" +
                    "Error : "+e.getMessage().toString());
        }

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

        Button shareSelectedNamePdf=findViewById(R.id.share_selected_name_pdf);
        shareSelectedNamePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                createSelectedNamePDF(selectedNameToSharePdf);

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

//                File pdfFile = new File(Environment.getExternalStoragePublicDirectory
//                        (Environment.DIRECTORY_DOWNLOADS), "Your file");
                    try {

//                        File pdfFile = new File(context.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath(), fileNameToSendPdf);
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
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(5000, 1600, 2).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

//        EditText myEditText=findViewById(R.id.fileNameIUserInput);

        Paint myPaint = new Paint();
//        String myString = myEditText.getText().toString();
        int x = 10, y = 25;
        try {

            TableTotalView tTView = new TableTotalView();
            MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(SharePdf.this);
//        TableLayout tableReceiverFromTableTotalView=tTView.tableView(db);
            TableLayout layout = null;
//            layout=tTView.tableView(db);
//            int i=1;
            String unit = "", rate = "", dateHead = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
//                tTView.tableView(db);//No it should not be called from here ,from here static variable is null
                    //Try to make table variable static inside method
//                layout = tTView.tableView(db);
//                    layout=TableTotalView.table;
                    tableView(db);
                    layout = SharePdf.table;//Initilized all data in static SharePdf.table variable
//                    using tableView method defined below

                    //Get Unit and Rate from Contact to write in database
                    unit = "Unit : " + Contact.getQUANTITYUNITNAME();
                    rate = "Rate : RS " + Contact.getRate() + " per " + Contact.getQUANTITYUNITNAME();

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


                //Writing Group Name line of pdf
                x += 57;
                String groupName = "Group Name : " + Params.CURRENT_GROUP_DB_NAME;
                myPage.getCanvas().drawText(groupName, x, y, myPaint);
//                x += myPaint.descent() - myPaint.ascent() + 50;

//                x = 10;//Updating x=10, not to disturb below postions
//                //Writing pdf generating date in first line of pdf
//                x += 340;

                x = 945;
                int y1=65;

                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                myPaint.setTextSize(50f);
                myPage.getCanvas().drawText(dateHead, x+150, y1, myPaint);
                x += myPaint.descent() - myPaint.ascent() + 50;
            }

            myPaint.setTypeface(Typeface.DEFAULT);
            myPaint.setTextSize(22f);

//            Writing unit and Rate in second line of pdf
            x = 60;
            y += 40;
            myPage.getCanvas().drawText(unit, x + 10, y, myPaint);
//            y += myPaint.descent() - myPaint.ascent();
            x += myPaint.descent() - myPaint.ascent() + 50;
            myPage.getCanvas().drawText(rate, x + 56, y, myPaint);
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
                Log.d("dbsvm", "Inside SharePdf.java Unable ot add myPage canvas to myPdfDocument" +
                        e.getMessage().toString());
            }

//           File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath(),"share.pdf");

            fileNameToSendPdf=Params.CURRENT_GROUP_DB_NAME+"-"+Params.CURRENT_GROUP_CONFIG_DB_NAME+".pdf";
            controlSpStoreSavedPdfFileName.putString("fileAllName",fileNameToSendPdf);
            controlSpStoreSavedPdfFileName.commit();
//           File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath(),"share.pdf");
            String storage_folder = "/" + Params.AppName;

            String destPath =SharePdf.this.getExternalFilesDir(null).getAbsolutePath();
//            Log.d("dbsvm",destPath);
            File f = new File(destPath, storage_folder);
            if (!f.exists()) {
                f.mkdirs();
            }

            File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath()+ "/" + Params.AppName +"/"+fileNameToSendPdf);

//            File myFile= new File(context.getExternalFilesDir(DIRECTORY_DOWNLOADS).getAbsolutePath(),fileNameToSendPdf);
            myPdfDocument.writeTo(new FileOutputStream(myFile));


        } catch (Exception e) {
            Log.d("dbsvm", "Inside SharePdf.java Unable to convert TableLayout to pdf" +
                    "\nError : " + e.getMessage().toString());
        }
//        for (String line:myString.split("\n")){
//            myPage.getCanvas().drawText(line, x, y, myPaint);
//            y+=myPaint.descent()-myPaint.ascent();
//        }











        myPdfDocument.close();
    }

    public void createSelectedNamePDF(String selectedNameReceived) {

        //Run TableTotal View with Total View Button for temporary reason as
        //Table is static , so we will acess it after running there
        //File name should be entered to save pdf
        GetAlerts ga = new GetAlerts();
        boolean falgValidDate = true;



        myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1000, 1600, 2).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

//        EditText myEditText=findViewById(R.id.fileNameIUserInput);

        Paint myPaint = new Paint();
//        String myString = myEditText.getText().toString();
        int x = 10, y = 25;
        try {

            TableTotalView tTView = new TableTotalView();
            MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(SharePdf.this);
//        TableLayout tableReceiverFromTableTotalView=tTView.tableView(db);
            TableLayout layout = null;
//            layout=tTView.tableView(db);
//            int i=1;
            String unit = "", rate = "", dateHead = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
//                tTView.tableView(db);//No it should not be called from here ,from here static variable is null
                    //Try to make table variable static inside method
//                layout = tTView.tableView(db);
//                    layout=TableTotalView.table;
                    tableView(db);
                    layout = SharePdf.table;//Initilized all data in static SharePdf.table variable
//                    using tableView method defined below

                    //Get Unit and Rate from Contact to write in database
                    unit = "Unit : " + Contact.getQUANTITYUNITNAME();
                    rate = "       Rate : RS " + Contact.getRate() + " per " + Contact.getQUANTITYUNITNAME();

                    //Log.d("dbsvm", "TableView method called from TableTotalView.java");
                } catch (Exception e) {
                    //Log.d("dbsvm", "Unable to call Table View method\nError : " + e.getMessage().toString());
                }
            }



            String appAdvertise="Data stored and generated by app : ";
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            myPaint.setTextSize(25f);
            myPage.getCanvas().drawText(appAdvertise,40,32,myPaint);
            myPaint.setTextSize(32f);
            myPaint.setTypeface(Typeface.create(myFont,Typeface.BOLD));
//            myPaint.setTypeface(Typeface.DEFAULT_BOLD)

            myPage.getCanvas().drawText(styledAppNameToPutInPdf, 110, 82, myPaint);
            myPage.getCanvas().drawBitmap(bmp,353,12,myPaint);//draw image
            myPaint.setTextSize(22f);
            myPaint.setTypeface(Typeface.DEFAULT);
//            y+=100;

//            SpannableString s=new SpannableString("Download Data Shielder App");
//            s.setSpan(new URLSpan("http://www.developer.android.com"), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            myPage.getCanvas().drawText(s.toString(),x+70,y,myPaint);
            y+=160;
            x=10;
            //As on 0  index S.No. is present and on 1 date is there , so increase index of name by 2
            int selectedNameIndex=customerDatabasesArrayList.indexOf(selectedNameReceived)+2;

            //Log.d("dbsvm","SelectedName : "+selectedNameReceived+"\n" +"SelectedNameIndex : "+selectedNameIndex);


//        TableLayout layout = (TableLayout) findViewById(R.id.IdTable);

            //Writing Unit and Rate to pdf in above heading
//            x += myPaint.descent() - myPaint.ascent() + 50;

//            Adding date as pdf table heading
            View childOnlyDate0 = layout.getChildAt(1);
            if (childOnlyDate0 instanceof TableRow) {
                TableRow rowOnlyDate = (TableRow) childOnlyDate0;
                TextView view = (TextView) rowOnlyDate.getChildAt(1);
                String myString = view.getText().toString();
//                dateHead = myString.split("\n")[0];
//
////                String dateHead=Contact.DefaultDate;
//                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    dateHead=formatter.format(Contact.DATE1);//Today's Date
//                }
                //SelectedNameReceived is name of  person/thing   , printed along data configuration name for ease
//                datehead = selectedNameReceived+"  ";
                String selectedNameToShare = selectedNameReceived+"  ";


                //Writing Group Name line of pdf
                x += 57;
                String groupName = "Group Name : " + Params.CURRENT_GROUP_DB_NAME;
                myPage.getCanvas().drawText(groupName, x, y, myPaint);
//                x += myPaint.descent() - myPaint.ascent() + 50;

                x = 10;//Updating x=10, not to disturb below postions
                //Writing pdf generating date in first line of pdf
                x = 530;

//                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
//                myPaint.setTextSize(25f);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                myPaint.setTextSize(31f);
//                myPage.getCanvas().drawText(dateHead, x, y, myPaint);
                myPage.getCanvas().drawText(selectedNameToShare, x, y, myPaint);
                x += myPaint.descent() - myPaint.ascent() + 50;

                x=545;
                int y1=65;
                //Showing configuration in pdf
                dateHead=Params.CURRENT_GROUP_CONFIG_DB_NAME;
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                myPaint.setTextSize(31f);
                myPage.getCanvas().drawText(dateHead, x, y1, myPaint);
            }

                myPaint.setTypeface(Typeface.DEFAULT);
            myPaint.setTextSize(22f);

//            Writing unit and Rate in second line of pdf
            x = 60;
            y += 40;
            myPage.getCanvas().drawText(unit, x + 10, y, myPaint);
//            y += myPaint.descent() - myPaint.ascent();
            x += myPaint.descent() - myPaint.ascent() + 50;
            myPage.getCanvas().drawText(rate, x + 56, y, myPaint);
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
                    if(x1!=0 && x1!=1 && x1!=selectedNameIndex)
                    {
                        continue;
                    }
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

            y+=15;
            myPaint.setTypeface(Typeface.DEFAULT);

            //Data value
            for (int i = 1; i < layout.getChildCount() - 2; i++) {
                View child = layout.getChildAt(i);

                if (child instanceof TableRow) {
                    TableRow row = (TableRow) child;
                    x = 16;
                    y += 31;
                    for (int x1 = 0; x1 <row.getChildCount(); x1++) {

                        if(x1!=0 && x1!=1 && x1!=selectedNameIndex)
                        {
                            continue;
                        }
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
                    if(x1!=0 && x1!=1 && x1!=selectedNameIndex)
                    {
                        continue;
                    }
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
                    if(x1!=0 && x1!=1 && x1!=selectedNameIndex)
                    {
                        continue;
                    }
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
                Log.d("dbsvm", "Inside SharePdf.java Unable ot add myPage canvas to myPdfDocument" +
                        e.getMessage().toString());
            }

//            File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath(),"share.pdf");
            fileNameToSendPdf=Params.CURRENT_GROUP_DB_NAME+"-"+Params.CURRENT_GROUP_CONFIG_DB_NAME+"-"+selectedNameToSharePdf+".pdf";
            controlSpStoreSavedPdfFileName.putString("fileSingleName",fileNameToSendPdf);
            controlSpStoreSavedPdfFileName.commit();
//            Log.d("dbsvm","fileNameToSendPdf: "+fileNameToSendPdf);
            //Saving pdf file to app specific folder in Android/data/packageName/Data Shielder ..
            String storage_folder = "/" + Params.AppName;

            String destPath =SharePdf.this.getExternalFilesDir(null).getAbsolutePath();
//            Log.d("dbsvm",destPath);
            File f = new File(destPath, storage_folder);
            if (!f.exists()) {
                f.mkdirs();
            }

            File myFile= new File(context.getExternalFilesDir(null).getAbsolutePath()+ "/" + Params.AppName +"/"+fileNameToSendPdf);
            myPdfDocument.writeTo(new FileOutputStream(myFile));


        } catch (Exception e) {
            Log.d("dbsvm", "Inside SharePdf.java Unable to convert TableLayout to pdf" +
                    "\nError : " + e.getMessage().toString());
        }
        myPdfDocument.close();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tableView(MyDbHandlerGroupConfigs db)

    {
        try {
//            MyDbHandlerGroupConfigs db = new MyDbHandlerGroupConfigs(com.easyway2in.sqlitedb.TableView.SharePdf.this);
            try {
                table.removeAllViews();//To update new costs at same postion
                //Log.d("dbsvm","Inside SharePdf.java tableView , All table data removed");
            }
            catch (Exception e)
            {
                //Log.d("dbsvm","Inside SharePdf.java tableView ,unable to remove data");

            }
            ArrayList<String> tempdate = new ArrayList<>();
            ArrayList<String> tempc1 = new ArrayList<>();
            ArrayList<String> tempc2 = new ArrayList<>();
            ArrayList<String> tempc3 = new ArrayList<>();
            ArrayList<String> tempc4 = new ArrayList<>();
            ArrayList<String> tempc5 = new ArrayList<>();
            ArrayList<String> tempc6 = new ArrayList<>();
            ArrayList<String> tempc7 = new ArrayList<>();
            ArrayList<String> tempc8 = new ArrayList<>();
            ArrayList<String> tempc9 = new ArrayList<>();
            ArrayList<String> tempc10 = new ArrayList<>();
            ArrayList<String> tempc11 = new ArrayList<>();
            ArrayList<String> tempc12 = new ArrayList<>();
            ArrayList<String> tempc13 = new ArrayList<>();
            ArrayList<String> tempc14 = new ArrayList<>();
            ArrayList<String> tempc15 = new ArrayList<>();
            ArrayList<String> todayTotalArrayList = new ArrayList<>();
            String tDATE;
            double sC1 = 0, sC2 = 0, sC3 = 0, sC4 = 0, sC5 = 0, sC6 = 0, sC7 = 0, sC8 = 0, sC9 = 0, sC10 = 0, sC11 = 0, sC12 = 0, sC13 = 0, sC14 = 0, sC15 = 0;
            double tC1 = 0, tC2 = 0, tC3 = 0, tC4 = 0, tC5 = 0, tC6 = 0, tC7 = 0, tC8 = 0, tC9 = 0, tC10 = 0, tC11 = 0, tC12 = 0, tC13 = 0, tC14 = 0, tC15 = 0;
            double todayTotal = 0, todayTotalMonth = 0;
            //Get all contacts
            List<Contact> allContacts = db.getAllSavedRecords();//fetch all saved records
            for (Contact contact : allContacts) {
//                    temp.add("Id: "+contact.getId()+"\nName: "+contact.getName()+
//                    "\nPhone Number: "+contact.getPhoneNumber());
//                    sum.add(contact.getC1());
                tDATE=contact.getDATE();
                tC1 = contact.getC1();
                tC2 = contact.getC2();
                tC3 = contact.getC3();
                tC4 = contact.getC4();
                tC5 = contact.getC5();
                tC6 = contact.getC6();
                tC7 = contact.getC7();
                tC8 = contact.getC8();
                tC9 = contact.getC9();
                tC10 = contact.getC10();
                tC11 = contact.getC11();
                tC12 = contact.getC12();
                tC13 = contact.getC13();
                tC14 = contact.getC14();
                tC15 = contact.getC15();
                //handling todays total on table view
                todayTotal = tC1 + tC2 + tC3 + tC4 + tC5 + tC6 + tC7 + tC8 + tC9 + tC10 + tC11 + tC12 + tC13 + tC14 + tC15;
                todayTotalMonth += todayTotal;//updating monthly total milk
//                    Update individual user milk sum
                sC1 += tC1;
                sC2 += tC2;
                sC3 += tC3;
                sC4 += tC4;
                sC5 += tC5;
                sC6 += tC6;
                sC7 += tC7;
                sC8 += tC8;
                sC9 += tC9;
                sC10 += tC10;
                sC11 += tC11;
                sC12 += tC12;
                sC13 += tC13;
                sC14 += tC14;
                sC15 += tC15;

                tempdate.add(contact.getDATE());
//                tempdate.add(Contact.rate+"");
//                tempdate.add(tDATE);
                tempc1.add("" + contact.getC1() + "");
                tempc2.add("" + contact.getC2() + "");
                tempc3.add("" + contact.getC3() + "");
                tempc4.add("" + contact.getC4() + "");
                tempc5.add("" + contact.getC5() + "");
                tempc6.add("" + contact.getC6() + "");
                tempc7.add("" + contact.getC7() + "");
                tempc8.add("" + contact.getC8() + "");
                tempc9.add("" + contact.getC9() + "");
                tempc10.add("" + contact.getC10() + "");
                tempc11.add("" + contact.getC11() + "");
                tempc12.add("" + contact.getC12() + "");
                tempc13.add("" + contact.getC13() + "");
                tempc14.add("" + contact.getC14() + "");
                tempc15.add("" + contact.getC15() + "");
                todayTotalArrayList.add("" + todayTotal);//adding todays total in arrayList

//            temp.add("Name: "+contact.getName());
//            temp.add("Phone Number: "+contact.getPhoneNumber());
//                //Log.d("dbsvm", "Id : " + contact.getC1() + "\n" +
//                        "Name : " + contact.getDATE() + "\n");

            }
//            table = (TableLayout) findViewById(R.id.myTable);//declaring outside function
            //inorder to increase code reusability in other classes

            //Add column headings
            TableRow hrow = new TableRow(SharePdf.this);
//            String hdate = "Date";
//            String hc1 = "       Arun";
//            String hc2 = "       Arvind ";
//            String hc3 = "    Chetan";
//            String hc4 = "   Jayprakash";
//            String hc5 = "   Mahesh";
//            String hc6 = "   Naresh";
//            String hc7 = "   Prempal";
//            String hc8 = "       Ravi";
//            String hc9 = "      Ravindra";
//            String hc10 = "     Rinku";
//            String hc11 = "     New1";
//            String hc12 = "     new2";
//            String hc13 = "     New3";
//            String hc14 = "     New4";
//            String hc15 = "     New5";

            CustomerNames defaultCustomerNames=new CustomerNames();
            String dayNum="S. No.";
            String hdate= "Date";
            String hc1 =""+defaultCustomerNames.getNAME1();
            String hc2 = ""+defaultCustomerNames.getNAME2();
            String hc3 = ""+defaultCustomerNames.getNAME3();
            String hc4 = ""+defaultCustomerNames.getNAME4();
            String hc5 = ""+defaultCustomerNames.getNAME5();
            String hc6 = ""+defaultCustomerNames.getNAME6();
            String hc7 = ""+defaultCustomerNames.getNAME7();
            String hc8 = ""+defaultCustomerNames.getNAME8();
            String hc9 = ""+defaultCustomerNames.getNAME9();
            String hc10=""+ defaultCustomerNames.getNAME10();
            String hc11= ""+defaultCustomerNames.getNAME11();
            String hc12= ""+defaultCustomerNames.getNAME12();
            String hc13= ""+defaultCustomerNames.getNAME13();
            String hc14= ""+defaultCustomerNames.getNAME14();
            String hc15= ""+defaultCustomerNames.getNAME15();

            String htodayTotal = "Total";

            TextView htvdayNum = new TextView(SharePdf.this);
            htvdayNum.setTextSize(15);
            htvdayNum.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htvdayNum.setText(dayNum);
//
            TextView htvdate = new TextView(SharePdf.this);
            htvdate.setTextSize(18);
//                htv1.setLinkTextColor();
            htvdate.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htvdate.setText(hdate);

            TextView htv1 = new TextView(SharePdf.this);
            htv1.setTextSize(18);
            htv1.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv1.setText(hc1);

            TextView htv2 = new TextView(SharePdf.this);
            htv2.setTextSize(18);
            htv2.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv2.setText(hc2);

            TextView htv3 = new TextView(SharePdf.this);
            htv3.setTextSize(18);
            htv3.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv3.setText(hc3);

            TextView htv4 = new TextView(SharePdf.this);
            htv4.setTextSize(18);
            htv4.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv4.setText(hc4);

            TextView htv5 = new TextView(SharePdf.this);
            htv5.setTextSize(18);
            htv5.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv5.setText(hc5);

            TextView htv6 = new TextView(SharePdf.this);
            htv6.setTextSize(18);
            htv6.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv6.setText(hc6);

            TextView htv7 = new TextView(SharePdf.this);
            htv7.setTextSize(18);
            htv7.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv7.setText(hc7);

            TextView htv8 = new TextView(SharePdf.this);
            htv8.setTextSize(18);
            htv8.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv8.setText(hc8);

            TextView htv9 = new TextView(SharePdf.this);
            htv9.setTextSize(18);
            htv9.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv9.setText(hc9);

            TextView htv10 = new TextView(SharePdf.this);
            htv10.setTextSize(18);
            htv10.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv10.setText(hc10);

            TextView htv11 = new TextView(SharePdf.this);
            htv11.setTextSize(18);
            htv11.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv11.setText(hc11);

            TextView htv12 = new TextView(SharePdf.this);
            htv12.setTextSize(18);
            htv12.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv12.setText(hc12);

            TextView htv13 = new TextView(SharePdf.this);
            htv13.setTextSize(18);
            htv13.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv13.setText(hc13);

            TextView htv14 = new TextView(SharePdf.this);
            htv14.setTextSize(18);
            htv14.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv14.setText(hc14);

            TextView htv15 = new TextView(SharePdf.this);
            htv15.setTextSize(18);
            htv15.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htv15.setText(hc15);

            TextView htvtodayTotal = new TextView(SharePdf.this);
            htvtodayTotal.setTextSize(18);
            htvtodayTotal.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            htvtodayTotal.setText(htodayTotal);

            hrow.addView(htvdayNum);
            hrow.addView(htvdate);
            hrow.addView(htv1);
            hrow.addView(htv2);
            hrow.addView(htv3);
            hrow.addView(htv4);
            hrow.addView(htv5);
            hrow.addView(htv6);
            hrow.addView(htv7);
            hrow.addView(htv8);
            hrow.addView(htv9);
            hrow.addView(htv10);
            hrow.addView(htv11);
            hrow.addView(htv12);
            hrow.addView(htv13);
            hrow.addView(htv14);
            hrow.addView(htv15);
            hrow.addView(htvtodayTotal);
            table.addView(hrow);

            //Log.d("dbsvm",tempdate.size()+"\n");            //Log.d("dbsvm",tempdate.size()+"\n");

            for (int i = 0; i < tempdate.size(); i++) {
                //Log.d("dbsvm",tempdate.get(i)+"\n");
                TableRow row = new TableRow(SharePdf.this);
                String date = tempdate.get(i);
                String c1 = tempc1.get(i);
                String c2 = tempc2.get(i);
                String c3 = tempc3.get(i);
                String c4 = tempc4.get(i);
                String c5 = tempc5.get(i);
                String c6 = tempc6.get(i);
                String c7 = tempc7.get(i);
                String c8 = tempc8.get(i);
                String c9 = tempc9.get(i);
                String c10 = tempc10.get(i);
                String c11 = tempc11.get(i);
                String c12 = tempc12.get(i);
                String c13 = tempc13.get(i);
                String c14 = tempc14.get(i);
                String c15 = tempc15.get(i);
                String vartodayTotal = todayTotalArrayList.get(i) + "       ";

                TextView tvDayNum = new TextView(SharePdf.this);
                tvDayNum.setText(i+1+"");

                TextView tvdate = new TextView(SharePdf.this);
                tvdate.setText(date);
                TextView tv1 = new TextView(SharePdf.this);
                tv1.setText(c1);
                TextView tv2 = new TextView(SharePdf.this);
                tv2.setText(c2);
                TextView tv3 = new TextView(SharePdf.this);
                tv3.setText(c3);
                TextView tv4 = new TextView(SharePdf.this);
                tv4.setText(c4);
                TextView tv5 = new TextView(SharePdf.this);
                tv5.setText(c5);
                TextView tv6 = new TextView(SharePdf.this);
                tv6.setText(c6);
                TextView tv7 = new TextView(SharePdf.this);
                tv7.setText(c7);
                TextView tv8 = new TextView(SharePdf.this);
                tv8.setText(c8);
                TextView tv9 = new TextView(SharePdf.this);
                tv9.setText(c9);
                TextView tv10 = new TextView(SharePdf.this);
                tv10.setText(c10);
                TextView tv11 = new TextView(SharePdf.this);
                tv11.setText(c11);
                TextView tv12 = new TextView(SharePdf.this);
                tv12.setText(c12);
                TextView tv13 = new TextView(SharePdf.this);
                tv13.setText(c13);
                TextView tv14 = new TextView(SharePdf.this);
                tv14.setText(c14);
                TextView tv15 = new TextView(SharePdf.this);
                tv15.setText(c15);
                TextView tvtodayTotal = new TextView(SharePdf.this);
                tvtodayTotal.setText(vartodayTotal);

                row.addView(tvDayNum);
                row.addView(tvdate);
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);
                row.addView(tv4);
                row.addView(tv5);
                row.addView(tv6);
                row.addView(tv7);
                row.addView(tv8);
                row.addView(tv9);
                row.addView(tv10);
                row.addView(tv11);
                row.addView(tv12);
                row.addView(tv13);
                row.addView(tv14);
                row.addView(tv15);
                row.addView(tvtodayTotal);


                table.addView(row);
            }
            //Milk per ltr rate to calculate total cost per user
            //Take input from user in text box
            double rate;
//            rate = 43.00;//temprary
            rate=Contact.Rate;

//            //Add total footer row
//            String addToCreateSpaceBefore="   ";
//            String ftotal_head = "Total(Ltr)";

            Contact init_contact=db.getDefaultDataForTextBoxes();
            //Add total footer row
            String addToCreateSpaceBefore="   ";
            String ftotal_head = "Total("+Contact.getQUANTITYUNITNAME()+")";

            String ftotCostC1 = "" + sC1 ;
            String ftotCostC2 = "" + sC2 ;
            String ftotCostC3 = "" + sC3 ;
            String ftotCostC4 = "" + sC4 ;
            String ftotCostC5 = "" + sC5 ;
            String ftotCostC6 = "" + sC6 ;
            String ftotCostC7 = "" + sC7 ;
            String ftotCostC8 = "" + sC8 ;
            String ftotCostC9 = "" + sC9 ;
            String ftotCostC10 = "" + sC10 ;
            String ftotCostC11 = "" + sC11 ;
            String ftotCostC12 = "" + sC12 ;
            String ftotCostC13 = "" + sC13 ;
            String ftotCostC14 = "" + sC14 ;
            String ftotCostC15 = "" + sC15 ;
            String ftotCosttodayTotalMonth = "" + todayTotalMonth ;


            TableRow frow = new TableRow(SharePdf.this);


            TextView faddToCreateSpaceBefore=new TextView(SharePdf.this);
            faddToCreateSpaceBefore.setText(addToCreateSpaceBefore);


            TextView ftvhead = new TextView(SharePdf.this);
            ftvhead.setTextSize(18);
            ftvhead.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftvhead.setText(ftotal_head);


            TextView ftv1 = new TextView(SharePdf.this);
            ftv1.setTextSize(18);
            ftv1.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv1.setText(ftotCostC1);

            TextView ftv2 = new TextView(SharePdf.this);
            ftv2.setTextSize(18);
            ftv2.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv2.setText(ftotCostC2);

            TextView ftv3 = new TextView(SharePdf.this);
            ftv3.setTextSize(18);
            ftv3.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv3.setText(ftotCostC3);

            TextView ftv4 = new TextView(SharePdf.this);
            ftv4.setTextSize(18);
            ftv4.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv4.setText(ftotCostC4);

            TextView ftv5 = new TextView(SharePdf.this);
            ftv5.setTextSize(18);
            ftv5.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv5.setText(ftotCostC5);

            TextView ftv6 = new TextView(SharePdf.this);
            ftv6.setTextSize(18);
            ftv6.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv6.setText(ftotCostC6);

            TextView ftv7 = new TextView(SharePdf.this);
            ftv7.setTextSize(18);
            ftv7.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv7.setText(ftotCostC7);

            TextView ftv8 = new TextView(SharePdf.this);
            ftv8.setTextSize(18);
            ftv8.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv8.setText(ftotCostC8);

            TextView ftv9 = new TextView(SharePdf.this);
            ftv9.setTextSize(18);
            ftv9.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv9.setText(ftotCostC9);

            TextView ftv10 = new TextView(SharePdf.this);
            ftv10.setTextSize(18);
            ftv10.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv10.setText(ftotCostC10);

            TextView ftv11 = new TextView(SharePdf.this);
            ftv11.setTextSize(18);
            ftv11.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv11.setText(ftotCostC11);

            TextView ftv12 = new TextView(SharePdf.this);
            ftv12.setTextSize(18);
            ftv12.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv12.setText(ftotCostC12);

            TextView ftv13 = new TextView(SharePdf.this);
            ftv13.setTextSize(18);
            ftv13.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv13.setText(ftotCostC13);

            TextView ftv14 = new TextView(SharePdf.this);
            ftv14.setTextSize(18);
            ftv14.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv14.setText(ftotCostC14);

            TextView ftv15 = new TextView(SharePdf.this);
            ftv15.setTextSize(18);
            ftv15.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv15.setText(ftotCostC15);

            TextView ftvtotCosttodayTotalMonth = new TextView(SharePdf.this);
            ftvtotCosttodayTotalMonth.setTextSize(18);
            ftvtotCosttodayTotalMonth.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftvtotCosttodayTotalMonth.setText(ftotCosttodayTotalMonth);

            frow.addView(faddToCreateSpaceBefore);
            frow.addView(ftvhead);
            frow.addView(ftv1);
            frow.addView(ftv2);
            frow.addView(ftv3);
            frow.addView(ftv4);
            frow.addView(ftv5);
            frow.addView(ftv6);
            frow.addView(ftv7);
            frow.addView(ftv8);
            frow.addView(ftv9);
            frow.addView(ftv10);
            frow.addView(ftv11);
            frow.addView(ftv12);
            frow.addView(ftv13);
            frow.addView(ftv14);
            frow.addView(ftv15);
            frow.addView(ftvtotCosttodayTotalMonth);

            table.addView(frow);//first frow added

            //Adding frow2 for total cost(rs)















            String ftotal_head2 = "Total(Rs)";
            String ftotCostC12a="" + sC1 * rate;
            String ftotCostC22="" + sC2 * rate;
            String ftotCostC32="" + sC3 * rate;
            String ftotCostC42="" + sC4 * rate;
            String ftotCostC52="" + sC5 * rate;
            String ftotCostC62="" + sC6 * rate;
            String ftotCostC72="" + sC7 * rate;
            String ftotCostC82="" + sC8 * rate;
            String ftotCostC92="" + sC9 * rate;
            String ftotCostC102="" + sC10 * rate;
            String ftotCostC112="" + sC11 * rate;
            String ftotCostC122="" + sC12 * rate;
            String ftotCostC132="" + sC13 * rate;
            String ftotCostC142="" + sC14 * rate;
            String ftotCostC152="" + sC15 * rate;
            String ftotCosttodayTotalMonth2="" + todayTotalMonth * rate + "";

            TableRow frow2 = new TableRow(SharePdf.this);

            TextView faddToCreateSpaceBefore2=new TextView(SharePdf.this);
            faddToCreateSpaceBefore.setText(addToCreateSpaceBefore);

            TextView ftvhead2 = new TextView(SharePdf.this);
            ftvhead2.setTextSize(18);
            ftvhead2.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftvhead2.setText(ftotal_head2);


            TextView ftv12a = new TextView(SharePdf.this);
            ftv12a.setTextSize(18);
            ftv12a.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv12a.setText(ftotCostC12a);

            TextView ftv22 = new TextView(SharePdf.this);
            ftv22.setTextSize(18);
            ftv22.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv22.setText(ftotCostC22);

            TextView ftv32 = new TextView(SharePdf.this);
            ftv32.setTextSize(18);
            ftv32.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv32.setText(ftotCostC32);

            TextView ftv42 = new TextView(SharePdf.this);
            ftv42.setTextSize(18);
            ftv42.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv42.setText(ftotCostC42);

            TextView ftv52 = new TextView(SharePdf.this);
            ftv52.setTextSize(18);
            ftv52.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv52.setText(ftotCostC52);

            TextView ftv62 = new TextView(SharePdf.this);
            ftv62.setTextSize(18);
            ftv62.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv62.setText(ftotCostC62);

            TextView ftv72 = new TextView(SharePdf.this);
            ftv72.setTextSize(18);
            ftv72.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv72.setText(ftotCostC72);

            TextView ftv82 = new TextView(SharePdf.this);
            ftv82.setTextSize(18);
            ftv82.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv82.setText(ftotCostC82);

            TextView ftv92 = new TextView(SharePdf.this);
            ftv92.setTextSize(18);
            ftv92.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv92.setText(ftotCostC92);

            TextView ftv102 = new TextView(SharePdf.this);
            ftv102.setTextSize(18);
            ftv102.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv102.setText(ftotCostC102);

            TextView ftv112 = new TextView(SharePdf.this);
            ftv112.setTextSize(18);
            ftv112.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv112.setText(ftotCostC112);

            TextView ftv122 = new TextView(SharePdf.this);
            ftv122.setTextSize(18);
            ftv122.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv122.setText(ftotCostC122);

            TextView ftv132 = new TextView(SharePdf.this);
            ftv132.setTextSize(18);
            ftv132.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv132.setText(ftotCostC132);

            TextView ftv142 = new TextView(SharePdf.this);
            ftv142.setTextSize(18);
            ftv142.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv142.setText(ftotCostC142);

            TextView ftv152 = new TextView(SharePdf.this);
            ftv152.setTextSize(18);
            ftv152.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftv152.setText(ftotCostC152);

            TextView ftvtotCosttodayTotalMonth2 = new TextView(SharePdf.this);
            ftvtotCosttodayTotalMonth2.setTextSize(18);
            ftvtotCosttodayTotalMonth2.setTextColor(SharePdf.this.getResources().getColor(R.color.black));
            ftvtotCosttodayTotalMonth2.setText(ftotCosttodayTotalMonth2);

//            frow2.addView(faddToCreateSpaceBefore);//Add space before last two rows, SharePdf.this initialised
            // for frow above, and used here as purpose are same
//            frow2.addView(faddToCreateSpaceBefore);
            frow2.addView(faddToCreateSpaceBefore2);
            frow2.addView(ftvhead2);
            frow2.addView(ftv12a);
            frow2.addView(ftv22);
            frow2.addView(ftv32);
            frow2.addView(ftv42);
            frow2.addView(ftv52);
            frow2.addView(ftv62);
            frow2.addView(ftv72);
            frow2.addView(ftv82);
            frow2.addView(ftv92);
            frow2.addView(ftv102);
            frow2.addView(ftv112);
            frow2.addView(ftv122);
            frow2.addView(ftv132);
            frow2.addView(ftv142);
            frow2.addView(ftv152);
            frow2.addView(ftvtotCosttodayTotalMonth2);

            table.addView(frow2);//second frow added



            //Log.d("dbsvm","Total Table rows: "+table.getChildCount()+"");
        } catch (Exception e) {
            //Log.d("dbsvm", "Unable to run tableView method in SharePdf.java\nError : "+e.getMessage().toString());
//                textView.setText(e.getMessage().toString());
        }
//        return table;//To convert into pdf
    }

    public  void showAllCustomerNames()
    {
        MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(SharePdf.this);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPickerToSelectName);



        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                CustomerNames currentNamesNumberPicker = db.getDefaultCustomerDataForTextBoxes();
//                customerDatabasesArrayList = db.getAllSavedCustomerGroupDatabaseNames();
                customerDatabasesArrayList.add(CustomerNames.NAME1);
                //Log.d("dbsvm","Name1 : "+CustomerNames.NAME1);
                customerDatabasesArrayList.add(CustomerNames.NAME2);
                customerDatabasesArrayList.add(CustomerNames.NAME3);
                customerDatabasesArrayList.add(CustomerNames.NAME4);
                customerDatabasesArrayList.add(CustomerNames.NAME5);
                customerDatabasesArrayList.add(CustomerNames.NAME6);
                customerDatabasesArrayList.add(CustomerNames.NAME7);
                customerDatabasesArrayList.add(CustomerNames.NAME8);
                customerDatabasesArrayList.add(CustomerNames.NAME9);
                customerDatabasesArrayList.add(CustomerNames.NAME10);
                customerDatabasesArrayList.add(CustomerNames.NAME11);
                customerDatabasesArrayList.add(CustomerNames.NAME12);
                customerDatabasesArrayList.add(CustomerNames.NAME13);
                customerDatabasesArrayList.add(CustomerNames.NAME14);
                customerDatabasesArrayList.add(CustomerNames.NAME15);


            }
            i = 0;
            sizeOfReceivedArrayList = customerDatabasesArrayList.size();
            //Log.d("dbsvm","Names arraylist size : "+sizeOfReceivedArrayList);
            str = new String[sizeOfReceivedArrayList];
            for (String cDatabaseName : customerDatabasesArrayList) {
                str[i] = cDatabaseName;
                i++;
            }
            //Log.d("dbsvm","Str length : "+str.length);
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
                TextView atShowSelectedName=findViewById(R.id.showSelectedNameToSharePdf);
                atShowSelectedName.setText( "No data found");
                selectedNameToSharePdf="";
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
                    ((EditText) child).setTextSize(26);
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
