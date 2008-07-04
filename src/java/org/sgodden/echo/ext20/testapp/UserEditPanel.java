/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
package org.sgodden.echo.ext20.testapp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.Alignment;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.CheckboxField;
import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.DateField;
import org.sgodden.echo.ext20.FieldSet;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.PasswordField;
import org.sgodden.echo.ext20.RadioButton;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.TimeField;
import org.sgodden.echo.ext20.Window;
import org.sgodden.echo.ext20.data.DefaultSimpleStore;
import org.sgodden.echo.ext20.data.SimpleStore;
import org.sgodden.echo.ext20.layout.FormLayout;

/**
 * A panel which allows edit of a single user.
 * <p>
 * FIXME - use echo list models instead of simple store.
 * </p>
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class UserEditPanel
        extends Panel {

    private static final transient Log log = LogFactory.getLog(UserEditPanel.class);
    private Button cancelButton;
    private Button saveButton;
    private List<ActionListener> saveListeners = new ArrayList<ActionListener>();

    public UserEditPanel(Object[] data) {
        super(new FormLayout());
        setRenderId("userFormPanel");
        setTitle("Edit user");

        setBodyPadding("5px");
        setBorder(true);

        final TextField codeField = new TextField((String) data[0], "Code");
        codeField.setBlankAllowed(false);
        add(codeField);
//        codeField.setFocusTraversalIndex(0);
//        codeField.setFocusTraversalParticipant(true);
//        
//        ApplicationInstance.getActive().setFocusedComponent(codeField);

        final TextField nameField = new TextField((String) data[1], "Name");
        nameField.setBlankAllowed(false);
        add(nameField);
//        nameField.setFocusTraversalIndex(1);
//        nameField.setFocusTraversalParticipant(true);

        TextField postcodeField = new TextField();
        postcodeField.setFieldLabel("Post code");
        postcodeField.setRegExp("^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2})$");
        postcodeField.setRegexpFailureText("Invalid Postcode");
        add(postcodeField);

        postcodeField.addPropertyChangeListener(
            TextField.VALUE_CHANGED_PROPERTY, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                log.info("The value in the postcode field changed");
            }
        });

        final TextField invalidField = new TextField();
        invalidField.setFieldLabel("Invalid Field");
        invalidField.setValue("Is this field is invalid for busines reasons?");
        add(invalidField);

        PasswordField passwordField = new PasswordField();
        passwordField.setFieldLabel("Password Field");
        add(passwordField);

        Calendar cal = Calendar.getInstance(
                ApplicationInstance.getActive().getLocale());

        final DateField dateField = new DateField(cal, "Date");
        dateField.setBlankAllowed(false);
        add(dateField);

        final TimeField timeField = new TimeField(cal, "Time");
        timeField.setBlankAllowed(false);
        add(timeField);

        TextArea textArea = new TextArea("Some text in a text area", "Notes");
        add(textArea);

        // and let's put these last few in a field set
        FieldSet fieldSet = new FieldSet();
        fieldSet.setTitle("A field set");
        fieldSet.setCheckboxToggle(true);
        add(fieldSet);

        final CheckboxField enabledField = new CheckboxField(true, "Enabled");
        fieldSet.add(enabledField);

        enabledField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String text = "You " +
                        (enabledField.getSelected() ? "selected" : "unselected") +
                        " the check box";
                Window window = new Window(text);
                window.setPadding("5px");
                window.setHeight(100);
                window.setWidth(200);
                window.setHtml(text);
                window.setModal(true);
                add(window);
            }
        });

        final RadioButton userRoleButton = new RadioButton(true, "User");
        userRoleButton.setName("role");
        fieldSet.add(userRoleButton);

        final RadioButton adminRoleButton = new RadioButton(false, "Admin");
        adminRoleButton.setName("role");
        fieldSet.add(adminRoleButton);

        SimpleStore roleStore = makeRoleStore();
        final ComboBox roleCombo = makeRoleCombo(roleStore);
        fieldSet.add(roleCombo);
        
                //        HtmlEditor editor = new HtmlEditor();
                //        userEditPanel.add(editor);

        cancelButton = new Button("Cancel");
        cancelButton.setRenderId("cancelButton");
        addButton(cancelButton);

        addKeyPressListener("esc", new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                cancelButton.doClick();
            }
        });

        saveButton = new Button("Save");
        addButton(saveButton);
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                log.info(roleCombo.getValue());
                if (invalidField.getValue().length() > 10) {
                    invalidField.setIsValid(false);
                    invalidField.setInvalidText("Value cannot exceed 10 characters");

                } else if (!(dateField.isClientInputValid())) {
                    Window window = new Window("Correct form errors");
                    window.setModal(true);
                    window.setHtml("Please correct the form errors");
                    window.setHeight(100);
                    window.setWidth(200);
                    window.setPadding("5px");
                    add(window);
                } else {
                    // print out field values to make sure they have transferred correctly
                    log.info("Save button pressed:");
                    log.info("  codeField: " + codeField.getValue());
                    log.info("  nameField: " + nameField.getValue());
                    log.info("  calendar: " + dateField.getCalendar());
                    log.info("  enabled: " + enabledField.getSelected());

                    for (ActionListener listener : saveListeners) {
                        listener.actionPerformed(e);
                    }
                }
            }
        });

        addButton(createAlignTestButton());

        ApplicationInstance.getActive().setFocusedComponent(codeField);
    }

    /**
     * Creates a button which moves the save button above the cancel button
     * using ext alignment.
     * @return the button.
     */
    private Button createAlignTestButton() {
        Button ret = new Button("Alignment test");

        ret.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                saveButton.alignTo(cancelButton, Alignment.BOTTOM, Alignment.TOP, 0, -10);
            }
        });

        return ret;
    }

    /**
     * Adds a listener to be notified when the cancel action
     * is taken.
     * 
     * @param listener
     */
    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    /**
     * Adds a listener to be notified when the save action is taken.
     * @param listener
     */
    public void addSaveListener(ActionListener listener) {
        saveListeners.add(listener);
    }

    /**
     * Returns a combox box for selection of roles, based
     * on the passed store.
     * @param store the store.
     * @return the combo box.
     */
    private ComboBox makeRoleCombo(SimpleStore store) {
        ComboBox ret = new ComboBox(store);
        ret.setFieldLabel("Role");
        ret.setDisplayField("description");
        ret.setValueField("id");
        ret.setTypeAhead(true);

        ret.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Window window = new Window("Combo Selected");
                window.setModal(true);
                window.setHtml("action listener fired");
                window.setHeight(100);
                window.setWidth(200);
                window.setPadding("5px");
                add(window);
            }
        }) ;

        return ret;
    }

    /**
     * Creates a dummy store for role data.
     * @return the store.
     */
    private SimpleStore makeRoleStore() {
        Object[][] data = new Object[2][2];
        data[0] = new String[]{"admin", "Administrator"};
        data[1] = new String[]{"user", "User"};

        DefaultSimpleStore store = new DefaultSimpleStore(
                data,
                0,
                new String[]{"id", "description"});

        return store;
    }
}
