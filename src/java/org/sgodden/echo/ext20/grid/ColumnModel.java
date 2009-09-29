package org.sgodden.echo.ext20.grid;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import nextapp.echo.app.event.ChangeListener;

/**
 * Representation of a Grid's Column Model
 * @author Lloyd Colling
 *
 */
public interface ColumnModel extends Iterable<ColumnConfiguration>, Serializable{

    /**
     * Adds a column configuration.
     * @param column the column to add.
     */
    public void addColumn(ColumnConfiguration column);

    /**
     * Removes the columns at the specified index
     * @param index
     */
    public void removeColumn(int index);

    /**
     * Removes the specified column
     * @param col
     */
    public void removeColumn(ColumnConfiguration col);

    /**
     * Returns the column at the specified index
     * @param index
     * @return
     */
    public ColumnConfiguration getColumn(int index);

    /**
     * Returns the number of columns in the model
     * @return
     */
    public int getColumnCount();

    /**
     * Sets the column configurations.
     * @param columns the column configurations.
     */
    public void setColumns(List<ColumnConfiguration> columns);

    /**
     * Returns whether columns are sortable by default.
     * @return whether columns are sortable by default.
     */
    public boolean isDefaultSortable();

    /**
     * Sets whether columns are sortable by default.
     * @param defaultSortable whether columns are sortable by default.
     */
    public void setDefaultSortable(boolean defaultSortable);

    /**
     * Returns the default width of a column.
     * @return the default width of a column.
     */
    public Integer getDefaultWidth();

    /**
     * Sets the default width of a column.
     * @param defaultWidth the default width of a column.
     */
    public void setDefaultWidth(Integer defaultWidth);

    /**
     * Returns the index of the column having the specified data
     * index (column name).
     * @param dataIndex the data index (column name) of the column.
     * @return the index of the column having that header.
     */
    public int getIndexForDataIndex(String dataIndex);

    /**
     * Adds a listener for changes to this column model
     * @param listener
     */
    public void addChangeListener(ChangeListener listener);

    /**
     * Removes listener for changes to this column model
     * @param listener
     */
    public void removeChangeListener(ChangeListener listener);

    /**
     * Returns an iterator over this model's columns
     */
    public Iterator<ColumnConfiguration> iterator();
    
    /**
     * Used only for converting a column model into it's JSON representation.
     * DO NOT USE!
     * @return
     * @deprecated
     */
    public List<ColumnConfiguration> getColumns();

}