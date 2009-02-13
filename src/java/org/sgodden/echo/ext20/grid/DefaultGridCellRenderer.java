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
        return String.valueOf(valueAt);
    }

    public String getClientSideValueRendererScript(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        if (valueAt instanceof Boolean) {
            return "if (value === true || value === \"true\") {" +
                    "    renderedValue = '<div class=\"x-grid3-check-col-on\" width=\"16\" height=\"16\"/>';" +
                    "} else {" +
                    "    renderedValue = '<div class=\"x-grid3-check-col\" width=\"16\" height=\"16\"/>';" +
                    "}";
        } else {
            return super.getClientSideValueRendererScript(gridPanel, valueAt, colIndex, rowIndex);
        }
    }

}
