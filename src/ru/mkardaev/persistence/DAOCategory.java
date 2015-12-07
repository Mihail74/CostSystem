package ru.mkardaev.persistence;

import java.util.List;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Category;

public interface DAOCategory extends DAO<Category>
{
    List<Category> getAllCategories() throws ApException;
}
