package hu.ait.shangd.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hu.ait.shangd.weatherapp.R;
import hu.ait.shangd.weatherapp.WeatherDetailsActivity;
import hu.ait.shangd.weatherapp.adapter.ForecastRecyclerAdapter;
import hu.ait.shangd.weatherapp.data.DailyWeather;
import hu.ait.shangd.weatherapp.data.WeatherCurrent;
import hu.ait.shangd.weatherapp.util.Utility;

public class ForecastWeatherFragment extends Fragment {

    public final static String TAG = "ForecastWeatherFragment";

    private ForecastRecyclerAdapter adapter;
    private List<DailyWeather> dataSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dataSet = ((WeatherDetailsActivity) getActivity()).getCity()
                .getWeatherForecast().getDailyWeatherList();

        View rootView = inflater.inflate(
                R.layout.fragment_weather_forecast, container, false);
        WeatherCurrent weatherCurrent = ((WeatherDetailsActivity) getActivity())
                .getCity().getWeatherCurrent();

        TextView tvCurrentCity = (TextView) rootView.findViewById(R.id.tv_forecast_city);
        TextView tvCurrentCountry = (TextView) rootView.findViewById(R.id.tv_forecast_country);
        TextView tvCurrentDesc = (TextView) rootView.findViewById(R.id.tv_forecast_desc);
        TextView tvCurrentTemp = (TextView) rootView.findViewById(R.id.tv_forecast_current_temp);
        TextView tvCurrentMax = (TextView) rootView.findViewById(R.id.tv_forecast_current_max);
        TextView tvCurrentMin = (TextView) rootView.findViewById(R.id.tv_forecast_current_min);

        tvCurrentCity.setText(weatherCurrent.getCityName());
        tvCurrentCountry.setText(weatherCurrent.getCityCountry());
        tvCurrentDesc.setText(weatherCurrent.getWeatherMain());
        tvCurrentTemp.setText(String.valueOf(weatherCurrent.getTemp())+"°");
        tvCurrentMax.setText(String.valueOf(getMaxTemp())+"°");
        tvCurrentMin.setText(String.valueOf(getMinTemp())+"°");

        ImageView ivCurrentIcon = (ImageView) rootView.findViewById(R.id.iv_forecast_icon_big);
        String iconUrl = Utility.getIconUrl(getContext(),
                ((WeatherDetailsActivity) getActivity()).getCity());
        Glide.with(getActivity()).load(iconUrl).centerCrop()
                .override(168,168).into(ivCurrentIcon);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.forecast_recycler_view);
        adapter = new ForecastRecyclerAdapter(getActivity(), dataSet);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public int getMaxTemp() {
        long now = System.currentTimeMillis()/1000L;
        for (DailyWeather daily : dataSet) {
            if (Utility.isSameDay(((WeatherDetailsActivity) getActivity()).getTimeZoneId(),
                    daily.getWeatherTime(), now)) {
                return daily.getTempMax();
            }
        }

        return 0;
    }

    public int getMinTemp() {
        long now = System.currentTimeMillis()/1000L;
        for (DailyWeather daily : dataSet) {
            if (Utility.isSameDay(((WeatherDetailsActivity) getActivity()).getTimeZoneId(),
                    daily.getWeatherTime(), now)) {
                return daily.getTempMin();
            }
        }

        return 0;
    }
}
