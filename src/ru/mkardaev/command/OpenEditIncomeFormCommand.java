package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.form.EditIncomeForm;
import ru.mkardaev.ui.models.MoneyActionUIModel;

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
        form.init(dtObject.<MoneyActionUIModel> getProperty(ApplicationContext.MONEY_ACTION_UI_MODEL).getMoneyAction());
        form.bind();
    }
}
