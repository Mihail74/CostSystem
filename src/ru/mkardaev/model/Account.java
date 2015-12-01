package ru.mkardaev.model;

public class Account
{
    private long id;
    private long value;

    public Account(long id)
    {
        this(id, 0L);
    }

    public Account(long id, long value)
    {
        this.id = id;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (!(obj instanceof Category))
            return false;
        Account other = (Account) obj;
        return this.id == other.id;
    }

    public long getId()
    {
        return id;
    }

    public Long getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id);
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setValue(long value)
    {
        this.value = value;
    }
}
