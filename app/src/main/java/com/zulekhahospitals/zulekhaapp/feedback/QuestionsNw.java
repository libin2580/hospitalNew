package com.zulekhahospitals.zulekhaapp.feedback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leafle_pdf_Fragment;
import com.zulekhahospitals.zulekhaapp.login.LoginActivity;
import com.zulekhahospitals.zulekhaapp.utility.NetworkCheckingClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.BaseUrl;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class QuestionsNw extends AppCompatActivity {
    ImageButton but_excelnt, but_good, but_poor, but_excelntlrg, but_poorlrg, but_goodlrg;//but_verypoor
    // but_verypoorlarge;
    Button submit_feedback,back_to_hom;
    int cnt_exclnt = 0, cnt_good = 0, cnt_poor = 0, cnt_verypoor = 0;
    ArrayList<QuestionModel> questionsArrayList;
    TextView txt_questn_number, txt_questn_name;
    ProgressBar progress;
    ImageView ic_send;
    String q;
    String starttext ="";
    String question_number, question_name;
    int id = 0;
    StringBuffer sb_questionid, sb_answer, sb_department, sb_suggestions, sb_recommend,sb_poor_department,sb_overall_satisfcation;
    TextView questns;
    ArrayList<JsonModel> jsonModelArrayList;
    String userid, user_id, question;
    EditText edt_deprmnt_name, edt_suggestins;
    ImageButton logout;
    RadioButton radio_yes, radio_no;
    TextView txt_appreciate;
    String radioid, str_radio_check, suggestions,name;
    Animation animation;
    HTextView department_name;
    int selects = 0;
    static int s = 0;
    MaterialSpinner spinner;
    RadioGroup radio_recommend;
    String newtext="";
    boolean btngoodclicked = false;
    boolean btnexcellentclicked = false;
    boolean btnpoorclicked = false;
    private static final String[] ANDROID_VERSIONS = {
            "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat",
            "Lollipop", "Marshmallow"
    };
    private static String[]stockArr;
    AutoCompleteTextView actv;
    String place;
    LinearLayout linearLayout_recommend, linearLayout_smily, lay_disatis, lay_suggestions, lay_questions, lay_department,lay_appreciate;
    String agentid, department_dis_name,new_user_id;
    String BASE_URL_DEPT =BaseUrl+"android/department.php";
    String BASE_URL_ANSWER =BaseUrl+"android/answer.php";

    /*   String demo="http://192.200.125.44/zulekhafeedback/";*/
    // String REGISTER_URL = "http://192.200.125.44/zulekhafeedback/answer.php";
    //String URL_DEPARTMENT = "http://192.200.125.44/zulekhafeedback/department.php";
//    String URL_DELETE = "http://192.200.125.45:1234/zulekhafeedback/delete.php";
//      String REGISTER_URL1 = "http://zulekhahospitals.com/feedback/answer.php";
//        String URL_DEPARTMENT1 = "http://zulekhahospitals.com/feedback/department.php";
    // String URL_DELETE1 = "http://zulekhahospitals.com/feedback/delete.php";
    RelativeLayout questnz;
    String department="",username="",email="",location="",usertype="",phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questions);
        analytics = FirebaseAnalytics.getInstance(QuestionsNw.this);
        analytics.setCurrentScreen(QuestionsNw.this,QuestionsNw.this.getLocalClassName(), null /* class override */);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        new_user_id=getIntent().getStringExtra("user_ids");
        place=getIntent().getStringExtra("place");
        System.out.println("placeeeeee"+place);
        but_good = (ImageButton) findViewById(R.id.but_good);
        but_poor = (ImageButton) findViewById(R.id.but_poor);
       /* but_verypoor = (ImageButton) findViewById(R.id.but_vrypoor);*/
        submit_feedback = (Button) findViewById(R.id.but_submit_feedback);
        sb_questionid = new StringBuffer();
        sb_answer = new StringBuffer();
        sb_department = new StringBuffer();
        sb_suggestions = new StringBuffer();
        sb_recommend = new StringBuffer();
        sb_poor_department = new StringBuffer();
        sb_overall_satisfcation = new StringBuffer();
        questionsArrayList = new ArrayList<>();
        questionsArrayList = LoginActivityFeedBack.questn();

        SharedPreferences preferences = getSharedPreferences("MyPref_login", MODE_PRIVATE);
        userid = preferences.getString("user_id", null);
        department = preferences.getString("department", null);
        username = preferences.getString("name", null);
        email = preferences.getString("email", null);
        location = preferences.getString("location", null);
        usertype= preferences.getString("usertype", null);
        phone= preferences.getString("phone", null);
        System.out.println("useriddddd............." +"\ndept : "+ department+"\nuserid : "+userid+"\nusername : "+username+"\nusertype : "+usertype+"\nlocation : "+location+"\nphone : "+phone+"\nemail : "+email);



        departmnt(location);
        actv= (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);
        ic_send= (ImageView) findViewById(R.id.send_deprt);
        questnz= (RelativeLayout) findViewById(R.id.questnz);
        progress = (ProgressBar) findViewById(R.id.progress_bar_questions);
        department_name = (HTextView) findViewById(R.id.quest_department);
        lay_questions = (LinearLayout) findViewById(R.id.layout_questn);
        lay_department = (LinearLayout) findViewById(R.id.layout_departmnt);
        radio_recommend = (RadioGroup) findViewById(R.id.radio_recommends);
        radio_yes = (RadioButton) findViewById(R.id.radio_checked_yess);
        radio_no = (RadioButton) findViewById(R.id.radio_checked_noo);
        linearLayout_recommend = (LinearLayout) findViewById(R.id.layout_recommend);
        linearLayout_smily = (LinearLayout) findViewById(R.id.layout_smily);
        lay_suggestions = (LinearLayout) findViewById(R.id.lay_suggestions);

        lay_disatis = (LinearLayout) findViewById(R.id.lay_disatisfied);
        lay_appreciate= (LinearLayout) findViewById(R.id.lay_back_appreciate);
        txt_questn_name = (TextView) findViewById(R.id.quest_name);
        txt_appreciate = (TextView) findViewById(R.id.text_backk_appreciate);
        //  edt_deprmnt_name = (EditText) findViewById(R.id.disat_departmnt_name);
        edt_suggestins = (EditText) findViewById(R.id.edt_suggestins);
        txt_questn_number = (TextView) findViewById(R.id.quest_number);
        but_excelnt = (ImageButton) findViewById(R.id.but_excelnt);
        but_excelntlrg = (ImageButton) findViewById(R.id.but_excelntLRG);
        but_goodlrg = (ImageButton) findViewById(R.id.but_goodlrg);
        but_poorlrg = (ImageButton) findViewById(R.id.but_poorlarge);
        lay_appreciate.setVisibility(View.GONE);


        back_to_hom= (Button) findViewById(R.id.but_backhom);
        back_to_hom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ic_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.dismiss();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                System.out.println("appended valueee onclick..........."+actv.getText().toString());
                System.out.println("appended starttext..........."+ starttext);
                sb_overall_satisfcation=new StringBuffer();
                sb_overall_satisfcation.append("Poor");
                sb_poor_department.append(actv.getText().toString());
                // sb_department.append(department_dis_name);
                if(starttext.equalsIgnoreCase("")&&newtext.equalsIgnoreCase("")&&actv.getText().toString().equalsIgnoreCase("")) {

                    Snackbar.with(QuestionsNw.this,null)
                            .type(Type.ERROR)
                            .message("Please mention your disatisfied Department name")
                            .duration(Duration.SHORT)

                            .show();

                }
                else {
                     imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    lay_disatis.setVisibility(View.GONE);
                    lay_questions.setVisibility(View.VISIBLE);
                    linearLayout_recommend.setVisibility(View.VISIBLE);
                    txt_questn_name.setText("Would you Recommend this Hospital To Others?");
                }
            }
        });

        // spinner = (MaterialSpinner) findViewById(R.id.spinner);


        department_name.setAnimateType(HTextViewType.ANVIL);
        department_name.setSingleLine(false);
        //  department_name = (TextView) findViewById(R.id.quest_department);
        Typeface myFont5 = Typeface.createFromAsset(getApplicationContext().getAssets(), "Roboto-Regular.ttf");
        department_name.setTypeface(myFont5);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom);
        //  but_verypoorlarge = (ImageButton) findViewById(R.id.but_verypoorlarge);
        // logout = (ImageButton) findViewById(R.id.close);


        String log_status = preferences.getString("log_status", null);



        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                progress.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        radio_recommend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                RadioButton checkedRadioButton = (RadioButton) radio_recommend.findViewById(id);

                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {


                    str_radio_check = checkedRadioButton.getText().toString();
                    System.out.println("str_radio_check....."+    str_radio_check+"is..checkeddd....."+isChecked);
                    if (str_radio_check.contentEquals("Yes")) {
                        System.out.println("yesssss");

                        sb_recommend.append("Yes");


                        lay_questions.setVisibility(View.GONE);
                        lay_suggestions.setVisibility(View.VISIBLE);
                        submit_feedback.setVisibility(View.VISIBLE);
                        linearLayout_smily.setVisibility(View.GONE);
                        lay_disatis.setVisibility(View.GONE);
                        linearLayout_recommend.setVisibility(View.GONE);
                        lay_questions.setVisibility(View.GONE);


                    } else {
                        System.out.println("noooo");
                        sb_recommend.append("No");
                        lay_suggestions.setVisibility(View.VISIBLE);
                        submit_feedback.setVisibility(View.VISIBLE);
                        linearLayout_smily.setVisibility(View.GONE);
                        lay_disatis.setVisibility(View.GONE);
                        linearLayout_recommend.setVisibility(View.GONE);
                        lay_questions.setVisibility(View.GONE);


                    }

                }
            }
        });


        //  edt_suggestins.setSingleLine();

        //  edt_suggestins.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        //  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {


        // edt_deprmnt_name.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        edt_suggestins.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                    System.out.println(" suggestions..." + suggestions);
//
//                   // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                  //  imm.hideSoftInputFromWindow(edt_suggestins.getWindowToken(), 0);
//
//                    if (suggestions != null) {
//
//
//
//                      //  edt_suggestins.setSelection(0);
//
//                        linearLayout_smily.setVisibility(View.GONE);
//                        lay_disatis.setVisibility(View.GONE);
//                        linearLayout_recommend.setVisibility(View.GONE);
//                        lay_questions.setVisibility(View.GONE);
//
//                        submit_feedback.setVisibility(View.VISIBLE);
//                        but_excelnt.setVisibility(View.VISIBLE);
//                        but_excelntlrg.setVisibility(View.INVISIBLE);
//                        but_excelnt.setEnabled(false);
//                        but_good.setEnabled(false);
//                        but_poor.setEnabled(false);
//                ///        but_verypoor.setEnabled(false);
//                        but_excelntlrg.setEnabled(false);
//                        but_poorlrg.setEnabled(false);
//                        but_goodlrg.setEnabled(false);
//                      //  but_verypoorlarge.setEnabled(false);
//                        Snackbar.with(QuestionsNwnw.this,null)
//                                .type(Type.SUCCESS)
//                                .message("Please press the submit button to give your feedback")
//                                .duration(Duration.SHORT)
//
//                                .show();
////                        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//
//                        System.out.println("appended stringg.............." + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());
//
//                        System.out.println("id18stringgg...not null" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());
//
//                    }
//                    else {
//                        System.out.println("id18stringgg...null" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());
//                    }
//            }
//        });

        //     return true;
//                } else {
//
//                    return false;
//                }
        // }

        //    });
        if (questionsArrayList != null) {
            agentid = "0";
            for (int i = 0; i < questionsArrayList.size(); i++) {

                String qid = questionsArrayList.get(i).getQuestid();

                question = questionsArrayList.get(i).getQuestname();
                System.out.println("qusetionzzz"+ question);


                System.out.println("agent id : "+agentid);

                String department = questionsArrayList.get(i).getDepartment();
                user_id = questionsArrayList.get(i).getUser_id();
                name = questionsArrayList.get(i).getPerson_name();
                System.out.println("user...id" + user_id);

            }
            if (id <=questionsArrayList.size()) {

                if (id == 0) {
                    id = 0;

                    txt_questn_number.setText("" + id + 1);
                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                    department_name.animateText(questionsArrayList.get(id).getDepartment());
                }
//                } else {
//
//
//                    txt_questn_number.setText("" + id);
//                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
//                    department_name.animateText(questionsArrayList.get(id).getDepartment());
//                }


            } else {

                submit_feedback.setVisibility(View.VISIBLE);

            }
        }


        submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (agentid != null) {
                    but_excelnt.setEnabled(false);
                    but_good.setEnabled(false);
                    but_poor.setEnabled(false);
                    //  but_verypoor.setEnabled(false);
                    but_excelntlrg.setEnabled(false);
                    but_poorlrg.setEnabled(false);
                    but_goodlrg.setEnabled(false);
                    //  but_verypoorlarge.setEnabled(false);
//
//                   new MaterialStyledDialog.Builder(QuestionsNw.this)
//                            .setTitle("Submit FeedBack!")
//                            .setDescription("Are you sure you want to submit this feedback?")
//                            .setStyle(Style.HEADER_WITH_TITLE)
//                            .setIcon(R.drawable.logo)
//                          //  .withIconAnimation(true)
//                          //  .withDialogAnimation(true)
//                            .setHeaderColor(R.color.colorPrimary)
//                            .setCancelable(true)
//                            .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
//                                @Override
//                                public void onClick(MaterialDialog dialog, DialogAction which) {
                    NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
                    final boolean i = networkCheckingClass.ckeckinternet();
                    System.out.println("login....i" + i);
                    if (i) {

                        fun();
                        submit_feedback.setVisibility(View.INVISIBLE);


                    }
                    else {
                        Snackbar.with(QuestionsNw.this, null)
                                .type(Type.SUCCESS)
                                .message("No internet Connection!")
                                .duration(Duration.SHORT)
                                .show();


                    }
                }
//                            })
//
//                           .show();

                //  }
                else {
                    new MaterialStyledDialog.Builder(QuestionsNw.this)
                            // .setTitle("SUCCESS!")
                            .setDescription("Please login to submit your feedback.")
                            .setIcon(R.drawable.logo)
                            .setStyle(Style.HEADER_WITH_TITLE)
                            // .withDialogAnimation(true)
                            .setHeaderColor(R.color.colorPrimary)
                            .setCancelable(true)
                            .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {

                                    Intent s = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(s);
                                }
                            })
                            .show();


                }

            }
        });

        but_excelnt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                btnexcellentclicked=true;
                btngoodclicked=false;
                btnpoorclicked=false;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(id <questionsArrayList.size())
                    {

                        System.out.println("countexcellent." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                        but_excelnt.setVisibility(View.INVISIBLE);
                        but_excelntlrg.setVisibility(View.VISIBLE);

                        sb_department.append(questionsArrayList.get(id).getDepartment());
                        sb_department.append(",");

                        //   but_good.setBackgroundResource(R.drawable.goodlarge);
                        sb_questionid.append(questionsArrayList.get(id).getQuestid());
                        sb_questionid.append(",");
                        sb_answer.append("Excellent");
                        sb_answer.append(",");

                        cnt_exclnt++;

                        System.out.println("countexcellent." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                        new Handler().postDelayed(new Runnable() {
                            //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                but_excelnt.setVisibility(View.VISIBLE);
                                but_excelntlrg.setVisibility(View.INVISIBLE);

                                // close this activity

                            }
                        }, 500);
//
                        id++;
                        if(id <=questionsArrayList.size())
                        {


                            if(id==questionsArrayList.size())
                            {   lay_department.setVisibility(View.GONE);
                                txt_questn_name.setText("Overall Satisfation of the Hospital?");

                            }
                            else {
                                txt_questn_number.setText("" + id);
                                txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                department_name.animateText(questionsArrayList.get(id).getDepartment());
                            }


                        }



//                        else {
//                            txt_questn_name.setText("Overall Satisfation of the Hospital?");
//                        }


                        System.out.println("countexcellent." + id + "..............");

                    }
                    // increaseSize();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {



                    if (id==questionsArrayList.size()) {

                        lay_department.setVisibility(View.GONE);
                        linearLayout_smily.setVisibility(View.VISIBLE);
                        linearLayout_recommend.setVisibility(View.GONE);

                        lay_questions.setVisibility(View.VISIBLE);

                        but_excelnt.setVisibility(View.INVISIBLE);
                        but_excelntlrg.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                but_excelnt.setVisibility(View.VISIBLE);
                                but_excelntlrg.setVisibility(View.INVISIBLE);

                                if (btnpoorclicked == false && btngoodclicked == false && btnexcellentclicked == true) {

                                    sb_overall_satisfcation = new StringBuffer();
                                    sb_poor_department = new StringBuffer();

                                    sb_overall_satisfcation.append("Excellent");
                                    sb_poor_department.append("N/A");
                                    txt_questn_name.setText("Would you Recommend this Hospital To Others?");

                                    txt_questn_name.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
                                    linearLayout_smily.setVisibility(View.GONE);
                                    linearLayout_recommend.setVisibility(View.VISIBLE);
                                }

                                System.out.println("button..excellent....would youuuu recmdddd");

                                // close this activity

                            }
                        }, 500);




                    }
                    //  resetSize();
                }
                return true;
            }
        });

//        but_excelnt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                System.out.println("iddddd"+id+"...."+questionsArrayList.size());
//                try {
//
//
//
//                    System.out.println("countexcellentq." + id + "..............");
//
//
//
//                }
//                catch (Exception e)
//                {
//
//                }
//
//
//
//
//            }
//        });
        but_good.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnexcellentclicked=false;
                    btngoodclicked=true;
                    btnpoorclicked=false;


                    try {

                        if (id < questionsArrayList.size()) {

                            but_good.setVisibility(View.INVISIBLE);
                            but_goodlrg.setVisibility(View.VISIBLE);

                            sb_department.append(questionsArrayList.get(id).getDepartment());
                            sb_department.append(",");
                            System.out.println("countgood." + id + ".............." + questionsArrayList.get(id).getQuestname() + "...." + "......" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                            //   but_good.setBackgroundResource(R.drawable.goodlarge);
                            sb_questionid.append(questionsArrayList.get(id).getQuestid());
                            sb_questionid.append(",");
                            sb_answer.append("Good");
                            sb_answer.append(",");
                            System.out.println("countgood." + id + ".............." + questionsArrayList.get(id).getQuestname() + "...." + "......" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                            cnt_good++;

                            System.out.println("countgood." + id + ".............." + questionsArrayList.get(id).getQuestname() + "...." + "......" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_good.setVisibility(View.VISIBLE);
                                    but_goodlrg.setVisibility(View.INVISIBLE);

                                    // close this activity

                                }
                            }, 500);
//
                            id++;
                            if (id <= questionsArrayList.size()) {


                                if (id == questionsArrayList.size()) {
                                    lay_department.setVisibility(View.GONE);
                                    txt_questn_name.setText("Overall Satisfation of the Hospital?");

                                } else {
                                    txt_questn_number.setText("" + id);
                                    txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                    department_name.animateText(questionsArrayList.get(id).getDepartment());
                                }


                            }


//                        else {
//                            txt_questn_name.setText("Overall Satisfation of the Hospital?");
//                        }


                            System.out.println("countgood." + id + "..............");

                        } else if (id == questionsArrayList.size()) {




                            lay_department.setVisibility(View.GONE);
                            linearLayout_smily.setVisibility(View.VISIBLE);
                            linearLayout_recommend.setVisibility(View.GONE);

                            lay_questions.setVisibility(View.VISIBLE);

                            but_good.setVisibility(View.INVISIBLE);
                            but_goodlrg.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    but_good.setVisibility(View.VISIBLE);
                                    but_goodlrg.setVisibility(View.INVISIBLE);

                                    if (btnpoorclicked == false && btngoodclicked == true && btnexcellentclicked == false) {

                                        sb_overall_satisfcation = new StringBuffer();
                                        sb_poor_department = new StringBuffer();

                                        sb_overall_satisfcation.append("Good");
                                        sb_poor_department.append("N/A");
                                        txt_questn_name.setText("Would you Recommend this Hospital To Others?");

                                        txt_questn_name.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
                                        linearLayout_smily.setVisibility(View.GONE);
                                        linearLayout_recommend.setVisibility(View.VISIBLE);
                                        System.out.println("button..gooodddd....would youuuu recmdddd");
                                    }


                                    // close this activity

                                }
                            }, 500);




                            System.out.println("countgoodq." + id + "..............");


                        }

                    }
                    catch (Exception e)
                    {

                    }

                }

                return true;
            }
        });
//        but_good.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("feedback....questionsArrayList.size()"+questionsArrayList.size());
//
//
//
//
//            }
//        });

        but_poor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnexcellentclicked=false;
                    btngoodclicked=false;
                    btnpoorclicked=true;
                    but_poor.setAlpha((float) 0.5);
                    if(btnpoorclicked==true&&btngoodclicked==false&&btnexcellentclicked==false)
                    {

                        if(txt_questn_name.getText().toString().contentEquals("Overall Satisfation of the Hospital?")) {

                            lay_department.setVisibility(View.GONE);
                            linearLayout_smily.setVisibility(View.GONE);
                            lay_disatis.setVisibility(View.VISIBLE);
                            // edt_deprmnt_name.setSingleLine();
                            lay_questions.setVisibility(View.GONE);

                            //  System.out.println("item.........."+arg0+"....."+arg1+"....."+arg2+"...."+arg3+"...."+actv.getText().toString());
                            actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                    //   Log.i("SELECTED TEXT WAS------->", arg0.getItemAtPosition(arg2));
                                    starttext = String.valueOf(arg0.getItemAtPosition(arg2));
                                    //   String selection = (String)parent.getItemAtPosition(position);
                                    System.out.println("item.........." + arg0 + "....." + arg1 + "....." + arg2 + "...." + arg3 + "...." + starttext);

                                    if (starttext != null) {
                                        actv.setTag("yes");
                                        System.out.println("item.........." + actv.getTag());
                                        System.out.println("item.........." + starttext);

//
//                                        sb_questionid.append(questionsArrayList.get(id).getQuestid());
//                                        sb_questionid.append(",");
//
//                                        sb_answer.append("poor");
//                                        sb_answer.append(",");

                                        // edt_deprmnt_name.setSelection(0);
                                        sb_overall_satisfcation = new StringBuffer();
//                                            sb_overall_satisfcation.append("Poor");
                                        //  sb_poor_department=new StringBuffer();
//                                            sb_poor_department.append(starttext);
//                                            sb_department.append(department_dis_name);
//                                          sb_department.append(",");

                                        //  sb_poor_department.append(",");
                                        txt_questn_number.setText("" + id);
                                        linearLayout_smily.setVisibility(View.GONE);

                                        id++;
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                        Snackbar.with(QuestionsNw.this, null)
                                                .type(Type.SUCCESS)
                                                .message("Department name you have selected is " + starttext)
                                                .duration(Duration.LONG)

                                                .show();


                                        System.out.println("sb_overallll..satisfaction...." + sb_overall_satisfcation.toString() + " sb_poor_department....." + sb_poor_department.toString());

                                    } else if (actv.getText().toString() != null) {
                                        newtext = actv.getText().toString();
                                        sb_overall_satisfcation = new StringBuffer();
//                                    sb_overall_satisfcation.append("Poor");
                                        sb_poor_department = new StringBuffer();
//                                    sb_poor_department.append(actv.getText().toString());

//                                            sb_department.append(department_dis_name);
//                                          sb_department.append(",");

                                        //  sb_poor_department.append(",");
                                        txt_questn_number.setText("" + id);
                                        linearLayout_smily.setVisibility(View.GONE);
                                        Snackbar.with(QuestionsNw.this, null)
                                                .type(Type.SUCCESS)
                                                .message("Department name you have selected is " + newtext)
                                                .duration(Duration.LONG)

                                                .show();

                                        id++;

                                        System.out.println("sb_overallll..satisfactions...." + sb_overall_satisfcation.toString() + " sb_poor_department....." + sb_poor_department.toString());

                                    } else {

                                    }


                                }
                            });
                        }

//                        actv.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged( Editable s) {
//                            System.out.println("typingggg.... "+s.toString());
//
//                            sb_overall_satisfcation=new StringBuffer();
//                            sb_overall_satisfcation.append("Poor");
//                            sb_poor_department=new StringBuffer();
//                            sb_poor_department.append(s);
//                            newtext=s.toString();
////                                            sb_department.append(department_dis_name);
////                                          sb_department.append(",");
//
//                            //  sb_poor_department.append(",");
//                            txt_questn_number.setText(""+ id);
//                            linearLayout_smily.setVisibility(View.GONE);
//
//                            id++;
//
//
//                        }
//                    });




                    }



                    System.out.println("feedback....questionsArrayList.size()"+questionsArrayList.size());



                    if(id <questionsArrayList.size())
                    {

                        but_poor.setVisibility(View.INVISIBLE);
                        but_poorlrg.setVisibility(View.VISIBLE);

                        sb_department.append(questionsArrayList.get(id).getDepartment());
                        sb_department.append(",");
                        System.out.println("countpoor." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                        //   but_good.setBackgroundResource(R.drawable.goodlarge);
                        sb_questionid.append(questionsArrayList.get(id).getQuestid());
                        sb_questionid.append(",");
                        sb_answer.append("Poor");
                        sb_answer.append(",");
                        System.out.println("countpoor." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

                        cnt_poor++;

                        System.out.println("countpoor." + id + ".............."+questionsArrayList.get(id).getQuestname()+"...." +"......"+ sb_questionid.toString() + sb_answer.toString() + sb_department.toString());


//
                        id++;
                        if(id <=questionsArrayList.size())
                        {


                            if(id==questionsArrayList.size())
                            {   lay_department.setVisibility(View.GONE);
                                txt_questn_name.setText("Overall Satisfation of the Hospital?");

                            }
                            else {
                                txt_questn_number.setText("" + id);
                                txt_questn_name.setText(questionsArrayList.get(id).getQuestname());
                                department_name.animateText(questionsArrayList.get(id).getDepartment());
                            }


                        }



//                        else {
//                            txt_questn_name.setText("Overall Satisfation of the Hospital?");
//                        }


                        System.out.println("countpoor." + id + "..............");

                    }



                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    but_poor.setAlpha((float)1.0);


                    new Handler().postDelayed(new Runnable() {
                        //
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            but_poor.setVisibility(View.VISIBLE);
                            but_poorlrg.setVisibility(View.INVISIBLE);

                            // close this activity

                        }
                    }, 500);
                }
                return true;
            }
        });
//        but_poor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//
//            }
//
//
//        });


    }



    public  void   fun() {
        try{

        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();
        progress.setVisibility(ProgressBar.VISIBLE);
        // System.out.println("buffered string value" + sb_questionid.toString() + sb_answer.toString() + sb_department.toString());

        jsonModelArrayList = new ArrayList<JsonModel>();
        Gson gson = new Gson();
        JsonModel jsonModel = new JsonModel();


            String  qustn = sb_questionid.toString().replace("[", "").replace("]", "")
                    .replace(", ", ",");
            String  answer = sb_answer.toString().replace("[", "").replace("]", "")
                    .replace(", ", ",");
            String  department = sb_department.toString().replace("[", "").replace("]", "")
                    .replace(", ", ",");

        jsonModel.setQuestions(qustn);
        jsonModel.setAnswers(answer);
        jsonModel.setDepartment(department);


        suggestions = edt_suggestins.getText().toString();
        sb_suggestions.append(suggestions);
//                            jsonModel.setSuggestions(sb_suggestions.toString());
//                            jsonModel.setRecommend(sb_recommend.toString());
//                            jsonModel.setOverall_satisfaction(sb_overall_satisfcation.toString());
//                            jsonModel.setPoor_department_name(sb_poor_department.toString())
        jsonModelArrayList.add(jsonModel);
        final String string_apnd = gson.toJson(jsonModelArrayList);

        if (i == true) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_ANSWER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("responseeeee" + response);

                            //    JSONObject jsonObj = null;
                            //                                jsonObj = new JSONObject(response);
//                               String statusd = jsonObj.getString("status");
//                                System.out.println("statusssss............"+statusd);


                            // pd.dismiss();

                            if (response.contentEquals("\"success\"")) {
                                lay_suggestions.setVisibility(View.GONE);
                                progress.setVisibility(ProgressBar.GONE);
                                new MaterialStyledDialog.Builder(QuestionsNw.this)
                                        .setTitle("Success")
                                        .setDescription("Thank you " + username+". We appreciate your feedback.")
                                        .setStyle(Style.HEADER_WITH_TITLE)
                                        // .setIcon(R.drawable.logo)
                                        .setHeaderColor(R.color.colorPrimary)
                                        .setCancelable(true)
                                        .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                System.out.println("goo..to loginnn");
                                                System.out.println("goo..to loginnn");
                                                questionsArrayList.clear();
                                               /* SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = preferences1.edit();
                                                //  editor.putString("log_status", "logout");
                                                editor.putString("user_id", null);
                                                editor.clear();
                                                editor.apply();
                                                editor.commit();*/
                                                /*Intent is = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(is);*/
                                                finish();

//                                                lay_appreciate.setVisibility(View.VISIBLE);
//                                                submit_feedback.setVisibility(View.GONE);
//                                                txt_appreciate.setText("Thank you "+name+" we appreciate your feedback" );


                                            }
                                        })
                                        .show();


                            } else {
                                lay_suggestions.setVisibility(View.GONE);
                                progress.setVisibility(ProgressBar.GONE);
                                new MaterialStyledDialog.Builder(QuestionsNw.this)
                                        .setTitle("Success")
                                        .setDescription("Thank you " + name + " we appreciate your feedback")
                                        .setStyle(Style.HEADER_WITH_TITLE)
                                        // .setIcon(R.drawable.logo)
                                        .setHeaderColor(R.color.colorPrimary)
                                        .setCancelable(true)
                                        .setPositive(getResources().getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                System.out.println("goo..to loginnn");
                                                System.out.println("goo..to loginnn");
                                                questionsArrayList.clear();
                                                /*SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = preferences1.edit();
                                                //  editor.putString("log_status", "logout");
                                                editor.putString("user_id", null);
                                                editor.clear();
                                                editor.apply();
                                                editor.commit();*/
                                                /*Intent is = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(is);*/
                                                finish();

//                                                lay_appreciate.setVisibility(View.VISIBLE);
//                                                submit_feedback.setVisibility(View.GONE);
//                                                txt_appreciate.setText("Thank you "+name+" we appreciate your feedback" );


                                            }
                                        })
                                        .show();

                            }


//                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                                        QuestionsNw.this);
//
//
//                                //    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
//                                alertDialogBuilder.setTitle("Alert");
//                                if (statusd.contentEquals("success")) {
//
//                                    lay_suggestions.setVisibility(View.GONE);
//                                    alertDialogBuilder.setMessage(statusd);
//                                } else {
//                                    alertDialogBuilder.setMessage(statusd);
//                                }
//                                alertDialogBuilder.setNeutralButton("OK",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                                if (statusd.contentEquals("success"))
//                                                {
//                                                    System.out.println("goo..to loginnn");
//                                                    questionsArrayList.clear();
//                                                    SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
//                                                    SharedPreferences.Editor editor = preferences1.edit();
//                                                    //  editor.putString("log_status", "logout");
//                                                    editor.putString("user_id", null);
//                                                    editor.clear();
//                                                    editor.apply();
//                                                    editor.commit();
////                                                                        SharedPreferences sharedPref = getSharedPreferences("sharedstatus", Context.MODE_PRIVATE);
////                                                                        SharedPreferences.Editor editor1 = sharedPref.edit();
////                                                                        editor1.putString("status", statusd);
////                                                                        editor1.apply();
////                                                                        editor1.commit();
//                                                    lay_appreciate.setVisibility(View.VISIBLE);
//                                                    submit_feedback.setVisibility(View.GONE);
//                                                    txt_appreciate.setText("Thank you "+name+" we appreciate your feedback" );
//
//                                                    // SubCoursesActivity1.notif.setText(""+0);
//
//                                                }
//                                                dialog.dismiss();
//
//
//                                            }
//                                        });
//                                AlertDialog alertDialog = alertDialogBuilder.create();
//                                alertDialog.show();
//                                Window window = alertDialog.getWindow();
//                                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
//                                //    nbutton.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.butnbakcolr));
//                                nbutton.setTextColor(getApplicationContext().getResources().getColor(R.color.butnbakcolr));


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {


                    System.out.println("buffered string value" + string_apnd + ".........." + sb_suggestions.toString() + "....." + sb_recommend.toString() + "...." + sb_overall_satisfcation.toString() + "...." + sb_poor_department.toString());
                    System.out.println("countexc" + cnt_exclnt + "good" + cnt_good + "poor" + cnt_poor + "verypoor" + cnt_verypoor);

                    System.out.println("DDDDDDDDDDDDD" + name + "email" + email + "poor" + phone + "verypoor" + location + usertype);
                    Map<String, String> params = new HashMap<String, String>();

                    System.out.println("-----------------------------------------------------------");
                    System.out.println("name : " + username);
                    System.out.println("email : " + email);
                    System.out.println("phoneno : " + phone);
                    System.out.println("location : " + location);
                    System.out.println("usertype : " + usertype);
                    System.out.println("someJSON : " + string_apnd);
                    System.out.println("agentid : " + agentid);
                    System.out.println("suggestions : " + sb_suggestions.toString());
                    System.out.println("recommend : " + sb_recommend.toString());
                    System.out.println("overall_satisfaction : " + sb_overall_satisfcation.toString());
                    System.out.println("poor_department_name : " + sb_poor_department.toString());
                    System.out.println("-----------------------------------------------------------");




ArrayList<testModel> testArraylist=new ArrayList<testModel>();
                    Gson gson = new Gson();
                    testModel jsonModel = new testModel();
                    jsonModel.setName(username);
                    jsonModel.setEmail(email);
                    jsonModel.setPhoneno(phone);
                    jsonModel.setLocation(location);
                    jsonModel.setUsertype(usertype);
                    jsonModel.setSomeJSON(string_apnd);
                    jsonModel.setAgentid(agentid);
                    jsonModel.setSuggestions(sb_suggestions.toString());
                    jsonModel.setRecommend(sb_recommend.toString());
                    jsonModel.setOverall_satisfaction(sb_overall_satisfcation.toString());
                    jsonModel.setPoor_department_name(sb_poor_department.toString());
                    testArraylist.add(jsonModel);
                    final String string_apndddd = gson.toJson(testArraylist);
                    System.out.println("string_apndddd : "+string_apndddd);






                    params.put("name", username);
                    params.put("email", email);
                    params.put("phoneno", phone);
                    params.put("location", location);
                    params.put("usertype", usertype);
                    params.put("someJSON", string_apnd);
                    params.put("agentid", agentid);
                    params.put("suggestions", sb_suggestions.toString());
                    params.put("recommend", sb_recommend.toString());
                    params.put("overall_satisfaction", sb_overall_satisfcation.toString());
                    params.put("poor_department_name", sb_poor_department.toString());
                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(QuestionsNw.this);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();
        } else {
            Snackbar.with(QuestionsNw.this, null)
                    .type(Type.SUCCESS)
                    .message("No internet connection")
                    .duration(Duration.LONG)

                    .show();

        }
    }catch (Exception e){
            e.printStackTrace();
        }

    }

    public  void departmnt(final String place)
    {
        //  progress.setVisibility(ProgressBar.VISIBLE);
        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
        boolean i = networkCheckingClass.ckeckinternet();

        if (i) {
            // progress.setVisibility(ProgressBar.VISIBLE)
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_DEPT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            //  progress.setVisibility(ProgressBar.GONE);

                            // JSONObject jsonObj = null;
                            //  jsonObj = new JSONObject(response);
                            System.out.println("responseeeee_________department" + response);
                            // statusd = jsonObj.getString("status");


                            // pd.dismiss();
                            if(response != null && !response.isEmpty() && !response.equals("null"))

                            {  try {

                                JSONArray jsonarray = new JSONArray(response);
                                System.out.println("00");
                                ArrayList<String> department_name=new ArrayList<>();
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                   /* String department_id=jsonobject.getString("deptId");
                                    String divId=jsonobject.getString("divId");*/
                                    String deptName=jsonobject.getString("deptName");
                                    department_name.add(deptName);



                                }
                                stockArr  = new String[department_name.size()];
                                stockArr= department_name.toArray(stockArr);
                                //  spinner.setItems(stockArr);
                                System.out.println("departmentname.stockArr............."+stockArr);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (QuestionsNw.this, R.layout.customautocompleteview, R.id.autoCompleteItem,stockArr);
                                actv.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Snackbar.with(QuestionsNw.this,null)
//                                    .type(Type.ERROR)
//                                    .message(error.toString())
//                                    .duration(Duration.LONG)
//
//                                    .show();
////
//                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

//meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass


                    params.put("branch_id",location);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();




        } else {
            Snackbar.with(QuestionsNw.this,null)
                    .type(Type.ERROR)
                    .message("No internet connection")
                    .duration(Duration.LONG)

                    .show();

        }
    }
    @Override
    public void onBackPressed() {


        new MaterialStyledDialog.Builder(QuestionsNw.this)
                .setTitle("EXIT/CONTINUE")
                .setDescription("Do you want to Continue with feedback")
                .setStyle(Style.HEADER_WITH_TITLE)
                // .withDialogAnimation(true)
                .setHeaderColor(R.color.colorPrimary)
                .setCancelable(true)
                .setPositive("Yes", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(final MaterialDialog dialog, DialogAction which) {

                        dialog.dismiss();





                    }
                })
                .setNegative("No", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                        intent.putExtra("EXIT", true);
//                        startActivity(intent);
// dialog.dismiss();
//                        finish();


                        sb_department.setLength(0);
                        sb_answer.setLength(0);
                        sb_overall_satisfcation.setLength(0);
                        sb_questionid.setLength(0);
                        sb_poor_department.setLength(0);
                        sb_suggestions.setLength(0);

                                            deleteuser();

                    }
                })
                .show();

    }
    public  void deleteuser()
    {
//        NetworkCheckingClass networkCheckingClass = new NetworkCheckingClass(getApplicationContext());
//        boolean i = networkCheckingClass.ckeckinternet();
//
//        if (i) {
//            progress.setVisibility(ProgressBar.VISIBLE);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_DELETE1,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(final String response) {
//                            //  progress.setVisibility(ProgressBar.GONE);
//
//                            // JSONObject jsonObj = null;
//                            //  jsonObj = new JSONObject(response);
//                            System.out.println("responseeeee_________delete" + response);
//                            // statusd = jsonObj.getString("status");
//
//
//                            // pd.dismiss();
//                            if(response!=null)
//
//                            {  //try {
//
//
//
//                                if (response.contentEquals("\"success\""))
//                                {

        progress.setVisibility(ProgressBar.INVISIBLE);
        /*SharedPreferences preferences1 = getSharedPreferences("MyPref_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences1.edit();
        //  editor.putString("log_status", "logout");
        editor.putString("user_id", null);
        editor.clear();
        editor.apply();
        editor.commit();
        Intent is = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(is);*/
        finish();
        //  String named = jsonObj.getString("name");

        //   pd.dismiss();

//                                }
//                              //  JSONArray jsonarray = new JSONArray(response);
//                                System.out.println("00");
//
//
//                              //  ArrayList<String> department_name=new ArrayList<>();
////                                for (int i = 0; i < jsonarray.length(); i++) {
//////                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//////                                    String department_id=jsonobject.getString("deptId");
//////                                    String divId=jsonobject.getString("divId");
//////                                    String deptName=jsonobject.getString("deptName");
//////                                    department_name.add(deptName);
////
////
////
////                                }
////                                stockArr  = new String[department_name.size()];
////                                stockArr= department_name.toArray(stockArr);
////                                //  spinner.setItems(stockArr);
////                                System.out.println("departmentname.stockArr............."+stockArr);
////
////                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
////                                        (QuestionsNw.this,R.layout.new_auto_inflate,stockArr);
////                                actv.setAdapter(adapter);
//
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
//
//
//
//                            }
//
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
////                            Snackbar.with(QuestionsNw.this,null)
////                                    .type(Type.ERROR)
////                                    .message(error.toString())
////                                    .duration(Duration.LONG)
////
////                                    .show();
//////
////                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//
////meridian.net.in/demo/etsdc/response.php?fid=1&email=" + email + "&phone=" + phon + "&name=" + fulnam + "&occupation=" + occ + "&location=" + loc + "&password=" + pass
//
//
//                    params.put("user_id",new_user_id);
//
//                    return params;
//                }
//
//            };
//
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            int socketTimeout = 30000;//30 seconds - change to what you want
//            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//            stringRequest.setRetryPolicy(policy);
//            requestQueue.add(stringRequest);
//            requestQueue.getCache().clear();
//
//
//
//
//        } else {
//            Snackbar.with(QuestionsNwnw.this,null)
//                    .type(Type.ERROR)
//                    .message("No internet connection")
//                    .duration(Duration.LONG)
//
//                    .show();
//
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("on destroyyyyyy");

        sb_department.setLength(0);
        sb_answer.setLength(0);
        sb_overall_satisfcation.setLength(0);
        sb_questionid.setLength(0);
        sb_poor_department.setLength(0);
        sb_suggestions.setLength(0);

        deleteuser();
    }
//


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        System.out.println("on destroyyyyyy");

        sb_department.setLength(0);
        sb_answer.setLength(0);
        sb_overall_satisfcation.setLength(0);
        sb_questionid.setLength(0);
        sb_poor_department.setLength(0);
        sb_suggestions.setLength(0);

        deleteuser();
    }

    @Override
    protected void onStop() {



        super.onStop();
        System.out.println("on stopppp");

//        sb_department.setLength(0);
//        sb_answer.setLength(0);
//        sb_overall_satisfcation.setLength(0);
//        sb_questionid.setLength(0);
//        sb_poor_department.setLength(0);
//        sb_suggestions.setLength(0);
//
//        deleteuser();
    }


}
