package ru.mkardaev.services.income;

import java.util.Date;
import java.util.List;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;

public interface IncomeStatisticsService
{
    /**
     * @return Список доходов account со startDate по endDate
     * @throws ApException
     */
    List<Income> getIncomes(Account account, Date startDate, Date endDate) throws ApException;

    /**
     * @return Список доходов account со startDate по endDate для категории category
     */
    List<Income> getIncomes(Account account, Date startDate, Date endDate, Category category) throws ApException;

    /**
     * @return суммарные доходы в период со startDate по endDate для account
     */
    Long getIncomeValue(Account account, Date startDate, Date endDate) throws ApException;

    /**
     * 
     * @return суммарные доходы в период со startDate по endDate для категории category для account
     */
    Long getIncomeValue(Account account, Date startDate, Date endDate, Category category) throws ApException;
}
