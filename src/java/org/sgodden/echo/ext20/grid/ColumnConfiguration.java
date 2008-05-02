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

/**
 * Configuration of a column for a grid.
 * 
 * @author sgodden
 *
 */
public class ColumnConfiguration 
		implements Serializable {

	private static final long serialVersionUID = 20080310L;
	
	private String header;
	private Integer width;
	private boolean sortable = true;
	private String dataIndex;
	
	/**
	 * Constructs a new column configuration.
	 * @param header the header text.
	 * @param width a specific width for the column.
	 * @param sortable whether the column is sortable.
	 * @param dataIndex the id of the column in the store to which this column maps.
	 */
	public ColumnConfiguration(String header, Integer width, Boolean sortable,
			String dataIndex) {
		super();
		this.header = header;
		this.width = width;
		this.sortable = sortable;
		this.dataIndex = dataIndex;
	}
	
	/**
	 * Constructs a new column configuration.
	 * @param header the header text.
	 * @param dataIndex the id of the column in the store to which this column maps.
	 */
	public ColumnConfiguration(String header, String dataIndex) {
		this(header, null, true, dataIndex);
	}

	/**
	 * Returns the header text.
	 * @return the header text.
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Sets the header text.
	 * @param header the header text.
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Returns the width.
	 * @return the width.
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * @param width the width.
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Returns whether this column is sortable. 
	 * @return whether this column is sortable.
	 */
	public boolean getSortable() {
		return sortable;
	}

	/**
	 * Sets whether this column is sortable.
	 * @param sortable whether this column is sortable.
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * Returns the index of the data column from which data
	 * should be taken for this column.
	 * @return the data column index.
	 */
	public String getDataIndex() {
		return dataIndex;
	}

	/**
	 * Sets the index of the data column from which data
	 * should be taken for this column.
	 * @param dataIndex the data column index.
	 */
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

}
