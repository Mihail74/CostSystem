package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Команда редактирования дохода
 * 
 * @author Mihail
 *
 */
public class EditIncomeCommand extends CommandAdapter implements ICommand
{
    private DAOMoneyAction moneyActionDAO;

    public EditIncomeCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        moneyActionDAO.update(dtObject.<MoneyAction> getProperty(ApplicationContext.INCOME));
    }
}
