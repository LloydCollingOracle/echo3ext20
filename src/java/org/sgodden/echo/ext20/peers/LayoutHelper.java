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
package org.sgodden.echo.ext20.peers;

import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.Layout;

public class LayoutHelper {
    
    public static int getChildIndex(Layout layout, Object layoutData) {
        if (layout instanceof BorderLayout) {
            return getChildIndexForBorderLayout((BorderLayout)layout, layoutData);
        }
        else {
            throw new IllegalArgumentException("Unsupported layout type: "
                    + layout.getClass());
        }
    }
    
    private static int getChildIndexForBorderLayout(BorderLayout layout, Object layoutData) {
        if (! (layoutData instanceof Integer) ) {
            throw new IllegalArgumentException("Layout data must be one" +
                    " of the BorderLayout constants");
        }
        else {
            Integer intLayoutData = (Integer) layoutData;
            switch (intLayoutData.intValue()) {
            case BorderLayout.NORTH:
                return 1;
            case BorderLayout.EAST:
                return 2;
            case BorderLayout.SOUTH:
                return 3;
            case BorderLayout.WEST:
                return 4;
            case BorderLayout.CENTER:
                return 5;
            default:
                throw new IllegalArgumentException("Unrecognised BorderLayout constant: " + layoutData);
            }
        }
        
    }

}
