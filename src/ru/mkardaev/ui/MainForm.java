package ru.mkardaev.ui;

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

import ru.mkardaev.command.expense.OpenEditExpenseFormCommand;
import ru.mkardaev.command.income.OpenEditIncomeFormCommand;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.form.expense.AddExpenseForm;
import ru.mkardaev.ui.form.income.AddIncomeForm;
import ru.mkardaev.ui.models.DateInterval;
import ru.mkardaev.ui.providers.input.ExpenseInputProvider;
import ru.mkardaev.ui.providers.input.IncomeInputProvider;
import ru.mkardaev.ui.providers.input.InputProvider;
import ru.mkardaev.ui.widgets.DateIntervalPickerWidget;
import ru.mkardaev.ui.widgets.MoneyActionTableWidget;
import ru.mkardaev.ui.widgets.TotalMoneyActionsValueWidget;
import ru.mkardaev.utils.Messages;

/**
 * Класс, описывающий главную форму приложения
 * 
 * @author Mihail
 *
 */
public class MainForm implements HasRefresh
{
    private int MIN_BUTTON_WIDTH = 100;

    private Button addExpenseButton;
    private Button addIncomeButton;

    private Display display;
    private Form form;
    private Shell shell;
    private FormToolkit toolKit;

    private TotalMoneyActionsValueWidget totalMoneyActionsWidget;
    private DateIntervalPickerWidget dateIntervalPicker;
    private DateInterval dateInteraval = new DateInterval();

    private MoneyActionTableWidget expenseTableWidget;
    private MoneyActionTableWidget incomeTableWidget;

    private InputProvider expensesInputProvider;
    private InputProvider incomesInputProvider;

    private Messages messages;

    public MainForm(Shell shell, Display display)
    {
        this.shell = shell;
        this.display = display;
        messages = ServicesFactory.getInstance().getMessages();
        toolKit = new FormToolkit(this.display);
    }

    /**
     * Строит, а затем открывает форму.
     */
    public void open()
    {
        form = toolKit.createForm(shell);
        form.setLayoutData(new GridData(GridData.FILL_BOTH));
        form.setText(messages.getMessage(Messages.Keys.MAIN_FORM_DESCRIPTION));
        form.getBody().setLayout(new GridLayout(1, true));

        createToolComposite();
        createTable();
    }

    @Override
    public void refresh()
    {
        if (totalMoneyActionsWidget != null)
        {
            totalMoneyActionsWidget.refresh();
        }
        if (expenseTableWidget != null)
        {
            expenseTableWidget.refresh();
        }
        if (incomeTableWidget != null)
        {
            incomeTableWidget.refresh();
        }
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
     * Создаёт виджет суммарных расходов/доходов
     * 
     * @param toolComposite
     */
    private void crateTotalMoneyActionsValueComposite(Composite toolComposite)
    {
        totalMoneyActionsWidget = new TotalMoneyActionsValueWidget(toolComposite);
        totalMoneyActionsWidget.setDateInterval(dateInteraval);
        totalMoneyActionsWidget.bind();
    }

    /**
     * Создаёт composite с кнопками добавления расходов и доходов
     * 
     * @param toolComposite
     */
    private void createButtonComposite(Composite toolComposite)
    {
        Composite buttonComposite = toolKit.createComposite(toolComposite);
        buttonComposite.setLayout(new GridLayout(1, true));
        buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        addExpenseButton = toolKit.createButton(buttonComposite, messages.getMessage(Messages.Keys.ADD_EXPENSE),
                SWT.PUSH);
        addIncomeButton = toolKit.createButton(buttonComposite, messages.getMessage(Messages.Keys.ADD_INCOME),
                SWT.PUSH);

        GridData buttonGridData = new GridData();
        buttonGridData.minimumWidth = MIN_BUTTON_WIDTH;
        buttonGridData.horizontalAlignment = SWT.CENTER;
        buttonGridData.grabExcessHorizontalSpace = true;

        addExpenseButton.setLayoutData(buttonGridData);
        addIncomeButton.setLayoutData(buttonGridData);

        addExpenseButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    AddExpenseForm form = new AddExpenseForm();
                    form.open();
                    refresh();
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
                    AddIncomeForm form = new AddIncomeForm();
                    form.open();
                    refresh();
                }
            }
        });
    }

    /**
     * Создаёт виджет выбора интервала дат
     * 
     * @param toolComposite
     */
    private void createDatePickerComposite(Composite toolComposite)
    {
        dateIntervalPicker = new DateIntervalPickerWidget(toolComposite);
        dateIntervalPicker.setDateInterval(dateInteraval);
        dateIntervalPicker.setParentForm(this);
        dateIntervalPicker.bind();
    }

    /**
     * Создаёт пустой composite
     * 
     * @param toolComposite
     */
    private void createEmptyComposite(Composite toolComposite)
    {
        Composite balanceComposite = toolKit.createComposite(toolComposite);
        balanceComposite.setLayout(new GridLayout(2, true));
        balanceComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    /**
     * Создание вкладок с таблицами
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
        expenseTableWidget.setDateInterval(dateInteraval);
        expenseTableWidget.setDoubleClickTableCommand(new OpenEditExpenseFormCommand());
        expenseTableWidget.setParentForm(this);
        expenseTableWidget.bind();
        expenseTab.setControl(expenseTableWidget.getControl());

        incomeTableWidget = new MoneyActionTableWidget(tablFolder);
        incomeTableWidget.setMoneyActionInputProvider(incomesInputProvider);
        incomeTableWidget.setDateInterval(dateInteraval);
        incomeTableWidget.setDoubleClickTableCommand(new OpenEditIncomeFormCommand());
        incomeTableWidget.setParentForm(this);
        incomeTableWidget.bind();
        incomeTab.setControl(incomeTableWidget.getControl());
    }

    /**
     * Создаёт верхную часть формы(над таблицей)
     */
    private void createToolComposite()
    {
        Composite toolComposite = toolKit.createComposite(form.getBody());
        toolComposite.setLayout(new GridLayout(2, true));
        toolComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        crateTotalMoneyActionsValueComposite(toolComposite);
        createEmptyComposite(toolComposite);
        createDatePickerComposite(toolComposite);
        createButtonComposite(toolComposite);
    }
}
