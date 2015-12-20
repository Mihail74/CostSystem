package ru.mkardaev.ui.utils;

import java.util.ArrayList;
import java.util.List;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Category;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.utils.Messages;

/**
 * Провайдер категорий для использования во Viewer
 * 
 * @author Mihail
 *
 */
public class CategoryInputProvider implements InputProvider
{
    private DAOCategory categoryDAO;
    private Messages messages;

    public CategoryInputProvider()
    {
        this.messages = ServicesFactory.getInstance().getMessages();
        this.categoryDAO = ServicesFactory.getInstance().getDaoCategory();
    }

    @Override
    public Object getInput(Object... args) throws ApException
    {
        Category add = new Category(Category.NEW_CATEGORY_ID, messages.getMessage(Messages.Keys.NEW_CATEGORY));
        List<Category> result = new ArrayList<>();
        result.add(add);
        result.addAll(categoryDAO.getAllCategories());
        return result.toArray();
    }
}
