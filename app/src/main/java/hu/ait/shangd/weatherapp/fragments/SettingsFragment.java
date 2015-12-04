package hu.ait.shangd.weatherapp.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import hu.ait.shangd.weatherapp.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
