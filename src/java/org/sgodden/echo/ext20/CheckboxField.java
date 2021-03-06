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
 * A checkbox field.
 * 
 * @author simon
 */
@SuppressWarnings("serial")
public class CheckboxField extends ExtComponent implements Field {

	public static final String PROPERTY_EDITABLE = "editable";
	public static final String PROPERTY_SELECTED_CHANGED = "selected";
	public static final String PROPERTY_FIELD_LABEL = "fieldLabel";

	public static final String INPUT_ACTION = "action";

	public static final String PROPERTY_ACTION_LISTENERS_CHANGED = "actionListeners";
	public static final String PROPERTY_INVALID_TEXT = "invalidText";
	public static final String PROPERTY_VALID = "isValid";

	/**
	 * Creates a new, unselected checkbox.
	 */
	public CheckboxField() {
		super();
		setSelected(false);
	}

	/**
	 * Creates a new checkbox.
	 * 
	 * @param selected
	 *            whether the checkbox should be initially selected.
	 */
	public CheckboxField(boolean selected) {
		this();
		setSelected(selected);
	}

	/**
	 * Creates a new checkbox.
	 * 
	 * @param selected
	 *            whether the checkbox should be initially selected.
	 * @param fieldLabel
	 *            the field label to be displayed in a form.
	 */
	public CheckboxField(boolean selected, String fieldLabel) {
		setSelected(selected);
		setFieldLabel(fieldLabel);
	}

	/**
	 * Returns the field label.
	 * 
	 * @return the field label.
	 */
	public String getFieldLabel() {
		return (String) get(PROPERTY_FIELD_LABEL);
	}

	/**
	 * Sets whether the checkbox is selected.
	 * 
	 * @param selected
	 *            whether the checkbox is selected.
	 */
	public void setSelected(boolean selected) {
		set(PROPERTY_SELECTED_CHANGED, selected);
	}

	/**
	 * Returns whether the checkbox is selected.
	 * 
	 * @return whether the checkbox is selected.
	 */
	public boolean getSelected() {
		return (Boolean) get(PROPERTY_SELECTED_CHANGED);
	}

	/**
	 * Sets the field label to be displayed in a form.
	 * 
	 * @param fieldLabel
	 *            the field label.
	 */
	public void setFieldLabel(String fieldLabel) {
		set(PROPERTY_FIELD_LABEL, fieldLabel);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_SELECTED_CHANGED.equals(inputName)) {
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
	 * Adds an <code>ActionListener</code> to the checkbox.
	 * <code>ActionListener</code>s will be invoked when the checkbox is checked
	 * or unchecked.
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
	 * Removes the specified action listener.
	 * 
	 * @param l
	 *            the <code>ActionListener</code> to remove.
	 */
	public void removeActionListener(ActionListener l) {
		getEventListenerList().removeListener(ActionListener.class, l);
		// Notification of action listener changes is provided due to
		// existence of hasActionListeners() method.
		firePropertyChange(PROPERTY_ACTION_LISTENERS_CHANGED, null, l);
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

}
