package ru.mkardaev.persistence;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Category;

public interface DAOCategory
{
    void create(Category category) throws ApException;

    void delete(long categoryId);

    Category get(long categoryId);

    void update(Category category);
}
