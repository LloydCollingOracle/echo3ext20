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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A time field.
 * <p/>
 * TODO - listeners on value change
 * 
 * @author sgodden
 *
 */
public class TimeField
        extends ExtComponent {
    
    private static final transient Log log = LogFactory.getLog(TimeField.class);

    public static final String TIME_FORMAT_PROPERTY = "timeFormat";
    public static final String TIME_CHANGED_PROPERTY = "time";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String ALLOW_BLANK_PROPERTY = "allowBlank";
    
    private Calendar calendar;

    public TimeField() {
        super();
        setFormat("H:i");
    }

    public TimeField(Calendar cal) {
        this();
        setCalendar(cal);
    }

    public TimeField(Calendar cal, String fieldLabel) {
        this(cal);
        setFieldLabel(fieldLabel);
    }

    public void setCalendar(Calendar cal) {
        this.calendar = cal;
        
        // set the text time property
        String hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(cal.get(Calendar.MINUTE));
        
        setProperty(TIME_CHANGED_PROPERTY, hours + ":" + mins);
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public void setFieldLabel(String fieldLabel) {
        setProperty(FIELD_LABEL_PROPERTY, fieldLabel);
    }

    public void setBlankAllowed(boolean blankAllowed) {
        setProperty(ALLOW_BLANK_PROPERTY, blankAllowed);
    }
    
    /**
     * Sets the time format string.
     * @param format the time format string, according to the rules specified in 
     * the ext documentation for Date.parseDate.
     */
    private void setFormat(String format) { // private because we are not handling different formats properly yet
        setProperty(TIME_FORMAT_PROPERTY, format);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (TIME_CHANGED_PROPERTY.equals(inputName)) {
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
}