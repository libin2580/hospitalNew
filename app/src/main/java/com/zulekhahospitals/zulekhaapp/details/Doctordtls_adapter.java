package com.zulekhahospitals.zulekhaapp.details;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.doctors.Doctor_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 11/8/2016.
 */
public class Doctordtls_adapter extends RecyclerView.Adapter<Doctordtls_adapter.ViewHolder> {


    List<Doctor_Model> zdrm;
    Context context;



    public Doctordtls_adapter(ArrayList<Doctor_Model> zdrm, Context context) {
        this.zdrm = zdrm;
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

            ///  tv=   (TextView) itemView.findViewById(R.id.title);
            tv1=   (TextView) itemView.findViewById(R.id.textView8);
            tv2=   (TextView) itemView.findViewById(R.id.textView11);
            tv3=   (TextView) itemView.findViewById(R.id.textView10);

            v=   (ImageView) itemView.findViewById(R.id.imageView8);
        }
    }


    @Override
    public int getItemCount() {
        return zdrm.size();
    }



    @Override
    public Doctordtls_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctor_dtls_btm_layout, viewGroup, false);
        Doctordtls_adapter.ViewHolder pvh = new Doctordtls_adapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final Doctordtls_adapter.ViewHolder personViewHolder, final int i) {

        System.out.println(">>>>>>idddddddddddddddddddddddddddddddd>>>>>>>>>" +zdrm.get(i).getDoctname());
        System.out.println(">>>>>>nameeeeeeeeeeeeeeeeeeeeeeeeeee>>>>>>>>>" +zdrm.get(i).getDocqual());
        String s = zdrm.get(i).getDocphoto();
        String d=s.replaceAll(" ","" +
                "%20");
        Typeface myFont2 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");
        personViewHolder.tv1.setTypeface(myFont2);
        personViewHolder.tv1.setText(zdrm.get(i).getDoctname());
        Typeface myFont3 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");
        personViewHolder.tv2.setTypeface(myFont3);
        personViewHolder.tv2.setText(zdrm.get(i).getDocqual());
        Typeface myFont4 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");
        personViewHolder.tv3.setTypeface(myFont4);
        personViewHolder.tv3.setText(zdrm.get(i).getDocspec());

        //  String img=enm.get(i).getNews_img();
        System.out.println("ooo" + d);
        Picasso.with(context).load("http://zulekhahospitals.com/uploads/doctor/"+d).noFade()  .noFade().transform(new CircleTransform()).into(personViewHolder.v);
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
