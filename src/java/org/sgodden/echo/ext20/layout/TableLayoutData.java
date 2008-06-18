package org.sgodden.echo.ext20.layout;

import java.util.HashMap;
import java.util.Map;

import nextapp.echo.app.LayoutData;

/**
 * Provides layout data for table cells.
 * @author sgodden
 */
@SuppressWarnings({"serial","unchecked"})
public class TableLayoutData 
        implements LayoutData {
    
    /**
     * The map of css styles to be applied to
     * the table cell.
     */
    private Map cssStyles = new HashMap();
    
    /**
     * The cell alignment (value of align attribute on the td).
     */
    private String cellAlign;
    
    /**
     * The cell vertical alignment (value of valign attribute on the td).
     */
    private String cellVAlign;
    
    /**
     * The column span of the cell.
     */
    private int colSpan;
    
    /**
     * The row span of the cell.
     */
    private int rowSpan;
    
    /**
     * Returns the map of css styles to be applied
     * the table cell.
     * @return the map of css styles.
     */
    public Map getCssStyles() {
        return cssStyles;
    }
    
    /**
     * Sets the specified css property, to be applied to
     * the <code>td</code> element.
     * @param propertyName the css property name.
     * @param value the value.
     */
    public void setCssStyle(String propertyName, String value) {
        cssStyles.put(propertyName, value);
    }
 
    /**
     * Sets the cell alignment.
     * @param cellAlign the cell alignment - one of "left", "center", "right",
     * "justify" or "char".
     */
    public void setCellAlign(String cellAlign) {
        this.cellAlign = cellAlign;
    }

    /**
     * Returns the cell alignment.
     * @return the cell alignment.
     */
    public String getCellAlign() {
        return cellAlign;
    }
    
    /**
     * Sets the cell vertical alignment.
     * @param cellVAlign the cell vertical alignment - one of "top", "middle",
     * "bottom" or "baseline".
     */
    public void setCellVAlign(String cellVAlign) {
        this.cellVAlign = cellVAlign;
    }

    /**
     * Returns the cell vertical alignment.
     * @return the cell vertical alignment.
     */
    public String getCellVAlign() {
        return cellVAlign;
    }
    
    /**
     * Sets the colSpan of the cell.
     * @param colSpan the colSpan of the cell.
     */
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }
    
    /**
     * Returns the colSpan of the cell.
     * @return the colSpan of the cell.
     */
    public int getColSpan() {
        return colSpan;
    }
    
    /**
     * Sets the rowSpan of the cell.
     * @param rowSpan the rowSpan of the cell.
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    /**
     * Returns the rowSpan of the cell.
     * @return the rowSpan of the cell.
     */
    public int getRowSpan() {
        return rowSpan;
    }

}