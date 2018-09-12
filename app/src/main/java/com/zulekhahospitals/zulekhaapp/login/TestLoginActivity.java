package com.zulekhahospitals.zulekhaapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TestLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    LoginButton login_button;

    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private static final int RC_SIGN_IN = 007;
    ImageView fb;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    AccessToken accessToken;
    Profile profile;
    String u;
    ProgressBar progress;
    String user_id,email,fullname,str_branch;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> INSIDE ONSUCCESS >>>>>>>>>>>>>>>>>>>>>>");
            accessToken = loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();
            RequestData();
        }

        @Override
        public void onCancel() {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> INSIDE ONCANCEL >>>>>>>>>>>>>>>>>>>>>>");
        }

        @Override
        public void onError(FacebookException e) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> INSIDE ONERROR >>>>>>>>>>>>>>>>>>>>>>");
            e.printStackTrace();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        fb = (ImageView) findViewById(R.id.txt_fb);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        progress = (ProgressBar)findViewById(R.id.progress_bar);
        //str_branch=getIntent().getStringExtra("str_branch");
        str_branch="1";
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        login_button=(LoginButton)findViewById(R.id.login_button);
        login_button.setReadPermissions("public_profile email ");
        login_button.registerCallback(callbackManager, callback);
        switch (0) {
            case 0:

                //   about.setBackgroundResource(R.color.colorPrimary);

                //toolbar.setTitle("ZH Dubai");

                u = "1";
                break;
            case 1:

                //  service.setBackgroundResource(R.color.colorPrimary);//(getResources().getDrawable(R.drawable.selctbtn));

                // toolbar.setTitle("ZH Sharjah");
                u = "2";
                break;
            case 2:
                // location.setBackgroundResource(R.color.colorPrimary);
                u = "33";
                //  toolbar.setTitle("ZMC");

                break;

            default:
                break;
        }

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                /*displayMessage(newProfile);*/
                /* System.out.println("newProfile.getName() : "+newProfile.getName());*/

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        /*login_button.setBackgroundResource(R.drawable.fb_icon);
        login_button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        login_button.setCompoundDrawablePadding(0);
        login_button.setPadding(0, 0, 0, 0);
        login_button.setText("");*/


        // new SendPostRequest1().execute();


        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();

        }

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestProfile()
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)

                .build();

        btnSignIn.setSize(SignInButton.SIZE_ICON_ONLY);
        btnSignIn.setScopes(gso.getScopeArray());


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(), MainActivityNw.class);
             Toast.makeText(getApplicationContext(),
              u, Toast.LENGTH_SHORT);
                i.putExtra("brn_id",u);
                startActivity(i);*/
                login_button.performClick();

            }
        });

    }

    private void signIn() {





        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            progress.setVisibility(ProgressBar.VISIBLE);
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    progress.setVisibility(ProgressBar.GONE);
                    handleSignInResult(googleSignInResult);
                }
            });
        }




        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void RequestData(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> INSIDE RequestData() >>>>>>>>>>>>>>>>>>>>>>");
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>> INSIDE OnCompleted >>>>>>>>>>>>>>>>>>>>>>");
                JSONObject json = response.getJSONObject();
                System.out.println("json : "+json);
                try {
                    if(json != null){


                        user_id="sociallogin";
                        if(json.has("email")) {
                            fullname = json.getString("name");
                           String text = "Name : "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                            System.out.println("text : "+text);
                        }
                       /* username="";
                        gender = "sociallogin";
                        age="";
                        phone="";*/
                       if(json.has("email")) {
                           email = json.getString("email");
                       }
                       /* nationality="sociallogin";
                        patient_type="sociallogin";

                        dob="";*/

                        saveDetails(1);




                        SharedPreferences preferences = getSharedPreferences("SocialPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("sociallogin","true");
                        editor.putString("google", "false");
                        editor.putString("facebook","true");
                        editor.commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void saveDetails(int from_socail){
        System.out.println("from_socail : "+from_socail);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", user_id);
        editor.putString("fullname",fullname);
        /*editor.putString("username",username );
        editor.putString("gender",gender );
        editor.putString("age",age );
        editor.putString("phone",phone );*/
        editor.putString("email",email );
       // editor.putString("nationality",nationality );
      //  editor.putString("patient_type",patient_type );
       // editor.putString("password",pass );
       // editor.putString("dob",dob );
        editor.putString("str_branch",str_branch);
        if(from_socail==1){
            SharedPreferences preferences3 = getSharedPreferences("SocialPref", MODE_PRIVATE);
            SharedPreferences.Editor editor3 = preferences3.edit();
            editor3.putString("sociallogin","true");
            editor3.commit();
        }
        else {
            SharedPreferences preferences3 = getSharedPreferences("SocialPref", MODE_PRIVATE);
            SharedPreferences.Editor editor3 = preferences3.edit();
            editor3.putString("sociallogin","false");
            editor3.commit();
        }



        editor.commit();
        try {
            String isfromfeedback = getIntent().getStringExtra("from");
            System.out.println("--------------------------------------------------");
            System.out.println("inside loginactivity");
            System.out.println("isfromfeedback : " + isfromfeedback);
            System.out.println("--------------------------------------------------");



         /*   if (isfromfeedback.equalsIgnoreCase("feedbacklogin")) {
                System.out.println("inside if(isfromfeedback.equalsIgnoreCase(\"feedbacklogin\"))");
                Intent is = new Intent(getApplicationContext(), Main2Activity.class);
                is.putExtra("brn_id", u);
                *//*is.putExtra("str_branch", str_branch);*//*
                is.putExtra("from2", "feedbacklogin");
                startActivity(is);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(TestLoginActivity.this);
                boolean Islogin = Boolean.parseBoolean("true");
                prefs.edit().putBoolean("Islogin", Islogin).commit();
                //   pd.dismiss();
                finish();

            }

            else {*/

                System.out.println("inside else case");
                Intent is = new Intent(getApplicationContext(), Main2Activity
                        .class);
                is.putExtra("brn_id", u);
                /*is.putExtra("str_branch", str_branch);*/
                startActivity(is);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(TestLoginActivity.this);
                boolean Islogin = Boolean.parseBoolean("true");
                prefs.edit().putBoolean("Islogin", Islogin).commit();
                //   pd.dismiss();
                finish();

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {


                            }
                        });

           // }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if(status.getStatusCode()==0){
                            /*textViewName.setText("");*/
                            mGoogleApiClient.clearDefaultAccountAndReconnect();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"something went wrong...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("", "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            // String personPhotoUrl = acct.getPhotoUrl().toString();
            String personemail = acct.getEmail();

            Log.e("", "Name: " + personName + ", email: " + email
                    + ", Image: " );




// G+

            /*
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);


*/

            user_id="sociallogin";
            fullname = personName;
           // username="";
          //  gender = "sociallogin";
          //  age="";
          //  phone="";
            email=personemail;
          //  nationality="sociallogin";
          //  patient_type="sociallogin";

         //   dob="";

            saveDetails(1);




            SharedPreferences preferences = getSharedPreferences("SocialPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("sociallogin","true");
            editor.putString("google", "true");
            editor.putString("facebook","false");
            editor.commit();



        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            SharedPreferences preferences = getSharedPreferences("SocialPref", MODE_PRIVATE);
            String status_google = preferences.getString("google", null);
            String status_facebook = preferences.getString("facebook", null);
            System.out.println("status_google : "+status_google);
            if (status_google.equalsIgnoreCase("true")) {


     /*   OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d("", "Got cached sign-in");
            GoogleSignInResult result = opr.get();

        } else {

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                }
            });
        }

        mGoogleApiClient.clearDefaultAccountAndReconnect();*/
                /* revokeAccess();*/

                /*mGoogleApiClient.disconnect();*/
                /*mGoogleApiClient.connect();*/
                /* mGoogleApiClient.clearDefaultAccountAndReconnect();*/
            }
            else  if (status_facebook.equalsIgnoreCase("true")){
                LoginManager.getInstance().logOut();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}