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

import nextapp.echo.app.Component;

/**
 * A radio button.
 * <p/>
 * TODO - listeners on value change
 * 
 * 
 * @author simon
 *
 */
@SuppressWarnings({"serial"})
public class RadioButton
        extends Component {

    public static final String SELECTED_CHANGED_PROPERTY = "selected";
    public static final String FIELD_LABEL_PROPERTY = "fieldLabel";
    public static final String NAME_PROPERTY = "name";

    /**
     * Creates a new unselected radion button.
     */
    public RadioButton() {
        super();
        setSelected(false);
    }

    /**
     * Creates a new radio button.
     * @param selected whether the button is initially selected.
     */
    public RadioButton(boolean selected) {
        this();
        setSelected(selected);
    }

    /**
     * Creates a new radion button.
     * @param selected whether the button is initially selected.
     * @param fieldLabel the field label to be displayed in forms.
     */
    public RadioButton(boolean selected, String fieldLabel) {
        setSelected(selected);
        setFieldLabel(fieldLabel);
    }
    
    /**
     * Returns the field label.
     * @return the field label.
     */
    public String getFieldLabel() {
        return (String) get(FIELD_LABEL_PROPERTY);
    }

    /**
     * Sets whether the button is selected.
     * @param selected whether the button is selected.
     */
    public void setSelected(boolean selected) {
        set(SELECTED_CHANGED_PROPERTY, selected);
    }

    /**
     * Returns whether the button is selected.
     * @return whether the button is selected.
     */
    public boolean getSelected() {
        return (Boolean) get(SELECTED_CHANGED_PROPERTY);
    }

    /**
     * Sets the field label to be used in forms.
     * @param fieldLabel the field label to be used in forms.
     */
    public void setFieldLabel(String fieldLabel) {
        set(FIELD_LABEL_PROPERTY, fieldLabel);
    }
    
    /**
     * Sets the name of the field being maintained, which effectively sets
     * the group that this button belongs to.  All radion buttons with the same
     * name are in the same button group.
     * @param name the name.
     */
    public void setName(String name) {
        set(NAME_PROPERTY, name);
    }
    
    /**
     * Returns the name of the button group.
     * @return the name of the button group.
     */
    public String getName() {
        return (String) get(NAME_PROPERTY);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        if (SELECTED_CHANGED_PROPERTY.equals(inputName)) {
            setSelected((Boolean) inputValue);
        }
    }
}
