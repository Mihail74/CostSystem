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
