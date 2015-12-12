package ru.mkardaev.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.CategoryFactory;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Category;
import ru.mkardaev.persistence.DAOCategory;

public class DAOCategoryImpl implements DAOCategory
{

    final static Logger logger = Logger.getLogger(DAOCategoryImpl.class);

    private static final String DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";
    private static final String INSERT_CATEGORY = "INSERT INTO category(title) VALUES(?)";
    private static final String SELECT_ALL_CATEGORY = "SELECT id, title FROM category";
    private static final String SELECT_CATEGORY = "SELECT id, title FROM category WHERE id = ?";
    private static final String UPDATE_CATEGORY = "UPDATE category SET title = ? WHERE id = ?";
    private CategoryFactory categoryFactory;

    public DAOCategoryImpl(CategoryFactory categoryFactory)
    {
        this.categoryFactory = categoryFactory;
    }

    @Override
    public void create(Category category) throws ApException, SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setString(1, category.getTitle());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys())
                {
                    if (!rs.next())
                    {
                        throw new ApException("Failed to save category");
                    }
                    category.setId(rs.getInt(1));
                }
            }

        }
        catch (SQLException e)
        {
            logger.error("Error save category", e);
            throw e;
        }
    }

    @Override
    public void delete(long categoryId) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_CATEGORY, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, categoryId);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            logger.error("Error delete category", e);
            throw e;
        }
    }

    @Override
    public Category get(long categoryId) throws SQLException
    {
        Category category = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(SELECT_CATEGORY))
            {
                stmt.setLong(1, categoryId);
                try (ResultSet rs = stmt.executeQuery())
                {
                    if (rs.next())
                    {
                        category = categoryFactory.createCategory(rs.getLong(1), rs.getString(2));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Error load category", e);
            throw e;
        }
        return category;
    }

    @Override
    public List<Category> getAllCategories() throws ApException
    {
        List<Category> result = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_CATEGORY))
            {
                try (ResultSet rs = stmt.executeQuery())
                {
                    while (rs.next())
                    {
                        result.add(categoryFactory.createCategory(rs.getLong(1), rs.getString(2)));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Error load categories", e);
            throw new ApException("Error load categories", e);
        }
        return result;
    }

    @Override
    public void update(Category category) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_CATEGORY))
            {
                stmt.setString(1, category.getTitle());
                stmt.setLong(2, category.getId());
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
