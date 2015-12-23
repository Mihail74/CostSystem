package ru.mkardaev.ui.widgets;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.HasRefresh;
import ru.mkardaev.ui.models.DateInterval;
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
    private HasRefresh parentForm;

    private DateInterval dateInterval;

    public DateIntervalPickerWidget(Composite parent)
    {
        this.parent = parent;
        toolKit = new FormToolkit(parent.getDisplay());
        messages = ServicesFactory.getInstance().getMessages();
    }

    /**
     * Производит построение и последующее отображение виджета. Перед построением виджета необходимо установить модель интервала дат, см.
     * {@link #setDateInterval(DateInterval)}
     */
    public void bind()
    {
        Composite composite = toolKit.createComposite(parent);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label fromLabel = toolKit.createLabel(composite, messages.getMessage(Messages.Keys.FROM_DATE_LABEL));
        fromDateTimePicker = new DateTime(composite, SWT.DROP_DOWN);
        fromDateTimePicker.addSelectionListener(new SelectionListener()
        {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0)
            {
            }

            @Override
            public void widgetSelected(SelectionEvent event)
            {
                DateTime newValue = ((DateTime) event.getSource());
                dateInterval.setFromDate(getDate(newValue));
                refreshParent();
            }
        });

        Label toLabel = toolKit.createLabel(composite, messages.getMessage(Messages.Keys.TO_DATE_LABEL));
        toDateTimePicker = new DateTime(composite, SWT.DROP_DOWN);

        fromLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fromDateTimePicker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toDateTimePicker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toDateTimePicker.addSelectionListener(new SelectionListener()
        {
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0)
            {
            }

            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                DateTime newValue = ((DateTime) arg0.getSource());
                dateInterval.setToDate(getDate(newValue));
                refreshParent();
            }
        });
        dateInterval.setFromDate(getDate(fromDateTimePicker));
        dateInterval.setToDate(getDate(toDateTimePicker));
        refreshParent();
    }

    public DateInterval getDateInterval()
    {
        return dateInterval;
    }

    /**
     * Устанавливает модель интервала дат в которую будут записываться выбранные даты.
     * 
     * @param dateInterval
     */
    public void setDateInterval(DateInterval dateInterval)
    {
        this.dateInterval = dateInterval;
    }

    /**
     * Устанавливает для виджета родительскую форму, имеющую метод refresh. При изменении выбранных дат будет вызываться parentForm.refresh()
     * 
     * @param parentForm
     */
    public void setParentForm(HasRefresh parentForm)
    {
        this.parentForm = parentForm;
    }

    /**
     * Возвращает выбранную в виджете дату
     * 
     * @param dateTime - виджет выбора даты
     * @return date
     */
    private Date getDate(DateTime dateTime)
    {
        int year = dateTime.getYear();
        int month = dateTime.getMonth();
        int day = dateTime.getDay();
        String TimeZoneId = ApplicationContext.getContext().<String> getData(Property.Keys.TIME_ZONE);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZoneId));
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * Производит обновление родителя при изменении модели выбранного интервала дат.
     */
    private void refreshParent()
    {
        if (parentForm != null)
        {
            parentForm.refresh();
        }
    }

}
