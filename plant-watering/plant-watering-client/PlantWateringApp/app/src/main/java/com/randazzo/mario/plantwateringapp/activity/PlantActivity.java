package com.randazzo.mario.plantwateringapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.Messages;
import com.randazzo.mario.plantwateringapp.util.PlantAdapter;
import com.randazzo.mario.plantwateringapp.util.Session;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantActivity extends BaseAuthorizedActivity {

    private FrameLayout loadingFrame;
    private FrameLayout viewFrame;
    private RecyclerView plantsView;

    private PlantAdapter plantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        this.ctx = this;

        loadingFrame = findViewById(R.id.plant_loading_frame);
        viewFrame = findViewById(R.id.plant_view_frame);

        plantsView = findViewById(R.id.plant_recycler_view);
        plantsView.setLayoutManager(new LinearLayoutManager(this));

        NetworkController.getInstance(this).addToRequestQueue(new AllPlantRequest());
    }

    @Override
    public boolean onSupportNavigateUp() {
        logout();
        return true;
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    public void addPlant(View view) {
        //Prepare the alert dialog for insert new plant
        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);
        addDialog.setTitle("Add new plant");
        addDialog.setMessage("Insert name and description of a new plant:");
        addDialog.setCancelable(false);

        //Inflate the EditText in the dialog box
        final View addPlantDialog = getLayoutInflater().inflate(R.layout.add_dialog_plant, null);
        addDialog.setView(addPlantDialog);

        addDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText plantName = addPlantDialog.findViewById(R.id.plant_add_name);
                EditText plantDescription = addPlantDialog.findViewById(R.id.plant_add_description);

                PlantDTO newPlant = new PlantDTO();
                newPlant.setName(plantName.getText().toString());
                newPlant.setDescription(plantDescription.getText().toString());

                NetworkController.getInstance(ctx).addToRequestQueue(new AddPlantRequest(newPlant));
                dialogInterface.cancel();
            }
        });

        addDialog.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        addDialog.show();
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
                            try {
                                loadingFrame.setVisibility(View.GONE);
                                viewFrame.setVisibility(View.VISIBLE);
                                plantAdapter = new PlantAdapter(response);
                                plantsView.setAdapter(plantAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            doOnErrorResponse(error, true);
                        }
                    });
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Authorization", "Token " + Session.getInstance().getSessionToken());
            return params;
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

    class AddPlantRequest extends StringRequest {

        private final PlantDTO plant;

        AddPlantRequest(final PlantDTO plant) {
            super(
                    Method.POST,
                    urlServer + "private/plant/add",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ctx, Messages.fromMessageResponse(response), Toast.LENGTH_LONG).show();
                            plantAdapter.addItem(plant);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            doOnErrorResponse(error, false);
                        }
                    }
            );

            this.plant = plant;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Content-Type", "application/json");
            params.put("Authorization", "Token " + Session.getInstance().getSessionToken());
            return params;
        }

        @Override
        public byte[] getBody() {
            return new Gson().toJson(plant).getBytes();
        }
    }

}
