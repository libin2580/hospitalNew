package com.zulekhahospitals.zulekhaapp.departments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.details.Doctor_Desc_Fragment;
import com.zulekhahospitals.zulekhaapp.doctors.Doctor_Fragment;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Rashid on 3/14/2017.
 */

public class DeptPlusDoctrDetailsAdapter extends RecyclerView.Adapter<DeptPlusDoctrDetailsAdapter.ViewHolder> {

String result2;
    List<DeptPlusDoctrDetailsModel> dsm;
    Context context;
    String result;
    ProgressDialog pd;
    ProgressDialog pDlog2;

    private int lastPosition = -1;


    public DeptPlusDoctrDetailsAdapter(ArrayList<DeptPlusDoctrDetailsModel> dsm, Context context) {

        this.dsm = dsm;
//in alphabetical order --start
        System.out.println("in addapter dsm");
        for(DeptPlusDoctrDetailsModel ff:dsm){
            System.out.println(ff.getName());
        }

        Collections.sort(this.dsm, new Comparator<DeptPlusDoctrDetailsModel>() {
            @Override
            public int compare(DeptPlusDoctrDetailsModel department_model, DeptPlusDoctrDetailsModel t1) {
                return department_model.name.compareToIgnoreCase(t1.name);
            }
        });

//in alphabetical order --end

        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cv1;
        // CardView cv;
        TextView speakr, topic, div, dept, evntnam;
        String imag;
        ImageView v;
        TextView tv,tv1,tv2,tv3;
        // TextView personAge;

        ImageView personPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            //   cv1 = ( LinearLayout) itemView.findViewById(R.id.cv_crc);

            tv=   (TextView) itemView.findViewById(R.id.title);
           /* tv1=   (TextView) itemView.findViewById(R.id.evnt_frm);
            tv2=   (TextView) itemView.findViewById(R.id.evnt_to);
            tv3=   (TextView) itemView.findViewById(R.id.evnt_vnue);
         ;*/
            v=   (ImageView) itemView.findViewById(R.id.imageView8);

        }
    }


    @Override
    public int getItemCount() {
        return dsm.size();
    }


    @Override
    public DeptPlusDoctrDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.department_item_layout, viewGroup, false);
        DeptPlusDoctrDetailsAdapter.ViewHolder pvh = new DeptPlusDoctrDetailsAdapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final DeptPlusDoctrDetailsAdapter.ViewHolder personViewHolder, final int i) {
        String x=dsm.get(i).getName();
        String z=x.replaceAll(" ","\n");
        personViewHolder.tv.setText(z);
        /*setAnimation(personViewHolder.itemView, i);*/
        Animation animation = AnimationUtils.loadAnimation(context,
                (i > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        personViewHolder.itemView.startAnimation(animation);
        lastPosition = i;
    }

    public void setFilter(List<DeptPlusDoctrDetailsModel> countryModels){
        dsm = new ArrayList<>();
        dsm.addAll(countryModels);
        notifyDataSetChanged();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    void setViewsDepartment( int pos,String brch_id){
    System.out.println("||||||||||||||||||||||| inside setViewsDepartment |||||||||||||||||||||||||||||");

            Intent i=new Intent(context,Doctor_Fragment.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("dep_id",dsm.get(pos).getId());
            i.putExtra("dep_nme",dsm.get(pos).getName());
            i.putExtra("branch_id",brch_id);
            context.startActivity(i);

    }

    void setViewsDoctor(int pos){

        System.out.println("||||||||||||||||||||||| inside setViewsDoctor |||||||||||||||||||||||||||||");
        System.out.println("position in adapter : "+pos);
        System.out.println("id : "+dsm.get(pos).getId());
        new getDoctorDetails(dsm.get(pos).getId()).execute();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.enter);
        viewToAnimate.startAnimation(animation);
        lastPosition = position;
        }
    }

    public class getDoctorDetails extends AsyncTask<Void, Void, Void> {
        String doc_id,dep_name,department_id;
        public getDoctorDetails(String d_id){
            doc_id=d_id;
           /* dep_name=d_name;*/
           /* department_id=dep_id;*/
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*pDlog2 = new ProgressDialog(context,R.style.MyTheme);

            pDlog2.setCancelable(false);
            pDlog2.show();*/

            ((Activity)context).findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
//http://zulekhahospitals.com/json-cme.php?fid=103&txtemail=rajeesh@meridian.net.in&password=123456&user_type=Synapse%20User

            try {


                //String gh=us_typ.replaceAll(" ","%20");
//                System.out.println(">>>>>>sss>>>>>>>>>" + s);
//                System.out.println(">>>>>>>ttt>>>>>>>>" + t);
//                //System.out.println(">>>>>>>vvv>>>>>>>>" + v);
//                System.out.println(">>>>>>>gh>>>>>>>>" +gh);
                HttpClient httpclient = new DefaultHttpClient();

                System.out.println("http://zulekhahospitals.com/mobileapp/doctorbyid.php?doctbybid="+doc_id);
                HttpPost httppost = new HttpPost("http://zulekhahospitals.com/mobileapp/doctorbyid.php?doctbybid="+doc_id);
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
                result2 = sb.toString();


                // Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Loading connection  :", e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void args) {
            /*if(pd.isShowing())
                pd.dismiss();*/
           /* progress.setVisibility(ProgressBar.GONE);*/
            String sam = result2.trim();
            System.out.println(">>>>>>>>>>>>>>>" + result2);
            System.out.println(">>>>>>>>>>>>>>>" + sam);
            String value = result2;
            if
                    (result2.equalsIgnoreCase("[]")) {
                /*Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT).show();*/
                Snackbar.with(context,null)
                        .type(Type.ERROR)
                        .message("No Events!")
                        .duration(Duration.LONG)

                        .show();
            }
            else if(result2 != null && !result2.isEmpty() && !result2.equals("null")) {
                JSONArray mArray;

                try {
                    System.out.println("result2 : "+result2);

                    mArray = new JSONArray(result2);
                    for (int i = 0; i < mArray.length(); i++) {

                        JSONObject mJsonObject = mArray.getJSONObject(i);
                        // Log.d("OutPut", mJsonObject.getString("image"));


                        Intent i2 = new Intent(context, Doctor_Desc_Fragment.class);
                        //i2.putExtra("dep_id",j);
                        i2.putExtra("dc_id",mJsonObject.getString("docid"));
                        i2.putExtra("dc_nm",mJsonObject.getString("docname"));
                        i2.putExtra("dc_ql",mJsonObject.getString("docqual"));
                        i2.putExtra("dc_sp",mJsonObject.getString("docspec"));
                        i2.putExtra("dc_pt",mJsonObject.getString("docphoto"));
                        i2.putExtra("dc_ex",mJsonObject.getString("docexp"));
                        i2.putExtra("dc_oth",mJsonObject.getString("docother"));
                        i2.putExtra("dep_id",mJsonObject.getString("docdeptid"));
                        i2.putExtra("dep_name",mJsonObject.getString("deptName"));
                        i2.putExtra("branch_id",mJsonObject.getString("docdivid"));
                        i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i2);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            else{

                System.out.println("nulllllllllllllllllllllllllllllllll");
            }
            ((Activity)context).findViewById(R.id.progress_bar).setVisibility(View.GONE);
           /* if(pDlog2.isShowing())
                pDlog2.dismiss();*/
        }
    }
    public void checkWhichView(int posi,String k){

        if(dsm.get(posi).getKey().equalsIgnoreCase("doctor")){
setViewsDoctor(posi);
        }
        else {
            setViewsDepartment(posi,k);
        }

    }
    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}