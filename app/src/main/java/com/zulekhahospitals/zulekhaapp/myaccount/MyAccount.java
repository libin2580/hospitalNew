package com.zulekhahospitals.zulekhaapp.myaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class MyAccount extends AppCompatActivity {
String user_id;
    ImageView back;
    ImageView myaccount_editprofile,myaccount_changepassword;
    String social_status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        back= (ImageView) findViewById(R.id.back_image);
        analytics = FirebaseAnalytics.getInstance(MyAccount.this);
        analytics.setCurrentScreen(MyAccount.this,MyAccount.this.getLocalClassName(), null /* class override */);

        myaccount_editprofile=(ImageView)findViewById(R.id.myaccount_editprofile);
        myaccount_changepassword=(ImageView)findViewById(R.id.myaccount_changepassword);

        SharedPreferences preferences = getSharedPreferences("SocialPref", MODE_PRIVATE);
        social_status=preferences.getString("sociallogin",null);
        System.out.println("social_status : "+social_status);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyAccount.this.goBack();
            }
        });
        user_id=getIntent().getStringExtra("user_id");
        System.out.println("userid : "+user_id);



        myaccount_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if (social_status.equalsIgnoreCase("true")) {
                        Snackbar.with(MyAccount.this, null)
                                .type(Type.WARNING)
                                .message("Not accessible since you are loggedin via socialnetwork")
                                .duration(Duration.LONG)

                                .show();
                    } else {
                        Intent i = new Intent(getApplicationContext(), ChangeAccountPassword.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                    }


            }
        });


        myaccount_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(social_status.equalsIgnoreCase("true")){
                    Snackbar.with(MyAccount.this,null)
                            .type(Type.WARNING)
                            .message("Not accessible since you are loggedin via socialnetwork")
                            .duration(Duration.LONG)

                            .show();
                }else {
                    Intent i = new Intent(getApplicationContext(), EditProfile.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                }


            }
        });


    }
    private void goBack() {
        super.onBackPressed();
    }
}
