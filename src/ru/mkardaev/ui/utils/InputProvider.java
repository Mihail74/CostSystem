package ru.mkardaev.ui.utils;

import ru.mkardaev.exception.ApException;

/**
 * Интерфес провайдера данных для Viewer
 * 
 * @author Mihail
 *
 */
public interface InputProvider
{
    /**
     * Метод получения данных на клиенте
     * 
     * @param args - аргументы
     * @return
     * @throws ApException
     */
    Object getInput(Object... args) throws ApException;
}
