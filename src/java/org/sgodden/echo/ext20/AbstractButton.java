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

import nextapp.echo.app.ImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * Abstract base class for all buttons.
 * 
 * @author sgodden
 */
@SuppressWarnings("serial")
public abstract class AbstractButton extends ExtComponent {

    public static final String TEXT_PROPERTY = "text";
    public static final String PROPERTY_DISABLED_ICON = "disabledIcon";
    public static final String PROPERTY_ENABLED_ICON = "icon";
    public static final String INPUT_ACTION = "action";
    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";
    public static final String ICON_CLASS = "iconClass";
    public static final String PRESSED_PROPERTY = "pressed";
    public static final String ENABLE_TOGGLE = "enableToggle";



	public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";

    private String actionCommand;

    /**
     * Creates a button with no text or icon.
     */
    public AbstractButton() {
        super();
        setPressed(false);
    }

    /**
     * Creates a button with text.
     * 
     * @param text
     *            the text to be displayed in the button
     */
    public AbstractButton(String text) {
        this();
        setText(text);
        setPressed(false);
    }

    /**
     * Creates a button with text and an icon.
     * 
     * @param text
     *            the text to be displayed in the button
     * @param iconUrl
     *            the icon to be displayed in the button
     */
    public AbstractButton(String text, ImageReference icon) {
        this(text);
        setIcon(icon);
        setPressed(false);
    }

    /**
     * Returns the icon displayed in the button when the button is disabled.
     * 
     * @return the icon
     */
    public ImageReference getDisabledIcon() {
        return (ImageReference) get(PROPERTY_ENABLED_ICON);
    }

    /**
     * Returns the icon displayed in the button.
     * 
     * @return the icon
     */
    public ImageReference getIcon() {
        return (ImageReference) get(PROPERTY_ENABLED_ICON);
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
     * <code>ActionListener</code>s will be invoked when the button is clicked.
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
     * Perform a button click programatically.
     */
    public void doClick() {
        fireActionEvent();
    }

    /**
     * Fires an action event to all listeners.
     */
    public void fireActionEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(
                ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, actionCommand);
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (INPUT_ACTION.equals(inputName)) {
            setPressed(!getPressed());
            fireActionEvent();
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

    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
    }

    /**
     * Sets the icon displayed in the button.
     * 
     * @param newValue
     *            the new icon
     */
    public void setDisabledIcon(ImageReference newValue) {
        set(PROPERTY_DISABLED_ICON, newValue);
    }

    /**
     * Sets the text to appear in the button.
     * 
     * @param text
     *            the text.
     */
    public void setText(String text) {
        set(TEXT_PROPERTY, text);
    }
    
    public String getText() {
        return (String)get(TEXT_PROPERTY);
    }

    /**
     * Sets the icon displayed in the button.
     * 
     * @param newValue
     *            the new icon
     */
    public void setIcon(ImageReference newValue) {
        set(PROPERTY_ENABLED_ICON, newValue);
    }

    /**
     * Sets the backgroud image of a button using a css class.
     */
    public void setIconClass(String iconClass) {
        set(ICON_CLASS, iconClass);
    }
    
    /**
     * Sets the pressed / toggled state of the button.
     */
    public void setPressed(boolean pressed) {
        set(PRESSED_PROPERTY, pressed);
    }
    
    /**
     * Gets the toggle state of the button.
     */
    public boolean getPressed(){
        Object ret = get(PRESSED_PROPERTY);
        if(ret == null){
            return false;
        }
        else{
            return (Boolean) ret;
        }
    }
    
    public void setEnableToggle(boolean enableToggle) {
		set(ENABLE_TOGGLE, enableToggle);
	}
}
