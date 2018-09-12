package com.zulekhahospitals.zulekhaapp.insurance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leafle_pdf_Fragment;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leaflet_Model;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leaflet_adapter;
import com.zulekhahospitals.zulekhaapp.imagecapture.ImageActivity;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 12/6/2017.
 */

public class Insurance_Tracking_Fragment extends Activity {
    FrameLayout container;
    FragmentManager fragmentManager;

    Fragment fragment;
    View view;
    ProgressBar progress;
    RecyclerView recyclerview;
    String news_id,news_title,news_content,result,news_img,Dat;
    Educational_leaflet_adapter adapter;
    static ArrayList<Educational_leaflet_Model> eem;
    Educational_leaflet_Model ee;
    String depid,depname;
    String tag = "events";
    ImageButton dep,appoint,util,more;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insurance_tracking_layout);
        progress = (ProgressBar)findViewById(R.id.progress_bar);
        analytics = FirebaseAnalytics.getInstance(Insurance_Tracking_Fragment.this);
        analytics.setCurrentScreen(Insurance_Tracking_Fragment.this,Insurance_Tracking_Fragment.this.getLocalClassName(), null /* class override */);

        //  Dat= getArguments().getString("des_id");
        System.out.println(">>>>>>daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhh>>>>>>>>>" +Dat);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager1);
        recyclerview.setAdapter(adapter);
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Insurance_Tracking_Fragment.this.goBack();
            }
        });

        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {
            //Toast.makeText(getActivity(),
            //	"You have Internet Connection", Toast.LENGTH_LONG)

            new DownloadData().execute();
        } else {

            final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Alert");

            alertDialog.setMessage("No Internet");


            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();


                }
            });
            alertDialog.show();


            //	Toast.makeText(getActivity(),
            //	getResources().getString(R.string.Sorry) +
            //	getResources().getString(R.string.cic),
            //	Toast.LENGTH_SHORT).show();
        }



    }

    private void goBack() {
        super.onBackPressed();
    }




    private class DownloadData extends AsyncTask<Object, Object, String> {

        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);

//            pd = new ProgressDialog(Login_Activity.this);
//            pd.setTitle("Submitting...");
//            pd.setMessage("Please wait...");
//            pd.setCancelable(false);
//            pd.show();

        }

        @Override
        protected String doInBackground(Object... params) {
//http://zulekhahospitals.com/json-cme.php?fid=103&txtemail=rajeesh@meridian.net.in&password=123456&user_type=Synapse%20User


            try {
                URL mUrl = new URL("http://94.56.30.84:9081/HospyRestAPI/Tracking/getInsTracking");
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestProperty("username","admin");
                httpConnection.setRequestProperty("password","mpsspl");
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("Content-length", "0");
                httpConnection.setUseCaches(false);
                httpConnection.setAllowUserInteraction(false);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);

                httpConnection.connect();

                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    //return sb.toString();
                    result = sb.toString();
              System.out.println(">>>>>>>>>>>>>>>"+result);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }




           // return null;
        }

        protected void onPostExecute(String args) {
            // btnSignIn.setEnabled(false);
            // edt.setEnabled(false);
            // pdt.setEnabled(false);
            //   pd.dismiss();
            // String sam = result.trim();
            try {
                progress.setVisibility(View.GONE);
                System.out.println(">>>>>>>>>>>>>>>" + args);
                //  System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;
                if
                        (result.equalsIgnoreCase("[]")) {
               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                    Snackbar.with(Insurance_Tracking_Fragment.this, null)
                            .type(Type.ERROR)
                            .message("No Data!")
                            .duration(Duration.LONG)

                            .show();
                } else if (result != null && !result.isEmpty() && !result.equals("null")) {
                    JSONArray mArray;
                    //  mCountryModel1 = new ArrayList<>();
                    eem = new ArrayList<Educational_leaflet_Model>();
                    try {
                        mArray = new JSONArray(result);
                        for (int i = 0; i < mArray.length(); i++) {

                            ee = new Educational_leaflet_Model();

                            JSONObject mJsonObject = mArray.getJSONObject(i);
                            //Log.d("OutPut", mJsonObject.getString("doctor_publish"));
//

                           // depid = mJsonObject.getString("departmentid");
                          //  depname = mJsonObject.getString("departmentname");
                            // exhibitors = mJsonObject.getString("exhibitors");
                            //news_img = mJsonObject.getString("newsimg");

                    /*    Newsdb_model city = new Newsdb_model();
                        city.setName(news_id);
                        city.setState(news_title);
                        city.setDescription(news_content);
                        city.setImg(news_img);
                        handler.addCity(city);*/

                            ee.setDepid(depid);
                            ee.setDepname(depname);
                            // ee.setExhibitors(exhibitors);

                      /*  System.out.println("<<news_id>>>>" + news_id);

                        System.out.println("<< news_title>>>>" +news_title);
                        System.out.println("<< news_content>>>" +news_content);*/

                            eem.add(ee);

                            System.out.println("" + ee);

/*
                            adapter = new Educational_leaflet_adapter(eem, getApplicationContext());
                            recyclerview.setAdapter(adapter);*/


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerview.addOnItemTouchListener(
                            new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String j = eem.get(position).getDepid();
                                    // String dep_na=dsm.get(position).getDeptName();
                                    // Lm=pns.get(position).getEvent_year();
                                    System.out.println("<<<oo>lllllllllllllllllllll>>>" + j);
                                    // ListV.setVisibility(View.GONE);
                                    Intent i2 = new Intent(getApplicationContext(), Educational_leafle_pdf_Fragment.class);
                                    //i2.putExtra("dep_id",j);
                                    i2.putExtra("dep_id", j);
                                    //  i2.putExtra("dep_nme",dep_na);
                                    startActivity(i2);

                                }
                            })
                    );
                } else {

                    System.out.println("nulllllllllllllllllllllllllllllllll");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

