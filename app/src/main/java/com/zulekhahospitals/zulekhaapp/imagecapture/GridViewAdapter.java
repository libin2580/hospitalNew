package com.zulekhahospitals.zulekhaapp.imagecapture;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.zulekhahospitals.zulekhaapp.R;

import java.util.ArrayList;

/**
 * Created by Rashid on 11/10/2016.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList();
    String[] fileLoc;
    String[] imgName;
    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        super(context,layoutResourceId,data);
        this.layoutResourceId=layoutResourceId;
        this.context=context;
        this.data=data;
    }




    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        ViewHolder holder=null;
        int i=0;
        fileLoc=new String[data.size()];
        imgName=new String[data.size()];
        for(ImageItem im:data)
        {

            fileLoc[i]=im.getImage();
            imgName[i]=im.getImg_name();
            i++;
        }

        if(row==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new ViewHolder();
            holder.image=(ImageView)row.findViewById(R.id.imgView2);
            row.setTag(holder);

        }
        else{
            holder=(ViewHolder)row.getTag();
        }






    System.out.println("###### in adapter image : "+fileLoc[position]);
    BitmapFactory.Options options1 = new BitmapFactory.Options();
        options1.inSampleSize = 8;
    Bitmap bitmap2 = BitmapFactory.decodeFile(fileLoc[position], options1);
    holder.image.setImageBitmap(bitmap2);

        return row;
    }

    static class ViewHolder{

        ImageView image;
    }
}
