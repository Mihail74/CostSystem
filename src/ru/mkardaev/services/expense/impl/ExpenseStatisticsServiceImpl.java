package ru.mkardaev.services.expense.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
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
    public List<Expense> getExpenses(Account account, Date startDate, Date endDate) throws ApException
    {
        return ServicesFactory.getInstance().getDaoMoneyAction().getByCreationDate(account, startDate, endDate).stream()
                .filter(e -> e instanceof Expense).map(e -> (Expense) e).collect(Collectors.toList());
    }

    @Override
    public List<Expense> getExpenses(Account account, Date startDate, Date endDate, Category category)
            throws ApException
    {
        return getExpenses(account, startDate, endDate).stream().filter(e -> e.getCategoryId() == category.getId())
                .collect(Collectors.toList());
    }

    @Override
    public double getExpenseValue(Account account, Date startDate, Date endDate) throws ApException
    {
        List<Expense> expenses = getExpenses(account, startDate, endDate);
        return getTotalExpenseValue(expenses);
    }

    @Override
    public double getExpenseValue(Account account, Date startDate, Date endDate, Category category) throws ApException
    {
        List<Expense> expenses = getExpenses(account, startDate, endDate, category);
        return getTotalExpenseValue(expenses);
    }

    /**
     * @param expenses - список затрат
     * @return суммарную величину затрат
     */
    private double getTotalExpenseValue(List<Expense> expenses)
    {
        double totalExpenseValue = 0L;

        for (Expense expense : expenses)
        {
            totalExpenseValue += expense.getValue();
        }

        return totalExpenseValue;
    }
}
