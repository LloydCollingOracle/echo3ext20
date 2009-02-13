package org.sgodden.echo.ext20.grid;

import nextapp.echo.app.Component;

/**
 * Implementation of a grid cell renderer that renders values
 * using String.valueOf(value) and returns a Label component.
 * 
 * No HTML escaping is performed by this renderer, so it can be very dangerous
 * to use with user-supplied content
 *
 * @author Lloyd Colling
 */
public class NoEscapingGridCellRenderer implements GridCellRenderer {

    public String getModelValue(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        return String.valueOf(valueAt);
    }

    public String getClientSideValueRendererScript(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        return "renderedValue = value;";
    }

}
