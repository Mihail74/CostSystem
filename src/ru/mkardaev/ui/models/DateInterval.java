package ru.mkardaev.ui.models;

import java.util.Date;

/**
 * Класс, описывающий интервал времени
 * 
 * @author Mihail
 *
 */
public class DateInterval
{
    private Date fromDate;
    private Date toDate;

    public DateInterval()
    {

    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }

}
