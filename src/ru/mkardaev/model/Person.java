package ru.mkardaev.model;

import ru.mkardaev.utils.IdGenerator;

public class Person
{
	private Account account;
	private long id;

	public Person(Account account)
	{
		this.id = IdGenerator.generateId();
		this.setAccount(account);
	}

	public Account getAccount()
	{
		return account;
	}

	public long getId()
	{
		return id;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public void setId(long id)
	{
		this.id = id;
	}
}
