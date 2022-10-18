package com.datahiveshield.mydata.mydairy.khata.files;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.datahiveshield.mydata.mydairy.khata.files.alertMessage.GetAlerts;
import com.datahiveshield.mydata.mydairy.khata.files.params.Params;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


public class GoogleSignIn1 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    SignInButton signInButton;
    private static final int SIGN_IN = 1;
    //    ActivityMainBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    Boolean connected = false;//temporary set to true, checking methods left to implement
//    Boolean flagLastAccountLogInExitst = false;
    ProgressDialog dialog;

    public static String personName="",personEmail="";
    SharedPreferences sharedPreferences,sPreferencehandleUserPermissions;
    SharedPreferences.Editor UserSignInInfo,permissionAskedBeforeData;
    public static Uri personPhoto;
    public static boolean guestSignIn_success=false;
    public static int REQUEST_PERMISSION=1;
    public static boolean permissionGranted=false;

    public static boolean askedBeforeForPermission=false;
    PermissionListener permissionlistener;
    private AlertDialog permissionDenied_DoNotAskAgain_dialog;

   private static final long TIME_INTERVAL_GAP=500;
   private long lastTimeClicked=System.currentTimeMillis();

   public static String appStoragefolder;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding=ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.google_sign_in);
//


        appStoragefolder=GoogleSignIn1.this.getExternalFilesDir(null).getAbsolutePath();

        guestSignIn_success=false;//Initially making false to handles intents that come
        //from other class


         sPreferencehandleUserPermissions= getSharedPreferences("sPreferencehandleUserPermissions",MODE_PRIVATE);
// Creating an Editor object to edit(write to the file)
        permissionAskedBeforeData =  sPreferencehandleUserPermissions.edit();
//        permissionAskedBeforeData.putBoolean("askedBeforeForPermission",false);
//         permissionAskedBeforeData.putBoolean("permissionGrantedByUser",false);
//        permissionAskedBeforeData.commit();

//         permissionAskedBeforeData.putBoolean("permissionGrantedByUser",false);
        @SuppressLint("WrongConstant") SharedPreferences getsPreferencehandleUserPermissions = getSharedPreferences("sPreferencehandleUserPermissions",MODE_APPEND);

//        permissionAskedBeforeData.
        askedBeforeForPermission=getsPreferencehandleUserPermissions.getBoolean("askedBeforeForPermission",false);
         permissionGranted=getsPreferencehandleUserPermissions.getBoolean("permissionGrantedByUser",false);

//        if(isStoragePermissionGranted())
//        {
//            //Log.d("dbsvm","Permission granted");
//        }
//        else
//        {
//
        if(!askedBeforeForPermission) {
//            ActivityCompat.requestPermissions(GoogleSignIn1.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

            permissionGranted = isStoragePermissionGranted();
            permissionAskedBeforeData.putBoolean("askedBeforeForPermission",true);
            permissionAskedBeforeData.commit();
        }
        else if(!TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE) && askedBeforeForPermission)
        {

            ActivityCompat.requestPermissions(GoogleSignIn1.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

            request_permissionDenied_DoNotAskAgain();

            permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
            permissionAskedBeforeData.putBoolean("permissionGrantedByUser",permissionGranted);
            permissionAskedBeforeData.commit();
            //Log.d("pgranted","After permission denied method call for settings\n" +
//                    "Permission Granted :"+permissionGranted);


        }
            //Log.d("pgranted","Permission Granted : "+permissionGranted+"\n" +
//                    "Asked before for permission : "+askedBeforeForPermission);



//
//        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            permissionGranted = isStoragePermissionGranted();
//
//        requestDoNotAskAgainPermission(GoogleSignIn1.this);
//            if (!permissionGranted)
//                requestStoragePermission();

//        }
        TextView appName=findViewById(R.id.appName);
        try
        {
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/GloriaHallelujah-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Meddon-Regular.ttf");
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/RuslanDisplay-Regular.ttf");

            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/BethEllen-Regular.ttf");

//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/Monofett-Regular.ttf");//3d text
//            Typeface myFont=Typeface.createFromAsset(this.getAssets(),"fonts/TradeWinds-Regular.ttf")
            appName.setText(Params.AppName);
            appName.setTypeface(myFont);
        }catch (Exception e)
        {
            Log.d("dbsvm","unable to change font of app name in HOME");
        }
         sharedPreferences = getSharedPreferences("UserSignInInfoSharedPref",MODE_PRIVATE);
// Creating an Editor object to edit(write to the file)
         UserSignInInfo = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
//        myEdit.putString("name", name.getText().toString());
//        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));

        GetAlerts ga = new GetAlerts();

        checkInternetConnectionStatus();//connected variable will be updated,which tells
        //whether user is connected to internet or not

        // Build a GoogleSignInClient with the options specified by gso.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

//        Environment.getDataDirectory()
        if (account != null) {
            if(TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("UserSignInInfoSharedPref",MODE_APPEND);
//                String userName=sharedPreferences.getString("name","");
                Log.d("dbsvm","Existing id "+Params.EmailId);
                Params.EmailId=sharedPreferences.getString("email","");
//                Log.d("dbsvm","Existing id "+Params.EmailId);
                startActivity(new Intent(GoogleSignIn1.this, HOMEcustomerGroupListView.class));
//                finish();
            }
            else
            {
                request_permissionDenied_DoNotAskAgain();
                permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionAskedBeforeData.putBoolean("permissionGrantedByUser",permissionGranted);
                permissionAskedBeforeData.commit();
                //Log.d("pgranted","After permission denied method call for settings\n" +
//                        "Permission Granted :"+permissionGranted);

            }
//            flagLastAccountLogInExitst = true;
        }
        // Set the dimensions of the sign-in button.

        //If user has allowed the permissions at first attempt only, the
        //update permission granted to true, implementing so below from isPermission granted
        //method ,so that control does not pass this for previous value, as flow of program
        //does not wait for user response for alert box
        //updated in isPermissionGranted method
//        permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
//        permissionAskedBeforeData.putBoolean("permissionGrantedByUser",permissionGranted);
//        permissionAskedBeforeData.commit();
//        //Log.d("pgranted","After permission denied method call for settings\n" +
//                "Permission Granted :"+permissionGranted);




//        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

//        ProgressDialog pd = new ProgressDialog(GoogleSignIn1.this);
//        pd.setTitle("Loading...");
//        pd.setMessage("Please wait.");
//        pd.setCancelable(false);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
                try {

//                    pd.show();
//                        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//                      dialog = ProgressDialog.show(GoogleSignIn1.this, "Loading", "Please wait...", true);

                        if (connected) {
//                            Log.d("dbsvm","tried to log in with mail");
                            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                            startActivityForResult.launch(signInIntent);
                        } else {
//                                dialog.dismiss();
                            ga.alertDialogBox(GoogleSignIn1.this, "No internet connection available!", "Unable To Connect");
                           handleInternetConnectionStatus();
                           return;
                        }

                } catch (Exception e) {

                    //Log.d("dbsvm", "Error : " + e.getMessage().toString());
                }

//                pd.dismiss();

//                checkInternetConnectionStatus();//To give better performance
                //method is called after the required operations,if user connects to
                //net in middle and press log in , so in second time it will get notify
//                 checkInternetConnectionStatus();
            }
        });

        Button signInGuest = findViewById(R.id.signInGuest);
        signInGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now=System.currentTimeMillis();
                if(now-lastTimeClicked<TIME_INTERVAL_GAP)
                    return;
                lastTimeClicked=now;
//                    boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

                guestSignIn_success=true;
                GoogleSignIn1.personName="Guest Sign In";
                Params.EmailId="DataShielderAppGuestSignIn";//Default folder to save guest sign in data in memory
                UserSignInInfo.putString("name",personName);
                UserSignInInfo.putString("email", "");
                UserSignInInfo.commit();

                if(TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            startActivity(new Intent(GoogleSignIn1.this, HOMEcustomerGroupListView.class));
//                            Toast.makeText(GoogleSignIn1.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            finish();

                }
                else
                {
//                    handleUserPermission();
                    //Log.d("pgranted","Before permission denied method call for settings\n" +"Permission Granted :"+permissionGranted);

                    request_permissionDenied_DoNotAskAgain();
                    permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
                    permissionAskedBeforeData.putBoolean("permissionGrantedByUser",permissionGranted);
                    permissionAskedBeforeData.commit();
                    //Log.d("pgranted","After permission denied method call for settings\n" +"Permission Granted :"+permissionGranted);


                }
            }
        });

    }



//    private void handleUserPermission() {
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .check();
//
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);
                    }
                }
            }
    );

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
//                String personName = account.getDisplayName();
                 personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
//                String personEmail = account.getEmail();
                 personEmail = account.getEmail();
                String personId = account.getId();
                 personPhoto = account.getPhotoUrl();
//                Toast.makeText(this, "Email: " + personEmail, Toast.LENGTH_SHORT).show();
                //If log in successfull, then store user data with email id name hidden folder in

                //Store image in shared prefernces
//                Bitmap realImage = BitmapFactory.decodeStream(personPhoto);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] b = baos.toByteArray();
//                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                //Retreiving image
//                String previouslyEncodedImage = shre.getString("image_data", "");
//
//                if( !previouslyEncodedImage.equalsIgnoreCase("") ){
//                    byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//                    imageConvertResult.setImageBitmap(bitmap);
//                }

                    UserSignInInfo.putString("name", personName);
                    UserSignInInfo.putString("email", personEmail);
//                UserSignInInfo.putString("profileImage",encodedImage);
                    UserSignInInfo.commit();

                    //user internal storage
                    Params.EmailId = personEmail;
//                startActivity(new Intent(GoogleSignIn1.this, HOMEcustomerGroupListView.class));

                if(TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    startActivity(new Intent(GoogleSignIn1.this, HOMEcustomerGroupListView.class));

                    //To check google drive operations
//                    startActivity(new Intent(GoogleSignIn1.this,GDrive.class));

                    finish();
                }
                else
                {
//                    handleUserPermission();
//                    requestStoragePermission();
                    request_permissionDenied_DoNotAskAgain();
                    permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
                    permissionAskedBeforeData.putBoolean("permissionGrantedByUser",permissionGranted);
                    //Log.d("pgranted","After permission denied method call for settings\n" +"Permission Granted :"+permissionGranted);

                }


            }
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
//            Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show();

            //If unable to log in with google , then store user data in
            //DataShilederApp name hidden folder
            Params.EmailId = "DataShielderAppGuestSignIn";
            guestSignIn_success=true;
//            startActivity(new Intent(GoogleSignIn1.this, HOMEcustomerGroupListView.class));


                startActivity(new Intent(GoogleSignIn1.this,HOMEcustomerGroupListView.class));



            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.d("dbsvm", "signInResult:failed code=" + e.getStatusCode());
            finish();
        }
    }



    //To check internet connection status
    public void checkInternetConnectionStatus() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(GoogleSignIn1.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

    }



    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.d("dbsvm","Permission is granted");
//                permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
//                permissionAskedBeforeData.putBoolean("permissionGrantedByUser",true);
//                permissionAskedBeforeData.commit();
//                permissionGranted=true;
//                //Log.d("pgranted","After permission denied method call for settings\n" +
//                        "Permission Granted :"+permissionGranted);
                return true;
            } else {

                Log.v("dbsvm","Permission is revoked");
                ActivityCompat.requestPermissions(GoogleSignIn1.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("dbsvm","Permission is granted");
            return true;
        }
    }








    public  void request_permissionDenied_DoNotAskAgain()
    {
//        permissionGranted=TedPermission.isGranted(GoogleSignIn1.this,Manifest.permission.READ_EXTERNAL_STORAGE);
//        permissionAskedBeforeData.putBoolean("permissionGrantedByUser",permissionGranted);
//        permissionAskedBeforeData.commit();
//        //Log.d("pgranted","Inside permission denied method executed before method call for settings\n" +
//                "Permission Granted :"+permissionGranted);


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Permission needed");
//        alertDialogBuilder.setMessage("Permission needed to save  data");
            alertDialogBuilder.setMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]");
            alertDialogBuilder.setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", GoogleSignIn1.this.getPackageName(),
                            null);
                    intent.setData(uri);
                    GoogleSignIn1.this.startActivity(intent);
                    //Log.d("pgranted", "Inside do not ask again  permission method ");


                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
//                        request_permissionDenied_DoNotAskAgain();
                    //Log.d("dbsvm", "onClick: Cancelling");
                }
            });

             permissionDenied_DoNotAskAgain_dialog = alertDialogBuilder.create();
        permissionDenied_DoNotAskAgain_dialog.show();

    }

    public void handleInternetConnectionStatus()
    {
        String msg="No internet connection available!";
        String title="Unable To Connect";
        AlertDialog.Builder dialog = new AlertDialog.Builder(GoogleSignIn1.this);
//        dialog.setMessage("Please insert appropriate name and phone number");
        dialog.setMessage(msg);

//        dialog.setTitle("Dialog Box");
        dialog.setTitle(title);

//       dialog.setPositiveButton("YES",
        dialog.setPositiveButton("Try Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
//                                            checkInternetConnectionStatus();
                        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(GoogleSignIn1.CONNECTIVITY_SERVICE);
                        NetworkInfo nInfo = cm.getActiveNetworkInfo();
                        connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                                            if(!connected)
                                            {
                                                handleInternetConnectionStatus();
                                            }
                                            else
                                            {
                            Toast.makeText(getApplicationContext(),"Connected To Internet",Toast.LENGTH_LONG).show();

                                            }

                    }
                });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Cancel is clicked", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

            finish();
    }


}
