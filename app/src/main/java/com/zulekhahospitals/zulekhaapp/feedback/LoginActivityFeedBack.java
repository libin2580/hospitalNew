package com.zulekhahospitals.zulekhaapp.feedback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hanks.htextview.HTextView;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leafle_pdf_Fragment;
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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;


public class LoginActivityFeedBack extends AppCompatActivity {
    Button login;
    TextView bsignup;
    TextView Reg, txtnew, gst;
    EditText edt_loginname, edt_loginnumber, edt_loginemail;
    String usernam, pass, str_email, str_pass, str_number, str_radio_patient,str_radio_place, str_name;
    ProgressDialog pd;
    TextView fb, twtr, gpls, lnkdlin, frgt;
    WebView wv;
    // ArrayList<UserDetailsModel> arr_usrs = new ArrayList<>();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String eml, status,agent_id;
    String REGISTER_URL = BaseUrl+"android/start.php?";
    ProgressBar progress;
    TextView forgotpasswrd;
    RadioGroup radiopatient ,radioplace;
    RadioButton radio_id_patient, radio_id_place;
    RadioButton radio_inpatient, radio_outpatient, radio_sh, radio_dubai;
    int patient = 0, place = 0;
    static ArrayList<QuestionModel> questionsArrayList;
    boolean edittexterror=false;

    String user_id,suser_id,branchselected,str_branch;
    ImageView back;
    SharedPreferences preferencesd;
    HTextView hd;
    String regexStr ="^[+]?[0-9]{10,13}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_new);

        analytics = FirebaseAnalytics.getInstance(LoginActivityFeedBack.this);
        analytics.setCurrentScreen(LoginActivityFeedBack.this,LoginActivityFeedBack.this.getLocalClassName(), null /* class override */);

     /*   SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
       agent_id= preferences.getString("agent_id",null);*/
        preferencesd= getSharedPreferences("MyPref", MODE_PRIVATE);
        suser_id = preferencesd.getString("user_id", null);


        agent_id="0";

        radiopatient = (RadioGroup) findViewById(R.id.radio_patient);
        radioplace = (RadioGroup) findViewById(R.id.radio_place);
        radio_inpatient = (RadioButton) findViewById(R.id.radio_inpatient);
        Typeface myFont4= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        radio_inpatient.setTypeface(myFont4);
        radio_outpatient = (RadioButton) findViewById(R.id.radio_outpatient);
        Typeface myFont5= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        radio_outpatient.setTypeface(myFont5);
        radio_sh = (RadioButton) findViewById(R.id.radio_sharjah);
        Typeface myFont6= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        radio_sh.setTypeface(myFont6);
        radio_dubai = (RadioButton) findViewById(R.id.radio_dubai);
        Typeface myFont7= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        radio_dubai.setTypeface(myFont7);


      /*  forgotpasswrd = (TextView) findViewById(R.id.txt_forgot);
        forgotpasswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialog cc = new CustomAlertDialog(LoginActivityFeedBack.this);
                cc.show();
            }
        });*/
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
       /* hd= (HTextView) findViewById(R.id.textView20);*/
        //  hd.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/font-name.ttf"));
// be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
      /*  hd.setAnimateType(HTextViewType.LINE);*/
        // hd.animateText("new simple string ok for that wish\n same on my screen not in yours");
        edt_loginname = (EditText) findViewById(R.id.edt_loginname);
        Typeface myFont8= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        edt_loginname.setTypeface(myFont8);
        edt_loginemail = (EditText) findViewById(R.id.edt_loginemail);
        Typeface myFont9= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        edt_loginemail.setTypeface(myFont9);
        edt_loginnumber = (EditText) findViewById(R.id.edt_loginnumber);
        Typeface myFont10= Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");
        edt_loginnumber .setTypeface(myFont10);

     //   edt_loginnumber.setText("+971");
        Selection.setSelection(edt_loginnumber.getText(), edt_loginnumber.getText().length());


        SharedPreferences myPrefs = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        edt_loginname.setText(myPrefs.getString("fullname", null));
        edt_loginemail.setText(myPrefs.getString("email", null));
        edt_loginnumber.setText(""+myPrefs.getString("phone", null));
        edt_loginnumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith("+971")){
                    edt_loginnumber.setText("+971");
                    Selection.setSelection(edt_loginnumber.getText(), edt_loginnumber.getText().length());

                }

            }
        });



        int selectedId_patient = radiopatient.getCheckedRadioButtonId();
       /* int selectedId_place = radioplace.getCheckedRadioButtonId();*/

        // find the radiobutton by returned id
        radio_id_patient = (RadioButton) findViewById(selectedId_patient);


        progress = (ProgressBar) findViewById(R.id.progress_bar);
        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });



        wv = new WebView(this);


        login = (Button) findViewById(R.id.but_proceed_login_activity);

        bsignup = (TextView) findViewById(R.id.txt_signup);
      /*  bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });*/
        SharedPreferences preferencesd= getSharedPreferences("MyPref1", MODE_PRIVATE);
        str_branch=preferencesd.getString("branch",null);

        System.out.println("branch inside LoginActivityFeedBack : "+str_branch);
        try {
            if (str_branch != null) {

                if (str_branch.equalsIgnoreCase("dubai")){
                    place=1;
                    radio_dubai.setChecked(true);
                }
                else if(str_branch.equalsIgnoreCase("Sharjah")){
                    place=2;
                    radio_sh.setChecked(true);
                }
                else {
                    place=33;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("value in variable place : "+String.valueOf(place));
        try {


            branchselected = getIntent().getStringExtra("branch_name");
            System.out.println("branchselecteddd........." + branchselected);

            if (branchselected.contentEquals("1")) {
                radio_dubai.setChecked(true);

            } else if (branchselected.contentEquals("2")) {
                radio_sh.setChecked(true);
            } else {

            }
        }
        catch (Exception E)
        {

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log();

            }
        });




    }

    private void goBack() {
        super.onBackPressed();
    }


    public void log() {


//    radio_id_place = (RadioButton) findViewById(selectedId_place);
//
//    if(radio_id_place.isChecked())
//    {
//        str_radio_place= radio_id_place.getText().toString();
//    }
//    else
//    {  str_radio_place="";
//
//    }


        if (radio_outpatient.isChecked()) {
            str_radio_patient = "Outpatient";
        }

        else if (radio_inpatient.isChecked()) {
            str_radio_patient = "Inpatient";

        }
        else {
            str_radio_patient = "";
        }
        if (radio_sh.isChecked()) {
            str_radio_place ="Sharjah";
        }

        else if (radio_dubai.isChecked()) {
            str_radio_place = "Dubai";
        }
        else {
            str_radio_place="" ;
        }
        str_email = edt_loginemail.getText().toString();
        str_name = edt_loginname.getText().toString();
        str_number = edt_loginnumber.getText().toString();
        if (str_radio_patient.contentEquals("Inpatient")) {
            patient = 1;
        } else {
            patient = 2;
        }
        if (str_radio_place.contentEquals("Dubai")) {
            place = 1;
        } else {
            place = 2;
        }

        System.out.println("..." + str_name + "...." + str_email + "..." + str_number + "..." + str_name + "..." + str_radio_patient+place+patient + "..." + place);

        if (str_email.matches("") || str_name.matches("") || str_radio_patient.matches("")) {

            Snackbar.with(LoginActivityFeedBack.this,null)
                    .type(Type.ERROR)
                    .message("Empty Fields")
                    .duration(Duration.LONG)

                    .show();
            edittexterror=true;

        }

        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(    edt_loginemail.getText().toString().trim()).matches()) {
            /*edt_loginemail.setError("Invalid Email");*/
            Snackbar.with(LoginActivityFeedBack.this,null)
                    .type(Type.ERROR)
                    .message("Invalied Email")
                    .duration(Duration.LONG)

                    .show();
            edittexterror=true;
        }
        else if(!edt_loginnumber.getText().toString().matches(regexStr))
        {
                        /*Toast.makeText(getActivity(), "invalid phone number", Toast.LENGTH_LONG).show();*/
            Snackbar.with(LoginActivityFeedBack.this,null)
                    .type(Type.ERROR)
                    .message("Invalied phone number")
                    .duration(Duration.LONG)

                    .show();
            edittexterror=true;
        }
        else if(edt_loginnumber.getText().toString().charAt(4)=='0')
        {
                     //   Toast.makeText(getActivity(), "invalid phone number", Toast.LENGTH_LONG).show();
            Snackbar.with(LoginActivityFeedBack.this,null)
                    .type(Type.ERROR)
                    .message("Phone number can't start with zero")
                    .duration(Duration.LONG)

                    .show();
            edittexterror=true;
        }
        else {
            System.out.println("edt_loginnumber.getText().toString().charAt(4) : "+edt_loginnumber.getText().toString().charAt(4));
            edittexterror=false;
        }



        if(edittexterror==false)

        {
            System.out.println("userrrri_refff" + str_email + str_number + str_name + agent_id + agent_id);
            if (edittexterror == false) {
                if (DetectConnection
                        .checkInternetConnection(LoginActivityFeedBack.this)) {


                    new SendPostRequest().execute();

                } else {

                    Snackbar.with(LoginActivityFeedBack.this, null)
                            .type(Type.ERROR)
                            .message("No internet Connection!")
                            .duration(Duration.SHORT)
                            .show();


                }




            }
        }

    }

   private class SendPostRequest extends AsyncTask<String, Void, String> {

        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        public String doInBackground(String... arg0) {

            try {
                System.out.println("inside SendPostRequest on proceed button click");
                URL url = new URL(REGISTER_URL); // here is your URL path

                JSONObject postDataParams = new JSONObject();



                postDataParams.put("name",str_name);
                postDataParams.put("phoneno",str_number);
                postDataParams.put("email",str_email);
         /*       postDataParams.put("agentid",agent_id);*/
                postDataParams.put("usertype",String.valueOf(patient));
                postDataParams.put("location",String.valueOf(place));
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
           /* Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();*/
          /*  if (result.contentEquals("\"success\"")) {*/
            if (result != null)

            {
                try {
                    questionsArrayList = new ArrayList<>();
                    System.out.println("result : "+result);
                    JSONArray jsonarray = new JSONArray(result);
                    System.out.println("00");
                    for (int i = 0; i < jsonarray.length(); i++) {
                        QuestionModel questionModel = new QuestionModel();
                        System.out.println("01");
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        /*String agentid = jsonobject.getString("agentid");*/
                        String qid = jsonobject.getString("ques_id");
                        String question = jsonobject.getString("question");
                        /*suser_id = jsonobject.getString("user_id");*/
                        String department = jsonobject.getString("department");
                        questionModel.setDepartment(department);
                        questionModel.setUser_id(user_id);
                        /*questionModel.setAgentid(agentid);*/

                        questionModel.setQuestid(qid);
                        questionModel.setQuestname(question);
                        questionsArrayList.add(questionModel);


                    }

                    System.out.println("userrrridd" + suser_id);
                    SharedPreferences preferences = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("log_status", "login");
                    editor.putString("feedbackname",str_name);
                    editor.putString("user_id", suser_id);







                    editor.putString("name",str_name);
                    /*editor.putString("department",department);*/
                    editor.putString("email",str_email);
                    editor.putString("phone",str_number);
                    editor.putString("usertype",Integer.toString(patient));

                    System.out.println("value in variable place : "+String.valueOf(place));
                    editor.putString("location",String.valueOf(place));

                    editor.commit();




                    Intent intent = new Intent(LoginActivityFeedBack.this, QuestionsNw.class);
                    intent.putExtra("user_ids", suser_id);
                    /// intent.putExtra("QuestionListExtra", ArrayList<QuestionModel>questionsArrayList);
                    startActivity(intent);
                    finish();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else {
                Snackbar.with(LoginActivityFeedBack.this,null)
                        .type(Type.SUCCESS)
                        .message("Failed!")
                        .duration(Duration.LONG)
                        .show();
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
    public  static  ArrayList<QuestionModel> questn()
    {

        return questionsArrayList;

    }
}
