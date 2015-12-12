package ru.mkardaev.ui.form;

import java.util.Calendar;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.providers.CategoryLabelProvider;
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
    private ComboViewer categoryCombo;
    private Text desriptionText;
    private Point dialogSize = new Point(400, 250);
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

        dialogShell.setSize(dialogSize);
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

    private void createCategoryCombo(Composite composite)
    {
        categoryCombo = new ComboViewer(composite, SWT.BORDER);
        categoryCombo.setContentProvider(ArrayContentProvider.getInstance());
        categoryCombo.setLabelProvider(new CategoryLabelProvider());

        Object[] categories = null;
        try
        {
            categories = ServicesFactory.getInstance().getDaoCategory().getAllCategories().toArray();
        }
        catch (ApException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        categoryCombo.setInput(categories);

        /* within the selection event, tell the object it was selected */
        categoryCombo.addSelectionChangedListener(new ISelectionChangedListener()
        {
            @Override
            public void selectionChanged(SelectionChangedEvent event)
            {
                // IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                // Person person = (Person) selection.getFirstElement();

                categoryCombo.refresh();
            }
        });

        categoryCombo.setSelection(new StructuredSelection(categories[0]), true);

    }

    private void createFormContent(Shell dialogShell)
    {
        Composite composite = new Composite(dialogShell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label categoryLabel = new Label(composite, SWT.NONE);
        categoryLabel.setText(messages.getMessage(Messages.Keys.CATEGORY));

        // ListView
        createCategoryCombo(composite);

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

        Label desriptionLabel = new Label(composite, SWT.NONE);
        desriptionLabel.setText(messages.getMessage(Messages.Keys.DESCRIPTION));
        desriptionText = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

        // layoutData
        categoryLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        categoryCombo.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        valueLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        valueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        creationDateLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        creationDatePicker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        desriptionLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        desriptionText.setLayoutData(new GridData(GridData.FILL_BOTH));

    }

}
