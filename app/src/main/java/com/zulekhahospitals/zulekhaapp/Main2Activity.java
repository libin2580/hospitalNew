package com.zulekhahospitals.zulekhaapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.internal.Utility;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.zulekhahospitals.zulekhaapp.appointment.ZH_Appointment_Fragment;
import com.zulekhahospitals.zulekhaapp.branches.Screen_Selection_Fragment;
import com.zulekhahospitals.zulekhaapp.departments.Department_Fragment;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leaflet_Fragment;
import com.zulekhahospitals.zulekhaapp.feedback.LoginActivityFeedBack;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.insurance.Insurance_Fragment;
import com.zulekhahospitals.zulekhaapp.insurance.InsurenceTracking;
import com.zulekhahospitals.zulekhaapp.login.LoginActivity;
import com.zulekhahospitals.zulekhaapp.login.NewReg;
import com.zulekhahospitals.zulekhaapp.myaccount.MyAccount;
import com.zulekhahospitals.zulekhaapp.newintro.NetworkCheckingClass;
import com.zulekhahospitals.zulekhaapp.newintro.New_HttpHandler;
import com.zulekhahospitals.zulekhaapp.sidebar.About_Fragment;
import com.zulekhahospitals.zulekhaapp.sidebar.CareerFragment;
import com.zulekhahospitals.zulekhaapp.sidebar.CmeFragment;
import com.zulekhahospitals.zulekhaapp.sidebar.ComplaintFragment;
import com.zulekhahospitals.zulekhaapp.sidebar.ContactFragment;
import com.zulekhahospitals.zulekhaapp.sidebar.Insurance_Aproval_Tracking_Fragment;
import com.zulekhahospitals.zulekhaapp.sidebar.LabReport;
import com.zulekhahospitals.zulekhaapp.sidebar.Pay_Online_Activity;
import com.zulekhahospitals.zulekhaapp.sidebar.RewardSystems_Fragment;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.RecyclerItemClickListener;
import com.zulekhahospitals.zulekhaapp.utility.UtilityFragment;
import com.zulekhahospitals.zulekhaapp.utility.ZHconstansts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.refactor.lib.colordialog.PromptDialog;
import io.fabric.sdk.android.Fabric;
import kotlin.Pair;

import static com.zulekhahospitals.zulekhaapp.R.attr.height;
import static com.zulekhahospitals.zulekhaapp.R.id.width;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    SliderLayout sliderShow;
    LinearLayout nav_icon;
    private static final String TWITTER_KEY = "sD0JMU3G4X46SCT0vprRxL26I";
    private static final String TWITTER_SECRET = "xgdize9TRYbyGkdT0KuQp3W09mwzJvXDIzI9x3dwDn2aat4LGs";
    public static boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_DELAY = 2000;
    DefaultSliderView defaultSliderView;
    String Banners[];
    private static long back_pressed;
    String lat, lng;
    String str_branch;
    TextView nav_username;
    String version_from_server;
    String menu_id, menu_wifi, menu_chat, menu_labreport, menu_career, menu_cme, menu_dubaiphone, menu_sharjahphone, menu_zmcphone;

    ImageView dep, apmt, utly, wifi_i, call_i, feedback_i, myaccount, image_careerportal, image_educational_lef, image_viewlabreport, image_viewcme, imageView_insurance_partners, image_utilities, imageView_about_us, imageView_contact_us;
    ArrayList<Zulekha_Highlight_Model> zhm;
    Zulekha_Highlight_Model zh;

    String result, k, u;
    String latestVersion = "";
    int updation_flag = 0;
    String Reg_id, fullname, userid;
    String insatalled_app_version = "", new_app_version = "";
    String ut;
    TextView tit,push_msg;
    ImageView utilities;


    public boolean isConnected = false;
    View customView, customViewCall;
    LinearLayout update_yes, update_no, call_yes, call_no;
    private PopupWindow mPopupWindow, mPopupWindowCall;
    String branch_name;
    RelativeLayout top_layout;
    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    TextView poptext;
    SharedPreferences preferencesd;
    String id, center, pobox, appointment_s, phone, fax, appointment, information, resume, near, latitude, longitude, adress, toll_free, report, cme, wifi, toll_free_num;
    ProgressBar progress_bar_slider;
    LinearLayout nav_chat, nav_contact_us, nav_about_aulekha_hospital, nav_career_portal, nav_educational_leaflet, nav_view_lab_report, nav_complaints_and_suggestions, nav_insurance_partners, nav_cme, nav_change_branch, nav_rate_this_app, nav_logout, nav_login, fb_icon, twitter_icon, gplus_icon, nav_utilities, nav_location_map, nav_feedback, nav_my_account;

    String branch_id[];
    String branch_phone_number[];
    String branch_tollfree_phone_number[];
    String branch_latitude[];
    String branch_longitude[];
    String tolfree = "";
    WebView twiter_feed;
    static ArrayList<Action_Services_Model> asm;
    Action_Services_Model am;
    private int STORAGE_PERMISSION_CODE = 23;
    private ViewPager viewPager;
    /*private MyViewPagerAdapter myViewPagerAdapter;*/
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;

    private ProgressDialog pdlog;
    JSONArray array1;
    private New_ViewPagerAdapter va;
    private ArrayList imageURL;
    ProgressBar progress;
    String ids, urls, value;
    RecyclerView recyclerview;
LinearLayoutManager layoutManager1;
    Banner_adapter adapter1;
    ZayedModel zm;
    String jsonString;
    String action, phones, banner,category;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public int pos=0;
    public Handler handler;
   // CarouselLayoutManager layoutManager1;
    LinearLayout slider_lay,slider_lay1;
    String refreshedToken;
    List<Pair<String, String>> params;
    String message;
    View custompopup_view;
    PopupWindow push_popupwindow;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main2);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        preferencesd = getSharedPreferences("MyPref", MODE_PRIVATE);
        userid = preferencesd.getString("user_id", null);
        System.out.println("userid : " + userid);
        nav_icon = (LinearLayout) findViewById(R.id.nav_icon);
        System.out.println("logoutt" + userid);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tit = (TextView) findViewById(R.id.toolbar_title_new);
        progress_bar_slider = (ProgressBar) findViewById(R.id.progress_bar_slider);
        sliderShow = (SliderLayout) findViewById(R.id.slider);
        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        // twiter_feed = (WebView) findViewById(R.id.webView);
slider_lay= (LinearLayout) findViewById(R.id.slider_lay);

        analytics = FirebaseAnalytics.getInstance(Main2Activity.this);
        analytics.setCurrentScreen(Main2Activity.this,Main2Activity.this.getLocalClassName(), null /* class override */);

       // slider_lay1= (LinearLayout) findViewById(R.id.slider_lay1);
        handler=new Handler();

        imageURL = new ArrayList();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
     // layoutManager1 = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,false);
        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
       // layoutManager1.setPostLayoutListener(new CarouselZoomPostLayoutListener());
      //  layoutManager1.setMaxVisibleItems(3);
        recyclerview.setLayoutManager(layoutManager1);
        recyclerview.setAdapter(adapter1);
        final LayoutInflater inflator = (LayoutInflater) Main2Activity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        custompopup_view = inflator.inflate(R.layout.push_popup, null);
        coordinatorLayout=findViewById(R.id.coordinatorLayout);
        push_msg = (TextView)custompopup_view. findViewById(R.id.push_msg);
        push_msg.setText(message);

        Button submit= (Button) custompopup_view. findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                push_popupwindow.dismiss();
            }
        });
      /*  recyclerview.setHasFixedSize(true);*/
       // recyclerview.addOnScrollListener(new CenterScrollListener());
        Intent intent1 = getIntent();
        String message = intent1.getStringExtra("message");

        System.out.println("*******message111111111111-"+message);

        if(message!=null){
            new PromptDialog(this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText("")
                    .setContentText(message)
                    .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
           // dialog.findViewById(R.id.confirm_button).setBackgroundColor(Color.parseColor("#368aba"));
           // display_patient_id();
            System.out.println("-------------------message111111111111-------------------------------"+message);

        }
        Intent intent = getIntent();

        SharedPreferences myPrefs = this.getSharedPreferences("MyPref", MODE_PRIVATE);


        try {
            String appointment_status = getIntent().getStringExtra("appointmentsubmitted");
            if (appointment_status.equalsIgnoreCase("true")) {
                //new FetchImage().execute();
                FetchImage1();
                Snackbar.with(Main2Activity.this, null)
                        .type(Type.SUCCESS)
                        .message("Appointment Submitted")
                        .duration(Duration.LONG)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            str_branch = intent.getStringExtra("str_branch");
            System.out.println("branch inside Main2Activity : " + str_branch);
            String isfromfeedback = intent.getStringExtra("from2");
            System.out.println("--------------------------------------------------");
            System.out.println("inside main2activity");
            System.out.println("isfromfeedback : " + isfromfeedback);
            System.out.println("--------------------------------------------------");

            if (isfromfeedback != null || isfromfeedback != "" || !isfromfeedback.isEmpty()) {
                if (isfromfeedback.equalsIgnoreCase("feedbacklogin")) {
                    Intent i9 = new Intent(Main2Activity.this, LoginActivityFeedBack.class);
                    i9.putExtra("branch_id", ut);
                    i9.putExtra("str_branch", preferencesd.getString("str_branch", null));
                    System.out.println("putExtra branch_id : " + ut);

                    startActivity(i9);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (intent.getBooleanExtra("EXIT", false)) {
            Intent i = new Intent(Main2Activity.this, LoginActivity.class);
            i.putExtra("str_branch", str_branch);
            i.putExtra("from", "");
            startActivity(i);
            finish();
        }

        k = intent.getStringExtra("str_branch");
        try {


            ut = intent.getStringExtra("brn_id");
            if (ut != null) {
                if (ut.equalsIgnoreCase("1")) {
                    lat = "25.291000";
                    lng = "55.384250";

                    tit.setText("ZULEKHA HOSPITAL DUBAI");
                } else if (ut.equalsIgnoreCase("2")) {
                    lat = "25.368350";
                    lng = "55.404533";
                    tit.setText("ZULEKHA HOSPITAL SHARJAH");
                } else if (ut.equalsIgnoreCase("33")) {
                    lat = "25.2679547";
                    lng = "55.3791249";
                    tit.setText("ZULEKHA MEDICAL CENTER");
                }
            }
        } catch (Exception E) {

        }


        NetworkCheckingClass networkCheckingClass1 = new NetworkCheckingClass(Main2Activity.this);
        boolean is = networkCheckingClass1.ckeckinternet();
        if (is) {
           // new FetchImage().execute();
            FetchImage1();
            new getLinks().execute();
        } else {
            Snackbar.with(Main2Activity.this, null)
                    .type(Type.ERROR)
                    .message("Please enable internet connection!")
                    .duration(Duration.LONG)

                    .show();
        }

        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(navigationView);
            }
        });


        LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customView = inflater2.inflate(R.layout.popup_update, null);

        update_yes = (LinearLayout) customView.findViewById(R.id.update_yes);
        update_no = (LinearLayout) customView.findViewById(R.id.update_no);
        update_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zulekhahospitals.zulekhaapp"));
                startActivity(i);
                mPopupWindow.dismiss();
            }
        });


        update_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + ut);
        System.out.println(getIntent().getIntExtra("current_but_id", 0));
        switch (getIntent().getIntExtra("current_but_id", 0)) {
            case 0:

                //   about.setBackgroundResource(R.color.colorPrimary);
                toolbar.setTitle("ZH Dubai");

                u = "1";
                break;
            case 1:

                //  service.setBackgroundResource(R.color.colorPrimary);//(getResources().getDrawable(R.drawable.selctbtn));

                toolbar.setTitle("ZH Sharjah");
                u = "2";
                break;
            case 2:
                // location.setBackgroundResource(R.color.colorPrimary);
                u = "33";
                toolbar.setTitle("ZMC");

                break;

            default:
                break;
        }
        LayoutInflater inflater3 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customViewCall = inflater3.inflate(R.layout.popup_call, null);
        poptext = (TextView) customViewCall.findViewById(R.id.pop_call_text);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<tollfreee>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + toll_free);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<tollfreenum>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + toll_free_num);
        if (zhm != null) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<tollfreenum>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + zhm.get(0).getToll_free());
        }
        call_yes = (LinearLayout) customViewCall.findViewById(R.id.call_yes);
        call_no = (LinearLayout) customViewCall.findViewById(R.id.call_no);

        call_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindowCall.dismiss();
            }
        });

        if (DetectConnection
                .checkInternetConnection(Main2Activity.this)) {
            //Toast.makeText(getActivity(),
            //	"You have Internet Connection", Toast.LENGTH_LONG)

            new DownloadData().execute();


        } else {

            /*final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");

            alertDialog.setMessage("No Internet");


            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();


                }
            });
            alertDialog.show();
*/
            Snackbar.with(Main2Activity.this, null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();

        }








       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
     /*   drawer.setBackgroundResource(R.drawable.sidebar_bg);*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundResource(R.drawable.sidebar_bg);

        nav_contact_us = (LinearLayout) findViewById(R.id.nav_contact_us);
        nav_about_aulekha_hospital = (LinearLayout) findViewById(R.id.nav_about_aulekha_hospital);
        nav_career_portal = (LinearLayout) findViewById(R.id.nav_career_portal);
        nav_educational_leaflet = (LinearLayout) findViewById(R.id.nav_educational_leaflet);
        nav_view_lab_report = (LinearLayout) findViewById(R.id.nav_view_lab_report);
        nav_complaints_and_suggestions = (LinearLayout) findViewById(R.id.nav_complaints_and_suggestions);
        nav_insurance_partners = (LinearLayout) findViewById(R.id.nav_insurance_partners);
        nav_cme = (LinearLayout) findViewById(R.id.nav_cme);
        nav_chat = (LinearLayout) findViewById(R.id.nav_chat);
        nav_change_branch = (LinearLayout) findViewById(R.id.nav_change_branch);
        nav_rate_this_app = (LinearLayout) findViewById(R.id.nav_rate_this_app);
        nav_logout = (LinearLayout) findViewById(R.id.nav_logout);
        nav_login = (LinearLayout) findViewById(R.id.nav_login);
        fb_icon = (LinearLayout) findViewById(R.id.fb_icon);
        twitter_icon = (LinearLayout) findViewById(R.id.twitter_icon);
        gplus_icon = (LinearLayout) findViewById(R.id.gplus_icon);
        nav_utilities = (LinearLayout) findViewById(R.id.nav_utilities);
        nav_location_map = (LinearLayout) findViewById(R.id.nav_location_map);
        nav_username = (TextView) findViewById(R.id.nav_user_name);
        nav_feedback = (LinearLayout) findViewById(R.id.nav_feedback);
        nav_my_account = (LinearLayout) findViewById(R.id.nav_my_account);

        nav_username.setText(myPrefs.getString("fullname", null));


        nav_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);

                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent i1 = new Intent(Main2Activity.this, LiveChatFragment.class);
                    // i1.putExtra("current_but_id",0);
                    i1.putExtra("br_id", ut);
                    i1.putExtra("link", menu_chat);
                    startActivity(i1);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);


            }
        });
        nav_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);

                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent i1 = new Intent(Main2Activity.this, ContactFragment.class);
                    // i1.putExtra("current_but_id",0);
                    i1.putExtra("br_id", ut);
                    startActivity(i1);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);


            }
        });

        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {


                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                System.out.println("position : "+position);

                int targetPosition = -1;

                if (layoutManager.canScrollHorizontally()) {
                    System.out.println("adapter1.getItemCount() : "+adapter1.getItemCount());
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
                        } else {
                            targetPosition = position + 1;
                            if (targetPosition == 4) {
                                pos = targetPosition - 1;
                            } else {
                                pos = pos + 1;
                            }
                            System.out.println("targetPosition : " + targetPosition);
                            System.out.println("pos : " + pos);
                            addBottomDots(pos);
                        }



                    }catch (ArrayIndexOutOfBoundsException ae){
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



        final int speedScroll = 4000;
        final Runnable runnable = new Runnable()
        {

            boolean flag = true;
            @Override public void run()
            {
                try {
                    if (pos < adapter1.getItemCount()) {
                        if (pos == adapter1.getItemCount() - 1) {
                            flag = false;
                        } else if (pos == 0) {
                            flag = true;
                        }
                        if (flag)
                            pos++;
                        else
                            pos--;

                        recyclerview.smoothScrollToPosition(pos);
                        handler.postDelayed(this, speedScroll);
                    }
                    addBottomDots(pos);
                }catch (Exception e){e.printStackTrace();}
            }

        };
       handler.postDelayed(runnable,speedScroll);

        final Handler handler = new Handler();



        nav_utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent i1 = new Intent(Main2Activity.this, UtilityFragment.class);
                    // i1.putExtra("current_but_id",0);
                    i1.putExtra("br_id", ut);
                    startActivity(i1);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        nav_location_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)


                    Intent i7 = new Intent(getApplicationContext(), MapsActivityNew.class);
                    //  i3.putExtra("current_but_id",1);
                    i7.putExtra("lat_id", lat);
                    i7.putExtra("lng_id", lng);
                    startActivity(i7);


                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        nav_about_aulekha_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);

                Intent i1 = new Intent(Main2Activity.this, About_Fragment.class);
                // i1.putExtra("current_but_id",0);
                i1.putExtra("br_id", ut);
                startActivity(i1);

                drawer.closeDrawer(GravityCompat.START);


            }
        });
        nav_career_portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent pr = new Intent(Main2Activity.this, CareerFragment.class);
                    pr.putExtra("link", menu_career);
                    // pr.putExtra("cre",zhm.get(0).getCme());
                    startActivity(pr);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);


            }
        });
        nav_educational_leaflet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent i1 = new Intent(Main2Activity.this, Educational_leaflet_Fragment.class);
                    // i1.putExtra("current_but_id",0);
                    startActivity(i1);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);


            }
        });
        nav_view_lab_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent ic = new Intent(Main2Activity.this, LabReport.class);
                    ic.putExtra("lab", menu_labreport);
                    startActivity(ic);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);


            }
        });
        nav_complaints_and_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent ip = new Intent(Main2Activity.this, ComplaintFragment.class);
                //  i3.putExtra("current_but_id",1);
                startActivity(ip);

            }
        });
        nav_insurance_partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent ip = new Intent(Main2Activity.this, Insurance_Fragment.class);
                    //  i3.putExtra("current_but_id",1);
                    startActivity(ip);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);


            }
        });
        nav_cme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)


                    Intent ic = new Intent(Main2Activity.this, CmeFragment.class);
                    ic.putExtra("cme", menu_cme);
                    startActivity(ic);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        nav_change_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                SharedPreferences preferences1 = Main2Activity.this.getSharedPreferences("MyPref1", MODE_PRIVATE);
                branch_name = preferences1.getString("barnch", null);
                preferences1.edit().clear().commit();
                // preferences.edit().clear().commit();
                //preferences1.edit().clear().commit();
                // progress.setVisibility(View.GONE);
                Intent i = new Intent(Main2Activity.this, Screen_Selection_Fragment.class);
                finish();
                startActivity(i);

            }
        });
        nav_rate_this_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zulekhahospitals.zulekhaapp"));
                startActivity(i);

            }
        });
        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                SharedPreferences settings = Main2Activity.this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                logout();

            }
        });
        nav_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent intlog = new Intent(Main2Activity.this, LoginActivity.class);
                intlog.putExtra("str_branch", str_branch);
                intlog.putExtra("from", "");

                startActivity(intlog);
                finish();

            }
        });

        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs2 = getSharedPreferences("MyPref", MODE_PRIVATE);
                Reg_id = myPrefs2.getString("user_id", null);
                if (Reg_id != null && !Reg_id.isEmpty()) {
                    /*Intent i9 = new Intent(getApplicationContext(),LoginActivityFeedBack.class);
                    //  i3.putExtra("current_but_id",1);
                    System.out.println("branchname........"+ut);
                    i9.putExtra("branch_name",ut);

                    startActivity(i9);*/

                    Intent i9 = new Intent(Main2Activity.this, LoginActivityFeedBack.class);
                    i9.putExtra("branch_id", ut);
                    i9.putExtra("str_branch", str_branch);

                    startActivity(i9);
                } else {
                    Intent ir = new Intent(Main2Activity.this, LoginActivity.class);
                    ir.putExtra("from", "feedbacklogin");
                    ir.putExtra("str_branch", str_branch);
                    finish();
                    startActivity(ir);
                }
            }
        });


        nav_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs1 = getSharedPreferences("MyPref", MODE_PRIVATE);
                Reg_id = myPrefs1.getString("user_id", null);
                if (Reg_id != null && !Reg_id.isEmpty()) {
                    Intent i = new Intent(Main2Activity.this, MyAccount.class);
                    i.putExtra("user_id", userid);
                    startActivity(i);
                } else {
                    Intent i9 = new Intent(Main2Activity.this, LoginActivity.class);
                    i9.putExtra("current_but_id", 0);
                    i9.putExtra("str_branch", "dubai");
                    i9.putExtra("from", "");
                    i9.putExtra("str_branch", str_branch);
                    finish();
                    startActivity(i9);
                }
            }
        });


        fb_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.facebook.com/zulekhahospitals");
            }
        });

        twitter_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://twitter.com/zulekhahosptls");
            }
        });
        gplus_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://plus.google.com/zulekhahospitals");
            }
        });


        Reg_id = myPrefs.getString("username", null);
        // photo=myPrefs.getString("photo", null);
        fullname = myPrefs.getString("fullname", null);
        // Toast.makeText(this, myPrefs.getString("username", ""), Toast.LENGTH_LONG).show();

        if (userid != null) {   // condition true means user is already login
            //  navigationView.getMenu().getItem(6).setIcon(R.drawable.zayed_logout);
            /*navigationView.getMenu().getItem(9).setTitle("Log Out");
            navigationView.getMenu().getItem(8).setVisible(true);*/
            nav_logout.setVisibility(View.VISIBLE);
            nav_login.setVisibility(View.GONE);

            System.out.println("logoutt");


        } else {
            //  navigationView.getMenu().getItem(6).setIcon(R.drawable.login);
            /*navigationView.getMenu().getItem(8).setVisible(false);
            navigationView.getMenu().getItem(9).setTitle("Log in");*/
            nav_logout.setVisibility(View.GONE);
            nav_login.setVisibility(View.VISIBLE);
            System.out.println("loginnn");


        }
        dep = (ImageView) findViewById(R.id.imageView1);
        apmt = (ImageView) findViewById(R.id.imageView2);
        utly = (ImageView) findViewById(R.id.imageView3);
        wifi_i = (ImageView) findViewById(R.id.imageView7);
        call_i = (ImageView) findViewById(R.id.imageView8);
        feedback_i = (ImageView) findViewById(R.id.imageView9);
        myaccount = (ImageView) findViewById(R.id.imageView6);
        image_careerportal = (ImageView) findViewById(R.id.image_careerportal);
        image_educational_lef = (ImageView) findViewById(R.id.image_educational_lef);
        image_viewlabreport = (ImageView) findViewById(R.id.image_viewlabreport);
        image_viewcme = (ImageView) findViewById(R.id.image_viewcme);
        imageView_insurance_partners = (ImageView) findViewById(R.id.imageView_insurance_partners);
        image_utilities = (ImageView) findViewById(R.id.image_utilities);
        imageView_about_us = (ImageView) findViewById(R.id.imageView_about_us);
        imageView_contact_us = (ImageView) findViewById(R.id.imageView_contact_us);

        apmt.setOnClickListener(this);
        dep.setOnClickListener(this);
        utly.setOnClickListener(this);
        wifi_i.setOnClickListener(this);
        /*call_i.setOnClickListener(this);*/
        feedback_i.setOnClickListener(this);
        myaccount.setOnClickListener(this);

        //  service.setBackgroundResource(R.color.colorPrimary);//(getResources().getDrawable(R.drawable.selctbtn));
        image_utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ic = new Intent(Main2Activity.this, UtilityFragment.class);
                ic.putExtra("br_id", ut);
                startActivity(ic);

            }
        });
        imageView_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                Intent ic = new Intent(Main2Activity.this, InsurenceTracking.class);
               // ic.putExtra("cme", zhm.get(0).getCme());
                startActivity(ic);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
            }
        });
        imageView_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)

                    Intent i1 = new Intent(Main2Activity.this, ContactFragment.class);
                    // i1.putExtra("current_but_id",0);
                    i1.putExtra("br_id", ut);
                    startActivity(i1);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }
                drawer.closeDrawer(GravityCompat.START);




                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zulekhahospitals.zulekhaapp"));
                startActivity(i);*/
            }
        });
        try {

/*
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

                    }
                    break;
            }}
        catch (Exception e){
            e.printStackTrace();
        }*/
            image_careerportal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DetectConnection
                            .checkInternetConnection(Main2Activity.this)) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)

                        Intent pr = new Intent(Main2Activity.this, CareerFragment.class);
                        pr.putExtra("link", menu_career);
                        // pr.putExtra("cre",zhm.get(0).getCme());
                        startActivity(pr);

                    } else {


                        Snackbar.with(Main2Activity.this, null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();

                    }
                }
            });

            image_educational_lef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DetectConnection
                            .checkInternetConnection(Main2Activity.this)) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)

                        Intent i1 = new Intent(Main2Activity.this, Educational_leaflet_Fragment.class);
                        // i1.putExtra("current_but_id",0);
                        startActivity(i1);
                    } else {


                        Snackbar.with(Main2Activity.this, null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();

                    }

                }
            });
            image_viewlabreport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DetectConnection
                            .checkInternetConnection(Main2Activity.this)) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)

                        Intent ic = new Intent(Main2Activity.this, LabReport.class);
                        ic.putExtra("lab", menu_labreport);
                        startActivity(ic);
                    } else {


                        Snackbar.with(Main2Activity.this, null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();

                    }
                }
            });
            image_viewcme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DetectConnection
                            .checkInternetConnection(Main2Activity.this)) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)


                        Intent ic = new Intent(Main2Activity.this, CmeFragment.class);
                        ic.putExtra("cme", menu_cme);
                        startActivity(ic);
                    } else {


                        Snackbar.with(Main2Activity.this, null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();

                    }
                }
            });
            imageView_insurance_partners.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DetectConnection
                            .checkInternetConnection(Main2Activity.this)) {
                        //Toast.makeText(getActivity(),
                        //	"You have Internet Connection", Toast.LENGTH_LONG)


                        Intent ic = new Intent(Main2Activity.this, Pay_Online_Activity.class);
                        ic.putExtra("cme", zhm.get(0).getCme());
                        startActivity(ic);
                    } else {


                        Snackbar.with(Main2Activity.this, null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();

                    }
                }
            });

        } catch (NullPointerException e) {

        }
    }

    private void FetchImage1() {
        params = new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>("user_id",userid));
            add(new Pair<String, String>("device", "android"));
            add(new Pair<String, String>("device_token", refreshedToken));


        }};
        System.out.println("_________params_____________" + params);
      //  URL
                //"http://www.zulekhahospitalsdemo.com.php56-4.phx1-2.websitetestlink.com/mobileapp/android/"
        Fuel.post(BaseUrl+"android/"+"zulekhabaner.php",params).responseString(new com.github.kittinunf.fuel.core.Handler<String>() {
            @Override
            public void success(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, String s) {
                System.out.println("_________sssssssssssssss_____________" + s);
                slider_lay.setVisibility(View.VISIBLE);

                // making notification bar transparent
                changeStatusBarColor();
                viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                try {

                    JSONObject jsonObj = new JSONObject(s);
                    String status = jsonObj.getString("status");
                    System.out.println("_________status1_____________" + status);
                    //      final String data = jsonObj.getString("message");
                    // System.out.println("___________data___________" + data);
                    if(status.equalsIgnoreCase("true")){

                        System.out.println("_________status1_____________" + status);
                        // array1 = new JSONArray(jsonString);
                        asm = new ArrayList<Action_Services_Model>();
                        if (status.equals("true")) {
                            array1 = jsonObj.getJSONArray("data");
                            for (int i = 0; i < array1.length(); i++) {
                                JSONObject obj = array1.getJSONObject(i);
                                am = new Action_Services_Model();
                                // String banner = obj.getString("banner");

                                banner = obj.getString("banner");
                                action = obj.getString("action");
                                value = obj.getString("value");
                                category=obj.getString("type");
                                if(category.equalsIgnoreCase("image")){
                                    am.setType(1);
                                }

                                if(category.equalsIgnoreCase("facebook")){
                                    am.setType(2);
                                }
                                if(category.equalsIgnoreCase("twitter")){
                                    am.setType(0);
                                }

                                am.setBanner(banner);
                                am.setAction(action);
                                am.setValue(value);
                                am.setCategory(category);
                                System.out.println("_________id_____________" + ids);
                                System.out.println("_________id_____________" + urls);
                                System.out.println("_________value_____________" + value);
                                System.out.println("_________category_____________" + category);
                                asm.add(am);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerview.setLayoutManager(layoutManager);
                                // recyclerview.setAdapter(adapter1);
                                adapter1 = new Banner_adapter(asm, getApplicationContext());
                                recyclerview.setAdapter(adapter1);
                                //slider_lay1.setVisibility(View.VISIBLE);
                                addBottomDots(0);
                            }

                        } else {
                            System.out.println("nodots");
                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, FuelError fuelError) {

            }
        });

    }

    private void display_patient_id() {
        try {
            // plus_btn.setVisibility(View.GONE);

           push_popupwindow = new PopupWindow(custompopup_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (Build.VERSION.SDK_INT >= 21) {
                push_popupwindow.setElevation(5.0f);
            }
            push_popupwindow.setFocusable(true);
            push_popupwindow.setAnimationStyle(R.style.AppTheme);
            push_popupwindow.showAtLocation(coordinatorLayout, Gravity.CENTER, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param currentPage This is the first paramter to addBottomDots method
     */
    public void addBottomDots(int currentPage) {
        if (array1 != null && array1.length() > 0 & !array1.equals(null)) {
            dots = new TextView[array1.length()];


            int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

            dotsLayout.removeAllViews();

            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(colorsInactive[currentPage]);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive[currentPage]);
        } else {
            System.out.println(">>>>");
            //dots = new TextView[array1.length()];
        }

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position > 0) {
              /*  btnSkip.setTextColor(Color.BLACK);
                btnNext.setTextColor(Color.BLACK);




                btnSkip.setTextColor(Color.GRAY);
                btnNext.setTextColor(Color.GRAY);*/
            }
            // changing the next button text 'NEXT' / 'GOT IT'
          /*  if (position == array1.length() - 1) {
                // last page. make button text to GOT IT
              //  btnNext.setText(getString(R.string.S));
                //btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
               // btnNext.setText(getString(R.string.next));
               // btnSkip.setVisibility(View.VISIBLE);
            }*/
        }

        /**
         * @param arg0
         * @param arg1
         * @param arg2
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        /**
         * @param arg0
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        call_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* System.out.println(getIntent().getIntExtra("current_but_id", 0));
                switch (getIntent().getIntExtra("current_but_id", 0)){
                    case 0:*/
                try {
                    if (branch_id.length == 0) {
                        new DownloadData().execute();
                    }
                    System.out.println("current branch id = " + ut);
                    for (int h = 0; h < branch_id.length; h++) {
                        System.out.println("branch_id : " + branch_id[h] + " => phone : " + branch_phone_number[h] + " => tollfree : " + branch_tollfree_phone_number[h]);
                    }

                    for (int h = 0; h < branch_id.length; h++) {
                        if (branch_id[h].equals(ut)) {
                            tolfree = branch_tollfree_phone_number[h];//or branch_phone_number[h]
                        }
                    }
                    poptext.setText("Do you want to make a call to " + tolfree);
                    displayCallingPopup();

                    call_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                    /*String tolfree = zhm.get(0).getToll_free().toString();*/

                            System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>" + tolfree);
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + tolfree));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Main2Activity.this.startActivity(intent);
                            mPopupWindowCall.dismiss();
                        }
                    });

                    //   about.setBackgroundResource(R.color.colorPrimary);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                       /* break;
                    case 1:
                        try {

                            displayCallingPopup();

                            call_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String tolfree1=zhm.get(0).getToll_free().toString();
                                    System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>"+tolfree1);
                                    Intent intent1 = new Intent(Intent.ACTION_DIAL);
                                    intent1.setData(Uri.parse("tel:"+tolfree1));
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Main2Activity.this.startActivity(intent1);
                                    mPopupWindowCall.dismiss();
                                }
                            });



                            //  service.setBackgroundResource(R.color.colorPrimary);//(getResources().getDrawable(R.drawable.selctbtn));

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {


                            displayCallingPopup();

                            call_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    String tolfree2=zhm.get(0).getToll_free().toString();
                                    System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>"+tolfree2);
                                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
                                    intent2.setData(Uri.parse("tel:"+tolfree2));
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Main2Activity.this.startActivity(intent2);
                                    mPopupWindowCall.dismiss();


                                }
                            });


                            // location.setBackgroundResource(R.color.colorPrimary);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }*/


            }
        });




/*        RequestQueue queue1 = Volley.newRequestQueue(Main2Activity.this);
        String url1 = "http://zulekhahospitals.com/mobileapp/branch.php?branch_id="+ut;

        StringRequest stringRequest1 = new StringRequest
                (Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //  tv.setText("Response is: "+ response);

                        System.out.println("++++++++++++++RESPONSE+++++++++++++++banner    :" + response);


                        try {
                            progress_bar_slider.setVisibility(View.VISIBLE);
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                DefaultSliderView defaultSliderView = new DefaultSliderView(Main2Activity.this);
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String id = jsonobject.getString("id");
                                String banner_id = jsonobject.getString("branch_id");
                                String banner = jsonobject.getString("branch_image");
                                defaultSliderView.image(banner);
                                sliderShow.addSlider(defaultSliderView);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progress_bar_slider.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //tv.setText("That didn't work!");

                    }
                });

        queue1.add(stringRequest1);


        sliderShow.setPresetTransformer(SliderLayout.Transformer.Stack);//accordation
        sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        sliderShow.setDuration(4000);*/


        try {
            SharedPreferences up_pref = Main2Activity.this.getSharedPreferences("updationPref", MODE_PRIVATE);
            String up_status = up_pref.getString("updation_available", null);
            if (up_status != null || !up_status.isEmpty()) {

                if (up_status.contentEquals("yes")) {
                    System.out.println("up_status : " + up_status);
                    /*Toast.makeText(Main2Activity.this,"New Update is Available",Toast.LENGTH_LONG).show();
                    displayPopup();*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void requestPermissions(String updateDeviceStats) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Snackbar.with(Main2Activity.this, null)
                        .type(Type.SUCCESS)

                        .message("Press Again to Exit from Zulekha !")
                        .duration(Duration.LONG)

                        .show();
            }
            back_pressed = System.currentTimeMillis();

           /* if (doubleBackToExitPressedOnce) {
                Snackbar.with(Main2Activity.this,null)
                        .type(Type.SUCCESS)

                        .message("Press Again for ,Exit from Zulekha !")
                        .duration(Duration.LONG)

                        .show();
                finish();
 final AlertDialog.Builder builder=new AlertDialog.Builder(this);

                builder.setMessage("Exit from Zulekha ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
            this.doubleBackToExitPressedOnce = true;
            Snackbar.with(Main2Activity.this,null)
                    .type(Type.SUCCESS)

                    .message("Press Again to Exit from Zulekha !")
                    .duration(Duration.LONG)

                    .show();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       /* int id = item.getItemId();

        if (id == R.id.navcont_cntus) {
            Intent i1 = new Intent(getApplicationContext(),ContactFragment.class);
            // i1.putExtra("current_but_id",0);
            i1.putExtra("br_id",ut);
            startActivity(i1);
            // Handle the camera action
        } else if (id == R.id.nav_abt) {
            Intent i1 = new Intent(getApplicationContext(),About_Fragment.class);
            // i1.putExtra("current_but_id",0);
            i1.putExtra("br_id",ut);
            startActivity(i1);

        } else if (id == R.id.nav_portal) {
            Intent pr = new Intent(getApplicationContext(),CareerFragment.class);
          // pr.putExtra("cre",zhm.get(0).getCme());
            startActivity(pr);
        } else if (id == R.id.nav_edulft) {

            Intent i1 = new Intent(getApplicationContext(),Educational_leaflet_Fragment.class);
            // i1.putExtra("current_but_id",0);
            startActivity(i1);
        } else if (id == R.id.nav_vlab) {
            Intent ic = new Intent(getApplicationContext(),LabReport.class);
            ic.putExtra("lab",zhm.get(0).getReport());
            startActivity(ic);

        } else if (id == R.id.nav_cmplnts) {
            Intent ip = new Intent(getApplicationContext(),ComplaintFragment.class);
            //  i3.putExtra("current_but_id",1);
            startActivity(ip);
        }

        else if (id == R.id.nav_ip) {
            Intent ip = new Intent(getApplicationContext(),Insurance_Fragment.class);
            //  i3.putExtra("current_but_id",1);
            startActivity(ip);
        }  else if (id == R.id.nav_rate) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zulekhahospitals.zulekhaapp"));
            startActivity(i);
        }
        else if(id==R.id.nav_cme){
            Intent ic = new Intent(getApplicationContext(),CmeFragment.class);
            ic.putExtra("cme",zhm.get(0).getCme());
            startActivity(ic);
        }
        else if (id == R.id.nav_logout) {
            //Toast.makeText(getApplicationContext(),"test5",Toast.LENGTH_SHORT).show();

//            SharedPreferences settings = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//            settings.edit().clear().commit();
            if(navigationView.getMenu().getItem(9).getTitle()=="Log Out") {
                logout();
            }
            else {


                Intent intlog = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intlog);
            }


                *//*Toast.makeText(getApplicationContext(),"test5",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                preferences.edit().clear().commit();


                Intent intlog=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intlog);*//*
            //finish();

               *//* SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();*//*

        }
else if(id==R.id.nav_branch){

            SharedPreferences preferences1= getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
         branch_name = preferences1.getString("barnch", null);
            preferences1.edit().clear().commit();
           // preferences.edit().clear().commit();
            //preferences1.edit().clear().commit();
           // progress.setVisibility(View.GONE);
            Intent  i = new Intent(Main2Activity.this,Screen_Selection_Fragment.class);
            finish();
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    public boolean ConnectionCheck() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void logout() {
        progress.setVisibility(View.VISIBLE);
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(Main2Activity.this);

        progress.setVisibility(View.GONE);


        if (ConnectionCheck()) {
            //  Toast.makeText(getApplicationContext(), "Social Media", Toast.LENGTH_SHORT).show();\


            System.out.println("***********************************************");
            System.out.println("user_id in logout : " + userid);
            System.out.println("***********************************************");


            HttpHandler h = new HttpHandler();
            String s = h.makeServiceCall(BaseUrl+"android/logout.php?id="+userid);
            System.out.println("***********************************************");
            System.out.println("userid in logout : " + userid);
            System.out.println("http makeservice call response : " + s);
            System.out.println("***********************************************");
            progress.setVisibility(View.GONE);
            Intent ib = new Intent(Main2Activity.this, LoginActivity.class);
            ib.putExtra("str_branch", str_branch);
            ib.putExtra("from", "");
            preferencesd.edit().clear().commit();

            startActivity(ib);
            finish();

        } else {

            Snackbar.with(Main2Activity.this, null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();
            progress.setVisibility(View.GONE);

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

            case R.id.imageView1:

                switch (getIntent().getIntExtra("current_but_id", 0)) {
                    case 0:
                        if (DetectConnection
                                .checkInternetConnection(Main2Activity.this)) {
                            //Toast.makeText(getActivity(),
                            //	"You have Internet Connection", Toast.LENGTH_LONG)

                            Intent i1 = new Intent(Main2Activity.this, Department_Fragment.class);
                            i1.putExtra("br_id", ut);
                            // i1.putExtra("current_but_id",0);
                            startActivity(i1);
                        } else {


                            Snackbar.with(Main2Activity.this, null)
                                    .type(Type.ERROR)
                                    .message("No Internet Connection!")
                                    .duration(Duration.LONG)

                                    .show();

                        }
                        //   about.setBackgroundResource(R.color.colorPrimary);
                        // String wif=zhm.get(0).getWifi().toString();
                        //   System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>"+wif);

                        //  Intent i7 = new Intent(getApplicationContext(),WifiFragment.class);
                        //  i3.putExtra("current_but_id",1);

                        //startActivity(i7);
                        break;
                    case 1:
                        if (DetectConnection
                                .checkInternetConnection(Main2Activity.this)) {
                            //Toast.makeText(getActivity(),
                            //	"You have Internet Connection", Toast.LENGTH_LONG)

                            Intent i2 = new Intent(Main2Activity.this, Department_Fragment.class);
                            i2.putExtra("br_id", ut);
                            // i1.putExtra("current_but_id",0);
                            startActivity(i2);
                            //  service.setBackgroundResource(R.color.colorPrimary);//(getResources().getDrawable(R.drawable.selctbtn));

                        } else {


                            Snackbar.with(Main2Activity.this, null)
                                    .type(Type.ERROR)
                                    .message("No Internet Connection!")
                                    .duration(Duration.LONG)

                                    .show();

                        }


                        break;
                    case 2:
                        if (DetectConnection
                                .checkInternetConnection(Main2Activity.this)) {
                            //Toast.makeText(getActivity(),
                            //	"You have Internet Connection", Toast.LENGTH_LONG)
                            Intent i3 = new Intent(Main2Activity.this, Department_Fragment.class);
                            i3.putExtra("br_id", ut);
                            // i1.putExtra("current_but_id",0);
                            startActivity(i3);
                        } else {


                            Snackbar.with(Main2Activity.this, null)
                                    .type(Type.ERROR)
                                    .message("No Internet Connection!")
                                    .duration(Duration.LONG)

                                    .show();

                        }


                        break;

                    default:
                        break;


                }
                break;


            case R.id.imageView2:

                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {

                    Intent i2 = new Intent(Main2Activity.this, ZH_Appointment_Fragment.class);
                    i2.putExtra("br_id", ut);
                    startActivity(i2);
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }

                break;
            case R.id.imageView3:
                if (DetectConnection
                        .checkInternetConnection(Main2Activity.this)) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)


                    Intent i3 = new Intent(Main2Activity.this, LiveChatFragment.class);
                    //  i3.putExtra("current_but_id",1);
                    i3.putExtra("link", menu_chat);
                    startActivity(i3);

                    // goToUrl("http://83.111.177.134/Code/webchatLogin.php");
                } else {


                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }

                break;

            case R.id.imageView6:


                SharedPreferences myPrefs1 = this.getSharedPreferences("MyPref", MODE_PRIVATE);
                Reg_id = myPrefs1.getString("user_id", null);
                if (Reg_id != null && !Reg_id.isEmpty()) {
                    Intent i = new Intent(Main2Activity.this, MyAccount.class);
                    i.putExtra("user_id", Reg_id);
                    startActivity(i);
                } else {
                    Intent i9 = new Intent(Main2Activity.this, LoginActivity.class);
                    i9.putExtra("current_but_id", 0);
                    i9.putExtra("str_branch", "dubai");
                    i9.putExtra("from", "");
                    i9.putExtra("str_branch", str_branch);
                    finish();
                    startActivity(i9);
                }


                break;
            case R.id.imageView7:


                System.out.println(getIntent().getIntExtra("current_but_id", 0));
                switch (getIntent().getIntExtra("current_but_id", 0)) {
                    case 0:
                        try {

                            if (DetectConnection
                                    .checkInternetConnection(Main2Activity.this)) {

                                SharedPreferences myPrefs = this.getSharedPreferences("MyPref", MODE_PRIVATE);
                                Reg_id = myPrefs.getString("user_id", null);

                                System.out.println("<<<<<<<<<sharedname>>>>" + Reg_id);
                                if (Reg_id != null && !Reg_id.isEmpty()) {
                                    //Toast.makeText(getApplicationContext(), Reg_id, Toast.LENGTH_SHORT).show();


                                    String wif = menu_wifi;
                                    System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>" + wif);
                                    Intent i7 = new Intent(Main2Activity.this, WifiFragment.class);
                                    //  i3.putExtra("current_but_id",1);
                                    i7.putExtra("wif_id", wif);

                                    startActivity(i7);
                                } else {
                                    Intent i9 = new Intent(Main2Activity.this, LoginActivity.class);
                                    i9.putExtra("current_but_id", 0);
                                    i9.putExtra("str_branch", "dubai");
                                    i9.putExtra("from", "");
                                    i9.putExtra("str_branch", str_branch);
                                    finish();
                                    startActivity(i9);
                                }
                            } else {


                                Snackbar.with(Main2Activity.this, null)
                                        .type(Type.ERROR)

                                        .message("No Internet Connection!")
                                        .duration(Duration.LONG)

                                        .show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //   about.setBackgroundResource(R.color.colorPrimary);

                        break;


                    default:
                        break;


                }
                break;


            case R.id.imageView9:


                SharedPreferences myPrefs2 = this.getSharedPreferences("MyPref", MODE_PRIVATE);
                Reg_id = myPrefs2.getString("user_id", null);
                if (Reg_id != null && !Reg_id.isEmpty()) {
                    /*Intent i9 = new Intent(getApplicationContext(),LoginActivityFeedBack.class);
                    //  i3.putExtra("current_but_id",1);
                    System.out.println("branchname........"+ut);
                    i9.putExtra("branch_name",ut);
                   
                    startActivity(i9);*/

                    Intent i9 = new Intent(Main2Activity.this, LoginActivityFeedBack.class);
                    i9.putExtra("branch_id", ut);
                    i9.putExtra("str_branch", str_branch);

                    startActivity(i9);
                } else {
                    Intent ir = new Intent(Main2Activity.this, LoginActivity.class);
                    ir.putExtra("from", "feedbacklogin");
                    ir.putExtra("str_branch", str_branch);
                    finish();
                    startActivity(ir);
                }


                break;


        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
     message=  intent.getStringExtra("message");
        System.out.println("-------------------222222222222222-------------------------------"+message);

        if(message!=null){
            new PromptDialog(this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText("")
                    .setContentText(message)
                    .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
            // dialog.findViewById(R.id.confirm_button).setBackgroundColor(Color.parseColor("#368aba"));
        //    display_patient_id();
            System.out.println("-------------------message111111111111-------------------------------"+message);

        }
    }

    private class DownloadData extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.GONE);

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


                HttpHandler h = new HttpHandler();
                String s = h.makeServiceCall(ZHconstansts.URL+"contact.php?key=100");
                //String s = h.makeServiceCall("http://zayedprize.org.ae/gogreen/json/client-agent.php");

                result = s;


                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading connection:", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {
            // btnSignIn.setEnabled(false);
            // edt.setEnabled(false);
            // pdt.setEnabled(false);
            //   pd.dismiss();
            try {
                progress.setVisibility(ProgressBar.GONE);
                String sam = result.trim();
                System.out.println(">>>>>>>Testttttttttttttttttttttttttt>>>>>>>>" + result);
                System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;

                JSONObject jsonObj = new JSONObject(result);

                String status = jsonObj.getString("status");
                if (status.equalsIgnoreCase("true")) {


                    JSONArray mArray;
                    zhm = new ArrayList<Zulekha_Highlight_Model>();
                    try {
                        mArray = jsonObj.getJSONArray("data");

                        branch_id = new String[mArray.length()];
                        branch_phone_number = new String[mArray.length()];
                        branch_tollfree_phone_number = new String[mArray.length()];
                        branch_latitude = new String[mArray.length()];
                        branch_longitude = new String[mArray.length()];

                        for (int i = 0; i < mArray.length(); i++) {
                            zh = new Zulekha_Highlight_Model();
                            JSONObject mJsonObject = mArray.getJSONObject(i);
                            //  Log.d("OutPut", mJsonObject.getString("image"));

                            // event_id,,highlight_image;

                            id = mJsonObject.getString("id");
                            center = mJsonObject.getString("branch");
                            pobox = mJsonObject.getString("pobox");
                            appointment_s = mJsonObject.getString("appointment");
                            phone = mJsonObject.getString("phone");

                            System.out.println("phone : " + phone);
                            fax = mJsonObject.getString("fax");
                            //  appointment= mJsonObject.getString("appointment");
                            information = mJsonObject.getString("information");

                            resume = mJsonObject.getString("send_resume");
                            near = mJsonObject.getString("near");
                            latitude = mJsonObject.getString("latitude");
                            longitude = mJsonObject.getString("longitude");
                            adress = mJsonObject.getString("adress");

                            toll_free = mJsonObject.getString("toll_free");

                            branch_id[i] = id;
                            branch_phone_number[i] = phone;
                            branch_tollfree_phone_number[i] = toll_free;
                            branch_latitude[i] = latitude;
                            branch_longitude[i] = longitude;

								/*report = mJsonObject.getString("report");
								cme = mJsonObject.getString("cme");
								wifi = mJsonObject.getString("wifi");*/
                            //  String o = mJsonObject.getString("specialty_other");
                            zh.setId(id);
                            zh.setCenter(center);
                            zh.setPobox(pobox);
                            zh.setAppointment_s(appointment_s);
                            zh.setPhone(phone);
                            zh.setFax(fax);
                            zh.setInformation(information);
                            zh.setResume(resume);
                            zh.setNear(near);
                            zh.setLatitude(latitude);
                            zh.setLongitude(longitude);
                            zh.setAdress(adress);
                            zh.setToll_free(toll_free);
                            zh.setReport(report);
                            zh.setCme(cme);
                            zh.setWifi(wifi);


                            System.out.println("<<<galry_img>>>>" + id);

                            //   System.out.println("<<<oo>>>>" + o);
                            //   onComplete();

                          /*  cnlist = new ArrayList<String>();*/
                            zhm.add(zh);
                            System.out.println("<<<oo>>>>" + toll_free_num);
/*
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setAdapter(adapter);*/


                            //adapter = new Event_adapter(upm, getActivity());
                            //  recyclerview.setAdapter(adapter);
                            //  Toast.makeText(getApplicationContext(), "Login Sucess", Toast.LENGTH_SHORT).show();
                            //status=5;
                            //   Intent i1 = new Intent(Participated_Events_Activity.this, HomeActivity.class);
                            //  i1.putExtra("fulname", k);
                            //   i1.putExtra("spclty", o);
                            // startActivity(i1);
                        }
                    /*for(int i=0;i<zhm.size();i++)
                    {
                        toll_free_num= zhm.get(i).getToll_free();
                        System.out.println("<<<tollfreee>>>>" +toll_free);

                        poptext.setText("Do you want to make a call to "+toll_free);

                    }*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

               /* ArrayAdapter<PastModel> adapter1 = new MyList();
                ListV.setAdapter(adapter1);
*/

                } else {
                    Snackbar.with(Main2Activity.this, null)
                            .type(Type.ERROR)

                            .message("No Data!")
                            .duration(Duration.LONG)

                            .show();
                    System.out.println("nulllllllllllllllllllllllllllllllll");
                }
           /* ListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Lm=pns.get(i).getEvent_year();
                    System.out.println("<<<oo>lllllllllllllllllllll>>>"+ Lm);
                    new DownloadData2().execute();
                }
            });*/


      /*          Main2Activity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        try{
                            insatalled_app_version=getCurrentVersionInfo();
                            VersionChecker versionChecker = new VersionChecker();
                            latestVersion = versionChecker.execute().get();

                            System.out.println("---------------------------------------------------------------------");
                            System.out.println("CURRENT VERSION : "+insatalled_app_version);
                            System.out.println("\nNEW VERSION : "+latestVersion);
                            System.out.println("---------------------------------------------------------------------");
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        finally{
                            if(!insatalled_app_version.contentEquals(latestVersion))
                            {
                                System.out.println("inside !insatalled_app_version.contentEquals(latestVersion)");
                                displayPopup();
                            }

                        }
                    }
                });*/

               /* Thread fetcher=new Thread(){
                    public void run(){

                        try{
                            insatalled_app_version=getCurrentVersionInfo();
                            VersionChecker versionChecker = new VersionChecker();
                            latestVersion = versionChecker.execute().get();
                             *//*new_app_version=Float.parseFloat(latestVersion);*//*
                            System.out.println("---------------------------------------------------------------------");
                            System.out.println("CURRENT VERSION : "+insatalled_app_version);
                            System.out.println("\nNEW VERSION : "+latestVersion);
                            System.out.println("---------------------------------------------------------------------");
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        finally{
                            if(!insatalled_app_version.contentEquals(latestVersion))
                            {
                                System.out.println("inside !insatalled_app_version.contentEquals(latestVersion)");
                                displayPopup();
                            }

                        }
                    }

                };
                fetcher.start();*/


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private String getCurrentVersionInfo() {
        String strVersion = "";

        PackageInfo packageInfo;
        try {
            packageInfo = Main2Activity.this
                    .getPackageManager()
                    .getPackageInfo(
                            Main2Activity.this.getPackageName(),
                            0
                    );
            strVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        System.out.println("strVersion : " + strVersion);
        return strVersion;
    }


    public class VersionChecker extends AsyncTask<String, String, String> {

        String newVersion;

        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=com.zulekhahospitals.zulekhaapp&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newVersion;
        }
    }

    public void displayPopup() {

        try {
            System.out.println("inside display popup");
            mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }
            mPopupWindow.setFocusable(true);
            mPopupWindow.setAnimationStyle(R.style.popupAnimation);


            mPopupWindow.showAtLocation(top_layout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void displayCallingPopup() {
        try {


            mPopupWindowCall = new PopupWindow(customViewCall, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindowCall.setElevation(5.0f);
            }

            mPopupWindowCall.setFocusable(true);
            mPopupWindowCall.setAnimationStyle(R.style.popupAnimation);
            mPopupWindowCall.showAtLocation(top_layout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public class FetchBanners extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar_slider.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            try {


                HttpHandler h = new HttpHandler();

                String jsonString = h.makeServiceCall(BaseUrl+"iphone/bannerslides.php?key=100"+"&user_id="+userid+"&device"+"android"+"&device_token="+refreshedToken);
                System.out.println("________jsonString_____________" + jsonString);

                //  String jsonString = h.makeServiceCall("http://zulekhahospitals.com/android/banner-slider.php?key=100");//("http://zulekhahospitals.com/mobileapp/branch.php?branch_id="+ut);
                if (jsonString != null) {

                    try {

                        JSONObject jsonobject = new JSONObject(jsonString);


                        String status = jsonobject.getString("status");
                        System.out.println("_________status_____________" + status);
                        if (status.equals("true")) {
                            JSONArray jsonArray = jsonobject.getJSONArray("data");
                            System.out.println("_________jsonArray_____________" + jsonArray);
                            asm = new ArrayList<Action_Services_Model>();
                            Banners = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                am = new Action_Services_Model();
                                String banner = jsonArray.getJSONObject(i).getString("banner");
                                String action = jsonArray.getJSONObject(i).getString("action");
                                System.out.println("_________action_____________" + action);
                                String phone = jsonArray.getJSONObject(i).getString("phone");
                                System.out.println("________phone_____________" + phone);
                                am.setBanner(banner);
                                am.setAction(action);
                                am.setValue(value);
                                asm.add(am);
                                Banners[i] = banner;

                            }

                                */
/*String id = jsonobject.getString("id");
                                String banner_id = jsonobject.getString("branch_id");*//*



                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (jsonString.equals("")) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                for (int i = 0; i < Banners.length; i++) {
                    defaultSliderView = new DefaultSliderView(Main2Activity.this);
                    defaultSliderView.image(Banners[i]);
                    sliderShow.addSlider(defaultSliderView);
                    System.out.println("Banners[" + i + "]=" + Banners[i]);
                    defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Log.d("MyActivity", "index selected:" + sliderShow.getCurrentPosition());
                            String act = asm.get(sliderShow.getCurrentPosition()).getAction().toString();
                            if (act.equalsIgnoreCase("call")) {
                                poptext.setText("Do you want to make a call to " + tolfree);
                                displayCallingPopup();

                                call_yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    */
/*String tolfree = zhm.get(0).getToll_free().toString();*//*


                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + asm.get(sliderShow.getCurrentPosition()).getValue().toString()));
                                        if (ActivityCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        startActivity(intent);
                                        mPopupWindowCall.dismiss();
                                        return;


                                    }
                                });
                            }
                            requestStoragePermission();

                   */
/* else if(act.equalsIgnoreCase("push")){
                        startActivity(new Intent(getActivity(), About_us.class));
                    }
                    else if(act.equalsIgnoreCase("loadweb")){
                        String url = asm.get(sliderLayout.getCurrentPosition()).getValue().toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }*//*

                            // Toast.makeText(getActivity(), "index selected:" + sliderLayout.getCurrentPosition(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            progress_bar_slider.setVisibility(View.GONE);
            sliderShow.setPresetTransformer(SliderLayout.Transformer.Stack);//accordation
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            //  sliderShow.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
            sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            */
/*sliderShow.setDuration(4000);*//*

        }
    }
*/

    class getLinks extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(ZHconstansts.URL+"menu3.php");
                // http://zulekhahospitals.com/android/menu3.php&key=100
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("key", "100");


                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    System.out.println("links - sb.toString() : " + sb.toString());
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                if (!result.isEmpty()) {
                    System.out.println("menu result : " + result);

                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                        menu_id = jsonObject2.getString("id");
                        menu_wifi = jsonObject2.getString("wifi");
                        menu_chat = jsonObject2.getString("chat");
                        menu_labreport = jsonObject2.getString("labreport");
                        menu_career = jsonObject2.getString("career");
                        menu_cme = jsonObject2.getString("cme");
                        menu_dubaiphone = jsonObject2.getString("dubaiphone");
                        menu_sharjahphone = jsonObject2.getString("sharjahphone");
                        menu_zmcphone = jsonObject2.getString("zmcphone");
                        version_from_server = jsonObject2.getString("latestVersion");
                        System.out.println("Menu Details : " + menu_id + "\n" + menu_wifi + "\n" + menu_chat + "\n" + menu_labreport + "\n" + menu_career + "\n" + menu_cme + "\n" + menu_dubaiphone + "\n" + menu_sharjahphone + "\n" + menu_zmcphone);


                    }
                }
                String current_app_version = getCurrentVersionInfo();
                System.out.println("current_app_version : " + current_app_version);
                System.out.println("version_from_server : " + version_from_server);
                if (!current_app_version.contentEquals(version_from_server)) {

                    displayPopup();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this, android.Manifest.permission.CALL_PHONE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.CALL_PHONE}, STORAGE_PERMISSION_CODE);
    }

/*
    class FetchImage extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        */
/**
         * @param strings
         * @return
         *//*

        @Override
        protected String doInBackground(String... strings) {
            // JSONArray array1;
            New_HttpHandler h = new New_HttpHandler();
            jsonString = h.makeServiceCall("http://www.zulekhahospitalsdemo.com.php56-4.phx1-2.websitetestlink.com/mobileapp/android/"+"zulekhabaner.php?user_id="+userid+"&device="+"android"+"&device_token="+refreshedToken);

         //orginal
          //  jsonString = h.makeServiceCall("http://zulekhahospitals.com/android/zulekhabaner.php?user_id="+userid+"&device"+"android"+"&device_token="+refreshedToken);
            System.out.println("_________jsonString11111111111_____________" +"http://www.zulekhahospitalsdemo.com.php56-4.phx1-2.websitetestlink.com/mobileapp/android/zulekhabaner.php?user_id="+userid+"&device"+"android"+"&device_token="+refreshedToken);

            return null;
        }


        */
/**
         * @param s
         *//*

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            slider_lay.setVisibility(View.VISIBLE);

            // making notification bar transparent
            changeStatusBarColor();
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
            if (jsonString != null && !jsonString.isEmpty() && !jsonString.equals("null")) {
                try {

                    JSONObject jsonobject = new JSONObject(jsonString);


                    String status = jsonobject.getString("status");

                    System.out.println("_________status_____________" + status);
                    // array1 = new JSONArray(jsonString);
                    asm = new ArrayList<Action_Services_Model>();
                    if (status.equals("true")) {
                        array1 = jsonobject.getJSONArray("data");
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject obj = array1.getJSONObject(i);
                            am = new Action_Services_Model();
                            // String banner = obj.getString("banner");

                            banner = obj.getString("banner");
                            action = obj.getString("action");
                            value = obj.getString("value");
                            category=obj.getString("type");
                            if(category.equalsIgnoreCase("image")){
                                am.setType(1);
                            }

                            if(category.equalsIgnoreCase("facebook")){
                                am.setType(2);
                            }
                            if(category.equalsIgnoreCase("twitter")){
                                am.setType(0);
                            }

                            am.setBanner(banner);
                            am.setAction(action);
                            am.setValue(value);
                            am.setCategory(category);
                            System.out.println("_________id_____________" + ids);
                            System.out.println("_________id_____________" + urls);
                            System.out.println("_________value_____________" + value);
                            System.out.println("_________category_____________" + category);
                            asm.add(am);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            recyclerview.setLayoutManager(layoutManager);
                            // recyclerview.setAdapter(adapter1);
                            adapter1 = new Banner_adapter(asm, getApplicationContext());
                            recyclerview.setAdapter(adapter1);
                            //slider_lay1.setVisibility(View.VISIBLE);
                            addBottomDots(0);
                        }

                    } else {
                        System.out.println("nodots");
                    }
                    // va = new New_ViewPagerAdapter(getApplicationContext(), imageURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               */
/* final int speedScroll = 150;
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        if(count < array1.length()){
                            recyclerview.scrollToPosition(++count);
                            handler.postDelayed(this,speedScroll);
                        }


                    }
                };*//*

               // userid+"&device="+"android"+"&device_token="+refreshedToken


                // handler.postDelayed(runnable,speedScroll);
                recyclerview.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {
                                System.out.println("URI in kkkkkkkkkkkkkkkkkk ADAPTER : " + position);
                                String act = asm.get(position).getAction();
                                System.out.println("URI in accccccccccccccccccccccccccc ADAPTER : " + act);
                                if (act.equalsIgnoreCase("call")) {
                                    final Dialog dialog = new Dialog(Main2Activity.this);
                                    dialog.setContentView(R.layout.custom_dialog);
                                    // Custom Android Allert Dialog Title
                                    dialog.setTitle("Are You Sure, Want to call ?");

                                    dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    LinearLayout dialogButtonCancel = (LinearLayout) dialog.findViewById(R.id.call_no);
                                    LinearLayout dialogButtonOk = (LinearLayout) dialog.findViewById(R.id.call_yes);
                                    // Click cancel to dismiss android custom dialog box
                                    dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    // Your android custom dialog ok action
                                    // Action for custom dialog ok button click
                                    dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>" + tolfree);
                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:" + asm.get(position).getValue()));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            Main2Activity.this.startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();



                                } else if (act.equalsIgnoreCase("facebook")) {
                                    String url="https://m.facebook.com/zulekhahospitals";
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                } else if (act.equalsIgnoreCase("twitter")) {
                                   // String url = asm.get(position).getValue().toString();
                                    String url="https://twitter.com/zulekhahospitals/status/"+asm.get(position).getValue();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                                else if (act.equalsIgnoreCase("twitter")) {
                                    // String url = asm.get(position).getValue().toString();
                                    String url1 =asm.get(position).getValue();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url1));
                                    startActivity(i);
                                }
                            }
                        })
                );
            }else {
                Snackbar.with(Main2Activity.this, null)
                        .type(Type.ERROR)
                        .message("Something went wrong")
                        .duration(Duration.LONG)

                        .show();
            }
        }


    }
*/
}