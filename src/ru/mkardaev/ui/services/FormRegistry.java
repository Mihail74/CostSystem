package ru.mkardaev.ui.services;

import java.util.HashMap;
import java.util.Map;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.form.AddExpenseForm;
import ru.mkardaev.ui.form.AddIncomeForm;
import ru.mkardaev.ui.utils.category.CategoryInputProvider;

/**
 * Реестр настроенных для использования форм
 * 
 * @author Mihail
 *
 */
public class FormRegistry
{
    public static final String ADD_EXPENSE_FORM = "addExpeneForm";
    public static final String ADD_INCOME_FORM = "addIncomeForm";
    private static FormRegistry instancte;

    private Map<String, Object> registry = new HashMap<>();

    private FormRegistry()
    {
        AddIncomeForm addIncomeForm = new AddIncomeForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        // addIncomeForm.setSaveAction(saveAction);
        registry.put(ADD_INCOME_FORM, addIncomeForm);

        AddExpenseForm addExpenseForm = new AddExpenseForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        // addExpenseForm.setSaveAction(saveAction);
        registry.put(ADD_EXPENSE_FORM, addExpenseForm);
    }

    @SuppressWarnings("unchecked")
    public <T> T getForm(String key)
    {
        return (T) registry.get(key);
    }

    public static FormRegistry getInstance()
    {
        if (instancte == null)
        {
            instancte = new FormRegistry();
        }
        return instancte;
    }
}
