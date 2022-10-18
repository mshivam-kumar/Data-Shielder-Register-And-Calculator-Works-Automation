package com.datahiveshield.mydata.mydairy.khata.files;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datahiveshield.mydata.mydairy.khata.files.params.Params;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.api.services.drive.Drive;

import java.io.File;

public class SaveToGoogleDrive extends AppCompatActivity {
   private static final long TIME_INTERVAL_GAP=500;
    private long lastTimeClicked=System.currentTimeMillis();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_data_folder_to_google_drive);


//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//
////                File pdfFile = new File(Environment.getExternalStoragePublicDirectory
////                        (Environment.DIRECTORY_DOWNLOADS), "Your file");
//        try {
//
//            File pdfFile = new File(context.getExternalFilesDir(null).getAbsolutePath().getPath(), "share.pdf");
//            Uri uri = Uri.fromFile(pdfFile);
//
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.setType("application/pdf");
//
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            startActivity(Intent.createChooser(shareIntent, "Share via"));
//
//            //Can not implement here as it deletes the file just after sharing invocation
//            //so file will become null to share, we can not share that (no file)
////                    try {
////
////                        String path = context.getExternalFilesDir(null).getAbsolutePath() + "/share.pdf";
////                        //Log.d("dbsvm", "/nshare pdf file Path to delete : " + path);
////
//////        File dir = new File(storage_folder);
////                        File file = new File(path);
////                        file.delete();
////                    }
////                    catch (Exception e)
////                    {
////                        //Log.d("dbsvm","Unable to delete share pdf file\n" +
////                                "Error : "+e.getMessage().toString());
////                    }
//
//        } catch (Exception e) {
//            //Log.d("dbsvm", "Error in sharing \n" +
//                    "Error : " + e.getMessage().toString());
//        }

        Button saveDataToGoogleDrive=findViewById(R.id.save_data_to_drive);
        saveDataToGoogleDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;

                try {
                    //Log.d("dbsvm","Inside SaveToGoogleDrive.java save to google drive button clicked");
//
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("application/plain");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( SaveToGoogleDrive.this.getExternalFilesDir(null).getAbsolutePath() + "/." + Params.EmailId));
                    startActivity(Intent.createChooser(intent, ""));
                }catch (Exception e)
                {
                    //Log.d("dbsvm","\nError : "+e.getMessage().toString());
                }
            }
        });

    }

}
