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
 * A basic menu item containing text and an optional icon.
 * 
 * @author sgodden
 */
public class MenuItem 
        extends BaseMenuItem {

	/**
	 * Creates an empty menu item.
	 */
    public MenuItem() {
        super();
    }

    /**
     * Creates a menu item.
     * @param text the text for the menu item.
     */
    public MenuItem(String text) {
        super(text);
    }

    /**
     * Creates a menu item.
     * @param text the text to display.
     * @param iconUrl the URL of the icon to display.
     */
    public MenuItem(String text, String iconUrl) {
        super(text, iconUrl);
    }

}
