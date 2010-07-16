package org.sgodden.echo.ext20;

import nextapp.echo.app.list.DefaultListCellRenderer;
import nextapp.echo.app.list.ListCellRenderer;
import nextapp.echo.app.list.ListModel;
import nextapp.echo.app.list.ListSelectionModel;

/**
 * An abstract list component.
 * @author sgodden
 *
 */
public interface AbstractListComponent {

    public static final String PROPERTY_LIST_CELL_RENDERER_CHANGED = "listCellRenderer";

    public static final ListCellRenderer DEFAULT_LIST_CELL_RENDERER = new DefaultListCellRenderer();

    /**
     * Returns the model.
     * @return the model.
     */
    public ListModel getModel();

    /**
     * Sets the model.
     * @param model the model.
     */
    public void setModel(ListModel model);

    /**
     * Returns the list cell renderer.
     * @return the list cell renderer.
     */
    public ListCellRenderer getCellRenderer();
    
    /**
     * Sets the renderer for items.
     * The renderer may not be null (use <code>DEFAULT_LIST_CELL_RENDERER</code>
     * for default behavior).
     * 
     * @param newValue the new renderer
     */
    public void setCellRenderer(ListCellRenderer newValue);

    public void setSelectionModel(ListSelectionModel selectionModel);
    
    public ListSelectionModel getSelectionModel();

}
