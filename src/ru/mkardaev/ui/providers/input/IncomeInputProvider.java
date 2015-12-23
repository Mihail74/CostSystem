package ru.mkardaev.ui.providers.input;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.model.Account;
import ru.mkardaev.model.Category;
import ru.mkardaev.model.Income;
import ru.mkardaev.persistence.DAOCategory;
import ru.mkardaev.persistence.DAOMoneyAction;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.ui.models.MoneyActionUIModel;

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
        if (args.length < 2)
        {
            return new Object();
        }
        Account account = ApplicationContext.getContext().getData(ApplicationContext.CURRENT_ACCOUNT);
        Date beginDate = (Date) args[0];
        Date endDate = (Date) args[1];
        Map<Long, Category> categories = categoryDAO.getAllCategories().stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));

        List<Income> incomes = moneyActionDAO.getByCreationDate(account, beginDate, endDate).stream()
                .filter(e -> e instanceof Income).map(e -> (Income) e).collect(Collectors.toList());

        List<MoneyActionUIModel> result = new ArrayList<>();
        for (Income income : incomes)
        {
            result.add(new MoneyActionUIModel(income, categories.get(income.getCategoryId())));
        }
        return result.toArray();
    }

}
