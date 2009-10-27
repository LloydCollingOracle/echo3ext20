package org.sgodden.echo.ext20.grid;

import nextapp.echo.app.Component;


/**
 * Default implementation of a grid cell renderer that renders values
 * using String.valueOf(value) and returns a Label component.
 *
 * @author Lloyd Colling
 */
public class DefaultGridCellRenderer extends AbstractGridCellRenderer {

    public String getModelValue(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        return valueAt == null ? null : String.valueOf(valueAt);
    }
}
