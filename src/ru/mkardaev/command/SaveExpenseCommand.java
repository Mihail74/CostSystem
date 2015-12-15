package ru.mkardaev.command;

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
    private DAOMoneyAction moneyActionDAO;

    public SaveExpenseCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        moneyActionDAO.create(dtObject.<MoneyAction> getProperty(ApplicationContext.EXPENSE));
    }
}
