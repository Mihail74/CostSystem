package ru.mkardaev.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
    /**
     * Конвертирует дату из long, в Date. Подразумевается, что gmt0Long представляет собой время в миллисекундах в GMT+0. Результирующая дата будет в
     * default timezon'e.
     * 
     * @param gmt0Long
     * @return
     */
    public static Date convertToDate(long gmt0Long)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(gmt0Long);
        calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getRawOffset());
        return calendar.getTime();
    }

    /**
     * Конвертирует дату в GMT+0, затем в long. При конвертации в GMT+0 используется TimeZone.getDefault();
     * 
     * @param date
     * @return
     */
    public static long convertToGMT0Long(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, -calendar.getTimeZone().getRawOffset());
        return calendar.getTime().getTime();
    }

}
