package ru.mkardaev.factories;

import java.util.Locale;

import ru.mkardaev.factories.impl.AccountFactoryImpl;
import ru.mkardaev.factories.impl.CategoryFactoryImpl;
import ru.mkardaev.factories.impl.MoneyActionFactoryImpl;
import ru.mkardaev.factories.impl.PersonFactoryImpl;
import ru.mkardaev.persistence.ConnectionService;
import ru.mkardaev.persistence.DAOAccount;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.persistence.impl.DAOAccountImpl;
import ru.mkardaev.persistence.impl.DAOCategoryImpl;
import ru.mkardaev.persistence.impl.DAOMoneyActionImpl;
import ru.mkardaev.utils.Messages;

public class ServicesFactory
{
    private static ServicesFactory instance = new ServicesFactory();

    private AccountFactory accountFactory;
    private CategoryFactory categoryFactory;
    private ConnectionService connectionService;
    private DAOAccount daoAccount;
    private DAOCategory daoCategory;
    private DAOMoneyAction daoMoneyAction;
    private Messages messages;
    private MoneyActionFactory moneyActionFactory;
    private PersonFactory personFactory;

    private ServicesFactory()
    {
    }

    public AccountFactory getAccountFactory()
    {
        return accountFactory;
    }

    public CategoryFactory getCategoryFactory()
    {
        return categoryFactory;
    }

    public ConnectionService getConnectionService()
    {
        return connectionService;
    }

    public DAOAccount getDaoAccount()
    {
        return daoAccount;
    }

    public DAOCategory getDaoCategory()
    {
        return daoCategory;
    }

    public DAOMoneyAction getDaoMoneyAction()
    {
        return daoMoneyAction;
    }

    public Messages getMessages()
    {
        return messages;
    }

    public MoneyActionFactory getMoneyActionFactory()
    {
        return moneyActionFactory;
    }

    public PersonFactory getPersonFactory()
    {
        return personFactory;
    }

    public void init() throws ClassNotFoundException
    {
        messages = Messages.getInstance();
        messages.initalize(new Locale("RU"));
        connectionService = ConnectionService.getInstance();
        connectionService.init();
        accountFactory = new AccountFactoryImpl();
        categoryFactory = new CategoryFactoryImpl();
        personFactory = new PersonFactoryImpl();
        moneyActionFactory = new MoneyActionFactoryImpl();
        daoAccount = new DAOAccountImpl(accountFactory);
        daoCategory = new DAOCategoryImpl(categoryFactory);
        daoMoneyAction = new DAOMoneyActionImpl(moneyActionFactory);
    }

    public static ServicesFactory getInstance()
    {
        return instance;

    }
}
