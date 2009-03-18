package org.sgodden.echo.ext20.peers;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialUtil;
import nextapp.echo.app.serial.property.LayoutDataPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.HtmlLayoutData;
import org.w3c.dom.Element;

public class HtmlLayoutDataPeer extends LayoutDataPeer{
    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue) 
    throws SerialException {
        SerialContext serialContext = (SerialContext) context.get(SerialContext.class);
        HtmlLayoutData layoutData = (HtmlLayoutData) propertyValue;
        propertyElement.setAttribute("t", 
                (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "LayoutData" : "L");
        SerialUtil.toXml(context, HtmlLayoutData.class, propertyElement, "locationName", layoutData.getLocationName());
    }

}
