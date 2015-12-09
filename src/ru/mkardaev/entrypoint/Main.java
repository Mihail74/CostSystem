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
        ApplicationGUI applicationGUI = new ApplicationGUI();
        applicationGUI.run();
    }
}
