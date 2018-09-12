package com.zulekhahospitals.zulekhaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by libin on 3/3/2017.
 */
public class LiveChatFragment extends Activity {


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
        setContentView(R.layout.livechat_fragment);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LiveChatFragment.this.goBack();
            }
        });
        Intent intent = getIntent();
        k=intent.getStringExtra("cme");
        String link=intent.getStringExtra("link");
        // app.cBranchModelcontact = app.handler.helper.getBranches().get(1);
        //  System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>"+app.cBranchModelcontact.getCme());

//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(app.cBranchModelcontact.getWifi()));
//        startActivity(i);

//        listview = (ListView)view. findViewById(R.id.listview);
//        dn = (TextView)view. findViewById(R.id.header_text);
//
//        dn.setText(depname);
        //  new DownloadData().execute();

        web = (WebView) findViewById(R.id.web);
        web.setBackgroundColor(0x00000000);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        web.loadUrl(link);
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("MYAPP", "Page loaded");
                progress.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);

                progress.setVisibility(ProgressBar.VISIBLE);
            }
        };
        web.setWebViewClient(client);


    }

    private void goBack() {
        super.onBackPressed();
    }


}

