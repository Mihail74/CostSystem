package ru.mkardaev.ui.widgets;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.command.DtObject;
import ru.mkardaev.command.GetTotalMoneyActionValues;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.HasRefresh;
import ru.mkardaev.ui.models.DateInterval;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

/**
 * Виджет отображающий суммарные доходы и расходы
 * 
 * @author Mihail
 *
 */
public class TotalMoneyActionsValueWidget implements HasRefresh
{
    private static final String MONEY_VALUE_PRINT_FORMAT = "%.2f";

    private Messages messages;
    private Composite parent;

    private FormToolkit toolKit;
    private Label totalExpenseValueLabel;
    private Label totalIncomeValueLabel;
    private DateInterval dateInterval;

    private ICommand updateValuesCommand;

    public TotalMoneyActionsValueWidget(Composite parent)
    {
        this.parent = parent;
        toolKit = new FormToolkit(parent.getDisplay());
        messages = ServicesFactory.getInstance().getMessages();
        updateValuesCommand = new GetTotalMoneyActionValues();
    }

    public void bind()
    {
        Composite composite = toolKit.createComposite(parent);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TOTAL_EXPENSE));
        totalExpenseValueLabel = toolKit.createLabel(composite, "");
        totalExpenseValueLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TOTAL_INCOME));
        totalIncomeValueLabel = toolKit.createLabel(composite, "");
        totalIncomeValueLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    @Override
    public void refresh()
    {
        updateValues();
    }

    public void setDateInterval(DateInterval dateInterval)
    {
        this.dateInterval = dateInterval;
    }

    private void updateValues()
    {
        if (dateInterval != null)
        {
            DtObject dtObject = new DtObject();
            dtObject.putProperty(ApplicationContext.FROM_DATE, DateUtils.getStartDayOfDate(dateInterval.getFromDate()));
            dtObject.putProperty(ApplicationContext.TO_DATE, DateUtils.getEndDayOfDate(dateInterval.getToDate()));
            updateValuesCommand.setDtObject(dtObject);
            try
            {
                updateValuesCommand.perform();
                double incomesValue = dtObject.<Double> getProperty(ApplicationContext.TOTAL_INCOME_VALUE);
                double expensesValue = dtObject.<Double> getProperty(ApplicationContext.TOTAL_EXPENSE_VALUE);

                totalIncomeValueLabel.setText(String.format(MONEY_VALUE_PRINT_FORMAT, incomesValue));
                totalExpenseValueLabel.setText(String.format(MONEY_VALUE_PRINT_FORMAT, expensesValue));

                totalIncomeValueLabel.redraw();
                parent.getShell().layout();

            }
            catch (ApException e)
            {
                totalIncomeValueLabel.setText("Ошибка");
                totalExpenseValueLabel.setText("Ошибка");
            }

        }
    }
}
