package com.datahiveshield.mydata.mydairy.khata.files;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDBHandlerIndividualPersonData;
import com.datahiveshield.mydata.mydairy.khata.files.data.MyDbHandlerIndividualPersonConfigs;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SettingsApp extends AppCompatActivity {
    SharedPreferences yourDetailsForMsgSharedPref;
    SharedPreferences.Editor yourDetailsSPrefEditor;
    private String yourName;
    private String yourPhoneNum;



    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_app);

        yourDetailsForMsgSharedPref= getSharedPreferences("yourDetailsForMsgSharedPref",MODE_PRIVATE);
        yourDetailsSPrefEditor=yourDetailsForMsgSharedPref.edit();

        yourName=yourDetailsForMsgSharedPref.getString("yourName","");
        yourPhoneNum=yourDetailsForMsgSharedPref.getString("yourPhoneNum","");

        EditText editTextYourName=findViewById(R.id.editTextYourName);
        editTextYourName.requestFocus();
        EditText editTextYourPhoneNum=findViewById(R.id.editTextYourPhoneNum);
        editTextYourName.setText(yourName);
        editTextYourPhoneNum.setText(yourPhoneNum);

        Button saveDetail=findViewById(R.id.saveDetails);
        saveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    yourDetailsSPrefEditor.putString("yourName", editTextYourName.getText().toString().trim());
                    yourDetailsSPrefEditor.commit();
                    yourDetailsSPrefEditor.putString("yourPhoneNum", (editTextYourPhoneNum).getText().toString().trim());
                    yourDetailsSPrefEditor.commit();

                    Toast.makeText(SettingsApp.this, "New Information Updated Successfully.",
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.d("dbsvm","Inside settings unable to save user contact information, Error : "+e.getMessage());
                }

            }
        });

    }
}
