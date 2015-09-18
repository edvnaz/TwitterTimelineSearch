package com.example.ed.twittertimelinesearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AlertMessage {
    AlertMessage(Activity context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Check if user id is correct:\n ''" + message + "''");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // here you can add functions
                    }
                }
        );
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.show();
    }
}
