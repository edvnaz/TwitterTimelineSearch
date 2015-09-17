package com.example.ed.twittertimelinesearch;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ed.twittertimelinesearch.POJOs.TwitterTweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TweetAdapter extends ArrayAdapter<TwitterTweet> {
    ArrayList<TwitterTweet> tT;
    Activity ac;

    //set stuff using getView() in inflated View
    public TweetAdapter(Activity activity, ArrayList<TwitterTweet> twitterTweets) {
        super(activity, R.layout.one_tweet, twitterTweets);
        this.tT = twitterTweets;
        this.ac = activity;
        LayoutInflater inflater = activity.getWindow().getLayoutInflater();
    }

    //modify to use tweet array
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.one_tweet, parent, false);
        }

        //filling Views
        TextView textView1 = (TextView) convertView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView textView3 = (TextView) convertView.findViewById(R.id.textView3);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TwitterTweet t = tT.get(position);

        //checking if REtweet
        if (t.getRetweeted() != null) {
            textView1.setText(t.getRetweeted().getReTwitterUser().getName());
            textView2.setText(t.getRetweeted().getText());
            textView3.setText(t.getRetweeted().getCreatedAt());
            Picasso.with(convertView.getContext()).load(t.getRetweeted().getReTwitterUser().getProfileImageUrl()).into(imageView);
        } else {
            textView1.setText(t.getTwitterUser().getName());
            textView2.setText(t.getText());
            textView3.setText(t.getCreatedAt());
            Picasso.with(convertView.getContext()).load(t.getTwitterUser().getProfileImageUrl()).into(imageView);
        }

        return convertView;
    }
}
