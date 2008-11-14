package org.sgodden.echo.ext20;

import nextapp.echo.app.Component;
import nextapp.echo.app.list.DefaultListCellRenderer;
import nextapp.echo.app.list.ListCellRenderer;
import nextapp.echo.app.list.ListModel;

/**
 * An abstract list component.
 * @author sgodden
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractListComponent extends Component {

    public static final String LIST_CELL_RENDERER_CHANGED_PROPERTY = "listCellRenderer";

    private static final ListCellRenderer DEFAULT_LIST_CELL_RENDERER = new DefaultListCellRenderer();

    /**
     * The list model.
     */
    protected ListModel model;
    
    /**
     * The list cell renderer.
     */
    private ListCellRenderer listCellRenderer = DEFAULT_LIST_CELL_RENDERER;

    /**
     * Returns the model.
     * @return the model.
     */
    public ListModel getModel() {
        return model;
    }

    /**
     * Sets the model.
     * @param model the model.
     */
    public void setModel(ListModel model) {
        this.model = model;
    }

    /**
     * Returns the list cell renderer.
     * @return the list cell renderer.
     */
    public ListCellRenderer getCellRenderer() {
        return listCellRenderer;
    }
    
    /**
     * Sets the renderer for items.
     * The renderer may not be null (use <code>DEFAULT_LIST_CELL_RENDERER</code>
     * for default behavior).
     * 
     * @param newValue the new renderer
     */
    public void setCellRenderer(ListCellRenderer newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Cell Renderer may not be null.");
        }
        ListCellRenderer oldValue = listCellRenderer;
        listCellRenderer = newValue;
        firePropertyChange(LIST_CELL_RENDERER_CHANGED_PROPERTY, oldValue, newValue);
    }


}
