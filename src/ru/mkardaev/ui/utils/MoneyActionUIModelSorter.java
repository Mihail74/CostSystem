package ru.mkardaev.ui.utils;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import ru.mkardaev.ui.models.MoneyActionUIModel;

public class MoneyActionUIModelSorter extends ViewerSorter
{
    private static final int ASCENDING = 0;
    private static final int DESCENDING = 1;
    private int column;
    private int direction;

    @Override
    public int compare(Viewer viewer, Object e1, Object e2)
    {
        int result = 0;
        MoneyActionUIModel action1 = (MoneyActionUIModel) e1;
        MoneyActionUIModel action2 = (MoneyActionUIModel) e2;

        switch (column) {
        case MoneyActionUIModel.tableColumnNumbers.COLUMN_DATE:
            result = action1.getMoneyAction().getCreationDate().compareTo(action2.getMoneyAction().getCreationDate());
            break;
        case MoneyActionUIModel.tableColumnNumbers.COLUMN_CATEGORY:
            result = action1.getCategory().getTitle().compareTo(action2.getCategory().getTitle());
            break;
        case MoneyActionUIModel.tableColumnNumbers.COLUMN_VALUE:
            result = Double.compare(action1.getMoneyAction().getValue(), action2.getMoneyAction().getValue());
            break;
        case MoneyActionUIModel.tableColumnNumbers.COLUMN_DESCRIPTION:
            result = action1.getMoneyAction().getDescription().compareTo(action2.getMoneyAction().getDescription());
            break;

        }

        if (direction == DESCENDING)
        {
            result = -result;
        }

        return result;
    }

    public void doSort(int column)
    {
        if (column == this.column)
        {
            direction = 1 - direction;
        }
        else
        {
            this.column = column;
            direction = ASCENDING;
        }
    }
}
