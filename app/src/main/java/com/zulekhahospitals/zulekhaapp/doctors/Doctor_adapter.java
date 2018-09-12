package com.zulekhahospitals.zulekhaapp.doctors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.details.Doctor_Desc_Fragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by libin on 9/29/2016.
 */
public class Doctor_adapter extends RecyclerView.Adapter<Doctor_adapter.ViewHolder> {


    List<Doctor_Model> zdrm;
    Context context;

    private int lastPosition = -1;

    public Doctor_adapter(ArrayList<Doctor_Model> zdrm, Context context) {
        this.zdrm = zdrm;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cv1;
        // CardView cv;
        TextView speakr, topic, div, dept, evntnam;
        String imag;
        RoundedImageView v;
        WebView tv,tv1,tv2,tv3;
        // TextView personAge;
        ScrollView childScroll;
        TextView doctor_name,doctor_quali,doctor_specl;
        ImageView personPhoto;
        ProgressBar gallery_progressbar;

        ViewHolder(View itemView) {
            super(itemView);
            //   cv1 = ( LinearLayout) itemView.findViewById(R.id.cv_crc);

            ///  tv=   (TextView) itemView.findViewById(R.id.title);
           /* tv1=   (WebView) itemView.findViewById(R.id.textView8);*/
          /*  tv2=   (WebView) itemView.findViewById(R.id.textView11);
            tv3=   (WebView) itemView.findViewById(R.id.textView10);*/

            v=   (RoundedImageView) itemView.findViewById(R.id.imageView8);
            /*childScroll=(ScrollView)itemView.findViewById(R.id.childScroll);*/
            gallery_progressbar=(ProgressBar) itemView.findViewById(R.id.gallery_progressbar);
            doctor_name=(TextView)itemView.findViewById(R.id.doctor_name);
            doctor_quali=(TextView)itemView.findViewById(R.id.doctor_quali);
            doctor_specl=(TextView)itemView.findViewById(R.id.doctor_specl);
        }
    }


    @Override
    public int getItemCount() {
        return zdrm.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctors_item_layout, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder personViewHolder, final int i) {

        System.out.println(">>>>>>idddddddddddddddddddddddddddddddd>>>>>>>>>" +zdrm.get(i).getDoctname());
        System.out.println(">>>>>>nameeeeeeeeeeeeeeeeeeeeeeeeeee>>>>>>>>>" +zdrm.get(i).getDocqual());
        String s = zdrm.get(i).getDocphoto();
        String d=s.replaceAll(" ","" +
                "%20");
        Typeface myFont2 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");
    /*  personViewHolder.tv1.setTypeface(myFont2);*/
            /* personViewHolder.tv1.(zdrm.get(i).getDoctname());*/
       /* WebSettings webSettings = personViewHolder.tv1.getSettings();
        webSettings.setDefaultFontSize(14);
        String content="<font size='4'>"+zdrm.get(i).getDoctname()+"</font><br/><br/>"+ zdrm.get(i).getDocqual()+"<br/><br/>"+zdrm.get(i).getDocspec();
        personViewHolder.tv1.setBackgroundColor(0x00000000);
        personViewHolder.tv1.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");
        Typeface myFont3 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");*/
        personViewHolder.doctor_name.setText(zdrm.get(i).getDoctname());
        personViewHolder.doctor_quali.setText(zdrm.get(i).getDocqual());
        personViewHolder.doctor_specl.setText(zdrm.get(i).getDocspec());
        System.out.println("ooo" + d);
        Picasso.with(context).load("http://zulekhahospitals.com/uploads/doctor/"+d).noFade().fit().into(personViewHolder.v, new Callback() {
            @Override
            public void onSuccess() {
                personViewHolder.gallery_progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
       /* personViewHolder.tv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
        /*setAnimation(personViewHolder.itemView, i);*/
        Animation animation = AnimationUtils.loadAnimation(context,
                (i > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        personViewHolder.itemView.startAnimation(animation);
        lastPosition = i;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setFilter(List<Doctor_Model> doctorModels){
        zdrm = new ArrayList<>();
        zdrm.addAll(doctorModels);
        notifyDataSetChanged();
    }
    void setViews( int pos,String k,String dep_nm,String branch_id){



        String dc_id=zdrm.get(pos).getDoctid();
        String dc_nm=zdrm.get(pos).getDoctname();
        String dc_ql=zdrm.get(pos).getDocqual();
        String dc_sp=zdrm.get(pos).getDocspec();
        String dc_pt=zdrm.get(pos).getDocphoto();
        String dc_ex=zdrm.get(pos).getDocexp();
        String dc_oth=zdrm.get(pos).getDocother();
        Intent i2 = new Intent(context, Doctor_Desc_Fragment.class);
        //i2.putExtra("dep_id",j);
        i2.putExtra("dc_id",dc_id);
        i2.putExtra("dc_nm",dc_nm);
        i2.putExtra("dc_ql",dc_ql);
        i2.putExtra("dc_sp",dc_sp);
        i2.putExtra("dc_pt",dc_pt);
        i2.putExtra("dc_ex",dc_ex);
        i2.putExtra("dc_oth",dc_oth);
        i2.putExtra("dep_id",k);
        i2.putExtra("dep_name",dep_nm);
        i2.putExtra("branch_id",branch_id);
        i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



       /* String dep_id=dsm.get(pos).getDeptId();
        String dep_nme=dsm.get(pos).getDeptName();
        Intent i=new Intent(context,Doctor_Fragment.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("dep_id",dep_id);
        i.putExtra("dep_nme",dep_nme);*/
        context.startActivity(i2);
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
    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}