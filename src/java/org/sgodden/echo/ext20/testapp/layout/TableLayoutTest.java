package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Button;
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
        
        TableLayout layout = new TableLayout(2);
        layout.setCssStyle("width", "100%");
        layout.setCssStyle("height", "100%");
        setLayout(layout);
        
        add(makeLabelPanel("Label 1"));
        
        Panel p = makeTablePanel();
        add(p);
        TableLayoutData tld = new TableLayoutData();
        tld.setCellAlign("right");
        tld.setCellVAlign("bottom");
        p.setLayoutData(tld);
        
        add(makeLabelPanel("Label 2"));
        add(makeFitLayout());
        
        
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
    
    private Panel makeFitLayout() {
        Panel ret = new Panel();
        ret.setBorder(true);
        
        Panel inner = new Panel();
        ret.add(inner);
        
        inner.add(new Label("Row 2 column 2"));
        
        return ret;
    }

}
