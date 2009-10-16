package org.sgodden.echo.ext20.testapp.regression;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Container;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.layout.BorderLayout;
import org.sgodden.echo.ext20.layout.BorderLayoutData;

public class BorderLayoutTest extends Panel {

	public BorderLayoutTest() {
		super( new BorderLayout(), "Border Layout Test");
		Button button = new Button( "Test button");
		button.setLayoutData( new BorderLayoutData( BorderLayout.NORTH));
		Container panel = new Panel( "Simple Panel");
		panel.setLayoutData( new BorderLayoutData( BorderLayout.CENTER));
		add( button);
		add( panel);
	}
}
