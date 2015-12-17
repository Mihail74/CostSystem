package ru.mkardaev.ui.widgets;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import ru.mkardaev.command.DeleteMoneyActionCommand;
import ru.mkardaev.command.DtObject;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.HasRefresh;
import ru.mkardaev.ui.models.DateInterval;
import ru.mkardaev.ui.models.MoneyActionUIModel;
import ru.mkardaev.ui.utils.InputProvider;
import ru.mkardaev.ui.utils.MoneyActionUIModelSorter;
import ru.mkardaev.utils.DateUtils;
import ru.mkardaev.utils.Messages;

/**
 * Виджет таблицы, отображающей доходы/расходы
 * 
 * @author Mihail
 *
 */
public class MoneyActionTableWidget implements HasRefresh
{
    /**
     * Т.к. в таблице используются кнопки, то необходимо освобождать ресурсы при переинициализации кнопок, например при обновлении таблицы. Для этого
     * будет использоваться данный contentProvider
     *
     */
    private class ArrayContentProvider extends org.eclipse.jface.viewers.ArrayContentProvider
    {
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
        {
            if (((TableViewer) viewer).getTable() != null && ((TableViewer) viewer).getTable().getChildren() != null)
            {
                for (Control item : ((TableViewer) viewer).getTable().getChildren())
                {
                    if ((item != null) && (!item.isDisposed()))
                    {
                        ((Button) item).getImage().dispose();
                        item.dispose();
                    }
                }
            }
        }
    }

    private static final String MONEY_VALUE_PRINT_FORMAT = "%.2f";

    private DateInterval dateInterval;

    private Messages messages;

    private InputProvider moneyActionInputProvider;
    private ICommand doubleClickTableCommand;

    private Composite parent;
    private HasRefresh parentForm;
    private TableViewer tableViewer;

    public MoneyActionTableWidget(Composite parent)
    {
        this.parent = parent;
        messages = ServicesFactory.getInstance().getMessages();
    }

    /**
     * Производит построение виджета. До начала построения необходимо установить виджету интервал времени для которого необходимо отображать данны и
     * InputProvider, предоставляющий данные для таблицы, используя методы {@link #setDateInterval(DateInterval))},
     * {@link #setMoneyActionInputProvider(InputProvider)}
     */
    public void bind()
    {
        if (dateInterval == null || moneyActionInputProvider == null)
        {
            return;
        }
        tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setSorter(new MoneyActionUIModelSorter());

        Table table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        createColumns();
        initTableData();

        tableViewer.addDoubleClickListener(new IDoubleClickListener()
        {
            @Override
            public void doubleClick(DoubleClickEvent getSelection)
            {
                if (doubleClickTableCommand != null)
                {
                    IStructuredSelection selection = tableViewer.getStructuredSelection();
                    MoneyActionUIModel action = (MoneyActionUIModel) selection.getFirstElement();

                    DtObject dtObject = new DtObject();
                    dtObject.putProperty(ApplicationContext.MONEY_ACTION_MODEL, action.getMoneyAction());

                    doubleClickTableCommand.setDtObject(dtObject);
                    try
                    {
                        doubleClickTableCommand.perform();
                    }
                    catch (ApException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (parentForm != null)
                    {
                        parentForm.refresh();
                    }
                    else
                    {
                        refresh();
                    }
                }
            }
        });

        for (int i = 0, n = table.getColumnCount(); i < n; i++)
        {
            table.getColumn(i).pack();
        }
    }

    public Control getControl()
    {
        return tableViewer.getControl();
    }

    @Override
    public void refresh()
    {
        initTableData();
        tableViewer.refresh();
    }

    /**
     * Устанавливает интервал дат для которых будут отображаться доходы/расходы
     * 
     * @param dateInterval
     */
    public void setDateInterval(DateInterval dateInterval)
    {
        this.dateInterval = dateInterval;
    }

    public void setDoubleClickTableCommand(ICommand doubleClickTableCommand)
    {
        this.doubleClickTableCommand = doubleClickTableCommand;
    }

    public void setMoneyActionInputProvider(InputProvider moneyActionInputProvider)
    {
        this.moneyActionInputProvider = moneyActionInputProvider;
    }

    /**
     * Устанавливает для виджета родительскую форму, имеющую метод refresh. При изменении данных на виджете будет вызываться parentForm.refresh()
     * 
     * @param parentForm
     */
    public void setParentForm(HasRefresh parentForm)
    {
        this.parentForm = parentForm;
    }

    private void createColumns()
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
                return String.format(MONEY_VALUE_PRINT_FORMAT, action.getMoneyAction().getValue());
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

        TableViewerColumn deleteColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        deleteColumn.setLabelProvider(new ColumnLabelProvider()
        {
            // Для того, чтобы не забирать постоянно ресурсы у системы, будем запоминать созданные кнпоки.
            // При переинициализации таблицы сработает логика по dispos'у кнопок в специальном контент-провадйере таблицы
            Map<Object, Button> buttons = new HashMap<Object, Button>();

            @Override
            public void update(ViewerCell cell)
            {
                TableItem item = (TableItem) cell.getItem();
                Button button;
                if (buttons.containsKey(cell.getElement()) && !buttons.get(cell.getElement()).isDisposed())
                {
                    button = buttons.get(cell.getElement());
                }
                else
                {
                    button = new Button((Composite) cell.getViewerRow().getControl(), SWT.NONE);
                    // TODO: иконку надо вынести отдельно
                    button.setImage(new Image(Display.getDefault(), Resources.DELETE_ICON_PATH));
                    buttons.put(cell.getElement(), button);
                }
                long moneyActionId = ((MoneyActionUIModel) cell.getElement()).getMoneyAction().getId();
                button.addListener(SWT.Selection, new Listener()
                {
                    @Override
                    public void handleEvent(Event e)
                    {
                        // Второе условие нужно чтобы не срабатывало нажатие на освобождённую кнопку, такое поведение возникает при обновлении таблицы
                        // после удаления строки
                        if (e.type == SWT.Selection && !e.widget.isDisposed())
                        {
                            MessageBox messageBox = new MessageBox(parent.getShell(),
                                    SWT.ICON_WARNING | SWT.YES | SWT.NO);

                            messageBox.setText("Подтверждение удаление");
                            messageBox.setMessage("Вы действительно хотите удалить затрату?");
                            int buttonID = messageBox.open();
                            switch (buttonID) {
                            case SWT.YES:
                                DeleteMoneyActionCommand delCommand = new DeleteMoneyActionCommand();
                                DtObject dt = new DtObject();
                                dt.putProperty(ApplicationContext.MONEY_ACTION_ID, moneyActionId);
                                delCommand.setDtObject(dt);
                                try
                                {
                                    delCommand.perform();
                                    if (parentForm != null)
                                    {
                                        parentForm.refresh();
                                    }
                                    else
                                    {
                                        refresh();
                                    }
                                }
                                catch (ApException e1)
                                {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                break;
                            case SWT.NO:
                                break;
                            }
                        }
                    }
                });
                TableEditor editor = new TableEditor(item.getParent());
                editor.grabHorizontal = true;
                editor.grabVertical = true;
                editor.setEditor(button, item, cell.getColumnIndex());
                editor.layout();
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
            tableViewer
                    .setInput(moneyActionInputProvider.getInput(DateUtils.getStartDayOfDate(dateInterval.getFromDate()),
                            DateUtils.getEndDayOfDate(dateInterval.getToDate())));
        }
        catch (ApException e)
        {
            // TODO ошибка при загрузке данных
            e.printStackTrace();
        }
    }
}
