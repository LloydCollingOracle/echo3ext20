package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Component;
import nextapp.echo.app.list.ListCellRenderer;

/**
 * A list cell renderer for roles.
 * @author sgodden
 *
 */
public class RoleListCellRenderer implements ListCellRenderer {

    /**
     * Returns the description of the role as the object to render.
     */
    public Object getListCellRendererComponent(Component list, Object value,
            int index) {
        return ((Role)value).getDescription();
    }

}
