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
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import nextapp.echo.app.event.EventListenerList;

/**
 * An ext column model.
 * 
 * @author sgodden
 *
 */
public class DefaultColumnModel 
        implements Serializable, ChangeListener, Iterable<ColumnConfiguration>, ColumnModel {

    private static final long serialVersionUID = 20080310L;
    
    private List<ColumnConfiguration> columns = new ArrayList<ColumnConfiguration>();
    private boolean defaultSortable = true;
    private Integer defaultWidth;
    private EventListenerList listenerList = new EventListenerList();
    
    /**
     * Constructs a new empty column model.
     */
    public DefaultColumnModel(){
        setDefaultWidth(Integer.valueOf(100));
    }

    /**
     * Constructs a new column model using the specified
     * column configurations.
     * @param columns
     */
    public DefaultColumnModel(List<ColumnConfiguration> columns) {
        super();
        setDefaultWidth(Integer.valueOf(100));
        this.columns.addAll(columns);
        for (ColumnConfiguration c : columns) {
            c.addChangeListener(this);
        }
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#addColumn(org.sgodden.echo.ext20.grid.ColumnConfiguration)
     */
    public void addColumn(ColumnConfiguration column) {
        if (columns == null) {
            columns = new ArrayList < ColumnConfiguration >();
        }
        columns.add(column);
        column.addChangeListener(this);
        fireChangeEvent();
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#removeColumn(int)
     */
    public void removeColumn(int index) {
        ColumnConfiguration c = columns.get(index);
        columns.remove(index);
        c.removeChangeListener(this);
        fireChangeEvent();
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#removeColumn(org.sgodden.echo.ext20.grid.ColumnConfiguration)
     */
    public void removeColumn(ColumnConfiguration col) {
        columns.remove(col);
        col.removeChangeListener(this);
        fireChangeEvent();
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#getColumn(int)
     */
    public ColumnConfiguration getColumn(int index) {
        return columns.get(index);
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#getColumnCount()
     */
    public int getColumnCount() {
        return columns.size();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#setColumns(java.util.List)
     */
    public void setColumns(List<ColumnConfiguration> columns) {
        for (ColumnConfiguration c : this.columns)
            c.removeChangeListener(this);
        this.columns.clear();
        this.columns.addAll(columns);
        for (ColumnConfiguration c : columns) {
            c.addChangeListener(this);
        }
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#isDefaultSortable()
     */
    public boolean isDefaultSortable() {
        return defaultSortable;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#setDefaultSortable(boolean)
     */
    public void setDefaultSortable(boolean defaultSortable) {
        this.defaultSortable = defaultSortable;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#getDefaultWidth()
     */
    public Integer getDefaultWidth() {
        return defaultWidth;
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#setDefaultWidth(java.lang.Integer)
     */
    public void setDefaultWidth(Integer defaultWidth) {
        this.defaultWidth = defaultWidth;
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#getIndexForDataIndex(java.lang.String)
     */
    public int getIndexForDataIndex(String dataIndex) {
        int ret = -1;
        for (int i = 0; i < columns.size(); i++) {
            ColumnConfiguration col = columns.get(i);
            if (dataIndex.equals(col.getDataIndex())) {
                ret = i;
                break;
            }
        }
        
        if (ret == -1) {
            throw new IllegalArgumentException("Unknown column: " + dataIndex);
        }
        
        return ret;
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#addChangeListener(nextapp.echo.app.event.ChangeListener)
     */
    public void addChangeListener(ChangeListener listener) {
        listenerList.addListener(ChangeListener.class, listener);
    }
    
    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#removeChangeListener(nextapp.echo.app.event.ChangeListener)
     */
    public void removeChangeListener(ChangeListener listener) {
        listenerList.removeListener(ChangeListener.class, listener);
    }
    
    protected void fireChangeEvent() {
        ChangeEvent ce = null;
        for (EventListener l : listenerList.getListeners(ChangeListener.class)) {
            if (ce == null)
                ce = new ChangeEvent(this);
            ((ChangeListener)l).stateChanged(ce);
        }
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#stateChanged(nextapp.echo.app.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent arg0) {
        fireChangeEvent();
    }

    /* (non-Javadoc)
     * @see org.sgodden.echo.ext20.grid.ColumnModel#iterator()
     */
    public Iterator<ColumnConfiguration> iterator() {
        return columns.iterator();
    }
    
    /**
     * This method is only used for JSON compatibility with the Ext20
     * Column Model
     * @return
     * @deprecated
     */
    public List<ColumnConfiguration> getColumns() {
        List<ColumnConfiguration> copyList = new ArrayList<ColumnConfiguration>(columns.size());
        copyList.addAll(columns);
        return copyList;
    }
}
