//package com.datahiveshield.mydata.mydairy.khata.files;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.HandlerThread;
//import android.os.Looper;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//public class StartUpImageActivity extends Activity {
//    private Thread mSplashThread;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.startup_image_activity);
//
////        try {
////            Thread.sleep(1000);
////        } catch(InterruptedException e) {
////            // Process exception
////            //Log.d("dbsvm","\nError: "+e.getMessage().toString());
////        }
////        startActivity(new Intent(StartUpImageActivity.this,HOMEcustomerGroupListView.class));
//
//        //Going to GoogleSignIn1
//        startActivity(new Intent(StartUpImageActivity.this,GoogleSignIn1.class));
//        finish();
//
//    }
//
//
////        final  StartUpImageActivity splashScreen=this;
////        mSplashThread=new Thread() {
////            @Override
////            public void run(){
////                try {
////                    synchronized(this){
////                        // Wait given period of time or exit on touch
////                        wait(3000);
////                    }
////                }
////                catch(InterruptedException ex){
////                }
////
////                finish();
////
////                // Run next activity
////                Intent intent = new Intent();
////                intent.setClass(StartUpImageActivity.this, HOMEcustomerGroupListView.class);
////                startActivity(intent);
////            }
////        };
////
////        mSplashThread.start();
////
////    }
//
//}
