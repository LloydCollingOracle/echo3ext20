package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.ToolbarButton;

public class ToolbarButtonChangingTest extends Panel {
	public ToolbarButtonChangingTest() {
		super( "Changing ToolbarButton");
		createToolbar();
		createButton();
	}

	private void createButton() {
		Button button = new Button("Changing the buttons");
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			toolbar.remove( button1);
			toolbar.add( button3);
			toolbar.addSeparator();
			toolbar.add( button1);
			}
		});
		add( button);
		
	}
	private Toolbar toolbar = new Toolbar();
	private ToolbarButton button1 = new ToolbarButton( "Button1");
	private ToolbarButton button2 = new ToolbarButton( "Button2");
	private ToolbarButton button3 = new ToolbarButton( "Button3");
	
	private void createToolbar() {
		toolbar.add( button1);
		toolbar.addFill();
		toolbar.add( button2);
		setToolbar( toolbar);
	}
}
