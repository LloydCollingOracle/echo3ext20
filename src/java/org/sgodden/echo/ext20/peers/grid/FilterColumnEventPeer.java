package org.sgodden.echo.ext20.peers.grid;

import org.sgodden.echo.ext20.grid.FilterColumnEvent;
import org.w3c.dom.Element;

import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

/**
 * peer for serialization of properties associated with the {@link FilterColumnEvent}
 * @author dohare
 *
 */
public class FilterColumnEventPeer implements SerialPropertyPeer {

	@Override
	public Object toProperty(Context arg0, Class arg1, Element arg2)
			throws SerialException {
		String columnIndex = arg2.getFirstChild().getNodeValue();
        return new Integer(columnIndex);
	}

	@Override
	public void toXml(Context arg0, Class arg1, Element arg2, Object arg3)
			throws SerialException {
		// TODO Auto-generated method stub
		
	}

}
