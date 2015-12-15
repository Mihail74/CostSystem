package ru.mkardaev.ui.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.models.MoneyActionUIModel;

/**
 * Провайдер данных по расходам.
 * 
 * @author Mihail
 *
 */
public class ExpenseInputProvider implements InputProvider
{

    private DAOCategory categoryDAO;
    private DAOMoneyAction moneyActionDAO;

    public ExpenseInputProvider(DAOCategory categoryDAO, DAOMoneyAction moneyActionDAO)
    {
        this.categoryDAO = categoryDAO;
        this.moneyActionDAO = moneyActionDAO;
    }

    @Override
    public Object getInput(Object... args) throws ApException
    {
        if (args.length < 2)
        {
            return new Object();
        }
        Account account = ApplicationContext.getContext().getData(ApplicationContext.CURRENT_ACCOUNT);
        Date beginDate = (Date) args[0];
        Date endDate = (Date) args[1];
        Map<Long, Category> categories = categoryDAO.getAllCategories().stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));

        List<Expense> expenses = moneyActionDAO.getByCreationDate(account, beginDate, endDate).stream()
                .filter(e -> e instanceof Expense).map(e -> (Expense) e).collect(Collectors.toList());

        List<MoneyActionUIModel> result = new ArrayList<>();
        for (Expense expense : expenses)
        {

            result.add(new MoneyActionUIModel(expense, categories.get(expense.getCategoryId())));
        }
        return result.toArray();
    }

}
