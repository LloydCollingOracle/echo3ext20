package org.sgodden.echo.ext20.peers.grid;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.grid.ColumnModel;
import org.w3c.dom.Element;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

@SuppressWarnings({"unchecked"})
public class ColumnModelPeer implements SerialPropertyPeer {

	public Object toProperty(Context context, Class objectClass,
			Element propertyElement) throws SerialException {
		throw new UnsupportedOperationException();
	}

	public void toXml(Context context, Class objectClass,
			Element propertyElement, Object propertyValue)
			throws SerialException {
		SerialContext serialContext = (SerialContext) context
				.get(SerialContext.class);
		ColumnModel columnModel = (ColumnModel) propertyValue;
		propertyElement
				.setAttribute(
						"t",
						(serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "Echo2ColumnModel"
								: "E2CM");
		try {
//			propertyElement.setTextContent("<![CDATA["
//					+ JSONMapper.toJSON(columnModel).render(false) + "]]>");
			propertyElement.setTextContent(JSONMapper.toJSON(columnModel).render(false));
		} catch (MapperException e) {
			throw new SerialException("Error mapping ColumnModel", e);
		}

	}
}