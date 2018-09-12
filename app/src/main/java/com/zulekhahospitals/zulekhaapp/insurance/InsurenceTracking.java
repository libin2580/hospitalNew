package com.zulekhahospitals.zulekhaapp.insurance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.Zulekha_Highlight_Model;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class InsurenceTracking extends AppCompatActivity {
    ProgressBar progress;
    RecyclerView recyclerview;
    InsurenceTrackingAdapter ita;
    InsurenceTrackingModel im;
    ImageView back;
    ArrayList<InsurenceTrackingModel>arrayValues;
    LinearLayout linear_first_page,linear_submit,linear_nodata_page;
    EditText edt_tracking_number;
    String tracking_no;
    String back_btn_flag="exit";
    String result;
    TextView try_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurence_training);
        progress = (ProgressBar)findViewById(R.id.progress_bar);
        analytics = FirebaseAnalytics.getInstance(InsurenceTracking.this);
        analytics.setCurrentScreen(InsurenceTracking.this,InsurenceTracking.this.getLocalClassName(), null /* class override */);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager1);

        linear_first_page=(LinearLayout)findViewById(R.id.linear_first_page);
        linear_submit=(LinearLayout)findViewById(R.id.linear_submit);
        linear_nodata_page=(LinearLayout)findViewById(R.id.linear_nodata_page);
        edt_tracking_number=(EditText)findViewById(R.id.edt_tracking_number);

        arrayValues=new ArrayList<>();

        try_again= (TextView) findViewById(R.id.try_again);
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(InsurenceTracking.this)) {
                    //Educational_leaflet_Fragment.this.goBack();

                    if (back_btn_flag.equalsIgnoreCase("exit")) {
                        finish();
                    } else {
                        back_btn_flag = "exit";
                        linear_first_page.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.GONE);
                        linear_nodata_page.setVisibility(View.GONE);
                    }
                }else{
                    Snackbar.with(InsurenceTracking.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();
                }
            }
        });
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                /*if (DetectConnection
                        .checkInternetConnection(InsurenceTracking.this)) {
                    //Educational_leaflet_Fragment.this.goBack();

                    if (back_btn_flag.equalsIgnoreCase("exit")) {
                        finish();
                    } else {
                        back_btn_flag = "exit";
                        linear_first_page.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.GONE);
                        linear_nodata_page.setVisibility(View.GONE);
                    }
                }else{
                    Snackbar.with(InsurenceTracking.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();
                }*/
            }
        });

        linear_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_tracking_number.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Please enter tracking number.",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (DetectConnection
                            .checkInternetConnection(getApplicationContext())) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)
                        tracking_no="";
                        tracking_no=edt_tracking_number.getText().toString();
                        arrayValues.clear();
                        new SendRequest().execute();
                    } else {
                        Snackbar.with(InsurenceTracking.this, null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();
                    }
                }
            }
        });



    }
    /*private class DownloadData extends AsyncTask<Void, Void, Void> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                HttpHandler h=new HttpHandler();
                String s=h.makeServiceCall("http://mobileappstestsite.com.php56-3.dfw3-2.websitetestlink.com/service/educational-leaflets-department.php?key=100");
            } catch (Exception e) {
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {
            progress.setVisibility(ProgressBar.GONE);
        }
    }*/
    private class SendRequest extends AsyncTask<String, Void, String> {

        int no_data_flag=0;
        String error_report;
        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        public String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://94.56.30.84:9081/HospyRestAPI/Tracking/getInsTracking/"+tracking_no); // here is your URL path

                /*JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", name1);
                Log.e("params",postDataParams.toString());*/

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                //conn.setDoOutput(true);
                conn.setRequestProperty("username","admin");
                conn.setRequestProperty("password","mpsspl");


                /*OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();*/

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

            System.out.println("---------------------- result : "+result);


            try {
                JSONArray jsonArray=new JSONArray(result);
                if(jsonArray.length()>0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dataObj = jsonArray.getJSONObject(i);
                        no_data_flag=0;
                        if(dataObj.has("Error_Report"))
                        {
                            no_data_flag=1;
                            error_report=dataObj.getString("Error_Report");
                        }else {

                            im = new InsurenceTrackingModel();
                            if (dataObj.has("Tracking_No"))
                                im.setTracking_no(dataObj.getString("Tracking_No"));

                            if (dataObj.has("Service_Name"))
                                im.setService_name(dataObj.getString("Service_Name"));

                            if (dataObj.has("Approval_Status"))
                                im.setApproval_status(dataObj.getString("Approval_Status"));

                            if (dataObj.has("Remarks"))
                                im.setRemarks(dataObj.getString("Remarks"));

                            if (dataObj.has("Validity"))
                                im.setValidity(dataObj.getString("Validity"));

                            if (dataObj.has("Service_Cost"))
                                im.setService_cost(dataObj.getString("Service_Cost"));

                            if (dataObj.has("Approved_Limit"))
                                im.setApproved_limit(dataObj.getString("Approved_Limit"));

                            arrayValues.add(im);

                        }

                    }
                }else {
                    //Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();

                }



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Something Went Wrong.Please try again later.",Toast.LENGTH_SHORT).show();
                progress.setVisibility(ProgressBar.GONE);
                back_btn_flag="exit";
                linear_first_page.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
                linear_nodata_page.setVisibility(View.GONE);
            }


if(no_data_flag==0) {//means ->has data
    if (arrayValues.size() > 0) {
        if (DetectConnection
                .checkInternetConnection(InsurenceTracking.this)) {
            //Toast.makeText(getActivity(),
            //	"You have Internet Connection", Toast.LENGTH_LONG)

            new DownloadDatanew().execute();


        } else {



            Snackbar.with(InsurenceTracking.this, null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();

        }
        back_btn_flag="to_front_page";
        linear_first_page.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);
        linear_nodata_page.setVisibility(View.GONE);
        ita = new InsurenceTrackingAdapter(arrayValues, getApplicationContext());
        recyclerview.setAdapter(ita);
    }
}
else {

    back_btn_flag="to_front_page";
    Toast.makeText(getApplicationContext(),error_report,Toast.LENGTH_SHORT).show();
    linear_nodata_page.setVisibility(View.VISIBLE);
    linear_first_page.setVisibility(View.GONE);
    recyclerview.setVisibility(View.GONE);

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
        System.out.println("result.toString()"+result.toString());
        return result.toString();
    }

    private class DownloadDatanew extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.GONE);

//            pd = new ProgressDialog(Login_Activity.this);
//            pd.setTitle("Submitting...");
//            pd.setMessage("Please wait...");
//            pd.setCancelable(false);
//            pd.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
//http://zulekhahospitals.com/json-cme.php?fid=103&txtemail=rajeesh@meridian.net.in&password=123456&user_type=Synapse%20User

            try {


                HttpHandler h = new HttpHandler();
                String s = h.makeServiceCall(BaseUrl+"iphone/tracking.php?trackingno="+tracking_no);


                result = s;


                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {
            // btnSignIn.setEnabled(false);
            // edt.setEnabled(false);
            // pdt.setEnabled(false);
            //   pd.dismiss();
            try {
                progress.setVisibility(ProgressBar.GONE);
                String sam = result.trim();
                System.out.println(">>>>>>>>>>>>>>>" + result);
                System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;

                JSONObject jsonObj = new JSONObject(result);

                String status = jsonObj.getString("status");

                if (status.equalsIgnoreCase("true")) {
                    String message = jsonObj.getString("message");

                //    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    //JSONArray mArray;
                  //  zhm = new ArrayList<Zulekha_Highlight_Model>();
                   /* try {
                        mArray = jsonObj.getJSONArray("message");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }
    }