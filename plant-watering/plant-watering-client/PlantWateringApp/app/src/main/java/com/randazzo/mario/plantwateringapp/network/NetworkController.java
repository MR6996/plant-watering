package com.randazzo.mario.plantwateringapp.network;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkController {

    @SuppressLint("StaticFieldLeak")
    private static NetworkController ourInstance;

    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private RequestQueue mRequestQueue;


    private NetworkController(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkController getInstance(Context ctx) {
        if (ourInstance == null)
            ourInstance = new NetworkController(ctx);
        return ourInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
