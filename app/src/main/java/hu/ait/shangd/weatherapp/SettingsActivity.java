package hu.ait.shangd.weatherapp;

import android.app.Activity;
import android.os.Bundle;

import hu.ait.shangd.weatherapp.fragments.SettingsFragment;

public class SettingsActivity extends Activity {

    public static final String KEY_USE_CELSIUS = "use_celsius";
    public static final String KEY_USE_24_HOUR = "use_24_hour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
