package com.datahiveshield.mydata.mydairy.khata.files.params;

public class Params {
    public static final int DB_VERSION=1;
    public static  final String DEFAULT_DB_NAME="group_names_db";
    public static final String DEFAULT_INDIVIDUAL_PERSON_DATA_NAME="individual_person_db";
    public static  String DB_NAME="group_names_db";
    public static final String Customer_Group_Storing_DB_NAME="customer_group_storing_db_name";
    public static String Curent_Running_Db="";
    public static String App_Path_Location="";//To access app location in every class (which are not activity,
    //and can not call method to get app path) to save files.

//    public static final String TABLE_NAME="contacts_table";
//    public static  final String DEFAULT_TABLE_NAME="customer_group_table";//to store customer group
//    public static   String TABLE_NAME="customer_group_table";


    //Keys to out table in db
//    public static final String KEY_ID="id";
//
//    public static final String KEY_NAME="name";
//    public static final String KEY_PHONE="phone_number";

    public static final String DATE="date";
//    public static String selectedDate;//not part of db
    public static final String C1="C1",C2="C2",C3="C3",C4="C4",C5="C5",C6="C6",C7="C7",C8="C8",C9="C9",C10="C10",C11="C11",C12="C12",C13="C13",C14="C14",C15="C15";

    //Table constraints to store default data
    //    public static final String TABLE_NAME="contacts_table";
    public static final  String DEFAULT_DATA_TABLE_NAME="default_data_value_table";
    public static  String DATA_TABLE_NAME="data_value_table";

    public static final String RATE="rate";
    public  static final String QUANTITYUNITNAME="quantity_unit_name";
    //Default Customer Names constraint
    public static   final String DEFAULT_CUSTOMER_NAMES_TABLE="default_customer_names_table";

    public static   final String DEFAULT_CUSTOMER_NAMES_AND_PHONE_NUMBER_TABLE="default_customer_names_and_phone_number_table";
    public static final  String GROUP_MEMBER_NAME="group_member_name";
    public static final  String GROUP_MEMBER_PHONE_NUMBER="group_member_phone_number";

//    public static   final String DEFAULT_CUSTOMER_NAMES_PHONE_NUMBER_TABLE="default_customer_names_phone_number_table";
    public static   String CUSTOMER_NAMES_TABLE="default_customer_names_table";

    public static final String NAME1="NAME1",NAME2="NAME2",NAME3="NAME3",NAME4="NAME4",NAME5="NAME5",
            NAME6="NAME6",NAME7="NAME7",NAME8="NAME8", NAME9="NAME9",
            NAME10="NAME10", NAME11="NAME11",NAME12="NAME12",NAME13="NAME13",
            NAME14="NAME14",NAME15="NAME15";
    public static final String PHONE_NAME1="PHONE_NAME1",PHONE_NAME2="PHONE_NAME2",PHONE_NAME3="PHONE_NAME3",PHONE_NAME4="PHONE_NAME4",PHONE_NAME5="PHONE_NAME5",
            PHONE_NAME6="PHONE_NAME6",PHONE_NAME7="PHONE_NAME7",PHONE_NAME8="PHONE_NAME8", PHONE_NAME9="PHONE_NAME9",
            PHONE_NAME10="PHONE_NAME10", PHONE_NAME11="PHONE_NAME11",PHONE_NAME12="PHONE_NAME12",PHONE_NAME13="PHONE_NAME13",
            PHONE_NAME14="PHONE_NAME14",PHONE_NAME15="PHONE_NAME15";


    //Contraints for ToStoreCustomerGroupsTableName table
    //For each group , new database is created which has three tables in it
    public  static final  String ToStoreCustomerGroupsDatabase="to_store_customer_groups_database";
    public static  final String CustomerGroupCreatedDatabaseName="customer_group_created_database_name";//here used as variable same as DATE for above tables
    public static  final String CustomerGroupCreatedDatabasePhoneNum="customer_group_created_database_phone_num";//here used as variable same as DATE for above tables


    public static final String AppName="Data Shielder";//To be changed later on
    public static  String EmailId="";
    //for myltipurpose app
    //Table to store user configuration of data based on month and year name, situated inside
    //Customer Group database , different for all groups

    public  static String DATATABSE_DIRECTORY_NAME=Params.DB_NAME;
    public static String  DB_TO_CHANGE="";
//    Store customer group data based on month and year name
    public static final String DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE="configure_customer_group_data_with_month_and_year_table";
    public static final String CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE="configure_customer_group_data_with_month_and_year_table";

//    public static   final String DEFAULT_DATA_VALUE_TABLE="default_data_value_table";
//    public static   String DATA_VALUE_TABLE="default_data_value_table";


    public  static   String SubTableNameWithMonthAndYearToConfigureCustomerGroupNewData="sub_table_name_with_month_and_year_to_configure_customer_group_new_data";//To be overridden when saved inside
    //Particular CustomerGroup, it is to organize data of customer grp monthly per year

    public static String toAddBeforeNewTableName="";//To have uniqeness among tables
    public static String toAddBeforeNewCustomerDataconfigurationWithMonthAndYear="";//To have uniqeness among tables

    public static String groupNameToAddBeforeDefault_group_TableNamesReceived="";
    public static String groupNameToAddBeforeDefault_Config_TableNamesReceived="";


    public static String groupNameToAddBeforeDefaultTableNamesReceived="";

    public  static String CURRENT_GROUP_DB_NAME="";
    public static String CURRENT_GROUP_CONFIG_DB_NAME="";
    public static String INDIVIDUAL_PERSON_DATA_TABLE_NAME="individual_person_data_value_table";
    public static String INDIVIDUAL_PERSON_DATA_TABLE_NAME_ACCESS_TELLER="";//Used to tell whether Individual person
    public static String youWillGetOrGiveMoneyButtonPressed="";
    public static String YOU_WILL_GET_MONEY_BUTTON_VALUE="";
    //data is accessed currently by user, then change value of heading in xml layout
    //file, according to the requirement, ex Person Accessed: and Group Accessed headings.
}
