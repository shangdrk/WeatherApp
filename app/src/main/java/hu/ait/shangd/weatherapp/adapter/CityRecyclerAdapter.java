package hu.ait.shangd.weatherapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hu.ait.shangd.weatherapp.MainActivity;
import hu.ait.shangd.weatherapp.R;
import hu.ait.shangd.weatherapp.data.City;
import hu.ait.shangd.weatherapp.data.WeatherCurrent;
import hu.ait.shangd.weatherapp.util.Utility;

public class CityRecyclerAdapter
        extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<City> cityList;

    public CityRecyclerAdapter(Context context) {
        this.context = context;
        this.cityList = City.listAll(City.class);
    }

    public void addItem(WeatherCurrent current) {
        City city = new City();
        city.setWeatherCurrent(current);
        city.setCityId(current.getCityId());
        city.setName(current.getCityName());

        city.save();
        cityList.add(city);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View anItem = LayoutInflater.from(context).inflate(
                R.layout.city_row, parent, false
        );
        return new ViewHolder(anItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final City city = cityList.get(position);

        if (city.getWeatherCurrent() != null) {
            String iconUrl = Utility.getIconUrl(context, city);
            Glide.with(context).load(iconUrl).centerCrop()
                    .override(168,168).into(holder.ivIconMain);
            holder.tvCityMain.setText(city.getWeatherCurrent().getCityName());
            holder.tvDegreeMain.setText(city.getWeatherCurrent().getTemp()+"°");

            holder.cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        holder.cardView.setCardBackgroundColor(
                                ContextCompat.getColor(context,
                                        R.color.colorItemPressed)
                        );
                    } else {
                        holder.cardView.setCardBackgroundColor(
                                ContextCompat.getColor(context,
                                        R.color.colorItemBg)
                        );
                    }

                    return false;
                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).queryWeatherForecast(city);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void removeCity(int position) {
        cityList.get(position).delete();
        cityList.remove(position);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        City.deleteAll(City.class);
        cityList = City.listAll(City.class);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView ivIconMain;
        private final TextView tvCityMain;
        private final TextView tvDegreeMain;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            ivIconMain = (ImageView) itemView.findViewById(R.id.iv_icon_main);
            tvCityMain = (TextView) itemView.findViewById(R.id.tv_city_main);
            tvDegreeMain = (TextView) itemView.findViewById(R.id.tv_degree_main);
        }
    }
}
