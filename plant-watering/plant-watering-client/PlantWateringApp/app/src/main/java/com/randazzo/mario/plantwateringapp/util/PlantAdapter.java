package com.randazzo.mario.plantwateringapp.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantwateringapp.R;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantHolder> {

    private ArrayList<PlantDTO> plants;

    public PlantAdapter(List<PlantDTO> plants) {
        this.plants = new ArrayList<>(plants);
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
        plantHolder.name.setText(plants.get(i).getName());
        plantHolder.description.setText(plants.get(i).getDescription());
    }

    public void addItem(PlantDTO p) {
        plants.add(p);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return plants.size();
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
