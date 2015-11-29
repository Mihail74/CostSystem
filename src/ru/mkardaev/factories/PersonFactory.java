package ru.mkardaev.factories;

import ru.mkardaev.model.Person;

public class PersonFactory
{
    public static Person createPerson(long accountId)
    {
        return new Person(accountId);
    }
}
