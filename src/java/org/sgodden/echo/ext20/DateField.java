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

import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.Locale;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * A date field with built-in drop-down selector.
 * <p/>
 * TODO - listeners on value change.
 * <p/>
 * FIXME - implement proper locale processing.
 * 
 * @author sgodden
 *
 */
@SuppressWarnings({"serial"})
public class DateField
        extends ExtComponent implements Field {
    
    //private static final transient Log log = LogFactory.getLog(DateField.class);

	public static final String PROPERTY_ACTION_LISTENERS_CHANGED = "actionListeners";
    public static final String PROPERTY_DATE_FORMAT = "dateFormat";
    public static final String PROPERTY_DATE_CHANGED = "date";
    public static final String PROPERTY_FIELD_LABEL = "fieldLabel";
    public static final String PROPERTY_ALLOW_BLANK = "allowBlank";
	public static final String PROPERTY_INVALID_TEXT = "invalidText";
	public static final String PROPERTY_VALID = "isValid";
    public static final String PROPERTY_BLANK_TEXT = "blankText";
    
    private Calendar calendar;
    
    private boolean clientInputValid = true;

    /**
     * Creates a new date field, with the date set to today.
     */
    public DateField() {
        super();
        if (ApplicationInstance.getActive() != null)
            setLocale(ApplicationInstance.getActive().getLocale());
    }

    /**
     * Creates a new date field, which maintains the passed calendar.
     * @param cal the calendar to maintain.
     */
    public DateField(Calendar cal) {
        this();
        setCalendar(cal);
    }

    /**
     * Creates a new date field.
     * @param cal the calendar to be maintained.
     * @param fieldLabel the field label to be displayed in a form.
     */
    public DateField(Calendar cal, String fieldLabel) {
        this();
        setCalendar(cal);
        setFieldLabel(fieldLabel);
    }
    
    /**
     * Adds an <code>ActionListener</code> to the button.
     * <code>ActionListener</code>s will be invoked when the combo box is selected.
     *
     * @param l the <code>ActionListener</code> to add
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
        EventListener[] listeners = getEventListenerList().getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < listeners.length; ++i) {
            if (e == null) {
                e = new ActionEvent(this, null);
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
     * Returns the field label.
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) get(PROPERTY_FIELD_LABEL);
    }

    /**
     * Gets allow blank property
     */
    public boolean getAllowBlank(){
        return (Boolean) get(PROPERTY_ALLOW_BLANK);
        
    }
    
    /**
     * Sets the calendar whose value will be maintained by this field.
     * @param cal the calendar to maintain.
     */
    public void setCalendar(Calendar cal) {
        this.calendar = cal;
        set(PROPERTY_DATE_CHANGED, cal);
    }

    /**
     * Returns the calendar being maintained by this field.
     * @return the calendar being maintained.
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Sets the field label to display in forms.
     * @param fieldLabel the field label.
     */
    public void setFieldLabel(String fieldLabel) {
        set(PROPERTY_FIELD_LABEL, fieldLabel);
    }

    /**
     * Sets whether a blank value is allowed.
     * @param blankAllowed whether a blank value is allowed.
     */
    public void setAllowBlank(boolean allowBlank) {
        set(PROPERTY_ALLOW_BLANK, allowBlank);
    }

    /**
     * BROKEN - Sets the locale, and derives the ext locale string from it.
     * <p/>
     * This is broken for all but the simplest cases - use {@link #setDateFormat(String)}
     * instead.
     * @param locale the locale.
     */
    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        // FIXME - we need a robust way of calculating the ext date format
        if (locale.getCountry().equals("GB")) {
            set(PROPERTY_DATE_FORMAT, "d/m/y");
        }
        else {
            set(PROPERTY_DATE_FORMAT, "m/d/y");
        }
    }
    
    /**
     * Directly sets the date format string, since the automatic derivation of
     * this from the java locale is currently broken.
     * @param format the date format string, such as 'm/d/y'.
     */
    public void setDateFormat(String format) {
        set(PROPERTY_DATE_FORMAT, format);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (PROPERTY_DATE_CHANGED.equals(inputName)) {
            if (inputValue == null) {
                setCalendar(null);
            } else if (!(inputValue instanceof Date)) {
                // must have been an invalid date on the client side
                this.clientInputValid = false;
            } else {
                this.clientInputValid = true;
                
                if( calendar == null) {
                    calendar = Calendar.getInstance(getLocale());
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                }
                
                // retrieve the current hour and minute values so that we don't trample over them
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                
                calendar.setTime( (Date) inputValue );
                
                // re-set the hour and minutes values that were there before
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minutes);
                setCalendar(calendar);
                
                fireActionEvent();
            }
        }
    }
    
    /**
     * Returns whether the last received client input was valid.
     * @return whether the last received client input was valid.
     */
    public boolean isClientInputValid() {
        return clientInputValid;
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
        firePropertyChange(PROPERTY_ACTION_LISTENERS_CHANGED, l, null);

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
     * Sets the text to be displayed when the isBlankAllowed test fails.
     * 
     * @param value
     *            the value of the field.
     */
    public void setBlankText(String blankText) {
        set(PROPERTY_BLANK_TEXT, blankText);
    }
}
