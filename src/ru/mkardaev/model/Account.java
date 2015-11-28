package ru.mkardaev.model;

import ru.mkardaev.utils.IdGenerator;

public class Account
{
	private long id;
	private long value;

	public Account()
	{
		this.id = IdGenerator.generateId();
		this.value = 0L;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof Category))
			return false;
		Account other = (Account) obj;
		return this.id == other.id;
	}

	public long getId()
	{
		return id;
	}

	public Long getValue()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return Long.hashCode(id);
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setValue(long value)
	{
		this.value = value;
	}
}
