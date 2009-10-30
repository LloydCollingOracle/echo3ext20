package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Menu;
import org.sgodden.echo.ext20.MenuItem;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.Window;
import org.sgodden.echo.ext20.WindowListener;

/**
 * Example of nested menus.
 * @author rcharlton
 *
 */
@SuppressWarnings("serial")
public class MultiLevelMenuTest extends Panel {

	/**
	 * A simple menu with s 2 levels.
	 */
	public MultiLevelMenuTest() {
		super( "Multi Menu Test");
		createToolbar();
	}

	private Menu submenu = new Menu();
	private MenuItem submenuItem;
	
	/**
	 * Create the toolbar for the panel 
	 */
	private void createToolbar() {
		
		Toolbar toolbar = new Toolbar();
		Button button1 = new Button( "Button1");
		Menu menu = new Menu();
		MenuItem mi2 = new MenuItem("item 2");
		buildSecondLevelMenu();
		
		button1.isHoverMenu(true);
		toolbar.add( button1);
		menu.add(mi2);
		
		mi2.add(submenu);
		button1.setMenu(menu);
		setToolbar( toolbar);
	}
	
	/**
	 * Remove the sub menu item then re-build it
	 */
	private void rebuildSubMenu(){
		submenu.remove(submenuItem);
		buildSecondLevelMenu();
		
	}
	
	/**
	 * Builds the second level item.
	 */
	private void buildSecondLevelMenu(){
		submenuItem = new MenuItem("sub menu item");
		
		submenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Window w = new Window();
				 w.addWindowListener(new WindowListener() {
                    public void windowClosing(ActionEvent windowEvent) {
                    	rebuildSubMenu();
                    }
                });
				w.setHeight(200);
				w.setWidth(200);
				add(w);
			}
		});
		
		submenu.add(submenuItem);
	}
}