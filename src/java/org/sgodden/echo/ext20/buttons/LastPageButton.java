package org.sgodden.echo.ext20.buttons;

import org.sgodden.echo.ext20.Button;

/**
 * A button representing an action to
 * go to the last page in a list.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class LastPageButton extends Button {
    
    public LastPageButton() {
        super();
        setIconClass("x-tbar-page-last");
        setId("lastPage");
    }

}
