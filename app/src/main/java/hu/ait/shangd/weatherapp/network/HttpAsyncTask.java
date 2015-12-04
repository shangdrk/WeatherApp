package hu.ait.shangd.weatherapp.network;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;

import de.greenrobot.event.EventBus;
import hu.ait.shangd.weatherapp.data.WeatherCurrent;
import hu.ait.shangd.weatherapp.data.WeatherForecast;
import hu.ait.shangd.weatherapp.util.WeatherTypeAdapterFactory;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    private TaskName taskName;

    public enum TaskName {
        WEATHER_CURRENT, WEATHER_FORECAST, GEO_TIMEZONE;
    }

    public HttpAsyncTask(TaskName taskName) {
        super();
        this.taskName = taskName;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            is = conn.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                baos.write(ch);
            }

            result = new String(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            switch (taskName) {
                case WEATHER_CURRENT:
                    Gson gson_one = new GsonBuilder()
                            .registerTypeAdapterFactory(new WeatherTypeAdapterFactory())
                            .setDateFormat(DateFormat.LONG)
                            .create();
                    WeatherCurrent weatherCurrent = gson_one.fromJson(result,
                            WeatherCurrent.class);

                    EventBus.getDefault().post(weatherCurrent);
                    break;
                case WEATHER_FORECAST:
                    Gson gson_two = new GsonBuilder()
                            .registerTypeAdapterFactory(new WeatherTypeAdapterFactory())
                            .setDateFormat(DateFormat.LONG)
                            .create();
                    WeatherForecast weatherForecast = gson_two.fromJson(result,
                            WeatherForecast.class);

                    EventBus.getDefault().post(weatherForecast);
                    break;
                case GEO_TIMEZONE:
                    String timeZone = new JSONObject(result).getString("timeZoneId");

                    EventBus.getDefault().post(timeZone);
                    break;
                default:break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
