package ru.mkardaev.persistence;

import java.sql.SQLException;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Account;

public interface DAOAccount
{
    void create(Account account) throws ApException, SQLException;

    void delete(long accountId) throws SQLException;

    Account get(long accountId) throws SQLException;

    void update(Account account) throws SQLException;
}
