package org.sgodden.echo.ext20.peers.grid;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo.app.Component;
import nextapp.echo.app.table.EditableTableModel;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;

import org.sgodden.echo.ext20.data.TableModelAdapter;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.DefaultColumnModel;
import org.sgodden.echo.ext20.grid.GridPanel;
import org.sgodden.echo.ext20.grid.RemoveColumnEvent;
import org.sgodden.echo.ext20.util.ListSelectionUtil;

@SuppressWarnings( { "unchecked" })
public class GridPanelPeer extends AbstractComponentSynchronizePeer {

//    protected static final Service GRID_SERVICE = JavaScriptService
//            .forResource("EchoExt20.GridPanel",
//                    "org/sgodden/echo/ext20/resource/js/Ext20.GridPanel.js");

    private static final String PROPERTY_SELECTION = "selection";
    private static final String PROPERTY_COLUMN_MODEL = "columnModel";

    public GridPanelPeer() {
        super();
        addOutputProperty(PROPERTY_SELECTION);
        addOutputProperty(GridPanel.PROPERTY_PAGE_OFFSET); // FIXME - why do we
        // have to manually
        // add the output
        // property?
        addOutputProperty(GridPanel.PROPERTY_SET_SIZE_COLUMNS_TO_GRID);
        addOutputProperty(PROPERTY_COLUMN_MODEL);
        addOutputProperty(GridPanel.PROPERTY_MODEL);
        addOutputProperty(GridPanel.PROPERTY_STRIPE_ROWS);
        
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                GridPanel.INPUT_ACTION,
                GridPanel.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((GridPanel) component).hasActionListeners();
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                GridPanel.SORT_ACTION, "sortListeners") {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((GridPanel) component).isModelSortable());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                GridPanel.SELECT_ACTION, "selectListeners") {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((GridPanel) component).isNotifySelect());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                GridPanel.COLUMN_ADDED, GridPanel.COLUMN_LISTENERS) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((GridPanel) component).hasColumnListeners());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                GridPanel.COLUMN_REMOVED, GridPanel.COLUMN_LISTENERS,
                RemoveColumnEvent.class) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((GridPanel) component).hasColumnListeners());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                GridPanel.GROUP_ACTION, "groupListeners") {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((GridPanel) component).isModelSortable());
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getComponentClass
     * ()
     */
    @Override
    public Class getComponentClass() {
        return GridPanel.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType
     * (boolean)
     */
    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2GP" : "Echo20GridPanel";
    }

    /*
     * (non-Javadoc)
     * 
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#
     * getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (PROPERTY_SELECTION.equals(propertyName)) {
            return String.class;
        }
        if (GridPanel.PROPERTY_SORT_FIELD.equals(propertyName)) {
            return String.class;
        }
        if (GridPanel.PROPERTY_SORT_ORDER.equals(propertyName)) {
            return String.class;
        }
        if (GridPanel.PROPERTY_COLUMN_MODEL.equals(propertyName)) {
            return DefaultColumnModel.class;
        }
        if (GridPanel.PROPERTY_MODEL.equals(propertyName)) {
            return TableModelAdapter.class;
        }
        return super.getInputPropertyClass(propertyName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getOutputProperty
     * (nextapp.echo.app.util.Context, nextapp.echo.app.Component,
     * java.lang.String, int)
     */
    @Override
    public Object getOutputProperty(Context context, Component component,
            String propertyName, int propertyIndex) {
        GridPanel gridPanel = (GridPanel) component;
        if (PROPERTY_SELECTION.equals(propertyName)) {
            return ListSelectionUtil.toString(gridPanel.getSelectionModel(),
                    gridPanel.getModel().getRowCount());
        }
        if (GridPanel.PROPERTY_MODEL.equals(propertyName)) {
            return new TableModelAdapter(gridPanel);
        }
        return super.getOutputProperty(context, component, propertyName,
                propertyIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty
     * (nextapp.echo.app.util.Context, nextapp.echo.app.Component,
     * java.lang.String, int, java.lang.Object)
     */
    @Override
    public void storeInputProperty(Context context, Component component,
            String propertyName, int index, Object newValue) {
        ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context
                .get(ClientUpdateManager.class);
        if (PROPERTY_SELECTION.equals(propertyName)) {
            int[] selection = ListSelectionUtil.toIntArray((String) newValue);
            clientUpdateManager.setComponentProperty(component,
            		PROPERTY_SELECTION, selection);
        } else if (GridPanel.PROPERTY_SORT_FIELD.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component,
                    GridPanel.PROPERTY_SORT_FIELD, (String) newValue);
        } else if (GridPanel.PROPERTY_SORT_ORDER.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component,
                    GridPanel.PROPERTY_SORT_ORDER, (String) newValue);
        } else if (GridPanel.PROPERTY_COLUMN_MODEL.equals(propertyName)) {

            ColumnModel serverModel = ((GridPanel) component).getColumnModel();
            ColumnModel clientModel = (ColumnModel) newValue;
            
            GridPanel p = (GridPanel)component;
            boolean ignoreFirstCol = false;

            int x = ignoreFirstCol ? 1 : 0;
            
            for (;x < clientModel.getColumnCount(); x++) {
                ColumnConfiguration clientColumn = clientModel.getColumn(x);
                ColumnConfiguration serverColumn = serverModel.getColumn(ignoreFirstCol ? x - 1 : x);

                serverColumn.setAttributePath(clientColumn.getAttributePath());
                serverColumn.setHeader(clientColumn.getHeader());
                serverColumn.setDataIndex(clientColumn.getDataIndex());
                serverColumn.setDisplaySequence(ignoreFirstCol ? x - 1 : x);
                serverColumn.setHidden(clientColumn.getHidden());
                serverColumn.setSortDirection(clientColumn.getSortDirection());
                serverColumn.setSortSequence(ignoreFirstCol ? x - 1 : x);
                serverColumn.setWidth(clientColumn.getWidth());
                serverColumn.setGrouping(clientColumn.getGrouping());
            }
            clientUpdateManager.setComponentProperty(component,
                    GridPanel.PROPERTY_COLUMN_MODEL, serverModel);
        } else if (GridPanel.PROPERTY_MODEL.equals(propertyName)) {
            TableModelAdapter tma = (TableModelAdapter)newValue;
            GridPanel p = (GridPanel)component;
            EditableTableModel currentModel = (EditableTableModel)p.getModel();
            ColumnModel cm = p.getColumnModel();
            
            int offset = p.getPageOffset();
            
            for (int row = 0; row < tma.getData().length; row++) {
                for (int col = 0; col < tma.getData()[row].length; col++) {
                    String renderedValue = p.getGridCellRenderer().getModelValue(p, currentModel.getValueAt(col, row + offset), col, row + offset);
                    Object newTMValue = tma.getData()[row][col];
                    if (renderedValue == null && newTMValue == null) {
                        // value has not changed
                    } else if (renderedValue != null && newTMValue == null) {
                        currentModel.setValueAt(null, col, row + offset);
                    } else if (renderedValue == null && newTMValue != null) {
                        currentModel.setValueAt(convertToType(newTMValue, cm.getColumn(col).getColumnClass()), col, row + offset);
                    } else {
                        if (!renderedValue.equals(newTMValue))
                            currentModel.setValueAt(convertToType(newTMValue, cm.getColumn(col).getColumnClass()), col, row + offset);
                    }
                }
            }
        }
    }
    
    private Object convertToType(Object value, Class type) {
        if (type.isAssignableFrom(value.getClass()))
                return value;
        
        if (Object.class.equals(type))
            return value;
            
        if (String.class.equals(type)) {
            return String.valueOf(value);
        } else if (Boolean.class.equals(type)) {
            if (value instanceof String)
                return Boolean.valueOf((String)value);
        } else if (Number.class.isAssignableFrom(type)) {
            if (value instanceof String) {
                try {
                    return type.getMethod("valueOf", new Class[] {String.class}).invoke(null, value);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Can't convert value to Number", e);
                } catch (SecurityException e) {
                    throw new IllegalArgumentException("Can't convert value to Number", e);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Can't convert value to Number", e);
                } catch (InvocationTargetException e) {
                    throw new IllegalArgumentException("Can't convert value to Number", e);
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException("Can't convert value to Number", e);
                }
            }
        } else if ( Date.class.equals( type)) {
        	if ( value instanceof String) {
        		try {
        			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss");
					return formatter.parse( (String) value);
				} catch (ParseException e) {
                    throw new IllegalArgumentException("Can't convert " + value + " to Date", e);
				}
        	}
        }
        	
        
        throw new IllegalArgumentException("I can't convert " + value + " to " + type.getCanonicalName());
    }

}