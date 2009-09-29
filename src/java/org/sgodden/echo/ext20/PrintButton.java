package org.sgodden.echo.ext20;


/**
 * A button representing an action to
 * print the current record.
 * @author bwoods
 */
@SuppressWarnings("serial")
public class PrintButton extends Button {
    
    public PrintButton() {
        super();
        setActionCommand("PRINT");
    }

}
