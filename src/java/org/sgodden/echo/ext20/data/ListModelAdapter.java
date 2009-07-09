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
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.sgodden.echo.ext20.AbstractListComponent;
import org.sgodden.echo.ext20.ListImageCellRenderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.list.ListCellRenderer;
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
    ListModel model;
    
    public ListModelAdapter(){}
    
    /**
     * Constructs a new list model adapter.
     * @param model the model from which to take the data.
     */
    public ListModelAdapter(AbstractListComponent component) {
        this.model = component.getModel();
        ListCellRenderer cellRenderer = component.getCellRenderer();
        boolean isImageCellRenderer = cellRenderer instanceof ListImageCellRenderer;
        
        int rows = model.size();

        data = new Object[rows][isImageCellRenderer ? 3 : 2];

        for (int i = 0; i < rows; i++) {
            Object[] row = data[i];
            row[0] = cellRenderer.getListCellRendererComponent((Component)component, model.get(i), i);
            row[1] = i;
            if (isImageCellRenderer)
                row[2] = ((ListImageCellRenderer)cellRenderer).getImageLocation((Component)component, model.get(i), i);
        }
        
        if (isImageCellRenderer)
            fields = new String[]{"display","value", "icon"};
        else
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

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof ListModelAdapter))
            return false;
        
        if (this == other)
            return true;

        ListModelAdapter o = (ListModelAdapter)other;
        
        if (this.model.equals(o.model))
            return true;
        
        String[] fields = o.getFields();
        if (!Arrays.equals(this.fields, fields)) {
            return false;
        }
        
        Object[][] otherData = o.getData();
        
        return ArrayUtils.isEquals(otherData, this.getData());
    }
    
    @Override
    public int hashCode() {
        return ArrayUtils.hashCode(fields) + ArrayUtils.hashCode(data);
    }
}