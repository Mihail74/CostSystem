package ru.mkardaev.command;

import ru.mkardaev.exception.ApException;

/**
 * Адаптер ICommand. Наследникам достаточно реализовывать только метод {@link #perform()}
 * 
 * @author Mihail
 *
 */
public class CommandAdapter implements ICommand
{
    protected DtObject dtObject;

    @Override
    public void perform() throws ApException
    {

    }

    @Override
    public void setDtObject(DtObject dtObject)
    {
        this.dtObject = dtObject;
    }
}
