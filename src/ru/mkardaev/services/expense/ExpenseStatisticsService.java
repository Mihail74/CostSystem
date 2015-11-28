package ru.mkardaev.services.expense;

import java.util.Date;
import java.util.List;

import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;

/**
 * Сервис для сбора статистики по затратам
 * 
 * @author Kardaev Mihail
 */
public interface ExpenseStatisticsService
{
    /**
     * @return Список затрат account со startDate по endDate
     */
    List<Expense> getExpenses(Account account, Date startDate, Date endDate);

    /**
     * @return Список затрат account со startDate по endDate для категории category
     */
    List<Expense> getExpenses(Account account, Date startDate, Date endDate, Category category);

    /**
     * @return суммарные расходы account в период со startDate по endDate
     */
    Long getExpenseValue(Account account, Date startDate, Date endDate);

    /**
     * 
     * @return суммарные расходы account в период со startDate по endDate для категории category для account
     */
    Long getExpenseValue(Account account, Date startDate, Date endDate, Category category);
}
