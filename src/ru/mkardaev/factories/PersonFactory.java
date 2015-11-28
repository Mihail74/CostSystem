package ru.mkardaev.factories;

import ru.mkardaev.model.Account;
import ru.mkardaev.model.Person;

public class PersonFactory
{
    public static Person createPerson()
    {
        Account account = AccountFactory.createAccount();
        return new Person(account);
    }

    public static Person createPerson(Account account)
    {
        return new Person(account);
    }
}
