package ru.mkardaev.ui;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
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

import ru.mkardaev.utils.Messages;

/**
 * Класс, описывающий главную форму приложения
 * 
 * @author Mihail
 *
 */
public class MainForm
{
    private class Text
    {
        String x, y;

        Text(String x, String y)
        {
            this.x = x;
            this.y = y;
        }
    }

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

        TabFolder folder = new TabFolder(form.getBody(), SWT.NONE);
        folder.setLayout(new GridLayout(1, true));
        folder.setLayoutData(new GridData(GridData.FILL_BOTH));

        TabItem tab1 = new TabItem(folder, SWT.NONE);
        tab1.setText("Tab 1");

        TabItem tab2 = new TabItem(folder, SWT.NONE);
        tab2.setText("Tab 2");

        ListViewer lw = new ListViewer(folder, SWT.DROP_DOWN);
        lw.setContentProvider(new ArrayContentProvider());
        lw.setInput(new Text[] { new Text("a", "b"), new Text("C", "d") });
        lw.setLabelProvider(new LabelProvider()
        {
            @Override
            public String getText(Object element)
            {

                return ((Text) element).x;
            };
        });
        tab1.setControl(lw.getControl());

        Composite composite2 = new Composite(folder, SWT.NONE);
        composite2.setLayout(new GridLayout(1, true));
        composite2.setLayoutData(new GridData(SWT.FILL));
        tab2.setControl(composite2);

        Button button = toolKit.createButton(composite2, "Test", SWT.NULL);
        toolKit.createLabel(composite2, "label?");
    }
}
