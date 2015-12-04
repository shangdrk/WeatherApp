package hu.ait.shangd.weatherapp.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import hu.ait.shangd.weatherapp.R;
import hu.ait.shangd.weatherapp.data.City;
import hu.ait.shangd.weatherapp.data.DailyWeather;

public final class Utility {

    public static String unixLongToTime(String timeZoneId, long millis, boolean hour24) {
        SimpleDateFormat sdf;
        if (hour24) {
            sdf = new SimpleDateFormat("HH:mm");
        } else {
            sdf = new SimpleDateFormat("hh:mm a");
        }

        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        return sdf.format(new Date(millis*1000L));
    }

    public static boolean isSameDay(String timeZoneId, long millis1, long millis2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        return sdf.format(new Date(millis1*1000L))
                .equals(sdf.format(new Date(millis2 * 1000L)));
    }

    public static String getDayInTheWeek(String timeZoneId, long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        return sdf.format(new Date(millis * 1000L));
    }

    public static String getIconUrl(Context context, City city) {
        return String.format(context.getString(R.string.icon_url),
                city.getWeatherCurrent().getIconId()).concat(".png");
    }

    public static String getIconUrl(Context context, DailyWeather forecast) {
        return String.format(context.getString(R.string.icon_url),
                forecast.getIconId()).concat(".png");
    }
}
