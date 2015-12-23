package ru.mkardaev.ui.widgets;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.command.DtObject;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.command.moneyAction.GetTotalMoneyActionValues;
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

    // Команда, которая будет выполняться при изменении dateInterval.
    private ICommand updateValuesCommand;

    /**
     * @param parent - composite на котором будет отображаться виджет
     */
    public TotalMoneyActionsValueWidget(Composite parent)
    {
        this.parent = parent;
        toolKit = new FormToolkit(parent.getDisplay());
        messages = ServicesFactory.getInstance().getMessages();
        updateValuesCommand = new GetTotalMoneyActionValues();
    }

    /**
     * Создает и отображает виджет
     */
    public void bind()
    {
        Composite composite = toolKit.createComposite(parent);
        composite.setLayout(new GridLayout(2, true));

        toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TOTAL_EXPENSE));
        totalExpenseValueLabel = toolKit.createLabel(composite, "");

        toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TOTAL_INCOME));
        totalIncomeValueLabel = toolKit.createLabel(composite, "");

        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        totalIncomeValueLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        totalExpenseValueLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    @Override
    public void refresh()
    {
        updateValues();
    }

    /**
     * Задать интервал дат, для которых необходимо отображать суммарные расходы/доходы
     * 
     */
    public void setDateInterval(DateInterval dateInterval)
    {
        this.dateInterval = dateInterval;
    }

    /**
     * Обновляет поля на виджете
     */
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
            }
            catch (ApException e)
            {
                totalIncomeValueLabel.setText("Ошибка");
                totalExpenseValueLabel.setText("Ошибка");
            }

        }
    }
}
