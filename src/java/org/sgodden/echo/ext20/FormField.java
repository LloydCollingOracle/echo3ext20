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
 * A wrapper for a form component and its label.
 * @author sgodden
 */
public class FormField {
    private Component field;
    private String label;

    /**
     * Returns the form field (the input component).
     * @return the form field.
     */
    public Component getField() {
        return field;
    }
    /**
     * Sets the form field (the input component).
     * @param field the form field.
     */
    public void setField(Component field) {
        this.field = field;
    }
    /**
     * Returns the field label.
     * @return the field label.
     */
    public String getLabel() {
        return label;
    }
    /**
     * Sets the field label.
     * @param label the field label.
     */
    public void setLabel(String label) {
        this.label = label;
    }

}