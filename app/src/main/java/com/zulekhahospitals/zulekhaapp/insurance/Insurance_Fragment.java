package com.zulekhahospitals.zulekhaapp.insurance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.imagecapture.ImageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;


/**
 * Created by Libin_Cybraum on 7/18/2016.
 */
public class Insurance_Fragment extends Activity {

    String name;
    TextView tm;
    ProgressBar progress;
    String result;
    ArrayList<InsuranceModel> Ins;
    InsuranceModel im;
    String Title_of_rg, Cme_pnts, Venues, Dates;
    ListView lv;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      /*  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insurance_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        analytics = FirebaseAnalytics.getInstance(Insurance_Fragment.this);
        analytics.setCurrentScreen(Insurance_Fragment.this,Insurance_Fragment.this.getLocalClassName(), null /* class override */);

        lv = (ListView) findViewById(R.id.listView2);
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Insurance_Fragment.this.goBack();
            }
        });
        new DownloadData().execute();

    }

    private void goBack() {
        super.onBackPressed();
    }

    class DownloadData extends AsyncTask<Void, Void, Void> {

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

                /*HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/json.php?fid=115"
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
                String s=h.makeServiceCall(URL+"insurance.php?key=100");



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
            progress.setVisibility(ProgressBar.GONE);
            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
            if
                    (result.equalsIgnoreCase("[]")) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
            else if(result != null && !result.isEmpty() && !result.equals("null")) {
                JSONArray mArray;
                Ins = new ArrayList<InsuranceModel>();
                try {
                    mArray = new JSONArray(result);
                    for (int i = 0; i < mArray.length(); i++) {
                        im = new InsuranceModel();
                        JSONObject mJsonObject = mArray.getJSONObject(i);
                        Log.d("OutPut", mJsonObject.getString("insurance"));


                        Title_of_rg = mJsonObject.getString("insurance");

                        //  String o = mJsonObject.getString("specialty_other");
                        im.setIns(Title_of_rg);

                        System.out.println("<<<kk>>>>" + Title_of_rg);

                        //   System.out.println("<<<oo>>>>" + o);
                        //   onComplete();

                      Ins.add(im);

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
                ArrayAdapter<InsuranceModel> adapter1 = new MyList();
                lv.setAdapter(adapter1);
            }
            else{

                System.out.println("nulllllllllllllllllllllllllllllllll");
            }

        }

//        else {
//                Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();
//
//            }

    }

    private class MyList extends ArrayAdapter<InsuranceModel> {


        public MyList(){
            super(getApplicationContext(), R.layout.insurance_item_layout, Ins);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.insurance_item_layout, parent, false);
            }
            im = Ins.get(position);
//            if(mnth.equals(12)){
//               DATS.setText("Dec");
//            }
//            else if (mnth.equals(11))
//            {
//                DATS.setText("Nov");
//            }
//
//            tse= (TextView) findViewById(R.id.evt_typ);
//            tse1= (TextView) findViewById(R.id.dat);
//            tse2= (TextView) findViewById(R.id.datst);
//            Typeface tfr = Typeface.createFromAsset(getAssets(),"HelveticaNeueLTStd-Roman.otf");
//            tse.setTypeface(tfr);
//            Typeface tft = Typeface.createFromAsset(getAssets(),"HelveticaNeueLTStd-Roman.otf");
//            tse1.setTypeface(tft);
//            Typeface tftee = Typeface.createFromAsset(getAssets(),"HelveticaNeueLTStd-Roman.otf");
//            tse2.setTypeface(tftee);
            TextView TIT_ORG = (TextView) itemView.findViewById(R.id.evt_typ);
            TIT_ORG.setText(im.getIns());




            return itemView;
        }
    }
}