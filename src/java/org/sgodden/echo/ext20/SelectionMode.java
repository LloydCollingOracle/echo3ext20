package org.sgodden.echo.ext20;

/**
 * Enumerates the available selection modes for lists.
 * @author sgodden
 */
public enum SelectionMode {
    
    /**
     * Only one list index can be selected at a time.
     */
    SINGLE_SELECTION,
    /**
     * One contiguous index interval can be selected at a time.
     */
    SINGLE_INTERVAL_SELECTION,
    /**
     * In this mode, there's no restriction on what can be selected.
     */
    MULTIPLE_INTERVAL_SELECTION

}
