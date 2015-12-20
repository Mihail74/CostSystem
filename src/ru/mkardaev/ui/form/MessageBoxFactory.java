package ru.mkardaev.ui.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxFactory
{
    public static MessageBox getErrorMessageBox(Shell parent, String text, String message)
    {
        MessageBox messageBox = new MessageBox(parent.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

        messageBox.setText(text);
        messageBox.setMessage(message);
        return messageBox;
    }
}
