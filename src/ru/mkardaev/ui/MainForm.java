package ru.mkardaev.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.utils.Messages;

/**
 * Класс, описывающий главную форму приложения
 * 
 * @author Mihail
 *
 */
public class MainForm
{
    private Display display;
    private Messages messages;
    private Shell shell;

    public MainForm(Shell shell, Display display)
    {
        this.shell = shell;
        this.display = display;
        this.messages = ServicesFactory.getInstance().getMessages();
    }

    /**
     * Запускает процесс построения формы
     */
    public void bind()
    {
        FormToolkit toolKit = new FormToolkit(display);

        Form form = toolKit.createForm(shell);
        form.setLayoutData(new GridData(GridData.FILL_BOTH));
        form.setText(messages.getMessage(Messages.Keys.MAIN_FORM_DESCRIPTION));
        form.getBody().setLayout(new GridLayout(1, true));

        Composite toolComposite = toolKit.createComposite(form.getBody());
        toolComposite.setLayout(new GridLayout(2, true));
        toolComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        DateIntervalPickerWidget dateIntervalPicker = new DateIntervalPickerWidget(toolComposite);
        dateIntervalPicker.bind();

        toolKit.createButton(toolComposite, "test", SWT.PUSH);
        // Composite emptyComposite = toolKit.createComposite(form.getBody());

        TotalMoneyActionsWidget totalMoneyActionsWidget = new TotalMoneyActionsWidget(toolComposite);
        totalMoneyActionsWidget.bind();
        totalMoneyActionsWidget.setTotalExpeseValue("123");

        toolKit.createButton(toolComposite, "test", SWT.PUSH);

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

        Button button = toolKit.createButton(composite2, "Test", SWT.NULL);
        toolKit.createLabel(composite2, "label?");
    }
}
