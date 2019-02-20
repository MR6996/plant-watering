package com.randazzo.mario.plantwateringapp.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.randazzo.mario.plantwateringapp.R;

import org.json.JSONArray;
import org.json.JSONException;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantHolder> {

    private JSONArray plants;

    public PlantAdapter(JSONArray plants) {
        this.plants = plants;
    }

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View contactView = inflater.inflate(R.layout.item_layout_plant, parent, false);
        return new PlantHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantHolder plantHolder, int i) {
        try {
            plantHolder.name.setText(plants.getJSONObject(i).getString("name"));
            plantHolder.description.setText(plants.getJSONObject(i).getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return plants.length();
    }

    class PlantHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;

        PlantHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.plant_item_name);
            description = itemView.findViewById(R.id.plant_item_description);
        }
    }
}
