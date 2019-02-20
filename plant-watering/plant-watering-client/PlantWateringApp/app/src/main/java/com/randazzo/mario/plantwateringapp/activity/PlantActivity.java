package com.randazzo.mario.plantwateringapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.PlantAdapter;

import org.json.JSONArray;

public class PlantActivity extends BaseActivity {

    private PlantActivity ctx;

    private FrameLayout loadingFrame;
    private FrameLayout viewFrame;
    private RecyclerView plantsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ctx = this;

        loadingFrame = findViewById(R.id.plant_loading_frame);
        viewFrame = findViewById(R.id.plant_view_frame);

        plantsView = findViewById(R.id.plant_recycler_view);
        plantsView.setLayoutManager(new LinearLayoutManager(this));

        NetworkController.getInstance(this).addToRequestQueue(new AllPlantRequest(null));
    }

    class AllPlantRequest extends JsonArrayRequest {

        AllPlantRequest(@Nullable JSONArray jsonRequest) {
            super(
                    Request.Method.GET,
                    urlServer + "private/plant/all",
                    jsonRequest,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            loadingFrame.setVisibility(View.GONE);
                            viewFrame.setVisibility(View.VISIBLE);
                            plantsView.setAdapter(new PlantAdapter(response));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ctx.showOkDialog(getString(R.string.error), error.getMessage(), true);
                        }
                    });
        }
    }
}
