package org.sgodden.echo.ext20.buttons;

import org.sgodden.echo.ext20.Button;

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
