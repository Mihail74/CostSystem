package ru.mkardaev.model;

public class Person
{
    private long accountId;
    private long id = 0L;

    public Person(long id, long accountId)
    {
        this.id = id;
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
