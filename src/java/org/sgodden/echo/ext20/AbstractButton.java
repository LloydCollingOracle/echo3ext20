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
 * Abstract base class for all buttons.
 * 
 * @author sgodden
 */
public abstract class AbstractButton 
        extends ExtComponent {
    
        
    public static final String TEXT_PROPERTY = "text";
    public static final String ICON_URL_PROPERTY = "iconUrl";
    public static final String INPUT_ACTION = "action";
    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";
    public static final String ICON_CLASS_PROPERTY="iconClass";
    
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";

    /**
     * Creates a button with no text or icon.
     */
    public AbstractButton() {
        super();
    }

    /**
     * Creates a button with text.
     *
     * @param text the text to be displayed in the button
     */
    public AbstractButton(String text) {
        this();
        setText(text);
    }

    /**
     * Creates a button with text and an icon.
     *
     * @param text the text to be displayed in the button
     * @param iconUrl the icon to be displayed in the button
     */
    public AbstractButton(String text, String iconUrl) {
        this(text);
        setIconUrl(iconUrl);
    }
    
    /**
     * Sets the text to appear in the button.
     * @param text the text.
     */
    public void setText(String text) {
        setProperty(TEXT_PROPERTY, text);
    }
    
    /**
     * Sets the url of the icon for the button.
     * <p/>
     * Note that if the url is not absolute, then the context path of
     * the application will be prepended to it.
     * 
     * @param iconUrl the url of the icon for the button.
     */
    public void setIconUrl(String iconUrl) {
        setProperty(ICON_URL_PROPERTY, iconUrl);
    }
    
    /**
     * Sets a css class to be used to provide the icon
     * for the button.
     * @param iconClass the css style to provide the icon for the button.
     */
    public void setIconClass(String iconClass) {
    	setProperty(ICON_CLASS_PROPERTY, iconClass);
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
        // Notification of action listener changes is provided due to 
        // existence of hasActionListeners() method. 
        firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
    }
    
    
    /**
     * @see nextapp.echo.app.Component#processInput(java.lang.String, java.lang.Object)
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
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
    private void fireActionEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, (String) getRenderProperty(ACTION_COMMAND_PROPERTY));
            }
            ((ActionListener) listeners[i]).actionPerformed(e);
        }
    }

}
