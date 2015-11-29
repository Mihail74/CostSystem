package ru.mkardaev.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SessionFactory
{
    private static final String CONNECTION_STRING = "jdbc:derby:CS;create=true";
    private static Map<String, String> initialTableQuery = new LinkedHashMap<>();
    private static SessionFactory instance = new SessionFactory();

    private SessionFactory()
    {

    }

    static
    {
        //@formatter:off
        initialTableQuery.put("category",
                "CREATE TABLE "
                        + "category(id INT PRIMARY KEY  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "title VARCHAR(255))");
        initialTableQuery.put("account",
                "CREATE TABLE "
                        + "account(id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "value int)");
        initialTableQuery.put("person",
                "CREATE TABLE "
                        + "person(id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "account_id INT REFERENCES account(id))");
        initialTableQuery.put("money_action",
                "CREATE TABLE "
                        + "money_action(id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "account_id INT REFERENCES account(id),"
                        + "category_id INT REFERENCES category(id),"
                        + "creation_date TIMESTAMP NOT NULL,"
                        + "value INT NOT NULL,"
                        + "description VARCHAR(255),"
                        + "type INT NOT NULL)");
        //@formatter:on
    }

    public Connection getConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(CONNECTION_STRING);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void init() throws ClassNotFoundException
    {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING))
        {
            initTables(con);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private List<String> getExistingTableNames(Connection con) throws SQLException
    {
        List<String> existingTables = new ArrayList<>();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
        try
        {
            while (res.next())
            {
                existingTables.add(res.getString("TABLE_NAME").toLowerCase());
            }
        }
        finally
        {
            res.close();
        }
        return existingTables;
    }

    private void initTables(Connection con) throws SQLException
    {
        List<String> existingTables = getExistingTableNames(con);

        for (Entry<String, String> entry : initialTableQuery.entrySet())
        {
            if (!existingTables.contains(entry.getKey()))
            {
                try (Statement stmt = con.createStatement())
                {
                    stmt.execute(entry.getValue());
                }
            }
        }

    }

    public static SessionFactory getInstance()
    {
        return instance;
    }
}
