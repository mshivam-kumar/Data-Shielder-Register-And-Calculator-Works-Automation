package com.datahiveshield.mydata.mydairy.khata.files;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.datahiveshield.mydata.mydairy.khata.files.params.Params;

public class ChooseMoneyWillGiveOrGetIndividualPersonGroup extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_moneywillgiveorget_individual_person_data);

        //Set back button to go to previous activity
        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button youWillGiveMoney=findViewById(R.id.youWillGiveMoney);
        Button youWillGetMoney=findViewById(R.id.youWillGetMoney);
        youWillGetMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Params.youWillGetOrGiveMoneyButtonPressed=youWillGetMoney.getText().toString();
                Params.YOU_WILL_GET_MONEY_BUTTON_VALUE=Params.youWillGetOrGiveMoneyButtonPressed;
                Log.d("dbsvm","Button pressed "+Params.youWillGetOrGiveMoneyButtonPressed);
                startActivity(new Intent(ChooseMoneyWillGiveOrGetIndividualPersonGroup.this,HOMEIndividualPersonDataListView.class));
            }
        });

        youWillGiveMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Params.youWillGetOrGiveMoneyButtonPressed=youWillGiveMoney.getText().toString();
                Params.YOU_WILL_GET_MONEY_BUTTON_VALUE="";
                Log.d("dbsvm","Button pressed "+Params.youWillGetOrGiveMoneyButtonPressed);
                startActivity(new Intent(ChooseMoneyWillGiveOrGetIndividualPersonGroup.this,HOMEIndividualPersonDataListView.class));
            }
        });
    }
}
