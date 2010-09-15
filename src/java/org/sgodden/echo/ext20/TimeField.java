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

/**
 * A time field.
 * <p/>
 * TODO - listeners on value change
 * 
 * @author sgodden
 *
 */
@SuppressWarnings({"serial"})
public class TimeField
        extends ExtComponent implements Field {
    
    //private static final transient Log log = LogFactory.getLog(TimeField.class);

    public static final String PROPERTY_TIME_FORMAT = "timeFormat";
    public static final String PROPERTY_TIME_CHANGED = "time";
    public static final String PROPERTY_FIELD_LABEL = "fieldLabel";
    public static final String PROPERTY_ALLOW_BLANK = "allowBlank";
	public static final String PROPERTY_INVALID_TEXT = "invalidText";
	public static final String PROPERTY_VALID = "isValid";
    
    private Calendar calendar;    

    /**
     * Creates a new empty time field.
     */
    public TimeField() {
        super();
        setFormat("H:i");
    }

    /**
     * Creates a new time field.
     * @param cal the calendar whose time component should be maintained.
     */
    public TimeField(Calendar cal) {
        this();
        setCalendar(cal);
    }

    /**
     * Creates a new time field.
     * @param cal the calendar whose time component should be maintained.
     * @param fieldLabel the field label to be displayed in forms.
     */
    public TimeField(Calendar cal, String fieldLabel) {
        this(cal);
        setFieldLabel(fieldLabel);
    }

    /**
     * Sets the calendar whose time component should be maintained.
     * @param cal the calendar whose time component should be maintained.
     */
    public void setCalendar(Calendar cal) {
        this.calendar = cal;
        
        // set the text time property
        String hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(cal.get(Calendar.MINUTE));
        
        set(PROPERTY_TIME_CHANGED, hours + ":" + mins);
    }

    /**
     * Returns the calendar whose time component should be maintained.
     * @return the calendar whose time component should be maintained.
     */
    public Calendar getCalendar() {
        return this.calendar;
    }

    /**
     * Sets the field label to be displayed in forms.
     * @param fieldLabel the field label to be displayed in forms.
     */
    public void setFieldLabel(String fieldLabel) {
        set(PROPERTY_FIELD_LABEL, fieldLabel);
    }

    /**
     * Sets whether a blank value is allowed.
     * @param blankAllowed whether a blank value is allowed.
     * use allowBlank() method instead.
     */
    @Deprecated
    public void setBlankAllowed(boolean blankAllowed) {
        set(PROPERTY_ALLOW_BLANK, blankAllowed);
    }
    
    public void setAllowBlank( boolean allowBlank) {
        set(PROPERTY_ALLOW_BLANK, allowBlank);
    }
    
    /**
     * Sets the time format string.
     * @param format the time format string, according to the rules specified in 
     * the ext documentation for Date.parseDate.
     */
    private void setFormat(String format) { // private because we are not handling different formats properly yet
        set(PROPERTY_TIME_FORMAT, format);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (PROPERTY_TIME_CHANGED.equals(inputName)) {
            String timeValue = (String) inputValue;
            int hourOfDay = 0;
            int minute = 0;
            if (timeValue != null) {
                String[] strings = timeValue.split(":");
                hourOfDay = Integer.valueOf(strings[0]);
                minute = Integer.valueOf(strings[1]);
            }
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
        }
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