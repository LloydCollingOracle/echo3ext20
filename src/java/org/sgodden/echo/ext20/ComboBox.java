/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sgodden.echo.ext20;

import java.util.EventListener;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A combo box control with support for autocomplete.
 * <p/>
 * Code example:
 * <pre class="code">
Object[][] data = new Object[2][2];
data[0] = new String[]{"admin", "Administrator"}; // id, description
data[1] = new String[]{"user", "User"};

SimpleStore store = new SimpleStore(
data,
0,
new String[]{"id", "description"});

ComboBox combo = new ComboBox(store);
combo.setFieldLabel("Role");
combo.setDisplayField("description");
combo.setValueField("id");
combo.setTypeAhead(true);

add(combo);
 * </pre>
 * <p/>
 * TODO - selection listener.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class ComboBox
        extends ExtComponent {

    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String EDITABLE_PROPERTY = "editable";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String FORCE_SELECTION_PROPERTY = "forceSelection";
    public static final String INPUT_ACTION = "action";
    public static final String MODEL_CHANGED_PROPERTY="model";
    public static final String SELECTION_CHANGED_PROPERTY = "selection";
    public static final String SELECTION_MODEL_CHANGED_PROPERTY = "selectionModel";
    public static final String STORE_PROPERTY = "store";
    public static final String TYPE_AHEAD_PROPERTY = "typeAhead";


    private ListModel model;
    private ListSelectionModel selectionModel;
    /**
     * Local handler for list data events.
     */
    private ListDataListener listDataListener = new ListDataListener(){
        public void intervalAdded(ListDataEvent e) {
            firePropertyChange(MODEL_CHANGED_PROPERTY, null, model);
        }
        public void intervalRemoved(ListDataEvent e) {
            firePropertyChange(MODEL_CHANGED_PROPERTY, null, model);
        }
        public void contentsChanged(ListDataEvent e) {
            firePropertyChange(MODEL_CHANGED_PROPERTY, null, model);
        }
    };
    /**
     * Local handler for list selection events.
     */
    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            if (!suppressChangeNotifications) {
                firePropertyChange(SELECTION_CHANGED_PROPERTY, null, null);
            }
        }
    };
    private boolean suppressChangeNotifications = false;


    /**
     * Creates a new combo box.
     * @param model the combo box data model.
     */
    public ComboBox(ListModel model) {
        super();
        setModel(model);
        setSelectionModel(new DefaultListSelectionModel());
    }

    /**
     * Creates a new combo box.
     * @param model the combo box data model.
     * @param fieldLabel the field label to be displayed in a form.
     */
    public ComboBox(ListModel model, String fieldLabel) {
        this(model);
    }

    /**
     * Adds an <code>ActionListener</code> to the button.
     * <code>ActionListener</code>s will be invoked when the combo box is selected.
     *
     * @param l the <code>ActionListener</code> to add
     */
    public void addActionListener(ActionListener l) {
        getEventListenerList().addListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
    }

    /**
     * Fires an action event to all listeners.
     */
    private void fireActionEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, null);
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }

    /**
     * Returns the field label.
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) getComponentProperty(FIELD_LABEL_PROPERTY);
    }

    /**
     * Returns the selected item.
     * @return the selected item, or <code>null</code> if no item is selected.
     */
    public Object getSelectedItem() {
        Object ret = null;
        if (selectionModel.getMinSelectionIndex() > -1) {
            ret = model.getElementAt(selectionModel.getMinSelectionIndex());
        }
        return ret;
    }


    /**
     * Returns the list model.
     * @return the list model.
     */
    public ListModel getModel() {
        return model;
    }

    /**
     * Returns the selection model.
     * @return the selection model.
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Returns whether any <code>ActionListener</code>s are registered.
     *
     * @return true if any action listeners are registered
     */
    public boolean hasActionListeners() {
        return getEventListenerList().getListenerCount(ActionListener.class) != 0;
    }
    /**
     * Handles the process input event and fires any action events.
     * @param inputName the inputName of the event.
     * @param inputValue the associated value (irrelevant for ComboBox).
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (inputName.equals(SELECTION_CHANGED_PROPERTY)) {
            processSelectionInput((Integer) inputValue);
        }
        else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
    }

    /**
     * Removes the specified action listener.
     * @param l the listener to remove.
     */
    public void removeActionListener(ActionListener l) {
        if (!hasEventListenerList()) {
            return;
        }
        getEventListenerList().removeListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, l, null);

    }

    /**
     * Sets whether the combo box is editable.
     * @param editable whether the combo box is editable.
     */
    public void setEditable(boolean editable) {
        setComponentProperty(EDITABLE_PROPERTY, editable);
    }

    /**
     * Sets the field label.
     * @param fieldLabel the field label.
     */
    public void setFieldLabel(String fieldLabel) {
        setComponentProperty(FIELD_LABEL_PROPERTY, fieldLabel);
    }

    /**
     * Sets whether it is mandatory to select one of the entries.
     * @param forceSelection whether a selection is mandatory.
     */
    public void setForceSelection(boolean forceSelection) {
        setComponentProperty(FORCE_SELECTION_PROPERTY, forceSelection);
    }

    public void setModel(ListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model may not be null");
        }
        this.model = model;
        // just in case they set the same model...
        model.removeListDataListener(listDataListener);
        model.addListDataListener(listDataListener);
        firePropertyChange(MODEL_CHANGED_PROPERTY, null, model);
    }

    /**
     * Selects the specified index in the selection model.
     *
     * @param selectedIndex the index to select.
     */
    private void processSelectionInput(int selectedIndex) {
        // Temporarily suppress the Tables selection event notifier.
        suppressChangeNotifications = true;
        selectionModel.clearSelection();
        selectionModel.addSelectionInterval(selectedIndex,
                selectedIndex);
        // End temporary suppression.
        suppressChangeNotifications = false;
    }

    /**
     * Sets the selected item, clearing the selection if a <code>null</code>
     * value is passed.
     * @param selectedItem the selected item, or <code>null</code> to clear
     * the selection.
     */
    public void setSelectedItem(Object selectedItem) {
        if (selectedItem == null) {
            selectionModel.clearSelection();
        }
        else {
            int size = model.getSize();
            for (int i = 0; i < size; i++) {
                if (model.getElementAt(i).equals(selectedItem)) {
                    selectionModel.setSelectionInterval(i, i);
                    break;
                }
            }
        }
    }

    /**
     * Sets the row selection model.
     * The selection model may not be null.
     *
     * @param newValue the new selection model
     */
    public void setSelectionModel(ListSelectionModel newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Selection model may not be null.");
        }
        ListSelectionModel oldValue = selectionModel;
        if (oldValue != null) {
            oldValue.removeListSelectionListener(listSelectionListener);
        }
        newValue.addListSelectionListener(listSelectionListener);
        selectionModel = newValue;
        firePropertyChange(SELECTION_MODEL_CHANGED_PROPERTY, oldValue, newValue);
    }

    /**
     * Sets whether type ahead should be enabled (defaults to true).
     * @param typeAhead whether type ahead should be enabled.
     */
    public void setTypeAhead(boolean typeAhead) {
        setComponentProperty(TYPE_AHEAD_PROPERTY, typeAhead);
    }

}