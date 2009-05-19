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
 * A html editor field.
 * <p/>
 * TODO - listeners on value change
 * <p/>
 * NOTE - this ext component does not seem to work too well across browsers.  Look at
 * implementing a TinyMCE component instead.
 * 
 * @author simon
 *
 */
@SuppressWarnings({"serial"})
public class HtmlEditor 
        extends ExtComponent {
    
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String TEXT_CHANGED_PROPERTY = "text";
    
    /**
     * Creates a new empty html editor.
     */
    public HtmlEditor() {
        super();
    }
    
    /**
     * Creates a new html editor with the passed HTML.
     * @param text the html content for the editor.
     */
    public HtmlEditor(String text) {
        setText(text);
    }
    
    /**
     * Returns the field label.
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) get(FIELD_LABEL_PROPERTY);
    }

    /**
     * Sets the field label to be used in forms.
     * @param fieldLabel the field label to be used in forms.
     */
    public void setFieldLabel(String fieldLabel) {
        set(FIELD_LABEL_PROPERTY, fieldLabel);
    }
    
    /**
     * Sets the HTML content.
     * @param text the html content.
     */
    public void setText(String text) {
        set(TEXT_CHANGED_PROPERTY, text);
    }
    
    /**
     * Returns the HTML content.
     * @return the HTML content.
     */
    public String getText() {
        return (String) get(TEXT_CHANGED_PROPERTY);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (TEXT_CHANGED_PROPERTY.equals(inputName)) {
            setText((String)inputValue);
        }
    }

}
