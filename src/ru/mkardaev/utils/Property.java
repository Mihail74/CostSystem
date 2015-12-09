package ru.mkardaev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import ru.mkardaev.resources.Resources;

public class Property
{
    public interface Keys
    {
        /**
         * Локаль приложения
         */
        public static final String LOCALE = "locale";
        /**
         * Часовой пояс приложения
         */
        public static final String TIME_ZONE = "timeZone";
    }

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
                logger.info(String.format("Properies load from file: %s", fileName));
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
        finally
        {
            initRequiredProperties();
        }

    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    /**
     * Производит инициализацию необходимых настроек, которые отсутствовали в файле конфигурации
     */
    private void initRequiredProperties()
    {
        if (properties.get(Keys.TIME_ZONE) == null)
        {
            properties.put(Keys.TIME_ZONE, TimeZone.getDefault().getID());
        }
        if (properties.get(Keys.LOCALE) == null)
        {
            properties.put(Keys.LOCALE, "RU");
        }
    }

    public static Property getInstance()
    {
        return instance;
    }
}
