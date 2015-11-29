package ru.mkardaev.model;

import java.util.Date;

public class Income extends MoneyAction
{
    private static final long serialVersionUID = 1L;

    public Income()
    {
        super();
    }

    public Income(long accountId, long categoryId, long value)
    {
        super(accountId, categoryId, value);
    }

    public Income(long accountId, long categoryId, long value, Date creationDate)
    {
        super(accountId, categoryId, value, creationDate);
    }

    @Override
    public String toString()
    {
        return String.format("Income: %d", getId());
    }

}
