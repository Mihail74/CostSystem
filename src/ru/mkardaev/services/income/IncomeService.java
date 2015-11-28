package ru.mkardaev.services.income;

import java.util.Date;

import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;

public interface IncomeService
{
    /**
     * Зафиксировать доход
     */
    void commintIncome(Income income);

    /**
     * Создать доход с текущей датой.
     * 
     * @param account - account для которого произошёл доход
     * @param category - категория дохода
     * @param value - величина дохода
     */
    Income createIncome(Account account, Category category, Long value);

    /**
     * См. {@link #createIncome(Account, Category, Long)}, только дата дохода - creationDate
     */
    Income createIncome(Account account, Category category, Long value, Date creationDate);
}
