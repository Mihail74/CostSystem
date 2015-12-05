package ru.mkardaev.exception;

/**
 * Базовый класс ошибок в приложении
 * 
 * @author Mihail
 *
 */
public class ApException extends Exception
{
    private static final long serialVersionUID = 3885485281805565825L;

    private Exception cause;
    private String message;

    public ApException()
    {

    }

    public ApException(String message)
    {
        this.message = message;
    }

    public ApException(String message, Exception cause)
    {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public Exception getCause()
    {
        return cause;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public void setCause(Exception cause)
    {
        this.cause = cause;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
