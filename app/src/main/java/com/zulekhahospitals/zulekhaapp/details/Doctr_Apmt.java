package com.zulekhahospitals.zulekhaapp.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;


/**
 * Created by Libin_Cybraum on 7/19/2016.
 */
public class Doctr_Apmt extends Activity implements View.OnClickListener {
    FrameLayout container;
    FragmentManager fragmentManager;
    String tag = "reg";
    Fragment fragment;
    //  View view;
    Button Reg, UnReg;
    String regexStr ="^[+]?[0-9]{10,13}$";
    //  String de_id, dept_nam, doc_nm;
    private int mYear, mMonth, mDay, mHour, mMinute;
    RadioButton radioMale, radioFemale;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    String result;
    ProgressBar progress = null;
    ProgressBar progress_Un = null;
    String brnch_id;
    String deptId, deptName, drname, drid, brnch;
    Spinner spinner0, spinner1, spinner2;


    ArrayList<DepartmentModel> Dep;
    //
    ArrayList<DoctorModel> Doc;
    DepartmentModel dm;
    DoctorModel dc;
    //  DepModel dp;
Button Cancel_Un,Cncl;
    ArrayList<String> divisonlist;
    ArrayList<String> departmenlist;
    ArrayList<String> doctorslist;
    ArrayAdapter<String> spinner_titles1;
    TextView bn, sp, dt;
    String dctor, dptmt, dvmt;
    String divison_id = "1";
    String dvn_id, deptn_id, dctn_id, dctnss;
    Button bt;
    Spinner Nat_nl_Un;
    TextView Appoint_Dat_Un,Appoint_Tim_Un,Appoint_Dat,Appoint_Tim,Do_b_Un;
    EditText Patnt_Nam, Mob_No, E_mail, Nat_nl, Do_b, Diag_Nos, Pin, Dip, Dic;
    EditText Patnt_Nam_Un, Mob_No_un, E_mail_Un,  Diag_Nos_Un, Pin_Un, Dip_Un, Dic_Un, Dipun, Dicun;
    String user_id, pname, select_div, select_dept, diagnosis, mobileno, dates, select_doctor, e_mail, nat_nl, do_b, apmt_tim, pin, dctr, dptm;
    String dctr_un, dptm_un;
    RadioGroup patient_type, gender;
    RadioGroup patient_type_Un, gender_Un;
    String Pt, gt;
    View view;
    String de_id, dept_nam, doc_nm;
    View includedLayout1,includedLayout2;
    String dep_name,j,k;
    RadioButton radioSelf, radioInsurence,radioSelf_un, radioInsurence_un;
    RadioButton rbgen,rbpt;
    String brn_id;
    RadioButton rbgen1,rbpt1;
    String apmt_dates_un,sts,user_id_un, pname_un,select_dept_un, diagnosis_un, mobileno_un, dates_un, select_doctor_un, e_mail_un, nat_nl_un, do_b_un, apmt_tim_un, pin_un;

    RadioGroup fastrack_clinic_rg;
    RadioButton yes,no;
    String fastrack_clinic_status;

    RadioGroup fastrack_clinic_rg_unreg;
    RadioButton yes_unreg,no_unreg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_doctor_side_layout);
    includedLayout1 = findViewById(R.id.id1);
        includedLayout2 = findViewById(R.id.id2);
       includedLayout1.setVisibility(View.VISIBLE);
       includedLayout2.setVisibility(View.GONE);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        progress_Un=(ProgressBar) findViewById(R.id.progress_bar);
        analytics = FirebaseAnalytics.getInstance(Doctr_Apmt.this);
        analytics.setCurrentScreen(Doctr_Apmt.this,Doctr_Apmt.this.getLocalClassName(), null /* class override */);
        Intent intent = getIntent();
        dep_name=intent.getStringExtra("dc_nm");
        j = intent.getStringExtra("dc_id");
        k= intent.getStringExtra("dc_id");
        dept_nam=intent.getStringExtra("dep_id");
        dvn_id=intent.getStringExtra("branch_id");
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("contact", getApplicationContext().MODE_PRIVATE);
        sts = myPrefs.getString("sample", "DEFAULT VALUE");
     //   Toast.makeText(getActivity(), myPrefs.getString("sample", "DEFAULT VALUE"), Toast.LENGTH_LONG).show();
//        sts=getArguments().getString("sts");
        System.out.println(",,,,,,,,,,,,status,,,,,,,,,,,,,,,,"+sts);

            Pin = (EditText)findViewById(R.id.pin);
            Dip = (EditText) findViewById(R.id.depmnt);
            Dic = (EditText) findViewById(R.id.doctors);
            Patnt_Nam = (EditText)findViewById(R.id.patient_nam);
            Mob_No = (EditText) findViewById(R.id.mob_no);
            E_mail = (EditText) findViewById(R.id.eml);
            Nat_nl = (EditText)findViewById(R.id.natl);
            patient_type = (RadioGroup)findViewById(R.id.patient_type);
            gender = (RadioGroup) findViewById(R.id.gender);
            Do_b = (EditText) findViewById(R.id.dob_date);
            Appoint_Dat = (TextView) findViewById(R.id.aapoint_date);
            Appoint_Tim = (TextView) findViewById(R.id.aapoint_time);
            Diag_Nos = (EditText) findViewById(R.id.diag_nois);
            Cncl = (Button) findViewById(R.id.cancl_btn);
            Reg = (Button) findViewById(R.id.fragment_register);


        fastrack_clinic_rg=(RadioGroup)findViewById(R.id.fastrack_clinic);
        yes=(RadioButton)findViewById(R.id.stay_ahead) ;
        no=(RadioButton)findViewById(R.id.no_ahead) ;

        radioSelf= (RadioButton) findViewById(R.id.sp);
        radioInsurence= (RadioButton)findViewById(R.id.i);
        SharedPreferences preferencesdbrch= getSharedPreferences("MyPref1", MODE_PRIVATE);
        String str_branch=preferencesdbrch.getString("branch",null);
        System.out.println("branch inside LoginActivityFeedBack : "+str_branch);
        try {
            if (str_branch != null) {

                if (str_branch.equalsIgnoreCase("dubai")){

                    brn_id="1";
                }
                else if(str_branch.equalsIgnoreCase("Sharjah")){

                    brn_id="2";
                }
                else if(str_branch.equalsIgnoreCase("zmc")){

                    brn_id="33";
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

try{
        SharedPreferences preferencesd= getSharedPreferences("MyPref", MODE_PRIVATE);

        Mob_No.setText(preferencesd.getString("phone", null));
        E_mail.setText(preferencesd.getString("email", null));

        String pref_patienttype=preferencesd.getString("patient_type", null);
        if(pref_patienttype.replaceAll("\\s+","").equals("SelfPaying")){
            radioSelf.setChecked(true);
        }
        else {
            radioInsurence.setChecked(true);
        }

}catch (Exception e){
    e.printStackTrace();
}



            Dip.setText(dep_name);
            Dic.setText(k);
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<libittttttttttttttttt>>>>>>>>>>>>>>>>>>>>>>" + dep_name);
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<libzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz>>>>>>>>>>>>>>>>>>>>>>" +k);
          //  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<doc>>>>>>>>>>>>>>>>>>>>>>>" + doc_nm);
            UnReg = (Button) findViewById(R.id.fragment_unregister);
            Reg.setOnClickListener(this);
            UnReg.setOnClickListener(this);
            Cncl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Patnt_Nam_Un.setText("");
                    Mob_No.setText("");
                    E_mail.setText("");
                    //  Nat_nl_Un.setText("");
                    // Do_b_Un.setText("");
                    Appoint_Tim.setText("");
                    Appoint_Dat.setText("");

                }
            });
        ImageView back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Doctr_Apmt.this.goBack();
            }
        });
            Appoint_Dat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c=Calendar.getInstance();
                    mYear=c.get(Calendar.YEAR);
                    mMonth=c.get(Calendar.MONTH);
                    mDay=c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog=new DatePickerDialog(Doctr_Apmt.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth) {

                            SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                            String current_date=mYear+"-"+mMonth+"-"+mDay;
                            String selected_date=year+"-"+monthofYear+"-"+dayofMonth;
                            try {

                                if (dfDate.parse(selected_date).before(dfDate.parse(current_date)))
                                {
                                    Snackbar.with(Doctr_Apmt.this,null)
                                            .type(Type.ERROR)
                                            .message("can't select past date")
                                            .duration(Duration.LONG)

                                            .show();
                                }
                                else {
                                    Appoint_Dat.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + year);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }







                        }
                    },mYear,mMonth,mDay);
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    datePickerDialog.show();
                }
            });
            Appoint_Tim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog=new TimePickerDialog(Doctr_Apmt.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                            String AM_PM="";
                            if(hourofDay<12){
                                AM_PM="AM";}
                            else{
                                AM_PM="PM";}

                            if (hourofDay > 12) {
                                hourofDay -= 12;
                            }

                            if(hourofDay==0)
                                hourofDay=12;
                            System.out.println("------------------------------------------------------------");
                            System.out.println("hour of day :"+hourofDay);
                            System.out.println("am_pm :"+AM_PM);
                            System.out.println("------------------------------------------------------------");

                            if(AM_PM.equalsIgnoreCase("am")){

                                if(hourofDay<7 || hourofDay==12)
                                {
                                    Snackbar.with(Doctr_Apmt.this,null)
                                            .type(Type.ERROR)
                                            .message("Sorry.Appointment time is from 7 Am to 11 Pm.")
                                            .duration(Duration.LONG)

                                            .show();
                                    Appoint_Tim.setText("");
                                }

                                else
                                {
                                    Appoint_Tim.setText(hourofDay + ":" + minute + " " + AM_PM);
                                }
                            }
                            else if(AM_PM.equalsIgnoreCase("pm"))
                            {
                                if(hourofDay>=11 && hourofDay!=12)
                                {
                                    if(minute>0) {
                                        Snackbar.with(Doctr_Apmt.this, null)
                                                .type(Type.ERROR)
                                                .message("Sorry.Appointment time is from 7 Am to 11 Pm.")
                                                .duration(Duration.LONG)

                                                .show();
                                        Appoint_Tim.setText("");
                                    }
                                    else
                                    {
                                        Appoint_Tim.setText(hourofDay + ":" + minute + " " + AM_PM);
                                    }
                                }
                                else if(hourofDay<11){
                                    Appoint_Tim.setText(hourofDay + ":" + minute + " " + AM_PM);
                                }
                                else if(hourofDay==12)
                                {
                                    Appoint_Tim.setText(hourofDay + ":" + minute + " " + AM_PM);
                                }

                            }

                        }
                    },mHour,mMinute,false);
                    timePickerDialog.show();
                }
            });
            Do_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c=Calendar.getInstance();
                    mYear=c.get(Calendar.YEAR);
                    mMonth=c.get(Calendar.MONTH);
                    mDay=c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog=new DatePickerDialog(Doctr_Apmt.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth) {
                            SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                            String current_date=mYear+"-"+mMonth+"-"+mDay;
                            String selected_date=year+"-"+monthofYear+"-"+dayofMonth;
                            try {

                                if (dfDate.parse(selected_date).after(dfDate.parse(current_date)))
                                {
                                    Snackbar.with(Doctr_Apmt.this,null)
                                            .type(Type.ERROR)
                                            .message("can't select future date")
                                            .duration(Duration.LONG)

                                            .show();
                                }
                                else {
                                    Do_b.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + year);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }




                        }
                    },mYear,mMonth,mDay);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });
            /*patient_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.sp:
                            // do operations specific to this selection
                            Pt = "sp";
                            break;

                        case R.id.i:
                            // do operations specific to this selection
                            Pt = "I";
                            break;


                    }


                }
            });*/
            gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.male_id:
                            // do operations specific to this selection
                            gt = "male";
                            break;

                        case R.id.female_id:
                            // do operations specific to this selection
                            gt = "female";
                            break;


                    }


                }
            });
            bt = (Button) findViewById(R.id.submt_btn1);
       /* Cancel_Un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pin.setText("");
                Patnt_Nam.setText("");
                Mob_No.setText("");
                E_mail.setText("");
                Appoint_Tim.setText("");
                Appoint_Dat.setText("");
               //Appoint_Dat_Un.setText("");

            }
        });*/
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pin = Pin.getText().toString();
                    pname = Patnt_Nam.getText().toString();
                    mobileno = Mob_No.getText().toString();
                    e_mail = E_mail.getText().toString();
                    nat_nl = Nat_nl.getText().toString();
                    do_b = Do_b.getText().toString();
                    apmt_tim = Appoint_Tim.getText().toString();
                    dctr = Dic.getText().toString();
                    dptm = Dip.getText().toString();
                    dates = Appoint_Dat.getText().toString();
                    doc_nm=Dic.getText().toString();


                    if(yes.isChecked()){
                        fastrack_clinic_status="yes";

                    }else {
                        fastrack_clinic_status="no";
                    }
                    System.out.println("fastrack_clinic_status : "+fastrack_clinic_status);

//                select_div=Prefed_Hospital.getText().toString();
//                select_dept=Refred_Hospital.getText().toString();

                    // diagnosis=Diag_Nos.getText().toString();
                    if (DetectConnection
                            .checkInternetConnection(getApplicationContext())) {
                        if (pin.equals("") || mobileno.equals("") || e_mail.equals("") || apmt_tim.equals("") || dates.equals("")) {
                           /* Toast.makeText(getApplicationContext(), "Fill all Fields", Toast.LENGTH_LONG).show();*/
                            Snackbar.with(Doctr_Apmt.this,null)
                                    .type(Type.ERROR)
                                    .message("Please Fill all the Fields!")
                                    .duration(Duration.LONG)

                                    .show();

                        }
                        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(e_mail.trim()).matches()) {
                            Snackbar.with(Doctr_Apmt.this,null)
                                    .type(Type.ERROR)
                                    .message("Invalied E-mail address!")
                                    .duration(Duration.LONG)

                                    .show();
                        }
                        else if (!mobileno.matches(regexStr)) {
                            Snackbar.with(Doctr_Apmt.this,null)
                                    .type(Type.ERROR)
                                    .message("Invalied mobile number!")
                                    .duration(Duration.LONG)

                                    .show();
                        }

                        else {


                            int select_id1=patient_type.getCheckedRadioButtonId();
                            rbpt=(RadioButton)findViewById(select_id1);
                            Pt=rbpt.getText().toString().replaceAll("\\s","%20");
                            System.out.println("pt : "+Pt);


                            new DownloadData5().execute();
                        }
                    } else {
                        /*Toast.makeText(getApplicationContext(),
                                "Please Turn on your Mobile Data",
                                Toast.LENGTH_LONG).show();*/
                        Snackbar.with(Doctr_Apmt.this,null)
                                .type(Type.ERROR)
                                .message("Turn on your mobile data")
                                .duration(Duration.LONG)

                                .show();
                    }


                }


            });


    }

    private void goBack() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_register:
                Dip.setText(dep_name);
                Dic.setText(k);
                // Toast.makeText(getBaseContext(), "btn1", Toast.LENGTH_LONG).show();
                //Your Operation
                Reg.setTextColor(Color.parseColor("white"));
                UnReg.setTextColor(Color.parseColor("black"));
                Reg.setBackgroundResource(R.color.colorAccent);
                UnReg.setBackgroundResource(R.color.ZulekhaWhite);
               includedLayout1.setVisibility(View.VISIBLE);
              includedLayout2.setVisibility(View.GONE);


                Cncl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pin.setText("");
                        Patnt_Nam.setText("");
                        Mob_No.setText("");
                        E_mail.setText("");
                        Appoint_Tim.setText("");
                        Appoint_Dat.setText("");
                        //Appoint_Dat_Un.setText("");

                    }
                });
                break;

            case R.id.fragment_unregister:
                Cancel_Un=(Button) findViewById(R.id.cancl_btn_un);
              /*  progress_Un = (ProgressBar) findViewById(R.id.progress_bar_un);*/
                Patnt_Nam_Un = (EditText) findViewById(R.id.patient_nam_un);
                Mob_No_un = (EditText) findViewById(R.id.mob_no_un);
                E_mail_Un = (EditText)findViewById(R.id.eml_un);
                Nat_nl_Un = (Spinner) findViewById(R.id.natl_un);
                /*patient_type = (RadioGroup)findViewById(R.id.patient_type_un);
                gender = (RadioGroup) findViewById(R.id.gender_un);*/
                Do_b_Un = (TextView) findViewById(R.id.dob_date_un);
                Appoint_Dat_Un = (TextView) findViewById(R.id.aapoint_date_un);
                Appoint_Tim_Un = (TextView) findViewById(R.id.aapoint_time_un);
                Diag_Nos_Un = (EditText)findViewById(R.id.diag_nois_un);
                Dip_Un = (EditText)findViewById(R.id.depmnt_un);
                Dic_Un = (EditText) findViewById(R.id.doctors_un);
                radioMale= (RadioButton) findViewById(R.id.male_id_un);
                radioFemale= (RadioButton) findViewById(R.id.female_id_un);
                patient_type_Un = (RadioGroup) findViewById(R.id.patient_type_un);
                gender_Un = (RadioGroup)findViewById(R.id.gender_un);
                radioSelf_un= (RadioButton) findViewById(R.id.sp_un);
                radioInsurence_un= (RadioButton)findViewById(R.id.i_un);

                fastrack_clinic_rg_unreg=(RadioGroup)findViewById(R.id.fastrack_clinic_unreg);
                yes_unreg=(RadioButton)findViewById(R.id.stay_ahead_unreg) ;
                no_unreg=(RadioButton)findViewById(R.id.no_ahead_unreg) ;

                // Toast.makeText(getBaseContext(), "btn2", Toast.LENGTH_LONG).show();
                int nat_pos=0;
                try{
                SharedPreferences preferencesd= getSharedPreferences("MyPref", MODE_PRIVATE);
                Patnt_Nam_Un.setText(preferencesd.getString("fullname", null));
                Mob_No_un.setText(preferencesd.getString("phone", null));
                E_mail_Un.setText(preferencesd.getString("email", null));
                String pref_nationality=preferencesd.getString("nationality", null);
                String pref_patienttype=preferencesd.getString("patient_type", null);
                String pref_gender=preferencesd.getString("gender", null);
                Do_b_Un.setText(preferencesd.getString("dob", null));
System.out.println("pref_nationality : "+pref_nationality);

                String nations[]=getResources().getStringArray(R.array.countries_array);
                for(int i=0;i<nations.length;i++){
                    if(pref_nationality.replaceAll("\\s+","").equals(nations[i])){
                        nat_pos=i;
                        System.out.println("nat_pos : "+nat_pos);
                    }
                }



                if(pref_gender.equals("Male")){
                    radioMale.setChecked(true);
                }
                else {
                    radioFemale.setChecked(true);
                }

                if(pref_patienttype.replaceAll("\\s+","").equals("SelfPaying")){
                    radioSelf_un.setChecked(true);
                }
                else {
                    radioInsurence_un.setChecked(true);
                }
                /*Nat_nl_Un.setSelection(nat_pos);*/


                }catch (Exception e){
                    e.printStackTrace();
                }




                Dip_Un.setText(dep_name);
                Dic_Un.setText(k);
                UnReg.setTextColor(Color.parseColor("white"));
                Reg.setTextColor(Color.parseColor("black"));
                Reg.setBackgroundResource(R.color.ZulekhaWhite);
                UnReg.setBackgroundResource(R.color.colorAccent);
                includedLayout1.setVisibility(View.GONE);
              includedLayout2.setVisibility(View.VISIBLE);
                bt = (Button) findViewById(R.id.submt_btn_un);


                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Doctr_Apmt.this,
                        R.array.countries_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Nat_nl_Un.setAdapter(adapter);
                Nat_nl_Un.setSelection(nat_pos);

                Nat_nl_Un.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                       int arg2, long arg3) {

                                int position = Nat_nl_Un.getSelectedItemPosition();
                                //  Toast.makeText(getApplicationContext(),"You have selected "+position,Toast.LENGTH_LONG).show();
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }

                        }
                );

                Appoint_Dat_Un.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Calendar c=Calendar.getInstance();
                        mYear=c.get(Calendar.YEAR);
                        mMonth=c.get(Calendar.MONTH);
                        mDay=c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog=new DatePickerDialog(Doctr_Apmt.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth) {


                                SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                                String current_date=mYear+"-"+mMonth+"-"+mDay;
                                String selected_date=year+"-"+monthofYear+"-"+dayofMonth;
                                try {

                                    if (dfDate.parse(selected_date).before(dfDate.parse(current_date)))
                                    {
                                        Snackbar.with(Doctr_Apmt.this,null)
                                                .type(Type.ERROR)
                                                .message("can't select past date")
                                                .duration(Duration.LONG)

                                                .show();
                                    }
                                    else {
                                        Appoint_Dat_Un.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + year);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                            }
                        },mYear,mMonth,mDay);
                        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        datePickerDialog.show();
                    }
                });
                Appoint_Tim_Un.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog=new TimePickerDialog(Doctr_Apmt.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                                String AM_PM="";
                                if(hourofDay<12){
                                    AM_PM="AM";}
                                else{
                                    AM_PM="PM";}

                                if (hourofDay > 12) {
                                    hourofDay -= 12;
                                }

                                if(hourofDay==0)
                                    hourofDay=12;
                                System.out.println("------------------------------------------------------------");
                                System.out.println("hour of day :"+hourofDay);
                                System.out.println("am_pm :"+AM_PM);
                                System.out.println("------------------------------------------------------------");

                                if(AM_PM.equalsIgnoreCase("am")){

                                    if(hourofDay<7 || hourofDay==12)
                                    {
                                        Snackbar.with(Doctr_Apmt.this,null)
                                                .type(Type.ERROR)
                                                .message("Sorry.Appointment time is from 7 Am to 11 Pm.")
                                                .duration(Duration.LONG)

                                                .show();
                                        Appoint_Tim_Un.setText("");
                                    }

                                    else
                                    {
                                        Appoint_Tim_Un.setText(hourofDay + ":" + minute + " " + AM_PM);
                                    }
                                }
                                else if(AM_PM.equalsIgnoreCase("pm"))
                                {
                                    if(hourofDay>=11 && hourofDay!=12)
                                    {
                                        if(minute>0) {
                                            Snackbar.with(Doctr_Apmt.this, null)
                                                    .type(Type.ERROR)
                                                    .message("Sorry.Appointment time is from 7 Am to 11 Pm.")
                                                    .duration(Duration.LONG)

                                                    .show();
                                            Appoint_Tim_Un.setText("");
                                        }
                                        else
                                        {
                                            Appoint_Tim_Un.setText(hourofDay + ":" + minute + " " + AM_PM);
                                        }
                                    }
                                    else if(hourofDay<11){
                                        Appoint_Tim_Un.setText(hourofDay + ":" + minute + " " + AM_PM);
                                    }
                                    else if(hourofDay==12)
                                    {
                                        Appoint_Tim_Un.setText(hourofDay + ":" + minute + " " + AM_PM);
                                    }

                                }

                            }
                        },mHour,mMinute,false);
                        timePickerDialog.show();
                    }
                });
                Do_b_Un.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c=Calendar.getInstance();
                        mYear=c.get(Calendar.YEAR);
                        mMonth=c.get(Calendar.MONTH);
                        mDay=c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog=new DatePickerDialog(Doctr_Apmt.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth) {


                                SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                                String current_date=mYear+"-"+mMonth+"-"+mDay;
                                String selected_date=year+"-"+monthofYear+"-"+dayofMonth;
                                try {

                                    if (dfDate.parse(selected_date).after(dfDate.parse(current_date)))
                                    {
                                        Snackbar.with(Doctr_Apmt.this,null)
                                                .type(Type.ERROR)
                                                .message("can't select future date")
                                                .duration(Duration.LONG)

                                                .show();
                                    }
                                    else {
                                        Do_b_Un.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + year);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }



                            }
                        },mYear,mMonth,mDay);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });
                patient_type_Un.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.sp_un:
                                // do operations specific to this selection
                                Pt = "sp";
                                break;

                            case R.id.i_un:
                                // do operations specific to this selection
                                Pt = "I";
                                break;


                        }


                    }
                });
                gender_Un.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.male_id_un:
                                // do operations specific to this selection
                                gt = "male";
                                break;

                            case R.id.female_id_un:
                                // do operations specific to this selection
                                gt = "female";
                                break;


                        }


                    }
                });
                Cancel_Un.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Patnt_Nam_Un.setText("");
                        Mob_No_un.setText("");
                        E_mail_Un.setText("");
                        Nat_nl_Un.setSelection(0);
                        Do_b_Un.setText("");
                        Appoint_Tim_Un.setText("");
                        Appoint_Dat_Un.setText("");

                    }
                });
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_id = "2";

                        pname_un = Patnt_Nam_Un.getText().toString();
                        mobileno_un = Mob_No_un.getText().toString();
                        e_mail_un = E_mail_Un.getText().toString();
                        nat_nl_un = String.valueOf(Nat_nl_Un.getSelectedItem());
                        do_b_un = Do_b_Un.getText().toString();
                        apmt_tim_un = Appoint_Tim_Un.getText().toString();
                        dctr_un = Dic_Un.getText().toString();
                        dptm_un = Dip_Un.getText().toString();
                        apmt_dates_un = Appoint_Dat_Un.getText().toString();
                        doc_nm=Dic.getText().toString();

                        if(yes_unreg.isChecked()){
                            fastrack_clinic_status="yes";

                        }else {
                            fastrack_clinic_status="no";
                        }
                        System.out.println("fastrack_clinic_status : "+fastrack_clinic_status);


                        if (DetectConnection
                                .checkInternetConnection(getApplicationContext())) {
                            if(pname_un.equals("")||mobileno_un.equals("")||e_mail_un.equals("")||nat_nl_un.equals("")||do_b_un.equals("")
                                    ||apmt_tim_un.equals("")||apmt_dates_un.equals(""))
                            {
                                /*Toast.makeText(getApplicationContext(), "Fill all Fields", Toast.LENGTH_LONG).show();*/
                                Snackbar.with(Doctr_Apmt.this,null)
                                        .type(Type.ERROR)
                                        .message("Please Fill all the Fields")
                                        .duration(Duration.LONG)

                                        .show();

                            }
                            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(e_mail_un.trim()).matches()) {
                                Snackbar.with(Doctr_Apmt.this,null)
                                        .type(Type.ERROR)
                                        .message("Invalied E-mail address!")
                                        .duration(Duration.LONG)

                                        .show();
                            }
                            else if (!mobileno_un.matches(regexStr)) {
                                Snackbar.with(Doctr_Apmt.this,null)
                                        .type(Type.ERROR)
                                        .message("Invalied mobile number!")
                                        .duration(Duration.LONG)

                                        .show();
                            }
                            else
                            {

                                int select_id1=patient_type_Un.getCheckedRadioButtonId();
                                int select_id2=gender_Un.getCheckedRadioButtonId();
                                rbpt1=(RadioButton)findViewById(select_id1);
                                rbgen1=(RadioButton)findViewById(select_id2);
                                Pt=rbpt1.getText().toString().trim().replaceAll("\\s","%20");
                                gt=rbgen1.getText().toString().trim().replaceAll("\\s","%20");
                                System.out.println("pt : "+Pt+"\ngt : "+gt);

                                new DownloadData4().execute();
                            }
                        } else {
                           /* Toast.makeText(getApplicationContext(),
                                    "Please Turn on your Mobile Data",
                                    Toast.LENGTH_LONG).show();*/
                            Snackbar.with(Doctr_Apmt.this,null)
                                    .type(Type.ERROR)
                                    .message("Turn on your mobile data")
                                    .duration(Duration.LONG)

                                    .show();
                        }





//                select_div=Prefed_Hospital.getText().toString();
//                select_dept=Refred_Hospital.getText().toString();

                        // diagnosis=Diag_Nos.getText().toString();


                    }
                });
                //Your Operation
                break;
        }

    }

    private class DownloadData4 extends AsyncTask<Void, Void, Void>

    {

        //ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_Un.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
//
            // http://zulekhahospitals.com/json-cme.php?fid=102&doctor=dr&company=&
            // txtname=rajeesh@meridian.net.in&specialty=Audiology&specialty_other=&full_name=rajeeshmeridian&
            // emirates=Dubai&mobile=9995063421&work_place=Zulekha%20Hospitals&work_place_other=
            // &landline=04832830558&user_type=Synapse%20User&password=123456

            try {
                String j=apmt_tim_un.replaceAll(" ","%20");
                //   System.out.println(">>>>>>select_doctor>>>>>>>>>" + select_doctor);
                System.out.println("<<<doctorsssss>>>>" +doc_nm);
                System.out.println("<<<deptssssss>>>>" + deptn_id);
                System.out.println("<<<divisionssss>>>>" + dvn_id);
                String js = doc_nm.replaceAll(" ", "%20");
                System.out.println("<<<jsssss>>>>" + js);
                HttpClient httpclient = new DefaultHttpClient();



                System.out.println("--------------------------------------------------------------------------");
                System.out.println(BaseUrl+"json.php?fid=118&pin="+null+"&name="
                        + pname_un.replaceAll(" ","%20") +"&mobile="+mobileno_un+"&email="+e_mail_un +"&nationality="+nat_nl_un+"&gender="+gt.replaceAll(" ","%20")+
                        "&p_type="+Pt.replaceAll(" ","%20")+"&dob="+do_b_un+"&date="+apmt_dates_un+"&time="+j +"&dep="+dept_nam.replaceAll(" ","%20")+"&doc="+js.trim().replaceAll(" ","%20")+"&div_id="+dvn_id+"&appt_type="+fastrack_clinic_status);
                System.out.println("--------------------------------------------------------------------------");


                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/json.php?fid=118&pin="+null+"&name="
                        + pname_un.replaceAll(" ","%20") +"&mobile="+mobileno_un+"&email="+e_mail_un +"&nationality="+nat_nl_un+"&gender="+gt.replaceAll(" ","%20")+
                        "&p_type="+Pt.replaceAll(" ","%20")+"&dob="+do_b_un+"&date="+apmt_dates_un+"&time="+j +"&dep="+dept_nam.replaceAll(" ","%20")+"&doc="+js.trim().replaceAll(" ","%20")+"&div_id="+dvn_id+"&appt_type="+fastrack_clinic_status);
//                pname=Patnt_Nam.getText().toString()
////                select_div=Prefed_Hospital.getText().toString();
////                select_dept=Refred_Hospital.getText().toString();
//
//                diagnosis=Diag_Nos.getText().toString();
//                mobileno=Mob_No.getText().toString();
//                dates= Appoint_Dat.getText().toString();
//                ent Referral
//
//                http://zulekhahospitals.com/json-cme.php?fid=104&user_id=2&pname=rajeesh&select_div=1&select_dept=
//                // 78&diagnosis=testdiagnosis&mobileno=9995063421&dates=15/03/2016&select_doctor=Dr. (Col) Ajay Raj Gupta
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();


                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading  error  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {
            try {
                String sam = result.trim();
                System.out.println(">>>>>>>>>>>>>>>" + result);
                System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;
                if
                        (result.equalsIgnoreCase(value)) {

                    JSONArray mArray;
                    try {
                        mArray = new JSONArray(result);
                        for (int i = 0; i < mArray.length(); i++) {
                            JSONObject mJsonObject = mArray.getJSONObject(i);


                            String k = mJsonObject.getString("result");


                            System.out.println("<<<kk>>>>" + k);
                            if (getApplicationContext() != null) {
                                if (k.equalsIgnoreCase("true")) {

                                    Patnt_Nam_Un.setText("");
                                    Mob_No_un.setText("");
                                    E_mail_Un.setText("");
                                /*Nat_nl.setText("");*/

                                    patient_type_Un.clearCheck();

                                    Do_b_Un.setText("");
                                    Appoint_Dat_Un.setText("");
                                    Appoint_Tim_Un.setText("");
                                    gender_Un.clearCheck();

                                    Intent home = new Intent(Doctr_Apmt.this, Main2Activity.class);
                                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    home.putExtra("appointmentsubmitted", "true");
                                    home.putExtra("brn_id", brn_id);
                                    startActivity(home);
                                    try {
                                        finish();
                                    } catch (Exception e) {

                                    }
                                /*Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();*/

                                    progress_Un.setVisibility(ProgressBar.GONE);
                                } else {

                               /* Toast.makeText(getActivity(), "Not Submitted", Toast.LENGTH_SHORT).show();*/
                                    Snackbar.with(Doctr_Apmt.this, null)
                                            .type(Type.ERROR)
                                            .message("Failed")
                                            .duration(Duration.LONG)

                                            .show();
                                    progress_Un.setVisibility(ProgressBar.GONE);
                                }
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (getApplicationContext() != null) {
                        Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            progress_Un.setVisibility(ProgressBar.GONE);
        }

    }

    @SuppressLint("ValidFragment")
    class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            Appoint_Dat.setText(month + "/" + day + "/" + year);
        }
    }

    public class SelectDateFragment1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            Do_b_Un.setText(month + "/" + day + "/" + year);
        }

    }

    private class DownloadData5 extends AsyncTask<Void, Void, Void>

    {

        //ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
//
            // http://zulekhahospitals.com/json-cme.php?fid=102&doctor=dr&company=&
            // txtname=rajeesh@meridian.net.in&specialty=Audiology&specialty_other=&full_name=rajeeshmeridian&
            // emirates=Dubai&mobile=9995063421&work_place=Zulekha%20Hospitals&work_place_other=
            // &landline=04832830558&user_type=Synapse%20User&password=123456

            try {
                String j=apmt_tim.replaceAll(" ","%20");
                //   System.out.println(">>>>>>select_doctor>>>>>>>>>" + select_doctor);
                System.out.println("<<<doctorsssss>>>>" + doc_nm);
                System.out.println("<<<deptssssss>>>>" + deptn_id);
                System.out.println("<<<divisionssss>>>>" + dvn_id);
                String js = doc_nm.replaceAll(" ", "");
                System.out.println("<<<jsssss>>>>" + js);
                HttpClient httpclient = new DefaultHttpClient();


                System.out.println("--------------------------------------------------------------------------");
                System.out.println(BaseUrl+"json.php?fid=118&pin="+pin+"&name="
                        + null +"&mobile="+mobileno + "&email=" +e_mail+"&nationality=" + null +"&gender=" + null +
                        "&p_type="+Pt+"&dob="+null+"&date="+dates+"&time="+j+"&dep="+dept_nam.replaceAll(" ","%20")+"&doc="+js.trim().replaceAll(" ","%20")+"&div_id="+dvn_id+"&appt_type="+fastrack_clinic_status);
                System.out.println("--------------------------------------------------------------------------");


                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/json.php?fid=118&pin="+pin+"&name="
                        + null +"&mobile="+mobileno + "&email=" +e_mail+"&nationality=" + null +"&gender=" + null +
                        "&p_type="+Pt+"&dob="+null+"&date="+dates+"&time="+j+"&dep="+dept_nam.replaceAll(" ","%20")+"&doc="+js.trim().replaceAll(" ","%20")+"&div_id="+dvn_id+"&appt_type="+fastrack_clinic_status);
//                pname=Patnt_Nam.getText().toString();
////                select_div=Prefed_Hospital.getText().toString();
////                select_dept=Refred_Hospital.getText().toString();
//
//                diagnosis=Diag_Nos.getText().toString();
//                mobileno=Mob_No.getText().toString();
//                dates= Appoint_Dat.getText().toString();
//                ent Referral
//
//                http://zulekhahospitals.com/json-cme.php?fid=104&user_id=2&pname=rajeesh&select_div=1&select_dept=
//                // 78&diagnosis=testdiagnosis&mobileno=9995063421&dates=15/03/2016&select_doctor=Dr. (Col) Ajay Raj Gupta
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();


                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading  error  :", e.toString());
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void args) {
            try {
                String sam = result.trim();
                System.out.println(">>>>>>>>>>>>>>>" + result);
                System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;
                if
                        (result.equalsIgnoreCase(value)) {

                    JSONArray mArray;
                    try {
                        mArray = new JSONArray(result);
                        for (int i = 0; i < mArray.length(); i++) {
                            JSONObject mJsonObject = mArray.getJSONObject(i);


                            String k = mJsonObject.getString("result");

                            System.out.println("<<<kk>>>>" + k);

                            if (k.equalsIgnoreCase("true")) {

                                Pin.setText("");
                                Mob_No.setText("");
                                E_mail.setText("");
                                Appoint_Dat.setText("");
                                Appoint_Tim.setText("");
                           /* patient_type.clearCheck();*/

                                Intent home = new Intent(Doctr_Apmt.this, Main2Activity.class);
                                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                home.putExtra("appointmentsubmitted", "true");
                                home.putExtra("brn_id", brn_id);
                                startActivity(home);
                                try {
                                    finish();
                                } catch (Exception e) {

                                }
                            } else {

                            /*Toast.makeText(getActivity(), "Not Submitted", Toast.LENGTH_SHORT).show();*/
                                Snackbar.with(Doctr_Apmt.this, null)
                                        .type(Type.ERROR)
                                        .message("Failed")
                                        .duration(Duration.LONG)
                                        .show();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();

                }
            }catch (Exception e){}
            progress.setVisibility(ProgressBar.GONE);
            }

    }

    @SuppressLint("ValidFragment")
    class SelectDateFragment4 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            Appoint_Dat_Un.setText(month + "/" + day + "/" + year);
        }
    }

    public class SelectDateFragment5 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if(getActivity()!=null) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            }
            return null;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            Do_b_Un.setText(month + "/" + day + "/" + year);
        }

    }
}