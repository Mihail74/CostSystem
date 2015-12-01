package ru.mkardaev.factories;

import ru.mkardaev.model.Person;

public interface PersonFactory
{
    Person createPerson(long id, long accountId);
}
