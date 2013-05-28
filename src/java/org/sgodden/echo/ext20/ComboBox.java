/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sgodden.echo.ext20;

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.app.event.ListDataEvent;
import nextapp.echo.app.event.ListDataListener;
import nextapp.echo.app.list.DefaultListSelectionModel;
import nextapp.echo.app.list.ListCellRenderer;
import nextapp.echo.app.list.ListModel;
import nextapp.echo.app.list.ListSelectionModel;

/**
 * A combo box control with support for autocomplete.
 *
 * @author sgodden
 */
@SuppressWarnings( { "serial" })
public class ComboBox extends ExtComponent implements AbstractListComponent, Field {

    public static final String PROPERTY_ACTION_LISTENERS_CHANGED = "actionListeners";
    public static final String PROPERTY_ALLOW_BLANK = "allowBlank";
    public static final String PROPERTY_EDITABLE = "editable";
    public static final String PROPERTY_EMPTY_TEXT = "emptyText";
    public static final String PROPERTY_FIELD_LABEL = "fieldLabel";
    public static final String PROPERTY_FORCE_SELECTION = "forceSelection";
    public static final String INPUT_ACTION = "action";
    public static final String PROPERTY_INVALID_TEXT = "invalidText";
    public static final String PROPERTY_LIST_WIDTH = "listWidth";
    public static final String MODEL_CHANGED = "model";
    public static final String PROPERTY_SELECTION_CHANGED = "selection";
    public static final String PROPERTY_SELECTION_MODEL_CHANGED = "selectionModel";
    public static final String PROPERTY_STORE = "store";
    public static final String PROPERTY_TYPE_AHEAD = "typeAhead";
    public static final String PROPERTY_WIDTH = "width";
    public static final String PROPERTY_VALID = "isValid";
    public static final String PROPERTY_RAW_VALUE_CHANGED = "rawValue";

    public static final String PROPERTY_AUTOWIDTH = "autoWidth";
    public static final String PROPERTY_RESIZABLE = "resizable";
    public static final String PROPERTY_TEXT_SIZE = "textSize";
    public static final String PROPERTY_NOTIFY_VALUE_IMMEDIATE = "notifyValueImmediate";

    private ListSelectionModel selectionModel;
    private ListCellRenderer cellRenderer = DEFAULT_LIST_CELL_RENDERER;
	private ListCellRenderer selectionRenderer;
    private ListModel model;
    private boolean useDisplayValueAsModelValue;

    /**
     * Local handler for list data events.
     */
    private ListDataListener listDataListener = new ListDataListener() {
        public void intervalAdded(ListDataEvent e) {
            firePropertyChange(MODEL_CHANGED, null, getModel());
        }

        public void intervalRemoved(ListDataEvent e) {
            firePropertyChange(MODEL_CHANGED, null, getModel());
        }

        public void contentsChanged(ListDataEvent e) {
            firePropertyChange(MODEL_CHANGED, null, getModel());
        }
    };
    /**
     * Local handler for list selection events.
     */
    private ChangeListener listSelectionListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if (!suppressChangeNotifications) {
                firePropertyChange(PROPERTY_SELECTION_CHANGED, null, selectionModel);
            }
        }
    };
    private boolean suppressChangeNotifications = false;

    /**
     * Creates a new combo box.
     */
    public ComboBox() {
        super();
        setSelectionModel(new DefaultListSelectionModel());
        setTypeAhead(false);
        setIsValid(true);
        setAutoWidth(true);
        setResizable(false);
        set(PROPERTY_LIST_WIDTH, "auto");
        set(PROPERTY_WIDTH, "auto");
        setUseDisplayValueAsModelValue(false);
        setNotifyImmediately(true);
    }

    /**
     * Creates a new combo box.
     *
     * @param model
     *            the combo box data model.
     */
    public ComboBox(ListModel model) {
        this();
        setModel(model);
    }

    /**
     * Creates a new combo box.
     *
     * @param model
     *            the combo box data model.
     * @param fieldLabel
     *            the field label to be displayed in a form.
     */
    public ComboBox(ListModel model, String fieldLabel) {
        this(model);
    }

    /**
     * Adds an <code>ActionListener</code> to the button.
     * <code>ActionListener</code>s will be invoked when the combo box is
     * selected.
     *
     * @param l
     *            the <code>ActionListener</code> to add
     */
    public void addActionListener(ActionListener l) {
        getEventListenerList().addListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(PROPERTY_ACTION_LISTENERS_CHANGED, null, l);
    }

    /**
     * Fires an action event to all listeners.
     */
    private void fireActionEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(
                ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, null);
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }

    /**
     * Gets allow blank property
     */
    public boolean getAllowBlank(){
        return (Boolean) get(PROPERTY_ALLOW_BLANK);

    }

    /**
     * Returns the field label.
     *
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) get(PROPERTY_FIELD_LABEL);
    }

    /**
     * Returns the selected item.
     *
     * @return the selected item, or <code>null</code> if no item is selected.
     */
    public Object getSelectedItem() {
        Object ret = null;
        if (selectionModel.getMinSelectedIndex() > -1) {
            ret = getModel().get(selectionModel.getMinSelectedIndex());
        }
        return ret;
    }

    /**
     * Returns the selection model.
     *
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
     *
     * @param inputName
     *            the inputName of the event.
     * @param inputValue
     *            the associated value (irrelevant for ComboBox).
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (inputName.equals(PROPERTY_SELECTION_CHANGED)) {
            setIsValid(true);
            if (inputValue == null) {
                selectionModel.clearSelection();

                for (int x = 0; x < getModel().size(); x++) {
                    if (getModel().get(x) == null) {
                        processSelectionInput(x);
                    }
                }
            } else {
                processSelectionInput((Integer) inputValue);
            }

        } else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
        else if (PROPERTY_RAW_VALUE_CHANGED.equals(inputName)) {
            setRawValue((String) inputValue);
        }
    }

    /**
     * Removes the specified action listener.
     *
     * @param l
     *            the listener to remove.
     */
    public void removeActionListener(ActionListener l) {
        if (!hasEventListenerList()) {
            return;
        }
        getEventListenerList().removeListener(ActionListener.class, l);
        // Notification of action listener changes is provided due to
        // existence of hasActionListeners() method.
        firePropertyChange(PROPERTY_ACTION_LISTENERS_CHANGED, l, null);

    }

    /**
     * Returns the value (text) of the field.
     *
     * @return the value of the field.
     */
    public String getRawValue() {
        return (String) get(PROPERTY_RAW_VALUE_CHANGED);
    }

    /**
     * Sets the value (text) of the field.
     *
     * @param value
     *            the value of the field.
     */
    public void setRawValue(String rawValue) {
        set(PROPERTY_RAW_VALUE_CHANGED, rawValue);
    }

    /**
     * Sets whether the combo box is editable.
     *
     * @param editable
     *            whether the combo box is editable.
     */
    public void setEditable(boolean editable) {
        set(PROPERTY_EDITABLE, editable);
    }

    /**
     * Sets text to be displayed when the user has entered no text themselves.
     *
     * @param emptyText
     *            text to be displayed when the user has entered no text
     *            themselves.
     */
    public void setEmptyText(String emptyText) {
        set(PROPERTY_EMPTY_TEXT, emptyText);
    }

    /**
     * Sets allow blank property
     */
    public void setAllowBlank(boolean allowBlank){
        set(PROPERTY_ALLOW_BLANK, allowBlank);

    }

    /**
     * Sets the field label.
     *
     * @param fieldLabel
     *            the field label.
     */
    public void setFieldLabel(String fieldLabel) {
        set(PROPERTY_FIELD_LABEL, fieldLabel);
    }

    /**
     * Sets whether it is mandatory to select one of the entries.
     *
     * @param forceSelection
     *            whether a selection is mandatory.
     */
    public void setForceSelection(boolean forceSelection) {
        set(PROPERTY_FORCE_SELECTION, forceSelection);
    }

    /**
     * Sets whether the field value is valid.
     *
     * @param isValid
     *            whether the field value is valid.
     */
    public void setIsValid(boolean isValid) {
        set(PROPERTY_VALID, isValid);
    }

    /**
     * Sets the width of the dropped down list.
     *
     * @param listWidth
     *            the width.
     */
    public void setListWidth(int listWidth) {
        set(PROPERTY_LIST_WIDTH, listWidth);
    }

    public void setModel(ListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model may not be null");
        }
        this.model = model;
        // just in case they set the same model...
        model.removeListDataListener(listDataListener);
        model.addListDataListener(listDataListener);
        firePropertyChange(MODEL_CHANGED, null, model);
        selectionModel.clearSelection();
    }

    /**
     * Sets the invalid text property.
     *
     * @param invalidText
     *            the invalid text.
     */
    public void setInvalidText(String invalidText) {
        set(PROPERTY_INVALID_TEXT, invalidText);
    }

    /**
     * Sets the input text size property of the component.
     *
     * @param textSize
     *            the input text size.
     */
    public void setTextSize(String textSize) {
        set(PROPERTY_TEXT_SIZE, textSize);
    }
    
    /**
     * Selects the specified index in the selection model.
     *
     * @param selectedIndex
     *            the index to select.
     */
    protected void processSelectionInput(int selectedIndex) {
        selectionModel.clearSelection();
        if (selectedIndex >= 0)
            selectionModel.setSelectedIndex(selectedIndex, true);
    }

    /**
     * Sets the selected item, clearing the selection if a <code>null</code>
     * value is passed.
     *
     * @param selectedItem
     *            the selected item, or <code>null</code> to clear the
     *            selection.
     */
    public void setSelectedItem(Object selectedItem) {
        Object oldValue = getSelectedItem();
        if (selectedItem == null) {
            selectionModel.clearSelection();
            setRawValue(null);
        } else {
            int size = getModel().size();
            int modelIndex = -1;
            selectionModel.clearSelection();
            for (int i = 0; i < size; i++) {
                if ((getModel().get(i) == null && selectedItem == null) || (getModel().get(i) != null && getModel().get(i).equals(selectedItem))) {
                    selectionModel.setSelectedIndex(i, true);
                    modelIndex = i;
                    break;
                }
            }
            if(selectionModel.getMinSelectedIndex() != -1){
                Object value = getSelectionRenderer().getListCellRendererComponent(this, selectedItem, modelIndex);
                setRawValue(String.valueOf(value));
            }
        }
        firePropertyChange(PROPERTY_SELECTION_CHANGED, oldValue, selectedItem);
    }
    
    /**
     * Gets the cell renderer to use for the selected item. Defaults to the same
     * renderer as {@link #getCellRenderer()}
     * @return a cell renderer for the selected item
     */
    public ListCellRenderer getSelectionRenderer() {
    	return selectionRenderer == null ? cellRenderer : selectionRenderer;
    }
    
    public void setSelectionRenderer(ListCellRenderer renderer) {
    	this.selectionRenderer = renderer;
    }

    /**
     * Sets the row selection model. The selection model may not be null.
     *
     * @param newValue
     *            the new selection model
     */
    public void setSelectionModel(ListSelectionModel newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException(
                    "Selection model may not be null.");
        }
        ListSelectionModel oldValue = selectionModel;
        if (oldValue != null) {
            oldValue.removeChangeListener(listSelectionListener);
        }
        newValue.addChangeListener(listSelectionListener);
        selectionModel = newValue;
        firePropertyChange(PROPERTY_SELECTION_MODEL_CHANGED, oldValue, newValue);
    }

    /**
     * Sets whether type ahead should be enabled (defaults to false).
     *
     * @param typeAhead
     *            whether type ahead should be enabled.
     */
    public void setTypeAhead(boolean typeAhead) {
        set(PROPERTY_TYPE_AHEAD, typeAhead);
    }

    /**
     * Sets the width of the combo box.
     *
     * @param width
     *            the width.
     */
    public void setWidth(int width) {
        set(PROPERTY_WIDTH, width);
    }

    public ListCellRenderer getCellRenderer() {
        return cellRenderer;
    }

    public ListModel getModel() {
        return model;
    }

    public void setCellRenderer(ListCellRenderer newValue) {
        cellRenderer = newValue;
    }

    public final void setAutoWidth(boolean autoWidth) {
        set(PROPERTY_AUTOWIDTH, autoWidth);
        if (autoWidth) {
            set(PROPERTY_WIDTH, "auto");
        }
    }

    public final boolean getAutoWidth() {
        return (Boolean) get(PROPERTY_AUTOWIDTH);
    }

    public final void setResizable(boolean resizable) {
        set(PROPERTY_RESIZABLE, resizable);
    }

    public final boolean getResizable() {
        return (Boolean) get(PROPERTY_RESIZABLE);
    }

    public boolean getUseDisplayValueAsModelValue() {
        return useDisplayValueAsModelValue;
    }

    public void setUseDisplayValueAsModelValue(
            boolean useDisplayValueAsModelValue) {
        this.useDisplayValueAsModelValue = useDisplayValueAsModelValue;
    }
    
    /**
     * Set custom property to be set if we want a server message immediately
     * after a value has been changed on a this combo box field.
     * @param notify
     */
    public void setNotifyImmediately(final boolean notify) {
        set(PROPERTY_NOTIFY_VALUE_IMMEDIATE, notify);
    }

    /**
     * Gets notify immediately property
     */
    public boolean getNotifyImmediately() {
        return (Boolean) get(PROPERTY_NOTIFY_VALUE_IMMEDIATE);
    }
}