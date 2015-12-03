package ru.mkardaev.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Income;
import ru.mkardaev.model.MoneyAction;
import ru.mkardaev.persistence.ConnectionService;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.utils.DateUtils;

public class DAOMoneyActionImpl implements DAOMoneyAction
{
    final static Logger logger = Logger.getLogger(DAOMoneyActionImpl.class);

    private static final String DELETE_SQL = "DELETE FROM money_action WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO money_action(account_id, category_id, creation_date, value, description, type) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_SQL = "SELECT id, title FROM category WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE category SET title = ? WHERE id = ?";
    // private CategoryFactory categoryFactory;

    @Override
    public void create(MoneyAction moneyAction) throws ApException, SQLException
    {
        try (Connection connection = ConnectionService.getInstance().getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, moneyAction.getAccountId());
                stmt.setLong(2, moneyAction.getCategoryId());
                stmt.setLong(3, DateUtils.convertToGMT0Long(moneyAction.getCreationDate()));
                stmt.setLong(4, moneyAction.getValue());
                stmt.setString(5, moneyAction.getDescription());
                stmt.setLong(6, moneyAction instanceof Income ? MoneyAction.INCOME_TYPE : MoneyAction.EXPENSE_TYPE);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys())
                {
                    if (!rs.next())
                    {
                        throw new ApException("Can't save money action");
                    }
                    moneyAction.setId(rs.getInt(1));
                }
            }

        }
        catch (SQLException e)
        {
            logger.error("Error on save account", e);
            throw e;
        }

    }

    @Override
    public void delete(long moneyActionId) throws SQLException
    {
        try (Connection connection = ConnectionService.getInstance().getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_SQL, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, moneyActionId);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            logger.error("Error delete money action", e);
            throw e;
        }
    }

    @Override
    public MoneyAction get(long moneyActionId) throws SQLException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(MoneyAction moneyAction) throws SQLException
    {
        // TODO Auto-generated method stub

    }

}
