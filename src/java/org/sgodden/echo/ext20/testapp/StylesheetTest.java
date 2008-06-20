package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;

/**
 * A simple test to ensure that the components
 * are themeable using echo3 stylesheets.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class StylesheetTest extends Panel {
    
    /**
     * Creates a new stylesheet test.
     */
    public StylesheetTest() {
        super("Stylesheets");
        
        Panel heightTest = new Panel();
        add(heightTest);
        heightTest.setRenderId("styleTest");
        heightTest.setStyleName("testStyle");
        heightTest.add(new Label("Various properties should have been set on this panel from the stylesheet"));
        heightTest.addButton(new Button("A button"));
    }

}
