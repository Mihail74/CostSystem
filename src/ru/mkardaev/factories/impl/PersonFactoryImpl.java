package ru.mkardaev.factories.impl;

import ru.mkardaev.factories.PersonFactory;
import ru.mkardaev.model.Person;

public class PersonFactoryImpl implements PersonFactory
{
    @Override
    public Person createPerson(long id, long accountId)
    {
        return new Person(id, accountId);
    }
}
