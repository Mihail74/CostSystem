package ru.mkardaev.entrypoint;

import java.sql.SQLException;

import ru.mkardaev.factories.ServicesFactory;

public class Main
{
    public static void initializeAllServices() throws ClassNotFoundException, SQLException
    {
        ServicesFactory.getInstance().init();
    }

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, SQLException
    {
        initializeAllServices();
        // Display display = new Display();
        // Shell shell = new Shell(display, SWT.RESIZE | SWT.DIALOG_TRIM);
        //
        // shell.setLayout(new GridLayout(1, true));
        // Label label = new Label(shell, SWT.BORDER);
        // label.setText("I am a Label");
        //
        // FormToolkit toolKit = new FormToolkit(display);
        //
        // Form form = toolKit.createForm(shell);
        // // form.setLayoutData(new GridData(GridData.FILL_BOTH));
        // form.setText("Eclipse Forms");
        //
        // form.getBody().setLayout(new GridLayout());
        // Button button = toolKit.createButton(form.getBody(), "Test", SWT.NULL);
        // toolKit.createLabel(form.getBody(), "label?");
        //
        // // tool bar
        // form.getToolBarManager().add(new Action("TEST")
        // {
        // @Override
        // public void run()
        // {
        // }
        // });
        //
        // Menu menu = new Menu(form.getBody());
        // MenuItem item = new MenuItem(menu, SWT.NULL);
        // item.setText("Testing item");
        // form.setMenu(menu);
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
}
