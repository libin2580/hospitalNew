package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.appointment.DivModel;
import com.zulekhahospitals.zulekhaapp.appointment.ZH_Appointment_Register_Fragment;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

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
import java.util.HashMap;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 12/4/2017.
 */

public class RewardSystems_Fragment extends Activity {


    View view;
    public JSONObject jsonobject;
    public JSONArray jsonarray;
    public ListView listview;
    ProgressBar progress = null;
    //ListnewsAdapter adapter;
    public static final String LINK = "link";
    ArrayList<HashMap<String, String>> arraylist;
    FrameLayout container;
    FragmentManager fragmentManager;
    Fragment fragment;
    String Murl, wid, depname, result, wi_fi;
    TextView dn;
    //	 Keep all Images in array
    String k;
    WebView web = null;
    ImageView back;
    ArrayList<DivModel> divlist;
    DivModel dv;
    Spinner spinner0, spinner1, spinner2;
    String br,br_id;
    String brnch_id;
    String deptId, deptName, drname, drid, brnch;
    ArrayList<String> divisonlist;
    String brn_id;
    EditText Mob_nao,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward_system_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        back = (ImageView) findViewById(R.id.back_image);
        analytics = FirebaseAnalytics.getInstance(RewardSystems_Fragment.this);
        analytics.setCurrentScreen(RewardSystems_Fragment.this,RewardSystems_Fragment.this.getLocalClassName(), null /* class override */);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardSystems_Fragment.this.goBack();
            }
        });
        Mob_nao = (EditText)findViewById(R.id.mobile);
        name = (EditText) findViewById(R.id.name);

        try {
            SharedPreferences preferencesd = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

            Mob_nao.setText(preferencesd.getString("phone", null));
            name.setText(preferencesd.getString("fullname", null));


           /* String pref_patienttype = preferencesd.getString("patient_type", null);
            if (pref_patienttype.replaceAll("\\s+", "").equals("SelfPaying")) {
                radioSelf.setChecked(true);
            } else {
                radioInsurence.setChecked(true);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }

        new DownloadData5().execute();
        spinner2 = (Spinner) findViewById(R.id.brchs);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                br= spinner2.getSelectedItem().toString();
                br_id =divlist.get(i).getBranch_id();
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+br_id);
              //  new ZH_Appointment_Register_Fragment.DownloadData().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void goBack() {
        super.onBackPressed();
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
            // http://zulekhahospitals.com/json-cme.php?fid=102&doctor=dr&company=&
            // txtname=rajeesh@meridian.net.in&specialty=Audiology&specialty_other=&full_name=rajeeshmeridian&
            // emirates=Dubai&mobile=9995063421&work_place=Zulekha%20Hospitals&work_place_other=
            // &landline=04832830558&user_type=Synapse%20User&password=123456

            try {

                //   System.out.println(">>>>>>select_doctor>>>>>>>>>" + select_doctor);
//


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(BaseUrl+"json-cme.php?fid=109");

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
                    if (divlist != null) {
                        if (getApplicationContext()!= null) {
                            ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.patientreferal_spinner_item, R.id.txt, divisonlist);

                            spinner2.setAdapter(spinnerAdapter2);
                            spinnerAdapter2.notifyDataSetChanged();

                            SharedPreferences preferencesd = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                            String str_branch = preferencesd.getString("branch", null);
                            String k_branch = preferencesd.getString("branchK", null);

                            System.out.println("branch inside LoginActivityFeedBack : " + str_branch);
                            try {
                                if (str_branch != null) {

                                    if (str_branch.equalsIgnoreCase("dubai")) {
                                        spinner2.setSelection(0);
                                        brn_id = "1";

                                    } else if (str_branch.equalsIgnoreCase("Sharjah")) {
                                        spinner2.setSelection(1);
                                        brn_id = "2";

                                    } else if (str_branch.equalsIgnoreCase("zmc")) {
                                        spinner2.setSelection(2);
                                        brn_id = "33";

                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (k_branch != null) {

                                    if (k_branch.equalsIgnoreCase("dubai")) {
                                        spinner2.setSelection(0);
                                        brn_id = "1";

                                    } else if (k_branch.equalsIgnoreCase("Sharjah")) {
                                        spinner2.setSelection(1);
                                        brn_id = "2";

                                    } else if (k_branch.equalsIgnoreCase("zmc")) {
                                        spinner2.setSelection(2);
                                        brn_id = "33";

                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    } else {
                        System.out.println("no data");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}