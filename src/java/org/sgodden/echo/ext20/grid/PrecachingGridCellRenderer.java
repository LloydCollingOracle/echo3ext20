package org.sgodden.echo.ext20.grid;

import nextapp.echo.app.Component;
import nextapp.echo.app.table.TableModel;

/**
 * <p>A Pre-caching Grid Cell Renderer is called by the TableModelAdapter 
 * with the Grid, TableModel, startRow and number of rows so that it 
 * can pre-cache any data that would be needed in later calls to it's 
 * getModelValue and getClientSideValueRendererScript methods.</p>
 * <p>An example of this would be to run a query to load up complex data
 * grouped by the row identifiers, allowing a single query to be run instead
 * of a query per row.</p>
 * @author Lloyd Colling
 *
 */
public interface PrecachingGridCellRenderer extends GridCellRenderer {

	/**
	 * Called to pre-cache data that will be used later in the getModelValue 
	 * and getClientSideValueRendererScript methods.
	 * @param gridPanel
	 * @param model
	 * @param offset
	 * @param pageSize
	 */
	void cacheBeforeRender(Component gridPanel, TableModel model, int offset, int pageSize);
}
