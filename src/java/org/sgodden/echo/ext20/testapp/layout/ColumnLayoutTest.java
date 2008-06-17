package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.ColumnLayout;
import org.sgodden.echo.ext20.layout.ColumnLayoutData;

/**
 * Provides a simple test for column layout.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class ColumnLayoutTest 
        extends Panel {
    
    public ColumnLayoutTest(){
        super(new ColumnLayout(), "Column");
        
        Panel left = new Panel();
        add(left);
        
        left.setLayoutData(new ColumnLayoutData(.5));
        left.add(new Label("Left-hand side"));
        
        Panel right = new Panel();
        add(right);
        
        right.setLayoutData(new ColumnLayoutData(.5));
        right.setCssStyle("textAlign", "right");
        right.add(new Label("Right-hand side (right-aligned)"));
    }
    
}