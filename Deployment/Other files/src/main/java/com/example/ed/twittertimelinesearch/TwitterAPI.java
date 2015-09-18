package com.example.ed.twittertimelinesearch;


import android.app.Activity;
import android.util.Base64;

import com.example.ed.twittertimelinesearch.POJOs.TwitterTweet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TwitterAPI {

    private String twitterApiKey;
    private String twitterAPISecret;
    Activity activity;
    final static String TWITTER_TOKEN_URL = "https://api.twitter.com/oauth2/token";
    final static String TWITTER_STREAM_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
    static String TWITTER_STREAM_URL_2 = "&include_rts=false&count=200";

    public TwitterAPI(String twitterAPIKey, String twitterApiSecret) {
        this.twitterApiKey = twitterAPIKey;
        this.twitterAPISecret = twitterApiSecret;
        this.activity = activity;
    }

    public ArrayList<TwitterTweet> getTwitterTweets(String screenName) {
        ArrayList<TwitterTweet> twitterTweetArrayList = null;
        try {
            String twitterUrlApiKey = URLEncoder.encode(twitterApiKey, "UTF-8");
            String twitterUrlApiSecret = URLEncoder.encode(twitterAPISecret, "UTF-8");
            String twitterKeySecret = twitterUrlApiKey + ":" + twitterUrlApiSecret;
            String twitterKeyBase64 = Base64.encodeToString(twitterKeySecret.getBytes(), Base64.NO_WRAP);
            TwitterAuthToken twitterAuthToken = getTwitterAuthToken(twitterKeyBase64);
            //Twitter Array List
            twitterTweetArrayList = getTwitterTweets(screenName, twitterAuthToken);
        } catch (UnsupportedEncodingException | IllegalStateException ignored) {
        }
        return twitterTweetArrayList;
    }

    public ArrayList<TwitterTweet> getTwitterTweets(String screenName,
                                                    TwitterAuthToken twitterAuthToken) {
        ArrayList<TwitterTweet> twitterTweetArrayList = null;

        //tweets return size (pages)
        for (int i = 1; i <= 2; i++) {
            if (twitterAuthToken != null && twitterAuthToken.token_type.equals("bearer")) {
                //tweets return size (pages)
                HttpGet httpGet = new HttpGet(TWITTER_STREAM_URL + screenName + TWITTER_STREAM_URL_2 + "&page=" + i);
                httpGet.setHeader("Authorization", "Bearer " + twitterAuthToken.access_token);
                httpGet.setHeader("Content-Type", "application/json");
                HttpUtil httpUtil = new HttpUtil();
                //JSON string
                String twitterTweets = httpUtil.getHttpResponse(httpGet);
                if (i > 1) {
                    twitterTweetArrayList.addAll(convertJsonToTwitterTweet(twitterTweets));
                } else twitterTweetArrayList = convertJsonToTwitterTweet(twitterTweets);
            }
        }
        return twitterTweetArrayList;
    }

    public TwitterAuthToken getTwitterAuthToken(String twitterKeyBase64) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(TWITTER_TOKEN_URL);
        httpPost.setHeader("Authorization", "Basic " + twitterKeyBase64);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
        HttpUtil httpUtil = new HttpUtil();
        String twitterJsonResponse = httpUtil.getHttpResponse(httpPost);
        return convertJsonToTwitterAuthToken(twitterJsonResponse);
    }

    private TwitterAuthToken convertJsonToTwitterAuthToken(String jsonAuth) {
        TwitterAuthToken twitterAuthToken = null;
        if (jsonAuth != null && jsonAuth.length() > 0) {
            try {
                Gson gson = new Gson();
                twitterAuthToken = gson.fromJson(jsonAuth, TwitterAuthToken.class);
            } catch (IllegalStateException ignored) {
            }
        }
        return twitterAuthToken;
    }

    private ArrayList<TwitterTweet> convertJsonToTwitterTweet(String twitterTweets) {
        ArrayList<TwitterTweet> twitterTweetArrayList = null;
        if (twitterTweets != null && twitterTweets.length() > 0) {
            try {
                Gson gson = new Gson();
                twitterTweetArrayList =
                        gson.fromJson(twitterTweets, new TypeToken<ArrayList<TwitterTweet>>() {
                        }.getType());
            } catch (IllegalStateException ignored) {
            }
        }
        return twitterTweetArrayList;
    }

    private class TwitterAuthToken {
        String token_type;
        String access_token;
    }
}
