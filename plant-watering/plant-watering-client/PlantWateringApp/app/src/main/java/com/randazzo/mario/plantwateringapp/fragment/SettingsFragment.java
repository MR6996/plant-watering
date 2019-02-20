package com.randazzo.mario.plantwateringapp.fragment;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.randazzo.mario.plantwateringapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.preferences, s);
    }
}
