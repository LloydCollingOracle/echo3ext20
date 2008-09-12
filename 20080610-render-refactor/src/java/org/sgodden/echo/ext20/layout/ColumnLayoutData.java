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
package org.sgodden.echo.ext20.layout;

import nextapp.echo.app.LayoutData;

/**
 * Layout data for use within a column layout.
 * 
 * @author goddens
 *
 */
public class ColumnLayoutData
        implements LayoutData {

    private double columnWidth;

    /**
     * Creates a new column layout data object, the specified column width.
     * <p/>
     * The total value of columnWidth for all children added to a column
     * layout must equal 1.
     * @param columnWidth
     */
    public ColumnLayoutData(double columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * Returns the column width.
     * @return the column width.
     */
    public double getColumnWidth() {
        return columnWidth;
    }

}