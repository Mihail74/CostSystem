package ru.mkardaev.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.AccountFactory;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.persistence.DAOAccount;

public class DAOAccountImpl implements DAOAccount
{
    final static Logger logger = Logger.getLogger(DAOAccountImpl.class);
    private static final String DELETE_ACCOUNT = "DELETE FROM account WHERE id = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO account(title) VALUES(?)";
    private static final String SELECT_ACCOUNT = "SELECT id, value FROM account WHERE id = ?";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET value = ? WHERE id = ?";
    private AccountFactory accountFactory;

    public DAOAccountImpl(AccountFactory accountFactory)
    {
        this.accountFactory = accountFactory;
    }

    @Override
    public void create(Account account) throws ApException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, account.getValue());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys())
                {
                    if (!rs.next())
                    {
                        throw new ApException("Failed to save category");
                    }
                    account.setId(rs.getInt(1));
                }
            }

        }
        catch (SQLException e)
        {
            logger.error("Error save account", e);
            throw new ApException("Error save account", e);
        }

    }

    @Override
    public void delete(long accountId) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_ACCOUNT, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, accountId);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            logger.error("Error delete account", e);
            throw e;
        }
    }

    @Override
    public Account get(long accountId) throws SQLException
    {
        Account account = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ACCOUNT))
            {
                stmt.setLong(1, accountId);
                stmt.executeQuery();
                try (ResultSet rs = stmt.executeQuery())
                {
                    if (rs.next())
                    {
                        account = accountFactory.createAccount(rs.getLong(1), rs.getLong(2));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Error load account", e);
            throw e;
        }
        return account;
    }

    @Override
    public void update(Account account) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ACCOUNT))
            {
                stmt.setLong(1, account.getId());
                stmt.setLong(2, account.getValue());
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            logger.error("Error update account", e);
            throw e;
        }
    }

    private Connection getConnection()
    {
        return ServicesFactory.getInstance().getConnectionService().getConnection();
    }

}
