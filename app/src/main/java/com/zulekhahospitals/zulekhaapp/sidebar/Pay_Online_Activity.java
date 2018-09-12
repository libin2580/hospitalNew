package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 11/28/2017.
 */

public class Pay_Online_Activity extends Activity {


    View view;
    public JSONObject jsonobject;
    public JSONArray jsonarray;
    public ListView listview;
    ProgressBar progress = null;
    //ListnewsAdapter adapter;
    public static final String LINK="link";
    ArrayList<HashMap<String, String>> arraylist;
    FrameLayout container;
    FragmentManager fragmentManager;
    Fragment fragment;
    String Murl,wid,depname,result,wi_fi;
    TextView dn;
    //	 Keep all Images in array
    String k;
    WebView web = null;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_online_fragment);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        back= (ImageView) findViewById(R.id.back_image);
        analytics = FirebaseAnalytics.getInstance(Pay_Online_Activity.this);
        analytics.setCurrentScreen(Pay_Online_Activity.this,Pay_Online_Activity.this.getLocalClassName(), null /* class override */);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pay_Online_Activity.this.goBack();
            }
        });
      /*  Intent intent = getIntent();
        k=intent.getStringExtra("cme");*/
/*       // app.cBranchModelcontact = app.handler.helper.getBranches().get(1);
      //  System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>"+app.cBranchModelcontact.getCme());

//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(app.cBranchModelcontact.getWifi()));
//        startActivity(i);

//        listview = (ListView)view. findViewById(R.id.listview);
//        dn = (TextView)view. findViewById(R.id.header_text);
//
//        dn.setText(depname);
        //  new DownloadData().execute();*/
        web = (WebView) findViewById(R.id.web);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        web.loadUrl(BaseUrl+"app-online-payment.php");
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("MYAPP", "Page loaded");
                System.out.println("<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>"+url);
                if(url.equalsIgnoreCase("http://zulekhahospitals.com/app-paymentstatus.php")){

                   // final String message=jsonObject.getString("message");
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.postDelayed(new Runnable() {

                        public void run() {
                            // Run your task here
                            Pay_Online_Activity.this.goBack();
                            System.out.println("<<<<<<<<<1>>>>>>>>>>>>>>>>>>>>>>>"+"ok2");
                           // prvs_wnrs.setVisibility(View.GONE);

                        }
                    }, 10000 );


                }
                progress.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                System.out.println("<<<<<<<<<<111111111111>>>>>>>>>>>>>>>>>>>>>"+url);

                progress.setVisibility(ProgressBar.VISIBLE);
            }
        };
        web.setWebViewClient(client);


    }

    private void goBack() {
        super.onBackPressed();
    }


}