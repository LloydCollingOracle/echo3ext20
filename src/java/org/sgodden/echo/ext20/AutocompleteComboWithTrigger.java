package org.sgodden.echo.ext20;

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * An Autocompletion ComboBox with a separate button for triggering an action
 * 
 * @author Lloyd Colling
 */
public class AutocompleteComboWithTrigger extends AutocompleteCombo {

    private static final long serialVersionUID = 1L;
    
    public static final String PROPERTY_TRIGGER_ACTION = "triggerAction";

    public static final String PROPERTY_TRIGGER_LISTENERS_CHANGED = "triggerActionListeners";
    
    public AutocompleteComboWithTrigger() {
        super();
        setHideTrigger(false);
    }
    
    public boolean hasTriggerActionListeners() {
        return getEventListenerList().getListenerCount(TriggerActionListener.class) > 0;
    }
    
    public void addTriggerActionListener(TriggerActionListener listener) {
        getEventListenerList().addListener(TriggerActionListener.class, listener);
        firePropertyChange(PROPERTY_TRIGGER_LISTENERS_CHANGED, null, getEventListenerList().getListeners(TriggerActionListener.class));
    }
    
    public void removeTriggerActionListener(TriggerActionListener listener) {
        getEventListenerList().removeListener(TriggerActionListener.class, listener);
        firePropertyChange(PROPERTY_TRIGGER_LISTENERS_CHANGED, null, getEventListenerList().getListeners(TriggerActionListener.class));
    }

    protected void fireTriggerAction() {
        ActionEvent e = null;
        for (EventListener el : getEventListenerList().getListeners(TriggerActionListener.class)) {
            if (e == null) {
                e = new ActionEvent(this, "TRIGGER");
            }
            ((TriggerActionListener)el).actionPerformed(e);
        }
    }
    
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (PROPERTY_TRIGGER_ACTION.equals(inputName)) {
            fireTriggerAction();
        }
    }
    
    /**
     * Marker interface to differentiate between trigger listeners and value changed action listeners
     * 
     * @author Lloyd Colling
     */
    public static interface TriggerActionListener extends ActionListener {}
}
