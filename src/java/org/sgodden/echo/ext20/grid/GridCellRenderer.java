package org.sgodden.echo.ext20.grid;

import nextapp.echo.app.Component;

/**
 * Renders a value at a specified co-ordinate in a grid as a Component
 *
 * @author Lloyd Colling
 */
public interface GridCellRenderer {

	/**
	 * Renders the specified value in the grid as a Component
	 * @param gridPanel
	 * @param valueAt
	 * @param colIndex
	 * @param rowIndex
	 * @return
	 */
	public Component getGridCellRendererComponent(Component gridPanel, Object valueAt,
			int colIndex, int rowIndex);

}
