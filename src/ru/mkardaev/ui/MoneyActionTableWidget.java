package ru.mkardaev.ui;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Person;
import ru.mkardaev.utils.Messages;

public class MoneyActionTableWidget
{
    private Messages messages;
    private Composite parent;
    private TableViewer tableViewer;
    private FormToolkit toolKit;

    public MoneyActionTableWidget(Composite parent)
    {
        this.parent = parent;
        toolKit = new FormToolkit(parent.getDisplay());
        messages = ServicesFactory.getInstance().getMessages();
    }

    public void bind()
    {
        tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

        Table table = tableViewer.getTable();

        tableViewer.setSorter(new MoneyActionSorter());
        TableViewerColumn column1 = new TableViewerColumn(tableViewer, SWT.NONE);
        column1.getColumn().setText("test1");
        column1.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element)
            {
                Person p = (Person) element;
                return String.valueOf(p.getId());
            }
        });

        column1.getColumn().addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent event)
            {
                // for sort
                int x = 5;
                x++;
            }
        });

        TableViewerColumn column2 = new TableViewerColumn(tableViewer, SWT.NONE);
        column2.getColumn().setText("test2");
        column2.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element)
            {
                Person p = (Person) element;
                return String.valueOf(p.getAccountId());
            }
        });

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        tableViewer.setInput(new Person[] { new Person(1, 2), new Person(3, 4) });

        for (int i = 0, n = table.getColumnCount(); i < n; i++)
        {
            table.getColumn(i).pack();
        }
        tableViewer.addDoubleClickListener(new IDoubleClickListener()
        {

            @Override
            public void doubleClick(DoubleClickEvent getSelection)
            {
                // IStructuredSelection selection = tableViewer.getStructuredSelection();
                // Object firstElement = selection.getFirstElement();
                // int x = 5;
                // x++;

            }
        });
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
        {
            @Override
            public void selectionChanged(SelectionChangedEvent event)
            {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                Object firstElement = selection.getFirstElement();
                int x = 5;
                x++;
            }
        });
    }

    public Control getControl()
    {
        return tableViewer.getControl();
    }
}
