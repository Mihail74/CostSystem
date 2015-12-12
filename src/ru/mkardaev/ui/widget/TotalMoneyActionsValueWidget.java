package ru.mkardaev.ui.widget;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.utils.Messages;

/**
 * Виджет величин суммарных MoneyAction
 * 
 * @author Mihail
 *
 */
public class TotalMoneyActionsValueWidget
{
    private Messages messages;
    private Composite parent;
    private FormToolkit toolKit;
    private Label totalExpenseValueLabel;
    private Label totalIncomeValueLabel;

    public TotalMoneyActionsValueWidget(Composite parent)
    {
        this.parent = parent;
        toolKit = new FormToolkit(parent.getDisplay());
        messages = ServicesFactory.getInstance().getMessages();
    }

    public void bind()
    {
        Composite composite = toolKit.createComposite(parent);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TOTAL_EXPENSE));
        totalExpenseValueLabel = toolKit.createLabel(composite, "");

        toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TOTAL_INCOME));
        totalIncomeValueLabel = toolKit.createLabel(composite, "");
    }

    public void setTotalExpeseValue(String value)
    {
        totalExpenseValueLabel.setText(value);
    }

    public void setTotalIncomeValue(String value)
    {
        totalIncomeValueLabel.setText(value);
    }
}
