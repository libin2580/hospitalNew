package com.zulekhahospitals.zulekhaapp.appointment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;


/**
 * Created by Libin_Cybraum on 7/16/2016.
 */
public class ZH_Appointment_Fragment extends FragmentActivity {
    FrameLayout container;
    FragmentManager fragmentManager;
    String tag = "Appointment";
    Fragment fragment;
    View view;
    Button Reg, UnReg;

    public ZH_Appointment_Fragment() {

    }

    String sts;
    ImageView back;
    String k;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_layout);
     /*   SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("contact", getApplicationContext().MODE_WORLD_READABLE);
        sts = myPrefs.getString("sample", "DEFAULT VALUE");
        // Toast.makeText(getActivity(), myPrefs.getString("sample", "DEFAULT VALUE"), Toast.LENGTH_LONG).show();
//        sts=getArguments().getString("sts");
        System.out.println(",,,,,,,,,,,,status,,,,,,,,,,,,,,,," + sts);*/
        analytics = FirebaseAnalytics.getInstance(ZH_Appointment_Fragment.this);
        analytics.setCurrentScreen(ZH_Appointment_Fragment.this,ZH_Appointment_Fragment.this.getLocalClassName(), null /* class override */);

        Intent intent = getIntent();
        k = intent.getStringExtra("br_id");
        back = (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //  s=getSupportFragmentManager().findFragmentById(R.id.frame_container).getTag();
                System.out.println(",,,,,,,,,,,,statusssssssssssssssssssssssssssssssssssssssssssssssssssssss,,,,,,,,,,,,,,,," + s);


                finish();


                /*    AlertDialog.Builder builder = new AlertDialog.Builder(ZH_Appointment_Fragment.this);
                    builder.setTitle("Exit");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage("Are You Sure You Want To Exit The App?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //ZH_Appointment_Fragment.this.goBack();
                            finish();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Show location settings when the user acknowledges the alert dialog

                        }
                    });
                    Dialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();*/




            }
        });
        Reg = (Button) findViewById(R.id.fragment_register);

        UnReg = (Button) findViewById(R.id.fragment_unregister);
        Reg.setOnClickListener(new ClickListener());
        UnReg.setOnClickListener(new ClickListener());
//		displayView(0);
        Reg.performClick();


    }

    public void displayView(int position) {
        Fragment fragment = null;
        String tag = "";
        switch (position) {
            case 0:
                fragment = new ZH_Appointment_Register_Fragment();
                Reg.setTextColor(Color.parseColor("white"));
                UnReg.setTextColor(Color.parseColor("black"));
                 tag = "appointment";

                break;
            case 1:
                fragment = new ZH_Appointment_UnRegFragment();
                UnReg.setTextColor(Color.parseColor("white"));
                Reg.setTextColor(Color.parseColor("black"));
           tag = "unreg";

                break;

            default:
                break;
        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.frame_cont, fragment, tag);
            // titleStack.add(tag);
            transaction.addToBackStack(tag).commit();
            // titleView.setText(fragment.getTag());
        } else {
            Log.e("ImageActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }



    public static Button b;

    public class ClickListener implements View.OnClickListener {

        @SuppressLint("NewApi")
        @Override
        public void onClick(View arg0) {


            if (arg0 instanceof Button) {
                if (b != null) {
                    b.setBackgroundResource(R.color.ZulekhaWhite);
                }
                b = (Button) arg0;

                b.setBackgroundResource(R.color.colorAccent);
            }


            // TODO Auto-generated method stub
            if (arg0.getId() == R.id.fragment_register) {// for home

                displayView(0);
            } else if (arg0.getId() == R.id.fragment_unregister) {

                displayView(1);


            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

finish();

          /*  AlertDialog.Builder builder = new AlertDialog.Builder(ZH_Appointment_Fragment.this);
            builder.setTitle("Exit");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Are You Sure You Want Close Appointment?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    //ZH_Appointment_Fragment.this.goBack();
                    finish();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog

                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();*/



    }

}
