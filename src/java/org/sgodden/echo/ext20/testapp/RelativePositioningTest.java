package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.DeferredUiCreate;
import org.sgodden.echo.ext20.Panel;

/**
 * Tests out positioning relative to other components.
 * 
 * @author sgodden
 *
 */
@SuppressWarnings({"serial"})
public class RelativePositioningTest 
        extends Panel
        implements DeferredUiCreate {
    
    public RelativePositioningTest(){
        super("Relative positioning");
        setHtml("Relative positioning is not currently working");
    }

    public void createUI() {
        /*
        Panel innerPanel = new Panel(new TableLayout(2));
        innerPanel.setBorder(true);
        add(innerPanel);
        Button b1 = new Button("Button 1");
        b1.alignTo(
                this, 
                Alignment.RIGHT, 
                Alignment.RIGHT, 
                -5, 
                -5);
        
        innerPanel.add(b1);
        
        Button b2 = new Button("Button 2");
        b2.alignTo(
                b1, 
                Alignment.RIGHT, 
                Alignment.LEFT, 
                -5, 
                0);
        
        innerPanel.add(b2);
        */
    }

}
