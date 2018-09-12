package com.zulekhahospitals.zulekhaapp.newintro;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.zulekhahospitals.zulekhaapp.R;


import java.util.ArrayList;

/**
 * Created by Rashid on 10/18/2016.
 */
public class New_ViewPagerAdapter extends PagerAdapter {
     Context context;
     ArrayList<ZayedModel> imageURL;

    public New_ViewPagerAdapter(Context ReceivedContext, ArrayList<ZayedModel> ReceivedImageURL)
    {
        this.context=ReceivedContext;
        this.imageURL=ReceivedImageURL;
    }

    @Override
    public int getCount() {
        return imageURL.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView=inflater.inflate(R.layout.new_viewpager_item,container,false);

        imgView=(ImageView)itemView.findViewById(R.id.imageView3);

        System.out.println("#################################################################################");
        System.out.println("URI in PAGE ADAPTER : "+ Uri.parse(imageURL.get(position).getUrl()));
        System.out.println("#################################################################################");

        Picasso.with(context)
                .load(imageURL.get(position).getUrl())
                .fit()
                .into(imgView);



        ((ViewPager)container).addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((RelativeLayout)object);
    }

}
