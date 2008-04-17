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
import java.util.Locale;
import nextapp.echo.app.ApplicationInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A date field.
 * <p/>
 * TODO - listeners on value change
 * 
 * @author sgodden
 *
 */
public class DateField
        extends ExtComponent {
    
    private static final transient Log log = LogFactory.getLog(DateField.class);

    public static final String DATE_FORMAT_PROPERTY = "dateFormat";
    public static final String DATE_CHANGED_PROPERTY = "date";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String ALLOW_BLANK_PROPERTY = "allowBlank";
    
    private Calendar calendar;
    
    private boolean clientInputValid = true;

    public DateField() {
        super();
        setLocale(ApplicationInstance.getActive().getLocale());
    }

    public DateField(Calendar cal) {
        this();
        setCalendar(cal);
    }

    public DateField(Calendar cal, String fieldLabel) {
        this();
        setCalendar(cal);
        setFieldLabel(fieldLabel);
    }

    public void setCalendar(Calendar cal) {
        this.calendar = cal;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setFieldLabel(String fieldLabel) {
        setProperty(FIELD_LABEL_PROPERTY, fieldLabel);
    }

    public void setBlankAllowed(boolean blankAllowed) {
        setProperty(ALLOW_BLANK_PROPERTY, blankAllowed);
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
            setProperty(DATE_FORMAT_PROPERTY, "d/m/y");
        }
        else {
            setProperty(DATE_FORMAT_PROPERTY, "m/d/y");
        }
    }
    
    /**
     * Directly sets the date format string, since the automatic derivation of
     * this from the java locale is currently broken.
     * @param format the date format string, such as 'm/d/y'.
     */
    public void setDateFormat(String format) {
        setProperty(DATE_FORMAT_PROPERTY, format);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (DATE_CHANGED_PROPERTY.equals(inputName)) {
        	if (!(inputValue instanceof Date)
        			|| inputValue == null) {
        		// must have been an invalid date on the client side
        		this.clientInputValid = false;
        	} else {
        		this.clientInputValid = true;
	            // retrieve the current hour and minute values so that we don't trample over them
	            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
	            int minutes = calendar.get(Calendar.MINUTE);
	            
	            calendar.setTime( (Date) inputValue );
	            
	            // re-set the hour and minutes values that were there before
	            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
	            calendar.set(Calendar.MINUTE, minutes);
        	}
        }
    }
    
    /**
     * Returns whether the last received client input was valid.
     * @return
     */
    public boolean isClientInputValid() {
    	return clientInputValid;
    }
}