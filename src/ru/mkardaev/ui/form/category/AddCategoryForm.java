package ru.mkardaev.ui.form.category;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.mkardaev.command.DtObject;
import ru.mkardaev.command.ICommand;
import ru.mkardaev.command.category.SaveCategoryCommand;
import ru.mkardaev.exception.ApException;
import ru.mkardaev.factories.ServicesFactory;
import ru.mkardaev.model.Category;
import ru.mkardaev.resources.ApplicationContext;
import ru.mkardaev.resources.Resources;
import ru.mkardaev.ui.form.MessageBoxFactory;
import ru.mkardaev.utils.Messages;

public class AddCategoryForm
{
    private int MIN_BUTTON_WIDTH = 100;

    private Button okButton;
    private Button cancelButton;

    private Shell dialogShell;
    private Messages messages;
    private Text titleText;
    protected ICommand saveCommand;

    public AddCategoryForm()
    {
        saveCommand = new SaveCategoryCommand();
        messages = ServicesFactory.getInstance().getMessages();
    }

    public void bind()
    {
        Display display = Display.getDefault();
        dialogShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
        dialogShell.setLayout(new GridLayout(1, true));

        Image formImage = new Image(display, Resources.FORM_ICON_PATH);
        dialogShell.setImage(formImage);
        dialogShell.setText(messages.getMessage(Messages.Keys.ADD_CATEGORY));

        createFormContent(dialogShell);
        createButtons(dialogShell);

        dialogShell.open();
        dialogShell.pack();
        while (!dialogShell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        formImage.dispose();
    }

    protected void save()
    {
        try
        {
            String title = titleText.getText();
            Category category = ServicesFactory.getInstance().getCategoryFactory().createCategory(title);

            DtObject dtObject = new DtObject();
            dtObject.putProperty(ApplicationContext.CATEGORY, category);
            saveCommand.setDtObject(dtObject);
            saveCommand.perform();
        }
        catch (ApException e)
        {
            MessageBoxFactory.getErrorMessageBox(dialogShell, messages.getMessage(Messages.Keys.ERROR),
                    messages.getMessage(Messages.Keys.ERROR_ON_SAVE)).open();
            e.printStackTrace();
        }
        dialogShell.dispose();
    }

    private Button createButton(Composite parent, String text, int horizontalAlignment)
    {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = horizontalAlignment;
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = MIN_BUTTON_WIDTH;

        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        button.setLayoutData(gridData);

        return button;
    }

    /**
     * Создаёт кнопки "Сохранить" и "Отмена"
     * 
     */
    private void createButtons(Shell dialogShell)
    {
        Composite composite = new Composite(dialogShell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        okButton = createButton(composite, messages.getMessage(Messages.Keys.SAVE), SWT.RIGHT);
        cancelButton = createButton(composite, messages.getMessage(Messages.Keys.CANCEL), SWT.LEFT);
        okButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    okButton.setToolTipText("");
                    if ("".equals(titleText.getText().trim()))
                    {
                        okButton.setToolTipText(messages.getMessage(Messages.Keys.TITLE_SHOULD_BE_NOT_EMPTY));
                    }
                    else
                    {
                        save();
                    }
                }
            }
        });
        cancelButton.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                if (e.type == SWT.Selection)
                {
                    dialogShell.dispose();
                }
            }
        });
    }

    private void createFormContent(Shell dialogShell)
    {
        Composite composite = new Composite(dialogShell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label titleLabel = new Label(composite, SWT.NONE);
        titleLabel.setText(messages.getMessage(Messages.Keys.TITLE));
        titleText = new Text(composite, SWT.BORDER);

        titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        titleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }
}
