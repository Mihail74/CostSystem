package ru.mkardaev.command.expense;

import ru.mkardaev.command.CommandAdapter;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.form.expense.EditExpenseForm;

/**
 * Команда открытия формы редактирования затраты
 * 
 * @author Mihail
 *
 */
public class OpenEditExpenseFormCommand extends CommandAdapter implements ICommand
{
    @Override
    public void perform() throws ApException
    {
        EditExpenseForm form = new EditExpenseForm();
        form.init(dtObject.<MoneyAction> getProperty(ApplicationContext.MONEY_ACTION_MODEL));
        form.open();
    }
}
