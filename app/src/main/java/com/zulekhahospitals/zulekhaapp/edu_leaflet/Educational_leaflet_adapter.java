package com.zulekhahospitals.zulekhaapp.edu_leaflet;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zulekhahospitals.zulekhaapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by libin on 10/17/2016.
 */
public class Educational_leaflet_adapter extends RecyclerView.Adapter<Educational_leaflet_adapter.ViewHolder> {


    List<Educational_leaflet_Model> eem;
    //in alphabetical order --start
    private int lastPosition = -1;

//in alphabetical order --end


    Context context;



    public Educational_leaflet_adapter(ArrayList<Educational_leaflet_Model> eem, Context context) {
        this.eem = eem;
//in alphabetical order --start
        Collections.sort(this.eem, new Comparator<Educational_leaflet_Model>() {
            @Override
            public int compare(Educational_leaflet_Model educational_leaflet_model, Educational_leaflet_Model t1) {
                return educational_leaflet_model.depname.compareToIgnoreCase(t1.depname);
            }
        });

        this.context = context;
//in alphabetical order --end
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

            tv=   (TextView) itemView.findViewById(R.id.evt_typ);

           /* tv1=   (TextView) itemView.findViewById(R.id.evnt_frm);
            tv2=   (TextView) itemView.findViewById(R.id.evnt_to);
            tv3=   (TextView) itemView.findViewById(R.id.evnt_vnue);
         ;*/
           // v=   (ImageView) itemView.findViewById(R.id.imageView13);
        }
    }


    @Override
    public int getItemCount() {
        return eem.size();
    }


    @Override
    public Educational_leaflet_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exhibtors_item_layout, viewGroup, false);
        Educational_leaflet_adapter.ViewHolder pvh = new Educational_leaflet_adapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final Educational_leaflet_adapter.ViewHolder personViewHolder, final int i) {


        String s = eem.get(i).getDepname();
        Typeface myFont2 = Typeface.createFromAsset(context.getAssets(), "OpenSansLight.ttf");
        personViewHolder.tv.setTypeface(myFont2);
        personViewHolder.tv.setText(eem.get(i).getDepname());
        System.out.println("exhibtors" + s);
        /*String img=eem.get(i).getNews_img();
        System.out.println("ooo" + s);
        Picasso.with(context).load(img).resize(150, 150).into(personViewHolder.v);*/
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
    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

}