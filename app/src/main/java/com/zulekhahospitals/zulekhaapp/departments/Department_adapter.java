package com.zulekhahospitals.zulekhaapp.departments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.doctors.Doctor_Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by libin on 9/29/2016.
 */
public class Department_adapter extends RecyclerView.Adapter<Department_adapter.ViewHolder> {


    List<Department_Model>dsm;
    Context context;
    private int lastPosition = -1;


    public Department_adapter(ArrayList<Department_Model> dsm, Context context) {

        this.dsm = dsm;
//in alphabetical order --start

        Collections.sort(this.dsm, new Comparator<Department_Model>() {
            @Override
            public int compare(Department_Model department_model, Department_Model t1) {
                return department_model.deptName.compareToIgnoreCase(t1.deptName);
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
    public Department_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.department_item_layout, viewGroup, false);
        Department_adapter.ViewHolder pvh = new Department_adapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final Department_adapter.ViewHolder personViewHolder, final int i) {


        String s = dsm.get(i).getDepartment_image();
        String x=dsm.get(i).getDeptName();
        String z=x.replaceAll(" ","\n");
   personViewHolder.tv.setText(z);

        //  String img=enm.get(i).getNews_img();
        System.out.println("ooo" + s);
        Picasso.with(context).load(s).noFade().into(personViewHolder.v);
        // Here you apply the animation when the view is bound
        /*setAnimation(personViewHolder.itemView, i);*/
        Animation animation = AnimationUtils.loadAnimation(context,
                (i > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        personViewHolder.itemView.startAnimation(animation);
        lastPosition = i;

    }

   /* public void setFilter(List<Department_Model> countryModels){
        dsm = new ArrayList<>();
        dsm.addAll(countryModels);
        notifyDataSetChanged();
    }*/
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    void setViews( int pos,String brch_id){
        String dep_id=dsm.get(pos).getDeptId();
        String dep_nme=dsm.get(pos).getDeptName();
        Intent i=new Intent(context,Doctor_Fragment.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("dep_id",dep_id);
        i.putExtra("dep_nme",dep_nme);
        i.putExtra("branch_id",brch_id);
        context.startActivity(i);
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