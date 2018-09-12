/**
 * 
 */
package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.MapsActivityNew;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.Zulekha_Highlight_Model;
import com.zulekhahospitals.zulekhaapp.appointment.ZH_Appointment_Fragment;
import com.zulekhahospitals.zulekhaapp.departments.Department_Fragment;
import com.zulekhahospitals.zulekhaapp.httphandler.HttpHandler;
import com.zulekhahospitals.zulekhaapp.login.NewReg;
import com.zulekhahospitals.zulekhaapp.permission.CheckAndRequestPermission;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * 
 * @author libin@meridian
 */
public class ContactFragment extends Activity implements OnClickListener{
	ArrayList<String> cnlist;
	Spinner spinner;
	TextView text, bn;
LinearLayout webview_linear_layout;
	Button but1, but2, but3;
	ProgressBar progress;
	String result;
	TextView linear_pobox,linear_phone,linear_fax_no,linear_for_appointments,linear_information,linear_send_resumes,linear_near;
	String id,center,pobox,appointment_s,phone,fax,appointment,information,resume,near,latitude,longitude,adress,toll_free,report,cme,wifi;
	static ArrayList<Zulekha_Highlight_Model> zhm;
	Zulekha_Highlight_Model zh;
	Spinner spinner0, spinner1, spinner2;
	String ads,ph,lat,lng,res;
	/*WebView vv;*/
	LinearLayout call_yes,call_no,email_yes,email_no,buttons_linearlayout;
	String k;

	TextView poptext,poptextemail;
	RelativeLayout top_layout;
	private PopupWindow mPopupWindowCall,mPopupWindowEmail;
	ImageButton dep,appoint,util,more;
	ImageView back;
	View customViewCall,customViewEmail;
	CheckAndRequestPermission cp=new CheckAndRequestPermission();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_layout);
		top_layout=(RelativeLayout)findViewById(R.id.top_layout);
		progress = (ProgressBar) findViewById(R.id.progress_bar);
		analytics = FirebaseAnalytics.getInstance(ContactFragment.this);
		analytics.setCurrentScreen(ContactFragment.this,ContactFragment.this.getLocalClassName(), null /* class override */);

		/*vv= (WebView)  findViewById(R.id.vv);*/
		but1 = (Button) findViewById(R.id.call);
		dep= (ImageButton) findViewById(R.id.dep);
		appoint= (ImageButton) findViewById(R.id.appoint);
		util= (ImageButton) findViewById(R.id.util);
		webview_linear_layout=(LinearLayout)findViewById(R.id.webview_linear_layout);
		buttons_linearlayout=(LinearLayout)findViewById(R.id.buttons_linearlayout);
	more= (ImageButton) findViewById(R.id.more);

		linear_pobox=(TextView)findViewById(R.id.linear_pobox);
		linear_phone=(TextView)findViewById(R.id.linear_phone);
		linear_fax_no=(TextView)findViewById(R.id.linear_fax_no);
		linear_for_appointments=(TextView)findViewById(R.id.linear_for_appointments);
		linear_information=(TextView)findViewById(R.id.linear_information);
		linear_send_resumes=(TextView)findViewById(R.id.linear_send_resumes);
		linear_near=(TextView)findViewById(R.id.linear_near);



		cp.checkAndRequestPermissions(this);
		dep.setOnClickListener(this);
		appoint.setOnClickListener(this);
		util.setOnClickListener(this);
		more.setOnClickListener(this);
		Intent intent = getIntent();
		k=intent.getStringExtra("br_id");
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callPhone(v);
			}
		});
		but2 = (Button) findViewById(R.id.mail);
		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendMail(v);
			}
		});
		but3 = (Button) findViewById(R.id.map_finder);
		but3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i7 = new Intent(getApplicationContext(),MapsActivityNew.class);
				//  i3.putExtra("current_but_id",1);
				i7.putExtra("lat_id",lat);
				i7.putExtra("lng_id",lng);
				startActivity(i7);
			}
		});
		back= (ImageView) findViewById(R.id.back_image);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				ContactFragment.this.goBack();
			}
		});
		spinner = (Spinner)findViewById(R.id.location_spinner);
		//text = (TextView)findViewById(R.id.textData);
		//bn = (TextView) findViewById(R.id.th);


		LayoutInflater inflater3=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		customViewCall=inflater3.inflate(R.layout.popup_call,null);
		poptext=(TextView)customViewCall.findViewById(R.id.pop_call_text);

		call_yes=(LinearLayout)customViewCall.findViewById(R.id.call_yes);
		call_no=(LinearLayout)customViewCall.findViewById(R.id.call_no);

		call_no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPopupWindowCall.dismiss();
			}
		});


		LayoutInflater inflater4=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		customViewEmail=inflater4.inflate(R.layout.popup_email,null);
		poptextemail=(TextView)customViewEmail.findViewById(R.id.pop_email_text);

		email_yes=(LinearLayout)customViewEmail.findViewById(R.id.email_yes);
		email_no=(LinearLayout)customViewEmail.findViewById(R.id.email_no);

		email_no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPopupWindowEmail.dismiss();
			}
		});

		if (DetectConnection
				.checkInternetConnection(getApplicationContext())) {
			//Toast.makeText(getActivity(),
			//	"You have Internet Connection", Toast.LENGTH_LONG)
			new DownloadData1().execute();
			spinner.setVisibility(View.VISIBLE);
			webview_linear_layout.setVisibility(View.VISIBLE);
			buttons_linearlayout.setVisibility(View.VISIBLE);

		} else {

			/*final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getApplicationContext()).create();
			alertDialog.setTitle("Alert");

			alertDialog.setMessage("No Internet");


			alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();


				}
			});
			alertDialog.show();*/
			spinner.setVisibility(View.GONE);
			webview_linear_layout.setVisibility(View.GONE);
			buttons_linearlayout.setVisibility(View.GONE);

			Snackbar.with(ContactFragment.this,null)
					.type(Type.ERROR)
					.message("No Internet")
					.duration(Duration.LONG)

					.show();




		}
		//spinner = (Spinner) findViewById(R.id.div);

	}

	private void goBack() {
		super.onBackPressed();
	}

	public void sendMail(View v) {
		String data = ((Button) v).getText().toString();
		res=linear_information.getText().toString().replaceAll("\\s+","");
        int column_index=res.indexOf(":");

		res=res.substring(column_index+1,res.length());
		poptextemail.setText("Do you want to send mail to "+res);
		displayEmailPopup();

		email_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent emailIntent = new Intent(Intent.ACTION_SEND);// , Uri.fromParts(
				// "mailto:" + data.trim(), "", null));

				System.out.println("res : "+res);
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { res});
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Zulekha");
				emailIntent.setType("message/rfc822");
				startActivityForResult((Intent.createChooser(emailIntent, "Email")), 55);
				mPopupWindowEmail.dismiss();
			}
		});


	}


	public void callPhone(View v) {
		String data = ((Button) v).getText().toString();

		poptext.setText("Do you want to make a call to "+ph);
		displayCallingPopup();

		call_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String tolfree = zhm.get(0).getToll_free().toString();

				System.out.println("<<<<<<<<<<<<<<<<<<wifieeeeeeeee>>>>>>>>>>>>>>>>>" + tolfree);
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + ph));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(intent);
				mPopupWindowCall.dismiss();
			}
		});

		/*Intent callIntent = new Intent(Intent.ACTION_DIAL);
		callIntent.setData(Uri.parse("tel:" + ph));
		startActivityForResult(callIntent, 44);*/
		// Intent callIntent = new Intent(Intent.ACTION_CALL);
		// callIntent.setData(Uri.parse(data.trim()));
		// startActivity(callIntent);
	}

	public void showMap(View v) {

		String data = but3.getText().toString();
		/*if (data.length() > 0) {
			String[] datas = data.split("::");
			String l = "0.0", lo = "0.0";
			if (datas.length > 0) {
				if (datas[0].length() > 0) {
					l = datas[0];
				}
				if (datas[1].length() > 0) {
					lo = datas[1];
				}*/
				/*Double lat = Utils.getDoubleData(l);
				Double lng = Utils.getDoubleData(lo);*/
		double total = Double.parseDouble(lat);
		double price = Double.parseDouble(lng);
				String uri = String.format(Locale.ENGLISH,
						"http://maps.google.com/maps?daddr=%f,%f (%s)",total,
						price, "Zulekha Hospital is at");
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				startActivityForResult(intent, 33);


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
				/*Intent i3 = new Intent(getApplicationContext(),UtilityFragment.class);*/
				//	i1.putExtra("current_but_id",1);
				//i1.putExtra("brn","sharjah");
				/*startActivity(i3);*/
				break;
			case R.id.more:
				ContactFragment.this.goBack();
				break;
		}
	}

	private class DownloadData1 extends AsyncTask<Void, Void, Void> {

		ProgressDialog pd = null;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setVisibility(ProgressBar.VISIBLE);

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

				HttpHandler h=new HttpHandler();
				String s=h.makeServiceCall(BaseUrl+"android/contact.php?key=100");






				result = s;


				// Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("Loading connection  :", e.toString());
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
				System.out.println(">>>>>>>>>>>>>>>" + result);
				System.out.println(">>>>>>>>>>>>>>>" + sam);
				String value = result;

				JSONObject jsonObj = new JSONObject(result);

				String status = jsonObj.getString("status");
				if (status.equalsIgnoreCase("true")) {



						JSONArray mArray;
						zhm = new ArrayList<Zulekha_Highlight_Model>();
						try {
							mArray = jsonObj.getJSONArray("data");
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

								cnlist = new ArrayList<String>();
								zhm.add(zh);

								//divlist.add(dv);
								for (Zulekha_Highlight_Model zh : zhm) {
									cnlist.add(zh.getCenter());
									//  divisonlist.add(dv.getBranch_id());
								}
								spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
									@Override
									public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
										ads = zhm.get(i).getAdress();
										ph = zhm.get(i).getPhone();
										lat = zhm.get(i).getLatitude();
										lng = zhm.get(i).getLongitude();
										res = zhm.get(i).getInformation();

										/*if (ads.isEmpty() || ads == "" || ads == null || ads.contentEquals("")) {


											webview_linear_layout.setVisibility(View.GONE);

										} else {

											webview_linear_layout.setVisibility(View.VISIBLE);


										}*/

										linear_pobox.setText(zhm.get(i).getPobox());
										linear_phone.setText(zhm.get(i).getPhone());
										linear_fax_no.setText(zhm.get(i).getFax());
										linear_for_appointments.setText(zhm.get(i).getAppointment_s());
										linear_information.setText(zhm.get(i).getInformation());
										linear_send_resumes.setText(zhm.get(i).getResume());
										linear_near.setText(zhm.get(i).getNear());

										if (ph.isEmpty() || ph == "" || ph == null || ph.contentEquals("")) {


											but1.setVisibility(View.GONE);

										} else {

											but1.setVisibility(View.VISIBLE);

										}

										if (lat.isEmpty() || lat == "" || lat == null || lat.contentEquals("")) {

											but3.setVisibility(View.GONE);

										} else {


											but3.setVisibility(View.VISIBLE);

										}

										if (res.isEmpty() || res == "" || res == null || res.contentEquals("")) {

											but2.setVisibility(View.GONE);

										} else {

											but2.setVisibility(View.VISIBLE);

										}


										System.out.println(">>>>>>phhhhhhhhhhhhhhhhhhh>>>>>>>>>" + ph);
										System.out.println(">>>>>ads>>>>>>>>" + ads);
										System.out.println(">>>>>>latttttttttttttt>>>>>>>>>" + lat);
										System.out.println(">>>>>>lngggggggggggggg>>>>>>>>>" + lng);
										System.out.println(">>>>>ressssssssssssssss>>>>>>>>>" + res);
										/*vv.loadData(ads, "text/html; charset=UTF-8", null);*/
								/*String myCustomStyleString="<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/OpenSansLight" +
										"\")}body,* {font-family: MyFont; font-size: medium;text-align: justify;}</style>";
								vv.loadDataWithBaseURL("", myCustomStyleString+ads+"</div>", "text/html", "utf-8", null);*/
										//   vv.loadData(Dat, "text/html", "UTF-8");

                                        int column_index=ph.indexOf(":");
										ph = ph.substring(column_index+1, ph.length());
										but1.setText(zhm.get(i).getPhone());

										try {
											but2.setText(zhm.get(i).getInformation());
										} catch (Exception e) {
											e.printStackTrace();
										}
								/*but3.setText(lat+""+lng);*/

										System.out.println("ph : " + ph);
										System.out.println("res : " + res);
										System.out.println("lat : " + lat);
										System.out.println("lng : " + lng);


										//new ZH_Appointment_Register_Fragment.DownloadData().execute();
									}

									@Override
									public void onNothingSelected(AdapterView<?> adapterView) {

									}
								});
								ArrayAdapter<String> spinnerAdapter0 = new ArrayAdapter<String>(getApplicationContext(),
										R.layout.spinner_cntact, R.id.txt, cnlist);

//                    spinnerAdapter0
//                            .setDropDownViewResource(R.layout.patientreferal_spinner_item);

								spinner.setAdapter(spinnerAdapter0);
								spinnerAdapter0.notifyDataSetChanged();
								System.out.println("<<<oo>>>>" + zh);
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
						} catch (JSONException e) {
							e.printStackTrace();
						}

               /* ArrayAdapter<PastModel> adapter1 = new MyList();
                ListV.setAdapter(adapter1);
*/


           /* ListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Lm=pns.get(i).getEvent_year();
                    System.out.println("<<<oo>lllllllllllllllllllll>>>"+ Lm);
                    new DownloadData2().execute();
                }
            });*/

				}
				else{

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void displayCallingPopup()
	{
		try {


			mPopupWindowCall = new PopupWindow(customViewCall, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			if (Build.VERSION.SDK_INT >= 21) {
				mPopupWindowCall.setElevation(5.0f);
			}

			mPopupWindowCall.setFocusable(true);
			mPopupWindowCall.setAnimationStyle(R.style.popupAnimation);






			mPopupWindowCall.showAtLocation(top_layout, Gravity.CENTER, 0, 0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void displayEmailPopup()
	{
		try {


			mPopupWindowEmail = new PopupWindow(customViewEmail, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			if (Build.VERSION.SDK_INT >= 21) {
				mPopupWindowEmail.setElevation(5.0f);
			}

			mPopupWindowEmail.setFocusable(true);
			mPopupWindowEmail.setAnimationStyle(R.style.popupAnimation);






			mPopupWindowEmail.showAtLocation(top_layout, Gravity.CENTER, 0, 0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

//        else {
//                Toast.makeText(getApplicationContext(), "notcorrect" + result, Toast.LENGTH_SHORT).show();
//
//            }





