package ru.mkardaev.services.expense;

import java.util.Date;

import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;

/**
 * Сервис для работы с затратами
 * 
 * @author Mihail
 *
 */
public interface ExpenseService
{
    /**
     * Зафиксировать затрату expense
     */
    void commintExpense(Expense expense);

    /**
     * Создать затрату с текущей датой.
     * 
     * @param account - account на котором произошла затрара
     * @param category - категория затраты
     * @param value - величина затраты
     */
    Expense createExpense(Account account, Category category, Long value);

    /**
     * См. {@link #createExpense(Account, Category, Long)}, только дата затраты - creationDate
     */
    Expense createExpense(Account account, Category category, Long value, Date creationDate);
}
