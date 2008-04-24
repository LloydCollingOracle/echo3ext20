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
package org.sgodden.echo.ext20;

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * A text field.
 * <p/>
 * TODO - listeners on value change
 * 
 * 
 * @author simon
 *
 */
public class CheckboxField
        extends ExtComponent {

    public static final String SELECTED_CHANGED_PROPERTY = "selected";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";

    public static final String INPUT_ACTION = "action";
    
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";


    public CheckboxField() {
        super();
        setSelected(false);
    }

    public CheckboxField(boolean selected) {
        this();
        setSelected(selected);
    }

    public CheckboxField(boolean selected, String fieldLabel) {
        setSelected(selected);
        setFieldLabel(fieldLabel);
    }

    public void setSelected(boolean selected) {
        setProperty(SELECTED_CHANGED_PROPERTY, selected);
    }

    public boolean getSelected() {
        return (Boolean) getProperty(SELECTED_CHANGED_PROPERTY);
    }

    public void setFieldLabel(String fieldLabel) {
        setProperty(FIELD_LABEL_PROPERTY, fieldLabel);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
    	super.processInput(inputName, inputValue);
        if (SELECTED_CHANGED_PROPERTY.equals(inputName)) {
            setSelected((Boolean) inputValue);
        }
        if (INPUT_ACTION.equals(inputName)) {
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
     * Adds an <code>ActionListener</code> to the check box.
     * <code>ActionListener</code>s will be invoked when component
     * is checked or unchecked.
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


}
