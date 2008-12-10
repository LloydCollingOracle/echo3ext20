package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;
import nextapp.echo.app.list.ListCellRenderer;

/**
 * A list renderer that also returns a location for the image
 * to represent each value.
 * @author Lloyd Colling
 *
 */
public interface ListImageCellRenderer extends ListCellRenderer {

    /**
     * Returns the location of the associated image for the given value
     * relative to the application context.
     * @param list
     * @param value
     * @param index
     * @return
     */
    public String getImageLocation(Component list, Object value, int index);
}
