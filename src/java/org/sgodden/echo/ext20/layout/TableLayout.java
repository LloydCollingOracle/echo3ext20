package org.sgodden.echo.ext20.layout;

import java.util.HashMap;
import java.util.Map;

/**
 * A layout which uses an HTML table.  Note that if you do not
 * specify a number of columns, then this layout lays out its
 * children in a single row.
 * <p/>
 * When adding components to a panel with this layout, use
 * instances of echo {@link TableLayoutData} to provide their
 * layout data.
 * @author sgodden
 *
 */
@SuppressWarnings({"serial","unchecked"})
public class TableLayout
        implements Layout {

    /**
     * The number of columns.  If not specified, then all
     * children will be laid out in a single row.
     */
    private Integer columns;
    /**
     * Whether this table should show a border - useful for debugging layout problems.
     */
    private boolean border;
    
    /**
     * The map of css styles to be applied to
     * the table cell.
     */
    private Map tableCssStyle = new HashMap();

    /**
     * Creates a table layout.
     * @param columns the number of columns for the table.
     */
    public TableLayout(int columns) {
        this.columns = columns;
    }
    
    /**
     * Creates a new table layout, where all components
     * will be rendered in a single row.
     */
    public TableLayout() {
    }

    /**
     * Returns the number of columns in the table.
     * @return the number of columns in the table.
     */
    public Integer getColumns() {
        return columns;
    }
    
    /**
     * Returns the map of css styles to be applied
     * the table cell.
     * @return the map of css styles.
     */
    public Map getTableCssStyles() {
        return tableCssStyle;
    }
    
    /**
     * Sets the specified css property, to be applied to
     * the <code>table</code> element.
     * @param propertyName the css property name.
     * @param value the value.
     */
    public void setCssStyle(String propertyName, String value) {
        tableCssStyle.put(propertyName, value);
    }
    
    /**
     * Sets whether the table should show a border for all cells (useful
     * for debugging layout problems).
     * @param border whether the table should show a border for all cells.
     */
    public void setBorder(boolean border) {
        this.border = border;
    }
    
    /**
     * Sets whether the table should show a border for all cells (useful
     * for debugging layout problems).
     * @return whether the table should show a border for all cells.
     */
    public boolean getBorder() {
        return border;
    }

}