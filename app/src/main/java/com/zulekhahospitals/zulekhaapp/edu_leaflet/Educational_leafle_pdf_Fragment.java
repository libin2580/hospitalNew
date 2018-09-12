package com.zulekhahospitals.zulekhaapp.edu_leaflet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.RecyclerItemClickListener;

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

/**
 * Created by libin on 10/3/2016.
 */

public class Educational_leafle_pdf_Fragment extends Activity {
    FrameLayout container;
    FragmentManager fragmentManager;
    String tag = "events";
    Fragment fragment;
    View view;
    RecyclerView recyclerview;
    //static ArrayList<UpcomingModel> upm;
    Educational_leafle_pdf_adapter adapter;
    ProgressBar progress;
    String result, galry_image,Dat;
    static ArrayList<Educational_leafle_pdf_Model> ehm;
    Educational_leafle_pdf_Model eh;
    String imageid,dept_name,leaflets,leaflets_cover,status,leaflets_pdf,orders,add_date;
    String k;
    String GoogleDocs="http://docs.google.com/gview?embedded=true&url=";
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highlight_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Educational_leafle_pdf_Fragment.this.goBack();
            }
        });
        analytics = FirebaseAnalytics.getInstance(Educational_leafle_pdf_Fragment.this);
        analytics.setCurrentScreen(Educational_leafle_pdf_Fragment.this,Educational_leafle_pdf_Fragment.this.getLocalClassName(), null /* class override */);
        Intent intent = getIntent();
        k = intent.getStringExtra("dep_id");
       // Dat= getArguments().getString("des_id");
        System.out.println(">>>>>>daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhh>>>>>>>>>"+k);
       /* GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);


        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });*/
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager1);
        recyclerview.setAdapter(adapter);

     /*   recyclerview.setHasFixedSize(true);
        Context context=getActivity();

        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(llm);
        // Change to gridLayout
        recyclerview.setLayoutManager(new GridLayoutManager(context, 3));*/

        //  GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        //recyclerview.setLayoutManager(layoutManager);
        //recyclerview.setOrientation(GridLayoutManager.VERTICAL);
        //recyclerview.setLayoutManager(layoutManager);
        // recyclerview.setAdapter( adapter );
        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {
            //Toast.makeText(getActivity(),
            //	"You have Internet Connection", Toast.LENGTH_LONG)

            new DownloadData1().execute();
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



        }


    }

    private void goBack() {
        super.onBackPressed();
    }

    private class DownloadData1 extends AsyncTask<Object, Object, String> {

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


                //String gh=us_typ.replaceAll(" ","%20");
//                System.out.println(">>>>>>sss>>>>>>>>>" + s);
//                System.out.println(">>>>>>>ttt>>>>>>>>" + t);
//                //System.out.println(">>>>>>>vvv>>>>>>>>" + v);
//                System.out.println(">>>>>>>gh>>>>>>>>" +gh);


               /* HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/json.php?fid=121&leafdept="+k
                );
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();*/
                URL url = new URL(BaseUrl+"android/educational-leaflets.php");



                JSONObject postDataParams=new JSONObject();
                postDataParams.put("key","100");
                postDataParams.put("departmentid",k);

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
                    result=sb.toString();
                    return result;

                }
                else {
                    return new String("false : "+responseCode);
                }




                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(String args) {
            // btnSignIn.setEnabled(false);
            // edt.setEnabled(false);
            // pdt.setEnabled(false);
            //   pd.dismiss();
            progress.setVisibility(ProgressBar.GONE);
            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>result" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
            if(result.trim().equalsIgnoreCase("[]")||result.trim().contentEquals("[]")) {
               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/

                Snackbar.with(Educational_leafle_pdf_Fragment.this,null)
                        .type(Type.ERROR)
                        .message("No Leaflets!")
                        .duration(Duration.LONG)

                        .show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                       finish();
                                         }
                }, 3000);
            } else if(result != null && !result.isEmpty() && !result.equals("null")) {
                JSONArray mArray;
                ehm = new ArrayList<Educational_leafle_pdf_Model>();
                try {
                    mArray = new JSONArray(result);
                    for (int i = 0; i < mArray.length(); i++) {
                        eh = new Educational_leafle_pdf_Model();
                        JSONObject mJsonObject = mArray.getJSONObject(i);
                      //  Log.d("OutPut", mJsonObject.getString("image"));


                        imageid= mJsonObject.getString("imageid");
                        dept_name= mJsonObject.getString("departmentid");
                        leaflets= mJsonObject.getString("leaflets");
                        leaflets_cover= mJsonObject.getString("leaflets_cover");
                        status= mJsonObject.getString("status");
                        leaflets_pdf = mJsonObject.getString("leaflets_pdf");
                                orders= mJsonObject.getString("orders");
                        add_date= mJsonObject.getString("add_date");
                        //  String o = mJsonObject.getString("specialty_other");
                        eh.setImageid(imageid);
                        eh.setDept_name(dept_name);
                        eh.setLeaflets(leaflets);
                        eh.setLeaflets_cover(leaflets_cover);
                        eh.setStatus(status);
                        eh.setLeaflets_pdf(leaflets_pdf);
                        eh.setOrders(orders);
                        eh.setAdd_date(add_date);

                        System.out.println("<<<galry_img>>>>" + galry_image);

                        //   System.out.println("<<<oo>>>>" + o);
                        //   onComplete();

                        ehm.add(eh);
                        System.out.println("<<<oo>>>>" + eh);
/*
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setAdapter(adapter);*/
                        adapter = new Educational_leafle_pdf_adapter(ehm, getApplicationContext());
                        recyclerview.setAdapter(adapter);


                        //adapter = new Event_adapter(upm, getActivity());
                        //  recyclerview.setAdapter(adapter);
                        //  Toast.makeText(getApplicationContext(), "Login Sucess", Toast.LENGTH_SHORT).show();
                        //status=5;
                        //   Intent i1 = new Intent(Participated_Events_Activity.this, HomeActivity.class);
                        //  i1.putExtra("fulname", k);
                        //   i1.putExtra("spclty", o);
                        // startActivity(i1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                recyclerview.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {



                                        String pdf =ehm.get(position).getLeaflets_pdf();
//
System.out.println("<<<oo>lllllllllllllllllllll>>>" +pdf);
//
                                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                                        pdfIntent.setDataAndType(Uri.parse(pdf), "application/pdf");
                                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                        try{
                                            startActivity(pdfIntent);
                                        }catch(ActivityNotFoundException e){
                                            // Toast.makeText(ImageActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                                        }




                            }
                        })
                );
            }
            else{

                Snackbar.with(Educational_leafle_pdf_Fragment.this, null)
                        .type(Type.ERROR)
                        .message("No Leaflets!")
                        .duration(Duration.LONG)

                        .show();

                System.out.println("nulllllllllllllllllllllllllllllllll");
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

