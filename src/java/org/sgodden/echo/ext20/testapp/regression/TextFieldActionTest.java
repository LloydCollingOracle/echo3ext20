package org.sgodden.echo.ext20.testapp.regression;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.sgodden.echo.ext20.Label;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;

public class TextFieldActionTest extends Panel {
	public TextFieldActionTest() {
		super( "Test Field Action");
		
		final TextField field = new TextField();
		final Label label = new Label();
		field.setEmptyText( "Please input some words");
		field.setNotifyImmediately( true);
		field.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				label.setText( field.getValue());
			}
		});
		
		add( field);
		add( label);
	}
}
