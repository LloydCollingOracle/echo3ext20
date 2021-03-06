package org.sgodden.echo.ext20;

import java.util.Arrays;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;
import nextapp.echo.app.list.DefaultListSelectionModel;
import nextapp.echo.app.list.ListCellRenderer;
import nextapp.echo.app.list.ListModel;
import nextapp.echo.app.list.ListSelectionModel;

public class MultiSelectComboBox extends ExtComponent implements AbstractListComponent, Field {
	public static final String PROPERTY_RAW_VALUE_CHANGED = "rawValue";
	public static final String PROPERTY_VALUE_CHANGED = "selectedValue";
	public static final String PROPERTY_MODEL_CHANGED = "model";
	public static final String INPUT_ACTION = "action";
	public static final String PROPERTY_ACTION_LISTENERS_CHANGED = "actionListeners";
	public static final String PROPERTY_MULTI_SELECT = "multiSelect";
	public static final String PROPERTY_EDITABLE = "editable";
	public static final String PROPERTY_SEPARATOR = "separator";	
    public static final String PROPERTY_ALLOW_BLANK = "allowBlank";
    public static final String PROPERTY_BLANK_TEXT = "blankText";
	public static final String PROPERTY_INVALID_TEXT = "invalidText";
	public static final String PROPERTY_VALID = "isValid";

	private ListModel model;
	private ListSelectionModel selectionModel;
	private ListCellRenderer cellRenderer = DEFAULT_LIST_CELL_RENDERER;
	private boolean useDisplayValueAsModelValue;
	
	public MultiSelectComboBox(DefaultListModel model) {
		super();
		setSelectionModel(new DefaultListSelectionModel());
		setModel(model);
		setUseDisplayValueAsModelValue(false);
	}
    /**
     * Sets the row selection model. The selection model may not be null.
     * 
     * @param newValue
     *            the new selection model
     */
    public void setSelectionModel(ListSelectionModel newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException( "Selection model may not be null.");
        }
        selectionModel = newValue;
        selectionModel.setSelectionMode( DefaultListSelectionModel.MULTIPLE_SELECTION);
    }

    public void setModel(ListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model may not be null");
        }
        this.model = model;
        firePropertyChange(PROPERTY_MODEL_CHANGED, null, model);
        selectionModel.clearSelection();
    }

    public ListModel getModel() {
        return model;
    }

    /**
     * Returns the selection model.
     * 
     * @return the selection model.
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }
    
	public String getSelectedValue() {
		return (String) super.get( PROPERTY_VALUE_CHANGED);
	}
	
	/** 
	 * @param value The selected items' index, seperated by comma
	 */
	public void setSelectedValue( String value) {
		set( PROPERTY_VALUE_CHANGED, value);
		selectionModel.clearSelection();
		if ( value == null || "".equals( value)) return;
		String[] splitedValue = value.split( getSeparator());
		for (String v : splitedValue) {
			selectionModel.setSelectedIndex( Integer.parseInt( v), true);
		}
	}
	public ListCellRenderer getCellRenderer() {
		return cellRenderer;
	}
	public void setCellRenderer(ListCellRenderer newValue) {
		cellRenderer = newValue;
	}
	
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (PROPERTY_VALUE_CHANGED.equals(inputName)) {
            setSelectedValue((String) inputValue);
        } else if (PROPERTY_RAW_VALUE_CHANGED.equals(inputName)) {
        	setRawValue((String) inputValue);
        }  else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
    }
	public String getValue() {
		if ( getMultiSelect()) {
			int min = selectionModel.getMinSelectedIndex();
			int max = selectionModel.getMaxSelectedIndex();
			if ( max < 0) {
				return null;
			}
			if ( min == max) {
				return ""+ model.get( min);
			}
			String result = "";
			for ( int i=min; i<=max; i++) {
				if ( selectionModel.isSelectedIndex( i)) {
		    		result += model.get(i);
		    		if ( i<max) result += getSeparator();
				}
			}
		    return result;
		} else {
			return getRawValue();
		}
	}
	
	public String getRawValue() {
		System.out.println( "Getting Raw value");
		return (String) get(PROPERTY_RAW_VALUE_CHANGED);
	}
	
	public void setRawValue( String rawValue) {
		System.out.println( "Raw value is " + rawValue);
		set( PROPERTY_RAW_VALUE_CHANGED, rawValue);
	}
	
	/**
	 * @param value is a String contains the display value, seperated by comma
	 */
	public void setValue(String value) {
		if ( getMultiSelect()) {
            set( PROPERTY_VALUE_CHANGED, value);
			selectionModel.clearSelection();
			if ( value == null || "".equals( value)) return;
			
			String[] splited = value.split( getSeparator());
			Set<String> values = new HashSet<String>( Arrays.asList( splited));
			for ( int i=0; i<model.size(); i++) {
				if ( values.contains( model.get( i))) {
					selectionModel.setSelectedIndex( i, true);
				}
			}
		} else setRawValue( value);
	}
	
	public void setMultiSelect( boolean multiSelect) {
		set( PROPERTY_MULTI_SELECT, multiSelect);
	}
	
	public boolean getMultiSelect() {
		Object multiSelect = get( PROPERTY_MULTI_SELECT);
		//default is true
		return multiSelect == null ? true : (Boolean)multiSelect;
	}
	
	public void setEditable(boolean editable) {
		set( PROPERTY_EDITABLE, editable);
	}
	
	public boolean getEditable() {
		Object editable = get( PROPERTY_EDITABLE);
		//default is true
		return editable == null ? true : (Boolean)editable;
	}
	
	public void setSeparator( String separator) {
		set( PROPERTY_SEPARATOR, separator);
	}
	
	public String getSeparator() {
		Object separator = get(PROPERTY_SEPARATOR);
		return separator == null ? "," : (String) separator;
	}

	/**
     * Gets allow blank property
     */
    public boolean getAllowBlank(){
        return (Boolean) get(PROPERTY_ALLOW_BLANK);
        
    }
    
    /**
     * Sets whether a blank value is allowed.
     * 
     * @param allowBlank
     *            whether a blank value is allowed.
     */
    public void setAllowBlank(boolean allowBlank) {
        set(PROPERTY_ALLOW_BLANK, allowBlank);
    }

    /**
     * Sets the text to be displayed when the isBlankAllowed test fails.
     * 
     * @param value
     *            the value of the field.
     */
    public void setBlankText(String blankText) {
        set(PROPERTY_BLANK_TEXT, blankText);
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
	 * Sets whether the field value is valid.
	 * 
	 * @param isValid
	 *            whether the field value is valid.
	 */
	public void setIsValid(boolean isValid) {
		set(PROPERTY_VALID, isValid);
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
     * Returns whether any <code>ActionListener</code>s are registered.
     * 
     * @return true if any action listeners are registered
     */
    public boolean hasActionListeners() {
        return getEventListenerList().getListenerCount(ActionListener.class) != 0;
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

    public boolean getUseDisplayValueAsModelValue() {
        return useDisplayValueAsModelValue;
    }

    public void setUseDisplayValueAsModelValue(
            boolean useDisplayValueAsModelValue) {
        this.useDisplayValueAsModelValue = useDisplayValueAsModelValue;
    }
}
