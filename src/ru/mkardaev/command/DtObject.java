package ru.mkardaev.command;

import java.util.HashMap;
import java.util.Map;

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
