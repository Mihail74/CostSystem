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

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.form.MoneyActionFormBase;
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
    private Display display;
    private Form form;
    private Messages messages;
    private Shell shell;
    private FormToolkit toolKit;

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

        MoneyActionTableWidget commonTableWidget = new MoneyActionTableWidget(tablFolder);
        commonTableWidget.bind();
        expenseTab.setControl(commonTableWidget.getControl());

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

        TotalMoneyActionsWidget totalMoneyActionsWidget = new TotalMoneyActionsWidget(toolComposite);
        totalMoneyActionsWidget.bind();

        Composite balanceComposite = toolKit.createComposite(toolComposite);
        balanceComposite.setLayout(new GridLayout(2, true));
        balanceComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        toolKit.createLabel(balanceComposite, messages.getMessage(Messages.Keys.ACCOUNT_BALANCE));

        DateIntervalPickerWidget dateIntervalPicker = new DateIntervalPickerWidget(toolComposite);
        dateIntervalPicker.bind();

        Composite buttonComposite = toolKit.createComposite(toolComposite);
        buttonComposite.setLayout(new GridLayout(1, true));
        buttonComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        addExpenseButton = toolKit.createButton(buttonComposite, messages.getMessage(Messages.Keys.ADD_EXPENSE),
                SWT.PUSH);
        addIncomeButton = toolKit.createButton(buttonComposite, messages.getMessage(Messages.Keys.ADD_INCOME),
                SWT.PUSH);

        addExpenseButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    new MoneyActionFormBase().bind();
                }
            }
        });
    }
}
