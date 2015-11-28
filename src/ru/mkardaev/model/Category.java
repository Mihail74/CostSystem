package ru.mkardaev.model;

import javax.xml.bind.annotation.XmlRootElement;

import ru.mkardaev.utils.IdGenerator;

@XmlRootElement
public class Category
{

    private long id;

    private String title;

    public Category()
    {
        id = IdGenerator.generateId();
    }

    public Category(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public Category(String title)
    {
        this();
        this.title = title;
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
        Category other = (Category) obj;
        return this.id == other.id;
    }

    public long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
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

    public void setTitle(String title)
    {
        this.title = title;
    }

}
