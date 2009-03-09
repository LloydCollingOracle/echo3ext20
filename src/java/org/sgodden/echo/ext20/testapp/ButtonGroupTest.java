package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.TableLayout;

/**
 * Tests Button Group Functionality.
 * 
 * @author rcharlton
 * 
 */
@SuppressWarnings("serial")
public class ButtonGroupTest extends Panel {

    public ButtonGroupTest() {
        TableLayout tl = new TableLayout(3);
        setLayout(tl);
        setTitle("Button Group Test");

        add(createButton("button 1", "buttonGroup"));
        add(createButton("button 2", "buttonGroup"));

        Button menuButton = new Button();
        menuButton.isHoverMenu(true);
        Menu adminMenu = new Menu();
        // adminMenu.
        menuButton.add(adminMenu);

        MenuItem mi = new MenuItem();
        mi.setText("item1");
        adminMenu.add(mi);

        mi = new MenuItem();
        mi.setText("item2");
        adminMenu.add(mi);

        add(menuButton);

    }

    private static Button createButton(String buttonName, String buttonGroup) {
        Button ret = new Button(buttonName);
        ret.setToggleGroup(buttonGroup);
        ret.setCssClass("custombuttoncss");

        return ret;
    }
}
