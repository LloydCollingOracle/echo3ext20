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

import nextapp.echo.app.event.ActionListener;

/**
 * Listener to be notified before a component is rendered.
 * <p>
 * NOTE - this is likely to be deprecated, since it it will cause overlapping
 * events to occur (i.e. event for component 2 will fire while event for
 * component 1 is still being processed), and overlapping events are not allowed
 * and get discarded.
 * @author sgodden
 */
public interface BeforeRenderListener 
    extends ActionListener {

}
