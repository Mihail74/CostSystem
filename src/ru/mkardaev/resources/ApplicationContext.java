package ru.mkardaev.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Контекст приложения, импользуемый для хранения данных во время работы приложения.
 * 
 * @author Mihail
 *
 */
public class ApplicationContext
{
    public static final String CURRENT_ACCOUNT = "currentAccount";
    public static final String CURRENT_PERSON = "currentPerson";
    public static final String EXPENSE = "expense";
    public static final String INCOME = "icnome";
    public static final String MONEY_ACTION_ID = "moneyActionId";
    public static final String MONEY_ACTION_UI_MODEL = "moneyActionUIModel";

    private static ApplicationContext instance = new ApplicationContext();

    private Map<String, Object> data = new HashMap<>();

    private ApplicationContext()
    {
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(String key)
    {
        return (T) data.get(key);
    }

    public void putData(String key, Object value)
    {
        data.put(key, value);
    }

    public void removeData(String key)
    {
        data.remove(key);
    }

    public static ApplicationContext getContext()
    {
        return instance;
    }
}
