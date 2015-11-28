package ru.mkardaev.entrypoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FormLoginForm
{
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final int DEFAULT_MARGIN = 8;
    private static final String LOGIN_FORM_TITLE = "Login form";
    private static final String LOGIN_LABEL_TEXT = "Login:";
    private static final int MIN_BUTTON_WIDTH = 50;
    private static final String OK_BUTTON_TEXT = "Ok";
    private static final String PASSWORD_LABEL_TEXT = "Password:";
    public Button cancelButton;
    public Text loginText;
    public Button okButton;
    public Text passwordText;
    private Group labelAndTextGroup = null;

    public FormLoginForm(Shell shell)
    {
        shell.setText(LOGIN_FORM_TITLE);
        createFormLayout(shell);

        createLabelAndTextComposite(shell);
        createButtonsComposite(shell);
    }

    private Button createButton(Composite parent, String text)
    {
        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        return button;
    }

    private void createButtonsComposite(Shell shell)
    {
        Composite buttonComposite = new Composite(shell, SWT.NONE);
        buttonComposite.setLayout(new FormLayout());
        buttonComposite.setLayoutData(createFormData(new FormAttachment(0, 0),
                new FormAttachment(labelAndTextGroup, DEFAULT_MARGIN), new FormAttachment(100, 0), null));

        Button okButton = createButton(buttonComposite, OK_BUTTON_TEXT);
        Button cancelButton = createButton(buttonComposite, CANCEL_BUTTON_TEXT);

        FormData data = new FormData();
        data.width = MIN_BUTTON_WIDTH;
        data.right = new FormAttachment(50, 0);

        okButton.setLayoutData(data);

        data = new FormData();
        data.width = MIN_BUTTON_WIDTH;
        data.left = new FormAttachment(okButton, 0);

        cancelButton.setLayoutData(data);
    }

    private FormData createFormData(FormAttachment left, FormAttachment top, FormAttachment right,
            FormAttachment bottom)
    {
        FormData data = new FormData();
        data.left = left;
        data.top = top;
        data.right = right;
        data.bottom = bottom;
        return data;
    }

    private void createFormLayout(Shell shell)
    {
        FormLayout layout = new FormLayout();
        layout.marginWidth = DEFAULT_MARGIN;
        layout.marginHeight = DEFAULT_MARGIN;
        shell.setLayout(layout);
    }

    private Label createLabel(Composite parent, String text)
    {
        Label login = new Label(parent, SWT.NONE);
        login.setText(text);
        return login;
    }

    private void createLabelAndTextComposite(Shell shell)
    {
        createLabelAndTextGroupLayout(shell);

        Label loginLabel = createLabel(labelAndTextGroup, LOGIN_LABEL_TEXT);
        Label passwordLabel = createLabel(labelAndTextGroup, PASSWORD_LABEL_TEXT);

        Text loginText = createText(labelAndTextGroup);
        Text passwordText = createText(labelAndTextGroup);

        loginLabel.setLayoutData(createFormData(null, new FormAttachment(loginText, 0, SWT.CENTER), null, null));

        passwordLabel.setLayoutData(createFormData(null, new FormAttachment(passwordText, 0, SWT.CENTER), null, null));

        loginText.setLayoutData(
                createFormData(new FormAttachment(50, DEFAULT_MARGIN), null, new FormAttachment(100, 0), null));

        passwordText.setLayoutData(createFormData(new FormAttachment(loginText, 0, SWT.LEFT),
                new FormAttachment(loginText, DEFAULT_MARGIN), new FormAttachment(100, 0), null));

    }

    private void createLabelAndTextGroupLayout(Shell shell)
    {
        this.labelAndTextGroup = new Group(shell, SWT.NONE);
        FormLayout groupLayout = new FormLayout();
        groupLayout.marginWidth = DEFAULT_MARGIN;
        groupLayout.marginHeight = DEFAULT_MARGIN;
        labelAndTextGroup.setLayout(groupLayout);

        FormData layoutData = new FormData();
        layoutData.left = new FormAttachment(0, DEFAULT_MARGIN);
        layoutData.right = new FormAttachment(100, -DEFAULT_MARGIN);
        layoutData.top = new FormAttachment(0, DEFAULT_MARGIN);
        layoutData.bottom = new FormAttachment(75, 0);
        labelAndTextGroup.setLayoutData(layoutData);
    }

    private Text createText(Composite parent)
    {
        Text loginText = new Text(parent, SWT.BORDER);
        return loginText;
    }

}
