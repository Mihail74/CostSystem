package ru.mkardaev.entrypoint;

import ru.mkardaev.persistence.SessionFactory;

public class Main
{

    public static void main(String[] args) throws ClassNotFoundException
    {
        SessionFactory.getInstance().init();

    }

    // -------------------
    // try(
    //
    // Connection connection = SessionFactory.getInstance().getConnection())
    //
    // {
    // PreparedStatement prStmt = connection.prepareStatement("INSERT INTO category(title) VALUES(?)",
    // Statement.RETURN_GENERATED_KEYS);
    // prStmt.setNull(1, Types.VARCHAR);
    // prStmt.executeUpdate();
    //
    // ResultSet rs = prStmt.getGeneratedKeys();
    // while (rs.next())
    // {
    // int x = rs.getInt(1);
    // x++;
    // }
    // rs.close();
    //
    // Statement stmt = connection.createStatement();
    // ResultSet rs = stmt.executeQuery("select * from category");
    // while (rs.next())
    // {
    // int id = rs.getInt(1);
    // String string = rs.getString(2);
    // System.out.println("id = " + id + " , title = " + string);
    // }
    //
    // } catch(
    //
    // SQLException e)
    //
    // {
    // e.printStackTrace();
    // }
    //
    // ---------------------
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
