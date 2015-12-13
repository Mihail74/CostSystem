package ru.mkardaev.ui.form;

import org.eclipse.jface.viewers.StructuredSelection;

import ru.mkardaev.command.DtObject;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Expense;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.utils.InputProvider;
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

    @Override
    protected void onApply()
    {
        Category category = (Category) ((StructuredSelection) categoryCombo.getSelection()).getFirstElement();
        Account account = ApplicationContext.getContext().getData(ApplicationContext.CURRENT_ACCOUNT);
        Long value = Long.valueOf(valueText.getText());
        String description = desriptionText.getText();

        Expense expense = ServicesFactory.getInstance().getMoneyActionFactory().createExpense(account, category, value);
        expense.setDescription(description);

        DtObject dtObject = new DtObject();
        dtObject.putProperty(ApplicationContext.EXPENSE, expense);
        saveCommand.setDtObject(dtObject);
    }
}