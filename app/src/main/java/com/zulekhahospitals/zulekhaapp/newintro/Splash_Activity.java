package com.zulekhahospitals.zulekhaapp.newintro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.branches.Screen_Selection_Fragment;
import com.zulekhahospitals.zulekhaapp.firebase.app.Config;
import com.zulekhahospitals.zulekhaapp.firebase.util.NotificationUtils;
import com.zulekhahospitals.zulekhaapp.login.TestLoginActivity;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by libin on 10/18/2016.
 */

public class Splash_Activity extends Activity {
    int status=0;
    String insatalled_app_version="",latestVersion="";
    public Splash_Activity() {
    }
    TextView tv;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String refreshedToken;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splsh_layout);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tv=(TextView) findViewById(R.id.textView22);
       /* while(x<=5){
            y=x++;
            --y;
            ++x;
            System.out.println("++++++++++++++++11111111111+++++++++++++++"+x);
            System.out.println("++++++++++++++++22222222222+++++++++++++++"+y);
        }*/
       // System.out.println("++++++++++++++++xxxxxxxxxxx+++++++++++++++"+x);
     //   System.out.println("++++++++++++++++yyyyyyyyyyyyyyyy+++++++++++++++"+y);

        try {
            PackageInfo info1 = getPackageManager().getPackageInfo(
                    "com.zulekhahospitals.zulekhaapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info1.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        String info="Zulekha Hospital "+getVersionInfo();
        System.out.println("+++++++++++++++++++++++++++++++"+info);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken() (String authorizedEntity, String scope);

        System.out.println("<<<<<<<<<<<<<<<<<<refreshedToken>>>>>>>>>>>>>>>>>>>>>>>>>"+refreshedToken);
        mRegistrationBroadcastReceiver =
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        // checking for type intent filter
                        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                            // gcm successfully registered
                            // now subscribe to `global` topic to receive app wide notifications
                            FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                            displayFirebaseRegId();

                        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                            // new push notification is received

                            String message = intent.getStringExtra("message");

                            Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                            //tv.setText(message);
                        }
                    }
                };
        displayFirebaseRegId();
        tv.setText(info);
        Thread timer=new Thread(){
            public void run(){

                try{
                    sleep(3000);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                finally{
                    //status=5;
                    Intent i=new Intent(Splash_Activity.this,Main2Activity.class);
                    //i.putExtra("YEAR","");
                    //i.putExtra("value",status);
                    startActivity(i);
                    finish();
                }
            }

        };


        if (DetectConnection
                .checkInternetConnection(Splash_Activity.this)) {
            timer.start();
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    Splash_Activity.this).create();

            // Setting Dialog Title


            // Setting Dialog Message
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Zulekha needs internet connection for proper working.Please turn on your internet.");

            // Setting Icon to Dialog


            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                   finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
/*        Splash_Activity.this.runOnUiThread(new Runnable() {
            public void run() {
                try{
                    insatalled_app_version=getCurrentVersionInfo();
                    VersionChecker versionChecker = new VersionChecker();
                    latestVersion = versionChecker.execute().get();

                    System.out.println("---------------------------------------------------------------------");
                    System.out.println("CURRENT VERSION : "+insatalled_app_version);
                    System.out.println("\nNEW VERSION : "+latestVersion);
                    System.out.println("---------------------------------------------------------------------");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }

                finally{

                    if(!insatalled_app_version.contentEquals(latestVersion))
                    {
                        System.out.println("inside !insatalled_app_version.contentEquals(latestVersion)");
                        SharedPreferences up_pref = getApplicationContext().getSharedPreferences("updationPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = up_pref.edit();
                        editor.putString("updation_available","yes");

                        editor.commit();

                    }
                    else {
                        SharedPreferences up_pref = getApplicationContext().getSharedPreferences("updationPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = up_pref.edit();
                        editor.putString("updation_available","no");
                        editor.commit();

                    }

                }
            }
        });*/

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    private String getVersionInfo() {
        String strVersion = "Version ";

        PackageInfo packageInfo;
        try {
            packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            getApplicationContext().getPackageName(),
                            0
                    );
            strVersion += packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            strVersion = "Unknown";
        }
        return strVersion;
    }
/*
    private String getCurrentVersionInfo() {
        String strVersion="" ;

        PackageInfo packageInfo;
        try {
            packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            getApplicationContext().getPackageName(),
                            0
                    );
            strVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return strVersion;
    }
*/
/*
    public class VersionChecker extends AsyncTask<String, String, String> {

        String newVersion;

        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=com.zulekhahospitals.zulekhaapp&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newVersion;
        }
    }
*/
}