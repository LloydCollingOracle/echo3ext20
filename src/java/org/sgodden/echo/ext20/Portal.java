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
import org.sgodden.echo.ext20.layout.ColumnLayout;

/**
 * A portal page, which contains columns, which contain portlets that can be dragged around.
 * @author sgodden
 */
public class Portal 
        extends Panel {
    
	/**
	 * Creates a new portal.
	 */
    public Portal(){
        super(new ColumnLayout());
    }
    
    @Override
    public final void add(Component c) {
        if (!(c instanceof PortalColumn)) {
            throw new IllegalArgumentException("Only portal columns may be added to portals");
        }
        super.add(c);
    }

}
