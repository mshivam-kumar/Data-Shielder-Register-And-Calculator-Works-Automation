package com.datahiveshield.mydata.mydairy.khata.files.data;

        import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.datahiveshield.mydata.mydairy.khata.files.DeleteIndividualPersonGroup;
import com.datahiveshield.mydata.mydairy.khata.files.model.CustomerNames;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.io.File;
import java.util.ArrayList;

public class MyDBHandlerIndividualPersonData extends SQLiteOpenHelper {
    public static SQLiteDatabase db1;
    public Context context;

    //    Context context;
    public MyDBHandlerIndividualPersonData(Context context)
    {

//        super(context, Params.DATATABSE_DIRECTORY_NAME,null,Params.DB_VERSION);
//                + File.separator + "/."+Params.EmailId+"/"+ Params.DB_NAME
        super(context, context.getExternalFilesDir(null).getAbsolutePath()
                + File.separator+"/."+ Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed
                + File.separator
                + Params.DB_NAME,null,Params.DB_VERSION);
//        Log.d("dbsvm","Database saving path -"+context.getExternalFilesDir(null).getAbsolutePath()
//                + File.separator+"/."+ Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed
//                + File.separator
//                + Params.DB_NAME);

        this.context=context;
        //Log.d("dbsvm","\nGroup  location : "+context.getExternalFilesDir(null).getAbsolutePath()

//                + File.separator + "/."+Params.EmailId+"/"+Params.DB_NAME
//                + File.separator
//                + Params.DB_NAME);
        //For default data
//            super(context, Params.DEFAULT_DATA_DB_NAME, null, Params.DEFAULT_DATA_DB_VERSION);

//        //Log.d("dbsvm","inside MyDbHandler");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d("dbsvm","inside  MyDbHandler.java");

        //Creating table in database
//        String create="CREATE TABLE "+Params.TABLE_NAME+"("+
//                Params.KEY_ID+" INTEGER PRIMARY KEY,"+
//                Params.KEY_NAME+" TEXT, "+Params.KEY_PHONE+" TEXT "+")";
////        String create="DROP TABLE "+Params.TABLE_NAME;
//            db1=db;
        //Log.d("dbsvm","inside MyDbHandler");
//            createDefaultDataTableForTextBoxes(db);//Create table to  store default data into texboxes


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        //Log.d("dbsvm","inside onUpgrade method");
//        db.execSQL("DROP TABLE IF EXISTS "+Params.DEFAULT_DATA_TABLE_NAME);
//        //Log.d("dbsvm","table dropped");
//        onCreate(db);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public CustomerNames getDefaultCustomerDataForTextBoxes(){
//        List<Contact> contactList=new ArrayList<>();
//        List<Contact> contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        //Generate the query to read from database
//        Contact contact=new Contact();
        CustomerNames contact=new CustomerNames();
        try {

            String select = "SELECT * FROM " + Params.DEFAULT_CUSTOMER_NAMES_TABLE;
//        String select="SELECT * FROM "+Params.DEFAULT_DATA_TABLE_NAME;

            Cursor cursor = db.rawQuery(select, null);
            //Loop through now
            if (cursor.moveToFirst()) {
//                do {
//                    Contact contact = new Contact();
//                    contact.setDATE(cursor.getString(0));
                contact.setNAME1(cursor.getString(0));
                contact.setNAME2(cursor.getString(1));
                contact.setNAME3(cursor.getString(2));
                contact.setNAME4(cursor.getString(3));
                contact.setNAME5(cursor.getString(4));
                contact.setNAME6(cursor.getString(5));
                contact.setNAME7(cursor.getString(6));
                contact.setNAME8(cursor.getString(7));
                contact.setNAME9(cursor.getString(8));
                contact.setNAME10(cursor.getString(9));
                contact.setNAME11(cursor.getString(10));
                contact.setNAME13(cursor.getString(11));
                contact.setNAME12(cursor.getString(12));
                contact.setNAME14(cursor.getString(13));
                contact.setNAME15(cursor.getString(14));

//                    contactList.add(contact);
//                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Unable to fetch default customer data "+e.getMessage().toString());
        }
        return contact;
    }


    //    public void updateDefaultValueData(Contact contact,int key)




    //Tells whether record for particulat date (passed) exists or not
    public int isIndividualPersonDateExist(String date) {

        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Params.INDIVIDUAL_PERSON_DATA_TABLE_NAME + " WHERE " + Params.DATE + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {date});

//        boolean hasObject = false;
        int flag=0;//does not exist
        if(cursor.moveToFirst()){
//            hasObject = true;
            flag=1;
            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            //Log.d("dbsvm", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return flag;
    }


    public void createCustomerGroupDatabaseStoringTable(SQLiteDatabase db)
    {
        try {
            String create = "CREATE TABLE IF NOT EXISTS " + Params.ToStoreCustomerGroupsDatabase + "(" +
                    Params.CustomerGroupCreatedDatabaseName + " VARCHAR(20) PRIMARY KEY , " + Params.CustomerGroupCreatedDatabasePhoneNum + " VARCHAR(20) " + ")";

            //Log.d("dbsvm", "Query being run\n : " + create);
            db.execSQL(create);
            //Log.d("dbsvm", "ToStoreCustomerGroupsTableName Table created successfully");

//            db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);
//                   //Log.d("dbsvm"," Main contacts_db table dropped");
//
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","ToStoreCustomerGroupsTableName "+e.getMessage().toString());
        }

    }



    //Tells whether record for particulat date (passed) exists or not
    public int isCustomerGroupDatabaseExist(String tableName) {

        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Params.ToStoreCustomerGroupsDatabase + " WHERE " + Params.CustomerGroupCreatedDatabaseName + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {tableName});

//        boolean hasObject = false;
        int flag=0;//does not exist
        if(cursor.moveToFirst()){
//            hasObject = true;
            flag=1;
            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            //Log.d("dbsvm", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return flag;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
//public void addContact()
    public void insertCustomerGroupDatabaseNameIntoTable(String newDatabaseName,String newDatabasePhoneNum)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put(Params.C1, contact.getC1());
//            values.put(Params.C2, contact.getC2());

            values.put(Params.CustomerGroupCreatedDatabaseName,newDatabaseName);
            values.put(Params.CustomerGroupCreatedDatabasePhoneNum,newDatabasePhoneNum);
//            //Log.d("sk", "Successfully inserted");


            db.insert(Params.ToStoreCustomerGroupsDatabase, null, values);
            //Log.d("dbsvm", "CustomerGroupTable Successfully inserted");
            db.close();

            //notify caller (class) that data is being inserted
            //flag is returned to handle alertBoxes, to show msg to user
            //if tries to insert data with the exiting date in database

//            GetAlerts ga=new GetAlerts();
//            ga.alertDialogBox(null, "Date : "+contact.getDATE() + "  record inserted", "Record Inserted");

        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Unbale to insert Customer Group Table, Error: "+e.getMessage().toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateCustomerGroupDatabaseStoringTable(String oldName,String newDbName)
    //Need to run and debug
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put(Params.C1, contact.getC1());
//            values.put(Params.C2, contact.getC2());

            values.put(Params.CustomerGroupCreatedDatabaseName,newDbName);
//            //Log.d("sk", "Successfully inserted");

//            db.update(Params.ToStoreCustomerGroupsDatabase, values, Params.CustomerGroupCreatedDatabaseName + "=?" ,
//                    new String[]{String.valueOf(newDbName)});
            db.update(Params.ToStoreCustomerGroupsDatabase, values, Params.CustomerGroupCreatedDatabaseName + "=?" ,
                    new String[]{String.valueOf(oldName)});

//            db.execSQL("Attach "+Params.DB_NAME+" AS "+" newDbName;");
//            db.update(Params.TABLE_NAME, values, "date = ?",null);

//            db.execSQL("ALTER DATABASE MyTestDatabase SET SINGLE_USER WITH ROLLBACK IMMEDIATE");
//            db.execSQL("ALTER DATABASE MyTestDatabase MODIFY NAME = MyTestDatabaseCopy") ;
//            db.execSQL("ALTER DATABASE MyTestDatabaseCopy SET MULTI_USER");
//            db.insert(Params.TABLE_NAME, null, values);
            //Log.d("dbsvm", "CustomerGroupTable Successfully updated");
            db.close();
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Unbale to update CustomerGroupTable, Error: "+e.getMessage().toString());
        }
    }


    public  void changeIndividualPersonGroupName(String oldDbName,String newDbName)
    {
        try {

            SQLiteDatabase db = this.getWritableDatabase();

//            String storage_folder = context.getExternalFilesDir(null).getAbsolutePath()+"/."+Params.EmailId+"/"+oldDbName;
//            String new_storage_folder=context.getExternalFilesDir(null).getAbsolutePath()+"/."+Params.EmailId+"/"+newDbName;
            String storage_folder = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName;
            String new_storage_folder = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+newDbName;
            File sourceFile = new File(storage_folder);
            File destFile = new File(new_storage_folder);

            //Now rename subfiles
            String oldsource1 = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName+"/"+oldDbName;
            String newdest1 = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName+"/"+newDbName;
//            File dir = new File(storage_folder);
            File oldsourcef1 = new File(oldsource1);
            File newdestf1 = new File(newdest1);
            if (oldsourcef1.renameTo(newdestf1)) {//Directory Name changed
                System.out.println("Directory renamed successfully");
            } else {
                System.out.println("Failed to rename directory");
            }

            String oldsource2 = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName+"/"+oldDbName+"-wal";
            String newdest2 = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName+"/"+newDbName+"-wal";
            String oldsource3 = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName+"/"+oldDbName+"-shm";
            String newdest3 = context.getExternalFilesDir(null).getAbsolutePath()
                    +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+oldDbName+"/"+newDbName+"-shm";

            File oldsourcef2 = new File(oldsource2);
            File newdestf2 = new File(newdest2);
            File oldsourcef3 = new File(oldsource3);
            File newdestf3 = new File(newdest3);
            oldsourcef2.renameTo(newdestf2);
            oldsourcef3.renameTo(newdestf3);

            //Now delete main directory of these sub files
            if (sourceFile.renameTo(destFile)) {//Directory Name changed
                System.out.println("Directory renamed successfully");
            } else {
                System.out.println("Failed to rename directory");
            }


            //Log.d("dbsvm","new config table name "+newDbName);
//            Params.Curent_Running_Db=newDbName;
//            Params.DB_NAME=newDbName;

        }
        catch (Exception e)
        {
            Log.d("dbsvm","Unable to change CustomerGroup db name\n" +
                    "Error : "+e.getMessage().toString());
        }
//
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteCustomeGroupDatabaseFromStoringTable(String dbName) {
        //Need to run adn debug
        try {
            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL("delete from " + Params.TABLE_NAME+" where "+Params.DATE+"="+Contact.selectedDate);
//            db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);//Table deleted
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.update(Params.ToStoreCustomerGroupsDatabase, Params.CustomerGroupCreatedDatabaseName +"=?", new String[]{String.valueOf(dbName)});
            db.delete(Params.ToStoreCustomerGroupsDatabase, Params.CustomerGroupCreatedDatabaseName +"=?", new String[]{String.valueOf(dbName)});
            //Log.d("dbsvm", "CustomerGroupTable deleted");
//            db.execSQL("DROP DATABASE "+dbName);//Delete all table associated with passed dbName
//            db.execSQL("DROP TABLE IF EXISTS "+Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE);
//            //Now delete other associated tables with it
//            db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);//Table deleted
//            db.execSQL("DROP TABLE IF EXISTS "+Params.DEFAULT_DATA_TABLE_NAME);//Table deleted
//            db.execSQL("DROP TABLE IF EXISTS "+Params.DEFAULT_CUSTOMER_NAMES_TABLE);//Table deleted
//            //Log.d("dbsvm","CustomerGroup table deleted along with " +
//                    "three other tables associated with it\n" +
//                    "Main, DefaultMilk value, Default customers");



            db.close();
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Error in deleting table CustomerGroupTable :"+e.getMessage().toString());
        }
    }

    public void dropGroupTablesAssociated(String dbName)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS "+Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE);
//    db.execSQL("DROP DATABASE "+dbName);//Delete all table associated with passed dbName

    }
    public int isCustomerDatabaseDataRowExist() {

        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Params.ToStoreCustomerGroupsDatabase;

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, null);

//        boolean hasObject = false;
        int flag=0;//does not exist
        if(cursor.moveToFirst()){
//            hasObject = true;
            flag=1;
            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
//            //Log.d("dbsvm", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return flag;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getAllSavedCustomerGroupDatabaseNames(){
        ArrayList<String> contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        //Generate the query to read from database
//        String select="SELECT * FROM "+Params.ToStoreCustomerGroupsDatabase;

        String select="";
//        Fetching only names to show in HomeIndividualPersonDataListView
        if(Params.youWillGetOrGiveMoneyButtonPressed.equals(Params.YOU_WILL_GET_MONEY_BUTTON_VALUE))
        {
            select="SELECT * FROM "+Params.ToStoreCustomerGroupsDatabase;

        }
        else {
            select = "SELECT " + Params.CustomerGroupCreatedDatabaseName + " FROM " + Params.ToStoreCustomerGroupsDatabase;
        }
//        String select="SELECT * FROM "+Params.DEFAULT_DATA_TABLE_NAME;

        Cursor cursor=db.rawQuery(select,null);
        //Loop through now
        if(cursor.moveToFirst())
        {
            do{
//                CustomerGroupDatabases contact=new CustomerGroupDatabases();
                String contact;
                contact=cursor.getString(0);
//                Log.d("dbsvm",cursor.getString(1));//to know phone number

                contactList.add(contact);
            }while (cursor.moveToNext());
            //Log.d("dbsvm","fetching database name storing table data");
        }
        //Log.d("dbsvm","fetched database name storing table data");

        return contactList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getRequiredCustomerGroupDatabasePhoneNumber(String personName){
        String number="";
        SQLiteDatabase db=this.getReadableDatabase();
        //Generate the query to read from database
//        String select="SELECT * FROM "+Params.ToStoreCustomerGroupsDatabase;

//        Fetching only "phone number" to show in HomeIndividualPersonDataListView
        String select="SELECT "+ Params.CustomerGroupCreatedDatabasePhoneNum +" FROM "+Params.ToStoreCustomerGroupsDatabase +
                " WHERE "+Params.CustomerGroupCreatedDatabaseName +" = '"+Params.CURRENT_GROUP_DB_NAME+"'";
//        String select="SELECT * FROM "+Params.DEFAULT_DATA_TABLE_NAME;

        Cursor cursor=db.rawQuery(select,null);
        //Loop through now
        if(cursor.moveToFirst())
        {
                number=cursor.getString(0);

            //Log.d("dbsvm","fetching database name storing table data");
        }
        //Log.d("dbsvm","fetched database name storing table data");

        return number;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void UpdateIndividualPersonPhoneNumber(String newNumber)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put(Params.C1, contact.getC1());
//            values.put(Params.C2, contact.getC2());

            values.put(String.valueOf(Params.CustomerGroupCreatedDatabasePhoneNum), newNumber);

//            db.update(Params.TABLE_NAME, values, "",new String[]{contact.getSelectedDate()});
            db.update(Params.ToStoreCustomerGroupsDatabase, values,Params.CustomerGroupCreatedDatabaseName +"=?",new String[]{String.valueOf(Params.CURRENT_GROUP_DB_NAME)});

//            db.update(Params.TABLE_NAME, values, "date = ?",null);

//            db.insert(Params.TABLE_NAME, null, values);
            //Log.d("dbsvm", "Successfully updated");
            db.close();
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Unbale to insert, Error: "+e.getMessage().toString());
        }
    }

    public void deleteIndividualPersonGroup()//Working fine group name deleted successfully
    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String storage_folder = context.getExternalFilesDir(null).getAbsolutePath()+"/."+Params.EmailId+"/"+Params.DB_NAME;
        String path = context.getExternalFilesDir(null).getAbsolutePath()
                +"/."+Params.EmailId+"/"+Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME+"/"+Params.youWillGetOrGiveMoneyButtonPressed+"/"+ DeleteIndividualPersonGroup.selectedGroupName;
//        File dir = new File(storage_folder);
//        Log.d("dbsvm","delete path"+path);
        File file = new File(path);

        DeleteIndividualPersonGroup.selectedGroupName="";//After deleting person with selected string, reset
        //string to null, to be safe from any inappropriate action caused

//        if (file.exists()) {
//            String deleteCmd = "rm -r " + path;
//            Runtime runtime = Runtime.getRuntime();
//            try {
//                runtime.exec(deleteCmd);
//                Log.d("dbsvm",path+" folder deleted successfully");
//            } catch (IOException e) {
//                Log.d("dbsvm","\nUnable to delete customer group\n"+
//                        "Error:" +e.getMessage().toString());
//            }
//        }

        if (file.exists()) {

            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                Log.d("dbsvm",children[i]+"\n");
                File entry=new File(file, children[i]);
                if (entry.isDirectory())
                {
                    Log.d("dbsvm","inside entry directory");
                    String[] nesChildren=entry.list();
                    for(int j=0;j<nesChildren.length;j++)
                    {
                        new File(entry,nesChildren[j]).delete();
                        Log.d("dbsvm",nesChildren[j]+" file deleted");
                    }
                    entry.delete();
                }
                else
                {
                    entry.delete();
                }
                //Log.d("dbsvm", children[i] + " deleted");
            }
            file.delete();
            //Log.d("dbsvm", file + " deleted");

        }

    }

    public void createConfigureCustomerGroupDataTableWithMonthAndYear(SQLiteDatabase db)
    {
        try {
            String create = "CREATE TABLE IF NOT EXISTS " + Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE + "(" +
                    Params.SubTableNameWithMonthAndYearToConfigureCustomerGroupNewData + " VARCHAR(15) PRIMARY KEY" + ")";

            //Log.d("dbsvm", "Query being run :\n " + create);
            db.execSQL(create);
            //Log.d("dbsvm", "\n    CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR\n Table created successfully");

//            db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);
//                   //Log.d("dbsvm"," Main contacts_db table dropped");
//
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","ConfigureCustomerGroupDataWithMonthAndYear "+e.getMessage().toString());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
//public void addContact()
    public void insertConfigureCustomerGroupDataWithMonthAndYear(String newTableName)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put(Params.C1, contact.getC1());
//            values.put(Params.C2, contact.getC2());

            values.put(Params.SubTableNameWithMonthAndYearToConfigureCustomerGroupNewData,newTableName);
//            //Log.d("sk", "Successfully inserted");


            db.insert(Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE, null, values);
            //Log.d("dbsvm", "CustomerGroupTable Successfully inserted");
            db.close();

            //notify caller (class) that data is being inserted
            //flag is returned to handle alertBoxes, to show msg to user
            //if tries to insert data with the exiting date in database

//            GetAlerts ga=new GetAlerts();
//            ga.alertDialogBox(null, "Date : "+contact.getDATE() + "  record inserted", "Record Inserted");

        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Unbale to insert Customer Group Table, Error: "+e.getMessage().toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateConfigureCustomerGroupDataTableWithMonthAndYear(String oldName,String newDbName)
    //Need to run and debug
    {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put(Params.C1, contact.getC1());
//            values.put(Params.C2, contact.getC2());

            values.put(Params.SubTableNameWithMonthAndYearToConfigureCustomerGroupNewData,newDbName);
//            //Log.d("sk", "Successfully inserted");

//            db.update(Params.ToStoreCustomerGroupsDatabase, values, Params.CustomerGroupCreatedDatabaseName + "=?" ,
//                    new String[]{String.valueOf(newDbName)});
            db.update(Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE, values, Params.SubTableNameWithMonthAndYearToConfigureCustomerGroupNewData + "=?" ,
                    new String[]{String.valueOf(oldName)});

//            db.execSQL("Attach "+Params.DB_NAME+" AS "+" newDbName;");
//            db.update(Params.TABLE_NAME, values, "date = ?",null);

//            db.insert(Params.TABLE_NAME, null, values);
            //Log.d("dbsvm", "ConfigureCustomerGroupDataWithMonthAndYearSuccessfully updated");
            db.close();
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Unbale to update createConfigureCustomerGroupDataWithMonthAndYear, Error: "+e.getMessage().toString());
        }
    }




    public int isConfigureCustomerGroupDataTableWithMonthAndYearRowExist() {

        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, null);

//        boolean hasObject = false;
        int flag=0;//does not exist
        if(cursor.moveToFirst()){
//            hasObject = true;
            flag=1;
            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
//            //Log.d("dbsvm", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return flag;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteConfigureCustomerGroupWithMonthAndYear(String dbName) {
        //Need to run adn debug
        try {
            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL("delete from " + Params.TABLE_NAME+" where "+Params.DATE+"="+Contact.selectedDate);
//            db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);//Table deleted
//            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE, Params.SubTableNameWithMonthAndYearToConfigureCustomerGroupNewData +"=?", new String[]{String.valueOf(dbName)});
            //Log.d("dbsvm", "CustomerGroupTable deleted");

//            db.execSQL("DROP DATABASE "+dbName);//Delete all table associated with passed dbName
            //Now delete other associated tables with it
//            db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);//Table deleted
//            db.execSQL("DROP TABLE IF EXISTS "+Params.DEFAULT_DATA_TABLE_NAME);//Table deleted
//            db.execSQL("DROP TABLE IF EXISTS "+Params.DEFAULT_CUSTOMER_NAMES_TABLE);//Table deleted
//            //Log.d("dbsvm","CustomerGroup table deleted along with " +
//                    "three other tables associated with it\n" +
//                    "Main, DefaultMilk value, Default customers");



            db.close();
        }
        catch (Exception e)
        {
            //Log.d("dbsvm","Error in deleting table CustomerGroupTable :"+e.getMessage().toString());
        }
    }


}

