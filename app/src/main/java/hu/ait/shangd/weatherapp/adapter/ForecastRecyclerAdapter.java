package hu.ait.shangd.weatherapp.adapter;

import android.content.Context;
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
import hu.ait.shangd.weatherapp.data.DailyWeather;
import hu.ait.shangd.weatherapp.util.Utility;

public class ForecastRecyclerAdapter
        extends RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<DailyWeather> dataSet;

    public ForecastRecyclerAdapter(Context context, List<DailyWeather> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View anItem = LayoutInflater.from(context).inflate(
                R.layout.forecast_row, parent, false
        );
        return new ViewHolder(anItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DailyWeather forecast = dataSet.get(position);

        holder.tvDay.setText(Utility.getDayInTheWeek(
                        ((WeatherDetailsActivity) context).getTimeZoneId(),
                        forecast.getWeatherTime())
        );
        holder.tvDegreeMax.setText(String.valueOf(forecast.getTempMax())+"°");
        holder.tvDegreeMin.setText(String.valueOf(forecast.getTempMin())+"°");

        String iconUrl = Utility.getIconUrl(context, forecast);
        Glide.with(context).load(iconUrl).centerCrop()
                .override(98,98).into(holder.forecastIcon);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDay;
        private ImageView forecastIcon;
        private TextView tvDegreeMax;
        private TextView tvDegreeMin;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            forecastIcon = (ImageView) itemView.findViewById(R.id.iv_forecast_icon_main);
            tvDegreeMax = (TextView) itemView.findViewById(R.id.tv_forecast_max);
            tvDegreeMin = (TextView) itemView.findViewById(R.id.tv_forecast_min);
        }
    }
}
