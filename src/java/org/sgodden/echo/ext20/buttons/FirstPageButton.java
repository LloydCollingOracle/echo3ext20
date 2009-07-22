package org.sgodden.echo.ext20.buttons;

import org.sgodden.echo.ext20.Button;

/**
 * A button representing an action to
 * go to the first page in a list.
 * @author sgodden
 */
@SuppressWarnings("serial")
public class FirstPageButton extends Button {
    
    public FirstPageButton() {
        super();
        setIconClass("x-tbar-page-first");
        setId("firstPage");
    }

}
