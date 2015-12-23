package ru.mkardaev.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.ApplicationContext;

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
        Property properties = ServicesFactory.getInstance().getProperty();
        return convertToDate(gmt0Long, TimeZone.getTimeZone(properties.getProperty(Property.Keys.TIME_ZONE)));
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
        Property properties = ServicesFactory.getInstance().getProperty();
        return convertToGMT0Long(date, TimeZone.getTimeZone(properties.getProperty(Property.Keys.TIME_ZONE)));
    }

    /**
     * Возвращает Calendar с заданной датой.
     * 
     * @return
     */
    public static Calendar getCaledar(Date date)
    {
        String TimeZoneId = ApplicationContext.getContext().<String> getData(Property.Keys.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZoneId));
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 
     * @return Calendar с текущей датой
     */
    public static Calendar getCurrentDate()
    {
        return getCaledar(new Date());
    }

    /**
     * 
     * @param year
     * @param month
     * @param day
     * @return Возвращает Date с заданными годом, месяцем, днём
     */
    public static Date getDate(int year, int month, int day)
    {
        String TimeZoneId = ApplicationContext.getContext().<String> getData(Property.Keys.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZoneId));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 
     * @param date
     * @return Возвращает конца дня, т.е. для даты устанавливается время 23:59:59
     */
    public static Date getEndDayOfDate(Date date)
    {
        String TimeZoneId = ApplicationContext.getContext().<String> getData(Property.Keys.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZoneId));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 
     * @param date
     * @return Возвращает начало дня, т.е. выставляет часы минуты и секунды в 0.
     */
    public static Date getStartDayOfDate(Date date)
    {
        String TimeZoneId = ApplicationContext.getContext().<String> getData(Property.Keys.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZoneId));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
