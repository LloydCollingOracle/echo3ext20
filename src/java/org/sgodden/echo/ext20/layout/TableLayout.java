package org.sgodden.echo.ext20.layout;

import org.sgodden.echo.ext20.testapp.layout.TableLayoutTest;
import org.sgodden.echo.ext20.testapp.layout.TableLayoutTest2;


/**
 * A layout which uses an HTML table.  Note that if you do not
 * specify a number of columns, then this layout lays out its
 * children in a single row.
 * <p/>
 * When adding components to a panel with this layout, use
 * instances of {@link TableLayoutData} to provide their
 * layout data.
 * </p>
 * <p>
 * Take a look at {@link TableLayoutTest} and {@link TableLayoutTest2}
 * for examples of how to use this layout, including arbitrary
 * insertion and deletion and swapping of rows.
 * @author sgodden
 *
 */
@SuppressWarnings({"serial"})
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
    private boolean border = false;
    /**
     * Whether the table should take 100% of the available
     * width of its container.
     */
    private boolean fullWidth = false;
    /**
     * Whether the table should take 100% of the available
     * height of its container.
     */
    private boolean fullHeight = false;
    /**
     * Padding for cells, in pixels.
     */
    private String cellPadding;
    /**
     * Spacing for table cells, in pixels.
     */
    private int cellSpacing;
    
    /**
     * The widths of the columns
     */
    private int[] columnWidths = null;

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
     */
    public TableLayout(int columns, boolean fullWidth, boolean fullHeight) {
        this(columns);
        setFullWidth(fullWidth);
        setFullHeight(fullHeight);
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
     * Sets whether the table should take 100% of the width of its container.
     */
    public void setFullWidth(boolean fullWidth) {
        this.fullWidth = fullWidth;
    }
    
    /**
     * Returns whether the table should take 100% of the width of its container.
     * @return whether the table should take 100% of the width of its container.
     */
    public boolean getFullWidth() {
        return fullWidth;
    }
    
    /**
     * Sets whether the table should take 100% of the height of its container.
     */
    public void setFullHeight(boolean fullHeight) {
        this.fullHeight = fullHeight;
    }
    
    /**
     * Returns whether the table should take 100% of the height of its container.
     * @return whether the table should take 100% of the height of its container.
     */
    public boolean getFullHeight() {
        return fullHeight;
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

    /**
     * Returns the cell spacing, in pixels.
     * @return the cell spacing, in pixels.
     */
    public int getCellSpacing() {
        return cellSpacing;
    }

    /**
     * Returns the cell padding, in CSS format.
     * @return the cell padding, in CSS format.
     */
    public String getCellPadding() {
        return cellPadding;
    }

    /**
     * Sets the cell padding on all four sides, in pixels.
     * @param cellPadding the cell padding on all four sides, in pixels.
     */
    public void setCellPadding(int cellPadding) {
        this.cellPadding = "" + cellPadding + "px";
    }
    
    /**
     * Sets the cell padding, in pixels.
     * @param top the top padding.
     * @param right the right padding.
     * @param bottom the bottom padding.
     * @param left the left padding.
     */
    public void setCellPadding(int top, int right, int bottom, int left) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(top));
        sb.append("px ");
        sb.append(String.valueOf(right));
        sb.append("px ");
        sb.append(String.valueOf(bottom));
        sb.append("px ");
        sb.append(String.valueOf(left));
        sb.append("px");
        this.cellPadding = sb.toString();
    }

    /**
     * Sets the cell spacing, in pixels.
     * @param cellSpacing the cell spacing, in pixels.
     */
    public void setCellSpacing(int cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    /**
     * Returns the widths of the columns in the layout
     * @return
     */
    public int[] getColumnWidths() {
        return columnWidths;
    }

    public void setColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
    }

}