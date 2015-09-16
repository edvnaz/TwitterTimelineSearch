package com.example.ed.twittertimelinesearch;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.ed.twittertimelinesearch.POJOs.TwitterTweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class TwitterAsyncTask extends AsyncTask<Object, Void, ArrayList<TwitterTweet>> {
    static Activity callerActivity;
    ArrayList<TwitterTweet> twitterTweets = null;

    boolean found = false;
    boolean checkBox;
    int arraySize, c = 0;
    public static int p1, p2, p3, p4;
    final static String TWITTER_API_KEY = "FAlzZ0XaqD6FYAbnQHyhOz8Tw";
    final static String TWITTER_API_SECRET = "s55Ww9XCXOrxOblH5p7NaJYgBsVUcptCP1z8Gxb6a2bSbDtGum";

    @Override
    protected ArrayList<TwitterTweet> doInBackground(Object... params) {
        ArrayList<TwitterTweet> foundTwitterTweets = new ArrayList<TwitterTweet>();

        callerActivity = (Activity) params[1];

        if (params.length > 0) {
            TwitterAPI twitterAPI = new TwitterAPI(TWITTER_API_KEY, TWITTER_API_SECRET);

            checkBoxState(checkBox);
            twitterTweets = twitterAPI.getTwitterTweets(params[0].toString());

            arraySize = twitterTweets.size();
            extract(arraySize, c, twitterTweets, foundTwitterTweets);
        }

        return foundTwitterTweets;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(ArrayList<TwitterTweet> foundTwitterTweets) {

        //Uses main twitter array for showing user image
        showUserImage(twitterTweets);

        TweetAdapter adapter;
        //if foundTwitterTweets(tweets found by par) is empty(no tweets found by par), then uses main tweets Array
        if (foundTwitterTweets.size() == 0) {
            adapter = new TweetAdapter(callerActivity, twitterTweets);
        } else {
            //Array adapter with found tweets by parameters
            adapter = new TweetAdapter(callerActivity, foundTwitterTweets);
        }

        //sets up parameter textView's to show found number
        results(foundTwitterTweets);
        //sets found parameters results to 0's
        reset();

        //-------------------test
        ListView stringListView = (ListView) callerActivity.findViewById(R.id.list_custom);
        stringListView.setAdapter(adapter);
        //-----------------------

//        callerActivity.setListAdapter(adapter);
        TimelineActivity.progressDialog.dismiss();
    }

    private void extract(int arraySize, int c, ArrayList<TwitterTweet> twitterTweets, ArrayList<TwitterTweet> foundTwitterTweets) {
        c = 0;
        if (TimelineActivity.getMessages(1).equals("false")) {
            Log.v("tusti parametrai", "tusti parametrai");
        } else {
            for (int i = 0; i < arraySize; i++) {
                Log.v("array for'as", twitterTweets.get(i).getText());
                //-------- P A R _ 1
                if (twitterTweets.get(i).getText().toLowerCase().contains(TimelineActivity.getMessages(2)) && (TimelineActivity.getMessages(2).length() != 0)) {
                    Log.v(">getMessage test", TimelineActivity.getMessages(2));
                    p1++;
                    Log.v(">array contains", twitterTweets.get(i).getText());
                    found = true;
                }
                //-------- P A R _ 2
                if (twitterTweets.get(i).getText().toLowerCase().contains(TimelineActivity.getMessages(3)) && (TimelineActivity.getMessages(3).length() != 0)) {
                    Log.v(">getMessage test", TimelineActivity.getMessages(3));
                    p2++;
                    Log.v(">array contains", twitterTweets.get(i).getText());
                    found = true;
                }
                //-------- P A R _ 3
                if (twitterTweets.get(i).getText().toLowerCase().contains(TimelineActivity.getMessages(4)) && (TimelineActivity.getMessages(4).length() != 0)) {
                    Log.v(">getMessage test", TimelineActivity.getMessages(4));
                    p3++;
                    Log.v(">array contains", twitterTweets.get(i).getText());
                    found = true;
                }
                //-------- P A R _ 4
                if (twitterTweets.get(i).getText().toLowerCase().contains(TimelineActivity.getMessages(5)) && (TimelineActivity.getMessages(5).length() != 0)) {
                    Log.v(">getMessage test", TimelineActivity.getMessages(5));
                    p4++;
                    Log.v(">array contains", twitterTweets.get(i).getText());
                    found = true;
                }
                //---------------- p e r k e l i m a s
                if (found) {
                    foundTwitterTweets.add(c, twitterTweets.get(i));
                    Log.v("Log ArrayList new = ", foundTwitterTweets.get(c).getText());
                    c++;
                    found = false;
                }
            }
        }
        Log.v("count times", String.valueOf("p1 = " + p1 + "\np2 = " + p2 + "\np3 = " + p3 + "\np4 = " + p4));
    }

    public void results(ArrayList<TwitterTweet> foundT) {
        if (TimelineActivity.Messages[2].length() == 0 && TimelineActivity.Messages[3].length() == 0 &&
                TimelineActivity.Messages[4].length() == 0 && TimelineActivity.Messages[5].length() == 0) {
            TextView tv = (TextView) callerActivity.findViewById(R.id.parameter_empty);
            tv.setVisibility(View.VISIBLE);
            TableLayout tl = (TableLayout) callerActivity.findViewById(R.id.parameter_found);
            tl.setVisibility(View.GONE);
        } else {
            TextView par1 = (TextView) callerActivity.findViewById(R.id.par1);
            if (TimelineActivity.Messages[2].length() == 0) {
                par1.setText("1st parameter empty");
            } else {
                par1.setText(" " + TimelineActivity.Messages[2] + " = " + p1);
            }
            TextView par2 = (TextView) callerActivity.findViewById(R.id.par2);
            if (TimelineActivity.Messages[3].length() == 0) {
                par2.setText("2nd parameter empty");
            } else {
                par2.setText(TimelineActivity.Messages[3] + " = " + p2);
            }
            TextView par3 = (TextView) callerActivity.findViewById(R.id.par3);
            if (TimelineActivity.Messages[4].length() == 0) {
                par3.setText(" 3rd parameter empty");
            } else {
                par3.setText(" " + TimelineActivity.Messages[4] + " = " + p3);
            }
            TextView par4 = (TextView) callerActivity.findViewById(R.id.par4);
            if (TimelineActivity.Messages[5].length() == 0) {
                par4.setText("4th parameter empty");
            } else {
                par4.setText(TimelineActivity.Messages[5] + " = " + p4);
            }
        }
    }

    public void showUserImage(ArrayList<TwitterTweet> twitterTweet) {
        //-----------original/ working
        ImageView imageView = (ImageView) callerActivity.findViewById(R.id.userImage);
        Picasso.with(callerActivity).load(twitterTweet.get(0).getTwitterUser().getProfileImageUrl()).into(imageView);
    }

    public void reset() {
        TwitterAsyncTask.p1 = 0;
        TwitterAsyncTask.p2 = 0;
        TwitterAsyncTask.p3 = 0;
        TwitterAsyncTask.p4 = 0;
    }

    public void checkBoxState(boolean checkBox) {
        if (checkBox) {
            TwitterAPI.TWITTER_STREAM_URL_2 = "&include_rts=true&count=200";
        }
    }

}
