package org.sgodden.echo.ext20.peers.grid;

import org.w3c.dom.Element;

import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

public class RemoveColumnEventPeer implements SerialPropertyPeer {

    public Object toProperty(Context arg0, Class arg1, Element arg2)
            throws SerialException {
        String columnIndex = arg2.getFirstChild().getNodeValue();
        return new Integer(columnIndex);
    }

    public void toXml(Context arg0, Class arg1, Element arg2, Object arg3)
            throws SerialException {
        // TODO Auto-generated method stub

    }

}
