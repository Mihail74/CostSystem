package ru.mkardaev.entrypoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GridLoginForm
{
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final int FORM_HEIGHT_MARGIN = 8;
    private static final int FORM_WIDTH_MARGIN = 8;
    private static final String LOGIN_FORM_TITLE = "Login form";
    private static final String LOGIN_LABEL_TEXT = "Login:";
    private static final int MIN_BUTTON_WIDTH = 50;
    private static final String OK_BUTTON_TEXT = "Ok";
    private static final String PASSWORD_LABEL_TEXT = "Password:";
    public Button cancelButton;
    public Text loginText;
    public Button okButton;
    public Text passwordText;

    public GridLoginForm(Shell shell)
    {
        shell.setText(LOGIN_FORM_TITLE);
        createFormLayout(shell);

        createLabelAndTextComposite(shell);
        createButtonsComposite(shell);
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

    private void createButtonsComposite(Shell shell)
    {
        Composite buttonComposite = createGridLayoutComposite(shell, 2);
        setAutoResizeLayoutData(buttonComposite, SWT.CENTER);

        this.okButton = createButton(buttonComposite, OK_BUTTON_TEXT, SWT.RIGHT);
        this.cancelButton = createButton(buttonComposite, CANCEL_BUTTON_TEXT, SWT.LEFT);
    }

    private void createFormLayout(Shell shell)
    {
        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.marginWidth = FORM_WIDTH_MARGIN;
        gridLayout.marginHeight = FORM_HEIGHT_MARGIN;

        shell.setLayout(gridLayout);
        setAutoResizeLayoutData(shell, SWT.FILL);
    }

    private Composite createGridLayoutComposite(Composite parent, int countColumn)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(countColumn, true));
        return composite;
    }

    private Group createGroupGridLayout(Shell shell, int countColumn)
    {
        Group loginPassGroup = new Group(shell, SWT.NONE);
        loginPassGroup.setLayout(new GridLayout(countColumn, true));
        return loginPassGroup;
    }

    private Label createLabel(Composite parent, String text, int style)
    {
        Label label = new Label(parent, style);
        label.setText(text);
        setAutoResizeLayoutData(label, SWT.FILL);
        return label;
    }

    private void createLabelAndTextComposite(Shell shell)
    {
        Group labelsAndTexts = createGroupGridLayout(shell, 2);
        setAutoResizeLayoutData(labelsAndTexts, SWT.FILL);

        createLabelsComposite(labelsAndTexts);
        createTextsComposite(labelsAndTexts);
    }

    private void createLabelsComposite(Group loginPassGroup)
    {
        Composite labelComposite = createGridLayoutComposite(loginPassGroup, 1);
        setAutoResizeLayoutData(labelComposite, SWT.FILL);

        createLabel(labelComposite, LOGIN_LABEL_TEXT, SWT.NONE);
        createLabel(labelComposite, PASSWORD_LABEL_TEXT, SWT.NONE);

    }

    private Text createText(Composite parent, int style)
    {
        Text text = new Text(parent, style);
        setAutoResizeLayoutData(text, SWT.FILL);
        return text;
    }

    private void createTextsComposite(Group parent)
    {
        Composite textComposite = createGridLayoutComposite(parent, 1);
        setAutoResizeLayoutData(textComposite, SWT.FILL);

        this.loginText = createText(textComposite, SWT.BORDER);
        this.passwordText = createText(textComposite, SWT.BORDER);
    }

    /**
     * Устанавливает контролу GridData, с помощью которой происходит autoresize.
     * 
     * @param style - стиль выравнивания
     */
    private void setAutoResizeLayoutData(Control control, int aligment)
    {
        control.setLayoutData(new GridData(aligment, SWT.FILL, true, true));
    }
}
