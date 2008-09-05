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

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialUtil;
import nextapp.echo.app.serial.property.LayoutDataPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.layout.AnchorLayoutData;
import org.sgodden.echo.ext20.layout.BorderLayoutData;
import org.w3c.dom.Element;

/**
 * Rendering peer for {@link AnchorLayoutData}.
 * @author sgodden
 *
 */
@SuppressWarnings({"unchecked"})
public class AnchorLayoutDataPeer extends LayoutDataPeer {
	
    /*
     * (non-Javadoc)
     * @see nextapp.echo.app.serial.property.LayoutDataPeer#toXml(nextapp.echo.app.util.Context, java.lang.Class, org.w3c.dom.Element, java.lang.Object)
     */
    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue) 
    throws SerialException {
        SerialContext serialContext = (SerialContext) context.get(SerialContext.class);
    	AnchorLayoutData layoutData = (AnchorLayoutData) propertyValue;
        propertyElement.setAttribute("t", 
                (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "LayoutData" : "L");
        SerialUtil.toXml(context, BorderLayoutData.class, propertyElement, "anchor", layoutData.getAnchorData());
    }

}
