package hu.ait.shangd.weatherapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.ait.shangd.weatherapp.fragments.CurrentWeatherFragment;
import hu.ait.shangd.weatherapp.fragments.ForecastWeatherFragment;

public class WeatherPagerAdapter extends FragmentPagerAdapter {

    public WeatherPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Current Weather";
            case 1:
                return "Forecast";
            default:
                return "Current Weather";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CurrentWeatherFragment();
            case 1:
                return new ForecastWeatherFragment();
            default:
                return new CurrentWeatherFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
