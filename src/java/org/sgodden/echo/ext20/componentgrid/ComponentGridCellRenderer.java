package org.sgodden.echo.ext20.componentgrid;

import java.io.Serializable;

import nextapp.echo.app.Component;

/**
 * <p>Interface for renderers of ComponentGridPanel models.</p>
 * 
 * <p>We are not using the Echo3 renderer as that requires the table/grid to be a subclass
 * of nextapp.echo.app.Table, which ComponentGridPanel isn't.</p>
 * 
 * @author Lloyd Colling
 *
 */
public interface ComponentGridCellRenderer extends Serializable {

    /**
     * Renders the contents of a ComponentGridPanel cell as a component
     * @param gridPanel
     * @param modelValue
     * @param colIndex
     * @param rowIndex
     * @return
     */
    public Component renderCell(Component gridPanel, Object modelValue, int colIndex, int rowIndex);
}
