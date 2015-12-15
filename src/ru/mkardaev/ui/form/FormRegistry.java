package ru.mkardaev.ui.form;

import java.util.HashMap;
import java.util.Map;

import ru.mkardaev.command.SaveExpenseCommand;
import ru.mkardaev.command.SaveIncomeCommand;
import ru.mkardaev.command.EditExpenseCommand;
import ru.mkardaev.command.EditIncomeCommand;
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
    public static final String EDIT_INCOME_FORM = "editIncomeForm";
    private static FormRegistry instancte;

    private Map<String, Object> registry = new HashMap<>();

    private FormRegistry()
    {
        AddIncomeForm addIncomeForm = new AddIncomeForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        addIncomeForm.setSaveCommand(new SaveIncomeCommand());
        registry.put(ADD_INCOME_FORM, addIncomeForm);

        AddExpenseForm addExpenseForm = new AddExpenseForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        addExpenseForm.setSaveCommand(new SaveExpenseCommand());
        registry.put(ADD_EXPENSE_FORM, addExpenseForm);

        EditExpenseForm editExpenseForm = new EditExpenseForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        editExpenseForm.setSaveCommand(new EditExpenseCommand());
        registry.put(EDIT_EXPENSE_FORM, editExpenseForm);

        EditIncomeForm editIncomeCommand = new EditIncomeForm(
                new CategoryInputProvider(ServicesFactory.getInstance().getDaoCategory()));
        editIncomeCommand.setSaveCommand(new EditIncomeCommand());
        registry.put(EDIT_INCOME_FORM, editIncomeCommand);
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
