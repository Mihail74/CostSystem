package ru.mkardaev.ui.form;

import java.util.HashMap;
import java.util.Map;

import ru.mkardaev.command.AddExpenseCommand;
import ru.mkardaev.command.AddIncomeCommand;
import ru.mkardaev.command.EditExpenseCommand;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.utils.CategoryInputProvider;

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
    public static final String EDIT_EXPENSE_FORM = "editExpeneForm";
    private static FormRegistry instancte;

    private Map<String, Object> registry = new HashMap<>();

    private FormRegistry()
    {
        AddIncomeForm addIncomeForm = new AddIncomeForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        addIncomeForm.setSaveCommand(new AddIncomeCommand());
        registry.put(ADD_INCOME_FORM, addIncomeForm);

        AddExpenseForm addExpenseForm = new AddExpenseForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        addExpenseForm.setSaveCommand(new AddExpenseCommand());
        registry.put(ADD_EXPENSE_FORM, addExpenseForm);

        EditExpenseForm editExpenseForm = new EditExpenseForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        editExpenseForm.setSaveCommand(new EditExpenseCommand());
        registry.put(EDIT_EXPENSE_FORM, editExpenseForm);
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
