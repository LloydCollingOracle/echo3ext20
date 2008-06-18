package org.sgodden.echo.ext20.layout;

import java.util.HashMap;
import java.util.Map;

/**
 * A layout which uses an HTML table.  Note that if you do not
 * specify a number of columns, then this layout lays out its
 * children in a single row.
 * <p/>
 * When adding components to a panel with this layout, use
 * instances of {@link TableLayoutData} to provide their
 * layout data.
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