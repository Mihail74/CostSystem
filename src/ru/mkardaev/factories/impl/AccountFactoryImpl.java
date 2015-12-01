package ru.mkardaev.factories.impl;

import ru.mkardaev.factories.AccountFactory;
import ru.mkardaev.model.Account;

public class AccountFactoryImpl implements AccountFactory
{
    @Override
    public Account createAccount(long id)
    {
        return createAccount(id, 0L);
    }

    @Override
    public Account createAccount(long id, long value)
    {
        return new Account(id, value);
    }
}
