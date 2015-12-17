package ru.mkardaev.ui;

/**
 * Интерфейс-маркер. Обозначает наличие метода refresh у класса.
 * 
 * @author Mihail
 *
 */
public interface HasRefresh
{
    /**
     * Производит обновление всех полей
     */
    void refresh();
}
