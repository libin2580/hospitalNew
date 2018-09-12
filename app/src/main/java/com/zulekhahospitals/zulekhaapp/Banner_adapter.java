package com.zulekhahospitals.zulekhaapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 6/14/2017.
 */

class Banner_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Action_Services_Model> asm;
    Context context;



    public Banner_adapter(ArrayList<Action_Services_Model> asm, Context context) {
        this.asm = asm;
        this.context = context;

    }


    public static class webviewHolder extends RecyclerView.ViewHolder {
        WebView imgView;
        CompactTweetView tweetView;
        public webviewHolder(View itemView) {
            super(itemView);
            imgView=   (WebView) itemView.findViewById(R.id.webview3);
            tweetView=   (CompactTweetView) itemView.findViewById(R.id.bike_tweet);
        }
    }
    public static class imageViewholder extends RecyclerView.ViewHolder {
        ImageView img;
        public imageViewholder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.imageview3);
        }
    }
    public static class textViewholder extends RecyclerView.ViewHolder {
        TextView fb_feed;
        public textViewholder(View itemView) {
            super(itemView);
            fb_feed=(TextView)itemView.findViewById(R.id.fb_feed);
        }
    }


    @Override
    public int getItemCount() {
        return asm.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_view, parent, false);
                return new webviewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_viewpager_item1_for_imageview, parent, false);
                return new imageViewholder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facebook_feed_layout, parent, false);
                return new textViewholder(view);
        }
        return null;
    }


   @Override
   public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
       System.out.println("asm.get(i).getAction() : "+asm.get(i).getCategory());
       switch (asm.get(i).getCategory()) {

           case "image":
               System.out.println("<<<<<<<<<<<<<Banner<<<<<<<<<<<<"+asm.get(i).getBanner());
               Picasso.with(context)
                       .load(asm.get(i).getBanner())

                       .into(((imageViewholder) holder).img);
               break;
           case "facebook":
               System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<"+asm.get(i).getValue().toString());
               ((textViewholder) holder).fb_feed.setText(asm.get(i).getValue());
               /*Picasso.with(context)
                       .load(asm.get(i).getBanner())

                       .into(((imageViewholder) holder).img);*/
               break;
           case "twitter":
               try {
                   final long tweetId = 20;
                   long id= Long.parseLong("1024973087358382080");
                   TweetUtils.loadTweet(id, new Callback<Tweet>() {
                       @Override
                       public void success(Result<Tweet> result) {
                           System.out.println("inside tweet");

//myLayout.addView(new TweetView(EmbeddedTweetsActivity.this, tweetId));

                           ((webviewHolder) holder).tweetView.setTweet(result.data);
                          // ((webviewHolder) holder).imgView.getSettings().setJavaScriptEnabled(true);
                       }

                       @Override
                       public void failure(TwitterException exception) {
// Toast.makeText(...).show();
                       }
                   });
                  /* System.out.println("URI in jjjjjjjjjjjjjjjjjjjjjjPAGE ADAPTER : " + asm.get(i).getBanner());
                   ((webviewHolder) holder).imgView.getSettings().setJavaScriptEnabled(true);
                   ((webviewHolder) holder).imgView.getSettings().setLoadsImagesAutomatically(true);
                   ((webviewHolder) holder).imgView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                   ((webviewHolder) holder).imgView.getSettings().setAllowFileAccess(true);
                   ((webviewHolder) holder).imgView.getSettings().setDomStorageEnabled(true);
                   ((webviewHolder) holder).imgView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                   ((webviewHolder) holder).imgView.getSettings().setLoadWithOverviewMode(true);
                   ((webviewHolder) holder).imgView.getSettings().setUseWideViewPort(true);
                   ((webviewHolder) holder).imgView.getSettings().setSupportZoom(true);
                   ((webviewHolder) holder).imgView.loadUrl(asm.get(i).getBanner());
                   ((webviewHolder) holder).imgView.setHorizontalScrollBarEnabled(false);*/

               } catch (Exception e) {
                   e.printStackTrace();
               }
               break;
       }

   }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemViewType(int position) {
        if (asm != null) {

                return asm.get(position).getType();

        }
        return 0;
    }




}
