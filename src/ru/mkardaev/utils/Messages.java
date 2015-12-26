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
        public static final String NEW_CATEGORY = "newCategory";
        public static final String ACCOUNT_BALANCE = "accountBalance";
        public static final String ADD_EXPENSE = "addExpense";
        public static final String ADD_EXPENSE_FORM_TITLE = "addExpenseFormTitle";
        public static final String ADD_INCOME = "addIncome";
        public static final String ADD_INCOME_FORM_TITLE = "addIncomeFormTitle";
        public static final String APPLICATION_TITLE = "applicationTitle";
        public static final String CANCEL = "cancel";
        public static final String CATEGORY = "category";
        public static final String CREATION_DATE = "creationDate";
        public static final String DESCRIPTION = "description";
        public static final String EDIT_EXPENSE_FORM_TITLE = "editExpenseFormTitle";
        public static final String EDIT_INCOME_FORM_TITLE = "editIncomeFormTitle";
        public static final String EXPENSES = "expenses";
        public static final String FROM_DATE_LABEL = "fromDateLabel";
        public static final String INCOMES = "incomes";
        public static final String MAIN_FORM_DESCRIPTION = "mainFormDescription";
        public static final String SAVE = "save";
        public static final String TO_DATE_LABEL = "toDateLabel";
        public static final String TOTAL_EXPENSE = "totalExpense";
        public static final String TOTAL_INCOME = "totalIncome";
        public static final String VALUE = "value";
        public static final String ADD_CATEGORY = "addCategory";
        public static final String TITLE = "title";
        public static final String SHOULD_BE_NUMBER = "shouldBeNumber";
        public static final String TITLE_SHOULD_BE_NOT_EMPTY = "titleShouldBeNotEmpty";
        public static final String CONFIRM_DELETE = "confirmDelete";
        public static final String DO_YOU_SURE = "doYouSure";
        public static final String ERROR = "error";
        public static final String ERROR_ON_SAVE = "errorOnSave";
        public static final String ERROR_ON_LOAD_DATA = "errorOnLoadData";
        public static final String NOT_FULL_REQUIRED_FIELDS = "notFullRequiredFields";
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
