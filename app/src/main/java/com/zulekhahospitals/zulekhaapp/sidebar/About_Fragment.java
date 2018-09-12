package com.zulekhahospitals.zulekhaapp.sidebar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.appointment.ZH_Appointment_Fragment;
import com.zulekhahospitals.zulekhaapp.departments.Department_Fragment;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 11/8/2016.
 */

public class About_Fragment extends FragmentActivity implements View.OnClickListener {
    ImageButton dep,appoint,util,more;
    ImageView back;
    String k;
    WebView textData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about);
      /*dep= (ImageButton) findViewById(R.id.dep);
        appoint= (ImageButton) findViewById(R.id.appoint);
        util= (ImageButton) findViewById(R.id.util);
        more= (ImageButton) findViewById(R.id.more);
        dep.setOnClickListener(this);
        appoint.setOnClickListener(this);
        util.setOnClickListener(this);
        more.setOnClickListener(this);*/
        analytics = FirebaseAnalytics.getInstance(About_Fragment.this);
        analytics.setCurrentScreen(About_Fragment.this,About_Fragment.this.getLocalClassName(), null /* class override */);

        back= (ImageView) findViewById(R.id.back_image);
        textData=(WebView)findViewById(R.id.textData);
        textData.setBackgroundColor(0x00000000);
        Intent intent = getIntent();
        k=intent.getStringExtra("br_id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              About_Fragment.this.goBack();
            }
        });


        String text = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        textData.loadDataWithBaseURL("",String.format(text,getString(R.string.text_about)), "text/html", "UTF-8", "");

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
                i4.putExtra("br_id",k);
                startActivity(i4);
                break;
            case R.id.appoint:
                Intent i1 = new Intent(getApplicationContext(),ZH_Appointment_Fragment.class);
                //	i1.putExtra("current_but_id",1);
                //i1.putExtra("brn","sharjah");
                startActivity(i1);
                break;
            case R.id.util:
                //Intent i3 = new Intent(getApplicationContext(),UtilityFragment.class);
                //	i1.putExtra("current_but_id",1);
                //i1.putExtra("brn","sharjah");
                //startActivity(i3);
                break;
            case R.id.more:
                About_Fragment.this.goBack();
                break;
        }
    }
}
