//package com.datahiveshield.mydata.mydairy.khata.files;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.datahiveshield.mydata.mydairy.khata.files.params.Params;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.DexterError;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.PermissionRequestErrorListener;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//
//import net.steamcrafted.loadtoast.LoadToast;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//
////To-Do Task list
////1. Google sign in
////2. Google sign out
////3. Creation of "Example folder" in Google Drive if it does not exist, if exists then listing its content
////4. Deleting the selected file from the "Example folder" (I think radio button could be used here)
////5. Downloading the selected file from the "Example folder" onto the mobile device, into a folder called "Example Download" (I think radio button could be used here)
////        -uploading the selected file from the "Example Download" into the "Example" folder (I think radio button could be used here)
////6. Delete the file from the "Example Download" folder
//
////generate SHA1 for debug only
////keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
//public class GDriveMain extends AppCompatActivity {
//
//    private static final String TAG = "GDrive";
//
//    private static final int REQUEST_CODE_SIGN_IN = 1;
//    private static final int PICK_FILE_REQUEST = 100;
//
//    static GoogleDriveServiceHelper mDriveServiceHelper;
//    static String folderId = "";
//
//    private Button signInButton;
//    private Button createFolderButton;
//    private Button folderFilesButton;
//    private Button uploadFileButton;
//    private Button signOutButton;
//    private Button createSubFolderButton;
//    GoogleSignInClient googleSignInClient;
//    LoadToast loadToast;
//    private Drive googleDriveService;
//    public static String folderIdToCreateFolderInside;
//    private GoogleSignInClient mGoogleSignInClient;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gdrive);
//
//        TextView loggedInEmailId = findViewById(R.id.loggedInEmailId);
//        loggedInEmailId.setText("mail-id : " + Params.EmailId);
//        requestForStoragePermission();
//        signInButton = findViewById(R.id.id_sign_in);
//        createFolderButton = findViewById(R.id.id_create_folder);
//        folderFilesButton = findViewById(R.id.id_folder_files);
//        uploadFileButton = findViewById(R.id.id_upload_file);
//        signOutButton = findViewById(R.id.id_sign_out);
//        createSubFolderButton = findViewById(R.id.createSubFolder);
//
//        loadToast = new LoadToast(this);
//    }
//
//
//    // Read/Write permission
//    private void requestForStoragePermission() {
//        Dexter.withContext(this)
//                .withPermissions(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        // check if all permissions are granted
//                        if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
//                            requestSignIn();
//                        }
//
//                        // check for permanent denial of any permission
//                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            // show alert dialog navigating to Settings
//                            showSettingsDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).
//                withErrorListener(new PermissionRequestErrorListener() {
//                    @Override
//                    public void onError(DexterError error) {
//                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .onSameThread()
//                .check();
//    }
//
//    /**
//     * Showing Alert Dialog with Settings option
//     * Navigates user to app settings
//     * NOTE: Keep proper title and message depending on your app
//     */
//    private void showSettingsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(GDriveMain.this);
//        builder.setTitle("Need Permissions");
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
//        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
////                openSettings();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//
//    }
//
//    // navigating user to app settings
////    private void openSettings() {
////        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////        Uri uri = Uri.fromParts("package", getPackageName(), null);
////        intent.setData(uri);
////        startActivityForResult(intent, 101);
////    }
//
//
//    /**
//     * Starts a sign-in activity using {@link #REQUEST_CODE_SIGN_IN}.
//     */
//    private void requestSignIn() {
//        Log.d("dbsvm", "Requesting sign-in");
//
////        GoogleSignInOptions signInOptions =
////                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
////                        .requestEmail()
////                        .build();
////        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult.launch(signInIntent);
//
//
//        // The result of the sign-in Intent is handled in onActivityResult.
////        startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
//        Log.d("dbsvm", "signed in");
//    }
//
//
//    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
////                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
////                        handleSignInResult(task);
//                        Intent data=result.getData();
//                        handleSignInResult(data);
//                    }
//                }
//            }
//    );
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
//
//        switch (requestCode) {
//            case REQUEST_CODE_SIGN_IN:
//                if (resultCode == Activity.RESULT_OK && resultData != null) {
//                    handleSignInResult(resultData);
//                    Log.d("dbsvm","signed in");
//                }
//                break;
//
//            case PICK_FILE_REQUEST:
//                if(resultData == null){
//                    //no data present
//                    return;
//                }
//
//                loadToast.setText("Uploading file...");
//                loadToast.show();
//
//                // Get the Uri of the selected file
//                Uri selectedFileUri = resultData.getData();
//                Log.d("dbsvm", "selected File Uri: "+selectedFileUri );
//                // Get the path
//                String selectedFilePath = FileUtils.getPath(this, selectedFileUri);
//                Log.d("dbsvm","Selected File Path:" + selectedFilePath);
//
//
//                if(selectedFilePath != null && !selectedFilePath.equals("")){
//                    if (mDriveServiceHelper != null) {
//                        mDriveServiceHelper.uploadFileToGoogleDrive(selectedFilePath)
//                                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
//                                    @Override
//                                    public void onSuccess(Boolean result) {
//                                        loadToast.success();
//                                        showMessage("File uploaded ...!!");
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        loadToast.error();
//                                        showMessage("Couldn't able to upload file, error: "+e);
//                                    }
//                                });
//                    }
//                }else{
//                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//
//        super.onActivityResult(requestCode, resultCode, resultData);
//    }
//
//    /**
//     * Handles the {@code result} of a completed sign-in activity initiated from {@link
//     * #requestSignIn()}.
//     */
//    private void handleSignInResult(Intent result) {
//        GoogleSignIn.getSignedInAccountFromIntent(result)
//                .addOnSuccessListener(googleAccount -> {
//                    Log.d("dbsvm", "Signed in as " + googleAccount.getEmail());
//
//                    // Use the authenticated account to sign in to the Drive service.
//                    GoogleAccountCredential credential =
//                            GoogleAccountCredential.usingOAuth2(
//                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
//                    credential.setSelectedAccount(googleAccount.getAccount());
//                    googleDriveService =
//                            new Drive.Builder(
//                                    AndroidHttp.newCompatibleTransport(),
//                                    new GsonFactory(),
//                                    credential)
//                                    .setApplicationName("Drive API Migration")
//                                    .build();
//
//                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
//                    // Its instantiation is required before handling any onClick actions.
//                    mDriveServiceHelper = new GoogleDriveServiceHelper(googleDriveService);
//
//                    Log.d("dbsvm","mDriveServiceHelper "+mDriveServiceHelper);
//                    //enable other button as sign-in complete
//                    signInButton.setEnabled(false);
//                    createFolderButton.setEnabled(true);
//                    folderFilesButton.setEnabled(true);
//                    uploadFileButton.setEnabled(true);
//                    signOutButton.setEnabled(true);
//
//                    showMessage("Sign-In done...!!");
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Log.d("dbsvm", "Unable to sign in.", exception);
//                        showMessage("Unable to sign in.");
//                        signInButton.setEnabled(true);
//                    }
//                });
//    }
//
//    // This method will get call when user click on sign-in button
//    public void signIn(View view) {
//        requestSignIn();
//    }
//
//    // This method will get call when user click on create folder button
//    public void createFolder(View view) {
//        Log.d("dbsvm","Inside createFolder");
//        if (mDriveServiceHelper != null) {
//
//        Log.d("dbsvm","Inside createFolder");
//
//            // check folder present or not
//            mDriveServiceHelper.isFolderPresent()
//                    .addOnSuccessListener(new OnSuccessListener<String>() {
//                        @Override
//                        public void onSuccess(String id) {
//                            if (id.isEmpty()){
//                                mDriveServiceHelper.createFolder()
//                                        .addOnSuccessListener(new OnSuccessListener<String>() {
//                                            @Override
//                                            public void onSuccess(String fileId) {
//                                                Log.d("dbsvm", "folder id: "+fileId );
//                                                folderId=fileId;
//                                                folderIdToCreateFolderInside=folderId;
////                                                showMessage("Folder Created with id: "+fileId);
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception exception) {
//                                                showMessage("Couldn't create file.");
//                                                Log.d("dbsvm", "Couldn't create file.", exception);
//                                            }
//                                        });
//                            }else {
//                                folderId=id;
//                                showMessage("Folder already present");
//
//                                //Search by name and type folder
//                                String qStr = "mimeType = 'application/vnd.google-apps.folder' and title = 'myFolder'";
//
////Get the list of Folders
//
////                                FileList fList= null;
////                                try {
////                                    fList = googleDriveService.files().list().setQ(qStr).execute();
////                                } catch (IOException e) {
////                                    e.printStackTrace();
////                                }
////
//////Check that the result is one folder
////                                File folder;
////
////                                if (fList.getFiles().size()==0){
////                                    folder= fList.getFiles().get(0);
////
////                                }
//
//
//                            }
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            showMessage("Couldn't create file..");
//                            Log.d("dbsvm", "Couldn't create file..", exception);
//                        }
//                    });
//        }
//    }
//
//    // This method will get call when user click on create folder button
//    public void createSubFolder(View view) {
//        Log.d("dbsvm","Inside createSubFolder");
//        try {
//            mDriveServiceHelper.createSubFolder1(GDriveMain.this);
//        }
//        catch (Exception e)
//        {
//            Log.d("dbsvm","unable to tree structure of folder \n" +
//                    "Error "+e.getMessage());
//        }
////        if (mDriveServiceHelper != null) {
////
////            Log.d("dbsvm","Inside createFolder");
////
////            // check folder present or not
////
////
////                                mDriveServiceHelper.createSubFolder()
////                                        .addOnSuccessListener(new OnSuccessListener<String>() {
////                                            @Override
////                                            public void onSuccess(String fileId) {
////                                                Log.d("dbsvm", "folder id: "+fileId );
////                                                folderId=fileId;
//////                                                folderIdToCreateFolderInside=folderId;
//////                                                showMessage("Folder Created with id: "+fileId);
////                                            }
////                                        })
////                                        .addOnFailureListener(new OnFailureListener() {
////                                            @Override
////                                            public void onFailure(@NonNull Exception exception) {
////                                                showMessage("Couldn't create file.");
////                                                Log.d("dbsvm", "Couldn't create file.", exception);
////                                            }
////                                        });
////                                Log.d("dbsvm","new nested folder created");
//////                                folderId=id;
//////                                showMessage("Folder already present");
////
////                                //Search by name and type folder
//////                                String qStr = "mimeType = 'application/vnd.google-apps.folder' and title = 'myFolder'";
////
//////Get the list of Folders
////
//////                                FileList fList= null;
//////                                try {
//////                                    fList = googleDriveService.files().list().setQ(qStr).execute();
//////                                } catch (IOException e) {
//////                                    e.printStackTrace();
//////                                }
//////
////////Check that the result is one folder
//////                                File folder;
//////
//////                                if (fList.getFiles().size()==0){
//////                                    folder= fList.getFiles().get(0);
//////
//////                                }
////
////
////
////
////                        }
//
//
//    }
//
//
//    // This method will get call when user click on folder data button
//    public void getFolderData(View view) {
//        if (mDriveServiceHelper != null) {
//            Intent intent = new Intent(this, ListActivity.class);
//
//            mDriveServiceHelper.getFolderFileList()
//                    .addOnSuccessListener(new OnSuccessListener<ArrayList<GoogleDriveFileHolder>>() {
//                        @Override
//                        public void onSuccess(ArrayList<GoogleDriveFileHolder> result) {
//                            Log.d("dbsvm", "onSuccess: result: "+result.size() );
//                            intent.putParcelableArrayListExtra("fileList", result);
//                            startActivity(intent);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            showMessage("Not able to access Folder data.");
//                            Log.d("dbsvm", "Not able to access Folder data.", e);
//                        }
//                    });
//        }
//    }
//
//    // This method will get call when user click on upload file button
//    public void uploadFile(View view) {
//
//        Intent intent;
//        if (android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
//            intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
//            intent.putExtra("CONTENT_TYPE", "*/*");
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            Log.d("dbsvm", "uploadFile: if" );
//        } else {
//
////            String[] mimeTypes =
////                    {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
////                            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
////                            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
////                            "text/plain",
////                            "application/pdf",
////                            "application/zip", "application/vnd.android.package-archive"};
//
//            String[] mimeTypes = {
//                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//            };
//            intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
//            intent.setType("application/x-sqlite3");
//            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//            Log.d("dbsvm", "uploadFile: else" );
//        }
//        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
//
//    }
//
//    // This method will get call when user click on sign-out button
//    public void signOut(View view) {
//        if (googleSignInClient != null){
//            googleSignInClient.signOut()
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            signInButton.setEnabled(true);
//                            createFolderButton.setEnabled(false);
//                            folderFilesButton.setEnabled(false);
//                            uploadFileButton.setEnabled(false);
//                            signOutButton.setEnabled(false);
//                            showMessage("Sign-Out is done...!!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            signInButton.setEnabled(false);
//                            showMessage("Unable to sign out.");
//                            Log.d("dbsvm", "Unable to sign out.", exception);
//                        }
//                    });
//        }
//    }
//
//    public void showMessage(String message) {
//        Log.i("dbsvm", message);
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//}