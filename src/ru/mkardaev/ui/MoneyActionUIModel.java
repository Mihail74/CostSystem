package ru.mkardaev.ui;

import ru.mkardaev.model.Category;
import ru.mkardaev.model.MoneyAction;

/**
 * Модель MoneyAction для отображения в таблице. Является контейнером MoneyAction и связанной с ним category
 * 
 * @author Mihail
 *
 */
public class MoneyActionUIModel
{
    public class tableColumnNumbers
    {
        public static final int COLUMN_CATEGORY = 1;
        public static final int COLUMN_DATE = 0;
        public static final int COLUMN_DESCRIPTION = 3;
        public static final int COLUMN_VALUE = 2;
    }

    private Category category;
    private MoneyAction moneyAction;

    public MoneyActionUIModel(MoneyAction moneyAction, Category category)
    {
        this.moneyAction = moneyAction;
        this.category = category;
    }

    public Category getCategory()
    {
        return category;
    }

    public MoneyAction getMoneyAction()
    {
        return moneyAction;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public void setMoneyAction(MoneyAction moneyAction)
    {
        this.moneyAction = moneyAction;
    }
}
