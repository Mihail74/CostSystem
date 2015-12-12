package ru.mkardaev.ui.utils.category;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.ui.InputProvider;

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
    public Object getInput() throws ApException
    {
        return categoryDAO.getAllCategories().toArray();
    }
}
