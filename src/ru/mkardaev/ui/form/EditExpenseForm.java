package ru.mkardaev.ui.form;

import java.util.Calendar;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import ru.mkardaev.command.DtObject;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Category;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.utils.InputProvider;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

/**
 * Форма редактирования расхода
 * 
 * @author Mihail
 *
 */
public class EditExpenseForm extends MoneyActionFormBase
{

    EditExpenseForm(InputProvider categoryInputProvider)
    {
        super(categoryInputProvider);
    }

    @Override
    protected void initializeValue()
    {
        dialogShell.setText(messages.getMessage(Messages.Keys.EDIT_EXPENSE_FORM_TITLE));

        Calendar date = DateUtils.getCaledar(moneyAction.getCreationDate());
        creationDatePicker.setYear(date.get(Calendar.YEAR));
        creationDatePicker.setMonth(date.get(Calendar.MONTH));
        creationDatePicker.setDay(date.get(Calendar.DAY_OF_MONTH));

        descriptionText.setText(moneyAction.getDescription());
        valueText.setText(String.valueOf(moneyAction.getValue()));
        Object[] categories;
        try
        {
            categories = (Object[]) categoryInputProvider.getInput();
            for (int i = 0; i < categories.length; ++i)
            {
                if (((Category) categories[i]).getId() == moneyAction.getCategoryId())
                {
                    categoryCombo.setSelection(new StructuredSelection(categories[i]), true);
                }
            }
        }
        catch (ApException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onApply()
    {
        Category category = (Category) ((IStructuredSelection) categoryCombo.getSelection()).getFirstElement();
        moneyAction.setCategoryId(category.getId());
        moneyAction.setCreationDate(DateUtils.getDate(creationDatePicker.getYear(), creationDatePicker.getMonth(),
                creationDatePicker.getDay()));
        moneyAction.setValue(Double.valueOf(valueText.getText()));
        moneyAction.setDescription(descriptionText.getText());

        DtObject dtObject = new DtObject();
        dtObject.putProperty(ApplicationContext.EXPENSE, moneyAction);
        saveCommand.setDtObject(dtObject);
    }

}
