package ru.mkardaev.ui.utils.category;

import java.util.Date;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Income;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.ui.utils.InputProvider;

/**
 * Провайдер данных по доходам.
 * 
 * @author Mihail
 *
 */
public class IncomeInputProvider implements InputProvider
{
    private DAOCategory categoryDAO;
    private DAOMoneyAction moneyActionDAO;

    public IncomeInputProvider(DAOCategory categoryDAO, DAOMoneyAction moneyActionDAO)
    {
        this.categoryDAO = categoryDAO;
        this.moneyActionDAO = moneyActionDAO;
    }

    @Override
    public Object getInput(Object... args) throws ApException
    {
        Income income = new Income();
        income.setCreationDate(new Date());
        return new Income[] { income };
    }

}
