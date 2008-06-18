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
     * The default padding for cells.
     * FIXME - by specifying this on layout data, does it mean we can't set it in stylesheets?
     */
    private int defaultPadding = 5;
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
    public Integer getColumns() {
        return columns;
    }

    /**
     * Returns the default padding for each cell.
     * @return the default padding for each cell.
     */
    public int getDefaultPadding() {
        return defaultPadding;
    }
    
    /**
     * Sets the default padding for each cell.
     * @param defaultPadding the default paddinf for each cell.
     */
    public void setDefaultPadding(int defaultPadding) {
        this.defaultPadding = defaultPadding;
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