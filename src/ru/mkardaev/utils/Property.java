package ru.mkardaev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Property
{
    final static Logger logger = Logger.getLogger(Property.class);
    private static Property instance = new Property();
    private static Properties properties;

    private Property()
    {
        properties = new Properties();

        String filename = "config.properties";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename))
        {
            properties.load(input);
            logger.info("Properies load from file: " + filename);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
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
