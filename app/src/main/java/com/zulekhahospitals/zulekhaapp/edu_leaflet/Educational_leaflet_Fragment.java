package com.zulekhahospitals.zulekhaapp.edu_leaflet;

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
import com.zulekhahospitals.zulekhaapp.appointment.ZH_Appointment_Fragment;
import com.zulekhahospitals.zulekhaapp.departments.Department_Fragment;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.RecyclerItemClickListener;
import com.zulekhahospitals.zulekhaapp.utility.UtilityFragment;
import com.zulekhahospitals.zulekhaapp.utility.ZHconstansts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 10/17/2016.
 */

public class Educational_leaflet_Fragment extends Activity implements View.OnClickListener{
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
        setContentView(R.layout.fragment_educational);
        progress = (ProgressBar)findViewById(R.id.progress_bar);
        dep= (ImageButton) findViewById(R.id.dep);
        appoint= (ImageButton) findViewById(R.id.appoint);
        util= (ImageButton) findViewById(R.id.util);
        more= (ImageButton) findViewById(R.id.more);
        analytics = FirebaseAnalytics.getInstance(Educational_leaflet_Fragment.this);
        analytics.setCurrentScreen(Educational_leaflet_Fragment.this,Educational_leaflet_Fragment.this.getLocalClassName(), null /* class override */);

        dep.setOnClickListener(this);
        appoint.setOnClickListener(this);
        util.setOnClickListener(this);
        more.setOnClickListener(this);
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

                Educational_leaflet_Fragment.this.goBack();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.dep:
                Intent i4 = new Intent(getApplicationContext(),Department_Fragment.class);
                //	i1.putExtra("current_but_id",1);
                //i1.putExtra("brn","sharjah");
                startActivity(i4);
                break;
            case R.id.appoint:
                Intent i1 = new Intent(getApplicationContext(),ZH_Appointment_Fragment.class);
                //	i1.putExtra("current_but_id",1);
                //i1.putExtra("brn","sharjah");
                startActivity(i1);
                break;
            case R.id.util:
                Intent i3 = new Intent(getApplicationContext(),UtilityFragment.class);
                //	i1.putExtra("current_but_id",1);
                //i1.putExtra("brn","sharjah");
                startActivity(i3);
                break;
            case R.id.more:
                Educational_leaflet_Fragment.this.goBack();
                break;
        }
    }

    private class DownloadData extends AsyncTask<Void, Void, Void> {

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
               /* HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/json.php?fid=120"
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
                HttpHandler h=new HttpHandler();
                String s=h.makeServiceCall(URL+"educational-leaflets-department.php?key=100");

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
            // String sam = result.trim();
            try {
                progress.setVisibility(View.GONE);
                System.out.println(">>>>>>>>>>>>>>>" + result);
                //  System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;
                if
                        (result.equalsIgnoreCase("[]")) {
               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                    Snackbar.with(Educational_leaflet_Fragment.this, null)
                            .type(Type.ERROR)
                            .message("No Events!")
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

                            depid = mJsonObject.getString("departmentid");
                            depname = mJsonObject.getString("departmentname");
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


                            adapter = new Educational_leaflet_adapter(eem, getApplicationContext());
                            recyclerview.setAdapter(adapter);


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
                               /* android.support.v4.app.Fragment fragment = new Zayed_News_Event_Desc_Fragment();

                                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                                        .beginTransaction();
                                transaction.replace(R.id.frame_container, fragment, tag);
                                // titleStack.add(tag);
                                Bundle args = new Bundle();
                                args.putInt("pos", position);
                                // args.putString("desc", up_evnt_desc);
                                //  args.putString("desimg", up_evnt_img);
                                //  System.out.println(">>>>>depppppppppppppppppppppppp<<<" + dept_id);
                                System.out.println(">>>>><<<" + position);
                                fragment.setArguments(args);
                                transaction.addToBackStack(tag).commit();*/
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
}
