package org.sgodden.echo.ext20.grid;

import org.sgodden.echo.ext20.util.InsertEntities;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;


/**
 * Default implementation of a grid cell renderer that renders values
 * using String.valueOf(value) and returns a Label component.
 *
 * @author Lloyd Colling
 */
public class DefaultGridCellRenderer implements GridCellRenderer {

    public Component getGridCellRendererComponent(Component gridPanel,
            Object valueAt, int colIndex, int rowIndex) {
        return new Label(valueAt == null ? "" : htmlise(String.valueOf(valueAt)));
    }
    
    private String htmlise(String value) {
        return InsertEntities.insertHTMLEntities(value);
    }

}
