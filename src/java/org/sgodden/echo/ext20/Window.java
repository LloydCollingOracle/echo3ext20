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
import org.sgodden.echo.ext20.layout.Layout;

/**
 * A floating window.
 * <p/>
 * <b>Important</b> - by default, if no window listeners are added to the window, it will invoke {@link #close()}
 * when the user clicks the close icon.  However, if any external window listeners are added via
 * the {@link #addWindowListener(WindowListener)} method, this default behaviour is removed,
 * and it becomes the reponsibility of the external listeners to close the window.
 * 
 * @author sgodden
 *
 */
public class Window 
        extends Panel 
        implements WindowListener {

    private static final long serialVersionUID = 20080102L;
    
    public static final String WINDOW_LISTENERS_CHANGED_PROPERTY = "windowListeners";
    public static final String INPUT_WINDOW_CLOSING = "windowClosing";
    
    public static final String MODAL_PROPERTY = "modal";

    public Window(Layout layout, String title) {
        super(layout, title);
    }

    public Window(Layout layout) {
        super(layout);
    }
    
    public Window(String title) {
        super(title);
    }

    public Window() {
        super();
    }
    
    /**
     * Closes the window, by removing it from its parent container.
     */
    public void close() {
    	getParent().remove(this);
    }
    
    @Override
    public void init(){
    	super.init();
    	getEventListenerList().addListener(WindowListener.class, this);
        firePropertyChange(WINDOW_LISTENERS_CHANGED_PROPERTY, null, this);
    }
    
    /**
     * Sets whether the window is modal.
     * @param modal whether the window is modal.
     */
    public void setModal(boolean modal) {
        setProperty(MODAL_PROPERTY, modal);
    }
    
    /**
     * Adds the specified window listener, removing the default window listener behaviour.
     * <p/>
     * <b>Note that it becomes your responsibility to close the window if you add any listeners</b>.
     * @param l the window listener to add.
     */
    public void addWindowListener(WindowListener l) {
        getEventListenerList().removeListener(WindowListener.class, this);
        getEventListenerList().addListener(WindowListener.class, l);
        firePropertyChange(WINDOW_LISTENERS_CHANGED_PROPERTY, null, l);
    }
    
    /**
     * Removes the specified window listener.
     * @param l the window listener to remove.
     */
    public void removeWindowListener(WindowListener l) {
        getEventListenerList().removeListener(WindowListener.class, l);
        firePropertyChange(WINDOW_LISTENERS_CHANGED_PROPERTY, null, l);
    }
    
    /**
     * Returns whether there are any window listeners registered in this window.
     * @return <code>true</code> if there are listeners registered, <code>false</code> if not.
     */
    public boolean hasWindowListeners() {
        return getEventListenerList().getListenerCount(WindowListener.class) != 0;
    }
    
        
    /**
     * @see nextapp.echo.app.Component#processInput(java.lang.String, java.lang.Object)
     */
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (INPUT_WINDOW_CLOSING.equals(inputName)) {
            fireWindowClosingEvent();
        }
    }
    
        
    /**
     * Fires a window closing event to all listeners.
     */
    private void fireWindowClosingEvent() {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(WindowListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, INPUT_WINDOW_CLOSING);
            }
            ((WindowListener) listeners[i]).windowClosing(e);
        }
    }

    /**
     * Default window closing handler, which simply closes the window.
     */
	public void windowClosing(ActionEvent event) {
		this.close();
	}
    
}