package com.zulekhahospitals.zulekhaapp.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.newintro.NetworkCheckingClass;

import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;

/**
 * Created by libin on 11/3/2016.
 */
public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public EditText editText;
    ProgressBar progress;
    TextView status_text;
     String eml;
    String status;


    public CustomAlertDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customalert);
        editText= (EditText) findViewById(R.id.alert_email);
        progress = (ProgressBar)findViewById(R.id.progress_bars);
        status_text=(TextView)findViewById(R.id.status_text);
        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        yes = (Button) findViewById(R.id.btn_ok);
        no = (Button) findViewById(R.id.btn_cancel);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_ok:
               eml = editText.getText().toString();
                if (eml.matches("") )
                {
                   /* Toast.makeText(c,"Empty Field", Toast.LENGTH_SHORT).show();*/
                    InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    status_text.setText("E-mail field can't be empty");
                    status_text.setVisibility(View.VISIBLE);
                    /*Snackbar.with(c,null)
                            .type(Type.ERROR)
                            .message("E-mail field can't be empty")
                            .duration(Duration.LONG)

                            .show();*/

                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(eml.trim()).matches()){
                    /*Toast.makeText(c,"Invalied E-mail", Toast.LENGTH_SHORT).show();*/
                    InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    status_text.setText("Invalied E-mail");
                    status_text.setVisibility(View.VISIBLE);
                    /*Snackbar.with(c,null)
                            .type(Type.ERROR)
                            .message("Invalied E-mail")
                            .duration(Duration.LONG)

                            .show();*/

            }
                else {
                    InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(c);
                    boolean i = networkCheckingClass.ckeckinternet();



                    if (i==true)
                    {
                 new SendPostRequest().execute();
                    } else {
                        /*final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Oops Your Connection Seems Off..");

                        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "yes", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();


                            }
                        });
                        alertDialog.show();*/
                        InputMethodManager imme = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imme.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        status_text.setText("No Internet Connection!");
                        status_text.setVisibility(View.VISIBLE);
                        /*Snackbar.with(c,null)
                                .type(Type.ERROR)
                                .message("No Internet Connection!")
                                .duration(Duration.LONG)

                                .show();*/

                    }


                }

                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }

    }
    private class SendPostRequest extends AsyncTask<String, Void, String> {

        public void onPreExecute(){
            progress.setVisibility(ProgressBar.VISIBLE);
            status_text.setVisibility(View.GONE);
        }

        public String doInBackground(String... arg0) {
            String jsonString="";
            try {
                System.out.println("eml : "+eml);
                URL url2 = new URL(BaseUrl+"android/forgot.php?email="+eml);

                HttpHandler h = new HttpHandler();
                 jsonString = h.makeServiceCall(url2.toString());


                if (jsonString!= null) {
                    System.out.println("inside jsonstring !=null");
                    System.out.println("jsonstring : "+jsonString);
                    if (jsonString.trim().contentEquals("\"success\"")) {
                        System.out.println("inside success");

                    }
                    else{
                        System.out.println("inside failed");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonString;
        }

        @Override
        public void onPostExecute(String result) {
            try {
                progress.setVisibility(ProgressBar.GONE);
                System.out.println("result : " + result);


                JSONObject jsonObj = new JSONObject(result);

                String status = jsonObj.getString("status");
                if (status.equalsIgnoreCase("true")) {


                /*Toast.makeText(c, "New password is sent to your email",
                        Toast.LENGTH_SHORT).show();
*/
              /*  Snackbar.with(EditProfile.this,null)
                        .type(Type.SUCCESS)
                        .message("Updation Success")
                        .duration(Duration.LONG)

                        .show();

*/
                    dismiss();
                    Snackbar.with(c, null)
                            .type(Type.SUCCESS)
                            .message("New password is sent to your email")
                            .duration(Duration.LONG)

                            .show();
                } else {
               /* Toast.makeText(getApplicationContext(), "Updation Failed",
                        Toast.LENGTH_SHORT).show();
*/
                /*Toast.makeText(c, "Failed",
                        Toast.LENGTH_SHORT).show();*/
                    status_text.setText("Not a registered E-mail id");
                    status_text.setVisibility(View.VISIBLE);
                /*Snackbar.with(c,null)
                        .type(Type.ERROR)
                        .message("Failed")
                        .duration(Duration.LONG)

                        .show();*/
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
}