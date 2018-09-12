package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by Libin_Cybraum on 8/10/2016.
 */
public class CareerFragment extends Activity {


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

ImageView back;
    WebView web = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        back= (ImageView) findViewById(R.id.back_image);
        analytics = FirebaseAnalytics.getInstance(CareerFragment.this);
        analytics.setCurrentScreen(CareerFragment.this,CareerFragment.this.getLocalClassName(), null /* class override */);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              CareerFragment.this.goBack();
            }
        });

        String link=getIntent().getStringExtra("link");

        web = (WebView) findViewById(R.id.web);
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


