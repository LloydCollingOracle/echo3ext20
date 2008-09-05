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

import nextapp.echo.app.table.TableModel;

/**
 * Adapts a swing {@link TableModel} to an ext {@link SimpleStore}.
 * @author sgodden
 *
 */
public class TableModelAdapter 
		implements SimpleStore, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Object[][] data;
	private Integer id;
	private String[] fields;
	
	public TableModelAdapter(){}
	
	/**
	 * Constructs a new table model adapter.
	 * @param tableModel the swing table model from which to take the data.
	 */
	public TableModelAdapter(TableModel tableModel) {
		data = new Object[tableModel.getRowCount()][tableModel.getColumnCount()];
		
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Object[] row = data[i];
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				row[j] = tableModel.getValueAt(i, j);
			}
		}
		
		makeFields(tableModel);
	}

	/**
	 * Constructs a new table model adapter, to return the indicated subset
     * (page) of the passed table model.
	 * @param tableModel the swing table model from which to take the data.
     * @param offset the offset to the first row to read from the table model.
     * @param limit the number of rows to read from the table model.
	 */
	public TableModelAdapter(
            TableModel tableModel,
            int offset,
            int limit) {

        int rows = offset + limit < tableModel.getRowCount()
                ? limit : tableModel.getRowCount() - offset;

		data = new Object[rows][tableModel.getColumnCount()];

		for (int i = 0; i < rows; i++) {
			Object[] row = data[i];
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				row[j] = tableModel.getValueAt(j, offset + i);
			}
		}

        makeFields(tableModel);
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

    /**
     * Extracts the field (column) information from the passed table model.
     * @param tableModel the table model.
     */
    private void makeFields(TableModel tableModel) {
		fields = new String[tableModel.getColumnCount()];
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			fields[i] = tableModel.getColumnName(i);
		}
    }

}