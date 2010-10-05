package org.sgodden.echo.ext20.grid;

import org.sgodden.echo.ext20.componentgrid.ComponentGridCellRenderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ChangeListener;

public interface ColumnConfiguration {

	/**
	 * Returns the attribute path.
	 * 
	 * @return the attribute path.
	 */
	public abstract String getAttributePath();

	/**
	 * Sets the attribute path.
	 * 
	 * @param attributePath
	 *            the attribute path.
	 */
	public abstract void setSortDirection(String sortDirection);

	/**
	 * Returns the sort direction.
	 * 
	 * @return the sort direction.
	 */
	public abstract String getSortDirection();

	/**
	 * Sets the sort direction.
	 * 
	 * @param attributePath
	 *            the sort direction.
	 */
	public abstract void setAttributePath(String attributePath);

	/**
	 * Returns the display sequence.
	 * 
	 * @return the display sequence.
	 */
	public abstract int getDisplaySequence();

	/**
	 * Sets the display sequence.
	 * 
	 * @param displaySequence
	 *            the display sequence.
	 */
	public abstract void setDisplaySequence(int displaySequence);

	/**
	 * Returns the sort sequence.
	 * 
	 * @return the sort sequence.
	 */
	public abstract int getSortSequence();

	/**
	 * Sets the sort sequence.
	 * 
	 * @param sortSequence
	 *            the sort sequence.
	 */
	public abstract void setSortSequence(int sortSequence);

	/**
	 * Returns the header text.
	 * 
	 * @return the header text.
	 */
	public abstract String getHeader();

	/**
	 * Sets the header text.
	 * 
	 * @param header
	 *            the header text.
	 */
	public abstract void setHeader(String header);

	/**
	 * Returns the width.
	 * 
	 * @return the width.
	 */
	public abstract Integer getWidth();

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            the width.
	 */
	public abstract void setWidth(Integer width);

	/**
	 * Returns whether this column is sortable.
	 * 
	 * @return whether this column is sortable.
	 */
	public abstract boolean getSortable();

	/**
	 * Sets whether this column is sortable.
	 * 
	 * @param sortable
	 *            whether this column is sortable.
	 */
	public abstract void setSortable(boolean sortable);

	/**
	 * Returns the index of the data column from which data should be taken for
	 * this column.
	 * 
	 * @return the data column index.
	 */
	public abstract String getDataIndex();

	/**
	 * Sets the index of the data column from which data should be taken for
	 * this column.
	 * 
	 * @param dataIndex
	 *            the data column index.
	 */
	public abstract void setDataIndex(String dataIndex);

	/**
	 * Returns whether this column should be hidden.
	 * 
	 * @return whether this column should be hidden.
	 */
	public abstract boolean getHidden();

	/**
	 * Sets whether this column should be hidden.
	 * 
	 * @param hidden
	 *            whether this column should be hidden.
	 */
	public abstract void setHidden(boolean hidden);

	public abstract void addChangeListener(ChangeListener listener);

	public abstract void removeChangeListener(ChangeListener listener);

	/**
	 * Whether this column is grouping the table
	 * 
	 * @return
	 */
	public abstract boolean getGrouping();

	public abstract void setGrouping(boolean isGrouping);

	/**
	 * The type of value in the column (return Object.class if not sure)
	 * 
	 * @return
	 */
	public Class<?> getColumnClass();

	public void setColumnClass(Class<?> columnClass);

	/**
	 * Sets the editor component to use for editing this column.
	 * 
	 * Please note that only specific components may be used as editors for
	 * columns, so setting this is any random component is likely to end in
	 * tears.
	 * 
	 * @param c
	 */
	public void setEditorComponent(Component c);

	/**
	 * Gets the editor component to use for editing this column.
	 * 
	 * @return
	 */
	public Component getEditorComponent();

	/**
	 * Returns whether the column menu is disabled for this column.
	 * 
	 * @return whether the column menu is disabled for this column.
	 */
	public boolean isMenuDisabled();

	/**
	 * Sets whether the column menu is disabled for this column.
	 * 
	 * @param disabled
	 *            whether the column menu is disabled for this column.
	 */
	public void setMenuDisabled(boolean disabled);

	/**
	 * Returns the renderer for this column, or null if it should use the
	 * default renderer from the ComponentGridPanel.
	 * 
	 * @return
	 */
	public ComponentGridCellRenderer getGridCellRenderer();

	/**
	 * Returns the values which can be grouped to be used to generate a grouping
	 * menu within ext js
	 */
	public abstract String getGroupableValues();
	
	/**
	 * Returns the values which can be grouped to be used to generate a grouping
	 * menu within ext js
	 */
	public abstract void setGroupableValues(String groupableValues);

	/**
	 * Returns the values which have been selected and will be used to generate
	 * a grouping menu within ext js
	 */
	public abstract String getSelectedGroupableValues();
	
	/**
	 * Returns the values which have been selected and will be used to generate
	 * a grouping menu within ext js
	 */
	public abstract void setSelectedGroupableValues(String selectedGroupableValues);
	
	

}