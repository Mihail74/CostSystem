package ru.mkardaev.services.expense.impl;

import java.util.Date;

import ru.mkardaev.DataInMemory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;
import ru.mkardaev.services.expense.ExpenseService;

public class ExpenseServiceImpl implements ExpenseService
{

	@Override
	public void commintExpense(Expense expense)
	{
		DataInMemory.moneyActions.add(expense);
	}

	@Override
	public Expense createExpense(Account account, Category category, Long value)
	{
		return new Expense(account, category, value);
	}

	@Override
	public Expense createExpense(Account account, Category category, Long value, Date creationDate)
	{
		return new Expense(account, category, value, creationDate);
	}

}
