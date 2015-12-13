package ru.mkardaev.ui.widget;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.ui.MoneyActionUIModel;
import ru.mkardaev.ui.form.EditExpenseForm;
import ru.mkardaev.ui.services.FormRegistry;
import ru.mkardaev.ui.utils.category.ExpenseInputProvider;
import ru.mkardaev.ui.utils.category.MoneyActionUIModelSorter;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

public class MoneyActionTableWidget
{
    private Date beginDate;
    private Date endDate;
    private Messages messages;
    private ExpenseInputProvider moneyActionInputProvider;
    private Composite parent;

    private Callable<Void> refreshCallback = new Callable<Void>()
    {

        @Override
        public Void call() throws Exception
        {
            refresh();
            return null;
        }

    };
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
        if (endDate == null || beginDate == null || moneyActionInputProvider == null)
        {
            return;
        }
        tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());
        tableViewer.setSorter(new MoneyActionUIModelSorter());

        Table table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        createColumn();
        initTableData();

        for (int i = 0, n = table.getColumnCount(); i < n; i++)
        {
            table.getColumn(i).pack();
        }
        tableViewer.addDoubleClickListener(new IDoubleClickListener()
        {

            @Override
            public void doubleClick(DoubleClickEvent getSelection)
            {
                IStructuredSelection selection = tableViewer.getStructuredSelection();
                MoneyActionUIModel action = (MoneyActionUIModel) selection.getFirstElement();
                EditExpenseForm form = FormRegistry.getInstance()
                        .<EditExpenseForm> getForm(FormRegistry.EDIT_EXPENSE_FORM);
                form.init(action.getMoneyAction());
                form.setSaveCallback(refreshCallback);
                form.bind();
            }
        });
    }

    public Control getControl()
    {
        return tableViewer.getControl();
    }

    public void refresh()
    {
        initTableData();
        tableViewer.refresh();
    }

    public void setDateInterval(Date beginDate, Date endDate)
    {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public void setMoneyActionInputProvider(ExpenseInputProvider moneyActionInputProvider)
    {
        this.moneyActionInputProvider = moneyActionInputProvider;
    }

    private void createColumn()
    {
        TableViewerColumn dateColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        dateColumn.getColumn().setText(messages.getMessage(Messages.Keys.CREATION_DATE));

        // Label providers
        dateColumn.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element)
            {
                MoneyActionUIModel action = (MoneyActionUIModel) element;
                return DateFormat.getDateInstance().format(action.getMoneyAction().getCreationDate());
            }
        });

        TableViewerColumn categoryColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        categoryColumn.getColumn().setText(messages.getMessage(Messages.Keys.CATEGORY));
        categoryColumn.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element)
            {
                MoneyActionUIModel action = (MoneyActionUIModel) element;
                return String.valueOf(action.getCategory().getTitle());
            }
        });

        TableViewerColumn valueColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        valueColumn.getColumn().setText(messages.getMessage(Messages.Keys.VALUE));
        valueColumn.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element)
            {
                MoneyActionUIModel action = (MoneyActionUIModel) element;
                return String.valueOf(action.getMoneyAction().getValue());
            }
        });

        TableViewerColumn descriptionColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        descriptionColumn.getColumn().setText(messages.getMessage(Messages.Keys.DESCRIPTION));
        descriptionColumn.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element)
            {
                MoneyActionUIModel action = (MoneyActionUIModel) element;
                return action.getMoneyAction().getDescription();
            }
        });

        // Column Sort
        dateColumn.getColumn().addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent event)
            {
                ((MoneyActionUIModelSorter) tableViewer.getSorter())
                        .doSort(MoneyActionUIModel.tableColumnNumbers.COLUMN_DATE);
                tableViewer.refresh();
            }
        });
        categoryColumn.getColumn().addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent event)
            {
                ((MoneyActionUIModelSorter) tableViewer.getSorter())
                        .doSort(MoneyActionUIModel.tableColumnNumbers.COLUMN_CATEGORY);
                tableViewer.refresh();
            }
        });
        valueColumn.getColumn().addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent event)
            {
                ((MoneyActionUIModelSorter) tableViewer.getSorter())
                        .doSort(MoneyActionUIModel.tableColumnNumbers.COLUMN_VALUE);
                tableViewer.refresh();
            }
        });
        descriptionColumn.getColumn().addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent event)
            {
                ((MoneyActionUIModelSorter) tableViewer.getSorter())
                        .doSort(MoneyActionUIModel.tableColumnNumbers.COLUMN_DESCRIPTION);
                tableViewer.refresh();
            }
        });
    }

    private void initTableData()
    {
        try
        {
            tableViewer.setInput(moneyActionInputProvider.getInput(DateUtils.getStartDayOfDate(beginDate),
                    DateUtils.getEndDayOfDate(endDate)));
        }
        catch (ApException e)
        {
            // TODO ошибка при загрузке данных
            e.printStackTrace();
        }
    }
}
