/* =================================================================0
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

import org.apache.log4j.Logger;

/**
 * A text field. <p/> TODO - listeners on value change <p/> TODO - specify the
 * length
 * 
 * @author simon
 * 
 */
@SuppressWarnings( { "serial" })
public class TextField extends Component {
	
	private static final Logger log = Logger.getLogger(TextField.class);

	public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
	public static final String ALLOW_BLANK_PROPERTY = "allowBlank";
	public static final String BLANK_TEXT_PROPERTY = "blankText";
	public static final String CASE_RESTRICTION_PROPERTY = "caseRestriction";
	public static final String EMPTY_TEXT_PROEPRTY = "emptyText";
	public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
	public static final String INPUT_ACTION = "action";
	public static final String INVALID_TEXT_PROPERTY = "invalidText";
	public static final String PROPERTY_SIZE = "size";
	public static final String REGEXP_FAILURETEXT_PROPERTY = "regExpFailureText";
	public static final String REGEXP_PROPERTY = "regExp";
	public static final String MIN_LENGTH = "minLength";
	public static final String MAX_LENGTH = "maxLength";
	public static final String NOTIFY_VALUE_IMMEDIATE_PROPERTY = "notifyValueImmediate";
	public static final String VALIDATION_DELAY = "validationDelay";
	public static final String VALUE_CHANGED_PROPERTY = "value";
	public static final String VALID_PROPERTY = "isValid";

	private String actionCommand;

	/**
	 * Creates a new empty text field.
	 */
	public TextField() {
		super();
		setIsValid(true);
		setNotifyImmediately(false);
	}

	/**
	 * Creates a new text field.
	 * 
	 * @param text
	 *            the text content of the field.
	 */
	public TextField(String text) {
		this();
		setValue(text);
	}

	/**
	 * Creates a new text field of the specified size.
	 * 
	 * @param size
	 *            the size of the field in characters.
	 */
	public TextField(int size) {
		this();
		setSize(size);
	}

	/**
	 * Creates a new text field.
	 * 
	 * @param text
	 *            the text content of the field.
	 * @param fieldLabel
	 *            the field label to display in forms.
	 */
	public TextField(String text, String fieldLabel) {
		this();
		setValue(text);
		setFieldLabel(fieldLabel);
	}

	/**
	 * Returns the case restriction.
	 * 
	 * @return the case restriction.
	 */
	public String getCaseRestriction() {
		return (String) get(CASE_RESTRICTION_PROPERTY);
	}

	/**
	 * Returns the field label.
	 * 
	 * @return the field label.
	 */
	public String getFieldLabel() {
		return (String) get(FIELD_LABEL_PROPERTY);
	}

	/**
	 * Gets notify immediately property
	 */
	public boolean getNotifyImmediately(){
		return (Boolean) get(NOTIFY_VALUE_IMMEDIATE_PROPERTY);
		
	}
	
	/**
	 * Returns the value (text) of the field.
	 * 
	 * @return the value of the field.
	 */
	public String getValue() {
		return (String) get(VALUE_CHANGED_PROPERTY);
	}

	/**
	 * Sets whether a blank value is allowed.
	 * 
	 * @param blankAllowed
	 *            whether a blank value is allowed.
	 */
	public void setBlankAllowed(boolean blankAllowed) {
		set(ALLOW_BLANK_PROPERTY, blankAllowed);
	}
	
	/**
	 * Sets the text to be displayed when the isBlankAllowed test fails.
	 * 
	 * @param value
	 *            the value of the field.
	 */
	public void setBlankText(String blankText) {
		set(BLANK_TEXT_PROPERTY, blankText);
	}

	/**
	 * Sets the case restriction.
	 * 
	 * @param enum
	 *            the case restriction.
	 */
	public void setCaseRestriction(CaseRestriction caseRestriction) {
		set(CASE_RESTRICTION_PROPERTY, caseRestriction.toString());
	}
	
	/**
	 * Sets text to be displayed when the user has entered no text themselves.
	 * 
	 * @param emptyText
	 *            text to be displayed when the user has entered no text
	 *            themselves.
	 */
	public void setEmptyText(String emptyText) {
		set(EMPTY_TEXT_PROEPRTY, emptyText);
	}

	/**
	 * Sets the field label to be used in forms.
	 * 
	 * @param fieldLabel
	 *            the field label to be used in forms.
	 */
	public void setFieldLabel(String fieldLabel) {
		set(FIELD_LABEL_PROPERTY, fieldLabel);
	}

	/**
	 * Sets the invalid text property.
	 * 
	 * @param invalidText
	 *            the invalid text.
	 */
	public void setInvalidText(String invalidText) {
		set(INVALID_TEXT_PROPERTY, invalidText);
	}
	
	/**
	 * Sets whether the field value is valid.
	 * 
	 * @param isValid
	 *            whether the field value is valid.
	 */
	public void setIsValid(boolean isValid) {
		set(VALID_PROPERTY, isValid);
	}

	/**
	 * Sets the maximum allowed length of the text field.
	 * 
	 * @param maxLength
	 *            the maximum length of the text field.
	 */
	public void setMaxLength(int maxLength) {
		set(MAX_LENGTH, maxLength);
	}
	
	/**
	 * Sets the minimum allowed length of the text field.
	 * 
	 * @param minLength
	 *            the minimum length of the text field.
	 */
	public void setMinLength(int minLength) {
		set(MIN_LENGTH, minLength);
	}

	/**
	 * Set custom property to be set if we want a server message immediatly after a
	 * value has been changed on a text field.
	 * 
	 * @param notify
	 */
	public void setNotifyImmediately(boolean notify) {
		set(NOTIFY_VALUE_IMMEDIATE_PROPERTY, notify);
	}

	/**
	 * Sets a regular expression to be used to validate the value.
	 * 
	 * @param RegExp
	 *            the regular expression.
	 */
	public void setRegExp(String regExp) {
		set(REGEXP_PROPERTY, regExp);
	}

	/**
	 * Custom property which applies a time delay to the 
	 * notify immediately function
	 * 
	 * @param delay the time delay to be applied
	 */
	public void setValidationDelay(int delay) {
		set(VALIDATION_DELAY, delay);
	}
	
	/**
	 * Sets the value (text) of the field.
	 * 
	 * @param value
	 *            the value of the field.
	 */
	public void setValue(String value) {
		set(VALUE_CHANGED_PROPERTY, value);
	}

	public String getMaxLength() {
		return (String) get(MAX_LENGTH);
	}

	/**
	 * Sets the failure text for a regular expression failure.
	 * 
	 * @param The
	 *            failure text.
	 */
	public void setRegexpFailureText(String regExpFailureText) {
		set(REGEXP_FAILURETEXT_PROPERTY, regExpFailureText);
	}

	/**
	 * Sets the size of the text field in characters.
	 * 
	 * @param size the size of the text field in characters.
	 */
	public void setSize(int size) {
		set(PROPERTY_SIZE, size);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (VALUE_CHANGED_PROPERTY.equals(inputName)) {
			setValue((String) inputValue);
		}
		else if (INPUT_ACTION.equals(inputName)) {
			fireActionEvent();
		}
	}

	/**
	 *Fires an action event to all listeners.
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
				e = new ActionEvent(this, actionCommand);
			}
			((ActionListener) listeners[i]).actionPerformed(e);
		}
	}
}