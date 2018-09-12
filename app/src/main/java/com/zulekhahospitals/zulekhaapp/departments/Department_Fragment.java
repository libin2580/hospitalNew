package com.zulekhahospitals.zulekhaapp.departments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.utility.DetectConnection;
import com.zulekhahospitals.zulekhaapp.utility.RecyclerItemClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.URL2;
import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

/**
 * Created by libin on 9/29/2016.
 */

public class Department_Fragment extends Activity implements SearchView.OnQueryTextListener {
    FrameLayout container;
    FragmentManager fragmentManager;
    String tag = "events";
    Fragment fragment;
    View view;
    RecyclerView recyclerview,recyclerview2;
    LinearLayout dubai_tab_layout,sharjah_tab_layout,zmc_tab_layout;
    TextView dept_label;
    SwipeRefreshLayout swiperefreshlayout;
    EditText search_edit_text;
    LinearLayout search_button,close_button,floatind_edit_linearlayout,dep_linearlayout;
    FloatingSearchView floating_search_view;
  //  static ArrayList<> upm;
    Department_adapter adapter;
    DeptPlusDoctrDetailsAdapter DPDadapter;
    ProgressBar progress;
    String result,result2, galry_image,Dat;
    int key_flag=0;
    static ArrayList<Department_Model> dsm;
    ArrayList<DeptPlusDoctrDetailsModel>DeptPlusDoctrArrayList;

    Department_Model dm;
    DeptPlusDoctrDetailsModel dpdm;
    String k,sponsor_id,event_id,name,email,phone,photo,address,website,content;
    String REGISTER_URL="http://zulekhahospitals.com/mobileapp/departments.php";
    String deptId,divId,deptName,department_image;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_fragment_layout);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Department_Fragment.this.goBack();
            }
        });
        analytics = FirebaseAnalytics.getInstance(Department_Fragment.this);
        analytics.setCurrentScreen(Department_Fragment.this,Department_Fragment.this.getLocalClassName(), null /* class override */);

        Intent intent = getIntent();
        k=intent.getStringExtra("br_id");
        System.out.println("braaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+k);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerview2 = (RecyclerView)findViewById(R.id.recyclerview2);

        swiperefreshlayout=(SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setColorSchemeResources(
                R.color.blue);

        dept_label=(TextView) findViewById(R.id.textView20);
        floating_search_view=(FloatingSearchView)findViewById(R.id.floating_search_view);
        search_button=(LinearLayout)findViewById(R.id.search_button);
        close_button=(LinearLayout)findViewById(R.id.close_button);
        dep_linearlayout=(LinearLayout)findViewById(R.id.dep_linearlayout);
        floatind_edit_linearlayout=(LinearLayout)findViewById(R.id.floatind_edit_linearlayout);

        dep_linearlayout.setVisibility(View.VISIBLE);
        search_button.setVisibility(View.VISIBLE);
        floatind_edit_linearlayout.setVisibility(View.GONE);
        close_button.setVisibility(View.GONE);
        dept_label.setVisibility(View.VISIBLE);
        floating_search_view.setVisibility(View.GONE);
        recyclerview.setHasFixedSize(true);
        recyclerview2.setHasFixedSize(true);
        Context context=getApplicationContext();
       // LinearLayoutManager llm= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager llm = new LinearLayoutManager(context);
/*        llm.setOrientation(LinearLayoutManager.HORIZONTAL);*/
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(llm);

        LinearLayoutManager llm2 = new LinearLayoutManager(context);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview2.setLayoutManager(llm2);

        dubai_tab_layout=(LinearLayout)findViewById(R.id.dubai_tab_layout);
        sharjah_tab_layout=(LinearLayout)findViewById(R.id.sharjah_tab_layout);
        zmc_tab_layout=(LinearLayout)findViewById(R.id.zmc_tab_layout);



        SharedPreferences preferencesd= getSharedPreferences("MyPref1", MODE_PRIVATE);
        String str_branch=preferencesd.getString("branch",null);
        String k_branch=preferencesd.getString("branchK",null);

        System.out.println("<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

        System.out.println("branch inside LoginActivityFeedBack : "+k_branch);
        try {
            if (str_branch != null) {

                if (str_branch.equalsIgnoreCase("dubai")){
                    System.out.println("<<<<<<<<<<11111111111>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

                    dubai_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                }
                else if(str_branch.equalsIgnoreCase("Sharjah")){
                    System.out.println("<<<<<<<<<<2222222222222>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

                    sharjah_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                }
                else if(str_branch.equalsIgnoreCase("zmc")){
                    System.out.println("<<<<<<<<<<33333333333>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

                    zmc_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (k_branch != null) {

                if (k_branch.equalsIgnoreCase("dubai")){
                    System.out.println("<<<<<<<<<<11111111111>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

                    dubai_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                }
                else if(k_branch.equalsIgnoreCase("Sharjah")){
                    System.out.println("<<<<<<<<<<2222222222222>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

                    sharjah_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                }
                else if(k_branch.equalsIgnoreCase("zmc")){
                    System.out.println("<<<<<<<<<<33333333333>>>>>>>>>>>>>>>>>>>>>>>>: "+str_branch);

                    zmc_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        dubai_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dubai_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                sharjah_tab_layout.setBackgroundResource(0);
                zmc_tab_layout.setBackgroundResource(0);
                k="1";
                FetchDepartments(k,0);
            }
        });

        sharjah_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dubai_tab_layout.setBackgroundResource(0);
                sharjah_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                zmc_tab_layout.setBackgroundResource(0);
                k="2";
                FetchDepartments(k,0);
            }
        });

        zmc_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dubai_tab_layout.setBackgroundResource(0);
                sharjah_tab_layout.setBackgroundResource(0);
                zmc_tab_layout.setBackground(getResources().getDrawable(R.drawable.tab_backgroung));
                k="33";
                FetchDepartments(k,0);
            }
        });


        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchDepartments(k,1);

            }
        });


        if (DetectConnection
                .checkInternetConnection(getApplicationContext())) {

            FetchDepartments(k,0);
        } else {



            Snackbar.with(Department_Fragment.this,null)
                    .type(Type.ERROR)
                    .message("No Internet Connection!")
                    .duration(Duration.LONG)

                    .show();

        }


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new getDeptPlusDoctrDetails().execute();



                search_button.setVisibility(View.GONE);
                close_button.setVisibility(View.VISIBLE);


                dep_linearlayout.setVisibility(View.GONE);
                floatind_edit_linearlayout.setVisibility(View.VISIBLE);


                floating_search_view.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
                recyclerview2.setVisibility(View.VISIBLE);




            }
        });


        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*new DownloadData1().execute();*/
                FetchDepartments(k,0);


                search_button.setVisibility(View.VISIBLE);
                close_button.setVisibility(View.GONE);


                dep_linearlayout.setVisibility(View.VISIBLE);
                floatind_edit_linearlayout.setVisibility(View.GONE);


                floating_search_view.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
                recyclerview2.setVisibility(View.GONE);
            }
        });
        //recyclerview.setLayoutManager(layoutManager);
        floating_search_view.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                final List<DeptPlusDoctrDetailsModel> filteredModelList = filter(DeptPlusDoctrArrayList, newQuery);
                DPDadapter.setFilter(filteredModelList);

                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("inside setOnQueryChangeListener");
                for(DeptPlusDoctrDetailsModel ddm:filteredModelList)
                {
                    System.out.println(ddm.getName()+" id : "+ddm.getId()+" key : "+ddm.getKey());
                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        });


    }



    private void goBack() {
        super.onBackPressed();
    }

    public void showProgressbar(){
        progress.setVisibility(ProgressBar.VISIBLE);
    }

    private void addBottomDots(int i) {

    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        final List<DeptPlusDoctrDetailsModel> filteredModelList = filter(DeptPlusDoctrArrayList, newText);
        DPDadapter.setFilter(filteredModelList);

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("inside setOnQueryChangeListener");
        for(DeptPlusDoctrDetailsModel ddm:filteredModelList)
        {
            System.out.println(ddm.getName()+" id : "+ddm.getId()+" key : "+ddm.getKey());
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private List<DeptPlusDoctrDetailsModel> filter(List<DeptPlusDoctrDetailsModel> models, String query) {
        query = query.toLowerCase();

        final List<DeptPlusDoctrDetailsModel> filteredModelList = new ArrayList<>();
        for (DeptPlusDoctrDetailsModel model1 : models) {
            final String text = model1.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model1);
            }
        }
        return filteredModelList;
    }

    public void FetchDepartments(String branch_id, final int isswiped) {

        k=branch_id;
         class DownloadData1 extends AsyncTask<Void, Void, Void> {

            ProgressDialog pd = null;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (isswiped == 0)
                    progress.setVisibility(ProgressBar.VISIBLE);
                else
                    swiperefreshlayout.setRefreshing(true);

            }

            @Override
            protected Void doInBackground(Void... params) {


                try {


                    HttpClient httpclient = new DefaultHttpClient();

System.out.println("http://zulekhahospitals.com/mobileapp/departments.php?branch_id=" + k);
                    HttpPost httppost = new HttpPost(URL2+"departments.php?branch_id="+k
                    );
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    InputStream is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();


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
                if (isswiped == 0)
                    progress.setVisibility(ProgressBar.GONE);
                else
                    swiperefreshlayout.setRefreshing(false);


                String sam = result.trim();
                System.out.println(">>>>>>>>>>>>>>>" + result);
                System.out.println(">>>>>>>>>>>>>>>" + sam);
                String value = result;
                if
                        (result.equalsIgnoreCase("[]")) {
               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                    Snackbar.with(Department_Fragment.this, null)
                            .type(Type.ERROR)
                            .message("No Events!")
                            .duration(Duration.LONG)

                            .show();
                } else if (result != null && !result.isEmpty() && !result.equals("null")) {
                    JSONArray mArray;
                    dsm = new ArrayList<Department_Model>();
                    try {
                        mArray = new JSONArray(result);
                        for (int i = 0; i < mArray.length(); i++) {
                            dm = new Department_Model();
                            JSONObject mJsonObject = mArray.getJSONObject(i);
                            //3 Log.d("OutPut", mJsonObject.getString("image"));

                            //  String ,,,,,,,;
                            deptId = mJsonObject.getString("deptId");
                            divId = mJsonObject.getString("divId");
                            deptName = mJsonObject.getString("deptName");
                            department_image = mJsonObject.getString("department_image");
                            dm.setDeptId(deptId);
                            dm.setDivId(divId);
                            dm.setDeptName(deptName);
                            dm.setDepartment_image(department_image);
                            dsm.add(dm);

                        /*recyclerview.setAdapter(adapter);*/
                            adapter = new Department_adapter(dsm, getApplicationContext());
                            recyclerview.setAdapter(adapter);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    recyclerview.addOnItemTouchListener(
                            new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    if (DetectConnection
                                            .checkInternetConnection(getApplicationContext())) {
                                        adapter.setViews(position, k);
                                    } else {


                                        Snackbar.with(Department_Fragment.this, null)
                                                .type(Type.ERROR)
                                                .message("No Internet Connection!")
                                                .duration(Duration.LONG)

                                                .show();

                                    }


                                }
                            })
                    );
                } else {

                    System.out.println("nulllllllllllllllllllllllllllllllll");
                }


            }
        }
new DownloadData1().execute();
    }


    private class getDeptPlusDoctrDetails extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {


            try {


                HttpClient httpclient = new DefaultHttpClient();


                HttpPost httppost = new HttpPost(URL2+"search-doct.php?branch_id="+k
                );
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();


                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {

            progress.setVisibility(ProgressBar.GONE);
            String sam = result.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result;
            if
                    (result.equalsIgnoreCase("[]")) {
               /* Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                Snackbar.with(Department_Fragment.this,null)
                        .type(Type.ERROR)
                        .message("No Data!")
                        .duration(Duration.LONG)

                        .show();
            } else if(result != null && !result.isEmpty() && !result.equals("null")) {
                JSONArray mArray;
                DeptPlusDoctrArrayList = new ArrayList<DeptPlusDoctrDetailsModel>();
                try {
                    mArray = new JSONArray(result);
                    for (int i = 0; i < mArray.length(); i++) {
                        dpdm = new DeptPlusDoctrDetailsModel();
                        JSONObject mJsonObject = mArray.getJSONObject(i);
                        //3 Log.d("OutPut", mJsonObject.getString("image"));

                        //  String ,,,,,,,;
                        dpdm.setId(mJsonObject.getString("id"));
                        dpdm.setName(mJsonObject.getString("name"));
                        dpdm.setKey(mJsonObject.getString("key"));


                        DeptPlusDoctrArrayList.add(dpdm);


                        DPDadapter = new DeptPlusDoctrDetailsAdapter(DeptPlusDoctrArrayList,Department_Fragment.this);
                        recyclerview2.setAdapter(DPDadapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                recyclerview2.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                if (DetectConnection
                                        .checkInternetConnection(getApplicationContext())) {

                                    System.out.println("touched key value : "+DeptPlusDoctrArrayList.get(position).getKey());

                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                DPDadapter.checkWhichView(position,k);



                                } else {


                                    Snackbar.with(Department_Fragment.this,null)
                                            .type(Type.ERROR)
                                            .message("No Internet Connection!")
                                            .duration(Duration.LONG)

                                            .show();

                                }


                            }
                        })
                );
            }
            else{

                System.out.println("nulllllllllllllllllllllllllllllllll");
            }


        }
    }


}
