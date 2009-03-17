package org.sgodden.echo.ext20.testapp.layout;

import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.FitLayout;

/**
 * Tests out the functionality of border layouts.
 * @author sgodden
 *
 */
public class BorderLayoutTest extends Panel {
	
	public BorderLayoutTest() {
		super(new BorderLayout());
		setTitle("Border layout");
	}
	
	private static class NorthPanel extends Panel {
		private NorthPanel(){
			super(new FitLayout());
			add(new Button("I'm a button in the north panel"));
		}
	}
	
	private static class CentrePanel extends Panel {
		private CentrePanel() {
			super(new FitLayout());
			add(new Label("I am the centre"));
		}
	}
	
	private static class SouthPanel extends Panel {
		private SouthPanel() {
			super(new FitLayout());
			add(new Label("I am the footer"));
		}
	}

}
