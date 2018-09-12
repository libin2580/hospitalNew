package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 11/11/2016.
 */

public class LabReport extends Activity {


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
        setContentView(R.layout.lab_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        analytics = FirebaseAnalytics.getInstance(LabReport.this);
        analytics.setCurrentScreen(LabReport.this,LabReport.this.getLocalClassName(), null /* class override */);

        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             LabReport.this.goBack();
            }
        });
        Intent intent = getIntent();
        k=intent.getStringExtra("lab");
        // app.cBranchModelcontact = app.handler.helper.getBranches().get(1);
     System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>"+k);

//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(app.cBranchModelcontact.getWifi()));
//        startActivity(i);

//        listview = (ListView)view. findViewById(R.id.listview);
//        dn = (TextView)view. findViewById(R.id.header_text);
//
//        dn.setText(depname);
        //  new DownloadData().execute();
        web = (WebView) findViewById(R.id.web);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


      //3  web.getSettings().setLoadsImagesAutomatically(true);
       // web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setSupportZoom(true);
       // web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        web.loadUrl(k);
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("MYAPP", "Page loaded");
                progress.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url1, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url1, favicon);
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<111111111111>>>>>>>>>>>>>>>>>>>>>"+url1);

                progress.setVisibility(ProgressBar.GONE);
            }
        };
        web.setWebViewClient(client);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.d("WEB_VIEW_TEST", "error code:" + errorCode + " - " + description);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>"+url);
                pdfIntent.setDataAndType(Uri.parse(url), "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pdfIntent);

                return true;
            }
        });

    }

    private void goBack() {
        super.onBackPressed();
    }


}


