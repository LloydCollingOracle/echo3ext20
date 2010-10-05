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
package org.sgodden.echo.ext20.grid;

import java.io.Serializable;
import java.util.EventListener;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.app.event.EventListenerList;

import org.sgodden.echo.ext20.componentgrid.ComponentGridCellRenderer;

/**
 * Configuration of a column for a grid.
 * 
 * @author sgodden
 * 
 */
public class DefaultColumnConfiguration implements Serializable, ColumnConfiguration {

    private static final long serialVersionUID = 20080310L;

    private String attributePath;
    private int displaySequence;
    private int sortSequence;
    private String header;
    private Integer width;
    private boolean sortable = true;
    private String dataIndex;
    private boolean hidden = false;
    private boolean grouping = false;
    private String sortDirection;
    private EventListenerList listenerList = new EventListenerList();
    private Component editorComponent;
    private boolean menuDisabled = false;
    private ComponentGridCellRenderer gridCellRenderer;
    private String groupableValues;
    private String selectedGroupableValues;
    private Class<?> columnClass;

	/**
     * Default constructor.
     */
    public DefaultColumnConfiguration() {
    }

    /**
     * Constructs a new column configuration.
     * 
     * @param header
     *            the header text.
     * @param width
     *            a specific width for the column.
     * @param sortable
     *            whether the column is sortable.
     * @param dataIndex
     *            the id of the column in the store to which this column maps.
     */
    public DefaultColumnConfiguration(String header, Integer width, Boolean sortable,
            String dataIndex) {
        this(header, width, sortable, dataIndex, false);
    }

    /**
     * Constructs a new column configuration.
     * 
     * @param header
     *            the header text.
     * @param width
     *            a specific width for the column.
     * @param sortable
     *            whether the column is sortable.
     * @param dataIndex
     *            the id of the column in the store to which this column maps.
     * @param hidden
     *            whether the column is hidden.
     */
    public DefaultColumnConfiguration(String header, Integer width, Boolean sortable,
            String dataIndex, boolean hidden) {
    	this(header, width, sortable, dataIndex, hidden, Object.class);
    }

    /**
     * Constructs a new column configuration.
     * 
     * @param header
     *            the header text.
     * @param dataIndex
     *            the id of the column in the store to which this column maps.
     */
    public DefaultColumnConfiguration(String header, String dataIndex) {
        this(header, null, true, dataIndex);
    }

    /**
     * Constructs a new column configuration.
     * 
     * @param header
     *            the header text.
     * @param width
     *            a specific width for the column.
     * @param sortable
     *            whether the column is sortable.
     * @param dataIndex
     *            the id of the column in the store to which this column maps.
     * @param hidden
     *            whether the column is hidden.
     * @param columnClass
     *            the class of the column.
     */
    
    public DefaultColumnConfiguration(String header, Integer width, Boolean sortable,
            String dataIndex, boolean hidden, Class <?> columnClass) {
        super();
        this.header = header;
        this.width = width;
        this.sortable = sortable;
        this.dataIndex = dataIndex;
        this.hidden = hidden;
        this.columnClass = columnClass;
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getAttributePath()
     */
    public String getAttributePath() {
        return attributePath;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setSortDirection(java.lang.String)
     */
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getSortDirection()
     */
    public String getSortDirection() {
        return sortDirection;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setAttributePath(java.lang.String)
     */
    public void setAttributePath(String attributePath) {
        this.attributePath = attributePath;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getDisplaySequence()
     */
    public int getDisplaySequence() {
        return displaySequence;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setDisplaySequence(int)
     */
    public void setDisplaySequence(int displaySequence) {
        this.displaySequence = displaySequence;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getSortSequence()
     */
    public int getSortSequence() {
        return sortSequence;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setSortSequence(int)
     */
    public void setSortSequence(int sortSequence) {
        this.sortSequence = sortSequence;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getHeader()
     */
    public String getHeader() {
        return header;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setHeader(java.lang.String)
     */
    public void setHeader(String header) {
        this.header = header;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getWidth()
     */
    public Integer getWidth() {
        return width;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setWidth(java.lang.Integer)
     */
    public void setWidth(Integer width) {
        this.width = width;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getSortable()
     */
    public boolean getSortable() {
        return sortable;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setSortable(boolean)
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getDataIndex()
     */
    public String getDataIndex() {
        return dataIndex;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setDataIndex(java.lang.String)
     */
    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#getHidden()
     */
    public boolean getHidden() {
        return hidden;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#setHidden(boolean)
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#addChangeListener(nextapp.echo.app.event.ChangeListener)
     */
    public void addChangeListener(ChangeListener listener) {
        listenerList.addListener(ChangeListener.class, listener);
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnConfiguration#removeChangeListener(nextapp.echo.app.event.ChangeListener)
     */
    public void removeChangeListener(ChangeListener listener) {
        listenerList.removeListener(ChangeListener.class, listener);
    }

    protected void fireChangeEvent() {
        ChangeEvent ce = null;
        for (EventListener l : listenerList.getListeners(ChangeListener.class)) {
            if (ce == null)
                ce = new ChangeEvent(this);
            ((ChangeListener) l).stateChanged(ce);
        }
    }

    public boolean getGrouping() {
        return grouping;
    }

    public void setGrouping(boolean grouping) {
        this.grouping = grouping;
    }

    public void setColumnClass(Class<?> columnClass) {
        this.columnClass = columnClass;
    }

    public Class<?> getColumnClass() {
        return columnClass;
    }

    public Component getEditorComponent() {
        return editorComponent;
    }

    public void setEditorComponent(Component c) {
        this.editorComponent = c;
    }

    public boolean isMenuDisabled() {
		return menuDisabled;
	}

	public void setMenuDisabled(boolean menuDisabled) {
		this.menuDisabled = menuDisabled;
	}

    public ComponentGridCellRenderer getGridCellRenderer() {
        return gridCellRenderer;
    }

    public void setGridCellRenderer(ComponentGridCellRenderer gridCellRenderer) {
        this.gridCellRenderer = gridCellRenderer;
    }
	
	public String getGroupableValues() {
		return this.groupableValues;
	}
	
	public String getSelectedGroupableValues() {
		return this.selectedGroupableValues;
	}

	public void setGroupableValues(String groupableValues) {
		this.groupableValues = groupableValues;
	}

	public void setSelectedGroupableValues(String selectedGroupableValues) {
		this.selectedGroupableValues = selectedGroupableValues;
	}

}
