package com.randazzo.mario.plantwateringapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.PlantAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

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

        NetworkController.getInstance(this).addToRequestQueue(new AllPlantRequest());
    }

    class AllPlantRequest extends JsonRequest<List<PlantDTO>> {

        private Gson gson = new Gson();

        AllPlantRequest() {
            super(
                    Request.Method.GET,
                    urlServer + "private/plant/all",
                    null,
                    new Response.Listener<List<PlantDTO>>() {
                        @Override
                        public void onResponse(List<PlantDTO> response) {
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

        @Override
        protected Response<List<PlantDTO>> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString =
                        new String(
                                response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                List<PlantDTO> responseList = Arrays.asList(gson.fromJson(jsonString, PlantDTO[].class));
                return Response.success(
                        responseList, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException | JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }

        }

    }
}
