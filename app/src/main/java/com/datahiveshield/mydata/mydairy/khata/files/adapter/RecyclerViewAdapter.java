package com.datahiveshield.mydata.mydairy.khata.files.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.datahiveshield.mydata.mydairy.khata.files.HOMEIndividualPersonDataListView;
import com.datahiveshield.mydata.mydairy.khata.files.HOMEcustomerGroupListView;
import com.datahiveshield.mydata.mydairy.khata.files.HomeConfigureCustomerGroupDataWithMonthAndYear;
import com.datahiveshield.mydata.mydairy.khata.files.IndividualHomeConfigureCustomerGroupDataWithMonthAndYear;
import com.datahiveshield.mydata.mydairy.khata.files.IndividualPersonMainActivity;
import com.datahiveshield.mydata.mydairy.khata.files.MainActivity;
import com.datahiveshield.mydata.mydairy.khata.files.R;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<String> contactList;

    public RecyclerViewAdapter(Context context, ArrayList<String> contactList1) {
        this.context = context;
        try {
            contactList = contactList1;
        }catch (Exception e)
        {
            //Log.d("dbsvm","Error : "+e.getMessage().toString());
        }
    }

    // Where to get the single card as viewholder Object

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

//        holder.contactName.setText(contact.getC1()+"");
//        holder.phoneNumber.setText(contact.getC2()+"");
//        holder.contactName.setText("a");
        try {
            int i=position;
            ViewHolder viewHolder=holder;
            if (i % 5 == 0)
                viewHolder.itemView.setBackgroundResource(R.drawable.grad_green_blue);
            else if (i % 5 == 1)
                viewHolder.itemView.setBackgroundResource(R.drawable.grad_aqua);
            else if (i % 5 == 2)
                viewHolder.itemView.setBackgroundResource(R.drawable.grad_red);
            else if (i % 5 == 3)
                viewHolder.itemView.setBackgroundResource(R.drawable.grad_sun);
            else if (i % 5 == 4)
                viewHolder.itemView.setBackgroundResource(R.drawable.grad_pink_red);

            TextView item = holder.contactName;
            item.setText(contactList.get(position).toString());
//            contactList.add("str");
        }catch (Exception e)
        {
            Log.d("dbsvm","Inside RecyclerViewAdapter\n" +
                    "Error : "+e.getMessage().toString());
        }
//        String contact = contactList.get(position);
//        for(String s: HOMEcustomerGroupListView.a)
//        {
//            holder.contactName.setText(contactList.get(0));
//        holder.contactName.setText(contactList.get(1));

//        }

    }

    // How many items?
    @Override
    public int getItemCount() {

        return contactList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView contactName;
        private static final long TIME_INTERVAL_GAP=500;
        private long lastTimeClicked=System.currentTimeMillis();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            contactName = itemView.findViewById(R.id.name);
//            phoneNumber = itemView.findViewById(R.id.phone_number);
//            iconButton = itemView.findViewById(R.id.icon_button);

//            iconButton.setOnClickListener(this);
        }


//        @Override
//        public void onClick(View view) {
//            //Log.d("ClickFromViewHolder", "Clicked");
//
//        }
        @Override
        public void onClick(View view) {
            long now=System.currentTimeMillis();
            if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                return;
            lastTimeClicked=now;
//            //Log.d("ClickFromViewHolder", "Clicked");
            int position = this.getAbsoluteAdapterPosition();
//            String name = contact;
            String table_group_name_added_before="",table_config_name_added_before="";
            String db_name = contactList.get(position);
            if(context instanceof HOMEcustomerGroupListView )//if HomecutomerGroupListViewCalls  then
            {
                Params.INDIVIDUAL_PERSON_DATA_TABLE_NAME_ACCESS_TELLER="";
//            Params.DB_NAME=db_name;//Change database to Customer group clicked
//                Params.groupNameToAddBeforeDefaultTableNamesReceived=Params.DB_NAME;
            Params.Curent_Running_Db=db_name;
//                associate all data with group name, it includes all table along configuration table
//                Params.DB_NAME=db_name;//Change database to Customer group clicked,
//                table_group_name_added_before = contactList.get(position);
//                Params.groupNameToAddBeforeDefault_group_TableNamesReceived=table_group_name_added_before;
//            String name = contact;
//            Params.toAddBeforeNewTableName=groupDatabaseName;

                //Log.d("dbsvm","Current table : "+Params.DATA_TABLE_NAME);

//                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
                Params.groupNameToAddBeforeDefault_group_TableNamesReceived=db_name;
                //Log.d("dbsvm","Current configuration table : "+Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE);
//                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefaultTableNamesReceived+"_"+Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
                Params.DB_NAME=db_name;
                Params.CURRENT_GROUP_DB_NAME=db_name;
                context.startActivity(new Intent(context, HomeConfigureCustomerGroupDataWithMonthAndYear.class));
//                Params.DB_NAME=Params.Curent_Running_Db;
            }
            else if(context instanceof HOMEIndividualPersonDataListView)//if HomecutomerGroupListViewCalls  then
            {
//                Log.d("dbsvm","Choice button pressed - "+Params.youWillGetOrGiveMoneyButtonPressed);
                Params.INDIVIDUAL_PERSON_DATA_TABLE_NAME_ACCESS_TELLER=Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME;
//            Params.DB_NAME=db_name;//Change database to Customer group clicked
//                Params.groupNameToAddBeforeDefaultTableNamesReceived=Params.DB_NAME;
                Params.Curent_Running_Db=db_name;
//                associate all data with group name, it includes all table along configuration table
//                Params.DB_NAME=db_name;//Change database to Customer group clicked,
//                table_group_name_added_before = contactList.get(position);
//                Params.groupNameToAddBeforeDefault_group_TableNamesReceived=table_group_name_added_before;
//            String name = contact;
//            Params.toAddBeforeNewTableName=groupDatabaseName;

                //Log.d("dbsvm","Current table : "+Params.DATA_TABLE_NAME);

//                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.DEFAULT_CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
                Params.groupNameToAddBeforeDefault_group_TableNamesReceived=db_name;//has use in SharePdfIndividualPerson.java
                //Log.d("dbsvm","Current configuration table : "+Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE);
//                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefaultTableNamesReceived+"_"+Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE;
                Params.DB_NAME=db_name;
                Params.CURRENT_GROUP_DB_NAME=db_name;
//                context.startActivity(new Intent(context, HomeConfigureCustomerGroupDataWithMonthAndYear.class));
                try {
                    context.startActivity(new Intent(context, IndividualHomeConfigureCustomerGroupDataWithMonthAndYear.class));
                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Error "+e.getMessage().toString());
                }
//                Params.DB_NAME=Params.Curent_Running_Db;
            }
            else {//context CreateNewConfigureIndividualPersonDattaWithMonthAndYear
                Params.CURRENT_GROUP_CONFIG_DB_NAME=db_name;
//                Params.groupNameToAddBeforeDefault_Config_TableNamesReceived=Params.DB_NAME;
                Params.DB_NAME=db_name;//_ is replaced when shown to user, but database is saved with _ ex:july_2021
//                String storage_folder = "/myDairyApp/"+Params.CURRENT_GROUP_DB_NAME+"/"+Params.CURRENT_GROUP_CONFIG_DB_NAME;
//                String dest
                try {
                    if (Params.INDIVIDUAL_PERSON_DATA_TABLE_NAME_ACCESS_TELLER.substring(0, 20).equals(Params.DEFAULT_INDIVIDUAL_PERSON_DATA_NAME)) {

                        context.startActivity(new Intent(context, IndividualPersonMainActivity.class));
//                        Log.d("dbsvm", "inside indi");
                    } else {
//                        Log.d("dbsvm", "inside main");
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                }
                catch (Exception e)
                {
                        context.startActivity(new Intent(context, MainActivity.class));

                }
//                Path = mContext.getExternalFilesDir(null).getAbsolutePath();
//    Log.d("dbsvm",destPath);
//    File f = new File(destPath, storage_folder);
//                if (!f.exists()) {
//                    f.mkdirs();
//                }
//                //Log.d("dbsvm","\nLocation : "+f.toString());

//                table_config_name_added_before = contactList.get(position);
//                Params.groupNameToAddBeforeDefault_Config_TableNamesReceived=table_config_name_added_before;
//                Params.TABLE_NAME=table_group_name_added_before+"_"+Params.DEFAULT_TABLE_NAME;
//                Params.DATA_TABLE_NAME=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_DATA_TABLE_NAME;
                //Get default customer names data storing table
//                Params.CUSTOMER_NAMES_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_CUSTOMER_NAMES_TABLE;
//                Params.DATA_VALUE_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.groupNameToAddBeforeDefault_Config_TableNamesReceived+"_"+Params.DEFAULT_DATA_VALUE_TABLE;
//                Params.CONFIGURE_CUSTOMER_GROUP_DATA_WITH_MONTH_AND_YEAR_TABLE=Params.groupNameToAddBeforeDefault_group_TableNamesReceived+"_"+Params.TABLE_NAME;

//                context.startActivity(new Intent(context, MainActivity.class));


                //Log.d("dbsvm","Current configuration table : "+Params.CURRENT_GROUP_CONFIG_DB_NAME);
//                Params.DB_NAME=Params.Curent_Running_Db;

            }

//            String phone = contact.getPhoneNumber();
//            Toast.makeText(context, "The position is " + String.valueOf(position) +
//                    " Name: " + name + ", Phone:" + phone, Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(context, displayContact.class);
//            intent.putExtra("Rname", name);
//            intent.putExtra("Rphone", phone);
//            context.startActivity(intent);

        }
    }
}

