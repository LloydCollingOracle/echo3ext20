package org.sgodden.echo.ext20.layout;

import java.io.Serializable;

import nextapp.echo.app.Table;

/**
 * A layout which uses an HTML table.
 * <p/>
 * N.B. - this layout seems problematic in terms of manipulating layout data
 * for each row and cell.  You may be better advised to use an echo3 {@link Table}
 * instead.
 * @author sgodden
 *
 */
public class TableLayout
        implements Layout, Serializable {

    private static final long serialVersionUID = 20080213L;
    private int columns;
    private int defaultPadding = 5;

    /**
     * Creates a table layout.
     * @param columns the number of columns for the table.
     */
    public TableLayout(int columns) {
        this.columns = columns;
    }

    /**
     * Creates a table layout.
     * @param columns the number of columns for the table.
     * @param defaultPadding the default padding for table cells.
     */
    public TableLayout(int columns, int defaultPadding) {
        this.columns = columns;
        this.defaultPadding = defaultPadding;
    }

    /**
     * Returns the number of columns in the table.
     * @return the number of columns in the table.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the default padding for each cell.
     * @return the default padding for each cell.
     */
    public int getDefaultPadding() {
        return defaultPadding;
    }
}
