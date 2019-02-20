package com.randazzo.mario.plantwateringapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.randazzo.mario.plantwateringapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        //startActivity(new Intent(this, PlantActivity.class));
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void settings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
