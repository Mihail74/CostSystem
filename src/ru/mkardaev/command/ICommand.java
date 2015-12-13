package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;

/**
 * Интерфейс выполнения комманд.
 * 
 * @author Mihail
 *
 */
public interface ICommand
{
    /**
     * Запускает выполнение комнды
     * 
     * @throws ApException
     */
    void perform() throws ApException;

    /**
     * Устаналивает объект переноса данных
     * 
     * @param dtObject
     */
    void setDtObject(DtObject dtObject);
}
