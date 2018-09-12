
package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.MainActivity;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.login.NewReg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * 
 * @author libin@meridian
 */
public class ComplaintFragment extends Activity{
	boolean edittexterror=false;
	Button submit;
	ProgressBar progress;
	EditText name, email, complaint;
	MainActivity act = null;
String REGISTER_URL="http://zulekhahospitals.com/json.php?fid=111";
	private void clearFields() {

		name.setText("");
		email.setText("");
		complaint.setText("");
	}

	public ComplaintFragment() {
		// TODO Auto-generated constructor stub
	}
String name_,email_,descr_;
	ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_complaint);
		analytics = FirebaseAnalytics.getInstance(ComplaintFragment.this);
		analytics.setCurrentScreen(ComplaintFragment.this,ComplaintFragment.this.getLocalClassName(), null /* class override */);

		// act = (ImageActivity) getActivity();
		name = (EditText) findViewById(R.id.name_comp);
		email = (EditText) findViewById(R.id.mail_comp);
		complaint = (EditText) findViewById(R.id.descr_comp);
		progress = (ProgressBar)findViewById(R.id.progress);
		name.requestFocus();
		back= (ImageView) findViewById(R.id.back_image);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				ComplaintFragment.this.goBack();
			}
		});
		submit = (Button) findViewById(R.id.submit_sugg);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			 name_ = name.getText().toString();
			 email_ = email.getText().toString();
			 descr_ = complaint.getText().toString();

				edittexterror = false;
				 if (name.getText().toString().isEmpty()) {
					name.setError("Enter Name");
					edittexterror = true;
				}else
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
					email.setError("Invalid Email");
					edittexterror = true;
				}
				else if (email.getText().toString().isEmpty()) {
					email.setError("Enter Email Id");
					edittexterror = true;
				}





				else if (complaint.getText().toString().isEmpty()) {
					complaint.setError("Enter comments");
					edittexterror = true;
				}


				else if (email_.trim().matches("") || name_.matches("") ||descr_.trim().matches("")  ) {

					final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
					alertDialog.setTitle("Alert");
					// alertDialog.setIcon(R.drawable.warning_blue);
					alertDialog.setMessage("Empty Fields");

					alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();


						}
					});
					alertDialog.show();
					Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
					//  nbutton.setTextColor(getResources().getColor(R.color.Orange));

				}



				if(edittexterror==false)
				{
					new SentToServer().execute();
/*
					NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
					boolean i = networkCheckingClass.ckeckinternet();
					if (i)
					{

						progress.setVisibility(View.VISIBLE);
						StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
								new Response.Listener<String>() {
									@Override
									public void onResponse(final String response) {


										// JSONObject jsonObj = null;
										//  jsonObj = new JSONObject(response);
										System.out.println("responseeeee"+response);
										// statusd = jsonObj.getString("status");

										*/
/*Toast toast= Toast.makeText(getApplicationContext(),
												"You have successfully Submitted", Toast.LENGTH_SHORT);*//*

										name.setText("");
										email.setText("");
										complaint.setText("");
										Snackbar.with(ComplaintFragment.this,null)
												.type(Type.SUCCESS)
												.message("Successfully Submitted")
												.duration(Duration.LONG)

												.show();

										new Handler().postDelayed(new Runnable() {



											@Override
											public void run() {
												// This method will be executed once the timer is over
												// Start your app main activity


												// close this activity
												finish();
											}
										}, 2000);
  progress.setVisibility(View.GONE);
									}
								},
								new Response.ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError error) {
										Snackbar.with(ComplaintFragment.this,null)
												.type(Type.ERROR)
												.message(error.toString())
												.duration(Duration.LONG)

												.show();

										*/
/*Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();*//*

									}
								}) {
							@Override
							protected Map<String, String> getParams() throws AuthFailureError {
								Map<String, String> params = new HashMap<String, String>();
								http://meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass


								params.put("name", name_);
								params.put("mailid",email_);
								params.put("suggestion",descr_);


								return params;
							}

						};

						RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
						int socketTimeout = 30000;//30 seconds - change to what you want
						RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
						stringRequest.setRetryPolicy(policy);
						requestQueue.add(stringRequest);
					} else {
						*/
/*Toast toast= Toast.makeText(getApplicationContext(),
								"Oops No Internet Connection", Toast.LENGTH_SHORT);*//*

						Snackbar.with(ComplaintFragment.this,null)
								.type(Type.ERROR)
								.message("No internet connection")
								.duration(Duration.LONG)

								.show();

					}
*/


				}

			}
		});





	}
	class SentToServer extends AsyncTask<String,String,String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... strings) {
			try {





				URL url = new URL(BaseUrl+"android/suggestions.php");



				JSONObject postDataParams=new JSONObject();
				postDataParams.put("key","100");
				postDataParams.put("name",name_);
				postDataParams.put("mailid",email_);

				postDataParams.put("suggestion",descr_);

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

			}catch (Exception e){
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
try {
	System.out.println("result : "+result);
	JSONArray jsonArray = new JSONArray(result);

	if(jsonArray.length()!=0){
		JSONObject jsonObject=jsonArray.getJSONObject(0);
		String status=jsonObject.get("status").toString();
		if(status.equalsIgnoreCase("true")){
			name.setText("");
			email.setText("");
			complaint.setText("");
			Snackbar.with(ComplaintFragment.this,null)
					.type(Type.SUCCESS)
					.message("Successfully Submitted")
					.duration(Duration.LONG)

					.show();

			new Handler().postDelayed(new Runnable() {



				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity


					// close this activity
					finish();
				}
			}, 2000);
		}
		else {
			Snackbar.with(ComplaintFragment.this,null)
					.type(Type.ERROR)
					.message("Failed")
					.duration(Duration.LONG)

					.show();
		}
	}
}catch (Exception e){
	e.printStackTrace();
}

			progress.setVisibility(View.GONE);
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
	private void goBack() {
		super.onBackPressed();
	}
}
