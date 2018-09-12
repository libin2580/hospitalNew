package com.zulekhahospitals.zulekhaapp.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.Main2Activity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.insurance.InsurenceTracking;
import com.zulekhahospitals.zulekhaapp.permission.CheckAndRequestPermission;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 11/2/2016.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    Button login;
    TextView bsignup;
    TextView Reg,txtnew,gst;
    EditText edtusrnam, edtpass;
    String usernam, pass,named;
    ProgressDialog pd;
    TextView twtr,gpls,lnkdlin,frgt;
    ImageView fb;
    String insatalled_app_version="",latestVersion="";
    WebView wv;
    float newVersion = 0;
    // ArrayList<UserDetailsModel> arr_usrs = new ArrayList<>();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String eml,status;
//    String REGISTER_URL="http://app.zayedevents.com.php56-9.dfw3-2.websitetestlink.com/services/loginand.php";
LoginButton login_button;

    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private static final int RC_SIGN_IN = 007;

    String Reg_id;
    static ArrayList<Zulekha_Login_Model> zlm;
    Zulekha_Login_Model zl;
    ProgressBar progress;
    String str_branch;
    String user_id,fullname,username,age,email,phone,log_status,patient_type, nationality,gender,dob;
    ImageView fbl;
    String u;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    AccessToken accessToken;
    Profile profile;

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
        setContentView(R.layout.activity_login);
        analytics = FirebaseAnalytics.getInstance(LoginActivity.this);
        analytics.setCurrentScreen(LoginActivity.this,LoginActivity.this.getLocalClassName(), null /* class override */);

        progress = (ProgressBar)findViewById(R.id.progress_bar);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        System.out.println(getIntent().getIntExtra("current_but_id", 0));
        CheckAndRequestPermission cp=new CheckAndRequestPermission();
        str_branch=getIntent().getStringExtra("str_branch");
        System.out.println("branch inside LoginActivity : "+str_branch);
        cp.checkAndRequestPermissions(this);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        login_button=(LoginButton)findViewById(R.id.login_button);
        login_button.setReadPermissions("public_profile email ");
        login_button.registerCallback(callbackManager, callback);

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


       /* login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


        switch (getIntent().getIntExtra("current_but_id", 0)) {
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



        SharedPreferences myPrefs = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Reg_id = myPrefs.getString("user_id", null);

        System.out.println("<<<<<<<<<sharedname>>>>" + Reg_id);
        if (Reg_id != null && !Reg_id.isEmpty()){
            //Toast.makeText(getApplicationContext(), Reg_id, Toast.LENGTH_SHORT).show();


            Intent synin = new Intent(getApplicationContext(), Main2Activity.class);
          synin.putExtra("brn_id",u);
            synin.putExtra("str_branch", str_branch);
            startActivity(synin);
            finish();
        }





        edtusrnam = (EditText) findViewById(R.id.us_nam);
        Typeface myFont7 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
        edtusrnam.setTypeface(myFont7);
        //
        edtpass = (EditText) findViewById(R.id.pw_d);
        Typeface myFont1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
        edtpass.setTypeface(myFont1);











     TextView skip= (TextView) findViewById(R.id.txt_skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Main2Activity.class);
                i.putExtra("str_branch", str_branch);
                i.putExtra("brn_id",u);

                startActivity(i);
                finish();
            }
        });


      /*  progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });*/


        fb= (ImageView) findViewById(R.id.txt_fb);


        frgt= (TextView) findViewById(R.id.txt_forgot);
        Typeface myFont2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
        frgt.setTypeface(myFont2);
       // twtr= (TextView) findViewById(R.id.txt_twitter);
        //  gpls= (TextView) findViewById(R.id.txt_id_googl);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String chk= preferences.getString("user_id",null);
    /*    if(chk!=null)
        { Intent i = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(i);
            finish();
        }*/










        wv=new WebView(this);

        frgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CustomAlertDialog cc=new CustomAlertDialog(LoginActivity.this);
                cc.show();
            }
        });




        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*Intent i=new Intent(getApplicationContext(), MainActivityNw.class);
             Toast.makeText(getApplicationContext(),
              u, Toast.LENGTH_SHORT);
                i.putExtra("brn_id",u);
                startActivity(i);*/
                login_button.performClick();

            }
        });



        login = (Button) findViewById(R.id.btn_login);
        Typeface myFont3 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
        login.setTypeface(myFont3);

        bsignup = (Button) findViewById(R.id.txt_signup);
        Typeface myFont4 = Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSansLight.ttf");
        bsignup.setTypeface(myFont4);
        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DetectConnection
                        .checkInternetConnection(getApplicationContext())) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)
                    Intent i = new Intent(getApplicationContext(),NewReg.class);
                    startActivity(i);
                   // finish();
                } else {
                    Snackbar.with(LoginActivity.this,null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)
                            .show();
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DetectConnection
                        .checkInternetConnection(getApplicationContext())) {
                    //Toast.makeText(getActivity(),
                    //	"You have Internet Connection", Toast.LENGTH_LONG)
                    System.out.println("ussssssssssssssssssssss....i"+edtusrnam.getText().toString());
                    System.out.println("pddddddddddddd....i"+edtpass.getText().toString());
                  //  log();
                    //recycler_inflate();
                  //  log1();

                    usernam = edtusrnam.getText().toString();
                    pass = edtpass.getText().toString();
                    if(usernam.matches("") ||pass.matches("")||usernam.toString().isEmpty() ||pass.toString().isEmpty()) {

                        Snackbar.with(LoginActivity.this, null)
                                .type(Type.ERROR)
                                .message("Empty Fields")
                                .duration(Duration.LONG)

                                .show();
//            nbutton.setBackgroundColor(getResources().getColor(R.color.));



                    }
                   /* else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(usernam.trim()).matches()){

                    *//*Toast.makeText(getApplicationContext(),"Invalied E-mail",Toast.LENGTH_LONG).show();*//*
                        Snackbar.with(LoginActivity.this,null)
                                .type(Type.ERROR)
                                .message("Invalied E-mail")
                                .duration(Duration.LONG)

                                .show();

                    }*/
                    else if (pass.length()<8 || pass.length()>15) {


                        Snackbar.with(LoginActivity.this,null)
                                .type(Type.ERROR)
                                .message("Password must contain minimum 8 and maximum 15 character")
                                .duration(Duration.LONG)

                                .show();

                    }

                    else {
                        new SendPostRequest().execute();
                    }
                } else {
                    Snackbar.with(LoginActivity.this,null)
                            .type(Type.ERROR)
                            .message("No Internet Connection!")
                            .duration(Duration.LONG)

                            .show();

                }


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
            public void onCompleted(JSONObject object,GraphResponse response) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>> INSIDE OnCompleted >>>>>>>>>>>>>>>>>>>>>>");
                JSONObject json = response.getJSONObject();
                System.out.println("json : "+json);
                try {
                    if(json != null){
                        String text = "Name : "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                        System.out.println("text : "+text);

                        user_id="sociallogin";
                        fullname = json.getString("name");
                        username="";
                        gender = "sociallogin";
                        age="";
                        phone="";
                        email=json.getString("email");
                        nationality="sociallogin";
                        patient_type="sociallogin";

                        dob="";

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
            username="";
            gender = "sociallogin";
            age="";
            phone="";
            email=personemail;
            nationality="sociallogin";
            patient_type="sociallogin";

            dob="";

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

    private class SendPostRequest extends AsyncTask<String, Void, String> {

        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
        }

        public String doInBackground(String... arg0) {

            try {

                URL url = new URL(BaseUrl+"android/signin.php"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("username",usernam);
                postDataParams.put("email",usernam);
                postDataParams.put("password",pass);

                Log.e("params",postDataParams.toString());

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

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        public void onPostExecute(String result) {
            progress.setVisibility(ProgressBar.GONE);
         /*   Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();*/
          if(result.contentEquals("\"already registerd\"")){
                Snackbar.with(LoginActivity.this,null)
                        .type(Type.ERROR)
                        .message("already registerd")
                        .duration(Duration.LONG)

                        .show();
            }
            else if(result.equalsIgnoreCase("\"failed\"")){
                Snackbar.with(LoginActivity.this,null)
                        .type(Type.ERROR)
                        .message("Invalid Credentials")
                        .duration(Duration.LONG)

                        .show();
            }
            else {

               /* Snackbar.with(LoginActivity.this,null)
                        .type(Type.SUCCESS)
                        .message("Login Success")
                        .duration(Duration.LONG)

                        .show();*/
                JSONObject jsonObj = null;
                zlm = new ArrayList<Zulekha_Login_Model>();
                try {
                    // String user_id,fullname,username,photo,,,log_status;
                    zl = new Zulekha_Login_Model();


/*
                        Snackbar.with(LoginActivity.this,null)
                                .type(Type.ERROR)
                                .message("Invalid Username or Password")
                                .duration(Duration.LONG)

                                .show();*/



                    {progress.setVisibility(View.GONE);
                        System.out.println("result : "+result);
                        jsonObj = new JSONObject(result);

                        String status=jsonObj.getString("status");
                        if(status.equalsIgnoreCase("true")){

                            JSONObject jsonObjdata=jsonObj.getJSONObject("data");
                            user_id=jsonObjdata.getString("id");
                            fullname = jsonObjdata.getString("fullname");
                            username=jsonObjdata.getString("username");
                            gender = jsonObjdata.getString("gender");
                            age=jsonObjdata.getString("age");
                            phone=jsonObjdata.getString("phone");
                            email=jsonObjdata.getString("email");
                            nationality=jsonObjdata.getString("nationality");
                            patient_type=jsonObjdata.getString("patient_type");

                            dob=jsonObjdata.getString("dob");


                            zl.setUser_id(user_id);
                            zl.setFullname(fullname);
                            zl.setUsername(username);
                            zl.setAge(age);
                            zl.setPhone(phone);
                            zl.setEmail(email);
                            zl.setLog_status(status);
                            zl.setPatient_type(patient_type);
                            zl.setNationality(nationality);
                            zl.setDob(dob);

                            zlm.add(zl);
                            System.out.println("result" + result);

                            saveDetails(0);


                        }
                        else {
                            //invalied username and pass
                            InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            Snackbar.with(LoginActivity.this,null)
                                    .type(Type.ERROR)
                                    .message("Invalid email or password")
                                    .duration(Duration.LONG)
                                    .show();
                        }


//                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
               /* Intent i = new Intent(getApplicationContext(),LoginActivityRetailer.class);
                startActivity(i);*/




        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
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


    public void saveDetails(int from_socail){
        System.out.println("from_socail : "+from_socail);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user_id", user_id);
        editor.putString("fullname",fullname);
        editor.putString("username",username );
        editor.putString("gender",gender );
        editor.putString("age",age );
        editor.putString("phone",phone );
        editor.putString("email",email );
        editor.putString("nationality",nationality );
        editor.putString("patient_type",patient_type );
        editor.putString("password",pass );
        editor.putString("dob",dob );
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



            if (isfromfeedback.equalsIgnoreCase("feedbacklogin")) {
                System.out.println("inside if(isfromfeedback.equalsIgnoreCase(\"feedbacklogin\"))");
                Intent is = new Intent(getApplicationContext(), Main2Activity.class);
                is.putExtra("brn_id", u);
                /*is.putExtra("str_branch", str_branch);*/
                is.putExtra("from2", "feedbacklogin");
                startActivity(is);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                boolean Islogin = Boolean.parseBoolean("true");
                prefs.edit().putBoolean("Islogin", Islogin).commit();
                //   pd.dismiss();
                finish();

            }

            else {

                System.out.println("inside else case");
                Intent is = new Intent(getApplicationContext(), Main2Activity
                        .class);
                is.putExtra("brn_id", u);
        /*is.putExtra("str_branch", str_branch);*/
                startActivity(is);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
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

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class SendPostRequest1 {
    }
}