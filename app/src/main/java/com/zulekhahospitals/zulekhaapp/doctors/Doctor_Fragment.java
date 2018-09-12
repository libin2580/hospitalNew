package com.zulekhahospitals.zulekhaapp.doctors;

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
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.NetworkCheckingClass;
import com.zulekhahospitals.zulekhaapp.utility.RecyclerItemClickListener;

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
import java.util.List;
import java.util.Map;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL2;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 9/29/2016.
 */

public class Doctor_Fragment extends Activity implements SearchView.OnQueryTextListener{
    FrameLayout container;
    FragmentManager fragmentManager;
    String tag = "events";
    Fragment fragment;
    View view;
    RecyclerView recyclerview;


    TextView doc_label;
    EditText search_edit_text;
    LinearLayout search_button,close_button,floatind_edit_linearlayout,doc_linearlayout;
    FloatingSearchView floating_search_view;
    Doctor_adapter adapter1;
    ProgressBar progress;
    String result, galry_image, Dat, Dat1;
     ArrayList<Doctor_Model> zdrm;
    Doctor_Model drm;
    String doctid, doctname, docqual, docspec, docphoto,docexp,docother;
    String REGISTER_URL = URL2+"json5.php?";
    //fid=109&doctbydid=%@
    String k,dep_nm,branch_id;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_fragment_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        // Dat= getArguments().getString("desc");
        // Dat= getArguments().getString("des_id");
        back= (ImageView) findViewById(R.id.back_image);
        analytics = FirebaseAnalytics.getInstance(Doctor_Fragment.this);
        analytics.setCurrentScreen(Doctor_Fragment.this,Doctor_Fragment.this.getLocalClassName(), null /* class override */);

            doc_label=(TextView) findViewById(R.id.textView20);

        floating_search_view=(FloatingSearchView)findViewById(R.id.floating_search_view2);
        search_button=(LinearLayout)findViewById(R.id.search_button);
        close_button=(LinearLayout)findViewById(R.id.close_button);
        doc_linearlayout=(LinearLayout)findViewById(R.id.doc_linearlayout);
        floatind_edit_linearlayout=(LinearLayout)findViewById(R.id.floatind_edit_linearlayout);


        doc_linearlayout.setVisibility(View.VISIBLE);
        search_button.setVisibility(View.VISIBLE);
        floatind_edit_linearlayout.setVisibility(View.GONE);
        close_button.setVisibility(View.GONE);
        doc_label.setVisibility(View.VISIBLE);
        floating_search_view.setVisibility(View.GONE);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Doctor_Fragment.this.goBack();
            }
        });
        Intent intent = getIntent();
        k = intent.getStringExtra("dep_id");
        dep_nm = intent.getStringExtra("dep_nme");
        branch_id=intent.getStringExtra("branch_id");
doc_label.setText(dep_nm);
        System.out.println(">>>>>>daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa>>>>>>>>>" + dep_nm);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager1);
        recyclerview.setAdapter(adapter1);
        /*try {
            recyclerview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    try {
                        findViewById(R.id.textView8).getParent().requestDisallowInterceptTouchEvent(false);
                    }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    return false;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }*/
        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {
            //Toast.makeText(getActivity(),
            //	"You have Internet Connection", Toast.LENGTH_LONG)
            //recycler_inflate();
            new DownloadData1().execute();
        } else {

            /*final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Alert");

            alertDialog.setMessage("No Internet");


            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();


                }
            });
            alertDialog.show();*/
            Snackbar.with(Doctor_Fragment.this,null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();

        }
        /*search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_button.setVisibility(View.GONE);
                close_button.setVisibility(View.VISIBLE);
                doc_linearlayout.setVisibility(View.GONE);
                floatind_edit_linearlayout.setVisibility(View.VISIBLE);
                floating_search_view.setVisibility(View.VISIBLE);


            }
        });*/


        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_button.setVisibility(View.VISIBLE);
                close_button.setVisibility(View.GONE);
                doc_linearlayout.setVisibility(View.VISIBLE);
                floatind_edit_linearlayout.setVisibility(View.GONE);
                floating_search_view.setVisibility(View.GONE);

            }
        });
        //recyclerview.setLayoutManager(layoutManager);
        floating_search_view.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                try {

                    final List<Doctor_Model> filteredModelList = filter(zdrm, newQuery);
                    adapter1.setFilter(filteredModelList);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }

    private void goBack() {
        super.onBackPressed();
    }

    private void recycler_inflate() {
        progress.setVisibility(View.VISIBLE);
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();
        if (i == true) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //  tv.setText("Response is: "+ response);

                            System.out.println("++++++++++++++RESPONSE+++++++++++++++   course detail :" + response);


                            try {
                                JSONArray jsonarray = new JSONArray(response);
                                zdrm = new ArrayList<Doctor_Model>();


                                for (int i = 0; i < jsonarray.length(); i++) {
                                    drm = new Doctor_Model();

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    // String deptId,divId,deptName,department_image;
                                    doctid = jsonobject.getString("doctid");
                                    doctname = jsonobject.getString("doctname");
                                    docqual = jsonobject.getString("docqual");
                                    docspec = jsonobject.getString("docspec");
                                    docphoto = jsonobject.getString("docphoto");
                                    //  event_id = mJsonObject.getString("event_id");

                                    //  String o = mJsonObject.getString("specialty_other");
                                    System.out.println(">>>>>>idddddddddddddddddddddddddddddddd>>>>>>>>>" + doctid);
                                    System.out.println(">>>>>>nameeeeeeeeeeeeeeeeeeeeeeeeeee>>>>>>>>>" + doctname);
                                    drm.setDoctid(doctid);
                                    drm.setDoctname(doctname);
                                    drm.setDocqual(docqual);
                                    drm.setDocspec(docspec);
                                    drm.setDocphoto(docphoto);

                                    //  wm.s

                                    System.out.println("<<<galry_img>>>>" + galry_image);

                                    //   System.out.println("<<<oo>>>>" + o);
                                    //   onComplete();

                                    zdrm.add(drm);
                                    System.out.println("<<<oo>>>>" + drm);
                                    progress.setVisibility(View.GONE);
                        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setAdapter(adapter);*/
                                   /* adapter1 = new Doctor_adapter(zdrm, getApplicationContext());
                                    recyclerview.setAdapter(adapter1);
*/
                                }


//                           recyclerAdapterCategoryDeal=new RecyclerAdapterCategoryDeal(categoryDealModelArrayList,CategoryDealActivity.this);
//
//
//
//
//
//
//                                recyclerView.scheduleLayoutAnimation();
//
//                                recyclerView.setAdapter( recyclerAdapterCategoryDeal);
//
//
//
//
//                                //    System.out.println(""+notifcnt);
//
//
//
//                                recyclerView.addOnItemTouchListener
//                                        (
//                                                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
//                                                    @Override
//                                                    public void onItemClick(View view, int position) {
////
//                                                        int clickd_id=position+1;
//                                                        Intent i = new Intent(getApplicationContext(),CategoryDealDetail.class);
//                                                        i.putExtra("deal_id",clickd_id);
//                                                        startActivity(i);
//
//
//                                                    }
//                                                })
//                                        );
//////

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            recyclerview.addOnItemTouchListener(
                                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            if (DetectConnection
                                                    .checkInternetConnection(getApplicationContext())) {

                                                adapter1.setViews(position,k,dep_nm,branch_id);
                                            } else {


                                                Snackbar.with(Doctor_Fragment.this,null)
                                                        .type(Type.ERROR)
                                                        .message("No Internet Connection!")
                                                        .duration(Duration.LONG)

                                                        .show();

                                            }
                                        }
                                    })
                            );

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(CourseRegistrationActivity.this).create();
//                            alertDialog.setTitle("Alert");
//                            alertDialog.setMessage("Please Login to Register For this Course");
//                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
////                                        but_regcrc1.setBackgroundResource(R.color.butnbakcolr);
////                                        but_regcrc1.setTextColor(getResources().getColor(R.color.White));
//
//                                        }
//                                    });
//                            alertDialog.show();
                            Snackbar.with(Doctor_Fragment.this,null)
                                    .type(Type.ERROR)
                                    .message(error.toString())
                                    .duration(Duration.LONG)

                                    .show();

                            /*Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();*/
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    ///   http://meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass

                    params.put("fid", "109");
                    params.put("doctbydid", k);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
        } else {
            final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Oops Your Connection Seems Off..");

            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();


                }
            });

            alertDialog.show();


        }
    }

    private class DownloadData1 extends AsyncTask<Void, Void, Void> {

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
        protected Void doInBackground(Void... params) {
//http://zulekhahospitals.com/json-cme.php?fid=103&txtemail=rajeesh@meridian.net.in&password=123456&user_type=Synapse%20User

            try {


                //String gh=us_typ.replaceAll(" ","%20");
//                System.out.println(">>>>>>sss>>>>>>>>>" + s);
//                System.out.println(">>>>>>>ttt>>>>>>>>" + t);
//                //System.out.println(">>>>>>>vvv>>>>>>>>" + v);
//                System.out.println(">>>>>>>gh>>>>>>>>" +gh);
                HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost(URL2+"json5.php?fid=109&doctbydid="+k);
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
            // btnSignIn.setEnabled(false);
            // edt.setEnabled(false);
            // pdt.setEnabled(false);
            //   pd.dismiss();
            progress.setVisibility(ProgressBar.GONE);
            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
            if
                    (result.equalsIgnoreCase("[]")) {
                /*Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                Snackbar.with(Doctor_Fragment.this,null)
                        .type(Type.ERROR)
                        .message("No Events!")
                        .duration(Duration.LONG)

                        .show();
            }
            else if(result != null && !result.isEmpty() && !result.equals("null")) {
                JSONArray mArray;
                zdrm = new ArrayList<Doctor_Model>();
                try {
                    mArray = new JSONArray(result);
                    for (int i = 0; i < mArray.length(); i++) {
                        drm = new Doctor_Model();
                        JSONObject mJsonObject = mArray.getJSONObject(i);
                        // Log.d("OutPut", mJsonObject.getString("image"));


                        doctid = mJsonObject.getString("doctid");
                        doctname = mJsonObject.getString("doctname");
                        docqual = mJsonObject.getString("docqual");
                        docspec = mJsonObject.getString("docspec");
                        docphoto = mJsonObject.getString("docphoto");
                        docexp = mJsonObject.getString("docexp");
                        docother= mJsonObject.getString("docother");
                        //  event_id = mJsonObject.getString("event_id");

                        //  String o = mJsonObject.getString("specialty_other");
                        drm.setDoctid(doctid);
                        drm.setDoctname(doctname);
                        drm.setDocqual(docqual);
                        drm.setDocspec(docspec);
                        drm.setDocphoto(docphoto);
                        drm.setDocexp(docexp);
                        drm.setDocother(docother);

                        //  wm.s

                        System.out.println("<<<galry_img>>>>" + galry_image);

                        //   System.out.println("<<<oo>>>>" + o);
                        //   onComplete();

                        zdrm.add(drm);
                        System.out.println("<<<oo>>>>" + drm);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setAdapter(adapter1);
                        adapter1 = new Doctor_adapter(zdrm, getApplicationContext());
                        recyclerview.setAdapter(adapter1);


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
                                if (DetectConnection
                                        .checkInternetConnection(getApplicationContext())) {

                                    adapter1.setViews(position,k,dep_nm,branch_id);
                                } else {


                                    Snackbar.with(Doctor_Fragment.this,null)
                                            .type(Type.ERROR)
                                            .message("No Internet Connection!")
                                            .duration(Duration.LONG)

                                            .show();

                                }

                            }
                        })
                );
            }
            else{

                System.out.println("nulllllllllllllllllllllllllllllllll");
            }
          /*  ListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Lm=pns.get(i).getEvent_year();
                    System.out.println("<<<oo>lllllllllllllllllllll>>>"+ Lm);
                    new DownloadData2().execute();
                }
            });*/


        }
    }
    @Override
    public boolean onQueryTextSubmit(String newText) {
        final List<Doctor_Model> filteredModelList = filter(zdrm, newText);
        adapter1.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private List<Doctor_Model> filter(List<Doctor_Model> models, String query) {
        query = query.toLowerCase();

        final List<Doctor_Model> filteredModelList = new ArrayList<>();
        for (Doctor_Model model1 : models) {
            final String text = model1.getDoctname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model1);
            }
        }
        return filteredModelList;
    }
}