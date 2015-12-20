package ru.mkardaev.command;

import org.apache.log4j.Logger;

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
    final static Logger logger = Logger.getLogger(EditIncomeCommand.class);
    private DAOMoneyAction moneyActionDAO;

    public EditIncomeCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        MoneyAction moneyAction = dtObject.<MoneyAction> getProperty(ApplicationContext.INCOME);
        moneyActionDAO.update(moneyAction);
        logger.info(String.format("Update MoneyAction id = %d", moneyAction.getId()));
    }
}
