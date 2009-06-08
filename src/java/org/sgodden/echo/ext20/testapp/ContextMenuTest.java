package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Layout tests.
 * 
 * @author sgodden
 *
 */
@SuppressWarnings({"serial"})
public class ContextMenuTest extends Panel {
    
    public ContextMenuTest(){
        super(new FitLayout(), "Context Menus");
        createUI();
    }

    public void createUI() {
        
        Panel outer = new Panel();
        Panel panelContextMenuPanel = new Panel();
        panelContextMenuPanel.setBorder(true);
        panelContextMenuPanel.setWidth(400);
        panelContextMenuPanel.setHeight(200);
        Menu m = new Menu();
        m.add(new MenuItem("A menu item 1"));
        m.add(new MenuItem("A menu item 2"));
        panelContextMenuPanel.setContextMenu(m);
        panelContextMenuPanel.add(new Label("Right-Click in this panel to see a context menu"));
        outer.add(panelContextMenuPanel);
        add(outer);
    }

}
