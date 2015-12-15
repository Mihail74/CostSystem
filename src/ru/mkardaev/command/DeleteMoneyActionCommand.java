package ru.mkardaev.command;

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
    private DAOMoneyAction moneyActionDAO;

    public DeleteMoneyActionCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        moneyActionDAO.delete(dtObject.<Long> getProperty(ApplicationContext.MONEY_ACTION_ID));
    }
}
