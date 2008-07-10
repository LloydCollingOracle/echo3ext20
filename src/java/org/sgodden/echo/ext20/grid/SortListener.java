package org.sgodden.echo.ext20.grid;

import java.util.EventListener;

/**
 * Interface to notify listeners of a requested change to
 * a grid panel's sort order.
 * @author sgodden
 */
public interface SortListener extends EventListener {
    
    /**
     * Notifies the listener that the following sort change
     * was requested.
     * @param sortField the field on which the grid's model should not be sorted.
     * @param ascending true to sort the field ascending, false to sort
     * the field descending.
     */
    public void sortChanged(String sortField, boolean ascending);

}
