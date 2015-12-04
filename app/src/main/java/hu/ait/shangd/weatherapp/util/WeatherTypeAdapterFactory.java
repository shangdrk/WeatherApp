package hu.ait.shangd.weatherapp.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hu.ait.shangd.weatherapp.data.DailyWeather;
import hu.ait.shangd.weatherapp.data.WeatherCurrent;
import hu.ait.shangd.weatherapp.data.WeatherForecast;

public class WeatherTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        if (type == WeatherCurrent.class) {
            return (TypeAdapter<T>) getWeatherCurrentTypeAdapter(elementAdapter);
        } else if (type == WeatherForecast.class) {
            return (TypeAdapter<T>) getWeatherForecastTypeAdapter(elementAdapter);
        } else {
            return null;
        }
    }

    private TypeAdapter<WeatherCurrent> getWeatherCurrentTypeAdapter(
            final TypeAdapter<JsonElement> elementAdapter) {

        return new TypeAdapter<WeatherCurrent>() {
            @Override
            public void write(JsonWriter out, WeatherCurrent value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.toString());
                }
            }

            @Override
            public WeatherCurrent read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                JsonObject jo = (JsonObject) elementAdapter.read(in);

                String cityName = jo.get("name").getAsString();
                int cityId = jo.get("id").getAsInt();
                String cityCountry = jo.getAsJsonObject("sys").get("country").getAsString();
                double cityLon = jo.getAsJsonObject("coord").get("lon").getAsDouble();
                double cityLat = jo.getAsJsonObject("coord").get("lat").getAsDouble();
                long citySunrise = jo.getAsJsonObject("sys").get("sunrise").getAsLong();
                long citySunset = jo.getAsJsonObject("sys").get("sunset").getAsLong();
                long weatherTime = jo.get("dt").getAsLong();
                String weatherMain = jo.getAsJsonArray("weather").get(0).getAsJsonObject().
                        get("main").getAsString();
                String iconId = jo.getAsJsonArray("weather").get(0).getAsJsonObject().
                        get("icon").getAsString();
                int temp = (int)jo.getAsJsonObject("main").get("temp").getAsDouble();
                int pressure = (int)jo.getAsJsonObject("main").get("pressure").getAsDouble();
                int humidity = (int)jo.getAsJsonObject("main").get("humidity").getAsDouble();

                return new WeatherCurrent().setCityName(cityName).setCityId(cityId)
                        .setCityCountry(cityCountry).setCityLon(cityLon).setCityLat(cityLat)
                        .setCitySunrise(citySunrise).setCitySunset(citySunset)
                        .setWeatherTime(weatherTime).setWeatherMain(weatherMain).setIconId(iconId)
                        .setTemp(temp).setPressure(pressure).setHumidity(humidity);
            }
        };
    }

    private TypeAdapter<WeatherForecast> getWeatherForecastTypeAdapter(
            final TypeAdapter<JsonElement> elementAdapter) {

        return new TypeAdapter<WeatherForecast>() {
            @Override
            public void write(JsonWriter out, WeatherForecast value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.toString());
                }
            }

            @Override
            public WeatherForecast read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                JsonObject jo = (JsonObject) elementAdapter.read(in);

                String cityName = jo.getAsJsonObject("city").get("name").getAsString();
                int cityId = jo.getAsJsonObject("city").get("id").getAsInt();
                List<DailyWeather> dailyWeatherList = new ArrayList<>();
                JsonArray ja = jo.getAsJsonArray("list");
                for (JsonElement element : ja) {
                    JsonObject jsonObj = element.getAsJsonObject();

                    dailyWeatherList.add(new DailyWeather()
                            .setWeatherTime(jsonObj.get("dt").getAsLong())
                            .setWeatherMain(jsonObj.getAsJsonArray("weather")
                                    .get(0).getAsJsonObject().get("main").getAsString())
                            .setIconId(jsonObj.getAsJsonArray("weather")
                                    .get(0).getAsJsonObject().get("icon").getAsString())
                            .setTempMax((int)jsonObj.getAsJsonObject("temp")
                                    .get("max").getAsDouble())
                            .setTempMin((int)jsonObj.getAsJsonObject("temp")
                                    .get("min").getAsDouble())
                    );
                }

                return new WeatherForecast().setCityName(cityName)
                        .setCityId(cityId)
                        .setDailyWeatherList(dailyWeatherList);
            }
        };
    }
}
