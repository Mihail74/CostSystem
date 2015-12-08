package ru.mkardaev.entrypoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.mkardaev.ui.MainForm;
import ru.mkardaev.utils.Messages;

public class Application
{
    private Messages messages;

    public Application(Messages messages)
    {
        this.messages = messages;
    }

    public void run()
    {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM);
        shell.setLayout(new GridLayout(1, true));
        shell.setText(messages.getMessage(Messages.MessagesKeys.APPLICATION_TITLE));
        shell.setImage(new Image(display, "icon.png"));

        MainForm mainForm = new MainForm(shell, display, messages);
        mainForm.build();

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
