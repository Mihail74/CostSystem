package ru.mkardaev.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
    /**
     * Конвертирует дату из long, в Date. Предполагается, что gmt0Long представляет собой время в миллисекундах в GMT+0. Результирующая дата будет в
     * запрошенной timezon'e.
     */
    public static Date convertToDate(long gmt0Long, TimeZone timeZone)
    {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(gmt0Long);
        calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getRawOffset());
        return calendar.getTime();
    }

    public static Date convertToDateWithDefaultTimeZone(long gmt0Long)
    {
        return convertToDate(gmt0Long, TimeZone.getDefault());
    }

    /**
     * Конвертирует дату в GMT+0, затем в long. При конвертации в GMT+0 используется заданная timeZone
     * 
     * @param date
     * @return
     */
    public static long convertToGMT0Long(Date date, TimeZone timeZone)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, -calendar.getTimeZone().getRawOffset());
        return calendar.getTime().getTime();
    }

    public static long convertToGMT0LongFromDefaultTimeZone(Date date)
    {
        return convertToGMT0Long(date, TimeZone.getDefault());
    }

}
