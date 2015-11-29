package ru.mkardaev.model;

import ru.mkardaev.utils.IdGenerator;

public class Person
{
    private long accountId;
    private long id;

    public Person(long accountId)
    {
        this.id = IdGenerator.generateId();
        this.accountId = accountId;
    }

    public long getAccountId()
    {
        return accountId;
    }

    public long getId()
    {
        return id;
    }

    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
