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
public class TextField extends ExtComponent implements Field {
    
    public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
    public static final String PROPERTY_ALLOW_BLANK = "allowBlank";
    public static final String PROPERTY_BLANK_TEXT = "blankText";
    public static final String PROPERTY_CASE_RESTRICTION = "caseRestriction";
    public static final String PROPERTY_EDITABLE = "editable";
    public static final String PROPERTY_EMPTY_TEXT = "emptyText";
    public static final String PROPERTY_FIELD_LABEL = "fieldLabel";
    public static final String INPUT_ACTION = "action";
    public static final String PROPERTY_INPUT_MASK = "maskRe";
    public static final String PROPERTY_INVALID_TEXT = "invalidText";
    public static final String PROPERTY_SIZE = "size";
    public static final String PROPERTY_REGEXP_FAILURETEXT = "regExpFailureText";
    public static final String PROPERTY_REGEXP = "regExp";
    public static final String PROPERTY_MIN_LENGTH = "minLength";
    public static final String PROPERTY_MIN_LENGTH_TEXT = "minLengthText";
    public static final String PROPERTY_MAX_LENGTH = "maxLength";
    public static final String PROPERTY_MAX_LENGTH_TEXT = "maxLengthText";
    public static final String PROPERTY_NOTIFY_VALUE_IMMEDIATE = "notifyValueImmediate";
    public static final String PROPERTY_VALIDATION_DELAY = "validationDelay";
    public static final String PROPERTY_VALUE_CHANGED = "value";
    public static final String PROPERTY_VALID = "isValid";
    public static final String PROPERTY_STRIP_WHITESPACE = "stripWhitespace";
    public static final String PROPERTY_GROW = "grow";
    public static final String PROPERTY_GROW_MIN = "growMin";
    public static final String PROPERTY_GROW_MAX = "growMax";

    private String actionCommand;

    /**
     * Creates a new empty text field.
     */
    public TextField() {
        super();
        setIsValid(true);
        setNotifyImmediately(false);
        setGrow(false);
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
        return (String) get(PROPERTY_CASE_RESTRICTION);
    }
    
    /**
     * Returns editable property 
     * 
     * @return the editable property
     */
    public boolean getEditable() {
        return (Boolean) get(PROPERTY_EDITABLE);
    }

    /**
     * Gets allow blank property
     */
    public boolean getAllowBlank(){
        return (Boolean) get(PROPERTY_ALLOW_BLANK);
        
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
     * Gets notify immediately property
     */
    public boolean getNotifyImmediately(){
        return (Boolean) get(PROPERTY_NOTIFY_VALUE_IMMEDIATE);
        
    }
    
    /**
     * Returns the value (text) of the field.
     * 
     * @return the value of the field.
     */
    public String getValue() {
        return (String) get(PROPERTY_VALUE_CHANGED);
    }

    /**
     * Sets whether a blank value is allowed.
     * 
     * @param allowBlank
     *            whether a blank value is allowed.
     */
    public void setAllowBlank(boolean allowBlank) {
        set(PROPERTY_ALLOW_BLANK, allowBlank);
    }
    
    /**
     * Sets the text to be displayed when the isBlankAllowed test fails.
     * 
     * @param value
     *            the value of the field.
     */
    public void setBlankText(String blankText) {
        set(PROPERTY_BLANK_TEXT, blankText);
    }

    /**
     * Sets the case restriction.
     * 
     * @param enum
     *            the case restriction.
     */
    public void setCaseRestriction(CaseRestriction caseRestriction) {
        set(PROPERTY_CASE_RESTRICTION, caseRestriction.toString());
    }
    
    /**
     * Sets the editable property for the component. To save on 
     * bandwidth only pass the property if we are going to disable the field.
     * @param editable
     */
    public void setEditable(boolean editable){
        if (!editable){
            set(PROPERTY_EDITABLE, editable);
        }
    }
    
    /**
     * Sets text to be displayed when the user has entered no text themselves.
     * 
     * @param emptyText
     *            text to be displayed when the user has entered no text
     *            themselves.
     */
    public void setEmptyText(String emptyText) {
        set(PROPERTY_EMPTY_TEXT, emptyText);
    }

    /**
     * Sets the field label to be used in forms.
     * 
     * @param fieldLabel
     *            the field label to be used in forms.
     */
    public void setFieldLabel(String fieldLabel) {
        set(PROPERTY_FIELD_LABEL, fieldLabel);
    }

    /**
     * Sets the input mask to a regular expression
     * that matches invalid keystrokes.
     * 
     * @param inputMask
     *            the input mask regular expression.
     */
    public void setInputMask(String inputMask) {
        set(PROPERTY_INPUT_MASK, inputMask);
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
    
    /**
     * Sets the maximum allowed length of the text field.
     * 
     * @param maxLength
     *            the maximum length of the text field.
     */
    public void setMaxLength(int maxLength) {
        set(PROPERTY_MAX_LENGTH, maxLength);
    }
    
    /**
     * Sets the error text if the max length of the field is exceeded.
     * 
     * @param maxLenthText
     */
    public void setMaxLengthText(String maxLengthText){
        set(PROPERTY_MAX_LENGTH_TEXT, maxLengthText);
    }
    
    /**
     * Sets the minimum allowed length of the text field.
     * 
     * @param minLength
     *            the minimum length of the text field.
     */
    public void setMinLength(int minLength) {
        set(PROPERTY_MIN_LENGTH, minLength);
    }
    
    /**
     * Sets the error text if the min length of the field is not satisfied.
     * 
     * @param minLenthText
     */
    public void setMinLengthText(String minLengthText){
        set(PROPERTY_MIN_LENGTH_TEXT, minLengthText);
    }

    /**
     * Set custom property to be set if we want a server message immediatly after a
     * value has been changed on a text field.
     * 
     * @param notify
     */
    public void setNotifyImmediately(boolean notify) {
        set(PROPERTY_NOTIFY_VALUE_IMMEDIATE, notify);
    }

    /**
     * Sets a regular expression to be used to validate the value.
     * 
     * @param RegExp
     *            the regular expression.
     */
    public void setRegExp(String regExp) {
        set(PROPERTY_REGEXP, regExp);
    }
    
    public String getRegExp() {
        return (String)get(PROPERTY_REGEXP);
    }

    /**
     * Custom property which applies a time delay to the 
     * notify immediately function
     * 
     * @param delay the time delay to be applied
     */
    public void setValidationDelay(int delay) {
        set(PROPERTY_VALIDATION_DELAY, delay);
    }
    
    /**
     * Sets the value (text) of the field.
     * 
     * @param value
     *            the value of the field.
     */
    public void setValue(String value) {
        set(PROPERTY_VALUE_CHANGED, value);
    }

    public String getMaxLength() {
        Object maxL = get(PROPERTY_MAX_LENGTH);
        if (maxL == null)
            return null;
        if (maxL instanceof String)
            return (String)maxL;
        else
            return String.valueOf(maxL);
    }

    /**
     * Sets the failure text for a regular expression failure.
     * 
     * @param The
     *            failure text.
     */
    public void setRegexpFailureText(String regExpFailureText) {
        set(PROPERTY_REGEXP_FAILURETEXT, regExpFailureText);
    }
    
    public String getRegexpFailureText() {
        return (String)get(PROPERTY_REGEXP_FAILURETEXT);
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
        if (PROPERTY_VALUE_CHANGED.equals(inputName)) {
            setValue((String) inputValue);
        }
        else if (INPUT_ACTION.equals(inputName)) {
            fireActionEvent();
        }
        else if (PROPERTY_VALID.equals(inputName)) {
        	setIsValid((Boolean) inputValue);
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
    
    /**
     * Adds an <code>ActionListener</code> to the textfield.
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

	public void setMaskRe(String value) {
		setInputMask(value);
	}

    public boolean isStripWhitespace() {
        return Boolean.TRUE.equals(get(PROPERTY_STRIP_WHITESPACE));
    }

    public void setStripWhitespace(boolean stripWhitespace) {
        set(PROPERTY_STRIP_WHITESPACE, Boolean.valueOf(stripWhitespace));
    }

    /**
     * Whether this field will grow in size as content is added
     * @return
     */
    public boolean getGrow() {
        return Boolean.TRUE.equals(get(PROPERTY_GROW));
    }

    public boolean isGrow() {
        return Boolean.TRUE.equals(get(PROPERTY_GROW));
    }

    public void setGrow(boolean grow) {
        set(PROPERTY_GROW, Boolean.valueOf(grow));
    }

    /**
     * What the minimum size (width/height in pixels) of this field is when shortening.
     * For TextFields, default value is 30 (pixels width). For TextAreas, default value is 60 (pixels height).
     * @return
     */
    public Integer getGrowMin() {
        return (Integer)get(PROPERTY_GROW_MIN);
    }

    public void setGrowMin(Integer growMin) {
        set(PROPERTY_GROW_MIN, growMin);
    }

    /**
     * What the maximum size (width/height in pixels) of this field is when growing.
     * For TextFields, default value is 800 (pixels width). For TextAreas, default value is 1000 (pixels height).
     * @return
     */
    public Integer getGrowMax() {
        return (Integer)get(PROPERTY_GROW_MAX);
    }

    public void setGrowMax(Integer growMax) {
        set(PROPERTY_GROW_MAX, growMax);
    }
}