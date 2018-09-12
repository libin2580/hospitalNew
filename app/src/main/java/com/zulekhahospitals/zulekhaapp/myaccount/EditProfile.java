package com.zulekhahospitals.zulekhaapp.myaccount;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class EditProfile extends AppCompatActivity {
    ImageView back;
    String user_id;
    Spinner spnr;
    RadioGroup radio_group, radio_group_patient;
    RadioButton radioMale, radioFemale, radioSelf, radioInsurence;
    ProgressBar progress;
    private int mYear, mMonth, mDay;
    EditText name, email , mobile, gender, nationality, age, patient_type;
    TextView input_dob;
    String name1, email1, mobile1, gender1, nationality1, age1, username1, patient_type1,input_dob1;
    Button sign_up;
    String pref_fullname,pref_age,pref_phone,pref_email,pref_nationality,pref_patienttype,pref_gender,pref_dob;
    boolean edittexterror = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user_id=getIntent().getStringExtra("user_id");
        System.out.println("userid : "+user_id);
        analytics = FirebaseAnalytics.getInstance(EditProfile.this);
        analytics.setCurrentScreen(EditProfile.this,EditProfile.this.getLocalClassName(), null /* class override */);

        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditProfile.this.goBack();
            }
        });
        progress = (ProgressBar)findViewById(R.id.progress);
        radio_group= (RadioGroup) findViewById(R.id.radio_group);
        radio_group_patient= (RadioGroup) findViewById(R.id.radio_group_patient);
        name= (EditText) findViewById(R.id.input_name);
        //gender= (EditText) findViewById(R.id.input_gender);
        radioMale= (RadioButton) findViewById(R.id.radioMale);
        radioFemale= (RadioButton) findViewById(R.id.radioFemale);
        radioSelf= (RadioButton) findViewById(R.id.radioSelf);
        radioInsurence= (RadioButton) findViewById(R.id.radioInsurence);
        age= (EditText) findViewById(R.id.input_age);
        email= (EditText) findViewById(R.id.input_email);
        input_dob=(TextView) findViewById(R.id.input_dob);
        //   nationality= (EditText) findViewById(R.id.input_nationality);
        // patient_type= (EditText) findViewById(R.id.input_type);


        mobile= (EditText) findViewById(R.id.input_mobile);

        sign_up= (Button) findViewById(R.id.btn_sign_up);

        spnr= (Spinner) findViewById(R.id.spinner);
        nationality1 = String.valueOf(spnr.getSelectedItem());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnr.setAdapter(adapter);

        SharedPreferences preferencesd= getSharedPreferences("MyPref", MODE_PRIVATE);
        pref_fullname = preferencesd.getString("fullname", null);
        pref_age=preferencesd.getString("age", null);
        pref_phone=preferencesd.getString("phone", null);
        pref_email=preferencesd.getString("email", null);
        pref_nationality=preferencesd.getString("nationality", null);
        pref_patienttype=preferencesd.getString("patient_type", null);
        pref_gender=preferencesd.getString("gender", null);
        pref_dob=preferencesd.getString("dob", null);
        user_id=preferencesd.getString("user_id",null);
        name.setText(pref_fullname);
        age.setText(pref_age);
        mobile.setText(pref_phone);
        email.setText(pref_email);
        input_dob.setText(pref_dob);
int nat_pos=0;
String nations[]=getResources().getStringArray(R.array.countries_array);
        for(int i=0;i<nations.length;i++){
            if(pref_nationality.replaceAll("\\s+","").equals(nations[i])){
                    nat_pos=i;
            }
        }



        if(pref_gender.equals("Male")){
            radioMale.setChecked(true);
        }
        else {
            radioFemale.setChecked(true);
        }

        if(pref_patienttype.replaceAll("\\s+","").equals("SelfPayingPatient")){
            radioSelf.setChecked(true);
        }
        else {
            radioInsurence.setChecked(true);
        }
        spnr.setSelection(nat_pos);







        input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(EditProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth) {

                        input_dob.setText(dayofMonth+"/"+(monthofYear+1)+"/"+year);
                    }
                },mYear,mMonth,mDay);
            /*    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());*/
                datePickerDialog.show();
            }
        });
        spnr.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr.getSelectedItemPosition();
                        //  Toast.makeText(getApplicationContext(),"You have selected "+position,Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexStr ="^[+]?[0-9]{10,13}$";
                if (radioMale.isChecked()) {
                    gender1 = radioMale.getText().toString();
                } else if (radioFemale.isChecked()) {
                    gender1 = radioFemale.getText().toString();
                }
                if (radioSelf.isChecked()) {
                    patient_type1 = radioSelf.getText().toString();
                } else if (radioInsurence.isChecked()) {
                    patient_type1 = radioInsurence.getText().toString();
                }
                // signup();
                //System.out.println("selected image-file"+filename);
                name1 = name.getText().toString();
                email1 = email.getText().toString();
                //  gender1 = gender.getText().toString();
                age1 = age.getText().toString();
                mobile1 = mobile.getText().toString();
                input_dob1=input_dob.getText().toString();
                nationality1=spnr.getSelectedItem().toString();
                // nationality1 = nationality.getText().toString();
                //  patient_type1 = patient_type.getText().toString();




                edittexterror = false;
    /*    boolean s=checkPassWordAndConfirmPassword(pass,confirmpass);
        if(s==true)
        {
            edittexterror = false;
        }
        else {
            edtcnfrmpass.setError("Incorrect password");
            edittexterror = true;
        }*/
                if (name.getText().toString().isEmpty() || age.getText().toString().trim().isEmpty() || mobile.getText().toString().isEmpty() || email.getText().toString().isEmpty() || input_dob.getText().toString().isEmpty() ) {

                  /*  final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(EditProfile.this).create();
                    alertDialog.setTitle("Alert");
                    // alertDialog.setIcon(R.drawable.warning_blue);
                    alertDialog.setMessage("Empty Fields");

                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();


                        }
                    });
                    alertDialog.show();
                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);*/
                    Snackbar.with(EditProfile.this,null)
                            .type(Type.ERROR)
                            .message("Empty Fields")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }
                else if(age.getText().toString().length()>2 ){
                    /*Toast.makeText(getApplicationContext(),"Invalied Age",Toast.LENGTH_LONG).show();*/
                    Snackbar.with(EditProfile.this,null)
                            .type(Type.ERROR)
                            .message("Invalied Age")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }
                else if(!mobile1.matches(regexStr)){
                   /* Toast.makeText(getApplicationContext(),"Invalied Phone Number",Toast.LENGTH_LONG).show();*/
                    Snackbar.with(EditProfile.this,null)
                            .type(Type.ERROR)
                            .message("Invalied Phone Number")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email1.trim()).matches()){

                   /* Toast.makeText(getApplicationContext(),"Invalied E-mail",Toast.LENGTH_LONG).show();*/
                    Snackbar.with(EditProfile.this,null)
                            .type(Type.ERROR)
                            .message("Invalied E-mail")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }

                else {
                    edittexterror = false;
                }



                if (edittexterror == false) {
                    if (DetectConnection
                            .checkInternetConnection(getApplicationContext())) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)

                        new SendPostRequest().execute();
                    } else {

                        /*final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(NewReg.this).create();
                        alertDialog.setTitle("Alert");

                        alertDialog.setMessage("No Internet");


                        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();


                            }
                        });
                        alertDialog.show();*/

                        Snackbar.with(EditProfile.this,null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();


                    }
                }
            }
        });
    }
    private void goBack() {
        super.onBackPressed();
    }


    private class SendPostRequest extends AsyncTask<String, Void, String> {

        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        public String doInBackground(String... arg0) {

            try {

                URL url = new URL(BaseUrl+"android/profile.php?"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", user_id);
                postDataParams.put("fullname", name1);
                postDataParams.put("gender", gender1);
                postDataParams.put("age", age1);
                postDataParams.put("phone", mobile1);
                postDataParams.put("email", email1);
                postDataParams.put("nationality",nationality1);
                postDataParams.put("patient_type", patient_type1);
                postDataParams.put("dob", input_dob1);


                /*postDataParams.put("type", type);
                postDataParams.put("fullname", fullname);
                postDataParams.put("phone", phone);
                postDataParams.put("email", email);
                postDataParams.put("location", location);
                postDataParams.put("username", username);
                postDataParams.put("password",password_s);
                postDataParams.put("token",refreshedToken);*/
                Log.e("params(updation)",postDataParams.toString());

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

                    System.out.println("sb.toString() : "+sb.toString());

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
            try {
                System.out.println("result : " + result);
                JSONObject jsonObj = new JSONObject(result);

                String status = jsonObj.getString("status");
                if (status.equalsIgnoreCase("true")) {

                    JSONObject jsonObjdata = jsonObj.getJSONObject("data");

                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();



                    editor.putString("fullname",jsonObjdata.getString("fullname"));

                    editor.putString("gender",jsonObjdata.getString("gender") );
                    editor.putString("age",jsonObjdata.getString("age") );
                    editor.putString("phone",jsonObjdata.getString("phone") );
                    editor.putString("email",jsonObjdata.getString("email") );
                    editor.putString("nationality",jsonObjdata.getString("nationality") );
                    editor.putString("patient_type",jsonObjdata.getString("patient_type") );
                    editor.putString("dob",jsonObjdata.getString("dob") );
////////////////////////////////////////////////////


                    editor.commit();
                    finish();
                    Toast.makeText(getApplicationContext(), "Updation Success",
                            Toast.LENGTH_SHORT).show();
              /*  Snackbar.with(EditProfile.this,null)
                        .type(Type.SUCCESS)
                        .message("Updation Success")
                        .duration(Duration.LONG)

                        .show();
*/
                } else {
               /* Toast.makeText(getApplicationContext(), "Updation Failed",
                        Toast.LENGTH_SHORT).show();
*/
                    Snackbar.with(EditProfile.this, null)
                            .type(Type.ERROR)
                            .message("Failed")
                            .duration(Duration.LONG)

                            .show();
                }
            }catch (Exception e){
                e.printStackTrace();
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
}
