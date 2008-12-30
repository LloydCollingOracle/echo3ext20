package org.sgodden.echo.ext20.peers.grid;

import java.io.StringReader;
import java.util.HashMap;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.DefaultColumnConfiguration;
import org.sgodden.echo.ext20.grid.DefaultColumnModel;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONBoolean;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;
import com.sdicons.json.serializer.helper.impl.ObjectHelper;
import com.sdicons.json.serializer.marshall.JSONMarshall;
import com.sdicons.json.serializer.marshall.MarshallException;
import com.sdicons.json.serializer.marshall.MarshallValue;
import com.sdicons.json.serializer.marshall.MarshallValueImpl;

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
            ColumnModel model = (ColumnModel) new ColumnModelMarshaller().unmarshall((JSONObject)jsonColModel).getReference();
            return model;
        } catch (MarshallException e) {
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

    static class ColumnModelMarshaller extends JSONMarshall {
        public MarshallValue unmarshall(JSONObject value) {
            final DefaultColumnModel cm = new DefaultColumnModel();
            if (value.containsKey("defaultSortable")) {
                cm.setDefaultSortable(((JSONBoolean) value.get("defaultSortable"))
                    .getValue());
            }
            if (value.containsKey("defaultWidth")) {
                cm.setDefaultWidth(new Integer(((JSONInteger) value
                    .get("defaultWidth")).getValue().intValue()));
            }
            JSONArray columns = (JSONArray) value.get("columns");
            for (int i = 0; i < columns.size(); i++) {
                JSONObject jsonCol = (JSONObject) columns.get(i);

                // extract the data
                String attributePath = ((JSONString) jsonCol
                        .get("attributePath")).getValue();
                String dataIndex = ((JSONString) jsonCol.get("dataIndex"))
                        .getValue();
                int displaySequence = ((JSONInteger) jsonCol
                        .get("displaySequence")).getValue().intValue();
                String header = ((JSONString) jsonCol.get("header")).getValue();
                boolean hidden = ((JSONBoolean) jsonCol.get("hidden"))
                        .getValue();
                boolean sortable = ((JSONBoolean) jsonCol.get("sortable"))
                        .getValue();
                String sortDirection = ((JSONString) jsonCol
                        .get("sortDirection")).getValue();
                int width = ((JSONInteger) jsonCol.get("width")).getValue()
                        .intValue();

                // set the data on the column
                ColumnConfiguration thisCol = new DefaultColumnConfiguration();
                thisCol.setAttributePath(attributePath);
                thisCol.setDataIndex(dataIndex);
                thisCol.setDisplaySequence(displaySequence);
                thisCol.setHeader(header);
                thisCol.setHidden(hidden);
                thisCol.setSortable(sortable);
                thisCol.setSortDirection(sortDirection);
                thisCol.setWidth(width);
                cm.addColumn(thisCol);
            }

            return new MarshallValueImpl(cm);
        }
    }
}