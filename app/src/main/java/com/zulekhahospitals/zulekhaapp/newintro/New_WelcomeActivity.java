package com.zulekhahospitals.zulekhaapp.newintro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.branches.Screen_Selection_Fragment;
import com.zulekhahospitals.zulekhaapp.login.NewReg;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class New_WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    /*private MyViewPagerAdapter myViewPagerAdapter;*/
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    private ProgressDialog pdlog;
    JSONArray array1;
    private New_ViewPagerAdapter va;
    private ArrayList imageURL;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
      /*  prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            if (DetectConnection
                    .checkInternetConnection(getApplicationContext())) {
                //Toast.makeText(getActivity(),
                //	"You have Internet Connection", Toast.LENGTH_LONG)

                launchHomeScreen();
                finish();
            }

                else
             {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Oops No Internet Connection \n Please Turn on Wifi or Mobile Data", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

                btnSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchHomeScreen();
                    }
                });
            }

        }
       */
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.new_activity_welcome);
        analytics = FirebaseAnalytics.getInstance(New_WelcomeActivity.this);
        analytics.setCurrentScreen(New_WelcomeActivity.this,New_WelcomeActivity.this.getLocalClassName(), null /* class override */);

        progress = (ProgressBar) findViewById(R.id.progress_bar);
        imageURL=new ArrayList();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });



        // layouts of all welcome sliders
        // add few more layouts if you want


        // adding bottom dots
        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {

            new FetchImage().execute();
        } else {
            /*Toast toast= Toast.makeText(getApplicationContext(),
                    "Oops No Internet Connection", Toast.LENGTH_SHORT);*/
            Snackbar.with(New_WelcomeActivity.this,null)
                    .type(Type.ERROR)
                    .message("No internet connection")
                    .duration(Duration.LONG)

                    .show();
            /*toast.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();*/
            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchHomeScreen();
                }
            });

        }


    }

    private void addBottomDots(int currentPage) {
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
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
       // prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(New_WelcomeActivity.this, Screen_Selection_Fragment.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == array1.length() - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

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
    }

    /**
     * View pager adapter
     */
   /* public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }*/

    class FetchImage extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            New_HttpHandler h=new New_HttpHandler();
            String jsonString=h.makeServiceCall(BaseUrl+"mobileapp/intro.php");
            if(jsonString!=null){
                try{
                    array1=new JSONArray(jsonString);

                    for(int i=0;i<array1.length();i++){
                        JSONObject obj=array1.getJSONObject(i);
                        ZayedModel zm=new ZayedModel();
                        zm.id=Integer.parseInt(obj.getString("id"));
                        zm.url=obj.getString("intro");

                        imageURL.add(zm);





                    }
                    va=new New_ViewPagerAdapter(getApplicationContext(),imageURL);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(ProgressBar.GONE);

            addBottomDots(0);//calling dot adding function

            viewPager.setAdapter(va);
            addBottomDots(0);

            // making notification bar transparent
            changeStatusBarColor();

      /*  myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);*/
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchHomeScreen();
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // checking for last page
                    // if last page home screen will be launched
                    int current = getItem(+1);
                    if (current < array1.length()) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {
                        launchHomeScreen();
                    }
                }
            });
        }
    }
}
