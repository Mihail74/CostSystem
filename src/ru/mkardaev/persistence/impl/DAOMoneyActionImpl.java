package ru.mkardaev.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.MoneyActionFactory;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Income;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.utils.DateUtils;

public class DAOMoneyActionImpl implements DAOMoneyAction
{
    final static Logger logger = Logger.getLogger(DAOMoneyActionImpl.class);

    private static final String DELETE_SQL = "DELETE FROM money_action WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO money_action(account_id, category_id, creation_date, value, description, type) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_BY_CREATION_DATE_SQL = "SELECT id, account_id, category_id, creation_date, value, description, type FROM money_action WHERE creation_date >= ? AND creation_date <= ?";
    private static final String SELECT_SQL = "SELECT id, account_id, category_id, creation_date, value, description, type FROM money_action WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE money_action SET account_id = ?, category_id = ?, creation_date = ?, value = ?, description = ?  WHERE id = ?";
    private MoneyActionFactory moneyActionFactory;

    public DAOMoneyActionImpl(MoneyActionFactory moneyActionFactory)
    {
        this.moneyActionFactory = moneyActionFactory;

    }

    @Override
    public void create(MoneyAction moneyAction) throws ApException, SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, moneyAction.getAccountId());
                stmt.setLong(2, moneyAction.getCategoryId());
                stmt.setLong(3, DateUtils.convertToGMT0LongFromDefaultTimeZone(moneyAction.getCreationDate()));
                stmt.setLong(4, moneyAction.getValue());
                stmt.setString(5, moneyAction.getDescription());
                stmt.setLong(6, moneyAction instanceof Income ? MoneyAction.INCOME_TYPE : MoneyAction.EXPENSE_TYPE);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys())
                {
                    if (!rs.next())
                    {
                        throw new ApException("Failed to save money action");
                    }
                    moneyAction.setId(rs.getInt(1));
                }
            }

        }
        catch (SQLException e)
        {
            logger.error("Error save money action", e);
            throw e;
        }

    }

    @Override
    public void delete(long moneyActionId) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_SQL, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, moneyActionId);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            logger.error("Error money action", e);
            throw e;
        }
    }

    @Override
    public MoneyAction get(long moneyActionId) throws SQLException
    {
        MoneyAction moneyAction = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(SELECT_SQL))
            {
                stmt.setLong(1, moneyActionId);
                try (ResultSet rs = stmt.executeQuery())
                {
                    if (rs.next())
                    {
                        moneyAction = moneyActionFactory.createMoneyAction(rs.getLong(7), rs.getLong(1), rs.getLong(2),
                                rs.getLong(3), DateUtils.convertToDateWithDefaultTimeZone(rs.getLong(4)), rs.getLong(5),
                                rs.getString(6));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Error load money action", e);
            throw e;
        }
        return moneyAction;
    }

    @Override
    public List<MoneyAction> getByCreationDate(Account account, Date startDate, Date endDate) throws ApException
    {
        List<MoneyAction> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_CREATION_DATE_SQL))
            {
                stmt.setLong(1, DateUtils.convertToGMT0LongFromDefaultTimeZone(startDate));
                stmt.setLong(2, DateUtils.convertToGMT0LongFromDefaultTimeZone(endDate));
                stmt.executeUpdate();
                try (ResultSet rs = stmt.executeQuery())
                {
                    if (rs.next())
                    {
                        result.add(moneyActionFactory.createMoneyAction(rs.getLong(7), rs.getLong(1), rs.getLong(2),
                                rs.getLong(3), DateUtils.convertToDateWithDefaultTimeZone(rs.getLong(4)), rs.getLong(5),
                                rs.getString(6)));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Error load money action", e);
            throw new ApException("Error load money action", e);
        }
        return result;
    }

    @Override
    public void update(MoneyAction moneyAction) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL))
            {
                stmt.setLong(1, moneyAction.getAccountId());
                stmt.setLong(2, moneyAction.getCategoryId());
                stmt.setLong(3, DateUtils.convertToGMT0LongFromDefaultTimeZone(moneyAction.getCreationDate()));
                stmt.setLong(4, moneyAction.getValue());
                stmt.setString(5, moneyAction.getDescription());
                stmt.setLong(6, moneyAction.getId());
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            logger.error("Error update category", e);
            throw e;
        }

    }

    private Connection getConnection()
    {
        return ServicesFactory.getInstance().getConnectionService().getConnection();
    }

}
