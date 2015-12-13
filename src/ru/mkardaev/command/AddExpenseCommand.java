package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;

public class AddExpenseCommand implements ICommand
{

    private DtObject dtObject;
    private DAOMoneyAction moneyActionDAO;

    public AddExpenseCommand()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        moneyActionDAO.create(dtObject.<MoneyAction> getProperty(ApplicationContext.EXPENSE));
    }

    @Override
    public void setDtObject(DtObject dtObject)
    {
        this.dtObject = dtObject;
    }
}
