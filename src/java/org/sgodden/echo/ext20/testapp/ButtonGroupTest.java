package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Tests Button Group Functionality.
 * 
 * @author rcharlton
 * 
 */
@SuppressWarnings("serial")
public class ButtonGroupTest extends Panel {

    public ButtonGroupTest() {

        setLayout(new FitLayout());
        setTitle("Button Group Test");
        add(createButton("button 1", "buttonGroup"));
        add(createButton("button 2", "buttonGroup"));
        add(createButton("button 3", "buttonGroup"));

    }

    private static Button createButton(String buttonName, String buttonGroup) {
        Button ret = new Button(buttonName);
        ret.setToggleGroup(buttonGroup);
        ret.setCssClass("custombuttoncss");

        return ret;
    }
}
