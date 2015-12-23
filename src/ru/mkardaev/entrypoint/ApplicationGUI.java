package ru.mkardaev.entrypoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.MainForm;
import ru.mkardaev.ui.providers.input.ExpenseInputProvider;
import ru.mkardaev.ui.providers.input.IncomeInputProvider;
import ru.mkardaev.utils.Messages;

/**
 * Класс построения интерфейса
 * 
 * @author Mihail
 *
 */
public class ApplicationGUI
{
    private Messages messages;

    public ApplicationGUI()
    {
        messages = ServicesFactory.getInstance().getMessages();
    }

    public void run()
    {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM);
        shell.setLayout(new GridLayout(1, true));
        shell.setText(messages.getMessage(Messages.Keys.APPLICATION_TITLE));
        Image formImage = new Image(display, Resources.FORM_ICON_PATH);
        shell.setImage(formImage);

        MainForm mainForm = new MainForm(shell, display);
        mainForm.setExpensesInputProvider(new ExpenseInputProvider(ServicesFactory.getInstance().getDaoCategory(),
                ServicesFactory.getInstance().getDaoMoneyAction()));
        mainForm.setIncomesInputProvider(new IncomeInputProvider(ServicesFactory.getInstance().getDaoCategory(),
                ServicesFactory.getInstance().getDaoMoneyAction()));
        mainForm.open();

        shell.pack();
        shell.open();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        display.dispose();
        formImage.dispose();
    }
}
