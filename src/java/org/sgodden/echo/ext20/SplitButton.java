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

import nextapp.echo.app.ImageReference;

/**
 * A split button that provides a built-in dropdown arrow that can fire 
 * an event separately from the default click event of the button. 
 * <p/>
 * Typically this would be used to display a dropdown menu that provides 
 * additional options to the primary button action, but any custom handler 
 * can provide the arrowclick implementation.
 * 
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class SplitButton 
        extends Button {

	public SplitButton() {
        super();
    }

    public SplitButton(String text, ImageReference icon) {
        super(text, icon);
    }

    public SplitButton(String text) {
        super(text);
    }
	
}
