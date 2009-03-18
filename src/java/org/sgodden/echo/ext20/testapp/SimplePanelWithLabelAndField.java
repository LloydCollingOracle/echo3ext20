package org.sgodden.echo.ext20.testapp;

import nextapp.echo.app.Label;

import org.sgodden.echo.ext20.HtmlLayoutData;
import org.sgodden.echo.ext20.HtmlPanel;
import org.sgodden.echo.ext20.TextField;

public class SimplePanelWithLabelAndField extends HtmlPanel {
	private Label label = new Label( "Hello");
	private TextField field = new TextField( "I'm a TextField in the inner panel");
	
	public SimplePanelWithLabelAndField() {
		super( "Simple HtmlPanel");
		setHtml( "<html><body><div>Simple HtmlPanel<div id=\"label\"></div></div> world!<div id=\"field\"></div></div></body></html>");
		label.setLayoutData( new HtmlLayoutData( "label"));
		add( label);
		field.setLayoutData( new HtmlLayoutData( "field"));
		add( field);
	}
}
