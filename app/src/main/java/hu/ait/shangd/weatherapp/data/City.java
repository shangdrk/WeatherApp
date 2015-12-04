package hu.ait.shangd.weatherapp.data;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

public class City extends SugarRecord<City>
        implements Serializable {

    private String name;
    private int cityId;

    @Ignore
    private WeatherCurrent weatherCurrent;
    @Ignore
    private WeatherForecast weatherForecast;

    public City() {}

    public City(String name, int id) {
        this.name = name;
        this.cityId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public WeatherCurrent getWeatherCurrent() {
        return weatherCurrent;
    }

    public void setWeatherCurrent(WeatherCurrent weatherCurrent) {
        this.weatherCurrent = weatherCurrent;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }
}
