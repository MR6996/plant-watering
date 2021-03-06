package com.randazzo.mario.plantwateringapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;

import com.android.volley.VolleyError;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.util.Messages;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context ctx;
    protected SharedPreferences preferences;
    protected String urlServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String serverIp = preferences.getString(
                getString(R.string.preferences_debug_server_ip_key),
                "0.0.0.0"
        );
        urlServer = "http://" + serverIp + ":8080/pl-webapp/";

        ctx = this;
    }


    protected void showOkDialog(String title, String message, final boolean close) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (close) {
                            ((Activity) ctx).finish();
                        }
                    }
                })
                .show();
    }

    public void doOnErrorResponse(VolleyError error, boolean close) {
        String messageError = error.getMessage();

        if (error.networkResponse != null)
            messageError = Messages.fromMessageResponse(new String(error.networkResponse.data));

        showOkDialog(getString(R.string.error), messageError, close);
    }

}
