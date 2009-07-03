package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.RadioButton;
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
        TableLayout tl = new TableLayout(4);
        setLayout(tl);
        setTitle("Button Group Test");

        add(createButton("button 1", "buttonGroup"));
        add(createButton("button 2", "buttonGroup"));

        final Button menuButton = new Button();
        menuButton.isHoverMenu(true);
        menuButton.setText("Hover menu button");
        final Menu adminMenu = new Menu();
        // adminMenu.
        menuButton.add(adminMenu);

        MenuItem mi = new MenuItem();
        mi.setText("item1");
        adminMenu.add(mi);

        mi = new MenuItem();
        mi.setText("item2");
        adminMenu.add(mi);
        
        final Menu adminMenu2 = new Menu();

        mi = new MenuItem();
        mi.setText("item3");
        adminMenu2.add(mi);

        mi = new MenuItem();
        mi.setText("item4");
        adminMenu2.add(mi);

        add(menuButton);
        
        final Button changeMenuButton = new Button("Change Hover Menu");
        changeMenuButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (menuButton.getComponent(0) == adminMenu) {
                    menuButton.remove(adminMenu);
                    menuButton.add(adminMenu2);
                } else if (menuButton.getComponent(0) == adminMenu2) {
                    menuButton.remove(adminMenu2);
                    menuButton.add(adminMenu);
                }
            }});
        add(changeMenuButton);

        final Button iconButton = new Button();
        iconButton.setEnableToggle(true);


        iconButton.setIconClass("icon-accept");
        iconButton.setText("Icon button from CSS");
        add(iconButton);

        // set the button's template, we can have a button without outter button wrapper.
        Button templatedButton = new Button();
        templatedButton.setTemplate( "<em unselectable=\"on\"><button class=\"x-btn-text\" type=\"{1}\">{0}</button></em>");
        templatedButton.setIconClass( "templated");
        templatedButton.setTooltip( "A templatedButton");
        add( templatedButton);
        
        RadioButton radioButton1 = new RadioButton( true);
        radioButton1.setName( "RadioGroup");
        radioButton1.setBoxLabel( "option one");
        add(radioButton1);

        RadioButton radioButton2 = new RadioButton();
        radioButton2.setName( "RadioGroup");
        radioButton2.setBoxLabel( "option two");
        add(radioButton2);
    }

    private static Button createButton(String buttonName, String buttonGroup) {
        Button ret = new Button(buttonName);
        ret.setToggleGroup(buttonGroup);
        ret.setCssClass("custombuttoncss");

        return ret;
    }
}
