package com.randazzo.mario.plantwateringapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.randazzo.mario.plantWatering.dto.PlantDTO;
import com.randazzo.mario.plantwateringapp.fragment.PlantDetailFragment;
import com.randazzo.mario.plantwateringapp.fragment.PlantListFragment;
import com.randazzo.mario.plantwateringapp.util.PlantAdapter;

public class PlantActivity extends BaseAuthorizedActivity {

    private FragmentManager fragmentManager;

    PlantAdapter.onItemClickListener plantItemClickListener = new PlantAdapter.onItemClickListener() {
        @Override
        public void onItemClick(View view, int position, PlantDTO plant) {
            fragmentManager
                    .beginTransaction()
                    .replace(
                            android.R.id.content,
                            PlantDetailFragment.newInstance(plant))
                    .setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(
                        android.R.id.content,
                        PlantListFragment.newInstance(urlServer, plantItemClickListener))
                .commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!fragmentManager.popBackStackImmediate())
            logout();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!fragmentManager.popBackStackImmediate())
            logout();
    }



}
