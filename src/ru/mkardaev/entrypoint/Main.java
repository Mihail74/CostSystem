package ru.mkardaev.entrypoint;

import java.sql.SQLException;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.utils.Property;

public class Main
{
    public static void initializeAllServices() throws ClassNotFoundException, SQLException
    {
        ServicesFactory.getInstance().init();
    }

    public static void main(String[] args)
            throws ClassNotFoundException, InterruptedException, SQLException, ApException
    {
        initializeAllServices();
        initializeApplicationContext();

        new ApplicationGUI().run();
    }

    private static void initializeApplicationContext()
    {
        ApplicationContext.getContext().putData(Property.Keys.LOCALE,
                ServicesFactory.getInstance().getProperty().getProperty(Property.Keys.LOCALE));
        ApplicationContext.getContext().putData(Property.Keys.TIME_ZONE,
                ServicesFactory.getInstance().getProperty().getProperty(Property.Keys.TIME_ZONE));
    }
}
