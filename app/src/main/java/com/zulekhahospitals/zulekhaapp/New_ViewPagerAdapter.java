package com.zulekhahospitals.zulekhaapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Rashid on 10/18/2016.
 */
public class New_ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<ZayedModel> imageURL;

    public New_ViewPagerAdapter(Context ReceivedContext, ArrayList<ZayedModel> ReceivedImageURL) {
        this.context = ReceivedContext;
        this.imageURL = ReceivedImageURL;
    }

    @Override
    public int getCount() {
        return imageURL.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
       final WebView imgView;
       final LinearLayout click_id;
        final ProgressBar gallery_progressbar;
       final View itemView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        itemView = inflater.inflate(R.layout.new_viewpager_item1, container, false);
        imgView = (WebView) itemView.findViewById(R.id.webview3);
        click_id=(LinearLayout) itemView.findViewById(R.id.click_id);
        // imgView=(ImageView)itemView.findViewById(R.id.imageView3);
        //gallery_progressbar=(ProgressBar)itemView.findViewById(R.id.gallery_progressbar);
        //progress_loader=(MKLoader)itemView.findViewById(R.id.progress_loader);
        System.out.println("#################################################################################");
        System.out.println("URI in PAGE ADAPTER : " + Uri.parse(imageURL.get(position).getUrls()));
        System.out.println("#################################################################################");
        imgView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(context,"hiiiiiii",Toast.LENGTH_LONG).show();
    }
});

     /*   Picasso.with(context)
                .load(imageURL.get(position).getUrl())
                .fit()
                .into(imgView);*/

       /* new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                .build()
                .load(imageURL.get(position).getUrl())
                .noFade()
                .fit()
                .into(imgView);*/
        try {
            System.out.println("URI in jjjjjjjjjjjjjjjjjjjjjjPAGE ADAPTER : " + Uri.parse(imageURL.get(position).getUrls()));
            imgView.getSettings().setJavaScriptEnabled(true);
            imgView.getSettings().setLoadsImagesAutomatically(true);

            imgView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


            //3  web.getSettings().setLoadsImagesAutomatically(true);
            // web.getSettings().setJavaScriptEnabled(true);
            imgView.getSettings().setAllowFileAccess(true);
            imgView.getSettings().setDomStorageEnabled(true);
            imgView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgView.getSettings().setLoadWithOverviewMode(true);
            imgView.getSettings().setUseWideViewPort(true);
            imgView.getSettings().setSupportZoom(true);
            imgView.loadUrl(imageURL.get(position).getIds());
            imgView.setHorizontalScrollBarEnabled(false);
            WebViewClient client = new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.d("MYAPP", "Page loaded");
                  //  progress.setVisibility(ProgressBar.GONE);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    // TODO Auto-generated method stub
                    super.onPageStarted(view, url, favicon);

                    //progress.setVisibility(ProgressBar.VISIBLE);
                }
            };
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("URI in kkkkkkkkkkkkkkkkkk ADAPTER : " + position);
                    String act = imageURL.get(position).getUrls().toString();
                    System.out.println("URI in urlllllllllllllllll ADAPTER : " + act);
                    System.out.println("URI in accccccccccccccccccccccccccc ADAPTER : " + act);
                }
            });
           /* imgView.setWebViewClient(client);
            imgView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    Log.d("WEB_VIEW_TEST", "error code:" + errorCode + " - " + description);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    try {


                    System.out.println("URI in kkkkkkkkkkkkkkkkkk ADAPTER : " + position);
                    String act = imageURL.get(position).getUrls().toString();
                        System.out.println("URI in urlllllllllllllllll ADAPTER : " + act);
                    System.out.println("URI in accccccccccccccccccccccccccc ADAPTER : " + act);
                    if (act.equalsIgnoreCase("call")) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + imageURL.get(position).getValue().toString()));
                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return true;
                        }
                        context.startActivity(intent);
                    }
                    else if(act.equalsIgnoreCase("push")){
                        String urls = imageURL.get(position).getValue().toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(urls));
                        context.startActivity(i);
                    }
                    else if(act.equalsIgnoreCase("loadweb")){
                        String urls = imageURL.get(position).getValue().toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(urls));
                        context.startActivity(i);
                    }
                    else {

                    }
                    }catch (NullPointerException e){

                    }
                    return true;
                }
            });*/

      /*  new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                .build()
                .load(imageURL.get(position).getUrl())
                .noFade()
                .into(imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                       progress_loader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  click_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("URI in kkkkkkkkkkkkkkkkkk ADAPTER : " + position);
                String act = imageURL.get(position).getUrls().toString();
                System.out.println("URI in accccccccccccccccccccccccccc ADAPTER : " + act);
                if (act.equalsIgnoreCase("call")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + imageURL.get(position).getValue().toString()));
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return true;
                    }
                    context.startActivity(intent);
                }
                else if(act.equalsIgnoreCase("push")){
                    String url = imageURL.get(position).getValue().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
                else if(act.equalsIgnoreCase("loadweb")){
                    String url = imageURL.get(position).getValue().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
                return false;
            }
        });*/
        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        ((ViewPager)container).addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((RelativeLayout)object);
    }

}
