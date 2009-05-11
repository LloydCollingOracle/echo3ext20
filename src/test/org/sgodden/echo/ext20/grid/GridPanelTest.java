package org.sgodden.echo.ext20.grid;

import nextapp.echo.app.table.DefaultTableModel;
import junit.framework.TestCase;

/**
 * Test suite for {@link GridPanel}.
 * @author sgodden
 *
 */
public class GridPanelTest extends TestCase {
	
	/**
	 * Test that when the grid panel's selection
	 * model is cleared, the {@link GridPanel#getSelectedIndices()}
	 * method returns no selections.
	 */
	public void testClearSelectionModel() {
		GridPanel p = new GridPanel();
		p.setModel(new DefaultTableModel(2,2));
		
		p.getSelectionModel().setSelectedIndex(0, true);
		int[] selections = p.getSelectedIndices();
		assertTrue(selections.length == 1);
		assertEquals(0, selections[0]);
		
		p.getSelectionModel().clearSelection();
		selections = p.getSelectedIndices();
		assertTrue(selections.length == 0);
	}

}
