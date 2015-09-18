package com.example.ed.twittertimelinesearch;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.example.ed.twittertimelinesearch";
    public static Boolean bool_par = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //change
    public void onCheckboxClicked(View v) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        switch (v.getId()) {
            case R.id.checkBox:
                if (checkBox.isChecked()) {
                    TwitterAPI.TWITTER_STREAM_URL_2 = "&include_rts=true&count=200";
                } else {
                    TwitterAPI.TWITTER_STREAM_URL_2 = "&include_rts=false&count=200";
                }
                break;
        }
    }

    public void startTimelineActivity(View view) {
        onCheckboxClicked(view);
        Intent timelineIntent = new Intent(this, TimelineActivity.class);

        EditText editText = (EditText) findViewById(R.id.search);
        String message = editText.getText().toString();

        if (message.contains(" ")) {
            new AlertMessage(this, message);
        } else {

            EditText editText1 = (EditText) findViewById(R.id.parameter1);
            String par1 = editText1.getText().toString();

            EditText editText2 = (EditText) findViewById(R.id.parameter2);
            String par2 = editText2.getText().toString();

            EditText editText3 = (EditText) findViewById(R.id.parameter3);
            String par3 = editText3.getText().toString();

            EditText editText4 = (EditText) findViewById(R.id.parameter4);
            String par4 = editText4.getText().toString();

            if (message.length() == 0) {
                message = "android";
            }
            if (par1.length() != 0 || par2.length() != 0 || par3.length() != 0 || par4.length() != 0) {
                bool_par = true;
            } else {
                Toast toast = Toast.makeText(MainActivity.this, "NO parameters to search", Toast.LENGTH_SHORT);
                toast.show();
            }

            String[] Messages = new String[]{message, String.valueOf(bool_par), par1, par2, par3, par4};

            timelineIntent.putExtra(EXTRA_MESSAGE, Messages);
            startActivity(timelineIntent);
        }
    }
}