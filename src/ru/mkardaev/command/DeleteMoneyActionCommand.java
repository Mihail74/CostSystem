package ru.mkardaev.command;

import org.apache.log4j.Logger;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Команда удаления MoneyAction
 * 
 * @author Mihail
 *
 */
public class DeleteMoneyActionCommand extends CommandAdapter implements ICommand
{
    final static Logger logger = Logger.getLogger(DeleteMoneyActionCommand.class);
    private DAOMoneyAction moneyActionDAO;

    public DeleteMoneyActionCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        long id = dtObject.<Long> getProperty(ApplicationContext.MONEY_ACTION_ID);
        moneyActionDAO.delete(id);
        logger.info(String.format("Delete MoneyAction id = %d", id));
    }
}
