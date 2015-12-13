package ru.mkardaev.ui.utils;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class MoneyActionSorter extends ViewerSorter
{
    @Override
    public int compare(Viewer viewer, Object e1, Object e2)
    {
        return 1;
    }
}
