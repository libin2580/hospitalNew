/*
package com.zulekhahospitals.zulekhaapp.doctors;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zulekhahospitals.zulekhaapp.details.DepartmentModel;
import com.zulekhahospitals.zulekhaapp.details.DoctorModel;

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
import java.util.ArrayList;
import java.util.Calendar;

import alexishospital.alexisapp.alexis.R;
import alexishospital.alexisapp.alexis.intro.MainMenuFragment;
import alexishospital.alexisapp.alexis.model.DepartmentModel;
import alexishospital.alexisapp.alexis.model.DoctorModel;
import alexishospital.alexisapp.alexis.utils.DetectConnection;

*/
/**
 * Created by Libin_Cybraum on 7/19/2016.
 *//*

public class Doctor_side_appointment_unreg extends android.support.v4.app.Fragment {

    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
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
    String dvn_id, deptn_id, dctn_id, dctnss;
    Button bt;
    EditText Patnt_Nam, Mob_No, E_mail, Nat_nl, Do_b, Appoint_Dat, Appoint_Tim, Diag_Nos;
    String user_id, pname, select_div, select_dept, diagnosis, mobileno, dates, select_doctor, e_mail, nat_nl, do_b, apmt_tim;
    RadioGroup patient_type, gender;
    String Pt, gt;
    View view;
    String dept_id,dept_nam,doc_nm,sts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.doctor_side_apointment_unreg_layout,
                container, false);
        progress = (ProgressBar) view.findViewById(R.id.progress_bar);
        SharedPreferences myPrefs = getActivity().getSharedPreferences("contact", getActivity().MODE_WORLD_READABLE);
        sts = myPrefs.getString("sample", "DEFAULT VALUE");
     //   Toast.makeText(getActivity(), myPrefs.getString("sample", "DEFAULT VALUE"), Toast.LENGTH_LONG).show();
//        sts=getArguments().getString("sts");
        System.out.println(",,,,,,,,,,,,status,,,,,,,,,,,,,,,,"+sts);
        if (sts.equalsIgnoreCase("no")) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Appointment will be available soon.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Start your app main activity
                            Intent i = new Intent(getActivity(), MainMenuFragment.class);
                            // i.putExtra("current_but_id",0);
                            startActivity(i);

                            // close this activity
                            //finish();
                            dialog.cancel();
                        }
                    });

       */
/* builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
*//*

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else {
            Patnt_Nam = (EditText) view.findViewById(R.id.patient_nam);
            Mob_No = (EditText) view.findViewById(R.id.mob_no);
            E_mail = (EditText) view.findViewById(R.id.eml);
            Nat_nl = (EditText) view.findViewById(R.id.natl);
            patient_type = (RadioGroup) view.findViewById(R.id.patient_type);
            gender = (RadioGroup) view.findViewById(R.id.gender);
            Do_b = (EditText) view.findViewById(R.id.dob_date);
            Appoint_Dat = (EditText) view.findViewById(R.id.aapoint_date);
            Appoint_Tim = (EditText) view.findViewById(R.id.aapoint_time);
            Diag_Nos = (EditText) view.findViewById(R.id.diag_nois);

            // dept_id=getArguments().getString("de_id");
            dept_nam = getArguments().getString("dept_nam");
            doc_nm = getArguments().getString("doc");
            //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<br_id>>>>>>>>>>>>>>>>>>>>>>>"+dept_id);
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<br_nm>>>>>>>>>>>>>>>>>>>>>>>" + dept_nam);
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<doc>>>>>>>>>>>>>>>>>>>>>>>" + doc_nm);
            Appoint_Dat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment newFragment = new SelectDateFragment();
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });
            Appoint_Tim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Appoint_Tim.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });
            Do_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDateFragment1();
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });
            patient_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
            });
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
            bt = (Button) view.findViewById(R.id.submt_btn);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user_id = "2";
                    pname = Patnt_Nam.getText().toString();
                    mobileno = Mob_No.getText().toString();
                    e_mail = E_mail.getText().toString();
                    nat_nl = Nat_nl.getText().toString();
                    do_b = Do_b.getText().toString();
                    apmt_tim = Appoint_Tim.getText().toString();

                    dates = Appoint_Dat.getText().toString();
//                select_div=Prefed_Hospital.getText().toString();
//                select_dept=Refred_Hospital.getText().toString();

                    // diagnosis=Diag_Nos.getText().toString();
                    if (DetectConnection
                            .checkInternetConnection(getActivity())) {


                        new DownloadData4().execute();

                    } else {
                        Toast.makeText(getActivity(),
                                "Please Turn on your Mobile Data",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
       */
/* final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        Appoint_Dat.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("/").append(month + 1).append("/")
                .append(year));

        // Button listener to show date picker dialog

        Appoint_Dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On button click show datepicker dialog
                getActivity().showDialog(DATE_PICKER_ID);
            }
        });*//*


            if (DetectConnection
                    .checkInternetConnection(getActivity())) {


                new DownloadData().execute();

            } else {
                Toast.makeText(getActivity(),
                        "Please Turn on your Mobile Data",
                        Toast.LENGTH_LONG).show();
            }
            if (DetectConnection
                    .checkInternetConnection(getActivity())) {


                new DownloadData2().execute();

            } else {
                Toast.makeText(getActivity(),
                        "Please Turn on your Mobile Data",
                        Toast.LENGTH_LONG).show();
            }

            // new DownloadData3().execute();
            bn = (TextView) view.findViewById(R.id.dv);
            sp = (TextView) view.findViewById(R.id.dpt);
            dt = (TextView) view.findViewById(R.id.dt);
//        new DownloadData1().execute();
            //  populateSpinner0();


            // Prefed_Hospital = (EditText) findViewById(R.id.branches);
            spinner0 = (Spinner) view.findViewById(R.id.div);
            spinner1 = (Spinner) view.findViewById(R.id.deptmnt);
            spinner2 = (Spinner) view.findViewById(R.id.doctors);

            spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub

                    dptmt = spinner0.getSelectedItem().toString();
                    dvn_id = Dep.get(arg2).getDep_id();
                    System.out.println("<<<<<<<<<divisionid>>>>>" + dvn_id);
                    bn.setText(dptmt);
                    if (DetectConnection
                            .checkInternetConnection(getActivity())) {


                        new DownloadData2().execute();

                    } else {
                        Toast.makeText(getActivity(),
                                "Please Turn on your Mobile Data",
                                Toast.LENGTH_LONG).show();
                    }


//                if(dptmt.equalsIgnoreCase("ZH dubai")){
//                    divison_id="1";
//                }
//                else if(dptmt.equalsIgnoreCase("ZH Sharjha")){
//                    divison_id="2";
//                }
//                else if(dptmt.equalsIgnoreCase("ZMC")){
//                    divison_id="33";
//                }
//                else {
//                    divison_id="1";
//                }

//                brnch = spinner0.getSelectedItem().toString();
//                System.out.println("<<<<<<<<branch>>>>" + brnch);
//                bn.setText(brnch);
//                if (branchList.size() == 0) {
//                    model = ZulekhaApplication.app.cBranchModel;
//                } else {
//                    model = branchList.get(arg2);
//                }


                    // gDoc.execute("http://64.131.78.157/~zulekham/json.php?fid=109&doctbydid="
                    // + model.getId());
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
                    sp.setText(dvmt);
                    //   new DownloadData3().execute();


//                brnch = spinner0.getSelectedItem().toString();
//                System.out.println("<<<<<<<<branch>>>>" + brnch);
//                bn.setText(brnch);
//                if (branchList.size() == 0) {
//                    model = ZulekhaApplication.app.cBranchModel;
//                } else {
//                    model = branchList.get(arg2);
//                }


                    // gDoc.execute("http://64.131.78.157/~zulekham/json.php?fid=109&doctbydid="
                    // + model.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }

            });
   */
/*     spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                dctor = spinner2.getSelectedItem().toString();
                dctn_id = doclist.get(arg2).getDrid();
                dctnss=doclist.get(arg2).getDrname();
                System.out.println("<<<<<<<<<docvtor id>>>>>" + dctn_id);
                dt.setText(dctor);
                //  new DownloadData3().execute();
//                brnch = spinner0.getSelectedItem().toString();
//                System.out.println("<<<<<<<<branch>>>>" + brnch);
//                bn.setText(brnch);
//                if (branchList.size() == 0) {
//                    model = ZulekhaApplication.app.cBranchModel;
//                } else {
//                    model = branchList.get(arg2);
//                }


                // gDoc.execute("http://64.131.78.157/~zulekham/json.php?fid=109&doctbydid="
                // + model.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });*//*

//                System.out.println(">>>>>>>div>>>>>>>>" + Text1);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

            // Refred_Hospital = (EditText) findViewById(R.id.refred_dept);
        }
        return view;
    }

    */
/*protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(getActivity(), pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            Appoint_Dat.setText(new StringBuilder().append(month + 1)
                    .append("/").append(day).append("/").append(year)
                    .append(""));

        }
    };*//*


    class DownloadData extends AsyncTask<Void, Void, Void> {
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
//


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.meridianinc.biz.cp-30.webhostbox.net/alexis/json.php?fid=101");

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
            if(result.equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "No ", Toast.LENGTH_SHORT).show();
            }
            else {
            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
//            if
//                    (result.equalsIgnoreCase(value)) {

            JSONArray mArray;

            Dep = new ArrayList<DepartmentModel>();

            try {
                mArray = new JSONArray(result);
                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mJsonObject = mArray.getJSONObject(i);
                    //  Log.d("OutPut", mJsonObject.getString("result"));
//                        Log.d("OutPut2", mJsonObject.getString("emirates"));
//                        Log.d("OutPut3", mJsonObject.getString("mobile"));
//
//                        Log.d("OutPut2", mJsonObject.getString("work_place"));
//                        Log.d("OutPut3", mJsonObject.getString("specialty_other"));

                    brnch_id = mJsonObject.getString("deptid");
                    brnch = mJsonObject.getString("deptname");
                    System.out.println("<<<brnch_id>>>>" + brnch_id);
                    System.out.println("<<<brnch>>>>" + brnch);
//                        String l = mJsonObject.getString("emirates");
//                        String m = mJsonObject.getString("mobile");
//                        String n = mJsonObject.getString("work_place");
//                        String o = mJsonObject.getString("specialty_other");

                    //   spinner_titles1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, divisonlist);
                    dm = new DepartmentModel();
                    dm.setDep_id(brnch_id);
                    dm.setDep_nam(brnch);
                    divisonlist = new ArrayList<String>();
                    Dep.add(dm);
                    for (DepartmentModel dm : Dep) {
                        divisonlist.add(dm.getDep_nam());
                        //  divisonlist.add(dv.getBranch_id());
                    }

                    ArrayAdapter<String> spinnerAdapter0 = new ArrayAdapter<String>(getActivity(),
                            R.layout.patientreferal_spinner_item, R.id.txt, divisonlist);

//                    spinnerAdapter0
//                            .setDropDownViewResource(R.layout.patientreferal_spinner_item);

                    spinner0.setAdapter(spinnerAdapter0);
                    spinnerAdapter0.notifyDataSetChanged();
                    // new DownloadData2().execute();

//                        System.out.println("<<<ll>>>>" + l);
//                        System.out.println("<<<mm>>>>" + m);
//                        System.out.println("<<<lnn>>>>" + n);
//                        System.out.println("<<<oo>>>>" + o);

//                        if(k.equalsIgnoreCase("true")){
//                            Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//
//                            Toast.makeText(getApplicationContext(), "Not Submitted", Toast.LENGTH_SHORT).show();
//                        }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            } else {
//                Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();
//
//            }


        }}
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
                HttpPost httppost = new HttpPost("http://www.meridianinc.biz.cp-30.webhostbox.net/alexis/json.php?fid=109&doctbydid=" + dvn_id);

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
            if(result.equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "No ", Toast.LENGTH_SHORT).show();
            }
            else {
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
                    //   Log.d("OutPut", mJsonObject.getString("result"));
//                        Log.d("OutPut2", mJsonObject.getString("emirates"));
//                        Log.d("OutPut3", mJsonObject.getString("mobile"));
//
//                        Log.d("OutPut2", mJsonObject.getString("work_place"));
//                        Log.d("OutPut3", mJsonObject.getString("specialty_other"));


                    deptId = mJsonObject.getString("doctid");
                    deptName = mJsonObject.getString("doctname");


                    dc = new DoctorModel();

                    dc.setDoc_id(deptId);
                    dc.setDoc_nam(deptName);
                    departmenlist = new ArrayList<String>();
                    Doc.add(dc);
                    for (DoctorModel dp : Doc) {
                        departmenlist.add(dc.getDoc_nam());
                    }
                    ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(),
                            R.layout.patientreferal_spinner_item, R.id.txt, departmenlist);

//                    spinnerAdapter0
//                            .setDropDownViewResource(R.layout.patientreferal_spinner_item);

                    spinner1.setAdapter(spinnerAdapter1);
                    spinnerAdapter1.notifyDataSetChanged();
                    // new DownloadData3().execute();
                    //  departmenlist.add(deptName);
//                    dp=new DepModel();
//                    dp.setDeptId(deptId);
//                    dp.setDeptName(deptName);
//
//                    deplist.add(dp);
//                        String l = mJsonObject.getString("emirates");
//                        String m = mJsonObject.getString("mobile");
//                        String n = mJsonObject.getString("work_place");
//                        String o = mJsonObject.getString("specialty_other");

                    System.out.println("<<<deptId>>>>" + deptId);
                    System.out.println("<<<deptName>>>>" + deptName);
//                        System.out.println("<<<ll>>>>" + l);
//                        System.out.println("<<<mm>>>>" + m);
//                        System.out.println("<<<lnn>>>>" + n);
//                        System.out.println("<<<oo>>>>" + o);

//                        if(k.equalsIgnoreCase("true")){
//                            Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//
//                            Toast.makeText(getApplicationContext(), "Not Submitted", Toast.LENGTH_SHORT).show();
//                        }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            } else {
//                Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();
//
//            }


        }
    }}

   */
/* class DownloadData3 extends AsyncTask<Void, Void, Void> {
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
//                System.out.println(">>>>>>>Pname>>>>>>>>" + pname);
//                System.out.println(">>>>>>>select_div>>>>>>>" + select_div);
//
//                System.out.println(">>>>>>select_dept>>>>>>>>>" + select_dept);
//                System.out.println(">>>>>>>diagnosis>>>>>>>>" + diagnosis);
//                System.out.println(">>>>>>>mobile no>>>>>>>>" + mobileno);
//                System.out.println(">>>>>>>dates>>>>>>>>" +dates);


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/json-cme.php?fid=111&deptid=" + deptn_id);

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
                Log.e("Loading Runnable:", e.toString());
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
            doclist = new ArrayList<DocModel>();
            JSONArray mArray;
            try {
                mArray = new JSONArray(result);
                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mJsonObject = mArray.getJSONObject(i);
                    //   Log.d("OutPut", mJsonObject.getString("result"));
//                        Log.d("OutPut2", mJsonObject.getString("emirates"));
//                        Log.d("OutPut3", mJsonObject.getString("mobile"));
//
//                        Log.d("OutPut2", mJsonObject.getString("work_place"));
//                        Log.d("OutPut3", mJsonObject.getString("specialty_other"));


                    drid = mJsonObject.getString("drid");
                    drname = mJsonObject.getString("drname");

//                    dv = new DivModel();
//                    dv.setBranch_id(brnch_id);
//                    dv.setBrnch(brnch);
//                    divisonlist = new ArrayList<String>();
//                    divlist.add(dv);


                    dm = new DocModel();
                    dm.setDrid(drid);
                    dm.setDrname(drname);
                    doctorslist = new ArrayList<String>();
                    doclist.add(dm);
                    for (DocModel dm : doclist) {
                        doctorslist.add(dm.getDrname());
                    }
                    ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(Alexis_Appointment_Register_Fragment.this,
                            R.layout.patientreferal_spinner_item, R.id.txt, doctorslist);

//                    spinnerAdapter0
//                            .setDropDownViewResource(R.layout.patientreferal_spinner_item);

                    spinner2.setAdapter(spinnerAdapter2);
                    spinnerAdapter2.notifyDataSetChanged();

//                        String l = mJsonObject.getString("emirates");
//                        String m = mJsonObject.getString("mobile");
//                        String n = mJsonObject.getString("work_place");
//                        String o = mJsonObject.getString("specialty_other");

                    System.out.println("<<<drid>>>>" + drid);
                    System.out.println("<<<drname>>>>" + drname);
//                        System.out.println("<<<ll>>>>" + l);
//                        System.out.println("<<<mm>>>>" + m);
//                        System.out.println("<<<lnn>>>>" + n);
//                        System.out.println("<<<oo>>>>" + o);

//                        if(k.equalsIgnoreCase("true")){
//                            Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//
//                            Toast.makeText(getApplicationContext(), "Not Submitted", Toast.LENGTH_SHORT).show();
//                        }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // }
//            else {
//                Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();
//
//            }


        }
    }*//*



    private class DownloadData4 extends AsyncTask<Void, Void, Void>

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

                //   System.out.println(">>>>>>select_doctor>>>>>>>>>" + select_doctor);
                System.out.println("<<<doctorsssss>>>>" + dctnss);
                System.out.println("<<<deptssssss>>>>" + deptn_id);
                System.out.println("<<<divisionssss>>>>" + dvn_id);
                String js = dvmt.replaceAll(" ", "");
                System.out.println("<<<jsssss>>>>" + js);
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.meridianinc.biz.cp-30.webhostbox.net/alexis/json.php?fid=118&pin=" + null + "&name="
                        + pname + "&mobile=" + mobileno + "&email=" + e_mail + "&nationality=" + nat_nl + "&gender=" + gt +
                        "&p_type=" + Pt + "&dob=" + do_b + "&date=" + dates + "&time=" + apmt_tim + "&dep=" + dvn_id + "&doc=" + js + "&div_id=" + "1");
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
            }


            return null;
        }

        protected void onPostExecute(Void args) {

            progress.setVisibility(ProgressBar.GONE);
            if(result.equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "No ", Toast.LENGTH_SHORT).show();
            }
            else {
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
                        //  Log.d("OutPut", mJsonObject.getString("result"));
//                        Log.d("OutPut2", mJsonObject.getString("emirates"));
//                        Log.d("OutPut3", mJsonObject.getString("mobile"));
//
//                        Log.d("OutPut2", mJsonObject.getString("work_place"));
//                        Log.d("OutPut3", mJsonObject.getString("specialty_other"));


                        String k = mJsonObject.getString("result");
//                        String l = mJsonObject.getString("emirates");
//                        String m = mJsonObject.getString("mobile");
//                        String n = mJsonObject.getString("work_place");
//                        String o = mJsonObject.getString("specialty_other");

                        System.out.println("<<<kk>>>>" + k);
//                        System.out.println("<<<ll>>>>" + l);
//                        System.out.println("<<<mm>>>>" + m);
//                        System.out.println("<<<lnn>>>>" + n);
//                        System.out.println("<<<oo>>>>" + o);

                        if (k.equalsIgnoreCase("true")) {
                            Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getActivity(), "Not Submitted", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "notcorrect" + result, Toast.LENGTH_SHORT).show();

            }


        }

    }}

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

    private class SelectDateFragment1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
            Do_b.setText(month + "/" + day + "/" + year);
        }

    }
}*/
