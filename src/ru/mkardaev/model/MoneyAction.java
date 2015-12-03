package ru.mkardaev.model;

import java.util.Date;

/**
 * @author Kardaev Mihail
 */
public abstract class MoneyAction implements java.io.Serializable
{
    public static final int EXPENSE_TYPE = 2;
    public static final int INCOME_TYPE = 1;
    private static final long serialVersionUID = 1L;

    protected long value;
    private long accountId;
    private long categoryId;
    private Date creationDate;
    private String description;
    private long id;

    public MoneyAction()
    {

    }

    public MoneyAction(long accountId, long categoryId, long value)
    {
        this.accountId = accountId;
        this.value = value;
        this.categoryId = categoryId;
        this.creationDate = new Date();
    }

    public MoneyAction(long accountId, long categoryId, long value, Date creationDate)
    {
        this(accountId, categoryId, value);
        this.creationDate = creationDate;
    }

    public long getAccountId()
    {
        return accountId;
    }

    public long getCategoryId()
    {
        return categoryId;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public String getDescription()
    {
        return description;
    }

    public long getId()
    {
        return id;
    }

    public long getValue()
    {
        return value;
    }

    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    public void setCategoryId(long categoryId)
    {
        this.categoryId = categoryId;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
