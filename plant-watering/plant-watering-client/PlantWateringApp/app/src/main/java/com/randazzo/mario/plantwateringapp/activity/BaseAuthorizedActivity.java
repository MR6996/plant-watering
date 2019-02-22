package com.randazzo.mario.plantwateringapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.Session;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseAuthorizedActivity extends BaseActivity {

    protected void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NetworkController.getInstance(ctx).addToRequestQueue(new LogOutRequest());
                    }
                })
                .setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    class LogOutRequest extends StringRequest {

        LogOutRequest() {
            super(
                    Method.POST,
                    urlServer + "private/logout",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Session.getInstance().clear();
                            ((Activity) ctx).finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            doOnErrorResponse(error, false);
                        }
                    }
            );
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Authorization", "Token " + Session.getInstance().getSessionToken());
            return params;
        }
    }
}
