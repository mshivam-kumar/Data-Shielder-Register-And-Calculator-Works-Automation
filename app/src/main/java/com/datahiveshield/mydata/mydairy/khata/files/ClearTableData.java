
package com.datahiveshield.mydata.mydairy.khata.files;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerGroupConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

public class ClearTableData extends Activity {
    Button clearData;
   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear_table_data);


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


        TextView groupAccessed=findViewById(R.id.groupAccessed);
        groupAccessed.setText("Group Accessed : "+Params.CURRENT_GROUP_DB_NAME );
        TextView dataAccessed=findViewById(R.id.infoHeading);
        dataAccessed.setText("Data Accessed : "+Params.CURRENT_GROUP_CONFIG_DB_NAME );


        MyDbHandlerGroupConfigs db=new MyDbHandlerGroupConfigs(ClearTableData.this);
        GetAlerts ga=new GetAlerts();



        clearData=findViewById(R.id.saveDetails);
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                try
                {
                    //function call from MyDbHandlerGroupConfigs to delete table data
                    db.clearTable();
                    String msg="Table data cleared successfully";
                    ga.alertDialogBox(ClearTableData.this,msg,"Cleared");
                }
                catch (Exception e)
                {
                    String msg="No data to clear\n" +
                            "Error:"+e.getMessage().toString();
                    ga.alertDialogBox(ClearTableData.this,msg,"Not cleared");
                }
            }
        });
    }
}
