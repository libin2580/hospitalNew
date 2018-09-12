package com.zulekhahospitals.zulekhaapp.myaccount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class ChangeAccountPassword extends AppCompatActivity {
    String user_id,old_password;
EditText current_password,new_password,confirm_password;
    Button submit_change_pass;
    ProgressBar progress;
    ImageView back;
    int flag=0;
    int error_status=0;
    boolean isConnected=false;
    String str_current_password,str_new_password,str_confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_password);
        analytics = FirebaseAnalytics.getInstance(ChangeAccountPassword.this);
        analytics.setCurrentScreen(ChangeAccountPassword.this,ChangeAccountPassword.this.getLocalClassName(), null /* class override */);

        current_password=(EditText)findViewById(R.id.current_password) ;
        new_password=(EditText)findViewById(R.id.new_password) ;
        confirm_password=(EditText)findViewById(R.id.confirm_password) ;
        submit_change_pass=(Button)findViewById(R.id.submit_change_pass);
        progress=(ProgressBar)findViewById(R.id.progress);
        back= (ImageView) findViewById(R.id.back_image);


        user_id=getIntent().getStringExtra("user_id");
        SharedPreferences preferencesd= getSharedPreferences("MyPref", MODE_PRIVATE);
        old_password = preferencesd.getString("password", null);
        System.out.println("old_password : "+old_password);
        user_id=preferencesd.getString("user_id",null);
        System.out.println("userid : "+user_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChangeAccountPassword.this.goBack();
            }
        });


        submit_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_current_password=current_password.getText().toString();
                        str_new_password=new_password.getText().toString();
                str_confirm_password=confirm_password.getText().toString();

                if (str_current_password.isEmpty() && str_new_password.isEmpty() && str_confirm_password.isEmpty()) {
                   /* Toast.makeText(getApplicationContext(),"fill all fields",Toast.LENGTH_SHORT).show();*/
                    Snackbar.with(ChangeAccountPassword.this,null)
                            .type(Type.ERROR)
                            .message("Please Fill all the Fields")
                            .duration(Duration.LONG)

                            .show();
                    error_status = 0;
                    current_password.requestFocus();


                } else {

                    if (str_current_password.isEmpty()) {

                        /*Toast.makeText(getApplicationContext(),"current passsword can't be empty",Toast.LENGTH_SHORT).show();*/
                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("Current password can't be empty")
                                .duration(Duration.LONG)

                                .show();
                        error_status = 0;

                    }
                    if (str_new_password.isEmpty()) {

                        /*Toast.makeText(getApplicationContext(),"new password can't be empty",Toast.LENGTH_SHORT).show();*/
                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("New password can't be empty")
                                .duration(Duration.LONG)

                                .show();
                        error_status = 0;
                    }
                    if (str_confirm_password.isEmpty()) {

                       /* Toast.makeText(getApplicationContext(),"confirm password can't be empty",Toast.LENGTH_SHORT).show();*/
                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("Confirm password can't be empty")
                                .duration(Duration.LONG)

                                .show();
                        error_status = 0;
                    }

            /*if (str_confirm_password.isEmpty()) {
                confirm_password.setError("confirm your password");

                error_status = 0;
            }*/
                }

                if (!str_current_password.isEmpty() &&!str_new_password.isEmpty() && !str_confirm_password.isEmpty() ) {
                     if (str_current_password.length()<8 || str_current_password.length()>15) {

                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("Current password must contain minimum 8 and maximum 15 character")
                                .duration(Duration.LONG)

                                .show();
                         error_status = 0;
                         current_password.requestFocus();
                    }
                    else if (str_new_password.length()<8 || str_new_password.length()>15) {


                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("New password must contain minimum 8 and maximum 15 character")
                                .duration(Duration.LONG)

                                .show();
                         error_status = 0;
                    }
                     else if (str_confirm_password.length()<8 || str_confirm_password.length()>15) {


                         Snackbar.with(ChangeAccountPassword.this,null)
                                 .type(Type.ERROR)
                                 .message("confirm password must contain minimum 8 and maximum 15 character")
                                 .duration(Duration.LONG)

                                 .show();
                         error_status = 0;
                     }

                    else if (!str_current_password.equals(old_password)) {
                       /* Toast.makeText(getApplicationContext(),"incorrect old password",Toast.LENGTH_SHORT).show();*/
                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("Incorrect current password")
                                .duration(Duration.LONG)

                                .show();

                        error_status = 0;
                    }
                    else
                    if(!str_new_password.equals(str_confirm_password))
                    {

                        /*Toast.makeText(getApplicationContext(),"new password and confrim password doesn't match",Toast.LENGTH_SHORT).show();*/
                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("new password and confrim password doesn't match")
                                .duration(Duration.LONG)

                                .show();
                        new_password.requestFocus();
                        error_status=0;

                    }
                    else {
                        error_status = 1;
                    }


                }

                if (error_status == 1) {
                    /*Toast.makeText(getApplicationContext(),"No Error",Toast.LENGTH_LONG).show();*/

/* CHECKING INTERNET CONNECTION -- START*/
                    ConnectivityManager cm =
                            (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();
/* CHECKING INTERNET CONNECTION -- END*/

                    if (isConnected) {
                        new SendToServer().execute();
                        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    } else {
                       /* Toast.makeText(getApplicationContext(), "Please enable internet connection.", Toast.LENGTH_SHORT).show();*/
                        Snackbar.with(ChangeAccountPassword.this,null)
                                .type(Type.ERROR)
                                .message("Enable internet connection")
                                .duration(Duration.LONG)

                                .show();
                        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                }


            }
        });

    }

    /* CHECKING INTERNET CONNECTION -- START*/
    public boolean ConnectionCheck(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return  isConnected;
    }
/* CHECKING INTERNET CONNECTION -- end*/
private class SendToServer extends AsyncTask<String,String,String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            /*progress.setVisibility(View.VISIBLE);*/
        progress.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(BaseUrl+"android/changepassword.php");

            JSONObject postDataParams=new JSONObject();
            postDataParams.put("old_pswd",old_password);
            postDataParams.put("new_pswd",str_new_password);
            postDataParams.put("user_id",user_id);

            System.out.println("old_pswd"+old_password);
            System.out.println("new_pswd"+str_new_password);
            System.out.println("user_id"+user_id);

            Log.e("params",postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            }
            else {
                return new String("false : "+responseCode);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            /*progress.setVisibility(View.GONE);*/
            progress.setVisibility(View.GONE);
            /*Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/
            System.out.println("result : "+result);
            JSONObject jsonObj = new JSONObject(result);


            String status = jsonObj.getString("status");
            if (status.equalsIgnoreCase("true")) {


                Toast.makeText(getApplicationContext(), "Successfully Changed", Toast.LENGTH_LONG).show();
           /* Snackbar.with(ChangeAccountPassword.this,null)
                    .type(Type.SUCCESS)
                    .message("Success")
                    .duration(Duration.LONG)

                    .show();*/
                SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                intent.putExtra("brn_id", "1");
                startActivity(intent);


            } else {
            /*Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();*/
                Snackbar.with(ChangeAccountPassword.this, null)
                        .type(Type.ERROR)
                        .message("Failed")
                        .duration(Duration.LONG)

                        .show();

            }

        }
    catch (Exception e){

    }
    }
}
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    private void goBack() {
        super.onBackPressed();
    }
}
