package ru.mkardaev.persistence;

import java.util.Date;
import java.util.List;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.MoneyAction;

public interface DAOMoneyAction extends DAO<MoneyAction>
{

    /**
     * список действий с финансами для заданного аккаунта для заданного диапазона дат, включая границы диапазона.
     * 
     */
    List<MoneyAction> getByCreationDate(Account account, Date startDate, Date endDate) throws ApException;
}
