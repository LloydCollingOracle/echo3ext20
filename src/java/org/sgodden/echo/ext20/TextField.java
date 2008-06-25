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

/**
 * A text field.
 * <p/>
 * TODO - listeners on value change
 * <p/>
 * TODO - specify the length
 * 
 * @author simon
 *
 */
@SuppressWarnings({"serial"})
public class TextField
        extends ExtComponent {

    public static final String ALLOW_BLANK_PROPERTY = "allowBlank";
    public static final String EMPTY_TEXT_PROEPRTY = "emptyText";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String REGEXP_PROPERTY = "regExp";
    public static final String VALUE_CHANGED_PROPERTY = "value";

    /**
     * Creates a new empty text field.
     */
    public TextField() {
        super();
    }

    /**
     * Creates a new text field.
     * @param text the text content of the field.
     */
    public TextField(String text) {
        this();
        setValue(text);
    }

    /**
     * Creates a new text field.
     * @param text the text content of the field.
     * @param fieldLabel the field label to display in forms.
     */
    public TextField(String text, String fieldLabel) {
        setValue(text);
        setFieldLabel(fieldLabel);
    }

    /**
     * Sets the value (text) of the field.
     * @param value the value of the field.
     */
    public void setValue(String value) {
        setProperty(VALUE_CHANGED_PROPERTY, value);
    }

    /**
     * Returns the value (text) of the field. 
     * @return the value of the field.
     */
    public String getValue() {
        return (String) getProperty(VALUE_CHANGED_PROPERTY);
    }
    
    /**
     * Sets the field label to be used in forms.
     * @param fieldLabel the field label to be used in forms.
     */
    public void setFieldLabel(String fieldLabel) {
        setProperty(FIELD_LABEL_PROPERTY, fieldLabel);
    }

    /**
     * Sets whether a blank value is allowed.
     * @param blankAllowed whether a blank value is allowed.
     */
    public void setBlankAllowed(boolean blankAllowed) {
        setProperty(ALLOW_BLANK_PROPERTY, blankAllowed);
    }
    
    /**
     * Sets text to be displayed when the user has entered no text themselves.
     * @param emptyText text to be displayed when the user has entered no text themselves.
     */
    public void setEmptyText(String emptyText) {
        setProperty(EMPTY_TEXT_PROEPRTY, emptyText);
    }
    
    /**
     * Sets a regular expression to be used to validate the value.
     * @param RegExp the regular expression.
     */
    public void setRegExp(String regExp) {
        setProperty(REGEXP_PROPERTY, regExp);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (VALUE_CHANGED_PROPERTY.equals(inputName)) {
            setValue((String) inputValue);
        }
    }
}
