package ru.mkardaev.factories;

import ru.mkardaev.model.Account;

public class AccountFactory
{
	public static Account createAccount()
	{
		return new Account();
	}
}
