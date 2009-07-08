package org.sgodden.echo.ext20.componentgrid;

import org.sgodden.echo.ext20.Label;

import nextapp.echo.app.Component;

/**
 * Default implementation of a ComponentGridCellRenderer that renders
 * the model value as a Label using it's toString method.
 * 
 * @author Lloyd Colling
 *
 */
@SuppressWarnings("serial")
public class DefaultComponentGridCellRenderer implements
        ComponentGridCellRenderer {

    public Component renderCell(Component gridPanel, Object modelValue,
            int colIndex, int rowIndex) {
        return new Label(modelValue.toString());
    }

}
