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
 * A column of portlets within a portal.
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class PortalColumn 
        extends ExtComponent {
    
    public static final String PROPERTY_PADDING = "padding";
    
    @Override
    public final void add(Component c) {
        if (!(c instanceof Portlet)) {
            throw new IllegalArgumentException("Only portlets may be added to portal columns");
        }
        super.add(c);
    }
    
    /**
     * Sets the padding (in CSS style) of the column.
     * @param padding the CSS padding of the column.
     */
    public void setPadding(String padding) {
        set(PROPERTY_PADDING, padding);
    }

}
