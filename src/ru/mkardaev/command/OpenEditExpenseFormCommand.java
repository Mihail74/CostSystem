package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.form.EditExpenseForm;
import ru.mkardaev.ui.models.MoneyActionUIModel;

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
        form.init(dtObject.<MoneyActionUIModel> getProperty(ApplicationContext.MONEY_ACTION_UI_MODEL).getMoneyAction());
        form.bind();
    }
}
