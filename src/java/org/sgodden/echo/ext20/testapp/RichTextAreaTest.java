package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.RichTextArea;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Tests Button Group Functionality.
 * 
 * @author rcharlton
 * 
 */
@SuppressWarnings("serial")
public class RichTextAreaTest extends Panel {

    public RichTextAreaTest() {
    	super(new FitLayout(), "Rich Text Area Test");
    	add (new RichTextArea());
    }
}
