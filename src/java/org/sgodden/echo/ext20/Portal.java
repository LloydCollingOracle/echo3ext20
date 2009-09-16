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

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.layout.ColumnLayout;

/**
 * A portal page, which contains columns, which contain portlets that can be
 * dragged around.
 * <p/>
 * Internally, a portal uses a {@link ColumnLayout}, so it's important to ensure
 * that you set the column layout data on the {@link PortalColumn} instances
 * that you add.
 * <p/>
 * Example code: <pre class="code"> public class MyPortal extends Portal {
 * 
 * public MyPortal() { initComponents(); }
 * 
 * private void initComponents() { PortalColumn col1 = new PortalColumn();
 * add(col1); col1.setLayoutData(new ColumnLayoutData(.5));
 * col1.setPadding("10px 0 10px 10px");
 * 
 * Portlet portlet1 = new Portlet(); col1.add(portlet1);
 * portlet1.setTitle("Portlet 1");
 * 
 * ... add some more portlets to column 1 ...
 * 
 * PortalColumn col2 = new PortalColumn(); add(col2); col2.setPadding("10px");
 * col2.setLayoutData(new ColumnLayoutData(.5));
 * 
 * Portlet portlet5 = new Portlet(); col2.add(portlet5);
 * portlet5.setTitle("Portlet 5");
 * 
 * ... etc. ... } } </pre>
 * 
 * @author sgodden
 */
@SuppressWarnings( { "serial" })
public class Portal extends Panel {

    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String INPUT_ACTION = "action";
    private String actionCommand;

    /**
     * Creates a new portal.
     */
    public Portal() {
        super(new ColumnLayout());
    }

    @Override
    public final void add(Component c) {
        if (!(c instanceof PortalColumn)) {
            throw new IllegalArgumentException(
                    "Only portal columns may be added to portals");
        }
        super.add(c);
    }

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

    public String getActionCommand() {
        return actionCommand;
    }

    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
    }
}
