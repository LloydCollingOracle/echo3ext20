package org.sgodden.echo.ext20.testapp.groovy

import java.beans.PropertyChangeListener
import java.util.Calendar
import java.util.List

import nextapp.echo.app.Label
import nextapp.echo.app.list.DefaultListModel;
import nextapp.echo.app.list.ListModel;
import nextapp.echo.app.ApplicationInstance
import nextapp.echo.app.event.ActionEvent
import nextapp.echo.app.event.ActionListener

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sgodden.echo.ext20.ActionListenable
import org.sgodden.echo.ext20.Button
import org.sgodden.echo.ext20.CaseRestriction
import org.sgodden.echo.ext20.ComboBox
import org.sgodden.echo.ext20.CheckboxField
import org.sgodden.echo.ext20.DateField
import org.sgodden.echo.ext20.FieldSet
import org.sgodden.echo.ext20.FormField
import org.sgodden.echo.ext20.FormGrid
import org.sgodden.echo.ext20.MultiSelect
import org.sgodden.echo.ext20.PasswordField
import org.sgodden.echo.ext20.Panel
import org.sgodden.echo.ext20.RadioButton
import org.sgodden.echo.ext20.TextArea
import org.sgodden.echo.ext20.TextField
import org.sgodden.echo.ext20.TimeField
import org.sgodden.echo.ext20.TriggerField
import org.sgodden.echo.ext20.Window
import org.sgodden.ui.models.DefaultBackingObjectListModel

/**
 * A form for editing a user.
 * @author sgodden
 */
class UserEditPanel extends Panel implements ActionListenable {

    private static final transient Log log = LogFactory.getLog(UserEditPanel.class);

    def FormGrid1
    def codeField
    def nameField
    def postcodeField
    def invalidField
    def passwordField
    def dateField
    def timeField
    def textAreaField
    
    def fieldSet
    def enabledField
    def userRoleButton
    def adminRoleButton
    def roleCombo
    def roleMultiSelect

    def cancelButton
    def saveButton
    
    def FormGrid2

    public UserEditPanel() {
        super();
        renderId = "userEditPanel"
        title = "Edit user"

        bodyPadding = "5px"
        border = true

        initComponents()
        
        components = [
            FormGrid1,
            fieldSet,
            FormGrid2
        ]

        buttons = [
            cancelButton,
            saveButton,
            makeChangeRoleButton()
        ]
        
        ApplicationInstance.getActive().setFocusedComponent(codeField)
    }

    public void setData(Object[] data) {
        codeField.value = data[0];
        nameField.value = data[1];
    }

    /**
     * Adds a listener to be notified when the cancel or save action
     * is taken.
     * 
     * @param listener
     */
    public void addActionListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
        saveButton.addActionListener(listener);
    }

    private void initComponents(Object[] data) {
        FormGrid1 = new FormGrid(2)
        
        codeField = new TextField()
        nameField = new TextField(
            minLength : 5
        )
        postcodeField = new TextField(
            regExp: "^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2})\$",
            regexpFailureText: "Invalid postal code",
            propertyChange: { println "A property changed on the postcode field"  },
            caseRestriction: CaseRestriction.LOWER
        )
        invalidField = new TextField(
            value: "Is this field invalid for business reasons?"
        )
        passwordField = new PasswordField()
        Calendar cal = Calendar.getInstance(
            ApplicationInstance.getActive().getLocale())
        dateField = new DateField(
            blankAllowed: false,
            calendar: cal
        )
        timeField = new TimeField(
            blankAllowed: false,
            calendar: cal
        )
        textAreaField = new TextArea(
            fieldLabel: "Notes",
            value: "Some text in a text area"
        )
        
        FormGrid1.formComponents = [
            [
                field: codeField,
                label: "Code"
            ],
            [
                field: nameField,
                label: "Name",
            ],
            [
                field: postcodeField,
                label: "Postal code"
            ],
            [
                field: invalidField,
                label: "Invalid field"
            ],
            [
                field: passwordField,
                label: "Password"
            ],
            [
                field: dateField,
                label: "Date"
            ],
            [
                field: timeField,
                label: "Time"
            ],
            [
                field: textAreaField,
                label: "Notes",
            ]
        ]
        
        FormGrid fieldSetForm = new FormGrid()
        
        fieldSet = new FieldSet(
            title: "A field set",
            checkboxToggle: true,
            components: [fieldSetForm]
        )
        
        enabledField = new CheckboxField(
            selected: true,
            actionPerformed: { println "Check box selected: ${enabledField.selected}"  }
        )
        fieldSetForm.add(enabledField, "Enabled")
        
        userRoleButton = new RadioButton(
            name: "role",
            selected: true
        )
        fieldSetForm.add(userRoleButton, "User")
        
        adminRoleButton = new RadioButton(
            name: "role",
            selected: false
        )
        fieldSetForm.add(adminRoleButton, "Administrator")
        
        roleCombo = makeRoleCombo(makeRoleModel())
        fieldSetForm.add(roleCombo, "Role combo");

        dateField = new DateField()
        def triggerField = new TriggerField(
        		actionPerformed: {
        		log.debug("TRIGGER FIELD PRESSED")
                def triggerWindow = new Window();
                triggerWindow.setHeight(100);
                triggerWindow.setWidth(260);
                triggerWindow.setTitle("Trigger Window");
                
                this.add(triggerWindow);
            }
        )
        
        def delayedActionField = new TextField(
            notifyImmediately : true,
            validationDelay : 500,       
        )
        
        delayedActionField.addPropertyChangeListener(
        		"value", 
        		{evt -> println evt.newValue} as PropertyChangeListener) 
        
        roleMultiSelect = makeRoleMultiSelect(makeRoleModel())
        fieldSetForm.add(roleMultiSelect, "Role multi select");
        
        FormGrid2 = new FormGrid(
            formComponents: [
                [
                    field: new DateField(),
                    label: "Date field"
                ],
                [
                    field: triggerField,
                    label: "Trigger Field"
                ],
                [
                    field: delayedActionField,
                    label: "Delayed Action Field"
                ]
            ]
        )

        cancelButton = new Button(
            actionCommand: "CANCEL",
            text: "Cancel"
        )
        saveButton = new Button(
            actionCommand: "SAVE",
            text: "Save",
            actionPerformed: { evt -> doSave(evt) }
        )
    }

    private void doSave(ActionEvent evt) {
        log.info(roleCombo.selectedItem);
        if (invalidField.value.length() > 10) {
            invalidField.isValid = false;
            invalidField.invalidText = "Value cannot exceed 10 characters";
        } 
        else if (!(dateField.isClientInputValid())) {
            Window window = new Window(
                title: "Correct form errors",
                modal: true,
                height: 100,
                width: 200,
                padding: "5px",
                html: "Please correct the form errors"
            )
            add(window);
        } else {
            // print out field values to make sure they have transferred correctly
            log.info("Save button pressed:");
            log.info("  codeField: " + codeField.value);
            log.info("  nameField: " + nameField.value);
            log.info("  calendar: " + dateField.calendar);
            log.info("  enabled: " + enabledField.selected);
            
            log.info("  multi select max selected is now: " + roleMultiSelect.selectionModel.getMaxSelectedIndex());
            
        }
    }

    /**
     * Returns a button which increments the selected item in the
     * role combo.
     * @return the button.
     */
    private Button makeChangeRoleButton() {
        Button ret = new Button(
            text: "Change selected role (server)",
            actionPerformed: {
                /*
                 * Increase the selection index on the role combo, wrapping
                 * it if it goes too far.
                 */
                def si = roleCombo.selectionModel.minSelectedIndex;
                si++;
                if (si == roleCombo.model.size) {
                    roleCombo.selectionModel.clearSelection();
                } else {
                    roleCombo.selectionModel.setSelectedIndex(si, true);
                }
            }
        )
        return ret;
    }

    /**
     * Returns a combox box for selection of roles, based
     * on the passed store.
     * @param store the store.
     * @return the combo box.
     */
    private ComboBox makeRoleCombo(ListModel store) {
        roleCombo = new ComboBox(
            fieldLabel: "Role",
            model: store,
            typeAhead: true,
            actionPerformed: {
                log.info("""Action listener fired 
                    : selected item is ${roleCombo.selectedItem}
                    ; selected backing object is ${roleCombo.selectedBackingObject}""")
            }
        )
        return roleCombo;
    }
    
    /**
     * Returns a multi select box for selection of roles, based
     * on the passed store.
     * @param store the store.
     * @return the combo box.
     */
    private MultiSelect makeRoleMultiSelect(ListModel store) {
        roleMultiSelect = new MultiSelect(
            fieldLabel: "Role",
            model: store,
            complex: true,
        )
        Integer[] arr = new Integer[3];
        arr[0] = 0;
        arr[1] = 2;
        arr[2] = 4;

        roleMultiSelect.fromLegend = "Available";
        roleMultiSelect.toLegend = "Selected";
        roleMultiSelect.selectedIndices = arr;

        return roleMultiSelect;
    }

    /**
     * Creates a dummy store for role data.
     * @return the store.
     */
    private ListModel makeRoleModel() {
        DefaultBackingObjectListModel ret = new DefaultBackingObjectListModel();
        ret.add("Administrator", new Integer(1));
        ret.add("User", new Integer(2));
        ret.add("Technical Manager", new Integer(3));
        ret.add("Supplier", new Integer(4));
        ret.add("Site", new Integer(5));
        return ret;
    }
}
