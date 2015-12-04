package hu.ait.shangd.weatherapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WeatherForecast implements Serializable {
    // example call: http://api.openweathermap.org/data/2.5/forecast?q=London&units=metric&appid=2de143494c0b295cca9337e1e96b00e0

    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("cityId")
    @Expose
    private int cityId;
    @SerializedName("dailyWeatherList")
    @Expose
    private List<DailyWeather> dailyWeatherList;

    public String getCityName() {
        return cityName;
    }

    public WeatherForecast setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public int getCityId() {
        return cityId;
    }

    public WeatherForecast setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public List<DailyWeather> getDailyWeatherList() {
        return dailyWeatherList;
    }

    public WeatherForecast setDailyWeatherList(List<DailyWeather> dailyWeatherList) {
        this.dailyWeatherList = dailyWeatherList;
        return this;
    }
}
