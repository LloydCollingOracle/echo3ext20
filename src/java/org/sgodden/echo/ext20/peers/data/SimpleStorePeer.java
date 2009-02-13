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
package org.sgodden.echo.ext20.peers.data;

import java.io.StringReader;

import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.data.TableModelAdapter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.mapper.helper.SimpleMapperHelper;
import com.sdicons.json.mapper.helper.impl.ObjectMapper;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONBoolean;
import com.sdicons.json.model.JSONNull;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@SuppressWarnings( { "unchecked" })
public class SimpleStorePeer implements SerialPropertyPeer {

    public void toXml(Context context, Class objectClass,
            Element propertyElement, Object propertyValue)
            throws SerialException {
        SerialContext serialContext = (SerialContext) context
                .get(SerialContext.class);
        propertyElement
                .setAttribute(
                        "t",
                        (serialContext.getFlags() & SerialContext.FLAG_RENDER_SHORT_NAMES) == 0 ? "Echo2SimpleStore"
                                : "E2SS");
        try {
            propertyElement.setTextContent(JSONMapper.toJSON(propertyValue)
                    .render(false));
        } catch (MapperException e) {
            throw new SerialException("Error mapping SimpleStore", e);
        }
    }

    public Object toProperty(Context context, Class objectClass,
            Element propertyElement) throws SerialException {

        StringBuffer sb = new StringBuffer();
        NodeList nodeList = propertyElement.getChildNodes();
        for (int x = 0; x < nodeList.getLength(); x++) {
            Node node = nodeList.item(x);
            if (node.getNodeType() == 3) {
                sb.append(node.getNodeValue());
            }
        }
        try {
            SimpleMapperHelper smh = JSONMapper.getRepository().findHelper(TableModelAdapter.class);
            if (smh == null || !(smh instanceof TableModelAdapterMapperHelper)) {
                JSONMapper.getRepository().addHelper(new TableModelAdapterMapperHelper());
            }
            
            JSONValue jsonColModel = new JSONParser(new StringReader(sb
                    .toString())).nextValue();
            TableModelAdapter tma = (TableModelAdapter) JSONMapper.toJava(
                    jsonColModel, TableModelAdapter.class);
            
            return tma;
        } catch (MapperException e) {
            e.printStackTrace();
            throw new SerialException("Error creating model", e);
        } catch (TokenStreamException e) {
            e.printStackTrace();
            throw new SerialException("Error creating model", e);
        } catch (RecognitionException e) {
            e.printStackTrace();
            throw new SerialException("Error creating model", e);
        }
    }

    static class TableModelAdapterMapperHelper implements SimpleMapperHelper {

        public JSONValue toJSON(final Object obj) throws MapperException {
            return new ObjectMapper().toJSON(obj);
        }

        public Object toJava(JSONValue jsonColVal, Class requestedClass)
                throws MapperException {
            TableModelAdapter tma = new TableModelAdapter();
            
            JSONObject jsObj = (JSONObject)jsonColVal;
            
            JSONArray dataArr = (JSONArray)jsObj.get("data");
            Object[][] rows = new Object[dataArr.size()][];
            for (int i = 0; i < rows.length; i++) {
                JSONArray rowArr = (JSONArray)dataArr.get(i);
                Object[] thisRow = new Object[rowArr.size()];
                rows[i] = thisRow;
                for (int j = 0; j < thisRow.length; j++) {
                    thisRow[j] = extractValue(rowArr.get(j));
                }
            }
            
            tma.setData(rows);
            
            return tma;
        }
        
        protected Object extractValue(JSONValue val) {
            if (val == null || val instanceof JSONNull)
                return null;
            else if (val instanceof JSONString)
                return ((JSONString)val).getValue();
            else if (val instanceof JSONBoolean)
                return Boolean.valueOf(((JSONBoolean)val).getValue());
            
            throw new IllegalArgumentException("I don't know how to convert value of type: " + val.getClass().getCanonicalName());
        }

        public Class getHelpedClass() {
            return TableModelAdapter.class;
        }

    }
}
