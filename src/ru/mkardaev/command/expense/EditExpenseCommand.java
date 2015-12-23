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
 * Команда редактирования затраты
 * 
 * @author Mihail
 *
 */
public class EditExpenseCommand extends CommandAdapter implements ICommand
{
    final static Logger logger = Logger.getLogger(EditExpenseCommand.class);
    private DAOMoneyAction moneyActionDAO;

    public EditExpenseCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        MoneyAction moneyAction = dtObject.<MoneyAction> getProperty(ApplicationContext.EXPENSE);
        moneyActionDAO.update(moneyAction);
        logger.info(String.format("Update MoneyAction id = %d", moneyAction.getId()));
    }
}
