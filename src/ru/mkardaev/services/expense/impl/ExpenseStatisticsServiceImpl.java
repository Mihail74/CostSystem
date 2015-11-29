package ru.mkardaev.services.expense.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.mkardaev.DataInMemory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;
import ru.mkardaev.services.expense.ExpenseStatisticsService;

/**
 * 
 * @author Kardaev Mihail
 *
 */
public class ExpenseStatisticsServiceImpl implements ExpenseStatisticsService
{
    @Override
    public List<Expense> getExpenses(Account account, Date startDate, Date endDate)
    {
        return DataInMemory.moneyActions.stream().filter(e -> e instanceof Expense)
                .map(e -> (Expense) e).filter(e -> e.getAccountId() == account.getId()
                        && !e.getCreationDate().before(startDate) && !e.getCreationDate().after(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> getExpenses(Account account, Date startDate, Date endDate, Category category)
    {
        return getExpenses(account, startDate, endDate).stream().filter(e -> e.getCategoryId() == category.getId())
                .collect(Collectors.toList());
    }

    @Override
    public Long getExpenseValue(Account account, Date startDate, Date endDate)
    {
        List<Expense> expenses = getExpenses(account, startDate, endDate);
        return getTotalExpenseValue(expenses);
    }

    @Override
    public Long getExpenseValue(Account account, Date startDate, Date endDate, Category category)
    {
        List<Expense> expenses = getExpenses(account, startDate, endDate, category);
        return getTotalExpenseValue(expenses);
    }

    /**
     * @param expenses - список затрат
     * @return суммарную величину затрат
     */
    private Long getTotalExpenseValue(List<Expense> expenses)
    {
        Long totalExpenseValue = 0L;

        for (Expense expense : expenses)
        {
            totalExpenseValue += expense.getValue();
        }

        return totalExpenseValue;
    }
}
