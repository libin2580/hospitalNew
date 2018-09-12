package com.zulekhahospitals.zulekhaapp.feedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leafle_pdf_Fragment;
import com.zulekhahospitals.zulekhaapp.login.Zulekha_Login_Model;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL2;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class feedbackloginform extends AppCompatActivity {
Button feedback_login;
    static ArrayList<Zulekha_Login_Model> zlm;
    EditText feedback_password,feedback_username;
    String usernam,pass;
    Zulekha_Login_Model zl;
    String user_id,fullname,username,age,email,phone,log_status,patient_type, nationality,gender,dob,u;
    ProgressBar progress;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackloginform);
        analytics = FirebaseAnalytics.getInstance(feedbackloginform.this);
        analytics.setCurrentScreen(feedbackloginform.this,feedbackloginform.this.getLocalClassName(), null /* class override */);

        feedback_login=(Button)findViewById(R.id.feedback_login);
        feedback_password=(EditText)findViewById(R.id.feedback_password);
        progress = (ProgressBar)findViewById(R.id.progress_bar);
        feedback_username=(EditText)findViewById(R.id.feedback_username);
u=getIntent().getStringExtra("branch_id");
        feedback_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DetectConnection
                        .checkInternetConnection(getApplicationContext())) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)
                   /* System.out.println("ussssssssssssssssssssss....i"+edtusrnam.getText().toString());
                    System.out.println("pddddddddddddd....i"+edtpass.getText().toString());*/
                    //  log();
                    //recycler_inflate();
                    //  log1();

                    usernam = feedback_username.getText().toString();
                    pass = feedback_password.getText().toString();
                    if(usernam.matches("") ||pass.matches("")||usernam.toString().isEmpty() ||pass.toString().isEmpty()) {

                        Snackbar.with(feedbackloginform.this, null)
                                .type(Type.ERROR)
                                .message("Empty Fields")
                                .duration(Duration.LONG)

                                .show();
//            nbutton.setBackgroundColor(getResources().getColor(R.color.));



                    }
                    else if (pass.length()<6 || pass.length()>15) {


                        Snackbar.with(feedbackloginform.this,null)
                                .type(Type.ERROR)
                                .message("password must contain minimum 6 and maximum 15 character")
                                .duration(Duration.LONG)

                                .show();

                    }
                    else {
                        new SendPostRequest().execute();
                    }
                } else {
                    Snackbar.with(feedbackloginform.this,null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }


            }
        });

    }
    private class SendPostRequest extends AsyncTask<String, Void, String> {

        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        public String doInBackground(String... arg0) {

            try {

                URL url = new URL(URL2+"login/signin.php"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("username",usernam);
                postDataParams.put("password",pass);

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
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        public void onPostExecute(String result) {
            progress.setVisibility(ProgressBar.GONE);
         /*   Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();*/
            if(result.contentEquals("\"already registerd\"")){
                Snackbar.with(feedbackloginform.this,null)
                        .type(Type.ERROR)
                        .message("already registerd")
                        .duration(Duration.LONG)

                        .show();
            }
            else if(result.equalsIgnoreCase("\"failed\"")){
                Snackbar.with(feedbackloginform.this,null)
                        .type(Type.ERROR)
                        .message("Invalid Credentials")
                        .duration(Duration.LONG)

                        .show();
            }
            else {

               /* Snackbar.with(LoginActivity.this,null)
                        .type(Type.SUCCESS)
                        .message("Login Success")
                        .duration(Duration.LONG)

                        .show();*/
                JSONObject jsonObj = null;
                zlm = new ArrayList<Zulekha_Login_Model>();
                try {
                    // String user_id,fullname,username,photo,,,log_status;
                    zl = new Zulekha_Login_Model();


/*
                        Snackbar.with(LoginActivity.this,null)
                                .type(Type.ERROR)
                                .message("Invalid Username or Password")
                                .duration(Duration.LONG)

                                .show();*/



                    {progress.setVisibility(View.GONE);

                        jsonObj = new JSONObject(result);
                        user_id = jsonObj.getString("user_id");
                        fullname = jsonObj.getString("fullname");
                        gender = jsonObj.getString("gender");
                        age=jsonObj.getString("age");
                        phone=jsonObj.getString("phone");
                        email=jsonObj.getString("email");
                        nationality=jsonObj.getString("nationality");
                        patient_type=jsonObj.getString("patient_type");
                        log_status=jsonObj.getString("log_status");
                        dob=jsonObj.getString("dob");


                        zl.setUser_id(user_id);
                        zl.setFullname(fullname);
                        zl.setUsername(username);
                        zl.setAge(age);
                        zl.setPhone(phone);
                        zl.setEmail(email);
                        zl.setLog_status(log_status);
                        zl.setPatient_type(patient_type);
                        zl.setNationality(nationality);
                        zl.setDob(dob);

                        zlm.add(zl);
                        System.out.println("result" + result);

                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("user_id", user_id);
                        editor.putString("fullname",fullname);
                        editor.putString("username",username );
                        editor.putString("gender",gender );
                        editor.putString("age",age );
                        editor.putString("phone",phone );
                        editor.putString("email",email );
                        editor.putString("nationality",nationality );
                        editor.putString("patient_type",patient_type );
                        editor.putString("password",pass );
                        editor.putString("dob",dob );




                        editor.commit();

                        Intent is = new Intent(getApplicationContext(), LoginActivityFeedBack
                                .class);
                        is.putExtra("brn_id",u);



                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(feedbackloginform.this);
                        boolean Islogin= Boolean.parseBoolean("true");
                        prefs.edit().putBoolean("Islogin", Islogin).commit();
                        //   pd.dismiss();
                        finish();
                        startActivity(is);
//                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
               /* Intent i = new Intent(getApplicationContext(),LoginActivityRetailer.class);
                startActivity(i);*/




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

}
