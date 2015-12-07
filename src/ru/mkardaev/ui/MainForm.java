package ru.mkardaev.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

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

    public MainForm(Shell shell, Display display, Messages messages)
    {
        this.shell = shell;
        this.display = display;
        this.messages = messages;
    }

    /**
     * Запускает процесс построения формы
     */
    public void build()
    {
        FormToolkit toolKit = new FormToolkit(display);
        Form form = toolKit.createForm(shell);
        form.setLayoutData(new GridData(GridData.FILL_BOTH));
        form.setText(messages.getMessage(Messages.MessagesKeys.MAIN_FORM_DESCRIPTION));

        form.getBody().setLayout(new GridLayout());

        Button button = toolKit.createButton(form.getBody(), "Test", SWT.NULL);
        toolKit.createLabel(form.getBody(), "label?");

    }
}
