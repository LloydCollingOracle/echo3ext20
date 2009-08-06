package org.sgodden.echo.ext20.peers.grid;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nextapp.echo.app.Component;
import nextapp.echo.app.list.ListModel;
import nextapp.echo.app.serial.SerialContext;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;

import org.sgodden.echo.ext20.CheckboxField;
import org.sgodden.echo.ext20.ComboBox;
import org.sgodden.echo.ext20.DateField;
import org.sgodden.echo.ext20.MultiSelectComboBox;
import org.sgodden.echo.ext20.TextArea;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.DefaultColumnConfiguration;
import org.sgodden.echo.ext20.grid.DefaultColumnModel;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.mapper.helper.SimpleMapperHelper;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONBoolean;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONNull;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@SuppressWarnings( { "unchecked" })
public class ColumnModelPeer implements SerialPropertyPeer {
    
    static Map<Class<?>, String> editorClassToType = null;
    static {
        editorClassToType = new HashMap<Class<?>, String>();
        editorClassToType.put(TextField.class, "Ext.form.TextField");
        editorClassToType.put(TextArea.class, "Ext.form.TextArea");
        editorClassToType.put(CheckboxField.class, "Ext.form.Checkbox");
        editorClassToType.put(ComboBox.class, "Ext.form.ComboBox");
        editorClassToType.put(MultiSelectComboBox.class, "Ext.ux.Andrie.Select");
        editorClassToType.put(DateField.class, "Ext.form.DateField");
    }

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
            JSONMapper.getRepository().addHelper(new ColumnConfigurationMapperHelper());
            JSONMapper.getRepository().addHelper(new ColumnModelMapperHelper());
            JSONValue jsonColModel = new JSONParser(new StringReader(sb
                    .toString())).nextValue();
            ColumnModel model = (ColumnModel)JSONMapper.toJava(jsonColModel, ColumnModel.class);
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
        JSONMapper.getRepository().addHelper(new ColumnConfigurationMapperHelper());
        JSONMapper.getRepository().addHelper(new ColumnModelMapperHelper());
        try {
            String colModelText = JSONMapper.toJSON(columnModel).render(false);
            propertyElement.setTextContent(colModelText);
        } catch (DOMException e) {
            throw new SerialException("Failed to map the column model into JSON", e);
        } catch (MapperException e) {
            throw new SerialException("Failed to map the column model into JSON", e);
        }
    }
    
    static class ColumnConfigurationMapperHelper implements SimpleMapperHelper {

        public JSONValue toJSON(final Object obj) throws MapperException {
            final ColumnConfiguration cc = (ColumnConfiguration)obj;
            JSONObject ret = new JSONObject();
            Map<String, JSONValue> attributes = ret.getValue();
            if (cc.getAttributePath() != null)
                attributes.put("attributePath", new JSONString(cc.getAttributePath()));
            if (cc.getSortDirection() != null)
                attributes.put("sortDirection", new JSONString(cc.getSortDirection()));
            attributes.put("displaySequence", new JSONInteger(new BigInteger("" + cc.getDisplaySequence())));
            attributes.put("sortSequence", new JSONInteger(new BigInteger("" + cc.getSortSequence())));
            if (cc.getHeader() != null)
                attributes.put("header", new JSONString(cc.getHeader()));
            if (cc.getWidth() != null) {
                if (Integer.valueOf(-1).equals(cc.getWidth())) {
                    attributes.put("width", new JSONInteger(new BigInteger("100")));
                } else {
                    attributes.put("width", new JSONInteger(new BigInteger("" + cc.getWidth())));
                }
            }
            attributes.put("sortable", new JSONBoolean(cc.getSortable()));
            attributes.put("menuDisabled", new JSONBoolean(cc.isMenuDisabled()));
            if (cc.getDataIndex() != null)
                attributes.put("dataIndex", new JSONString(cc.getDataIndex()));
                attributes.put("hidden", new JSONBoolean(cc.getHidden()));
                attributes.put("grouping", new JSONBoolean(cc.getGrouping()));
            if (cc.getColumnClass() != null)
                attributes.put("columnClass", new JSONString(cc.getColumnClass().getCanonicalName()));
            if (cc.getEditorComponent() != null) {
                JSONObject editorConfig = new JSONObject();
                Map<String, JSONValue> attributes2 = editorConfig.getValue();
                Component c = cc.getEditorComponent();
                
                Class<?> componentClass = c.getClass();
                do {
                    if (editorClassToType.containsKey(componentClass)) {
                        attributes2.put("type", new JSONString(editorClassToType.get( componentClass)));
                    }
                    componentClass = componentClass.getSuperclass();
                } while (!Component.class.equals(componentClass) && !attributes2.containsKey("type"));
                
                if (!attributes2.containsKey("type")) {
                    throw new IllegalStateException("Unknown type being used for editor!!!: " + c.getClass());
                }
                if ( attributes2.get( "type").toString().contains("Ext.form.ComboBox")) {
                	ComboBox combo = (ComboBox)c;
                	ListModel model = combo.getModel();
                	Object[] values = new Object[model.size()];
                	for ( int i=0; i<model.size(); i++) {
                		values[i] = model.get(i);
                	}
                	attributes2.put( "modelValues", JSONMapper.toJSON( values));
                }
                if ( attributes2.get( "type").toString().contains("Ext.ux.Andrie.Select")) {
                	MultiSelectComboBox combo = (MultiSelectComboBox)c;
                	ListModel model = combo.getModel();
                	Object[] values = new Object[model.size()];
                	for ( int i=0; i<model.size(); i++) {
                		values[i] = model.get(i);
                	}
                	attributes2.put( "modelValues", JSONMapper.toJSON( values));
                	attributes2.put( "multiSelect", JSONMapper.toJSON( combo.getMultiSelect()));
                }
                for (Iterator it = c.getLocalStyle().getPropertyNames(); it.hasNext();) {
                    String name = (String)it.next();
                    Object value = c.getLocalStyle().get(name);
                    if (value == null || "isValid".equals(name)) {
                        // ignore attribute
                    } else {
                        attributes2.put(name, JSONMapper.toJSON(value));
                    }
                }
                attributes.put("editorConfig", editorConfig);
            }
            return ret;
        }

        public Object toJava(JSONValue jsonColVal, Class requestedClass) throws MapperException {
            JSONObject jsonCol = (JSONObject)jsonColVal;

            // extract the data
            String attributePath = getStringValue(jsonCol
                    .get("attributePath"));
            String dataIndex = getStringValue(jsonCol.get("dataIndex"));
            int displaySequence = getIntegerValue(
                    jsonCol.get("displaySequence"));
            String header = getStringValue(jsonCol.get("header"));
            boolean hidden = getBooleanValue(jsonCol.get("hidden"));
            boolean sortable = getBooleanValue(jsonCol.get("sortable"));
            boolean grouping = getBooleanValue(jsonCol.get("grouping"));
            boolean menuDisabled = getBooleanValue(jsonCol.get("menuDisabled"));
            String sortDirection = getStringValue(jsonCol
                    .get("sortDirection"));
            int width = getIntegerValue(jsonCol.get("width"));

            // set the data on the column
            DefaultColumnConfiguration thisCol = new DefaultColumnConfiguration();
            thisCol.setAttributePath(attributePath);
            thisCol.setDataIndex(dataIndex);
            thisCol.setDisplaySequence(displaySequence);
            thisCol.setHeader(header);
            thisCol.setHidden(hidden);
            thisCol.setSortable(sortable);
            thisCol.setSortDirection(sortDirection);
            thisCol.setWidth(width);
            thisCol.setGrouping(grouping);
            thisCol.setMenuDisabled(menuDisabled);
            
            
            return thisCol;
        }

        public Class getHelpedClass() {
            return ColumnConfiguration.class;
        }
        
    }

    /**
     * Provides methods to marshall and unmarshall ColumnModels from and to JSON.
     * 
     * This is required as there are complex properties that have to be coped with on the
     * way to the client, and properties that may be missing when unmarshalling from the
     * client.
     * @author Lloyd Colling
     *
     */
    static class ColumnModelMapperHelper implements SimpleMapperHelper {

        public JSONValue toJSON(Object obj) throws MapperException {
            
            final ColumnModel cModel = (ColumnModel)obj;
            JSONObject ret = new JSONObject();

            Map<String, JSONValue> attributes = ret.getValue();
            if (cModel.getDefaultWidth() != null)
                attributes.put("defaultWidth", new JSONInteger(new BigInteger("" + cModel.getDefaultWidth())));
            attributes.put("defaultSortable", new JSONBoolean(cModel.isDefaultSortable()));
            
            final List<JSONValue> columns = new ArrayList<JSONValue>();
            for (final ColumnConfiguration cc : cModel ) {
                try {
                    columns.add(JSONMapper.toJSON(cc));
                } catch (MapperException e) {
                    throw new RuntimeException("Failure in mapping a column configuration", e);
                }
            }
            
            JSONArray columnsArray = new JSONArray();
            columnsArray.getValue().addAll(columns);
            attributes.put("columns", columnsArray);
            return ret;
        }


        public Object toJava(JSONValue val, Class requestedClass) throws MapperException {
            JSONObject value = (JSONObject)val;
            final DefaultColumnModel cm = new DefaultColumnModel();
            if (value.containsKey("defaultSortable")) {
                cm.setDefaultSortable(((JSONBoolean) value
                        .get("defaultSortable")).getValue());
            }
            if (value.containsKey("defaultWidth")) {
                cm.setDefaultWidth(new Integer(((JSONInteger) value
                        .get("defaultWidth")).getValue().intValue()));
            }
            JSONArray columns = (JSONArray) value.get("columns");
            for (int i = 0; i < columns.size(); i++) {
                JSONObject jsonCol = (JSONObject) columns.get(i);
                ColumnConfiguration thisCol = (ColumnConfiguration)JSONMapper.toJava(jsonCol, ColumnConfiguration.class);
                cm.addColumn(thisCol);
            }

            return cm;
        }


        public Class getHelpedClass() {
            return ColumnModel.class;
        }
    }

    private static boolean getBooleanValue(JSONValue boolean1) {
        if (boolean1 == null || boolean1 instanceof JSONNull)
            return false;
        return ((JSONBoolean) boolean1).getValue();
    }

    private static String getStringValue(JSONValue string) {
        if (string == null || string instanceof JSONNull)
            return null;
        return ((JSONString) string).getValue();
    }

    private static int getIntegerValue(JSONValue integer) {
        if (integer == null || integer instanceof JSONNull)
            return -1;
        
        BigInteger bi = ((JSONInteger) integer).getValue();
        return (bi == null) ? -1 : bi.intValue();
    }
}