 package com.zulekhahospitals.zulekhaapp.imagecapture;

 import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
 import com.google.firebase.analytics.FirebaseAnalytics;
 import com.zulekhahospitals.zulekhaapp.BuildConfig;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.permission.CheckAndRequestPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

 import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

 public class ImageActivity extends AppCompatActivity {
     public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=100;
    private static final int MEDIA_TYPE_IMAGE=1;

     public static final String MyPREFERENCES = "MyPrefs" ;
     public static final String ImageLoc = "locationKey";
     public String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME="camera_pics";
    private Uri fileUri;
     private Uri imgUriInsharedPref;
     private Bitmap bitmap2;
    private ImageView imgPreview;
      ImageView btnCapture,imgGallery;

     BitmapFactory.Options options1;
CheckAndRequestPermission cp=new CheckAndRequestPermission();
    private SharedPreferences sharedpreferenc;
     String sharedPrefValue=null;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsec);
         analytics = FirebaseAnalytics.getInstance(ImageActivity.this);
         analytics.setCurrentScreen(ImageActivity.this,ImageActivity.this.getLocalClassName(), null /* class override */);

        imgPreview=(ImageView)findViewById(R.id.picture);
        btnCapture=(ImageView)findViewById(R.id.btncamera);
        imgGallery=(ImageView)findViewById(R.id.btnGallery);

         imgPreview.setVisibility(View.VISIBLE);

cp.checkAndRequestPermissions(this);

/////////////////////// GETTING URI FROM SHARED PREFERENCE--START////////////////
                 sharedpreferenc = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                 sharedPrefValue = sharedpreferenc.getString(ImageLoc, "");
                 System.out.println("--------------------------------");
                 System.out.println("VALUE IN SHAREDPREFERENCE : " + sharedPrefValue);
                 System.out.println("--------------------------------");


                 if (sharedPrefValue != null) {

                     options1 = new BitmapFactory.Options();
                     // downsizing image as it throws OutOfMemory Exception for larger
                     // images
                     options1.inSampleSize = 8;
                     imgUriInsharedPref = Uri.parse(sharedpreferenc.getString(ImageLoc, ""));

                     System.out.println("--------------------------------");
                     System.out.println("VALUE IN imgUriInsharedPref(String->uri) : " + imgUriInsharedPref);
                     System.out.println("--------------------------------");


                     System.out.println("--------------------------------");

                     System.out.println("mCurrentPhotoPath : " + mCurrentPhotoPath);

                     System.out.println("imgUriInsharedPref : " + imgUriInsharedPref);
                     System.out.println("imgUriInsharedPref.toString() : " + imgUriInsharedPref.toString());
                     System.out.println("--------------------------------");


                     bitmap2 = BitmapFactory.decodeFile(imgUriInsharedPref.getPath(), options1);
                     imgPreview.setImageBitmap(bitmap2);

/////////////////////// GETTING URI FROM SHARED PREFERENCE--END////////////////
                 } else {
                     imgPreview.setImageDrawable(getResources().getDrawable(R.drawable.tp_icon));

                 }

                 btnCapture.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         captureImage();
                     }
                 });


                 imgGallery.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent i = new Intent(ImageActivity.this, Gallery.class);
                         startActivity(i);
                     }
                 });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
           /* Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();*/
            Snackbar.with(ImageActivity.this,null)
                    .type(Type.ERROR)
                    .message("Sorry! Your device doesn't support camera")
                    .duration(Duration.LONG)

                    .show();
            // will close the app if the device does't have camera
            finish();
        }
    }

     private void captureImage(){

         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /* fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);*/
         fileUri= FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider",getOutputMediaFile(MEDIA_TYPE_IMAGE));

         System.out.println("--------------------------------");
         System.out.println("FILE URI INSIDE captureImage() : " + fileUri);
         System.out.println("--------------------------------");

         intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
         startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
             }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if(requestCode==CAMERA_CAPTURE_IMAGE_REQUEST_CODE){
             if(resultCode==RESULT_OK){
                 previewCapturedImage();
             }else if(resultCode==RESULT_CANCELED){
               /*  Toast.makeText(getApplicationContext(),"user cancelled image capture",Toast.LENGTH_LONG).show();*/
                /* Snackbar.with(ImageActivity.this,null)
                         .type(Type.ERROR)
                         .message("user cancelled image capture")
                         .duration(Duration.LONG)

                         .show();*/
             }
             else {
                 /*Toast.makeText(getApplicationContext(),"sorry.failed to capture image",Toast.LENGTH_LONG).show();*/
                 Snackbar.with(ImageActivity.this,null)
                         .type(Type.ERROR)
                         .message("sorry.failed to capture image")
                         .duration(Duration.LONG)

                         .show();

             }
         }
     }

     private void previewCapturedImage(){
         try{
                imgPreview.setVisibility(View.VISIBLE);
             BitmapFactory.Options options=new BitmapFactory.Options();
             // downsizing image as it throws OutOfMemory Exception for larger
             // images
             options.inSampleSize = 8;

             /////////////////setting image name in sharedpreferences---start///////////////////////



             System.out.println("--------------------------------");
             System.out.println(" URI IN SHAREDPREFERENCE : "+sharedpreferenc.getString(ImageLoc,""));
             System.out.println("--------------------------------");


             Uri imageUri = Uri.parse(mCurrentPhotoPath);
             File file = new File(imageUri.getPath());
             try {
                 InputStream ims = new FileInputStream(file);
                 imgPreview.setImageBitmap(BitmapFactory.decodeStream(ims));
             } catch (FileNotFoundException e) {
                 return;
             }


             System.out.println("--------------------------------");
             System.out.println("mCurrentPhotoPath : " + mCurrentPhotoPath);
             System.out.println("imageUri : " + imageUri);
             System.out.println("imageUri.getPath() : " + imageUri.getPath());
             System.out.println("--------------------------------");


             sharedpreferenc=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
             SharedPreferences.Editor editor=sharedpreferenc.edit();
             editor.putString(ImageLoc,imageUri.toString());
             editor.commit();

             MediaScannerConnection.scanFile(this,
                     new String[]{fileUri.getPath()}, null,
                     new MediaScannerConnection.OnScanCompletedListener() {
                         public void onScanCompleted(String path, Uri uri) {
                         }
                     });

             ///////////////////setting image name in sharedpreferences---end/////////////////////

           /*  final Bitmap bitmap=BitmapFactory.decodeFile(fileUri.getPath(),options);
             imgPreview.setImageBitmap(bitmap);
             System.out.println("--------------------------------");
             System.out.println("FILE URI IN PREVIEW : "+fileUri);
             System.out.println("--------------------------------");*/
         }catch (NullPointerException e){
             e.printStackTrace();
         }
     }
     /**
      * Checking device has camera hardware or not
      * */
     private boolean isDeviceSupportCamera() {
         if (getApplicationContext().getPackageManager().hasSystemFeature(
                 PackageManager.FEATURE_CAMERA)) {
             // this device has a camera
             System.out.println("--------------------------------");
             System.out.println("DEVICE SUPPORTED");
             System.out.println("--------------------------------");
             return true;
         } else {
             // no camera on this device
             return false;
         }
     }

     /**
      * Here we store the file url as it will be null after returning from camera
      * app
      */
     @Override
     public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
         outState.putParcelable("file_uri",fileUri);
     }


     /*
 * Here we restore the fileUri again
 */

     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
         super.onRestoreInstanceState(savedInstanceState);
         fileUri=savedInstanceState.getParcelable("file_uri");
     }

/**
 * Creating file uri to store image/video
 */

     public Uri getOutputMediaFileUri(int type){
         return Uri.fromFile(getOutputMediaFile(type));
     }

     private  File getOutputMediaFile(int type){


         //////////////
         // External sdcard location
         File mediaStorageDir=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
         System.out.println("--------------------------------");
         System.out.println("MEDIA STORAGE DIR : "+mediaStorageDir);
         System.out.println("--------------------------------");
         // Create the storage directory if it does not exist
         if(!mediaStorageDir.exists()){
             if(!mediaStorageDir.mkdirs()){
                 Log.d(IMAGE_DIRECTORY_NAME,"Oops!failed to create"+IMAGE_DIRECTORY_NAME+" directory");
                 System.out.println("--------------------------------");
                 System.out.println("MEDIA STORAGE DIR NOT EXISTS");
                 System.out.println("--------------------------------");
                 return null;
             }
         }
         String timeStamp=new SimpleDateFormat("yyyyMMdd_HHss", Locale.getDefault()).format(new Date());
         File mediaFile;
         if(type==MEDIA_TYPE_IMAGE){
             mediaFile=new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
             System.out.println("--------------------------------");
             System.out.println("MEDIA FILE : "+mediaFile);
             System.out.println("--------------------------------");
         }
         else{
             return null;
         }

         mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();
         return mediaFile;
     }


     ////////////////////////////////
     public  boolean isStoragePermissionGranted() {
         if (Build.VERSION.SDK_INT >= 23) {
             if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                     == PackageManager.PERMISSION_GRANTED) {
                 Log.v("TAG","Permission is granted");
                 return true;
             } else {

                 Log.v("TAG","Permission is revoked");
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                 return false;
             }
         }
         else { //permission is automatically granted on sdk<23 upon installation
             Log.v("TAG","Permission is granted");
             return true;
         }
     }
     ////////////////////////////


     @Override
     protected void onStart() {
         super.onStart();


         checkAndRequestPermissions();

     }
     private  boolean checkAndRequestPermissions() {
         int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
         int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

         List<String> listPermissionsNeeded = new ArrayList<>();

         if (camera != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(Manifest.permission.CAMERA);
         }
         if (storage != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
         }

         if (!listPermissionsNeeded.isEmpty())
         {
             ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                     (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
             return false;
         }
         return true;
     }
 }
