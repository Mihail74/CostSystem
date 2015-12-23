package ru.mkardaev.command.expense;

import org.apache.log4j.Logger;

import ru.mkardaev.command.CommandAdapter;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Команда сохранения затраты
 * 
 * @author Mihail
 *
 */
public class SaveExpenseCommand extends CommandAdapter implements ICommand
{
    final static Logger logger = Logger.getLogger(SaveExpenseCommand.class);
    private DAOMoneyAction moneyActionDAO;

    public SaveExpenseCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        MoneyAction moneyAction = dtObject.<MoneyAction> getProperty(ApplicationContext.EXPENSE);
        moneyActionDAO.create(moneyAction);
        logger.info(String.format("Create MoneyAction id = %d", moneyAction.getId()));
    }
}
