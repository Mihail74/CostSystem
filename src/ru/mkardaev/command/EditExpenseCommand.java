package ru.mkardaev.command;

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
    private DAOMoneyAction moneyActionDAO;

    public EditExpenseCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        moneyActionDAO.update(dtObject.<MoneyAction> getProperty(ApplicationContext.EXPENSE));
    }
}
