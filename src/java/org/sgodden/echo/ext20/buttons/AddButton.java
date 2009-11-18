package org.sgodden.echo.ext20.buttons;

import org.sgodden.echo.ext20.Button;

/**
 * A button representing an action to
 * add something to something.
 * @author Lloyd Colling
 */
@SuppressWarnings("serial")
public class AddButton extends Button {
    
    public AddButton() {
        super();
        setActionCommand("ADD");
        setCssClass("add-button");
    }

}
