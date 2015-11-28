package ru.mkardaev.model;

import java.util.Date;

public class Income extends MoneyAction
{
    private static final long serialVersionUID = 1L;

    public Income()
    {
        super();
    }

    public Income(Account account, Category category, Long value)
    {
        super(account, category, value);
    }

    public Income(Account account, Category category, Long value, Date creationDate)
    {
        super(account, category, value, creationDate);
    }

    @Override
    public String toString()
    {
        return String.format("Income: %d", this.getId());
    }

}
