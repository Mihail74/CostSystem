package ru.mkardaev.ui.form;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.mkardaev.command.ICommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.form.category.AddCategoryForm;
import ru.mkardaev.ui.models.sorters.CategorySorter;
import ru.mkardaev.ui.providers.input.CategoryInputProvider;
import ru.mkardaev.ui.providers.input.InputProvider;
import ru.mkardaev.ui.providers.label.CategoryLabelProvider;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

/**
 * Базовый класс формы Добавления/редактирования доходов/расходов
 * 
 * @author Mihail
 *
 */
public abstract class MoneyActionFormBase
{
    private int MIN_BUTTON_WIDTH = 100;

    protected ComboViewer categoryCombo;
    protected Text valueText;
    protected DateTime creationDatePicker;
    protected Text descriptionText;

    protected Button okButton;
    protected Button cancelButton;

    protected Shell dialogShell;
    private Point dialogSize = new Point(400, 250);
    protected Messages messages;

    protected MoneyAction moneyAction;
    protected InputProvider categoryInputProvider;
    /**
     * Команда, которую выполняетя при сохранении
     */
    protected ICommand saveCommand;

    public MoneyActionFormBase()
    {
        messages = ServicesFactory.getInstance().getMessages();
        categoryInputProvider = new CategoryInputProvider();
    }

    public void init(MoneyAction moneyAction)
    {
        this.moneyAction = moneyAction;
    }

    /**
     * Создаёт и открывает форму.
     */
    public void open()
    {
        Display display = Display.getDefault();
        dialogShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
        dialogShell.setLayout(new GridLayout(1, true));
        Image formImage = new Image(display, Resources.FORM_ICON_PATH);
        dialogShell.setImage(formImage);

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
        formImage.dispose();
    }

    public void setSaveCommand(ICommand saveAction)
    {
        this.saveCommand = saveAction;
    }

    /**
     * Метод инициализации дополнительных настроек, например заголовка формы и т.п.
     */
    protected abstract void initializeValue();

    /**
     * Выполняется при нажатии кнопки "сохранить"
     */
    protected abstract void onApply();

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
                    save();
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
        categoryCombo.addSelectionChangedListener(new ISelectionChangedListener()
        {
            @Override
            public void selectionChanged(SelectionChangedEvent event)
            {
                StructuredSelection selection = (StructuredSelection) event.getSelection();
                if (selection.getFirstElement() instanceof Category)
                {
                    Category selectedCategory = (Category) selection.getFirstElement();
                    if (selectedCategory.getId() == Category.NEW_CATEGORY_ID)
                    {
                        AddCategoryForm form = new AddCategoryForm();
                        form.bind();
                        categoryCombo.getCombo().clearSelection();
                        refresh();
                    }
                }

            }
        });
        categoryCombo.getControl().addListener(SWT.Verify, new Listener()
        {

            @Override
            public void handleEvent(Event e)
            {
                String currentText = ((Combo) e.widget).getText();
                String newText = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
                List<String> availableString = Arrays.asList(((Combo) categoryCombo.getControl()).getItems());
                if (!"".equals(newText) && !availableString.contains(newText))
                {
                    e.doit = false;
                }

            }
        });
        refresh();
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
        valueText.addVerifyListener(new VerifyListener()
        {
            @Override
            public void verifyText(VerifyEvent e)
            {
                valueText.setToolTipText("");
                String currentText = ((Text) e.widget).getText();
                String newText = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
                if ("".equals(newText))
                {
                    return;
                }
                try
                {
                    Double.parseDouble(newText);
                }
                catch (NumberFormatException ex)
                {
                    valueText.setToolTipText(messages.getMessage(Messages.Keys.SHOULD_BE_NUMBER));
                    e.doit = false;
                }
            }
        });
        Label creationDateLabel = new Label(composite, SWT.NONE);
        creationDateLabel.setText(messages.getMessage(Messages.Keys.CREATION_DATE));
        creationDatePicker = new DateTime(composite, SWT.DROP_DOWN);

        Calendar currentDate = DateUtils.getCurrentDate();
        creationDatePicker.setYear(currentDate.get(Calendar.YEAR));
        creationDatePicker.setMonth(currentDate.get(Calendar.MONTH));
        creationDatePicker.setDay(currentDate.get(Calendar.DAY_OF_MONTH));

        Label desriptionLabel = new Label(composite, SWT.NONE);
        desriptionLabel.setText(messages.getMessage(Messages.Keys.DESCRIPTION));
        descriptionText = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

        // layoutData
        categoryLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        categoryCombo.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        valueLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        valueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        creationDateLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        creationDatePicker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        desriptionLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        descriptionText.setLayoutData(new GridData(GridData.FILL_BOTH));
    }

    private void refresh()
    {
        try
        {
            categoryCombo.setInput(categoryInputProvider.getInput());
        }
        catch (ApException e)
        {
            MessageBoxFactory.getErrorMessageBox(dialogShell, messages.getMessage(Messages.Keys.ERROR),
                    messages.getMessage(Messages.Keys.ERROR_ON_LOAD_DATA)).open();
        }
    }

    /**
     * Метод, выполняющийся при сохранении
     */
    private void save()
    {
        onApply();
        try
        {
            saveCommand.perform();
        }
        catch (ApException ex)
        {
            MessageBoxFactory.getErrorMessageBox(dialogShell, messages.getMessage(Messages.Keys.ERROR),
                    messages.getMessage(Messages.Keys.ERROR_ON_SAVE)).open();
            ex.printStackTrace();
        }
        dialogShell.dispose();
    }
}
