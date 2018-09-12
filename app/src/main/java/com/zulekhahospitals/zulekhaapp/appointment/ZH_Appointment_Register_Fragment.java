package com.zulekhahospitals.zulekhaapp.appointment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.ZHconstansts;

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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;
import static com.zulekhahospitals.zulekhaapp.appointment.Constants.BASE_URL;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;


/**
 * Created by Libin_Cybraum on 2/12/2016.
 */
public class ZH_Appointment_Register_Fragment extends Fragment {





    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String result;
    ProgressBar progress = null;

    String brnch_id;
    String deptId, deptName, drname, drid, brnch;
    Spinner spinner0, spinner1, spinner2;


    ArrayList<DepartmentModel> Dep;
    //
    ArrayList<DoctorModel> Doc;
   DepartmentModel dm;
    DoctorModel dc;
  //  DepModel dp;

 ArrayList<String> divisonlist;
    ArrayList<String> departmenlist;
    ArrayList<String> doctorslist;
    ArrayAdapter<String> spinner_titles1;
    TextView bn, sp, dt;
    String dctor, dptmt, dvmt;
    String divison_id = "1";
    String dvn_id, deptn_id, dctn_id,dctnss;
    Button bt;
    TextView Appoint_Dat,Appoint_Tim;
    EditText Pin,Patnt_Nam, Mob_No,E_mail,Nat_nl,Do_b, Diag_Nos;
    String pin,user_id, pname, select_div, select_dept, diagnosis, mobileno, dates, select_doctor,e_mail,nat_nl,do_b,apmt_tim;
    RadioGroup patient_type, gender;
    RadioButton radioSelf, radioInsurence;
    String Pt,gt,sts;
    View view;
    RadioButton rb;
    String brn_id;
String tag="appointment";
    ArrayList<DivModel> divlist;
    DivModel dv;
    String br,br_id;

    RadioGroup fastrack_clinic_rg;
    RadioButton yes,no;
    String fastrack_clinic_status;
    List<kotlin.Pair<String, String>> params;
    List<kotlin.Pair<String, String>> params1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity()!=null) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.from(getActivity()).inflate(R.layout.appointment_registred_activity,
                    container, false);
        }
        progress = (ProgressBar)view. findViewById(R.id.progress_bar);

        analytics = FirebaseAnalytics.getInstance(getActivity());
        analytics.setCurrentScreen(getActivity(),getActivity().getLocalClassName(), null /* class override */);

        new DownloadData5().execute();

        Pin = (EditText)view. findViewById(R.id.pin);
        Patnt_Nam = (EditText)view. findViewById(R.id.patient_nam);
        Mob_No = (EditText)view. findViewById(R.id.mob_no);
        E_mail = (EditText)view. findViewById(R.id.eml);
        Nat_nl= (EditText)view. findViewById(R.id.natl);
        patient_type = (RadioGroup)view.findViewById(R.id.patient_type);
        gender = (RadioGroup)view.findViewById(R.id.gender);
        Do_b = (EditText)view. findViewById(R.id.dob_date);
        Appoint_Dat = (TextView)view. findViewById(R.id.aapoint_date);
        Appoint_Tim = (TextView)view. findViewById(R.id.aapoint_time);
        Diag_Nos = (EditText)view. findViewById(R.id.diag_nois);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);


        fastrack_clinic_rg=(RadioGroup)view.findViewById(R.id.fastrack_clinic);
        yes=(RadioButton)view.findViewById(R.id.stay_ahead) ;
        no=(RadioButton)view.findViewById(R.id.no_ahead) ;

        radioSelf= (RadioButton) view.findViewById(R.id.sp);
        radioInsurence= (RadioButton)view.findViewById(R.id.i);

        try {
            SharedPreferences preferencesd = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);

            Mob_No.setText(preferencesd.getString("phone", null));
            E_mail.setText(preferencesd.getString("email", null));


            String pref_patienttype = preferencesd.getString("patient_type", null);
            if (pref_patienttype.replaceAll("\\s+", "").equals("SelfPaying")) {
                radioSelf.setChecked(true);
            } else {
                radioInsurence.setChecked(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }






        // Show current date
      Appoint_Dat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              final Calendar c=Calendar.getInstance();
              mYear=c.get(Calendar.YEAR);
              mMonth=c.get(Calendar.MONTH);
              mDay=c.get(Calendar.DAY_OF_MONTH);

              DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth) {
                      SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                      String current_date=mYear+"-"+mMonth+"-"+mDay;
                      String selected_date=year+"-"+monthofYear+"-"+dayofMonth;
                      try {

                          if (dfDate.parse(selected_date).before(dfDate.parse(current_date)))
                          {
                              Snackbar.with(getActivity(),null)
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
        // Button listener to show date picker dialog


        Appoint_Tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                                Snackbar.with(getActivity(),null)
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
                                    Snackbar.with(getActivity(), null)
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
       patient_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.sp:
                        // do operations specific to this selection
                        Pt="sp";
                        break;

                    case R.id.i:
                        // do operations specific to this selection
                        Pt="I";
                        break;



                }


            }
        });
       gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male_id:
                        // do operations specific to this selection
                        gt="male";
                        break;

                    case R.id.female_id:
                        // do operations specific to this selection
                       gt="female";
                        break;



                }


            }
        });
        bt = (Button)view. findViewById(R.id.submt_btn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id="2";
                pin=Pin.getText().toString();
                pname=Patnt_Nam.getText().toString();
                mobileno=Mob_No.getText().toString();
                e_mail=E_mail.getText().toString();
                nat_nl=Nat_nl.getText().toString();
                do_b=Do_b.getText().toString();
                apmt_tim=Appoint_Tim.getText().toString();
                String regexStr ="^[+]?[0-9]{10,13}$";
                dates= Appoint_Dat.getText().toString();


                int select_id=patient_type.getCheckedRadioButtonId();
                rb=(RadioButton)view.findViewById(select_id);
                Pt=rb.getText().toString().trim().replaceAll("\\s","%20");
                System.out.println("pt : "+Pt);

                if(yes.isChecked()){
                    fastrack_clinic_status="yes";

                }else {
                    fastrack_clinic_status="no";
                }
                System.out.println("fastrack_clinic_status : "+fastrack_clinic_status);


                if(getActivity()!=null) {

                if (DetectConnection
                        .checkInternetConnection(getActivity())) {
                    if (pin.equals("") || mobileno.equals("") || e_mail.equals("") || apmt_tim.equals("") || dates.equals("")) {
                        Snackbar.with(getActivity(),null)
                                .type(Type.ERROR)
                                .message("Please fill all the fields!")
                                .duration(Duration.LONG)

                                .show();
                        return;
                    }
                    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e_mail.trim()).matches())
                    {
                      /*  Toast.makeText(getActivity(), "invalid email", Toast.LENGTH_LONG).show();*/
                        Snackbar.with(getActivity(),null)
                                .type(Type.ERROR)
                                .message("Invalied E-mail")
                                .duration(Duration.LONG)

                                .show();
                        return;
                    }

                    else if(!mobileno.matches(regexStr))
                    {
                        /*Toast.makeText(getActivity(), "invalid phone number", Toast.LENGTH_LONG).show();*/
                        Snackbar.with(getActivity(),null)
                                .type(Type.ERROR)
                                .message("Invalied Phone Number")
                                .duration(Duration.LONG)

                                .show();
                        return;
                    }
                    else {

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        new DownloadData4().execute();
                    }
                }
                } else {
                    if(getActivity()!=null) {
                        Snackbar.with(getActivity(),null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();
                    }
                }



            }
        });
        if(getActivity()!=null) {
            if (DetectConnection
                    .checkInternetConnection(getActivity())) {

deprtment();
           // new DownloadData().execute();

            } else {
                Snackbar.with(getActivity(),null)
                        .type(Type.ERROR)
                        .message("No Internet Connection!")
                        .duration(Duration.LONG)

                        .show();
            }
            if (DetectConnection
                    .checkInternetConnection(getActivity())) {


              //  doctors();
              new DownloadData2().execute();

            } else {
                Snackbar.with(getActivity(),null)
                        .type(Type.ERROR)
                        .message("No Internet Connection!")
                        .duration(Duration.LONG)

                        .show();
            }
        }

       // new DownloadData3().execute();
        bn = (TextView)view. findViewById(R.id.dv);
      //  sp = (TextView)view. findViewById(R.id.dpt);
        dt = (TextView)view. findViewById(R.id.dt);
//        new DownloadData1().execute();
        //  populateSpinner0();


        // Prefed_Hospital = (EditText) findViewById(R.id.branches);
        spinner0 = (Spinner)view. findViewById(R.id.div);
        spinner1 = (Spinner)view. findViewById(R.id.deptmnt);
        spinner2 = (Spinner)view. findViewById(R.id.brchs);






spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        br= spinner2.getSelectedItem().toString();
       br_id =divlist.get(i).getBranch_id();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+br_id);
       // new DownloadData().execute();
        deprtment();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                dptmt = spinner0.getSelectedItem().toString();
                dvn_id = Dep.get(arg2).getDep_id();
                System.out.println("<<<<<<<<<divisionid>>>>>" + dvn_id);
              //  bn.setText(dptmt);
                if(getActivity()!=null) {
                    if (DetectConnection
                            .checkInternetConnection(getActivity())) {

//doctors();
                      new DownloadData2().execute();

                    } else {
                        Snackbar.with(getActivity(),null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();
                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                dvmt = spinner1.getSelectedItem().toString();

                deptn_id = Doc.get(arg2).getDoc_id();
                System.out.println("<<<<<<<<<deptid>>>>>" + deptn_id);
              //  sp.setText(dvmt);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


return view;
    }

    private void deprtment() {
        params = new ArrayList<kotlin.Pair<String, String>>() {{
            add(new kotlin.Pair<String, String>("branch_id", br_id));

        }};
        Fuel.post(URL+"department.php",params).responseString(new com.github.kittinunf.fuel.core.Handler<String>() {
            @Override
            public void success(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, String s) {

                JSONArray mArray;

                Dep = new ArrayList<DepartmentModel>();
                try {
                    mArray = new JSONArray(s);
                    for (int i = 0; i < mArray.length(); i++) {
                        JSONObject mJsonObject = mArray.getJSONObject(i);


                        brnch_id = mJsonObject.getString("deptId");
                        brnch = mJsonObject.getString("deptName");
                        System.out.println("<<<brnch_id>>>>" + brnch_id);
                        System.out.println("<<<brnch>>>>" + brnch);

                        dm = new DepartmentModel();
                        dm.setDep_id(brnch_id);
                        dm.setDep_nam(brnch);
                        divisonlist = new ArrayList<String>();
                        Dep.add(dm);
                        for (DepartmentModel dm : Dep) {
                            divisonlist.add(dm.getDep_nam());
                            //  divisonlist.add(dv.getBranch_id());
                        }
                        if (divisonlist != null) {
                            // Collections.sort(divisonlist);
                            if (getActivity() != null) {
                                ArrayAdapter<String> spinnerAdapter0 = new ArrayAdapter<String>(getActivity(),
                                        R.layout.patientreferal_spinner_item, R.id.txt, divisonlist);

//                    spinnerAdapter0
//                            .setDropDownViewResource(R.layout.patientreferal_spinner_item);

                                spinner0.setAdapter(spinnerAdapter0);
                                spinnerAdapter0.notifyDataSetChanged();
                            }


                        } else {

                            System.out.println("nodtata");
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               new DownloadData2().execute();
//doctors();
            }
            @Override
            public void failure(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, FuelError fuelError) {

            }
        });
    }




    class DownloadData2 extends AsyncTask<Void, Void, Void> {
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

                //   System.out.println(">>>>>>select_doctor>>>>>>>>>" + select_doctor);


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(ZHconstansts.URL+"doctbydeptid.php?deptid="+dvn_id);

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
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {

            progress.setVisibility(ProgressBar.GONE);
            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
//            if
//                    (result.equalsIgnoreCase(value)) {

            JSONArray mArray;
            Doc = new ArrayList<DoctorModel>();
            try {
                mArray = new JSONArray(result);
                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mJsonObject = mArray.getJSONObject(i);


                    deptId = mJsonObject.getString("drid");
                    deptName = mJsonObject.getString("drname");


                    dc = new DoctorModel();

                    dc.setDoc_id(deptId);
                    dc.setDoc_nam(deptName);

    departmenlist = new ArrayList<String>();
    Doc.add(dc);






                }



                for (DoctorModel dc : Doc) {
                    departmenlist.add(dc.getDoc_nam());
                }

                if (departmenlist!=null){
                   // Collections.sort(departmenlist);
                    System.out.println("sorted array : ");{
                        for(int i=0;i<departmenlist.size();i++){
                            System.out.println(departmenlist.get(i));
                        }
                    }

                    if(getActivity()!=null) {
                        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(),
                                R.layout.patientreferal_spinner_item, R.id.txt, departmenlist);

//                    spinnerAdapter0
//                            .setDropDownViewResource(R.layout.patientreferal_spinner_item);

                        spinner1.setAdapter(spinnerAdapter1);
                        spinnerAdapter1.notifyDataSetChanged();
                    }

                }else {
                    System.out.println("nodtata");
                }


                System.out.println("<<<deptId>>>>"+deptId);
                System.out.println("<<<deptName>>>>"+deptName);


            } catch (JSONException e) {
                e.printStackTrace();
            }

          //  new DownloadData5().execute();
        }
    }




    private class DownloadData4 extends AsyncTask<Void, Void, Void>

    {

        //ProgressDialog pd = null;


        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground (Void...params){

            // http://zulekhahospitals.com/json-cme.php?fid=102&doctor=dr&company=&
            // txtname=rajeesh@meridian.net.in&specialty=Audiology&specialty_other=&full_name=rajeeshmeridian&
            // emirates=Dubai&mobile=9995063421&work_place=Zulekha%20Hospitals&work_place_other=
            // &landline=04832830558&user_type=Synapse%20User&password=123456

            try {
String j=apmt_tim.replaceAll(" ","%20");
                //   System.out.println(">>>>>>select_doctor>>>>>>>>>" + select_doctor);
                System.out.println("<<<doctorsssss>>>>" + dctnss);
                System.out.println("<<<deptssssss>>>>" + deptn_id);
                System.out.println("<<<divisionssss>>>>" + dvn_id);
                String ks= dptmt.replaceAll(" ","");
                String js=dvmt.replaceAll(" ","");
                System.out.println("<<<jsssss>>>>" + js);









//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(BaseUrl+"json.php?fid=118&pin="+pin+"&name="
                        +null+"&mobile="+mobileno+"&email="+e_mail+"&nationality="+null+"&gender="+null+
                        "&p_type="+Pt+"&dob="+null+"&date="+dates+"&time="+j+"&dep="+ks+"&doc="+js.trim().replaceAll(" ","%20")+"&div_id="+br_id+"&appt_type="+fastrack_clinic_status);
System.out.println("url sending : http://zulekhahospitals.com/json.php?fid=118&pin="+pin+"&name="
                +null+"&mobile="+mobileno+"&email="+e_mail+"&nationality="+null+"&gender="+null+
                        "&p_type="+Pt+"&dob="+null+"&date="+dates+"&time="+j+"&dep="+ks+"&doc="+js.trim().replaceAll(" ","%20")+"&div_id="+br_id+"&appt_type="+fastrack_clinic_status);
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading  error  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {


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
                            patient_type.clearCheck();

                            Intent home=new Intent(getActivity(), Main2Activity.class);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            home.putExtra("appointmentsubmitted","true");
                            home.putExtra("brn_id",brn_id);
                            startActivity(home);
                            try {
                                getActivity().finish();
                            }catch (Exception e){

                            }
                           /* Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();*/


                        } else {

                            /*Toast.makeText(getActivity(), "Not Submitted", Toast.LENGTH_SHORT).show();*/
                            Snackbar.with(getActivity(),null)
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
                Toast.makeText(getActivity(), "notcorrect" + result, Toast.LENGTH_SHORT).show();

            }

            progress.setVisibility(ProgressBar.GONE);
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
 @SuppressLint("ValidFragment")
 class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
           Appoint_Dat.setText(month+"/"+day+"/"+year);
        }


    }

    private class DownloadData5 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
//


            try {



                HttpClient httpclient = new DefaultHttpClient();
                //http://zulekhahospitals.com/json-cme.php?fid=109
                HttpPost httppost = new HttpPost(BASE_URL+"branch.php");

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
                Log.e("Loading  connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {

            progress.setVisibility(ProgressBar.GONE);

            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
//            if
//                    (result.equalsIgnoreCase(value)) {

            JSONArray mArray;

            divlist = new ArrayList<DivModel>();

            try {
                mArray = new JSONArray(result);
                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mJsonObject = mArray.getJSONObject(i);


                    brnch_id = mJsonObject.getString("branchid");
                    brnch = mJsonObject.getString("branchname");
                    System.out.println("<<<brnch_id>>>>" + brnch_id);
                    System.out.println("<<<brnch>>>>" + brnch);
                    dv = new DivModel();
                    dv.setBranch_id(brnch_id);
                    dv.setBrnch(brnch);
                    divisonlist = new ArrayList<String>();
                    divlist.add(dv);
                    for (DivModel dv : divlist) {
                        divisonlist.add(dv.getBrnch());
                        //  divisonlist.add(dv.getBranch_id());
                    }
if (divlist!=null) {
    if(getActivity()!=null) {
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(getActivity(),
                R.layout.patientreferal_spinner_item, R.id.txt, divisonlist);

        spinner2.setAdapter(spinnerAdapter2);
        spinnerAdapter2.notifyDataSetChanged();

        SharedPreferences preferencesd= getActivity().getSharedPreferences("MyPref1", MODE_PRIVATE);
        String str_branch=preferencesd.getString("branch",null);
        String k_branch=preferencesd.getString("branchK",null);

        System.out.println("branch inside LoginActivityFeedBack : "+str_branch);
        try {
            if (str_branch != null) {

                if (str_branch.equalsIgnoreCase("dubai")){
                    spinner2.setSelection(0);
                    brn_id="1";

                }
                else if(str_branch.equalsIgnoreCase("Sharjah")){
                    spinner2.setSelection(1);
                    brn_id="2";

                }
                else if(str_branch.equalsIgnoreCase("zmc")){
                    spinner2.setSelection(2);
                    brn_id="33";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (k_branch != null) {

                if (k_branch.equalsIgnoreCase("dubai")){
                    spinner2.setSelection(0);
                    brn_id="1";

                }
                else if(k_branch.equalsIgnoreCase("Sharjah")){
                    spinner2.setSelection(1);
                    brn_id="2";

                }
                else if(k_branch.equalsIgnoreCase("zmc")){
                    spinner2.setSelection(2);
                    brn_id="33";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
                    else {
    System.out.println("no data");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
deprtment();
          //  new DownloadData().execute();

        }
    }
}