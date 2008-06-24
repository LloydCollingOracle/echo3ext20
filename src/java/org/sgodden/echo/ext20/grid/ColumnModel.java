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
import java.util.List;

/**
 * An ext column model.
 * 
 * @author sgodden
 *
 */
public class ColumnModel 
		implements Serializable {

	private static final long serialVersionUID = 20080310L;
	
	private List<ColumnConfiguration> columns;
	private boolean defaultSortable = true;
	private Integer defaultWidth;

	/**
	 * Constructs a new column model using the specified
	 * column configurations.
	 * @param columns
	 */
	public ColumnModel(List<ColumnConfiguration> columns) {
		super();
		this.columns = columns;
	}

	/**
	 * Returns the column configurations.
	 * @return the column configurations.
	 */
	public List<ColumnConfiguration> getColumns() {
		return columns;
	}

	/**
	 * Sets the column configurations.
	 * @param columns the column configurations.
	 */
	public void setColumns(List<ColumnConfiguration> columns) {
		this.columns = columns;
	}

	/**
	 * Returns whether columns are sortable by default.
	 * @return whether columns are sortable by default.
	 */
	public boolean isDefaultSortable() {
		return defaultSortable;
	}

	/**
	 * Sets whether columns are sortable by default.
	 * @param defaultSortable whether columns are sortable by default.
	 */
	public void setDefaultSortable(boolean defaultSortable) {
		this.defaultSortable = defaultSortable;
	}

	/**
	 * Returns the default width of a column.
	 * @return the default width of a column.
	 */
	public Integer getDefaultWidth() {
		return defaultWidth;
	}

	/**
	 * Sets the default width of a column.
	 * @param defaultWidth the default width of a column.
	 */
	public void setDefaultWidth(Integer defaultWidth) {
		this.defaultWidth = defaultWidth;
	}
    
    /**
     * Returns the index of the column having the specified data
     * index (column name).
     * @param dataIndex the data index (column name) of the column.
     * @return the index of the column having that header.
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
	
}
