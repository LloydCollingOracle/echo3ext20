package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Panel;

/**
 * Test playing around with CSS absolute positioning.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class AbsoluteLayoutTest 
    extends Panel {
    
    public AbsoluteLayoutTest(){
        super("Absolute");
        
        Panel topLeft = new Panel();
        add(topLeft);
        
        topLeft.setRenderId("resizePanel");
        topLeft.setBorder(true);
        topLeft.setCssStyle("position", "absolute");
        topLeft.setCssStyle("left", "0px");
        topLeft.setCssStyle("bottom", "0px");
        topLeft.setCssStyle("top", "0px");
        topLeft.add(new Label("This should be top left"));
        
        Panel bottomRight = new Panel();
        add(bottomRight);
        
        bottomRight.setBorder(true);
        bottomRight.setCssStyle("position", "absolute");
        bottomRight.setCssStyle("right", "0px");
        bottomRight.setCssStyle("bottom", "0px");
        bottomRight.add(new Label("This should be bottom right"));
    }

}
