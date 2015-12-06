package ru.mkardaev.model;

import java.util.Date;

public class Expense extends MoneyAction
{
    private static final long serialVersionUID = 1L;

    public Expense()
    {

    }

    public Expense(long accountId, long categoryId, Long value)
    {
        super(accountId, categoryId, value);
    }

    public Expense(long accountId, long categoryId, Long value, Date creationDate)
    {
        super(accountId, categoryId, value, creationDate);
    }

    @Override
    public String toString()
    {
        return String.format("Expense: %d", this.getId());
    }

}