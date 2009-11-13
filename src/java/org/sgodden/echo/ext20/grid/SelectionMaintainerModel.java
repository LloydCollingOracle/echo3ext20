package org.sgodden.echo.ext20.grid;

/**
 * A sortable table model that allows for the maintenance of selection values
 * for a specific GridPanel.
 * @author Lloyd Colling
 *
 */
public interface SelectionMaintainerModel extends SortableTableModel {

    /**
     * Retrieves a value used to represent the selection values of the grid panel
     * in relation to this model.
     * @param gridPanel
     * @return
     */
    public Object getPersistentSelection(GridPanel gridPanel);

    /**
     * Updates the grid panel's selection to match the selection values retrieved
     * from a previous call to getPersistentSelection.
     * @param gridPanel
     * @param maintainedSelection
     */
    public void applyPersistentSelection(GridPanel gridPanel,
            Object maintainedSelection);
    
}
