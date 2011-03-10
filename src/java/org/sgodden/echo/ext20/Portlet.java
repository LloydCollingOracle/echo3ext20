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
 * A portlet, which is essentially a panel that can be added to a portal column,
 * and which can then be dragged around, collapsed, or closed.
 * 
 * @author sgodden
 * @see Portal
 * @see PortalColumn
 */
@SuppressWarnings( { "serial" })
public class Portlet extends Panel {
	public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
	public static final String INPUT_ACTION = "action";
	private String actionCommand;

	/**
	 * Track where the Portlet was
	 */
	private Integer lastColumn = null, lastRow = null;

	/**
	 * The column in which the portlet resides.
	 */
	public static final String PROPERTY_COLUMN = "column";
	/**
	 * The row in which the portlet resides.
	 */
	public static final String PROPERTY_ROW = "row";
	/**
	 * Is the Portlet collapsed?
	 */
	public static final String PROPERTY_COLLAPSED = "collapsed";

	public Portlet() {
		super();
	}

	public Integer getColumn() {
		return (Integer) get(PROPERTY_COLUMN);
	}

	public void setColumn(Integer column) {
		lastColumn = getColumn();
		set(PROPERTY_COLUMN, column);
	}

	/**
	 * @return the column this Portlet was in previously or the current one if
	 *         lastColumn is null
	 */
	public Integer getLastColumn() {
		if (lastColumn == null) {
			return getColumn();
		}
		return lastColumn;
	}

	public Integer getRow() {
		return (Integer) get(PROPERTY_ROW);
	}

	public void setRow(Integer row) {
		lastRow = getRow();
		set(PROPERTY_ROW, row);
	}

	/**
	 * @return the row this Portlet was in previously or the current one if
	 *         lastRow is null
	 */
	public Integer getLastRow() {
		if (lastRow == null) {
			return getRow();
		}
		return lastRow;
	}

	public Boolean getCollapsed() {
		return (Boolean) get(PROPERTY_COLLAPSED);
	}

	public void setCollapsed(Boolean collapsed) {
		set(PROPERTY_COLLAPSED, collapsed);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_COLUMN.equals(inputName)) {
			setColumn((Integer) inputValue);
		} else if (PROPERTY_ROW.equals(inputName)) {
			setRow((Integer) inputValue);
		} else if (PROPERTY_COLLAPSED.equals(inputName)) {
			setCollapsed((Boolean) inputValue);
		}
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
