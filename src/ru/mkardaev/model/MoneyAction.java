package ru.mkardaev.model;

import java.util.Date;

import ru.mkardaev.utils.IdGenerator;

/**
 * @author Kardaev Mihail
 */
public abstract class MoneyAction implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    protected long value;
    private Account account;
    private Category category;
    private Date creationDate;
    private String description;
    private long id;

    public MoneyAction()
    {

    }

    public MoneyAction(Account account, Category category, Long value)
    {
        this.id = IdGenerator.generateId();
        this.account = account;
        this.value = value;
        this.category = category;
        this.creationDate = new Date();
    }

    public MoneyAction(Account account, Category category, Long value, Date creationDate)
    {
        this(account, category, value);
        this.creationDate = creationDate;
    }

    public Account getAccount()
    {
        return account;
    }

    public Category getCategory()
    {
        return category;
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

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public void setCategory(Category category)
    {
        this.category = category;
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
