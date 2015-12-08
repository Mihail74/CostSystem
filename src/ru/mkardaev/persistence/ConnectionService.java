package ru.mkardaev.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import ru.mkardaev.factories.AccountFactory;
import ru.mkardaev.factories.impl.AccountFactoryImpl;
import ru.mkardaev.factories.impl.PersonFactoryImpl;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Person;
import ru.mkardaev.resources.ApplicationContext;

/**
 * Сервис подключений к БД.
 * 
 * @author Mihail
 *
 */
public class ConnectionService
{
    final static Logger logger = Logger.getLogger(ConnectionService.class);
    private static final String CONNECTION_STRING = "jdbc:derby:CS;create=true";

    private static Map<String, String> initialTableQuery = new LinkedHashMap<>();
    private static ConnectionService instance = new ConnectionService();

    private Connection connection = null;

    private ConnectionService()
    {
      //@formatter:off
        initialTableQuery.put("category",
                "CREATE TABLE "
                        + "category(id BIGINT PRIMARY KEY  GENERATED ALWAYS AS IDENTITY,"
                        + "title VARCHAR(255))");
        initialTableQuery.put("account",
                "CREATE TABLE "
                        + "account(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "value BIGINT)");
        initialTableQuery.put("person",
                "CREATE TABLE "
                        + "person(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "account_id BIGINT REFERENCES account(id))");
        initialTableQuery.put("money_action",
                "CREATE TABLE "
                        + "money_action(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "account_id BIGINT REFERENCES account(id),"
                        + "category_id BIGINT REFERENCES category(id),"
                        + "creation_date BIGINT NOT NULL,"
                        + "value BIGINT NOT NULL,"
                        + "description VARCHAR(255),"
                        + "type BIGINT NOT NULL)");
        //@formatter:on
    }

    public void closeConnection()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(CONNECTION_STRING);
            }
        }
        catch (SQLException e)
        {
            logger.error("Error create connection", e);
        }

        return connection;
    }

    public void init() throws ClassNotFoundException
    {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        addShutDownHook();

        try (Connection con = getConnection())
        {
            initTables(con);
            createTestPersonAndAccount();
            logger.info("Tables initialized.");
        }
        catch (SQLException e)
        {
            logger.error("Error initialize tables", e);
        }

    }

    private void addShutDownHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                getInstance().closeConnection();
            }
        });
    }

    private void createTestPersonAndAccount() throws SQLException
    {
        AccountFactory accountFactory = new AccountFactoryImpl();
        PersonFactoryImpl personFactory = new PersonFactoryImpl();

        Person currentPerson = null;
        Account currentAccount = null;

        try (Statement stmt = getConnection().createStatement())
        {
            ResultSet rs = stmt.executeQuery("SELECT id, value FROM account FETCH FIRST 1 ROWS ONLY");
            if (rs.next())
            {
                currentAccount = accountFactory.createAccount(rs.getLong(1), rs.getLong(2));
            }
        }
        try (Statement stmt = getConnection().createStatement())
        {
            ResultSet rs;

            if (currentAccount != null)
            {
                rs = stmt.executeQuery("SELECT id, account_id FROM person WHERE account_id = " + currentAccount.getId()
                        + " FETCH FIRST 1 ROWS ONLY ");
            }
            else
            {
                rs = stmt.executeQuery("SELECT id, account_id FROM person FETCH FIRST 1 ROWS ONLY");
            }
            if (rs.next())
            {
                currentPerson = personFactory.createPerson(rs.getLong(1), rs.getLong(2));
            }
        }

        if (currentAccount == null)
        {
            try (PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO account(value) VALUES(0)",
                    Statement.RETURN_GENERATED_KEYS))
            {
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                currentAccount = accountFactory.createAccount(rs.getLong(1), 0L);
            }
        }
        if (currentPerson == null)
        {
            try (PreparedStatement stmt = getConnection().prepareStatement(
                    "INSERT INTO person(account_id) VALUES(" + currentAccount.getId() + ")",
                    Statement.RETURN_GENERATED_KEYS))
            {
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                currentPerson = personFactory.createPerson(rs.getLong(1), currentAccount.getId());
            }
        }
        ApplicationContext.getContext().putData(ApplicationContext.CURRENT_ACCOUNT, currentAccount);
        ApplicationContext.getContext().putData(ApplicationContext.CURRENT_PERSON, currentPerson);
        logger.info(String.format("Current account: id = %d", currentAccount.getId()));
        logger.info(String.format("Current person: id = %d, account_id = %d ", currentPerson.getId(),
                currentPerson.getAccountId()));
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

    /**
     * Производит создание всех таблиц, если это необходимо.
     * 
     */
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

    public static ConnectionService getInstance()
    {
        return instance;
    }
}
