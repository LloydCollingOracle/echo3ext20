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
package org.sgodden.echo.ext20.data;

import java.io.Serializable;

/**
 * A simple store implementation, whose data is taken from arrays.
 * <p/>
 * TODO - decide whether to scrap this, and work from echo table models instead.
 * 
 * @author sgodden
 *
 */
public class SimpleStore 
		implements Serializable {
	
	private static final long serialVersionUID = 20080310L;
	
	private Object[][] data;
	private Integer id;
	private String[] fields;

	/**
	 * Creates a new SimpleStore.
	 */
	public SimpleStore() {
	}
	
	/**
	 * Creates a new SimpleStore, where each row does not have an identifier.
	 * @param data the store data.
	 * @param fieldNames the field names of each column.
	 */
	public SimpleStore(Object[][] data, String[] fieldNames) {
		super();
		this.data = data;
		this.fields = fieldNames;
	}
	
	/**
	 * Creates a new SimpleStore, where each row has an identifier.
	 * @param data the store data.
	 * @param idColumnIndex the index of the column giving the id of the row.
	 * @param fieldNames the field names of each column.
	 */
	public SimpleStore(Object[][] data, int idColumnIndex, String[] fieldNames) {
		super();
		this.data = data;
		this.id = idColumnIndex;
		this.fields = fieldNames;
	}

	/**
	 * Returns the store data.
	 * @return the store data.
	 */
	public Object[][] getData() {
		return data;
	}

	/**
	 * Sets the store data.
	 * @param data the store data.
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

	/**
	 * Returns the index of the column which provides the row identifier, or
	 * <code>null</code> if there isn't one. 
	 * @return the index of the column which provides the row identifier, or
	 * <code>null</code> if there isn't one.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the index of the column which provides the row identifier. 
	 * @param id the index of the column which provides the row identifier.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the array of field names, which map by their position to the columns
	 * in the store data.
	 * @return the array of field names.
	 */
	public String[] getFields() {
		return fields;
	}

	/**
	 * Sets the array of field names, which map by their position to the columns
	 * in the store data.
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	/**
	 * Returns the number of rows of data.
	 * @return the number of rows of data.
	 */
	public int getSize() {
		return data.length;
	}
	
}