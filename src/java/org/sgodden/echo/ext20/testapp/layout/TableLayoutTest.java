package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.TableLayout;
import org.sgodden.echo.ext20.layout.TableLayoutData;

/**
 * Test for {@link TableLayout}.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class TableLayoutTest 
        extends Panel {
    
    public TableLayoutTest(){
        super("Table");
        
        TableLayout layout = new TableLayout(3);
        // try out most of the options
        layout.setFullWidth(true);
        layout.setCellPadding(10, 10, 0, 10);
        layout.setBorder(true);
        setLayout(layout);
        
        /*
         * Create components for the first row.
         */
        add(new Label("Row 1 Column 1"));
        add(new Label("Row 1 Column 2"));
        
        Component c = makeTablePanel();
        add(c);
        
        TableLayoutData tld = new TableLayoutData();
        // align this last cell to the right and bottom
        tld.setCellAlign("right");
        tld.setCellVAlign("bottom");
        c.setLayoutData(tld);
        
        /*
         * Create components for the second row.
         */
        c = new Label("Row 2 columns 1 and 2");
        add(c);
        tld = new TableLayoutData();
        // set this first cell to colspan 2
        tld.setColSpan(2);
        c.setLayoutData(tld);
        
        add(makePanel2());
        
        /*
         * Create components for third row.
         */
        c = new Label("Rows 3 and 4 columns 1 and 2");
        add(c);
        tld = new TableLayoutData();
        // this cell will span 2 columns and 2 rows.
        tld.setColSpan(2);
        tld.setRowSpan(2);
        c.setLayoutData(tld);
        
        add(new Label("Row 3 column 3"));
        
        /*
         * Create row 4 (cols 1 and 2 already taken up by the panel above)
         */
        c = new Label("Row 4 column 3");
        tld = new TableLayoutData();
        tld.setCellAlign("right");
        c.setLayoutData(tld);
        add(c);
        
    }
    
    private Panel makeTablePanel() {
        Panel ret = new Panel();
        ret.setPadding("10px");
        
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
        ret.setBodyBackground(Color.LIGHTGRAY);
        ret.setTitle("A panel");
        
        ret.add(new Label("Row 2 column 3"));
        
        return ret;
    }

}
