package ru.mkardaev.ui.models.sorters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import ru.mkardaev.model.Category;

/**
 * Класс, реализующий сортировку Категорий во Viewer'ах
 * 
 * @author Mihail
 *
 */
public class CategorySorter extends ViewerSorter
{
    @Override
    public int compare(Viewer viewer, Object e1, Object e2)
    {
        Category category1 = (Category) e1;
        Category category2 = (Category) e2;

        return category1.getTitle().compareTo(category2.getTitle());
    }
}
