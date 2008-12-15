package org.sgodden.echo.ext20.peers.grid;

import java.io.StringReader;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.grid.ColumnModel;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@SuppressWarnings( { "unchecked" })
public class ColumnModelPeer implements SerialPropertyPeer {

	public Object toProperty(Context context, Class objectClass,
			Element propertyElement) throws SerialException {
		try {
			StringBuffer sb = new StringBuffer();
			NodeList nodeList = propertyElement.getChildNodes();
			for (int x = 0; x < nodeList.getLength(); x++) {
				Node node = nodeList.item(x);
				if (node.getNodeType() == 3) {
					sb.append(node.getNodeValue());
				}
			}
			JSONValue jsonColModel = new JSONParser(new StringReader(sb
					.toString())).nextValue();
			ColumnModel model = (ColumnModel) JSONMapper.toJava(jsonColModel,
					ColumnModel.class);
			return model;
		} catch (MapperException e) {
			e.printStackTrace();
			throw new SerialException("Error creating column model", e);
		} catch (TokenStreamException e) {
			e.printStackTrace();
			throw new SerialException("Error creating column model", e);
		} catch (RecognitionException e) {
			e.printStackTrace();
			throw new SerialException("Error creating column model", e);
		}
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
			propertyElement.setTextContent(JSONMapper.toJSON(columnModel)
					.render(false));
		} catch (MapperException e) {
			throw new SerialException("Error mapping ColumnModel", e);
		}

	}
}