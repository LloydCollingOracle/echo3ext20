package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;
import nextapp.echo.app.list.ListCellRenderer;

public interface PopupListCellRenderer extends ListCellRenderer {

	/**
	 * Renders the HTML that should be shown for the given value in the list model
	 * when the popup list is shown
	 */
	public String getPopupRenderedHtml(Component list, Object value, int index);
}
