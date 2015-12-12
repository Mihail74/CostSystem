package ru.mkardaev.ui.form;

import ru.mkardaev.ui.InputProvider;
import ru.mkardaev.utils.Messages;

/**
 * Форма добавления расхода
 * 
 * @author Mihail
 *
 */
public class AddExpenseForm extends MoneyActionFormBase
{

    public AddExpenseForm(InputProvider categoryInputProvider)
    {
        super(categoryInputProvider);
    }

    @Override
    protected void initializeValue()
    {
        dialogShell.setText(messages.getMessage(Messages.Keys.ADD_EXPENSE_FORM_TITLE));
    }
}
