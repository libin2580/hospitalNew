package com.zulekhahospitals.zulekhaapp.branches;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.login.LoginActivity;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 9/29/2016.
 */

public class Screen_Selection_Fragment extends Activity implements View.OnClickListener, BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener {
    FrameLayout container;
    FragmentManager fragmentManager;
    String tag = "events";
    Fragment fragment;
    View view;
    RecyclerView recyclerview;
  //  static ArrayList<> upm;

    ProgressBar progress;
    String result, galry_image,Dat;
    static ArrayList<Screen_Selection_Model> ssm;
    Screen_Selection_Model sm;
    String id,screen,red,green,blue,alpha,fred,fgreen,fblue,falpha,hred,hgreen,hblue,halpha,edustatus;
    SliderLayout sliderShow;
    Button zh_dubai,zh_sharja,zh_zmc;
    String branch_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_selection_fragment_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        analytics = FirebaseAnalytics.getInstance(Screen_Selection_Fragment.this);
        analytics.setCurrentScreen(Screen_Selection_Fragment.this,Screen_Selection_Fragment.this.getLocalClassName(), null /* class override */);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      zh_dubai= (Button) findViewById(R.id.button1);
        zh_sharja= (Button) findViewById(R.id.button2);
        zh_zmc= (Button) findViewById(R.id.button3);
        zh_dubai.setOnClickListener(this);
        zh_sharja.setOnClickListener(this);
        zh_zmc.setOnClickListener(this);




        SharedPreferences myPrefs1 = this.getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        branch_name = myPrefs1.getString("branch", null);

        System.out.println("<<<<<<<<<sharedname>>>>" + branch_name);
        if (branch_name != null && !branch_name.isEmpty()){
            //Toast.makeText(getApplicationContext(), Reg_id, Toast.LENGTH_SHORT).show();
if(branch_name.equalsIgnoreCase("Dubai")){
    Intent synin = new Intent(getApplicationContext(), LoginActivity.class);
    synin.putExtra("current_but_id",0);
    synin.putExtra("str_branch","dubai");
    synin.putExtra("from","");
    startActivity(synin);
    finish();
}else if(branch_name.equalsIgnoreCase("Sharjah")){
    Intent synin = new Intent(getApplicationContext(), LoginActivity.class);
    synin.putExtra("current_but_id",1);
    synin.putExtra("str_branch","sharjah");
    synin.putExtra("from","");
    startActivity(synin);
    finish();
}else if(branch_name.equalsIgnoreCase("Zmc")){
    Intent synin = new Intent(getApplicationContext(), LoginActivity.class);
    synin.putExtra("current_but_id",2);
    synin.putExtra("str_branch","zmc");
    synin.putExtra("from","");
    startActivity(synin);
    finish();
}


        }
        sliderShow = (SliderLayout) findViewById(R.id.slider);


        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {

            new DownloadData1().execute();
        } else {



            Snackbar.with(Screen_Selection_Fragment.this,null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();

        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button1:
                new AlertDialog.Builder(this)
                        .setTitle("ZH")
                        .setMessage("Do you want to set ZH Dubai as default branch?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();

                                editor.putString("branch", "Dubai");
                                //editor.putString("fullname",fullname);
                               // editor.putString("username",username );

                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                i.putExtra("current_but_id",0);
                                i.putExtra("str_branch","dubai");
                                i.putExtra("from","");
                                finish();
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                               SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();

                                editor.putString("branchK", "Dubai");
                                //editor.putString("fullname",fullname);
                                // editor.putString("username",username );

                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                i.putExtra("current_but_id",0);
                                i.putExtra("str_branch","dubai");
                                i.putExtra("from","");
                                finish();
                                startActivity(i);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



                break;
            case R.id.button2:

                new AlertDialog.Builder(this)
                        .setTitle("ZH")
                        .setMessage("Do you want to set ZH Sharjah as default branch?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();

                                editor.putString("branch", "sharjah");
                                //editor.putString("fullname",fullname);
                                // editor.putString("username",username );

                                editor.commit();
                                Intent i1 = new Intent(getApplicationContext(),LoginActivity.class);
                                i1.putExtra("current_but_id",1);
                                i1.putExtra("str_branch","sharjah");
                                i1.putExtra("from","");
                                finish();
                                startActivity(i1);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                              SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();

                                editor.putString("branchK", "sharjah");
                                //editor.putString("fullname",fullname);
                                // editor.putString("username",username );

                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                i.putExtra("current_but_id",1);
                                i.putExtra("str_branch","sharjah");
                                i.putExtra("from","");
                                finish();
                                startActivity(i);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;
            case R.id.button3:

                new AlertDialog.Builder(this)
                        .setTitle("ZH")
                        .setMessage("Do you want to set ZMC as default branch?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();

                                editor.putString("branch", "Zmc");
                                //editor.putString("fullname",fullname);
                                // editor.putString("username",username );

                                editor.commit();

                                Intent i2 = new Intent(getApplicationContext(),LoginActivity.class);
                                i2.putExtra("current_but_id",2);
                                i2.putExtra("str_branch","zmc");
                                i2.putExtra("from","");
                                finish();
                                startActivity(i2);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                             SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();

                                editor.putString("branchK", "Zmc");
                                //editor.putString("fullname",fullname);
                                // editor.putString("username",username );

                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                i.putExtra("current_but_id",2);
                                i.putExtra("str_branch","zmc");
                                i.putExtra("from","");
                                finish();
                                startActivity(i);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }

    }


    private class DownloadData1 extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);



        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                HttpHandler h = new HttpHandler();
                String jsonString = h.makeServiceCall(BaseUrl+"iphone/screen.php");

                result = jsonString;


            } catch (Exception e) {
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {

            try {

                progress.setVisibility(ProgressBar.GONE);
                String sam = result.trim();
                System.out.println(">>>>>>>>>>>>>>>" + result);
                System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;

            if
                    (result.equalsIgnoreCase("[]")) {

               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                Snackbar.with(Screen_Selection_Fragment.this,null)
                        .type(Type.ERROR)
                        .message("No Events!")
                        .duration(Duration.LONG)

                        .show();
            } else if(result != null && !result.isEmpty() && !result.equals("null")) {
                JSONArray mArray;
                ssm = new ArrayList<Screen_Selection_Model>();
                try {
                    DefaultSliderView defaultSliderView = new DefaultSliderView(getApplicationContext());
                    mArray = new JSONArray(result);
                    for (int i = 0; i < mArray.length(); i++) {
                     sm = new Screen_Selection_Model();
                        JSONObject mJsonObject = mArray.getJSONObject(i);
                       //3 Log.d("OutPut", mJsonObject.getString("image"));

                      //  String ,,,,,,,;


                        id= mJsonObject.getString("id");
                        screen= mJsonObject.getString("screen");
                        red= mJsonObject.getString("red");
                        green= mJsonObject.getString("green");
                        blue= mJsonObject.getString("blue");
                        alpha= mJsonObject.getString("alpha");
                        fred= mJsonObject.getString("fred");
                        fgreen= mJsonObject.getString("fgreen");
                        fblue= mJsonObject.getString("fblue");
                        falpha= mJsonObject.getString("falpha");
                        hred= mJsonObject.getString("hred");
                        hgreen= mJsonObject.getString("hgreen");
                        hblue= mJsonObject.getString("hblue");
                        halpha= mJsonObject.getString("halpha");
                        edustatus= mJsonObject.getString("edustatus");
                       // String sponsor_id,event_id,name,email,phone,photo,address,website,content;
                         sm.setId(id);
                       sm.setScreen(screen);
                      sm.setRed(red);
                       sm.setGreen(green);
                        sm.setBlue(blue);
                        sm.setAlpha(alpha);
                        sm.setFred(fred);
                        sm.setFgreen(fgreen);
                        sm.setFblue(fblue);
                        sm.setFalpha(falpha);
                        sm.setHred(hred);
                        sm.setHgreen(hgreen);
                        sm.setHblue(hblue);
                        sm.setHalpha(halpha);
                        sm.setEdustatus(edustatus);


                        defaultSliderView.image(screen);
                        sliderShow.addSlider(defaultSliderView);

                        ssm.add(sm);
                        System.out.println("<<<oo>>>>" + sm);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sliderShow.setPresetTransformer(SliderLayout.Transformer.Stack);
                sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

            }
            else{

                System.out.println("nulllllllllllllllllllllllllllllllll");
            }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }

}
