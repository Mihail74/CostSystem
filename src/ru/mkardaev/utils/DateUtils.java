package ru.mkardaev.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ru.mkardaev.factories.ServicesFactory;

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

    /**
     * Конвертирует дату в long GMT+0 для часового пояса из property файла
     * 
     * @param gmt0Long
     * @return
     */
    public static Date convertToDateWithDefaultTimeZone(long gmt0Long)
    {
        return convertToDate(gmt0Long,
                TimeZone.getTimeZone(Property.getInstance().getProperty(Property.Keys.TIME_ZONE)));
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

    /**
     * Конвертирует дату в long GMT+0. При конвертации в GMT+0 используется часовой пояс из property файла
     * 
     * @param gmt0Long
     * @return
     */
    public static long convertToGMT0LongFromDefaultTimeZone(Date date)
    {
        return convertToGMT0Long(date,
                TimeZone.getTimeZone(ServicesFactory.getInstance().getProperty().getProperty(Property.Keys.TIME_ZONE)));
    }

}
