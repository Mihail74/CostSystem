package ru.mkardaev.entrypoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main
{

    // public static void main(String[] args) throws InterruptedException, ClassNotFoundException
    // {
    // Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    // try (Connection conn = DriverManager.getConnection("jdbc:derby:MyDB;create=true"))
    // {
    // System.out.println("connected");
    // }
    // catch (SQLException e)
    // {
    // e.printStackTrace();
    // }
    //
    // }

    public static void main(String[] args)
    {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.RESIZE | SWT.DIALOG_TRIM);

        FormLoginForm loginForm = new FormLoginForm(shell);
        // GridLoginForm loginForm = new GridLoginForm(shell);

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
