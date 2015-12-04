package hu.ait.shangd.weatherapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import de.greenrobot.event.EventBus;
import hu.ait.shangd.weatherapp.adapter.WeatherPagerAdapter;
import hu.ait.shangd.weatherapp.data.City;
import hu.ait.shangd.weatherapp.network.HttpAsyncTask;

public class WeatherDetailsActivity extends AppCompatActivity {

    private City city;
    private String timeZoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(MainActivity.KEY_CITY)) {
            city = (City) getIntent().getExtras().getSerializable(MainActivity.KEY_CITY);

            try {
                String queryUrl = getQueryUrl(city.getWeatherCurrent().getCityLat(),
                        city.getWeatherCurrent().getCityLon());
                new HttpAsyncTask(HttpAsyncTask.TaskName.GEO_TIMEZONE).execute(queryUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getQueryUrl(double lat, double lon) {
        return String.format(getString(R.string.geolocation_url),
                lat, lon,
                System.currentTimeMillis()/1000L,
                getString(R.string.geolocation_api_key));
    }

    public void onEventMainThread(String result) {
        timeZoneId = result;
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new WeatherPagerAdapter(getSupportFragmentManager()));
    }

    public City getCity() {
        return city;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }
}
