package org.sgodden.echo.ext20.peers.componentgrid;

import nextapp.echo.app.Component;
import nextapp.echo.app.table.EditableTableModel;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;

import org.sgodden.echo.ext20.componentgrid.ComponentGridPanel;
import org.sgodden.echo.ext20.data.TableModelAdapter;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.DefaultColumnModel;
import org.sgodden.echo.ext20.grid.RemoveColumnEvent;
import org.sgodden.echo.ext20.util.ListSelectionUtil;

@SuppressWarnings( { "unchecked" })
public class ComponentGridPanelPeer extends AbstractComponentSynchronizePeer {

    private static final String PROPERTY_SELECTION = "selection";
    private static final String PROPERTY_COLUMN_MODEL = "columnModel";

    public ComponentGridPanelPeer() {
        super();
        addOutputProperty(PROPERTY_SELECTION);
        addOutputProperty(ComponentGridPanel.PAGE_OFFSET_PROPERTY); // FIXME - why do we
        // have to manually
        // add the output
        // property?
        addOutputProperty(ComponentGridPanel.SET_SIZE_COLUMNS_TO_GRID_PROPERTY);
        addOutputProperty(PROPERTY_COLUMN_MODEL);
        addOutputProperty(ComponentGridPanel.PROPERTY_MODEL);
        addOutputProperty(ComponentGridPanel.ROW_COUNT_PROPERTY);
        
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                ComponentGridPanel.INPUT_ACTION,
                ComponentGridPanel.ACTION_LISTENERS_CHANGED_PROPERTY) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return ((ComponentGridPanel) component).hasActionListeners();
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                ComponentGridPanel.SORT_ACTION, "sortListeners") {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((ComponentGridPanel) component).isModelSortable());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                ComponentGridPanel.SELECT_ACTION, "selectListeners") {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((ComponentGridPanel) component).isNotifySelect());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                ComponentGridPanel.COLUMN_ADDED, ComponentGridPanel.COLUMN_LISTENERS) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((ComponentGridPanel) component).hasColumnListeners());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                ComponentGridPanel.COLUMN_REMOVED, ComponentGridPanel.COLUMN_LISTENERS,
                RemoveColumnEvent.class) {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((ComponentGridPanel) component).hasColumnListeners());
            }
        });

        addEvent(new AbstractComponentSynchronizePeer.EventPeer(
                ComponentGridPanel.GROUP_ACTION, "groupListeners") {
            @Override
            public boolean hasListeners(Context context, Component component) {
                return (((ComponentGridPanel) component).isModelSortable());
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
        return ComponentGridPanel.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType
     * (boolean)
     */
    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2CGP" : "Echo20ComponentGridPanel";
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
        if (ComponentGridPanel.SORT_FIELD_PROPERTY.equals(propertyName)) {
            return String.class;
        }
        if (ComponentGridPanel.SORT_ORDER_PROPERTY.equals(propertyName)) {
            return String.class;
        }
        if (ComponentGridPanel.COLUMN_MODEL_PROPERTY.equals(propertyName)) {
            return DefaultColumnModel.class;
        }
        if (ComponentGridPanel.PROPERTY_MODEL.equals(propertyName)) {
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
        ComponentGridPanel gridPanel = (ComponentGridPanel) component;
        if (PROPERTY_SELECTION.equals(propertyName)) {
            return ListSelectionUtil.toString(gridPanel.getSelectionModel(),
                    gridPanel.getModel().getRowCount());
        }
        if (ComponentGridPanel.PROPERTY_MODEL.equals(propertyName)) {
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
        } else if (ComponentGridPanel.SORT_FIELD_PROPERTY.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component,
                    ComponentGridPanel.SORT_FIELD_PROPERTY, (String) newValue);
        } else if (ComponentGridPanel.SORT_ORDER_PROPERTY.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component,
                    ComponentGridPanel.SORT_ORDER_PROPERTY, (String) newValue);
        } else if (ComponentGridPanel.COLUMN_MODEL_PROPERTY.equals(propertyName)) {

            ColumnModel serverModel = ((ComponentGridPanel) component).getColumnModel();
            ColumnModel clientModel = (ColumnModel) newValue;
            
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
                    ComponentGridPanel.COLUMN_MODEL_PROPERTY, serverModel);
        }
    }

}