package com.zulekhahospitals.zulekhaapp.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hbb20.CountryCodePicker;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.appointment.DivModel;
import com.zulekhahospitals.zulekhaapp.feedback.LoginActivityFeedBack;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.insurance.InsurenceTracking;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 12/12/2016.
 */

public class NewReg extends Activity {
    RadioGroup radio_group, radio_group_patient;
    RadioButton radioMale, radioFemale, radioSelf, radioInsurence;
    ProgressBar progress;
    EditText name, email, password, mobile, gender, nationality, age, username, patient_type,confirm_input_password;
    String name1, email1, password1, mobile1, gender1, nationality1, age1, username1, patient_type1,confirm_input_password1,nationality_spinner1;
    Button sign_up;
    LinearLayout lin_signup;

    String REGISTER_URL = "http://zulekhahospitals.com/mobileapp/booking.php";
    boolean edittexterror = false;

    CountryCodePicker ccp;
    Spinner spnr;
    String countryCode;
    String cc,jsonStr;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<String> cntry;
    ArrayList<DivModel2> divlist;
    DivModel2 dv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration);
        analytics = FirebaseAnalytics.getInstance(NewReg.this);
        analytics.setCurrentScreen(NewReg.this,NewReg.this.getLocalClassName(), null /* class override */);

        progress = (ProgressBar)findViewById(R.id.progress_bar);
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

        //   nationality= (EditText) findViewById(R.id.input_nationality);
        // patient_type= (EditText) findViewById(R.id.input_type);
        username= (EditText) findViewById(R.id.input_user_name);
        password= (EditText) findViewById(R.id.input_password);
        confirm_input_password=(EditText)findViewById(R.id.confirm_input_password);
        mobile= (EditText) findViewById(R.id.input_mobile);
        contactList = new ArrayList<>();

        new GetContacts().execute();

        sign_up= (Button) findViewById(R.id.btn_sign_up);
        lin_signup= (LinearLayout) findViewById(R.id.lin_signup);
        spnr= (Spinner) findViewById(R.id.spinner);
       // nationality1 = String.valueOf(spnr.getSelectedItem());
        getCountryCode(spnr.getSelectedItem());
      /*  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnr.setAdapter(adapter);*/
        spnr.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        System.out.println("<<<<<<<<<<<countryCode>>>>>>>>>>>>>>>>>"+spnr.getSelectedItem());
                        getCountryCode(spnr.getSelectedItem());
                        int position = spnr.getSelectedItemPosition();
             nationality1 = divlist.get(position).toString();
                /// System.out.println("<<<<<<<<<divisionid>>>>>" + dvn_id);
                      //  Toast.makeText(getApplicationContext(),"You have selected "+position,Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        //ccp.resetToDefaultCountry();
        ccp.setCountryForNameCode(countryCode);
        cc=ccp.getSelectedCountryCodeWithPlus();
        System.out.println("<<<<<<<<<<<cc>>>>>>>>>>>>>>>>>"+cc);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
               cc=ccp.getSelectedCountryCodeWithPlus();
                System.out.println("<<<<<<<<<<<cc>>>>>>>>>>>>>>>>>"+cc);

                //  Toast.makeText(ButcherRegistration.this, "Updated " + ccp.getSelectedCountryName(), Toast.LENGTH_SHORT).show();
            }
        });
        lin_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();

              //  Toast.makeText(getApplicationContext(),"sign up",Toast.LENGTH_LONG).show();
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String regexStr = "^[+]?[0-9]{10,13}$";
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
                mobile1 = cc + mobile.getText().toString();
                // nationality1 = nationality.getText().toString();
                //  patient_type1 = patient_type.getText().toString();
                username1 = username.getText().toString();
                password1 = password.getText().toString();
                confirm_input_password1 = confirm_input_password.getText().toString();
                nationality1 = spnr.getSelectedItem().toString();


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
                if (name.getText().toString().isEmpty() || age.getText().toString().trim().isEmpty() || mobile.getText().toString().isEmpty() || email.getText().toString().isEmpty() || username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirm_input_password.getText().toString().isEmpty()) {

                   /* final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(NewReg.this).create();
                    alertDialog.setTitle("Alert");
                    // alertDialog.setIcon(R.drawable.warning_blue);
                    alertDialog.setMessage("Empty Fields");

                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();


                        }
                    });
                    alertDialog.show();*/
                   /* Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);*/
                    Snackbar.with(NewReg.this, null)
                            .type(Type.ERROR)
                            .message("Empty Fields")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                } else if (age.getText().toString().length() > 2) {
                   /* Toast.makeText(getApplicationContext(),"Invalied Age",Toast.LENGTH_LONG).show();*/
                    Snackbar.with(NewReg.this, null)
                            .type(Type.ERROR)
                            .message("Invalied Age")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                } else if (mobile1.charAt(4) == '0') {
                    //   Toast.makeText(getActivity(), "invalid phone number", Toast.LENGTH_LONG).show();
                    Snackbar.with(NewReg.this, null)
                            .type(Type.ERROR)
                            .message("Phone number can't start with zero")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                } /*else {
                    System.out.println("edt_loginnumber.getText().toString().charAt(4) : " + mobile1.toString().charAt(4));
                    edittexterror = false;
                }*/

                else if(!mobile1.matches(regexStr)){
                    /*Toast.makeText(getApplicationContext(),"Invalied Phone Number",Toast.LENGTH_LONG).show();*/
                    Snackbar.with(NewReg.this,null)
                            .type(Type.ERROR)
                            .message("Invalied Phone Number")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }

                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email1.trim()).matches()){

                    /*Toast.makeText(getApplicationContext(),"Invalied E-mail",Toast.LENGTH_LONG).show();*/
                    Snackbar.with(NewReg.this,null)
                            .type(Type.ERROR)
                            .message("Invalied E-mail")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }
                else if (password1.length()<8 || password1.length()>15) {
                    password.setError("password must contain minimum 8 and maximum 15 character");
                    Snackbar.with(NewReg.this,null)
                            .type(Type.ERROR)
                            .message("password must contain minimum 8 and maximum 15 character")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }
                else if (confirm_input_password1.length()<8 || confirm_input_password1.length()>15) {
                    confirm_input_password.setError("confirm password must contain minimum 8 and maximum 15 character");

                    Snackbar.with(NewReg.this,null)
                            .type(Type.ERROR)
                            .message("confirm password must contain minimum 8 and maximum 15 character")
                            .duration(Duration.LONG)

                            .show();
                    edittexterror = true;
                }


                else if(!password1.equals(confirm_input_password1)){
                    System.out.println("inside password does not match");
                    System.out.println("password1 : "+password1);
                    System.out.println("confirm_input_password1 : "+confirm_input_password1);
                   /* Toast.makeText(getApplicationContext(),"Password and confirm password does not match",Toast.LENGTH_LONG).show();*/

                    Snackbar.with(NewReg.this,null)
                            .type(Type.ERROR)
                            .message("Password and Confirm password doesn't match")
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

                        Snackbar.with(NewReg.this,null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();


                    }
                }
            }
        });
            }

    private String getCountryCode(Object selectedItem) {

        String[] isoCountryCodes = Locale.getISOCountries();
        Map<String, String> countryMap = new HashMap<>();

        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            // Map all country names and codes in key - value pairs.
            countryMap.put(name, code);
        }
        // Get the country code for the given country name using the map.
        // Here you will need some validation or better yet
        // a list of countries to give to user to choose from.
       countryCode = countryMap.get(selectedItem); // "NL" for Netherlands.
System.out.println("<<<<<<<<<<<countryCode>>>>>>>>>>>>>>>>>"+countryCode);
        return countryCode;
    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {

        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        public String doInBackground(String... arg0) {

            try {

                URL url = new URL(BaseUrl+"android/registration.php"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", name1);
                postDataParams.put("gender", gender1);
                postDataParams.put("age", age1);
                postDataParams.put("phone", mobile1);
                postDataParams.put("email", email1);
                postDataParams.put("nation",nationality1);
                postDataParams.put("patient_type", patient_type1);
                postDataParams.put("username", username1);//username1
                postDataParams.put("password", password1);

                /*postDataParams.put("type", type);
                postDataParams.put("fullname", fullname);
                postDataParams.put("phone", phone);
                postDataParams.put("email", email);
                postDataParams.put("location", location);
                postDataParams.put("username", username);
                postDataParams.put("password",password_s);
                postDataParams.put("token",refreshedToken);*/
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
                    Toast.LENGTH_LONG).show();
*/
            try {
                System.out.println("registration result : " + result);
                JSONObject jsonObj = new JSONObject(result);
                String status=jsonObj.getString("status");

                if (status.equalsIgnoreCase("true")) {

                    finish();
                    Toast.makeText(getApplicationContext(), "Registration Success",
                            Toast.LENGTH_SHORT).show();
               /* Snackbar.with(NewReg.this,null)
                        .type(Type.SUCCESS)
                        .message("Registration Success")
                        .duration(Duration.LONG)

                        .show();*/
              /*  MaterialStyledDialog dialog = new MaterialStyledDialog(NewReg.this)
                        .setTitle("SUCCESS!")
                        .setDescription("Signup Successfull")
                        .setIcon(R.drawable.ic_done_white_24dp)
                        .withIconAnimation(true)
                        .withDialogAnimation(true)
                        .setHeaderColor(R.color.colorPrimary)
                        .setCancelable(true)
                        .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {


                                Intent inn= new Intent(getApplicationContext(),LoginActivity.class);
                                finish();

                            }
                        })
                        .build();

                dialog.show();
*/


               /* Intent i = new Intent(getApplicationContext(),LoginActivityRetailer.class);
                startActivity(i);*/
                } else if (status.equalsIgnoreCase("false")) {
               /* Toast.makeText(getApplicationContext(), "Registration Failed",
                        Toast.LENGTH_SHORT).show();*/
                    Snackbar.with(NewReg.this, null)
                            .type(Type.ERROR)
                            .message("Already Registered")
                            .duration(Duration.LONG)

                            .show();
               /* MaterialStyledDialog dialog = new MaterialStyledDialog(NewReg.this)
                        .setTitle("Failed!")
                        .setDescription("Already regstred")
                        .setIcon(R.drawable.ic_error_white_24dp)
                        .withIconAnimation(true)
                        .withDialogAnimation(true)
                        .setHeaderColor(R.color.colorPrimary)
                        .setCancelable(true)
                        .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {


                                dialog.dismiss();
                                //finish();

                            }
                        })
                        .build();

                dialog.show();*/
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
        System.out.println("result.toString()"+result.toString());
        return result.toString();
    }

    class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
         /*   pDialog = new ProgressDialog(Registration_Form.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(BaseUrl+"iphone/country.php");
            System.out.println("<<<<<<<<<<<<<<<<<<jsonStr>>>>>>>>>>>>>>>>>>>"+jsonStr);

            //    Log.e(TAG, "Response from url: " + jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (jsonStr != null) {
                try {
                    // JSONObject jsonObj = new JSONObject();

                    divlist= new ArrayList<DivModel2>();
                    // Getting JSON Array node
                    JSONArray contacts = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                         // tmp hash map for single contact
                        //HashMap<String, String> contact = new HashMap<>();
                        String country_name = c.getString("country_name");

                        //   contact.put("country_name", country_name);
                        System.out.println("<<<<<<<<<<<<<<<<<<country_name>>>>>>>>>>>>>>>>>>>"+country_name);
                        // contact.put("email", email);
                        // contact.put("mobile", mobile);
                        dv1 = new DivModel2();

                        // adding contact to contact list
                       //  cntry=new ArrayList<String>();
                      //  cntry.add(country_name);
                        dv1.setCountry(country_name);
                        divlist.add(dv1);

                        cntry=new ArrayList<String>();
                       // dv1.setCountry(country_name);
                        //contactList.add(contact);
                        for (DivModel2 dv1 : divlist) {
                            cntry.add(dv1.getCountry());
                            //  divisonlist.add(dv.getBranch_id());
                        }
                        ArrayAdapter<String> spinnerAdapter0 = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.patientreferal_spinner_item, R.id.txt, cntry);


                        spnr.setAdapter(spinnerAdapter0);
                        spinnerAdapter0.notifyDataSetChanged();
                    }
                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            }



        }
    }
}