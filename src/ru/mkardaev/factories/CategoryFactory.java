package ru.mkardaev.factories;

import ru.mkardaev.model.Category;

public class CategoryFactory
{
    public static Category createCategory()
    {
        return new Category();
    }

    public static Category createCategory(long id, String title)
    {
        return new Category(id, title);
    }

    public static Category createCategory(String title)
    {
        return new Category(title);
    }
}
