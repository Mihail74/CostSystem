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
	public Income createIncome(Account account, Category category, Long value)
	{
		return new Income(account, category, value);
	}

	@Override
	public Income createIncome(Account account, Category category, Long value, Date creationDate)
	{
		return new Income(account, category, value, creationDate);
	}

}
