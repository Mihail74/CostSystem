package ru.mkardaev.entrypoint;

import java.sql.SQLException;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.utils.Messages;

public class Main
{
    public static void initializeAllServices() throws ClassNotFoundException, SQLException
    {
        ServicesFactory.getInstance().init();
    }

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, SQLException
    {
        initializeAllServices();
        Application application = new Application(Messages.getInstance());
        application.run();
    }
}
