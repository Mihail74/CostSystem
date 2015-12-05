package ru.mkardaev.factories.impl;

import java.util.Date;

import ru.mkardaev.factories.MoneyActionFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;
import ru.mkardaev.model.Income;
import ru.mkardaev.model.MoneyAction;

public class MoneyActionFactoryImpl implements MoneyActionFactory
{
    @Override
    public Expense createExpense(Account account, Category category, long value)
    {
        return new Expense(account.getId(), category.getId(), value);
    }

    @Override
    public Expense createExpense(Account account, Category category, long value, Date creationDate)
    {
        return new Expense(account.getId(), category.getId(), value, creationDate);
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

    @Override
    public MoneyAction createMoneyAction(long type, long id, long accountId, long categoryId, Date creationDate,
            long value, String description)
    {
        MoneyAction result = null;
        if (MoneyAction.EXPENSE_TYPE == type)
        {
            result = new Expense();
        }
        if (MoneyAction.INCOME_TYPE == type)
        {
            result = new Income();
        }
        result.setId(id);
        result.setAccountId(accountId);
        result.setCategoryId(categoryId);
        result.setCreationDate(creationDate);
        result.setValue(value);
        result.setDescription(description);
        return result;
    }
}
