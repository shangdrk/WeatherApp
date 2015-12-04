package hu.ait.shangd.weatherapp.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import hu.ait.shangd.weatherapp.R;
import hu.ait.shangd.weatherapp.SettingsActivity;
import hu.ait.shangd.weatherapp.WeatherDetailsActivity;
import hu.ait.shangd.weatherapp.data.City;
import hu.ait.shangd.weatherapp.data.DailyWeather;
import hu.ait.shangd.weatherapp.util.Utility;

public class CurrentWeatherFragment extends Fragment {

    public final static String TAG = "CurrentWeatherFragment";

    private City city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_weather_current, container, false);

        city = ((WeatherDetailsActivity) getActivity()).getCity();
        if (city != null) {
            TextView tvCurrentCity = (TextView) rootView.findViewById(R.id.tv_current_city);
            TextView tvCurrentCountry = (TextView) rootView.findViewById(R.id.tv_current_country);
            TextView tvCurrentDesc = (TextView) rootView.findViewById(R.id.tv_current_desc);
            TextView tvCurrentTemp = (TextView) rootView.findViewById(R.id.tv_current_temp);
            TextView tvCurrentMax = (TextView) rootView.findViewById(R.id.tv_current_max);
            TextView tvCurrentMin = (TextView) rootView.findViewById(R.id.tv_current_min);
            TextView tvCurrentSunrise = (TextView) rootView.findViewById(R.id.tv_current_sunrise);
            TextView tvCurrentSunset = (TextView) rootView.findViewById(R.id.tv_current_sunset);
            TextView tvCurrentPressure = (TextView) rootView.findViewById(R.id.tv_current_pressure);
            TextView tvCurrentHumidity = (TextView) rootView.findViewById(R.id.tv_current_humidity);

            ImageView ivCurrentIcon = (ImageView) rootView.findViewById(R.id.iv_current_icon);
            long sunRise = city.getWeatherCurrent().getCitySunrise();
            long sunSet = city.getWeatherCurrent().getCitySunset();

            tvCurrentCity.setText(city.getWeatherCurrent().getCityName());
            tvCurrentCountry.setText(city.getWeatherCurrent().getCityCountry());
            tvCurrentDesc.setText(city.getWeatherCurrent().getWeatherMain());
            tvCurrentTemp.setText(String.valueOf(city.getWeatherCurrent().getTemp())+"°");

            boolean hour24 = PreferenceManager.getDefaultSharedPreferences(getContext())
                    .getBoolean(SettingsActivity.KEY_USE_24_HOUR, true);

            tvCurrentSunrise.setText(Utility.unixLongToTime(
                            ((WeatherDetailsActivity) getActivity()).getTimeZoneId(),
                            sunRise, hour24)
            );
            tvCurrentSunset.setText(Utility.unixLongToTime(
                    ((WeatherDetailsActivity) getActivity()).getTimeZoneId(),
                            sunSet, hour24)
            );

            tvCurrentMax.setText(String.valueOf(getMaxTemp())+"°");
            tvCurrentMin.setText(String.valueOf(getMinTemp())+"°");

            tvCurrentPressure.setText(String.valueOf(city.getWeatherCurrent().getPressure()));
            tvCurrentHumidity.setText(String.valueOf(city.getWeatherCurrent().getHumidity())+"%");

            String iconUrl = Utility.getIconUrl(getContext(), city);
            Glide.with(getActivity()).load(iconUrl).centerCrop()
                    .override(168,168).into(ivCurrentIcon);
        }

        return rootView;
    }

    public int getMaxTemp() {
        long now = System.currentTimeMillis()/1000L;
        for (DailyWeather daily : city.getWeatherForecast().getDailyWeatherList()) {
            if (Utility.isSameDay(((WeatherDetailsActivity) getActivity()).getTimeZoneId(),
                    daily.getWeatherTime(), now)) {
                return daily.getTempMax();
            }
        }

        return 0;
    }

    public int getMinTemp() {
        long now = System.currentTimeMillis()/1000L;
        for (DailyWeather daily : city.getWeatherForecast().getDailyWeatherList()) {
            if (Utility.isSameDay(((WeatherDetailsActivity) getActivity()).getTimeZoneId(),
                    daily.getWeatherTime(), now)) {
                return daily.getTempMin();
            }
        }

        return 0;
    }
}
