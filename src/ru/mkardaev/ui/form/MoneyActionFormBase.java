package ru.mkardaev.ui.form;

import java.util.Calendar;
import java.util.concurrent.Callable;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.InputProvider;
import ru.mkardaev.ui.utils.category.CategoryLabelProvider;
import ru.mkardaev.ui.utils.category.CategorySorter;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

/**
 * Базовый класс формы Добавления/редактирования
 * 
 * @author Mihail
 *
 */
public abstract class MoneyActionFormBase
{
    protected Button cancelButton;
    protected ComboViewer categoryCombo;
    protected InputProvider categoryInputProvider;
    protected Text desriptionText;
    protected Shell dialogShell;
    protected Messages messages;
    protected Button okButton;
    protected Text valueText;
    private Point dialogSize = new Point(400, 250);

    private int MIN_BUTTON_WIDTH = 100;

    /**
     * Действия, которые необходимо выполнить при сохранении
     */
    private Callable<Void> saveAction;
    /**
     * CallBack сохранения
     */
    private Callable<Void> saveCallback;

    public MoneyActionFormBase(InputProvider categoryInputProvider)
    {
        messages = ServicesFactory.getInstance().getMessages();
        this.categoryInputProvider = categoryInputProvider;
    }

    public void bind()
    {
        Display display = Display.getDefault();
        dialogShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
        dialogShell.setLayout(new GridLayout(1, true));
        dialogShell.setImage(new Image(display, Resources.ICON_PATH));

        createFormContent(dialogShell);
        createButtons(dialogShell);
        initializeValue();
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

    public void setSaveAction(Callable<Void> saveAction)
    {
        this.saveAction = saveAction;
    }

    public void setSaveCallback(Callable<Void> saveCallback)
    {
        this.saveCallback = saveCallback;
    }

    protected abstract void initializeValue();

    private Button createButton(Composite parent, String text, int horizontalAlignment)
    {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = horizontalAlignment;
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = MIN_BUTTON_WIDTH;

        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        button.setLayoutData(gridData);

        return button;
    }

    /**
     * Создаёт кнопки "Сохранить" и "Отмена"
     * 
     */
    private void createButtons(Shell dialogShell)
    {
        Composite composite = new Composite(dialogShell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        okButton = createButton(composite, messages.getMessage(Messages.Keys.SAVE), SWT.RIGHT);
        cancelButton = createButton(composite, messages.getMessage(Messages.Keys.CANCEL), SWT.LEFT);
        okButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    onSave();
                }
            }
        });
        cancelButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    dialogShell.dispose();
                }
            }
        });
    }

    /**
     * Создаёт combo для выбора категорий
     * 
     * @param composite
     */
    private void createCategoryCombo(Composite composite)
    {
        categoryCombo = new ComboViewer(composite, SWT.BORDER);
        categoryCombo.setContentProvider(ArrayContentProvider.getInstance());
        categoryCombo.setLabelProvider(new CategoryLabelProvider());
        categoryCombo.setSorter(new CategorySorter());

        try
        {
            categoryCombo.setInput(categoryInputProvider.getInput());
        }
        catch (ApException e)
        {
            // TODO: Сделать нормальное сообщение об ошибке
        }
        // categoryCombo.setSelection(new StructuredSelection(categories[0]), true);
    }

    /**
     * Создаёт весь контент на форме
     */
    private void createFormContent(Shell dialogShell)
    {
        Composite composite = new Composite(dialogShell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label categoryLabel = new Label(composite, SWT.NONE);
        categoryLabel.setText(messages.getMessage(Messages.Keys.CATEGORY));

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

    /**
     * Метод, выполняющийся при сохранении
     */
    private void onSave()
    {
        try
        {
            saveAction.call();
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        try
        {
            saveCallback.call();
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

}
