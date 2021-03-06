/*
 * ================================================================= # This
 * library is free software; you can redistribute it and/or # modify it under
 * the terms of the GNU Lesser General Public # License as published by the Free
 * Software Foundation; either # version 2.1 of the License, or (at your option)
 * any later version. # # This library is distributed in the hope that it will
 * be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of #
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU # Lesser
 * General Public License for more details. # # You should have received a copy
 * of the GNU Lesser General Public # License along with this library; if not,
 * write to the Free Software # Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301 USA # #
 * =================================================================
 */
package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialUtil;
import nextapp.echo.app.serial.property.LayoutDataPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.layout.TableLayoutData;
import org.w3c.dom.Element;

/**
 * Serialization peer for the {@link TableLayoutData} class.
 * @author sgodden
 */
@SuppressWarnings("unchecked")
public class TableLayoutDataPeer extends LayoutDataPeer {

    public void toXml(Context context, Class objectClass,
            Element propertyElement, Object propertyValue)
            throws SerialException {
        SerialContext serialContext = (SerialContext) context
                .get(SerialContext.class);
        TableLayoutData layoutData = (TableLayoutData) propertyValue;
        propertyElement
                .setAttribute(
                        "t",
                        (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "LayoutData"
                                : "L");

        SerialUtil.toXml(context, TableLayoutData.class, propertyElement,
                "cellStyle", layoutData.getCssStyles());
        SerialUtil.toXml(context, TableLayoutData.class, propertyElement,
                "cellAlign", layoutData.getCellAlign());
        SerialUtil.toXml(context, TableLayoutData.class, propertyElement,
                "cellVAlign", layoutData.getCellVAlign());
        SerialUtil.toXml(context, TableLayoutData.class, propertyElement,
                "cellCls", layoutData.getCellCls());
        SerialUtil.toXml(context, TableLayoutData.class, propertyElement,
                "colSpan", layoutData.getColSpan());
        SerialUtil.toXml(context, TableLayoutData.class, propertyElement,
                "rowSpan", layoutData.getRowSpan());
    }

}
