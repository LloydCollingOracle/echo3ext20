package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.TableLayout;

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
        setLayout(new TableLayout());
        
        Panel heightTest = new Panel();
        add(heightTest);
        heightTest.setStyleName("testStyle");
        heightTest.add(new Label("The height, background and border of this should have been set"));
    }

}
