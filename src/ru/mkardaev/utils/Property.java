package ru.mkardaev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import ru.mkardaev.resources.Resources;

public class Property
{
    final static Logger logger = Logger.getLogger(Property.class);
    private static Property instance = new Property();
    private static Properties properties;

    private Property()
    {
        properties = new Properties();

        String fileName = Resources.PROPERTIES_FILE;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName))
        {
            if (input != null)
            {
                properties.load(input);
                logger.info(String.format("Properies load from file: %S", fileName));
            }
            else
            {
                logger.error(String.format("Error open properties file: %s", fileName));
            }
        }
        catch (IOException e)
        {
            logger.error(String.format("Error load properties from file: %s", fileName), e);
            e.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    public static Property getInstance()
    {
        return instance;
    }
}
