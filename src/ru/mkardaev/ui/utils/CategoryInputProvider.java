package ru.mkardaev.ui.utils;

import java.util.List;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Category;
import ru.mkardaev.persistence.DAOCategory;

/**
 * Провайдер категорий для использования во Viewer
 * 
 * @author Mihail
 *
 */
public class CategoryInputProvider implements InputProvider
{
    private DAOCategory categoryDAO;

    public CategoryInputProvider(DAOCategory categoryDAO)
    {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Object getInput(Object... args) throws ApException
    {
        List<Category> categories = categoryDAO.getAllCategories();
        return categories.toArray();
    }
}
