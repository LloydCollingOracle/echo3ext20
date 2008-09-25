package org.sgodden.echo.ext20.layout;


/**
 * A layout which uses an HTML table.  Note that if you do not
 * specify a number of columns, then this layout lays out its
 * children in a single row.
 * <p/>
 * When adding components to a panel with this layout, use
 * instances of {@link TableLayoutData} to provide their
 * layout data.
 * <p>
 * <em>IMPORTANT</em> - Due to limitations with the
 * ext layout that is used on the client side, once a panel with a table layout has
 * been rendered, you may only remove and add components
 * from / to the end of the panel.  This limitation will
 * be removed in a future release.
 * </p>
 * <p>
 * Here's an example showing standard table tricks such as
 * aligning cells, and setting colspan and rowspan.
 * </p>
 * <pre class="code">
public class TableLayoutTest 
        extends Panel {
    
    public TableLayoutTest(){
        super("Table");
        
        TableLayout layout = new TableLayout(3);
        // set 100% width
        layout.setCssStyle("width", "100%");
        // renders the table border to help us "debug" the table
        layout.setBorder(true);
        setLayout(layout);
        
        //
        // Create components for the first row.
        // 
        add(new Label("Row 1 Column 1"));
        add(new Label("Row 1 Column 2"));
        
        Component c = makeTablePanel();
        add(c);
        
        TableLayoutData tld = new TableLayoutData();
        // align this last cell to the right and bottom
        tld.setCellAlign("right");
        tld.setCellVAlign("bottom");
        c.setLayoutData(tld);
        
        //
        // Create components for the second row.
        // 
        c = new Label("Row 2 columns 1 and 2");
        add(c);
        tld = new TableLayoutData();
        // set this first cell to colspan 2
        tld.setColSpan(2);
        c.setLayoutData(tld);
        
        add(makePanel2());
        
        //
        // Create components for third row.
        // 
        c = new Label("Rows 3 and 4 columns 1 and 2");
        add(c);
        tld = new TableLayoutData();
        // this cell will span 2 columns and 2 rows.
        tld.setColSpan(2);
        tld.setRowSpan(2);
        c.setLayoutData(tld);
        
        add(new Label("Row 3 column 3"));
        
        //
        // Create row 4 (cols 1 and 2 already taken up by the panel above)
        // 
        c = new Label("Row 4 column 3");
        tld = new TableLayoutData();
        tld.setCellAlign("right");
        c.setLayoutData(tld);
        add(c);
        
    }
    
    private Panel makeTablePanel() {
        Panel ret = new Panel();
        
        TableLayout layout = new TableLayout();
        ret.setLayout(layout);
        
        ret.setBorder(true);
        ret.add(new Button("Button 1"));
        ret.add(new Button("Button 2"));
        
        return ret;
    }
    
    private Panel makePanel2() {
        Panel ret = new Panel();
        ret.setBorder(true);
        ret.setHeight(100);
        ret.setBackground(Color.LIGHTGRAY);
        ret.setTitle("A panel");
        
        ret.add(new Label("Row 2 column 3"));
        
        return ret;
    }

} </pre>
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

}