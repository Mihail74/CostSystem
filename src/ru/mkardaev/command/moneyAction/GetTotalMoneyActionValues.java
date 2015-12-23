package ru.mkardaev.command.moneyAction;

import java.util.Date;
import java.util.List;

import ru.mkardaev.command.CommandAdapter;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Expense;
import ru.mkardaev.model.Income;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Команда получения суммарных затрат и доходов. Результат проставляется в dtObject
 * 
 * @author Mihail
 *
 */
public class GetTotalMoneyActionValues extends CommandAdapter implements ICommand
{
    private DAOMoneyAction moneyActionDAO;

    public GetTotalMoneyActionValues()
    {
        moneyActionDAO = ServicesFactory.getInstance().getDaoMoneyAction();
    }

    @Override
    public void perform() throws ApException
    {
        if (dtObject != null)
        {
            Date beginDate = dtObject.<Date> getProperty(ApplicationContext.FROM_DATE);
            Date endDate = dtObject.<Date> getProperty(ApplicationContext.TO_DATE);
            Account account = ApplicationContext.getContext().getData(ApplicationContext.CURRENT_ACCOUNT);

            List<MoneyAction> moneyActions = moneyActionDAO.getByCreationDate(account, beginDate, endDate);
            Double expenseValue = moneyActions.stream().filter(e -> e instanceof Expense)
                    .mapToDouble(MoneyAction::getValue).sum();
            Double incomeValue = moneyActions.stream().filter(e -> e instanceof Income)
                    .mapToDouble(MoneyAction::getValue).sum();

            dtObject.putProperty(ApplicationContext.TOTAL_INCOME_VALUE, incomeValue);
            dtObject.putProperty(ApplicationContext.TOTAL_EXPENSE_VALUE, expenseValue);
        }
    }
}
