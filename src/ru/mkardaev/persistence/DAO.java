package ru.mkardaev.persistence;

import java.sql.SQLException;

import ru.mkardaev.exception.ApException;

/**
 * Базовый интерфейс классов, реализующих доступ к базе данных.
 * 
 * @author Mihail
 *
 */
public interface DAO<T>
{
    /**
     * Сохраняет object в БД.
     * 
     * @param object - сохраняемый объект
     * @throws ApException
     * @throws SQLException
     */
    void create(T object) throws ApException;

    /**
     * Удаляет объект с заданным id из базы данных.
     * 
     * @param objectId - id удаляемого объекта
     * @throws SQLException
     */
    void delete(long objectId) throws ApException;

    /**
     * Достаёт объект из базы данных по его id.
     * 
     * @param objectId - id требуемого объекта.
     * @return объект, либо null, если искомого объекта в базе данных нет.
     * @throws SQLException
     */
    T get(long objectId) throws SQLException;

    /**
     * Производит обновление объекта в базе данных.
     * 
     * @param object - объект с обновленными полями.
     * @throws SQLException - если не удалось обновить объект или объект ещё не существует в БД.
     */
    void update(T object) throws ApException;
}
