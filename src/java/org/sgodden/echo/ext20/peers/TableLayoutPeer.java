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
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.layout.TableLayout;
import org.w3c.dom.Element;

@SuppressWarnings("unchecked")
public class TableLayoutPeer 
implements SerialPropertyPeer {

    public Object toProperty(Context arg0, Class arg1, Element arg2) throws SerialException {
        throw new UnsupportedOperationException();
    }

    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue) throws SerialException {
        SerialContext serialContext = (SerialContext) context.get(SerialContext.class);
        propertyElement.setAttribute("t", 
                (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "Ext20TableLayout" : "E2TL");
        
        TableLayout layout = (TableLayout) propertyValue;
        
        propertyElement.setAttribute("c", String.valueOf(layout.getColumns()));
        propertyElement.setAttribute("fw", layout.getFullWidth() ? "1" : "0");
        propertyElement.setAttribute("fh", layout.getFullHeight() ? "1" : "0");
        propertyElement.setAttribute("p", layout.getCellPadding());
        propertyElement.setAttribute("sp", String.valueOf(layout.getCellSpacing()));
        propertyElement.setAttribute("b", layout.getBorder() ? "1" : "0");
        
        if (layout.getColumnWidths() != null) {
            StringBuffer out = new StringBuffer();
            for (int i = 0; i < layout.getColumnWidths().length; i++) {
                if (i > 0)
                    out.append(",");
                out.append(layout.getColumnWidths()[i]);
            }
            propertyElement.setAttribute("cw", out.toString());
        }
    }

}