package ru.mkardaev.ui.providers;

import org.eclipse.jface.viewers.LabelProvider;

import ru.mkardaev.model.Category;

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
