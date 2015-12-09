package ru.mkardaev.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.utils.Messages;

/**
 * Виджет выбора интервала дат
 * 
 * @author Mihail
 *
 */
public class DateIntervalPicker
{
    private DateTime fromDateTimePicker;
    private Messages messages;
    private Composite parent;
    private DateTime toDateTimePicker;
    private FormToolkit toolKit;

    public DateIntervalPicker(Composite parent)
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

        Label fromLabel = toolKit.createLabel(composite, messages.getMessage("fromDateLabel"));
        fromDateTimePicker = new DateTime(composite, SWT.DROP_DOWN);

        Label toLabel = toolKit.createLabel(composite, messages.getMessage("toDateLabel"));
        toDateTimePicker = new DateTime(composite, SWT.DROP_DOWN);

        // для autoresize
        fromLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
        fromDateTimePicker.setLayoutData(new GridData(GridData.FILL_BOTH));
        toLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
        toDateTimePicker.setLayoutData(new GridData(GridData.FILL_BOTH));
    }

}
