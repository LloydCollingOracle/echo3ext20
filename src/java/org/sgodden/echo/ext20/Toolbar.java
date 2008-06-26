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
 * A toolbar.
 * @author sgodden
 */
@SuppressWarnings({"serial"})
public class Toolbar 
        extends ExtComponent {
    
	/**
	 * The position (top or bottom) of the toolbar.
	 */
    public static final String POSITION_PROPERTY = "position";
    
    void setPosition(Position position) {
        switch (position) {
            case TOP:
                setComponentProperty(POSITION_PROPERTY, "top");
                break;
            case BOTTOM:
                setComponentProperty(POSITION_PROPERTY, "bottom");
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
