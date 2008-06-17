package org.sgodden.echo.ext20.layout;

import java.util.HashMap;
import java.util.Map;

import nextapp.echo.app.layout.GridLayoutData;

/**
 * A layout which uses an HTML table.
 * <p/>
 * When adding components to a panel with this layout, use
 * instances of echo {@link GridLayoutData} to provide their
 * layout data.  This is confusing, apologies.
 * @author sgodden
 *
 */
@SuppressWarnings({"serial","unchecked"})
public class TableLayout
        implements Layout {

    private Integer columns;
    private int defaultPadding = 5;
    
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

}