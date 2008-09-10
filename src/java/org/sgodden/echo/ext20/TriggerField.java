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
 * A Trigger Field.
 * 
 * @author rcharlton
 */
@SuppressWarnings({"serial"})
public class TriggerField 
        extends TextField {

    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";	
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String INPUT_ACTION = "action";
    
    private String actionCommand;

	/**
	 * Creates a new Trigger Field.
	 * @param text the text contents.
	 * @param fieldLabel the field label to be used in a form.
	 */
    public TriggerField(String text, String fieldLabel) {
        super(text, fieldLabel);
    }

    /**
     * Creates a new Trigger Field.
     * @param text the text contents.
     */
    public TriggerField(String text) {
        super(text);
    }

    /**
     * Creates a new empty Trigger Fields.
     */
    public TriggerField() {
        super();
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
     * Adds an <code>ActionListener</code> to the button.
     * <code>ActionListener</code>s will be invoked when the button
     * is clicked.
     * 
     * @param l the <code>ActionListener</code> to add
     */
    public void addActionListener(ActionListener l) {
        getEventListenerList().addListener(ActionListener.class, l);
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
    }

    /**
     * Perform a button click programatically.
     */
    public void onTriggerClick() {
        fireActionEvent();
    }

    /**
     * Handles the process input event and fires any action events.
     * @param inputName the inputName of the event.
     * @param inputValue the associated value.
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
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

}
