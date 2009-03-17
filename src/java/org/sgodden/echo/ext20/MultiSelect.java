package org.sgodden.echo.ext20;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

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
 * A multi select control with support for autocomplete. <p/> TODO - selection
 * listener.
 * 
 * @author bwoods
 */
@SuppressWarnings( { "serial" })
public class MultiSelect extends ExtComponent implements AbstractListComponent {

    public static final String ALLOW_BLANK_PROPERTY = "allowBlank";
    public static final String COMPLEX_PROPERTY = "complex";
    public static final String EDITABLE_PROPERTY = "editable";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String FORCE_SELECTION_PROPERTY = "forceSelection";
    public static final String FROM_LEGEND_PROPERTY = "fromLegend";
    public static final String MODEL_CHANGED_PROPERTY = "model";
    public static final String SELECTION_CHANGED_PROPERTY = "selection";
    public static final String SELECTION_MODEL_CHANGED_PROPERTY = "selectionModel";
    public static final String TO_LEGEND_PROPERTY = "toLegend";
    public static final String INPUT_ACTION = "action";
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String HEIGHT_PROPERTY = "height";
    public static final String WIDTH_PROPERTY = "width";

    private ListSelectionModel selectionModel;
    private int[] selectedIndices;
    private ListCellRenderer cellRenderer = DEFAULT_LIST_CELL_RENDERER;
    private ListModel model;
    
    /**
     * Local handler for list data events.
     */
    private ListDataListener listDataListener = new ListDataListener() {
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
    private ChangeListener listSelectionListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if (!suppressChangeNotifications) {
                firePropertyChange(SELECTION_CHANGED_PROPERTY, null, null);
            }
        }
    };
    private boolean suppressChangeNotifications = false;

    /**
     * Creates a new multi select.
     */
    public MultiSelect() {
        super();
        setSelectionModel(new DefaultListSelectionModel());
    }

    /**
     * Creates a new multi select.
     * 
     * @param model
     *            the multi select data model.
     */
    public MultiSelect(ListModel model) {
        this();
        setModel(model);
    }

    /**
     * Creates a new multi select.
     * 
     * @param model
     *            the multi select data model.
     * @param fieldLabel
     *            the field label to be displayed in a form.
     */
    public MultiSelect(ListModel model, String fieldLabel) {
        this(model);
    }

    /**
     * Creates a new multi select.
     * 
     * @param model
     *            the multi select data model.
     * @param fieldLabel
     *            the field label to be displayed in a form.
     * @param isComplex
     *            is the multi select a simple or complex selection.
     */
    public MultiSelect(ListModel model, String fieldLabel, boolean isComplex) {
        this(model);
        setComplex(isComplex);
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
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
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
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, l, null);

    }

    /**
     * Gets allow blank property
     */
    public boolean getAllowBlank(){
        return (Boolean) get(ALLOW_BLANK_PROPERTY);
        
    }
    
    /**
     * Returns the complex boolean.
     * 
     * @return the complex boolean.
     */
    public String getComplex() {
        return (String) get(COMPLEX_PROPERTY);
    }

    /**
     * Returns the field label.
     * 
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) get(FIELD_LABEL_PROPERTY);
    }

    /**
     * Returns the from legend used by ItemSelector.
     * 
     * @return the from legend.
     */
    public String getFromLegend() {
        return (String) get(FROM_LEGEND_PROPERTY);
    }

    /**
     * Returns the selected item.
     * 
     * @return the selected item, or <code>null</code> if no item is selected.
     */
    public Object[] getSelectedItems() {
        int minimumIndex = selectionModel.getMinSelectedIndex();

        if (minimumIndex == -1) {
            // Nothing selected: return empty array.
            return null;
        }

        int maximumIndex = selectionModel.getMaxSelectedIndex();

        if (minimumIndex == maximumIndex
                || selectionModel.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
            // Single selection mode or only one index selected: return it
            // directly.

            return new Object[] { model.get(selectionModel
                    .getMinSelectedIndex()) };
        }
        List<Object> objs = new ArrayList<Object>();

        for (int i = minimumIndex; i <= maximumIndex; ++i) {
            if (selectionModel.isSelectedIndex(i)) {
                objs.add(model.get(i));
            }
        }
        return objs.toArray();
    }

    /**
     * Returns the to legend used by ItemSelector.
     * 
     * @return the to legend.
     */
    public String getToLegend() {
        return (String) get(TO_LEGEND_PROPERTY);
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
     * Sets whether a blank value is allowed.
     * 
     * @param allowBlank
     *            whether a blank value is allowed.
     */
    public void setAllowBlank(boolean allowBlank) {
        set(ALLOW_BLANK_PROPERTY, allowBlank);
    }
    
    /**
     * Should the selector display as a simple multi select component or a
     * complex item selector.
     * 
     * @param boolean complex.
     */
    public void setComplex(boolean complex) {
        set(COMPLEX_PROPERTY, complex);
    }

    /**
     * Sets whether the multi select is editable.
     * 
     * @param editable
     *            whether the multi select is editable.
     */
    public void setEditable(boolean editable) {
        set(EDITABLE_PROPERTY, editable);
    }

    /**
     * Sets whether the multi select is enabled.
     * 
     * @param enabled
     *            whether the multi select is enabled.
     */
    public void setEnabled(boolean enabled) {
        set(EDITABLE_PROPERTY, enabled);
    }

    /**
     * Sets the field label.
     * 
     * @param fieldLabel
     *            the field label.
     */
    public void setFieldLabel(String fieldLabel) {
        set(FIELD_LABEL_PROPERTY, fieldLabel);
    }

    /**
     * Sets whether it is mandatory to select one of the entries.
     * 
     * @param forceSelection
     *            whether a selection is mandatory.
     */
    public void setForceSelection(boolean forceSelection) {
        set(FORCE_SELECTION_PROPERTY, forceSelection);
    }

    /**
     * Sets the from legend used by ItemSelector.
     * 
     * @param fromLegend
     *            the from legend.
     */
    public void setFromLegend(String fromLegend) {
        set(FROM_LEGEND_PROPERTY, fromLegend);
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
        selectionModel.clearSelection();
    }

    /**
     * Handles the process input event and fires any action events.
     * 
     * @param inputName
     *            the inputName of the event.
     * @param inputValue
     *            the associated value (irrelevant for MultiSelect).
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (SELECTION_CHANGED_PROPERTY.equals(inputName)) {
            setSelectedIndices((int[]) inputValue);
        } else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
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
     * Returns the selected indices.
     * 
     * @return the selected indices.
     */
    public int[] getSelectedIndices() {
        return selectedIndices;
    }

    /**
     * Selects only the specified row indices.
     * 
     * @param selectedIndices
     *            the indices to select
     */
    private void setSelectedIndices(int[] selectedIndices) {
        this.selectedIndices = selectedIndices;
        // Temporarily suppress the Tables selection event notifier.
        suppressChangeNotifications = true;
        selectionModel.clearSelection();
        if (selectedIndices != null) {
            for (int i = 0; i < selectedIndices.length; ++i) {
                selectionModel.setSelectedIndex(selectedIndices[i], true);
            }
        }
        // End temporary suppression.
        suppressChangeNotifications = false;
    }

    public void setSelectedItems(Object[] selectedItems) {
        if (selectedItems == null) {
            selectionModel.clearSelection();
        } else {
            int size = model.size();
            for (int i = 0; i < size; i++) {
                for (Object selectedItem : selectedItems) {
                    if (model.get(i).equals(selectedItem)) {
                        selectionModel.setSelectedIndex(i, true);
                        break;
                    }
                }
            }
        }
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
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
        firePropertyChange(SELECTION_MODEL_CHANGED_PROPERTY, oldValue, newValue);
    }

    /**
     * Sets the to legend used by ItemSelector.
     * 
     * @param toLegend
     *            the to legend.
     */
    public void setToLegend(String toLegend) {
        set(TO_LEGEND_PROPERTY, toLegend);
    }

    public ListCellRenderer getCellRenderer() {
        return cellRenderer;
    }

    public ListModel getModel() {
        return model;
    }

    public void setCellRenderer(ListCellRenderer newValue) {
        this.cellRenderer = newValue;
    }
    
    public Integer getHeight() {
        return (Integer)get(HEIGHT_PROPERTY);
    }
    
    public void setHeight(Integer height) {
        set(HEIGHT_PROPERTY, height);
    }
    
    public Integer getWidth() {
        return (Integer)get(WIDTH_PROPERTY);
    }
    
    public void setWidth(Integer width) {
        set(WIDTH_PROPERTY, width);
    }
}