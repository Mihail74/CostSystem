package ru.mkardaev.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class MoneyActionSorter extends ViewerSorter
{
    private static final int ASCENDING = 0;

    private static final int DESCENDING = 1;

    private int column;

    private int direction;

    /**
     * Compares the object for sorting
     */
    @Override
    public int compare(Viewer viewer, Object e1, Object e2)
    {
        return 1;
    }

    /**
     * Does the sort. If it's a different column from the previous sort, do an ascending sort. If it's the same column as the last sort, toggle the
     * sort direction.
     * 
     * @param column
     */
    public void doSort(int column)
    {
        if (column == this.column)
        {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        }
        else
        {
            // New column; do an ascending sort
            this.column = column;
            direction = ASCENDING;
        }
    }
}
