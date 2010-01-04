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
 * A radio button.
 * <p/>
 * TODO - listeners on value change
 * 
 * 
 * @author simon
 *
 */
@SuppressWarnings({"serial"})
public class RadioButton
        extends ExtComponent {

    public static final String PROPERTY_SELECTED = "selected";
    public static final String PROPERTY_FIELD_LABEL = "fieldLabel";
    public static final String PROPERTY_BOX_LABEL = "boxLabel";
    public static final String PROPERTY_NAME = "name";
    public static final String INPUT_ACTION = "action";
    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";

    private String actionCommand;
    
    /**
     * Creates a new unselected radion button.
     */
    public RadioButton() {
        super();
        setSelected(false);
    }

    /**
     * Creates a new radio button.
     * @param selected whether the button is initially selected.
     */
    public RadioButton(boolean selected) {
        this();
        setSelected(selected);
    }

    /**
     * Creates a new radion button.
     * @param selected whether the button is initially selected.
     * @param fieldLabel the field label to be displayed in forms.
     */
    public RadioButton(boolean selected, String fieldLabel) {
        setSelected(selected);
        setFieldLabel(fieldLabel);
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
     * Adds an <code>ActionListener</code> to the radio button.
     * <code>ActionListener</code>s will be invoked when the radio button
     * is clicked.
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
    
    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
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
                e = new ActionEvent(this, actionCommand);
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }
    
    /**
     * Returns the field label.
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) get(PROPERTY_FIELD_LABEL);
    }
    /**
     * Returns the boxLabel.
     * @return the boxLabel.
     */
    public String getBoxLabel() {
    	return (String) get(PROPERTY_BOX_LABEL);
    }

    /**
     * Sets whether the button is selected.
     * @param selected whether the button is selected.
     */
    public void setSelected(boolean selected) {
        set(PROPERTY_SELECTED, selected);
    }

    /**
     * Returns whether the button is selected.
     * @return whether the button is selected.
     */
    public boolean getSelected() {
        return (Boolean) get(PROPERTY_SELECTED);
    }

    /**
     * Sets the field label to be used in forms.
     * @param fieldLabel the field label to be used in forms.
     */
    public void setFieldLabel(String fieldLabel) {
        set(PROPERTY_FIELD_LABEL, fieldLabel);
    }
    
    /**
     * Sets the field label to be used in forms.
     * @param boxLabel the field label to be used in forms.
     */
    public void setBoxLabel(String boxLabel) {
    	set(PROPERTY_BOX_LABEL, boxLabel);
    }
    
    /**
     * Sets the name of the field being maintained, which effectively sets
     * the group that this button belongs to.  All radion buttons with the same
     * name are in the same button group.
     * @param name the name.
     */
    public void setName(String name) {
        set(PROPERTY_NAME, name);
    }
    
    /**
     * Returns the name of the button group.
     * @return the name of the button group.
     */
    public String getName() {
        return (String) get(PROPERTY_NAME);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (PROPERTY_SELECTED.equals(inputName)) {
            setSelected((Boolean) inputValue);
        }
        if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
    }
}
