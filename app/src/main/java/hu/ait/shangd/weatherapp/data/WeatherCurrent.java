package hu.ait.shangd.weatherapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherCurrent implements Serializable {
    // example call: http://api.openweathermap.org/data/2.5/weather?q=London,uk&units=metric&appid=2de143494c0b295cca9337e1e96b00e0

    public WeatherCurrent() {}

    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("cityId")
    @Expose
    private int cityId;
    @SerializedName("cityCountry")
    @Expose
    private String cityCountry;
    @SerializedName("cityLon")
    @Expose
    private double cityLon;
    @SerializedName("cityLat")
    @Expose
    private double cityLat;
    @SerializedName("citySurise")
    @Expose
    private long citySunrise;
    @SerializedName("citySunset")
    @Expose
    private long citySunset;

    @SerializedName("weatherTime")
    @Expose
    private long weatherTime;
    @SerializedName("weatherMain")
    @Expose
    private String weatherMain;
    @SerializedName("iconId")
    @Expose
    private String iconId;
    @SerializedName("temp")
    @Expose
    private int temp;
    @SerializedName("pressure")
    @Expose
    private int pressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;


    public String getCityName() {
        return cityName;
    }

    public WeatherCurrent setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public int getCityId() {
        return cityId;
    }

    public WeatherCurrent setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public WeatherCurrent setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
        return this;
    }

    public double getCityLon() {
        return cityLon;
    }

    public WeatherCurrent setCityLon(double cityLon) {
        this.cityLon = cityLon;
        return this;
    }

    public double getCityLat() {
        return cityLat;
    }

    public WeatherCurrent setCityLat(double cityLat) {
        this.cityLat = cityLat;
        return this;
    }

    public long getCitySunrise() {
        return citySunrise;
    }

    public WeatherCurrent setCitySunrise(long citySunrise) {
        this.citySunrise = citySunrise;
        return this;
    }

    public long getCitySunset() {
        return citySunset;
    }

    public WeatherCurrent setCitySunset(long citySunset) {
        this.citySunset = citySunset;
        return this;
    }

    public long getWeatherTime() {
        return weatherTime;
    }

    public WeatherCurrent setWeatherTime(long weatherTime) {
        this.weatherTime = weatherTime;
        return this;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public WeatherCurrent setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
        return this;
    }

    public String getIconId() {
        return iconId;
    }

    public WeatherCurrent setIconId(String iconId) {
        this.iconId = iconId;
        return this;
    }

    public int getTemp() {
        return temp;
    }

    public WeatherCurrent setTemp(int temp) {
        this.temp = temp;
        return this;
    }

    public int getPressure() {
        return pressure;
    }

    public WeatherCurrent setPressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    public int getHumidity() {
        return humidity;
    }

    public WeatherCurrent setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }
}
