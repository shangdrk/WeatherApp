package hu.ait.shangd.weatherapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DailyWeather implements Serializable {

    @SerializedName("weatherTime")
    @Expose
    private long weatherTime;
    @SerializedName("tempMax")
    @Expose
    private int tempMax;
    @SerializedName("tempMax")
    @Expose
    private int tempMin;
    @SerializedName("weatherMain")
    @Expose
    private String weatherMain;
    @SerializedName("iconId")
    @Expose
    private String iconId;

    public long getWeatherTime() {
        return weatherTime;
    }

    public DailyWeather setWeatherTime(long weatherTime) {
        this.weatherTime = weatherTime;
        return this;
    }

    public int getTempMax() {
        return tempMax;
    }

    public DailyWeather setTempMax(int tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public int getTempMin() {
        return tempMin;
    }

    public DailyWeather setTempMin(int tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public DailyWeather setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
        return this;
    }

    public String getIconId() {
        return iconId;
    }

    public DailyWeather setIconId(String iconId) {
        this.iconId = iconId;
        return this;
    }
}
