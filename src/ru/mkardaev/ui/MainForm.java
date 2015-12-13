package ru.mkardaev.ui;

import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.form.AddExpenseForm;
import ru.mkardaev.ui.form.AddIncomeForm;
import ru.mkardaev.ui.services.FormRegistry;
import ru.mkardaev.ui.utils.category.ExpenseInputProvider;
import ru.mkardaev.ui.utils.category.IncomeInputProvider;
import ru.mkardaev.ui.widget.DateIntervalPickerWidget;
import ru.mkardaev.ui.widget.MoneyActionTableWidget;
import ru.mkardaev.ui.widget.TotalMoneyActionsValueWidget;
import ru.mkardaev.utils.Messages;

/**
 * Класс, описывающий главную форму приложения
 * 
 * @author Mihail
 *
 */
public class MainForm
{
    private Button addExpenseButton;
    private Button addIncomeButton;
    private DateIntervalPickerWidget dateIntervalPicker;
    private Display display;
    private ExpenseInputProvider expensesInputProvider;
    private MoneyActionTableWidget expenseTableWidget;
    private Form form;
    private IncomeInputProvider incomesInputProvider;
    private Messages messages;
    private int MIN_BUTTON_WIDTH = 100;
    private Callable<Void> refreshCallback;
    private Shell shell;
    private FormToolkit toolKit;

    public MainForm(Shell shell, Display display)
    {
        this.shell = shell;
        this.display = display;
        messages = ServicesFactory.getInstance().getMessages();
        toolKit = new FormToolkit(this.display);
        refreshCallback = new Callable<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                refreshForm();
                return null;
            }

        };
    }

    /**
     * Запускает процесс построения формы
     */
    public void bind()
    {
        form = toolKit.createForm(shell);
        form.setLayoutData(new GridData(GridData.FILL_BOTH));
        form.setText(messages.getMessage(Messages.Keys.MAIN_FORM_DESCRIPTION));
        form.getBody().setLayout(new GridLayout(1, true));

        createToolComposite();
        createTable();
    }

    public ExpenseInputProvider getExpensesInputProvider()
    {
        return expensesInputProvider;
    }

    public IncomeInputProvider getIncomesInputProvider()
    {
        return incomesInputProvider;
    }

    public void refreshForm()
    {
        expenseTableWidget.refresh();
    }

    public void setExpensesInputProvider(ExpenseInputProvider expensesInputProvider)
    {
        this.expensesInputProvider = expensesInputProvider;
    }

    public void setIncomesInputProvider(IncomeInputProvider incomesInputProvider)
    {
        this.incomesInputProvider = incomesInputProvider;
    }

    /**
     * Создаёт таблицу
     */
    private void createTable()
    {
        TabFolder tablFolder = new TabFolder(form.getBody(), SWT.NONE);
        tablFolder.setLayout(new GridLayout(1, true));
        tablFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        TabItem expenseTab = new TabItem(tablFolder, SWT.NONE);
        expenseTab.setText(messages.getMessage(Messages.Keys.EXPENSES));

        TabItem incomeTab = new TabItem(tablFolder, SWT.NONE);
        incomeTab.setText(messages.getMessage(Messages.Keys.INCOMES));

        expenseTableWidget = new MoneyActionTableWidget(tablFolder);
        expenseTableWidget.setMoneyActionInputProvider(expensesInputProvider);
        expenseTableWidget.setDateInterval(dateIntervalPicker.getFromDate(), dateIntervalPicker.getToDate());
        expenseTableWidget.bind();
        expenseTab.setControl(expenseTableWidget.getControl());

        Composite composite2 = new Composite(tablFolder, SWT.NONE);
        composite2.setLayout(new GridLayout(1, true));
        composite2.setLayoutData(new GridData(SWT.FILL));
        incomeTab.setControl(composite2);

    }

    /**
     * Создаёт верхную часть формы(над таблицей)
     * 
     * @param form
     */
    private void createToolComposite()
    {
        Composite toolComposite = toolKit.createComposite(form.getBody());
        toolComposite.setLayout(new GridLayout(2, true));
        toolComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        TotalMoneyActionsValueWidget totalMoneyActionsWidget = new TotalMoneyActionsValueWidget(toolComposite);
        totalMoneyActionsWidget.bind();

        Composite balanceComposite = toolKit.createComposite(toolComposite);
        balanceComposite.setLayout(new GridLayout(2, true));
        balanceComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        toolKit.createLabel(balanceComposite, messages.getMessage(Messages.Keys.ACCOUNT_BALANCE));

        dateIntervalPicker = new DateIntervalPickerWidget(toolComposite);
        dateIntervalPicker.bind();

        Composite buttonComposite = toolKit.createComposite(toolComposite);
        buttonComposite.setLayout(new GridLayout(1, true));
        buttonComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        addExpenseButton = toolKit.createButton(buttonComposite, messages.getMessage(Messages.Keys.ADD_EXPENSE),
                SWT.PUSH);
        addIncomeButton = toolKit.createButton(buttonComposite, messages.getMessage(Messages.Keys.ADD_INCOME),
                SWT.PUSH);

        GridData buttonGridData = new GridData();
        buttonGridData.minimumWidth = MIN_BUTTON_WIDTH;
        addExpenseButton.setLayoutData(buttonGridData);
        addIncomeButton.setLayoutData(buttonGridData);

        addExpenseButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    AddExpenseForm form = FormRegistry.getInstance()
                            .<AddExpenseForm> getForm(FormRegistry.ADD_EXPENSE_FORM);
                    form.setSaveCallback(refreshCallback);
                    form.bind();
                }
            }
        });

        addIncomeButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    AddIncomeForm form = FormRegistry.getInstance()
                            .<AddIncomeForm> getForm(FormRegistry.ADD_INCOME_FORM);
                    form.setSaveCallback(refreshCallback);
                    form.bind();
                }
            }
        });
    }
}
