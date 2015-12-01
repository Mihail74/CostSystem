package ru.mkardaev.entrypoint;

import ru.mkardaev.persistence.ConnectionService;

public class Main
{

    public static void main(String[] args) throws ClassNotFoundException
    {
        ConnectionService.getInstance().init();

    }

    // Display display = new Display();
    // Shell shell = new Shell(display, SWT.RESIZE | SWT.DIALOG_TRIM);
    //
    // FormLoginForm loginForm = new FormLoginForm(shell);
    // // GridLoginForm loginForm = new GridLoginForm(shell);
    //
    // shell.pack();
    // shell.open();
    // while (!shell.isDisposed())
    // {
    // if (!display.readAndDispatch())
    // {
    // display.sleep();
    // }
    // }
    // display.dispose();
}
