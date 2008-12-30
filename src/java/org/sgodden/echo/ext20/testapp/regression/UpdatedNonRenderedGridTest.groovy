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
package org.sgodden.echo.ext20.testapp.regression
import org.sgodden.echo.ext20.TabbedPane
import org.sgodden.echo.ext20.Panel
import org.sgodden.echo.ext20.grid.GridPanel
import nextapp.echo.app.table.TableModel
import org.sgodden.ui.models.DefaultSortableTableModel
import org.sgodden.echo.ext20.grid.DefaultColumnModel
import org.sgodden.echo.ext20.grid.DefaultColumnConfiguration
/**
 * Tests to ensure that a grid which is updated but not yet rendered does
 * not fall over and reconfigures itself on render.
 * @author sgodden
 *
 */
class UpdatedNonRenderedGridTest extends TabbedPane {
    
    private int modelUpdateCount
    private GridPanel gridPanel
    
    public UpdatedNonRenderedGridTest() {
        super();
        
        components = [
            new Panel(
                title: "Panel 1",
                html: '''Tabs are lazy rendered by default, which improves client response.
However, this means that certain components may not have been rendered when they receive updates.
<br/><br/>
For instance, a grid may have its model updated before it has been rendered, causing Ext to
fall over.
<br/><br/>
Click on the button below to update the model of the grid on the second tab, which has not been
rendered on initial display of these tabs.  Nothing should fall over...''',
                buttons: [
                    [
                        text: "Press me",
                        actionPerformed: {
                            gridPanel.model = makeTableModel()
                        }
                    ]
                ]
            ),
            makeGridPanel()
        ]
        
    }
    
    private makeGridPanel() {
        gridPanel = new GridPanel(
            title: "Grid panel",
            columnModel: new DefaultColumnModel(
                columns: [
                    new DefaultColumnConfiguration (
                        header: "Column 1",
                        width: 200,
                        dataIndex: "col1"
                    ),
                    new DefaultColumnConfiguration (
                        header: "Column 2",
                        width: 200,
                        dataIndex: "col2"
                    )
                ]
            ),
            model: makeTableModel()
        );
        
        return gridPanel
    }
    
    private makeTableModel() {
        def content = "Update " + (++modelUpdateCount)
        Object[][] data = [[content,content],[content,content]]
        Object[] colNames = ["col1","col2"]
        TableModel ret = new DefaultSortableTableModel(
            data,
            colNames
        )
        return ret
    }
    
}
