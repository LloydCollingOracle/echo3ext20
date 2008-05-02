package org.sgodden.echo.ext20.peers.grid;

import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.grid.GridPanel;
import org.sgodden.echo.ext20.util.ListSelectionUtil;

public class GridPanelPeer 
		extends AbstractComponentSynchronizePeer {
    
    protected static final Service GRID_SERVICE = JavaScriptService.forResource("EchoExt20.GridPanel", 
            "/org/sgodden/echo/ext20/resource/js/Ext20.GridPanel.js");
    
    private static final String PROPERTY_SELECTION = "selection";

    
    public GridPanelPeer() {
    	super();
        addOutputProperty(PROPERTY_SELECTION);
        addOutputProperty(GridPanel.SIMPLE_STORE_PROPERTY);
        
        addEvent(new AbstractComponentSynchronizePeer.EventPeer(GridPanel.INPUT_ACTION, GridPanel.ACTION_LISTENERS_CHANGED_PROPERTY) {
            public boolean hasListeners(Context context, Component component) {
                return ((GridPanel) component).hasActionListeners();
            }
        });
    }

	@Override
	public Class getComponentClass() {
		return GridPanel.class;
	}

	public String getClientComponentType(boolean shortType) {
        return shortType ? "E2GP" : "Echo20GridPanel";
	}
	

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getInputPropertyClass(String)
     */
    public Class getInputPropertyClass(String propertyName) {
        if (PROPERTY_SELECTION.equals(propertyName)) {
            return String.class;
        }
        return super.getInputPropertyClass(propertyName);
    }


    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getOutputProperty(
     *      nextapp.echo.app.util.Context, nextapp.echo.app.Component, java.lang.String, int)
     */
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
        GridPanel gridPanel = (GridPanel)component;
        if (PROPERTY_SELECTION.equals(propertyName)) {
            return ListSelectionUtil.toString(gridPanel.getSelectionModel(), gridPanel.getSimpleStore().getSize());
        }
        return super.getOutputProperty(context, component, propertyName, propertyIndex);
    }
    
    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context,
     *      nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
     */
    public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
        if (PROPERTY_SELECTION.equals(propertyName)) {
            int[] selection = ListSelectionUtil.toIntArray((String) newValue);
            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
            clientUpdateManager.setComponentProperty(component, Table.SELECTION_CHANGED_PROPERTY, selection);
        }
    }

}