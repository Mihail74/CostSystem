package ru.mkardaev.factories.impl;

import ru.mkardaev.factories.CategoryFactory;
import ru.mkardaev.model.Category;

public class CategoryFactoryImpl implements CategoryFactory
{
    @Override
    public Category createCategory(long id, String title)
    {
        return new Category(id, title);
    }

    @Override
    public Category createCategory(String title)
    {
        return new Category(title);
    }

}
