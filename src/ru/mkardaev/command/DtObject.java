package ru.mkardaev.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Объект переноса данных, используется для передачи данных в комманды и обратно.
 * 
 * @author Mihail
 *
 */
public class DtObject
{
    private Map<String, Object> map = new HashMap<>();

    public DtObject()
    {
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key)
    {
        return (T) map.get(key);
    }

    public void putProperty(String key, Object value)
    {
        map.put(key, value);
    }
}
