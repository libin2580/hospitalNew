package com.zulekhahospitals.zulekhaapp.edu_leaflet;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zulekhahospitals.zulekhaapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by libin on 10/3/2016.
 */
public class Educational_leafle_pdf_adapter extends RecyclerView.Adapter<Educational_leafle_pdf_adapter.ViewHolder> {


    List<Educational_leafle_pdf_Model> ehm;
    Context context;



    public Educational_leafle_pdf_adapter(ArrayList<Educational_leafle_pdf_Model> ehm, Context context) {
        this.ehm = ehm;
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
        ProgressBar progressbar;

        ViewHolder(View itemView) {
            super(itemView);
            //   cv1 = ( LinearLayout) itemView.findViewById(R.id.cv_crc);

          tv=   (TextView) itemView.findViewById(R.id.leaflets);
         //  tv1=   (TextView) itemView.findViewById(R.id.higlt);
          //  tv2=   (TextView) itemView.findViewById(R.id.evnt_to);
           // tv3=   (TextView) itemView.findViewById(R.id.evnt_vnue);

            v=   (ImageView) itemView.findViewById(R.id.imageView8);
            progressbar=(ProgressBar)itemView.findViewById(R.id.progressbar);
        }
    }


    @Override
    public int getItemCount() {
        return ehm.size();
    }


    @Override
    public Educational_leafle_pdf_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.highlight_item_layout, viewGroup, false);
        Educational_leafle_pdf_adapter.ViewHolder pvh = new Educational_leafle_pdf_adapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final Educational_leafle_pdf_adapter.ViewHolder personViewHolder, final int i) {


     String s = ehm.get(i).getLeaflets_cover();
        Typeface myFont2 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");
       personViewHolder.tv.setTypeface(myFont2);
     personViewHolder.tv.setText(ehm.get(i).getLeaflets());

        //  String img=enm.get(i).getNews_img();
        System.out.println("ooo" + s);
        Picasso.with(context)
                .load(s)
                .noFade()
                .into(personViewHolder.v, new Callback() {
                    @Override
                    public void onSuccess() {
                        personViewHolder.progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}