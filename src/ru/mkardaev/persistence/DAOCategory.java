package ru.mkardaev.persistence;

import java.sql.SQLException;
import java.util.List;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Category;

public interface DAOCategory
{
    void create(Category category) throws ApException, SQLException;

    void delete(long categoryId) throws SQLException;

    /**
     * Достаёт категорию по id
     * 
     * @param categoryId
     * @return категорию, либо null, если категории в базе нет
     * @throws SQLException
     */
    Category get(long categoryId) throws SQLException;

    List<Category> getAllCategories() throws ApException;

    void update(Category category) throws SQLException;
}