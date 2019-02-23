package com.randazzo.mario.plantwateringapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.activity.PlotsActivity;

import java.util.Objects;


public class PlantDetailFragment extends Fragment {

    public static final String PLANT_ID_KEY = "com_randazzo_mario_plantwatering_plant_id_key";

    private PlantDTO plant;
    private TextView plantName;
    private TextView descriptionName;
    private Button plotButton;

    public PlantDetailFragment() {
    }

    public static PlantDetailFragment newInstance(PlantDTO plant) {
        PlantDetailFragment fragment = new PlantDetailFragment();
        fragment.setPlant(plant);
        return fragment;
    }

    public void setPlant(PlantDTO plant) {
        this.plant = plant;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plant_detail, container, false);

        plantName = rootView.findViewById(R.id.plant_name);
        plantName.setText(plant.getName());

        descriptionName = rootView.findViewById(R.id.plant_description);
        descriptionName.setText(plant.getDescription());

        plotButton = rootView.findViewById(R.id.plant_plot_button);
        plotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent plotIntent = new Intent(getContext(), PlotsActivity.class);
                plotIntent.putExtra(PLANT_ID_KEY, plant.getId());
                Objects.requireNonNull(getActivity()).startActivity(plotIntent);
            }
        });

        return rootView;
    }


}
