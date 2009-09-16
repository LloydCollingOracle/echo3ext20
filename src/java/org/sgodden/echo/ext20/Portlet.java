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
package org.sgodden.echo.ext20;

/**
 * A portlet, which is essentially a panel that can be added to a portal column, 
 * and which can then be dragged around, collapsed, or closed.
 * 
 * @author sgodden
 * @see Portal
 * @see PortalColumn
 */
@SuppressWarnings({"serial"})
public class Portlet 
        extends Panel {
    
    /**
     * The column in which the portlet resides.
     */
    public static final String PROPERTY_COLUMN = "column";
    /**
     * The row in which the portlet resides.
     */
    public static final String PROPERTY_ROW = "row";
    
    public Portlet(){
        super();
    }
    
    public Integer getColumn() {
        return (Integer)get(PROPERTY_COLUMN);
    }
    
    public void setColumn(Integer column) {
        set(PROPERTY_COLUMN, column);
    }
    
    public Integer getRow() {
        return (Integer)get(PROPERTY_ROW);
    }
    
    public void setRow(Integer row) {
        set(PROPERTY_ROW, row);
    }
    
    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (PROPERTY_COLUMN.equals(inputName)) {
            setColumn((Integer) inputValue);
        }
        else if (PROPERTY_ROW.equals(inputName)) {
            setRow((Integer) inputValue);
        }
    }

}
