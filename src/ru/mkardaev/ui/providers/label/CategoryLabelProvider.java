package ru.mkardaev.ui.providers.label;

import org.eclipse.jface.viewers.LabelProvider;

import ru.mkardaev.model.Category;

/**
 * LabelProvider Категорий
 * 
 * @author Mihail
 *
 */
public class CategoryLabelProvider extends LabelProvider
{
    @Override
    public String getText(Object element)
    {
        if (element instanceof Category)
        {
            return ((Category) element).getTitle();
        }
        return "";
    }

}
