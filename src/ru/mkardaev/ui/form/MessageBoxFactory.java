package ru.mkardaev.ui.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxFactory
{
    /**
     * Возвращет MessageBox с иконкой Warning, кнопками YES, NO, заголовком text и сообщением messag
     * 
     * @param shell - shell
     * @param text - заголовок окна
     * @param message - сообщение
     */
    public static MessageBox getErrorMessageBox(Shell shell, String text, String message)
    {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);

        messageBox.setText(text);
        messageBox.setMessage(message);
        return messageBox;
    }
}
