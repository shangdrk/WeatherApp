package hu.ait.shangd.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import de.greenrobot.event.EventBus;
import hu.ait.shangd.weatherapp.adapter.CityRecyclerAdapter;
import hu.ait.shangd.weatherapp.data.City;
import hu.ait.shangd.weatherapp.data.WeatherCurrent;
import hu.ait.shangd.weatherapp.data.WeatherForecast;
import hu.ait.shangd.weatherapp.fragments.AddCityFragment;
import hu.ait.shangd.weatherapp.network.HttpAsyncTask;
import hu.ait.shangd.weatherapp.touch.CityItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AddCityFragment.AddCityFragmentInterface {

    public static final String KEY_CITY = "KEY_CITY";

    private CityRecyclerAdapter adapter;
    private City cityWithDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializes floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFragment(AddCityFragment.TAG);
            }
        });

        // Initializes navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Initializes recyler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.city_recycler_view);
        adapter = new CityRecyclerAdapter(this);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new CityItemTouchHelperCallback(adapter)
        );
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter.getItemCount() != 0) {
            List<City> cityList = adapter.getCityList();
            for (City city : cityList) {
                String cityId = String.valueOf(city.getCityId());
                String queryUrl;

                if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                        SettingsActivity.KEY_USE_CELSIUS, true)) {
                    queryUrl = getString(R.string.current_base_url).concat(
                            appendQueryParams(cityId, "metric", true)
                    );
                } else {
                    queryUrl = getString(R.string.current_base_url).concat(
                            appendQueryParams(cityId, "imperial", true)
                    );
                }

                new HttpAsyncTask(HttpAsyncTask.TaskName.WEATHER_CURRENT).execute(queryUrl);
            }
        }
    }

    public void queryWeatherForecast(City city) {
        cityWithDetails = city;
        String cityId = String.valueOf(city.getCityId());
        String queryUrl;

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                SettingsActivity.KEY_USE_CELSIUS, true)) {
            queryUrl = getString(R.string.forecast_base_url).concat(
                    appendQueryParams(cityId, "metric", true).concat("&cnt=10")
            );
        } else {
            queryUrl = getString(R.string.forecast_base_url).concat(
                    appendQueryParams(cityId, "imperial", true).concat("&cnt=10")
            );
        }

        new HttpAsyncTask(HttpAsyncTask.TaskName.WEATHER_FORECAST).execute(queryUrl);
    }

    public void showWeatherDetails() {
        if (cityWithDetails != null && cityWithDetails.getWeatherForecast() != null) {
            Intent intentShowDetails = new Intent(MainActivity.this,
                    WeatherDetailsActivity.class);

            intentShowDetails.putExtra(KEY_CITY, cityWithDetails);
            startActivity(intentShowDetails);
            cityWithDetails = null;
        }
    }

    protected void showDialogFragment(String tag) {
        AddCityFragment acf = new AddCityFragment();
        acf.setCancelable(false);
        acf.show(getFragmentManager(), tag);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startSettingsActivity();
        } else if (id == R.id.action_deleteAll) {
            adapter.deleteAll();
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            showDialogFragment(AddCityFragment.TAG);
        } else if (id == R.id.nav_settings) {
            startSettingsActivity();
        } else if (id == R.id.nav_about) {
            Toast.makeText(MainActivity.this, "Developer: Derek Shang\n" +
                    "\t\t\t\t\t\t\t\t\tv 1.1", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void startSettingsActivity() {
        Intent settingsIntent = new Intent(MainActivity.this,
                SettingsActivity.class);
        startActivity(settingsIntent);
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

    public void onEventMainThread(WeatherCurrent result) {
        if (adapter.getItemCount() == 0) {
            adapter.addItem(result);
        } else {
            for (City city : adapter.getCityList()) {
                if (result.getCityId() == city.getCityId()) {
                    city.setWeatherCurrent(result);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
            adapter.addItem(result);
        }
    }

    public void onEventMainThread(WeatherForecast result) {
        cityWithDetails.setWeatherForecast(result);
        showWeatherDetails();
    }

    public String appendQueryParams(String main, String units, boolean usingId) {
        String result = "";

        if (usingId) {
            result += "?id=".concat(main);
        } else {
            result += "?q=".concat(main.trim().replace(" ", "%20"));
        }
        result += "&units=".concat(units);
        result += "&appid=".concat(getString(R.string.api_key));
        return result;
    }

    // ************************* AddCityFragmentInterface ****************************** //
    @Override
    public void onFragmentPositiveClicked(String cityName) {
        String queryUrl;

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                SettingsActivity.KEY_USE_CELSIUS, true)) {
            queryUrl = getString(R.string.current_base_url).concat(
                    appendQueryParams(cityName, "metric", false)
            );
        } else {
            queryUrl = getString(R.string.current_base_url).concat(
                    appendQueryParams(cityName, "imperial", false)
            );
        }

        new HttpAsyncTask(HttpAsyncTask.TaskName.WEATHER_CURRENT).execute(queryUrl);
    }
}
