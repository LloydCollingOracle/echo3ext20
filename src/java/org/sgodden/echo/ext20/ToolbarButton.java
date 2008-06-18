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
 * A toolbar button.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class ToolbarButton 
        extends Button {

	/**
	 * Creates a new toolbar button.
	 * @param text the text for the button.
	 * @param iconUrl the url of the icon for the button.
	 */
    public ToolbarButton(String text, String iconUrl) {
        super(text, iconUrl);
    }

    /**
     * Creates a new toolbar button.
	 * @param text the text for the button.
     */
    public ToolbarButton(String text) {
        super(text);
    }

    /**
     * Creates a new empty toolbar button.
     */
    public ToolbarButton() {
        super();
    }

}
