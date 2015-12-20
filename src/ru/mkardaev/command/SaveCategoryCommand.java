package ru.mkardaev.command;

import org.apache.log4j.Logger;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Category;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Команда сохранения категории
 * 
 * @author Mihail
 *
 */
public class SaveCategoryCommand extends CommandAdapter implements ICommand
{
    final static Logger logger = Logger.getLogger(SaveCategoryCommand.class);
    private DAOCategory categoryDAO;

    public SaveCategoryCommand()
    {
        categoryDAO = ServicesFactory.getInstance().getDaoCategory();
    }

    @Override
    public void perform() throws ApException
    {
        Category category = dtObject.<Category> getProperty(ApplicationContext.CATEGORY);
        categoryDAO.create(category);
        logger.info(String.format("Create category id = %d", category.getId()));
    }
}
