package com.zulekhahospitals.zulekhaapp.insurance;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zulekhahospitals.zulekhaapp.R;

import java.util.ArrayList;

/**
 * Created by Anvin on 12/6/2017.
 */

public class InsurenceTrackingAdapter extends RecyclerView.Adapter<InsurenceTrackingAdapter.ViewHolder> {
    ArrayList<InsurenceTrackingModel> arrayValues;
    Context context;

    public InsurenceTrackingAdapter(ArrayList<InsurenceTrackingModel> values, Context context) {
        this.arrayValues = values;

        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tracking_number,txt_approval_status,txt_service_name,txt_remarks,txt_validity,txt_approval_limit,txt_service_cost;
        ViewHolder(View itemView) {
            super(itemView);
            txt_tracking_number=(TextView)itemView.findViewById(R.id.txt_tracking_number);

            txt_approval_status=(TextView)itemView.findViewById(R.id.txt_approval_status);
            txt_service_name=(TextView)itemView.findViewById(R.id.txt_service_name);
            txt_remarks=(TextView)itemView.findViewById(R.id.txt_remarks);
            txt_validity=(TextView)itemView.findViewById(R.id.txt_validity);
            txt_approval_limit=(TextView)itemView.findViewById(R.id.txt_approval_limit);
            txt_service_cost=(TextView)itemView.findViewById(R.id.txt_service_cost);
        }
    }


    @Override
    public int getItemCount() {
        return arrayValues.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_insurence_tracking, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder personViewHolder, final int i) {
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_tracking_number.setTypeface(tf1);
        personViewHolder.txt_tracking_number.setText("Tracking Number : "+arrayValues.get(i).getTracking_no());

        Typeface tf2 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_approval_status.setTypeface(tf2);
        personViewHolder.txt_approval_status.setText("Approval Status : "+arrayValues.get(i).getApproval_status());


        Typeface tf3 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_service_name.setTypeface(tf3);
        personViewHolder.txt_service_name.setText(arrayValues.get(i).getService_name());

        Typeface tf4 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_remarks.setTypeface(tf4);
        personViewHolder.txt_remarks.setText(arrayValues.get(i).getRemarks());

        Typeface tf5 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_validity.setTypeface(tf5);
        personViewHolder.txt_validity.setText(""+arrayValues.get(i).getValidity());

        Typeface tf6 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_approval_limit.setTypeface(tf6);
        personViewHolder.txt_approval_limit.setText(""+arrayValues.get(i).getApproved_limit());

        Typeface tf7= Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        personViewHolder.txt_service_cost.setTypeface(tf7);
        personViewHolder.txt_service_cost.setText(arrayValues.get(i).getService_cost());


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