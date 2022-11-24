package ga.cv3sarato.android.utils;

import android.content.Context;

import ga.cv3sarato.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_MONTH_DAY_TIME = "MM-dd HH:mm";
    public final static String FORMAT_DATE = "yyyy-MM-dd";

    public static String getFormatDate(String dateFormat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(currentTime);
    }

    public static String getFormatDate(String dateFormat, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static Date stringToDate(String dateStr, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static String getTimeInChat(Context context, long timestamp) {
        String result;
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timestamp);
        int dayOffset = Integer.parseInt(dayFormat.format(today)) - Integer.parseInt(dayFormat.format(otherDay));
        int yearOffset = Integer.parseInt(yearFormat.format(today)) - Integer.parseInt(yearFormat.format(otherDay));
        switch (dayOffset) {
            case 0:
                result = context.getResources().getString(R.string.chat_today) + " " + getHourAndMin(timestamp);
                break;
            case 1:
                result = context.getResources().getString(R.string.chat_yesterday) + " " + getHourAndMin(timestamp);
                break;
            default:
                result = getTime(yearOffset != 0,timestamp);
                break;
        }
        return result;
    }

    private static String getTime(boolean hasYear, long time) {
        String pattern = FORMAT_DATE_TIME;
        if(!hasYear){
            pattern = FORMAT_MONTH_DAY_TIME;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    private static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(new Date(time));
    }


}
