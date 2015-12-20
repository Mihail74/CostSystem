package ru.mkardaev.factories;

import ru.mkardaev.model.Category;

public interface CategoryFactory
{
    Category createCategory(long id, String title);

    Category createCategory(String title);
}
