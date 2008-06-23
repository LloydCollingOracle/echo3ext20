package org.sgodden.echo.ext20.grid;

import java.util.EventListener;
import org.sgodden.echo.ext20.SortOrder;

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
     * @param sortOrder the order in which to sort the field.
     */
    public void sortChanged(String sortField, SortOrder sortOrder);

}
