package ru.mkardaev.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс - источник id
 * 
 * @author Mihail
 *
 */
public class IdGenerator
{
    private static AtomicLong nextId = new AtomicLong(1L);

    public static long generateId()
    {
        return nextId.getAndIncrement();
    }

    public static void setNextId(long nextId)
    {
        IdGenerator.nextId.set(nextId);
    }

}
