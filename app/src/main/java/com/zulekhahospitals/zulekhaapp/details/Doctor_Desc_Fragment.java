package com.zulekhahospitals.zulekhaapp.details;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.daimajia.slider.library.SliderLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.doctors.Doctor_Model;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
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

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL2;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 9/27/2016.
 */
public class Doctor_Desc_Fragment extends Activity implements View.OnClickListener {
    FrameLayout container;
    FragmentManager fragmentManager;

    Fragment fragment;
    View view;
ImageView im;
    WebView vv;
   //String Dat,Datimg,DatTopic,Datvenue,Dat1;
Button Message,Call;
    private int STORAGE_PERMISSION_CODE = 23;
    String tag = "events";
    private DrawerLayout drawer;
    private static final int MENU_ITEMS = 5;
    private final ArrayList<View> mMenuItems = new ArrayList<>(MENU_ITEMS);
    ImageView imv;
    NavigationView navView;
    private SliderLayout mDemoSlider;
    TextView txtunam;
    LinearLayout l1, l2, l3, l4, l5;
    TextView crs, sched, gal, book, remaindr, sched1;
    SliderLayout sliderShow;
ScrollView scrollview20;
    String userid, unams;
    ArrayList<String> imgarry = new ArrayList<>();
    Button bcrc, bglry, bsch, butbuk, butrem;
    static final int NUM_ITEMS = 4;



    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "ImageActivity";
TextView nam,qual,spec;
    String j,k,l,m,n,o,p,q,branch_id;
    String dtl;

    RecyclerView recyclerview;
WebView content_webview;
    Doctordtls_adapter adapter1;
    ProgressBar progress,progressbar_image;
    String result, galry_image, Dat, Dat1;
    static ArrayList<Doctor_Model> zdrm;
    Doctor_Model drm;
    TextView header_text;
    String doctid, doctname, docqual, docspec, docphoto,docexp,docother,dep_name;
    ImageView nextl,nextr,RQst_Apmt;
    LinearLayoutManager layoutManager1;
    int lastVisible;
    private int currentPage = 1;
    private int uprange = 20;
    private int downrange = 0;
    ImageView back;
    private int values = 0;

    public int pos=0;
    public JSONArray mArray;
    private TextView[] dots;
    private LinearLayout dotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_desc_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        progressbar_image=(ProgressBar)findViewById(R.id.progress_bar_imageview);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
 layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager1);
        recyclerview.setAdapter(adapter1);
        nextl= (ImageView) findViewById(R.id.image_next);
        nextr= (ImageView) findViewById(R.id.image_next_rgt);
        RQst_Apmt= (ImageView) findViewById(R.id.rqst_apmt);
        back= (ImageView) findViewById(R.id.back_image);
        content_webview=(WebView)findViewById(R.id.content_webview);
        header_text=(TextView)findViewById(R.id.header_text);
        analytics = FirebaseAnalytics.getInstance(Doctor_Desc_Fragment.this);
        analytics.setCurrentScreen(Doctor_Desc_Fragment.this,Doctor_Desc_Fragment.this.getLocalClassName(), null /* class override */);

        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        /*scrollview20=(ScrollView )findViewById(R.id.scrollview20);*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Doctor_Desc_Fragment.this.goBack();
            }
        });

nextl.setOnClickListener(this);
        nextr.setOnClickListener(this);




        /*nextl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hiii",Toast.LENGTH_SHORT).show();
              // recyclerview.getLayoutManager().smoothScrollToPosition(layoutManager1.findLastVisibleItemPosition() - 1);
          recyclerview.getLayoutManager().scrollToPosition(layoutManager1.findLastVisibleItemPosition() - 1);
            }
        });
        nextr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"heloooo",Toast.LENGTH_SHORT).show();
             recyclerview.getLayoutManager().scrollToPosition(layoutManager1.findFirstVisibleItemPosition() + 1);
            }
        });*/
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

            Snackbar.with(Doctor_Desc_Fragment.this,null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();
        }

        Intent intent = getIntent();
        dep_name=intent.getStringExtra("dep_name");
        header_text.setText(dep_name);
        j = intent.getStringExtra("dc_id");
       k= intent.getStringExtra("dc_nm");
      l = intent.getStringExtra("dc_ql");
       m = intent.getStringExtra("dc_sp");
       n = intent.getStringExtra("dc_pt");
    o= intent.getStringExtra("dc_ex");
        p= intent.getStringExtra("dc_oth");
q= intent.getStringExtra("dep_id");
        branch_id=intent.getStringExtra("branch_id");
        System.out.println("-------------------------------------------------------------");
        System.out.println(" j (dc_id):"+j);
        System.out.println(" q (dep_id):"+q);
        System.out.println("-------------------------------------------------------------");
dtl=p+""+o;
        System.out.println(",,,,,,,,,,,,department,,,,,,,,,,,,,,,,"+dep_name);
        im= (ImageView)  findViewById(R.id.imageView7);
        vv= (WebView) findViewById(R.id.vv);
       nam=(TextView)  findViewById(R.id.textView1);
        Typeface myFont4 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
        nam.setTypeface(myFont4);
        nam.setText(k);
        /*qual=(TextView) findViewById(R.id.textView2);
        Typeface myFont5 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
       qual.setTypeface(myFont5);
        qual.setText(l);
        spec=(TextView)findViewById(R.id.textView3);
        Typeface myFont6 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
       spec.setTypeface(myFont6);

        spec.setText(m);

        spec.setMovementMethod(new ScrollingMovementMethod());
         */
        String new_content=l+"<br/>"+m;
        WebSettings webSettings = content_webview.getSettings();
        webSettings.setDefaultFontSize(13);
        content_webview.setBackgroundColor(0x00000000);
        content_webview.loadDataWithBaseURL("", new_content, "text/html", "UTF-8", "");

        RQst_Apmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getApplicationContext(), Doctr_Apmt.class);
                //i2.putExtra("dep_id",j);
                i2.putExtra("dc_id",nam.getText().toString());
                i2.putExtra("dc_nm",dep_name);
                i2.putExtra("dep_id",q);
                i2.putExtra("branch_id",branch_id);
                System.out.println(",,,,,,,,,,,,status,,,,,,,,,,,,,,,,"+nam.getText().toString()+""+dep_name);
             /*   i2.putExtra("dc_ql",dc_ql);
                i2.putExtra("dc_sp",dc_sp);
                i2.putExtra("dc_pt",dc_pt);
                i2.putExtra("dc_ex",dc_ex);
                i2.putExtra("dc_oth",dc_oth);
                i2.putExtra("dep_id",k);*/
                startActivity(i2);
            }
        });
 /*       Dat1= getArguments().getString("des_id");
        Dat= getArguments().getString("desc");
        Datimg= getArguments().getString("desimg");
        DatTopic=getArguments().getString("title");
        Datvenue=getArguments().getString("venue");*/

        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {
            //Toast.makeText(getActivity(),
            //	"You have Internet Connection", Toast.LENGTH_LONG)


            loadDocData();
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

            Snackbar.with(Doctor_Desc_Fragment.this,null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();

        }




        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                System.out.println("position : " + position);

                int targetPosition = -1;

                if (layoutManager.canScrollHorizontally()) {
                    try {
                        if (velocityX < 0) {
                            targetPosition = position - 1;
                            if (targetPosition == -1) {
                                pos = targetPosition + 1;
                            } else {
                                pos = pos - 1;
                            }

                            System.out.println("targetPosition : " + targetPosition);
                            System.out.println("pos : " + pos);
                            addBottomDots(pos);
                            values=pos;
                        } else {
                            targetPosition = position + 1;
                            if (targetPosition == mArray.length()) {//'4' must be the number of slides ---
                                pos = targetPosition - 1;
                            } else {
                                pos = pos + 1;
                            }
                            System.out.println("targetPosition : " + targetPosition);
                            System.out.println("pos : " + pos);
                            addBottomDots(pos);
                            values=pos;
                        }
                    } catch (ArrayIndexOutOfBoundsException ae) {
                        ae.printStackTrace();
                    }

                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }

        };
        snapHelper.attachToRecyclerView(recyclerview);







    }

    private void goBack() {
        super.onBackPressed();
    }


    private void loadDocData() {
        progressbar_image.setVisibility(ProgressBar.VISIBLE);
        String myCustomStyleString="<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/OpenSansLight" +
                "\")}body,* {font-family: MyFont; font-size: medium;text-align: justify;}</style>";
      vv.loadDataWithBaseURL("", myCustomStyleString+dtl+"</div>", "text/html", "utf-8", null);
  //   vv.loadData(Dat, "text/html", "UTF-8");
        try {
            Picasso.with(getApplicationContext())
                    .load("http://zulekhahospitals.com/uploads/doctor/" + n.replaceAll("\\s+", "%20"))
                    .noFade()
                    .resize(150, 150)
                    .into(im);
        }catch (Exception e){

        }
        finally {
            progressbar_image.setVisibility(ProgressBar.GONE);
        }
    }


















    @Override
    public void onClick(View view) {
        try {


    final int count = adapter1.getItemCount();
        System.out.println("count :"+count);
        int limit=count;
              switch (view.getId()) {
            case R.id.image_next:
                if(values==0){
                    System.out.println("Inside prev button (values==0)");
                }else {
                   if(values>=1){
                       values--;
                       System.out.println("Inside prev button values="+values);
                       recyclerview.smoothScrollToPosition(values);
                       addBottomDots(values);
                   }
                }

                break;
            case R.id.image_next_rgt:
                if(values==limit-1){
                    recyclerview.smoothScrollToPosition(values);
                    System.out.println("Inside next button (values==limit)");
                        }else {
                        values++;
                        System.out.println("Inside next button values=" + values);
                        recyclerview.smoothScrollToPosition(values);
                    addBottomDots(values);

}
                break;
        }}
        catch (Exception e){
            e.printStackTrace();
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

System.out.println("http://zulekhahospitals.com/mobileapp/json5.php?fid=109&doctbydid="+q);


                HttpPost httppost = new HttpPost(URL2+"json5.php?fid=109&doctbydid="+q);
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
               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                Snackbar.with(Doctor_Desc_Fragment.this,null)
                        .type(Type.ERROR)
                        .message("No Events!")
                        .duration(Duration.LONG)

                        .show();
            } else if(result != null && !result.isEmpty() && !result.equals("null")) {

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
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerview.setLayoutManager(layoutManager);
                       // recyclerview.setAdapter(adapter1);
                        adapter1 = new Doctordtls_adapter(zdrm, getApplicationContext());
                        recyclerview.setAdapter(adapter1);
                        addBottomDots(0);

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


                }
            else{

                System.out.println("nulllllllllllllllllllllllllllllllll");
            }
                recyclerview.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                progressbar_image.setVisibility(ProgressBar.VISIBLE);
                                j = zdrm.get(position).getDoctid();
                                k= zdrm.get(position).getDoctname();//getStringExtra("dc_nm");
                                l =zdrm.get(position).getDocqual();//getStringExtra("dc_ql");
                                m = zdrm.get(position).getDocspec();//getStringExtra("dc_sp");
                                n = zdrm.get(position).getDocphoto();//getStringExtra("dc_pt");
                                o= zdrm.get(position).getDocexp();//getStringExtra("dc_ex");
                                p= zdrm.get(position).getDocother();//getStringExtra("dc_oth");
                              //  q= intent.getStringExtra("dep_id");
                                dtl=p+""+o;

                                nam.setText(k);
                                String new_content=l+"<br/>"+m;
                                WebSettings webSettings = content_webview.getSettings();
                                webSettings.setDefaultFontSize(13);
                                content_webview.setBackgroundColor(0x00000000);
                                content_webview.loadDataWithBaseURL("", new_content, "text/html", "UTF-8", "");

                                /*qual.setText(l);
                                spec.setText(m);*/
                                String myCustomStyleString="<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/OpenSansLight" +
                                        "\")}body,* {font-family: MyFont; font-size: medium;text-align: justify;}</style>";
                                vv.loadDataWithBaseURL("", myCustomStyleString+dtl+"</div>", "text/html", "utf-8", null);
                                //   vv.loadData(Dat, "text/html", "UTF-8");
                                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>"+n);


                                System.out.println("______________________________________________________________________________________________");
                                System.out.println("Image url in click load : http://zulekhahospitals.com/uploads/doctor/"+n.replaceAll("\\s+","%20"));
                                System.out.println("______________________________________________________________________________________________");


                                try {
                                    Picasso.with(getApplicationContext())
                                            .load("http://zulekhahospitals.com/uploads/doctor/" + n.replaceAll("\\s+", "%20"))
                                            .noFade()
                                            .resize(150, 150)
                                            .into(im);
                                }catch (Exception e){

                                }
                                finally {
                                    progressbar_image.setVisibility(ProgressBar.GONE);
                                }

                            }
                        })
                );
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
    public void addBottomDots(int currentPage) {
        try{
            if (mArray != null && mArray.length() > 0 & !mArray.equals(null)) {
                dots = new TextView[mArray.length()];
                System.out.println("dots.length : "+dots.length);
                System.out.println("currentPage : "+currentPage);


                int[] colorsActive = getResources().getIntArray(R.array.array_dot_active_new);
                int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive_new);

                dotsLayout.removeAllViews();

                for (int i = 0; i < dots.length; i++) {
                    dots[i] = new TextView(this);
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(35);
                    dots[i].setTextColor(colorsInactive[0]);
                    dotsLayout.addView(dots[i]);
                }

                if (dots.length > 0)
                    dots[currentPage].setTextColor(colorsActive[0]);
            } else {
                System.out.println(">>>>");
                //dots = new TextView[array1.length()];
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    }
