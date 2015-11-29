package ru.mkardaev.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Category;

public class DAOCategoryImpl implements DAOCategory
{

    private static final String DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";
    private static final String INSERT_CATEGORY = "INSERT INTO category(title) VALUES(?)";

    @Override
    public void create(Category category) throws ApException
    {
        try (Connection connection = SessionFactory.getInstance().getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setString(1, category.getTitle());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys())
                {
                    if (!rs.next())
                    {
                        throw new ApException("Can't save category");
                    }
                    category.setId(rs.getInt(1));
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long categoryId)
    {
        try (Connection connection = SessionFactory.getInstance().getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_CATEGORY, Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setLong(1, categoryId);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Category get(long categoryId)
    {
        return null;
    }

    @Override
    public void update(Category category)
    {

    }

}
