package ru.mkardaev.services.income.impl;

import java.util.Date;

import ru.mkardaev.DataInMemory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;
import ru.mkardaev.services.income.IncomeService;

public class IncomeServiceImpl implements IncomeService
{

    @Override
    public void commintIncome(Income income)
    {
        DataInMemory.moneyActions.add(income);
    }

    @Override
    public Income createIncome(Account account, Category category, long value)
    {
        return new Income(account.getId(), category.getId(), value);
    }

    @Override
    public Income createIncome(Account account, Category category, long value, Date creationDate)
    {
        return new Income(account.getId(), category.getId(), value, creationDate);
    }

}
