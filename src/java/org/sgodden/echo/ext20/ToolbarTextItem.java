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
 * Renders text directly into a toolbar.
 * @author sgodden
 */
public class ToolbarTextItem 
        extends ExtComponent {
    
    public static final String TEXT_PROPERTY = "text";

    /**
     * Creates a new toolbar text item.
     * @param text the text to display.
     */
    public ToolbarTextItem(String text) {
        super();
        setText(text);
    }
    
    /**
     * Sets the text to display.
     * @param text the text to disaplay.
     */
    public void setText(String text) {
        setProperty(TEXT_PROPERTY, text);
    }

}
