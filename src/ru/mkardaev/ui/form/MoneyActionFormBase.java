package ru.mkardaev.ui.form;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

/**
 * Базовый класс формы Добавления/редактирования
 * 
 * @author Mihail
 *
 */
public class MoneyActionFormBase
{
    private Messages messages;
    private Text valueText;

    public MoneyActionFormBase()
    {
        messages = ServicesFactory.getInstance().getMessages();
    }

    public void bind()
    {
        Display display = Display.getDefault();
        Shell dialogShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
        dialogShell.setLayout(new GridLayout(1, true));
        dialogShell.setImage(new Image(display, Resources.ICON_PATH));

        createFormContent(dialogShell);

        dialogShell.pack();
        dialogShell.open();
        while (!dialogShell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }

    public void init()
    {

    }

    private void createFormContent(Shell dialogShell)
    {
        Composite composite = new Composite(dialogShell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label categoryLabel = new Label(composite, SWT.NONE);
        categoryLabel.setText(messages.getMessage(Messages.Keys.CATEGORY));

        // ListView
        new Button(composite, SWT.NONE).setLayoutData(new GridData(GridData.FILL_BOTH));

        Label valueLabel = new Label(composite, SWT.NONE);
        valueLabel.setText(messages.getMessage(Messages.Keys.VALUE));
        valueText = new Text(composite, SWT.BORDER);

        Label creationDateLabel = new Label(composite, SWT.NONE);
        creationDateLabel.setText(messages.getMessage(Messages.Keys.CREATION_DATE));
        DateTime creationDatePicker = new DateTime(composite, SWT.DROP_DOWN);

        Calendar currentDate = DateUtils.getCurrentDate();
        creationDatePicker.setYear(currentDate.get(Calendar.YEAR));
        creationDatePicker.setMonth(currentDate.get(Calendar.MONTH));
        creationDatePicker.setDay(currentDate.get(Calendar.DAY_OF_MONTH));

        categoryLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
        valueLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
        valueText.setLayoutData(new GridData(GridData.FILL_BOTH));
        creationDateLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
        creationDatePicker.setLayoutData(new GridData(GridData.FILL_BOTH));
    }

}
