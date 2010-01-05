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
import org.sgodden.echo.ext20.grid.ColumnModel
import org.sgodden.echo.ext20.grid.ColumnConfiguration
import org.sgodden.echo.ext20.ComboBoximport nextapp.echo.app.list.ListModelimport nextapp.echo.app.list.DefaultListModel/**
 * Tests to ensure that a grid which is updated but not yet rendered does
 * not fall over and reconfigures itself on render.
 * @author sgodden
 *
 */
class ComboBoxModelUpdatePanel extends Panel {
	
	private ComboBox combo1
	private ComboBox combo2
    
    public ComboBoxModelUpdatePanel() {
        super();
        
        title = 'Combo box model update'
        
        components = [
            new Panel(
                html: '''Here's a classic case of two linked combo boxes.  When the value in the first is changed, we want a different
model in the second.
'''
            ),
            makeCombo1(),
            makeCombo2()
        ]
    }
	
	def makeCombo1() {
		DefaultListModel listModel = new DefaultListModel()
		listModel.add("Group 1")
		listModel.add("Group 2")
		
		combo1 = new ComboBox(listModel)
		combo1.actionPerformed = {
			combo2.setModel(makeCombo2Model(combo1.selectedItem))
		}
		
		return combo1
	}
	
	def makeCombo2() {
		combo2 = new ComboBox(new DefaultListModel())
		return combo2
	}
	
	def makeCombo2Model(selectedItem) {
		DefaultListModel ret = new DefaultListModel()
		(1..5).each {
			ret.add(selectedItem + ": item $it")
		}
		return ret
	}
    
}
