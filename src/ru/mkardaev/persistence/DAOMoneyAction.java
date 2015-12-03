package ru.mkardaev.persistence;

import java.sql.SQLException;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.MoneyAction;

public interface DAOMoneyAction
{
    void create(MoneyAction moneyAction) throws ApException, SQLException;

    void delete(long moneyActionId) throws SQLException;

    MoneyAction get(long moneyActionId) throws SQLException;

    void update(MoneyAction moneyAction) throws SQLException;
}
