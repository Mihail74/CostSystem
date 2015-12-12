package ru.mkardaev.ui;

import ru.mkardaev.exception.ApException;

/**
 * Интерфес провайдера данных для Viewer
 * 
 * @author Mihail
 *
 */
public interface InputProvider
{
    Object getInput() throws ApException;
}
