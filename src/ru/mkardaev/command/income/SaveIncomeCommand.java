package ru.mkardaev.command.income;

import org.apache.log4j.Logger;

import ru.mkardaev.command.CommandAdapter;
import ru.mkardaev.command.ICommand;
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
    final static Logger logger = Logger.getLogger(SaveIncomeCommand.class);
    private DAOMoneyAction moneyActionDAO;

    public SaveIncomeCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        MoneyAction moneyAction = dtObject.<MoneyAction> getProperty(ApplicationContext.INCOME);
        moneyActionDAO.create(moneyAction);
        logger.info(String.format("Create MoneyAction id = %d", moneyAction.getId()));
    }
}
