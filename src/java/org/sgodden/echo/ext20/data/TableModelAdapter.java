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

import org.sgodden.echo.ext20.grid.GridPanel;

/**
 * Adapts a swing {@link TableModel} to an ext {@link SimpleStore}.
 * @author sgodden
 *
 */
public class TableModelAdapter 
        implements SimpleStore, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Object[][] data;
    private Object[][] renderedData;
    private Integer id;
    private String[] fields;
    
    public TableModelAdapter(){}
    
    /**
     * Constructs a new table model adapter.
     */
    public TableModelAdapter(GridPanel gridPanel) {
        TableModel tableModel = gridPanel.getModel();
        int offset = gridPanel.getPageOffset();
        int limit = gridPanel.getPageSize();
        
        int rows = tableModel.getRowCount();
        if (gridPanel.getPageSize() > 0)
            rows = offset + limit < tableModel.getRowCount()
                    ? limit : tableModel.getRowCount() - offset;
        int cols = tableModel.getColumnCount();
        
        data = new String[rows][cols];
        renderedData = new String[rows][cols];

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            Object[] row = data[rowIndex];
            Object[] renderedRow = renderedData[rowIndex];
            for (int colIndex = 0; colIndex < tableModel.getColumnCount(); colIndex++) {
                row[colIndex] = gridPanel.getGridCellRenderer().getModelValue(gridPanel, tableModel.getValueAt(colIndex, rowIndex + offset), colIndex, rowIndex + offset);
                renderedRow[colIndex] = gridPanel.getGridCellRenderer().getClientSideValueRendererScript(gridPanel, tableModel.getValueAt(colIndex, rowIndex + offset), colIndex, rowIndex + offset);
            }
        }
        makeFields(tableModel);
    }

    /**
     * Returns the raw model data for use on the client side.
     */
    public Object[][] getData() {
        return data;
    }
    
    public void setData(Object[][] data) {
        this.data = data;
    }

    /**
     * Returns the rendered model data for use in presenting
     * the data on the client side.
     * @return
     */
    public Object[][] getRenderedData() {
        return renderedData;
    }
    
    public void setRenderedData(Object[][] data) {
        this.renderedData = data;
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