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

import nextapp.echo.app.list.ListModel;

/**
 * Serializes an echo {@link ListModel} for
 * network transmission.
 * @author sgodden
 *
 */
public class ListModelAdapter
		implements SimpleStore, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Object[][] data;
	private Integer id;
	private String[] fields;
	
	public ListModelAdapter(){}
	
	/**
	 * Constructs a new list model adapter.
	 * @param model the model from which to take the data.
	 */
	public ListModelAdapter(ListModel model) {
        int rows = model.size();

		data = new Object[rows][2];

		for (int i = 0; i < rows; i++) {
			Object[] row = data[i];
    		row[0] = model.get(i);
            row[1] = i;
		}
		
		fields = new String[]{"display","value"};
	}

	/**
	 * Constructs an array of data from the underlying table model
	 * and returns it.
	 * <p/>
	 * Since this method creates a new array each time, don't call it
	 * more than you need to.
	 * 
	 * @return the newly created array of data.
	 */
	public Object[][] getData() {
		return data;
	}

    /**
     * Setter so that automatic JSON translation will work.
     * @param data the data.
     */
	public void setData(Object[][] data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sgodden.echo.ext20.data.SimpleStore#getFields()
	 */
	public String[] getFields() {
		return fields;
	}

    /**
     * Setter so that automatic JSON translation will work.
     * @param fields the fields.
     */
	public void setFields(String[] fields){
		this.fields = fields;
	}

	/**
	 * Returns null - it is up to the application to map selected rows
	 * to ids.
	 */
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sgodden.echo.ext20.data.SimpleStore#getSize()
	 */
	public int getSize() {
		return data.length;
	}

}