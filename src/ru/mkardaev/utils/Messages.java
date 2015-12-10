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
    public static class Keys
    {
        public static final String APPLICATION_TITLE = "applicationTitle";
        public static final String EXPENSES = "expenses";
        public static final String INCOMES = "incomes";
        public static final String MAIN_FORM_DESCRIPTION = "mainFormDescription";
        public static final String TOTAL_EXPENSE = "totalExpense";
        public static final String TOTAL_INCOME = "totalIncome";
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
