package org.sgodden.echo.ext20.buttons;

import org.sgodden.echo.ext20.Button;

/**
 * A button representing an action to
 * go to the previous page in a list.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class PreviousPageButton extends Button {
    
    public PreviousPageButton() {
        super();
        setIconClass("x-tbar-page-prev");
    }

}
