package org.sgodden.echo.ext20.peers.grid;

import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.service.JavaScriptService;

import org.sgodden.echo.ext20.data.TableModelAdapter;
import org.sgodden.echo.ext20.grid.ColumnConfiguration;
import org.sgodden.echo.ext20.grid.ColumnModel;
import org.sgodden.echo.ext20.grid.GridPanel;
import org.sgodden.echo.ext20.util.ListSelectionUtil;

@SuppressWarnings( { "unchecked" })
public class GridPanelPeer extends AbstractComponentSynchronizePeer {

	protected static final Service GRID_SERVICE = JavaScriptService
			.forResource("EchoExt20.GridPanel",
					"org/sgodden/echo/ext20/resource/js/Ext20.GridPanel.js");

	private static final String PROPERTY_SELECTION = "selection";
	private static final String PROPERTY_MODEL = "model";
	private static final String PROPERTY_COLUMN_MODEL = "columnModel";

	public GridPanelPeer() {
		super();
		addOutputProperty(PROPERTY_SELECTION);
		addOutputProperty(PROPERTY_MODEL);
		addOutputProperty(GridPanel.PAGE_OFFSET_PROPERTY); // FIXME - why do we
															// have to manually
															// add the output
															// property?
		addOutputProperty(GridPanel.SET_SIZE_COLUMNS_TO_GRID_PROPERTY);
		addOutputProperty(PROPERTY_COLUMN_MODEL);

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
	 * @seenextapp.echo.webcontainer.AbstractComponentSynchronizePeer#
	 * getInputPropertyClass(java.lang.String)
	 */
	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (PROPERTY_SELECTION.equals(propertyName)) {
			return String.class;
		}
		if (GridPanel.SORT_FIELD_PROPERTY.equals(propertyName)) {
			return String.class;
		}
		if (GridPanel.SORT_ORDER_PROPERTY.equals(propertyName)) {
			return String.class;
		}
		if (GridPanel.COLUMN_MODEL_PROPERTY.equals(propertyName)) {
			return ColumnModel.class;
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
					gridPanel.getTableModel().getRowCount());
		}
		if (PROPERTY_MODEL.equals(propertyName)) {
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
					Table.SELECTION_CHANGED_PROPERTY, selection);
		} else if (GridPanel.SORT_FIELD_PROPERTY.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component,
					GridPanel.SORT_FIELD_PROPERTY, (String) newValue);
		} else if (GridPanel.SORT_ORDER_PROPERTY.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component,
					GridPanel.SORT_ORDER_PROPERTY, (String) newValue);
		}
		if (GridPanel.COLUMN_MODEL_PROPERTY.equals(propertyName)) {

			ColumnModel serverModel = ((GridPanel) component).getColumnModel();
			ColumnModel clientModel = (ColumnModel) newValue;

			for (int x = 0; x < clientModel.getColumns().size(); x++) {
				ColumnConfiguration clientColumn = clientModel.getColumns()
						.get(x);
				ColumnConfiguration serverColumn = serverModel.getColumns()
						.get(x);

				serverColumn.setAttributePath(clientColumn.getAttributePath());
				serverColumn.setHeader(clientColumn.getHeader());
				serverColumn.setDataIndex(clientColumn.getDataIndex());
				serverColumn.setDisplaySequence(clientColumn
						.getDisplaySequence());
				serverColumn.setHidden(clientColumn.getHidden());
				serverColumn.setSortDirection(clientColumn.getSortDirection());
				serverColumn.setSortSequence(x);
				serverColumn.setWidth(clientColumn.getWidth());
			}
			clientUpdateManager.setComponentProperty(component,
					GridPanel.COLUMN_MODEL_PROPERTY, serverModel);
		}
	}

}