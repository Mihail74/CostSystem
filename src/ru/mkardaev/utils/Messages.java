package ru.mkardaev.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Утилитарный класс для работы с локализованными константами.
 * 
 * 
 * @author Mihail
 *
 */
public class Messages
{
    /**
     * Ключи для сообщений
     */
    public static class MessagesKeys
    {
        public static final String CATEGORY_ADDED = "categoryAdded";
        public static final String CATEGORY_FINDED = "categoryFinded";
        public static final String CATEGORY_NOT_FINDED = "categoryNotFinded";
        public static final String CHOOSE_OPERATION = "chooseOperation";
        public static final String ERROR_READ_FILE = "errorReadFile";
        public static final String ERROR_WRITE_FILE = "errorWriteFile";
        public static final String INPUT_CATEGORY_ID = "inputCategoryId";
        public static final String INPUT_CATEGORY_TITLE = "inputCategoryTitle";
        public static final String INPUT_NUMBER_IN_RANGE = "inputNumberInRange";
        public static final String NOT_ALLOW_TO_READ = "notAllowToRead";
        public static final String OPERATION_ADD = "operationAdd";
        public static final String OPERATION_EXIT = "operationExit";
        public static final String OPERATION_FIND = "operationFind";
        public static final String WELCOME = "welcome";
        public static final String WRONG_FORMAT_ID = "wrongFormatId";
    }

    private static Locale defaultLocale;

    private static ResourceBundle defaultResourceBundle;
    private static Messages INSTANCE = new Messages();
    /**
     * Название файла с константами локализации
     */
    private static final String MESSAGES_FILE_NAME = "messages";
    private ResourceBundle resourceBundle = null;

    private Messages()
    {
        defaultLocale = new Locale("ru");
        defaultResourceBundle = ResourceBundle.getBundle(MESSAGES_FILE_NAME, defaultLocale);
    }

    /**
     * Возвращает локализованное сообщение по его ключу. В случае, если локализованного сообщения не сущетвует, вернётся сообщение в русской локали.
     * 
     * @param key - ключ сообщения
     * @param args - аргументы, если нужны
     * @return локализованное сообщение
     */
    public String getMessage(String key, Object... args)
    {
        try
        {
            return String.format(resourceBundle.getString(key), args);
        }
        catch (MissingResourceException e)
        {
            return String.format(defaultResourceBundle.getString(key), args);
        }

    }

    /**
     * Инициализирует сервис сообщений заданной локалью.
     * 
     * @param locale - локаль в которой будут выводиться сообщения
     */
    public void initalize(Locale locale)
    {
        this.resourceBundle = ResourceBundle.getBundle(MESSAGES_FILE_NAME, locale);
    }

    public static Messages getInstance()
    {
        return INSTANCE;
    }

}
