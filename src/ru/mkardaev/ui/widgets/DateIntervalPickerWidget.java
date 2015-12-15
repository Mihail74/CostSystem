package ru.mkardaev.ui.widgets;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.utils.Messages;
import ru.mkardaev.utils.Property;

/**
 * Виджет выбора интервала дат
 * 
 * @author Mihail
 *
 */
public class DateIntervalPickerWidget
{
    private DateTime fromDateTimePicker;
    private Messages messages;
    private Composite parent;
    private DateTime toDateTimePicker;
    private FormToolkit toolKit;

    public DateIntervalPickerWidget(Composite parent)
    {
        this.parent = parent;
        toolKit = new FormToolkit(parent.getDisplay());
        messages = ServicesFactory.getInstance().getMessages();
    }

    public void bind()
    {
        Composite composite = toolKit.createComposite(parent);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label fromLabel = toolKit.createLabel(composite, messages.getMessage(Messages.Keys.FROM_DATE_LABEL));
        fromDateTimePicker = new DateTime(composite, SWT.DROP_DOWN);

        Label toLabel = toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TO_DATE_LABEL));
        toDateTimePicker = new DateTime(composite, SWT.DROP_DOWN);

        // для autoresize
        fromLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fromDateTimePicker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toDateTimePicker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    public Date getFromDate()
    {
        return getDate(fromDateTimePicker.getYear(), fromDateTimePicker.getMonth(), fromDateTimePicker.getDay());
    }

    public Date getToDate()
    {
        return getDate(toDateTimePicker.getYear(), toDateTimePicker.getMonth(), toDateTimePicker.getDay());
    }

    private Date getDate(int year, int month, int day)
    {
        String TimeZoneId = ApplicationContext.getContext().<String> getData(Property.Keys.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZoneId));
        calendar.set(year, month, day);
        return calendar.getTime();
    }

}
