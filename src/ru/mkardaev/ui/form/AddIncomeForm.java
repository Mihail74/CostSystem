package ru.mkardaev.ui.form;

import org.eclipse.jface.viewers.StructuredSelection;

import ru.mkardaev.command.DtObject;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.utils.InputProvider;
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

    @Override
    protected void onApply()
    {
        Category category = (Category) ((StructuredSelection) categoryCombo.getSelection()).getFirstElement();
        Account account = ApplicationContext.getContext().getData(ApplicationContext.CURRENT_ACCOUNT);
        Long value = Long.valueOf(valueText.getText());
        String description = desriptionText.getText();

        Income income = ServicesFactory.getInstance().getMoneyActionFactory().createIncome(account, category, value);
        income.setDescription(description);

        DtObject dtObject = new DtObject();
        dtObject.putProperty(ApplicationContext.INCOME, income);
        saveCommand.setDtObject(dtObject);
    }
}