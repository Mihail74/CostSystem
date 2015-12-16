package ru.mkardaev.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.command.OpenEditExpenseFormCommand;
import ru.mkardaev.command.OpenEditIncomeFormCommand;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.form.AddExpenseForm;
import ru.mkardaev.ui.form.AddIncomeForm;
import ru.mkardaev.ui.utils.ExpenseInputProvider;
import ru.mkardaev.ui.utils.IncomeInputProvider;
import ru.mkardaev.ui.utils.InputProvider;
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
    private Label balanceValue;

    private Display display;
    private Form form;
    private Shell shell;
    private FormToolkit toolKit;

    private TotalMoneyActionsValueWidget totalMoneyActionsWidget;
    private DateIntervalPickerWidget dateIntervalPicker;

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

    @Override
    public void refresh()
    {
        expenseTableWidget.refresh();
        incomeTableWidget.refresh();
    }

    public void setExpensesInputProvider(ExpenseInputProvider expensesInputProvider)
    {
        this.expensesInputProvider = expensesInputProvider;
    }

    public void setIncomesInputProvider(IncomeInputProvider incomesInputProvider)
    {
        this.incomesInputProvider = incomesInputProvider;
    }

    private void crateTotalMoneyActionsValueComposite(Composite toolComposite)
    {
        totalMoneyActionsWidget = new TotalMoneyActionsValueWidget(toolComposite);
        totalMoneyActionsWidget.bind();
    }

    private void createBalanceComposite(Composite toolComposite)
    {
        Composite balanceComposite = toolKit.createComposite(toolComposite);
        balanceComposite.setLayout(new GridLayout(2, true));
        balanceComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toolKit.createLabel(balanceComposite, messages.getMessage(Messages.Keys.ACCOUNT_BALANCE));
        balanceValue = toolKit.createLabel(balanceComposite, "");
    }

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
                    form.bind();
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
                    form.bind();
                    refresh();
                }
            }
        });
    }

    private void createDatePickerComposite(Composite toolComposite)
    {
        // TODO: Вариант сделать модель в этом классе и отдать её в пикер, на изменение данных повесить refresh данного класса
        dateIntervalPicker = new DateIntervalPickerWidget(toolComposite);
        dateIntervalPicker.bind();
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
        expenseTableWidget.setDateInterval(dateIntervalPicker.getFromDate(), dateIntervalPicker.getToDate());
        expenseTableWidget.setDoubleClickTableCommand(new OpenEditExpenseFormCommand());
        expenseTableWidget.bind();
        expenseTab.setControl(expenseTableWidget.getControl());

        incomeTableWidget = new MoneyActionTableWidget(tablFolder);
        incomeTableWidget.setMoneyActionInputProvider(incomesInputProvider);
        incomeTableWidget.setDateInterval(dateIntervalPicker.getFromDate(), dateIntervalPicker.getToDate());
        incomeTableWidget.setDoubleClickTableCommand(new OpenEditIncomeFormCommand());
        incomeTableWidget.bind();
        incomeTab.setControl(incomeTableWidget.getControl());
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
        toolComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        crateTotalMoneyActionsValueComposite(toolComposite);
        createBalanceComposite(toolComposite);
        createDatePickerComposite(toolComposite);
        createButtonComposite(toolComposite);
    }
}
