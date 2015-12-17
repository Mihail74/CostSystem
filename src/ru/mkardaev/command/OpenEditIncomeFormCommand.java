package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.form.EditIncomeForm;

/**
 * Команда открытия формы редактирования дохода
 * 
 * @author Mihail
 *
 */
public class OpenEditIncomeFormCommand extends CommandAdapter implements ICommand
{
    @Override
    public void perform() throws ApException
    {
        EditIncomeForm form = new EditIncomeForm();
        form.init(dtObject.<MoneyAction> getProperty(ApplicationContext.MONEY_ACTION_MODEL));
        form.bind();
    }
}
