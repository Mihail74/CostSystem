package ru.mkardaev.factories;

import java.util.Date;

import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;
import ru.mkardaev.model.Income;
import ru.mkardaev.model.MoneyAction;

public interface MoneyActionFactory
{

    /**
     * Создаёт затрату с текущей датой создания.
     * 
     * @param account - account на котором произошла затрара
     * @param category - категория затраты
     * @param value - величина затраты
     */
    Expense createExpense(Account account, Category category, double value);

    /**
     * См. {@link #createExpense(Account, Category, Long)}, только дата создания - creationDate
     */
    Expense createExpense(Account account, Category category, double value, Date creationDate);

    /**
     * Создать доход с текущей датой создания.
     * 
     * @param account - account для которого произошёл доход
     * @param category - категория дохода
     * @param value - величина дохода
     */
    Income createIncome(Account account, Category category, double value);

    /**
     * См. {@link #createIncome(Account, Category, Long)}, только дата создания - creationDate
     */
    Income createIncome(Account account, Category category, double value, Date creationDate);

    MoneyAction createMoneyAction(long type, long id, long accountId, long categoryId, Date creationDate, double value,
            String description);
}
