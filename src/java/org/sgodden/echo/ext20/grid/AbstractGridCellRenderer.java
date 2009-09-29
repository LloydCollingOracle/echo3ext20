package org.sgodden.echo.ext20.grid;

import java.io.Serializable;

import nextapp.echo.app.Component;

/**
 * An abstract implementation of the grid cell renderer interface that defines
 * a client-side value renderer that html encodes the client-side model value.
 * 
 * @author Lloyd Colling
 *
 */
public abstract class AbstractGridCellRenderer implements GridCellRenderer, Serializable {

    public abstract String getModelValue(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex);

    public String getClientSideValueRendererScript(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        return "if (value == null || value == \"\") {" +
                "    renderedValue = \"\";" +
                "} else {" +
                "    renderedValue = Ext.util.Format.htmlEncode(value);" +
                "}";
    }

}
