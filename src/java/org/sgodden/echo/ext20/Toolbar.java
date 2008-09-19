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
 * A toolbar.
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class Toolbar 
        extends Component {
    
	/**
	 * The position (top or bottom) of the toolbar.
	 */
    public static final String POSITION_PROPERTY = "position";

    /**
     * Adds a greed (100% width) filler item.
     */
    public void addFill() {
        add(new ToolbarFill());
    }

    /**
     * Adds a separator.
     */
    public void addSeparator() {
        add(new ToolbarSeparator());
    }

    /**
     * Adds and returns a toolbar text item with the specified text.
     * @param text the text for the toolbar item.
     * @return the newly created toolbar text item.
     */
    public ToolbarTextItem addTextItem(String text) {
        ToolbarTextItem item = new ToolbarTextItem(text);
        add(item);
        return item;
    }

    /**
     * Sets the toolbar's position (either top or bottom).
     * @param position the position.
     */
    void setPosition(Position position) {
        switch (position) {
            case TOP:
                set(POSITION_PROPERTY, "top");
                break;
            case BOTTOM:
                set(POSITION_PROPERTY, "bottom");
                break;                
            default:
                throw new IllegalArgumentException("Unknown position: " + position);
        }
    }
    
    static enum Position {
        TOP,
        BOTTOM
    }

}
