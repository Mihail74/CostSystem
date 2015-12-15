package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Команда сохранения дохода
 * 
 * @author Mihail
 *
 */
public class SaveIncomeCommand extends CommandAdapter implements ICommand
{
    private DAOMoneyAction moneyActionDAO;

    public SaveIncomeCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        moneyActionDAO.create(dtObject.<MoneyAction> getProperty(ApplicationContext.INCOME));
    }
}
