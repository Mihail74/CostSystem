package ru.mkardaev.entrypoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.MainForm;
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
        shell.setImage(new Image(display, Resources.ICON_PATH));

        MainForm mainForm = new MainForm(shell, display);
        mainForm.bind();

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
    }
}