package com.example.ed.twittertimelinesearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TimelineActivity extends Activity {

    public static String[] Messages = new String[6];
    public String twitterScreenName, par1, par2, par3, par4;
    boolean bool_par;
    final static String TAG = "TimelineActivity";
    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_listview);

        getintent();
//        //progress dialog -start
        progressDialog = ProgressDialog.show(this, "", "Tweeding. Please wait...", true);

        new TwitterAsyncTask().execute(twitterScreenName, this);
//        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
//        if (androidNetworkUtility.isConnected(this)) {
//
//        } else {
//            Log.v(TAG, "Network not Available!");
//        }
        //Shows username in main bar
        showUsername();
    }

    public Intent getintent() {
        Intent intent = getIntent();
        Messages = intent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);
        twitterScreenName = Messages[0];
        Log.v("array test", Messages[0] + "\n" + Messages[1] + "\n" + Messages[2] + "\n" + Messages[3] + "\n" + Messages[4] + "\n" + Messages[5]);
        return intent;
    }

    public void showUsername() {
        TextView username = (TextView) findViewById(R.id.username);
        username.setText("@" + String.valueOf(twitterScreenName));
    }

    public static String getMessages(int i) {
        return Messages[i];
    }

}
