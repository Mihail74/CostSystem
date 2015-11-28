package ru.mkardaev.model;

import java.util.Date;

public class Expense extends MoneyAction
{
    private static final long serialVersionUID = 1L;

    public Expense()
    {

    }

    public Expense(Account account, Category category, Long value)
    {
        super(account, category, value);
    }

    public Expense(Account account, Category category, Long value, Date creationDate)
    {
        super(account, category, value, creationDate);
    }

    @Override
    public String toString()
    {
        return String.format("Expense: %d", this.getId());
    }

}
