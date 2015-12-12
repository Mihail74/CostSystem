package ru.mkardaev.ui.form;

import ru.mkardaev.ui.InputProvider;
import ru.mkardaev.utils.Messages;

/**
 * Форма добавления дохода
 * 
 * @author Mihail
 *
 */
public class AddIncomeForm extends MoneyActionFormBase
{
    public AddIncomeForm(InputProvider categoryInputProvider)
    {
        super(categoryInputProvider);
    }

    @Override
    protected void initializeValue()
    {
        dialogShell.setText(messages.getMessage(Messages.Keys.ADD_INCOME_FORM_TITLE));
    }
}
