package org.sgodden.echo.ext20.testapp;

import org.sgodden.echo.ext20.HtmlLayoutData;
import org.sgodden.echo.ext20.HtmlPanel;
import org.sgodden.echo.ext20.TextField;

public class HtmlPanelTest extends HtmlPanel {
	private TextField textField1 = new TextField( "HtmlPanel");
	private TextField textField2 = new TextField( "Hello from field2");
	private SimplePanelWithLabelAndField simplePanel = new SimplePanelWithLabelAndField();
	
	public HtmlPanelTest() {
		super( "Test HtmlPanel");
		setHtml( "<html><body><div>This is a HtmlPanel include a field <div id=\"field1\"></div></div> And another HtmlPanel <div id=\"simplePanel\"></div> And <div id=\"field2\"></div></div></body></html>");
		textField1.setLayoutData( new HtmlLayoutData( "field1"));
		add( textField1);
		simplePanel.setLayoutData( new HtmlLayoutData( "simplePanel"));
		add( simplePanel);
		textField2.setLayoutData( new HtmlLayoutData( "field2"));
		add( textField2);
	}
}
