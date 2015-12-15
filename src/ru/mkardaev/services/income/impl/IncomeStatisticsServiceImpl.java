package ru.mkardaev.services.income.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;
import ru.mkardaev.services.income.IncomeStatisticsService;

public class IncomeStatisticsServiceImpl implements IncomeStatisticsService
{
    @Override
    public List<Income> getIncomes(Account account, Date startDate, Date endDate) throws ApException
    {
        return ServicesFactory.getInstance().getDaoMoneyAction().getByCreationDate(account, startDate, endDate).stream()
                .filter(e -> e instanceof Income)
                .map(e -> (Income) e).filter(e -> e.getAccountId() == account.getId()
                        && !e.getCreationDate().before(startDate) && !e.getCreationDate().after(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Income> getIncomes(Account account, Date startDate, Date endDate, Category category) throws ApException
    {
        return getIncomes(account, startDate, endDate).stream().filter(e -> e.getCategoryId() == category.getId())
                .collect(Collectors.toList());
    }

    @Override
    public double getIncomeValue(Account account, Date startDate, Date endDate) throws ApException
    {
        List<Income> expenses = getIncomes(account, startDate, endDate);
        return getTotalIncomeValue(expenses);
    }

    @Override
    public double getIncomeValue(Account account, Date startDate, Date endDate, Category category) throws ApException
    {
        List<Income> expenses = getIncomes(account, startDate, endDate, category);
        return getTotalIncomeValue(expenses);
    }

    /**
     * @param incomes - список доходов
     * @return суммарную величину дохода
     */
    private double getTotalIncomeValue(List<Income> incomes)
    {
        double totalIncomeValue = 0L;

        for (Income income : incomes)
        {
            totalIncomeValue += income.getValue();
        }

        return totalIncomeValue;
    }

}
