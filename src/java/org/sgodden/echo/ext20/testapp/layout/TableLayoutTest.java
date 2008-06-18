package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
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
        layout.setCssStyle("width", "100%");
        //layout.setCssStyle("height", "100%");
        layout.setBorder(true); // to visually 'debug' the layout
        setLayout(layout);
        
        // row 1
        add(makeLabelPanel("Row 1 Column 1"));
        add(makeLabelPanel("Row 1 Column 2"));
        
        Panel p = makeTablePanel();
        add(p);
        
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("right");
        tld.setCellVAlign("bottom");
        p.setLayoutData(tld);
        
        // row 2 - try out colspan
        p = makeLabelPanel("Row 2 columns 1 and 2");
        add(p);
        tld = new TableLayoutData();
        tld.setColSpan(2);
        p.setLayoutData(tld);
        
        add(makePanel2());
        
        // row 3 - try out rowspan
        p = makeLabelPanel("Rows 3 and 4 columns 1 and 2");
        add(p);
        tld = new TableLayoutData();
        tld.setColSpan(2);
        tld.setRowSpan(2);
        p.setLayoutData(tld);
        
        add(makeLabelPanel("Row 3 column 3"));
        
        // row 4 (cols 1 and 2 already taken up by the panel above)
        add(makeLabelPanel("Row 4 column 3"));
        
    }
    
    private Panel makeLabelPanel(String labelText) {
        Panel ret = new Panel();
        ret.add(new Label(labelText));
        return ret;
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

}
