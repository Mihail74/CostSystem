package ru.mkardaev.services.income.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.mkardaev.DataInMemory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;
import ru.mkardaev.services.income.IncomeStatisticsService;

public class IncomeStatisticsServiceImpl implements IncomeStatisticsService
{
	@Override
	public List<Income> getIncomes(Account account, Date startDate, Date endDate)
	{
		return DataInMemory.moneyActions.stream().filter(e -> e instanceof Income)
				.map(e -> (Income) e).filter(e -> e.getAccount().equals(account)
						&& !e.getCreationDate().before(startDate) && !e.getCreationDate().after(endDate))
				.collect(Collectors.toList());
	}

	@Override
	public List<Income> getIncomes(Account account, Date startDate, Date endDate, Category category)
	{
		return getIncomes(account, startDate, endDate).stream().filter(e -> e.getCategory().equals(category))
				.collect(Collectors.toList());
	}

	@Override
	public Long getIncomeValue(Account account, Date startDate, Date endDate)
	{
		List<Income> expenses = getIncomes(account, startDate, endDate);
		return getTotalIncomeValue(expenses);
	}

	@Override
	public Long getIncomeValue(Account account, Date startDate, Date endDate, Category category)
	{
		List<Income> expenses = getIncomes(account, startDate, endDate, category);
		return getTotalIncomeValue(expenses);
	}

	/**
	 * @param incomes - список доходов
	 * @return суммарную величину дохода
	 */
	private Long getTotalIncomeValue(List<Income> incomes)
	{
		Long totalIncomeValue = 0L;

		for (Income income : incomes)
		{
			totalIncomeValue += income.getValue();
		}

		return totalIncomeValue;
	}

}
