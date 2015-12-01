package ru.mkardaev.factories;

import ru.mkardaev.model.Account;

public interface AccountFactory
{
    Account createAccount(long id);

    Account createAccount(long id, long value);
}
